/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.neuroph.core.NeuralNetwork;

/**
 *
 * @author camilo
 */
@WebServlet(urlPatterns = {"/servletRedNeural"})
public class servletRedNeural extends HttpServlet {

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
           HashMap<String,String[]> map = (HashMap) request.getParameterMap();
            
            NeuralNetwork redNeuronal;
            switch(map.get("servicio")[0]){
                case "crear":
                    int inputs = Integer.parseInt(map.get("entrada")[0]);
                    int hiddens = Integer.parseInt(map.get("oculta")[0]);
                    int outputs = Integer.parseInt(map.get("salida")[0]);
                    Neural_Network net =  new Neural_Network(inputs, hiddens, outputs);
                    redNeuronal = net.nnet;
                    guardar(redNeuronal);
                    out.print(net.dataSet);
                    break;
      
                case "probar":
                    redNeuronal = NeuralNetwork.createFromFile("red.nnet");
                    double dato1 = Double.parseDouble(map.get("dato1")[0]);
                    double dato2 = Double.parseDouble(map.get("dato2")[0]);
                    double dato3 = Double.parseDouble(map.get("dato3")[0]);
                    double dato4 = Double.parseDouble(map.get("dato4")[0]);
                    dato1 = (dato1 - 4.3)*(-1)/(7.9 - 4.3) + 1;
                    dato2 = (dato2 - 2.0)*(-1)/(4.4 - 2.0) + 1;
                    dato3 = (dato3 - 1.0)*(-1)/(6.9 - 1.0) + 1;
                    dato4 = (dato4 - 0.1)*(-1)/(2.5 - 0.1) + 1;

                    redNeuronal.setInput(dato1,dato2,dato3,dato4);
                    redNeuronal.calculate();
                    
                    double[] resul = redNeuronal.getOutput();
                    //System.out.println(redNeuronal.getOutput());
                    HashMap json = new HashMap();
                    json.put("resul", resul);
                    
                    double[] x = new double[resul.length];
                    for (int i = 0; i < resul.length; i++) {
                        x[i] = Math.round(resul[i]);
                    }
                    int i;
                    String pron ="";
                    for (i = 0; i < x.length; i++) {
                        if(x[i] == 1)
                            break;
                    }
                    switch(i){
                        case 0:
                            pron = "Iris-virginica";
                            break;
                        case 1:
                            pron = "Iris-versicolor";
                            break;
                        case 2:
                            pron = "Iris-setosa";
                            break;
                    }
                    
                    json.put("pronostico", pron);
                    String stringjson = gson.toJson(json);
                    out.print(stringjson);
                    break;
            }
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

    public void guardar(Object o){
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("red.nnet");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
    }
}
