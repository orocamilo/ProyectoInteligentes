/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author camilo
 */
@WebServlet(urlPatterns = {"/servletApriori"})
public class servletApriori extends HttpServlet {
    GestorArchivos gestor = new GestorArchivos();
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
            String nombreArchivo = "transacciones2.csv";
            
            if(request.getParameterMap().get("operacion")[0].equals("dataset")){
                mandarDataSet(nombreArchivo, out);
            }
            else{
                double confianza = Double.parseDouble((String)request.getParameterMap().get("confianza")[0]);
                double soporte = Double.parseDouble((String)request.getParameterMap().get("soporte")[0]);
                calcularAsociaciones(nombreArchivo, confianza, soporte, out);
            }
            
           
        } 
    }

    private void calcularAsociaciones(String filename,double confianza, double soporte, final PrintWriter out) {
        try {
            String dataSetPath = gestor.DIRECTORIO + "\\" +filename;
            DataSource dataSource = new DataSource(dataSetPath);
            dataSource.getDataSet();
            Instances data = dataSource.getDataSet();
            
            Apriori model = new Apriori();
            
            
            model.setMinMetric(confianza);//confianza
            model.setLowerBoundMinSupport(soporte);//soporte
            //                model.setNumRules(20);
            model.buildAssociations(data);
            //                Gson gson = new Gson();
            //                String result = gson.toJson(model);
            out.println(model);
            System.out.println(model);
        } catch (Exception ex) {
            Logger.getLogger(servletApriori.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void mandarDataSet(String fileName, final PrintWriter out) {
        try {
            String contenido = gestor.obtenerContenido(fileName);
            String[] lineas = contenido.split("\n");
            Gson gson = new Gson();
            String respuesta = gson.toJson(lineas);
            out.print(respuesta);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(servletApriori.class.getName()).log(Level.SEVERE, null, ex);
        }
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
