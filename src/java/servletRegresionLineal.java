
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;
import java.util.Iterator;

@WebServlet(urlPatterns = {"/servletRegresionLineal"})
public class servletRegresionLineal extends HttpServlet {

    clsMetodosRegresion metodos = new clsMetodosRegresion();
    ArrayList<Double> XY = new ArrayList<>();
    ArrayList<Double> X2 = new ArrayList<>();
    ArrayList<Double> Y2 = new ArrayList<>();
    Double b1 = 0.0;
    Double b0 = 0.0;
    ArrayList<Double> Pronostico = new ArrayList<>();
    ArrayList<Double> ErrAbs = new ArrayList<>();
    ArrayList<Double> SumaErrAbs = new ArrayList<>();
    ArrayList<Double> Mad = new ArrayList<>();
    ArrayList<Double> ErrorNor = new ArrayList<>();
    ArrayList<Double> SumaErrorNor = new ArrayList<>();
    ArrayList<Double> ts = new ArrayList<>();
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            ArrayList<Double> arreglox = new ArrayList<>();
            ArrayList<Double> arregloy = new ArrayList<>();
            String query = request.getQueryString().replaceAll("%22", "\"");
            JsonParser parser = new JsonParser();
            JsonElement gson = parser.parse(query);
            JsonObject obj = gson.getAsJsonObject();
            JsonArray arx = obj.getAsJsonArray("x");
            JsonArray ary = obj.getAsJsonArray("y");
            Iterator<JsonElement> it = arx.iterator();
            //out.print(query);
            while (it.hasNext()) {
                JsonElement element = it.next();
                arreglox.add(element.getAsDouble());
            }

            it = ary.iterator();
            while (it.hasNext()) {
                JsonElement element = it.next();
                arregloy.add(element.getAsDouble());
            }

            XY = metodos.XY(arreglox, arregloy);
            X2 = metodos.X2(arreglox);
            Y2 = metodos.Y2(arregloy);
            b1 = metodos.B1(arreglox, arregloy);
            b0 = metodos.B0(arreglox, arregloy);
            Pronostico = metodos.Pronostico(arreglox, arregloy);
            ErrAbs = metodos.ErorrAbs(arreglox, arregloy);
            SumaErrAbs = metodos.SumaErorrAbs(arreglox, arregloy);
            Mad = metodos.Mad(arreglox, arregloy);
            ErrorNor = metodos.ErorrNor(arreglox, arregloy);
            SumaErrorNor = metodos.SumaErorrNor(arreglox, arregloy);
            ts = metodos.Ts(arreglox, arregloy);

            String json = "{\"xy\":" + XY.toString() + ",";
            json += "\"x2\":" + X2.toString() + ",";
            json += "\"y2\":" + Y2.toString() + ",";
            json += "\"pronostico\":" + Pronostico.toString() + ",";
            json += "\"ErrorAbs\":" + ErrAbs.toString() + ",";
            json += "\"SumaErrorAbs\":" + SumaErrAbs.toString() + ",";
            json += "\"mad\":" + Mad.toString() + ",";
            json += "\"ErrorNor\":" + ErrorNor.toString() + ",";
            json += "\"SumaErrorNor\":" + SumaErrorNor.toString() + ",";
            json += "\"ts\":" + ts.toString() + "}";
//            json += "'b1':" + b1 + ",";
//            json += "'b0':" + b0 + "}";
            out.print(json);
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
