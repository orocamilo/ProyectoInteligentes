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
import java.util.Scanner;
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
@WebServlet(urlPatterns = {"/servletKNN"})
public class servletKNN extends HttpServlet {
    clsMetodosKNN metodos;
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
           
            String data = (String) request.getParameterMap().get("data")[0];
            int k = Integer.parseInt(request.getParameterMap().get("k")[0]);
            HashMap<String,Object>[] tablaDatos = obtenerDatos(data);
            double porcentajeEntramiento = 70;
            int numDatosEntrenamiento = (int) Math.round(tablaDatos.length* (porcentajeEntramiento/100));
            int numDatosPrueba = tablaDatos.length - numDatosEntrenamiento ;
            HashMap<String,Object>[] prueba = new HashMap[numDatosPrueba];
            HashMap<String,Object>[] entrenamiento = new HashMap[numDatosEntrenamiento];
            obtenerRegistrosPruebaEntrenamiento(prueba, entrenamiento, tablaDatos);
            this.metodos = new clsMetodosKNN(prueba, entrenamiento);
            String pronostico[] = this.metodos.KNN(k);
            HashMap<String,Object>[][] knearest = this.metodos.getKNearestNeighbors();
            enviarRespueta(pronostico, entrenamiento, prueba, knearest, out);
            
        }    
    }

    private void enviarRespueta(String[] pronostico, HashMap<String,Object>[] modelo, HashMap<String,Object>[] prueba, HashMap<String,Object>[][] knearest, final PrintWriter out) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("pronostico", pronostico);
        map.put("modelo", modelo);
        map.put("prueba", prueba);
        map.put("porcentaje", this.metodos.porcentajeAcierto(pronostico));
        map.put("knearest", knearest);
        Gson gson = new Gson();
        out.print(gson.toJson(map));
    }
    
    private void obtenerRegistrosPruebaEntrenamiento(HashMap<String,Object>[] prueba, HashMap<String,Object>[] entrenamiento, HashMap<String,Object>[] registros){
        int j = 0;
        int k = 0;
        boolean[] elegidosPrueba = elegirRegistros(registros, prueba);
        
        for (int i = 0; i < elegidosPrueba.length; i++) {
            if(elegidosPrueba[i]){
                prueba[j] = registros[i];
                j++;
            }else{
                entrenamiento[k] = registros[i];
                k++;
            }
        }
    }

    private boolean[] elegirRegistros(HashMap<String, Object>[] registros, HashMap<String, Object>[] prueba) {
        boolean[] elegidosPrueba = new boolean[registros.length];
        for (int i = 0; i < prueba.length; i++) {
            int indice = (int) Math.round(Math.random()*(registros.length-1));
            while(elegidosPrueba[indice])
                indice = (int) Math.round(Math.random()*(registros.length-1));
            elegidosPrueba[indice] = true;
        }
        return elegidosPrueba;
    }
    
    private HashMap<String,Object>[] obtenerDatos(String data){
        String filas[]= data.split("\n");
        String atributos[] = filas[0].split(",");
        HashMap<String,Object>[] datos = new HashMap[filas.length-1];
        for (int i = 0; i < filas.length-1; i++) {
            datos[i] = new HashMap<>();
            String valores[] = filas[i + 1].split(",");
            for (int j = 0; j < valores.length; j++) {
                String valor = valores[j].trim();
                if(j==atributos.length-1){
                    String atributo = atributos[atributos.length-1].trim();
                    datos[i].put("#clase", atributo);
                    datos[i].put(atributo, obtenerDatoTipado(valor));
                }
                else    
                    datos[i].put(atributos[j], obtenerDatoTipado(valor));
            }
            
        }
        return datos;
    }
    
    private Object obtenerDatoTipado(String dato){
        try{
            return Double.parseDouble(dato);
        }catch(NumberFormatException ex){
            return dato;
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
