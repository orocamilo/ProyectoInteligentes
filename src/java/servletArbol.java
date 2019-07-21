/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author camilo
 */
@WebServlet(urlPatterns = {"/servletArbol"})
public class servletArbol extends HttpServlet {

    DBConnect db = new DBConnect();
    clsMetodosArbol metodos;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String[] atributos = {"anchoSepalo", "altoSepalo","anchoPetalo", "altoPetalo"};
            String[] clases = {"setosa", "virginica","versicolor"};
            
            int nrangos[] = {13,16,8,7};
            int nclases = 3;
            double[][][] matrices = new double[atributos.length][0][0];
            for (int i = 0; i < matrices.length; i++) {
                matrices[i] = new double [nrangos[i]][nclases];
            }
            
            HashMap<String,double[][]> matrices2 = new HashMap<>();
            double[][] MaxMin = getMaxMin(atributos);
            this.metodos = new clsMetodosArbol();
            for (int i = 0; i < atributos.length; i++) {
                this.metodos.calcularRangos(MaxMin[i][0], MaxMin[i][1], nrangos[i],atributos[i]);
                this.crearMatriz(atributos[i]);
                double[][] matriz = this.getMatriz(atributos[i], nrangos[i], nclases);
                matrices[i] = matriz;
                matrices2.put(atributos[i], matriz);
            }
            this.metodos.RealizarCalculos(atributos, matrices);
            this.metodos.crearArbol(matrices2, clases);
            HashMap datos = new HashMap();
            datos.put("raiz", this.metodos.getRaiz());
            datos.put("arbol", this.metodos.getArbol());
            
            Gson gson = new Gson();
            out.print(gson.toJson(datos));
            
        }

    }

    public double[][] getMatriz(String atributo,int nrangos,int nclases){
        double[][] matriz = new double[nrangos][nclases+1];
        String query = "Select * from Matriz" + atributo;
        ResultSet result = this.db.executeQuery(query, new LinkedList<String>());
        int i = 0; 
        try {
            while(result.next()){
                for (int j = 0; j < nclases; j++) {
                    matriz[i][j] = result.getDouble(j+2);
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(servletArbol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return matriz;
    }
    
    public void crearMatriz(String atributo) {
        double rangos[][] = this.metodos.getRangos();
        String query = "create or replace view Matriz"+atributo+" as \n";
        for (int i = 0; i < rangos.length; i++) {
            if (i != 0) {
                query += "union \n";
            }
            query += "select concat("+rangos[i][0]+",'..',"+rangos[i][1]+")"+" rango,sum (CASE WHEN clase like '%setosa%' THEN 1 ELSE 0 END) setosa,\n"
                    + "		sum(CASE WHEN clase like '%virginica%' THEN 1 ELSE 0 END) virginica,\n"
                    + "		sum(CASE WHEN clase like '%versicolor%' THEN 1 ELSE 0 END) versicolor\n"
                    + "from \"Iris\""
                    + "where \""+atributo+"\" >= ";
            if (i == rangos.length - 1) {
                query += rangos[i][0] + " and " + "\""+atributo+"\" <= " + rangos[i][1];
            } else {
                query += rangos[i][0] + " and " + "\""+atributo+"\" < " + rangos[i][1];
            }
        }
        this.db.executeQueryWithOutResultSet(query, new LinkedList<String>());
        System.out.println(query);
    }
    
    public double[][] getMaxMin(String atributos[]){
        try {
            double[][] MaxMin = new double[atributos.length][2];
            for (int i = 0; i < MaxMin.length; i++) {
                String query = "select max(\""+atributos[i]+"\")" + ",min(\""+atributos[i]+"\") from \"Iris\"";
//                System.out.println(query);
                ResultSet result = this.db.executeQuery(query, new LinkedList<String>());
                result.next();
                double max = result.getDouble("max");
                double min = result.getDouble("min");
                double[] tupla = {max, min};
                MaxMin[i] = tupla;
            }
            return MaxMin;
        } catch (SQLException ex) {
            Logger.getLogger(servletArbol.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Iris[] getDatos() {
        try {
            ResultSet result = db.executeQuery("select \"altoSepalo\",\"anchoSepalo\", \"altoPetalo\", \"anchoPetalo\", clase from public.\"Iris\" order by identificador", new LinkedList<String>());
            LinkedList<Iris> lista = new LinkedList<>();
            ResultSet resul2 = db.executeQuery("select count(*) cant from \"Iris\" group by identificador;", new LinkedList<String>());
            resul2.next();
            int setenta = resul2.getInt("cant");
            resul2.next();
            int treinta = resul2.getInt("cant");
            while (result.next()) {
                double altoSepalo = result.getDouble("altoSepalo");
                double anchoSepalo = result.getDouble("anchoSepalo");
                double anchoPetalo = result.getDouble("anchoPetalo");
                double altoPetalo = result.getDouble("altoPetalo");
                String clase = result.getString("clase");
                Iris flor = new Iris(anchoSepalo, altoSepalo, anchoPetalo, altoPetalo, clase);
                lista.add(flor);
            }
            Iris vector[] = new Iris[lista.size()];
            Iterator<Iris> it = lista.iterator();
            int i = 0;
            while (it.hasNext()) {
                vector[i] = it.next();
                i++;
            }
            return vector;
        } catch (SQLException ex) {
            Logger.getLogger(servletArbol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
