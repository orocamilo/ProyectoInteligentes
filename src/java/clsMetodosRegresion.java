
import java.util.ArrayList;

public class clsMetodosRegresion {

    
    
    
    
    public ArrayList XY(ArrayList<Double> x, ArrayList<Double> y) {
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            resultado.add(x.get(i) * y.get(i));
        }

        return resultado;
    }

    public ArrayList X2(ArrayList<Double> x) {
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            resultado.add(Math.pow(x.get(i), 2));
        }
        return resultado;
    }

    public ArrayList Y2(ArrayList<Double> y) {
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < y.size(); i++) {
            resultado.add(Math.pow(y.get(i), 2));
        }
        return resultado;
    }

    public Double B1(ArrayList<Double> x, ArrayList<Double> y) {
        Double resultado = 0.0;
        Double sumaX = 0.0;
        Double sumaY = 0.0;
        Double sumaXY = 0.0;
        Double sumaX2 = 0.0;
        ArrayList<Double> arregloXY = new ArrayList<>();
        ArrayList<Double> arregloSumaX2 = new ArrayList<>();
        int n = x.size();

        for (int i = 0; i < x.size(); i++) {
            sumaX += x.get(i);
            sumaY += y.get(i);
        }//aqui promediamos la suma de X  Y
        sumaX = sumaX / n;
        sumaY = sumaY / n;

        arregloXY = XY(x, y);

        for (int i = 0; i < arregloXY.size(); i++) {
            sumaXY += arregloXY.get(i);
        }
        arregloSumaX2 = X2(x);
        for (int i = 0; i < arregloSumaX2.size(); i++) {
            sumaX2 += arregloSumaX2.get(i);
        }

        resultado = (sumaXY - n * sumaX * sumaY) / (sumaX2 - n * (Math.pow(sumaX, 2)));

        return resultado;
    }

    public Double B0(ArrayList<Double> x, ArrayList<Double> y) {
        Double promX = 0.0;
        Double promY = 0.0;
        Double resultado;

        for (int i = 0; i < x.size(); i++) {
            promX += x.get(i);
            promY += y.get(i);
        }
        promX = promX / x.size();
        promY = promY / y.size();

        resultado = promY - B1(x, y) * promX;

        return resultado;
    }

    public ArrayList Pronostico(ArrayList<Double> x, ArrayList<Double> y) {
        Double b0 = B0(x, y);
        Double b1 = B1(x, y);
        ArrayList<Double> resultado = new ArrayList<>();
        for (Double x1 : x) {
            double resul = b0 + b1 * x1;
            resultado.add((double) Math.round(resul * 100d) / 100d);
        }

        return resultado;
    }

    public ArrayList ErorrAbs(ArrayList<Double> x, ArrayList<Double> y) {
        ArrayList<Double> ResultadoPronostico = Pronostico(x, y);
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            double resul = Math.abs(y.get(i) - ResultadoPronostico.get(i));
            resultado.add((double) Math.round(resul * 100d) / 100d);
        }
        return resultado;
    }

    public ArrayList SumaErorrAbs(ArrayList<Double> x, ArrayList<Double> y) {
        ArrayList<Double> ResultadoErrorAbs = ErorrAbs(x, y);
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if (i == 0) {
                double resul = ResultadoErrorAbs.get(i);
                resultado.add((double) Math.round(resul * 100d) / 100d);
            } else {
                double resul = resultado.get(i - 1) + ResultadoErrorAbs.get(i);
                resultado.add((double) Math.round(resul * 100d) / 100d);
            }
        }
        return resultado;
    }

    public ArrayList Mad(ArrayList<Double> x, ArrayList<Double> y) {
        ArrayList<Double> ResultadoSumaErrorAbs = SumaErorrAbs(x, y);
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            double resul = ResultadoSumaErrorAbs.get(i) / x.get(i);
            resultado.add((double) Math.round(resul * 100d) / 100d);
        }
        return resultado;
    }

    public ArrayList ErorrNor(ArrayList<Double> x, ArrayList<Double> y) {
        ArrayList<Double> ResultadoPronostico = Pronostico(x, y);
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            double resul = y.get(i) - ResultadoPronostico.get(i);
            resultado.add((double) Math.round(resul * 100d) / 100d);
        }
        return resultado;
    }

    public ArrayList SumaErorrNor(ArrayList<Double> x, ArrayList<Double> y) {
        ArrayList<Double> ResultadoErrorNor = ErorrNor(x, y);
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if (i == 0) {
                double resul = ResultadoErrorNor.get(i);
                resultado.add((double) Math.round(resul * 100d) / 100d);
            } else {
                double resul = resultado.get(i - 1) + ResultadoErrorNor.get(i);
                resultado.add((double) Math.round(resul * 100d) / 100d);
            }
        }
        return resultado;
    }

    public ArrayList Ts(ArrayList<Double> x, ArrayList<Double> y) {
        ArrayList<Double> resultadoMad = Mad(x, y);
        ArrayList<Double> resultadoSumaErrorNor = SumaErorrNor(x, y);
        ArrayList<Double> resultado = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            double resul = resultadoSumaErrorNor.get(i) / resultadoMad.get(i);
            resultado.add((double) Math.round(resul * 100d) / 100d);
        }
        return resultado;
    }
    
    

//    public double[][] MediaMatriz(Iris[] registros, Iris valorNuevo) {
//        double[][] matrizAux = new double[contador+1][contador+1];
//        double[] tupla = new double[registros.length];
//        
//        System.arraycopy(matriz, 0, matrizAux, 0, matriz.length);
//        
//        for (int i = 0; i < tupla.length; i++) {
//            tupla[i] = distancia_euclidiana(registros[i], valorNuevo);
//            matrizAux[matrizAux[0].length-1][i]= tupla[i];
//        }        
//        
//        System.arraycopy(matrizAux, 0, matriz, 0, matrizAux.length);
//        contador+=1;
//        return matrizAux;       
//        
//    }

}
