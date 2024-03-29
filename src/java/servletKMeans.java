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
import java.util.HashMap;
import java.util.LinkedList;
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
@WebServlet(urlPatterns = {"/servletKMeans"})
public class servletKMeans extends HttpServlet {

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
            Gson gson = new Gson();
            DBConnect db = new DBConnect();
            LinkedList<Integer> centroides = new LinkedList<>();
            HashMap<Integer,LinkedList<Iris>> mapa = new HashMap<>();
            int k = Integer.parseInt(request.getParameterMap().get("k")[0]);
            String query = "select kmeans("+k+");";
            db.executeQuery(query, new LinkedList<String>());
            
            query = "select distinct centroide from \"Iris\";";
            ResultSet resul = db.executeQuery(query, new LinkedList<String>());
            try {
                while(resul.next()){
                    centroides.add(resul.getInt(1));
                }
                for (Integer centroide : centroides) {
                    query = "select \"anchoSepalo\",\"altoSepalo\",\"anchoPetalo\",\"altoPetalo\",\"clase\" from \"Iris\" where centroide = "+centroide+";";
                    resul = db.executeQuery(query, new LinkedList<String>());
                    LinkedList<Iris> flores = new LinkedList<>();
                    while(resul.next()){
                        Iris flor = new Iris(resul.getDouble(1), resul.getDouble(2), resul.getDouble(3), resul.getDouble(4), resul.getString(5));
                        flores.add(flor);
                    }
                    mapa.put(centroide, flores);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(servletKMeans.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.print(gson.toJson(mapa));
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
