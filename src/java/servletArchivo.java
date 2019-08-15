/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.mail.Multipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author camilo
 */
@WebServlet(urlPatterns = {"/servletArchivo"})
public class servletArchivo extends HttpServlet {
    GestorArchivos gestorArchivos = new GestorArchivos();
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
//            InputStream inputStream = request.getInputStream();
//            File file = new File("D:\\Documentos\\NetBeansProjects\\RegresionLineal\\files\\1.txt");
//            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            String saveDirectory = "D:\\Documentos\\NetBeansProjects\\RegresionLineal\\files";
            MultipartRequest multipart = new MultipartRequest(request, saveDirectory);
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
        String operacion = (String) request.getParameterMap().get("operacion")[0];
        Gson json = new Gson();
        String respuesta = null;
        if(operacion.equals("filenames")){
            respuesta = json.toJson(gestorArchivos.getFileNames());
        }else{
            String filename = (String) request.getParameterMap().get("archivo")[0];;
            int porcentajeModelo = Integer.parseInt((String) request.getParameterMap().get("porcentaje")[0]);
            respuesta = "archivo seleccionado: " + filename;
            String contenido = gestorArchivos.obtenerContenido(filename);
            GestorDataSet gestorDataSet = new GestorDataSet(porcentajeModelo, contenido);
            HttpSession session = request.getSession(true);          
            session.setAttribute("gestorDataSet", gestorDataSet);
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(respuesta);
        }
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
