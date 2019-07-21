
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author camilo
 */
public class clsMetodosKNN {
    
    int numDatosEntrenamiento;
    int numDatosPrueba;
    double[][] matriz;
    int[][] kNearestIndexes;
    HashMap<String,Object>[] registrosPrueba;
    HashMap<String,Object>[] registrosEntramiento;

    public clsMetodosKNN(HashMap<String,Object>[] registrosPrueba, HashMap<String,Object>[] registrosEntrenamiento) {
        this.registrosEntramiento = registrosEntrenamiento;
        this.registrosPrueba = registrosPrueba;
        this.numDatosEntrenamiento = registrosEntrenamiento.length;
        this.numDatosPrueba = registrosPrueba.length;
        matriz = new double[numDatosPrueba][numDatosEntrenamiento];
        calcularDistancias();
    }

    public void calcularDistancias() {
        for (int i = 0; i < numDatosPrueba; i++) {
            for (int j = 0; j < numDatosEntrenamiento; j++) {
                matriz[i][j] = distancia_euclidiana(registrosPrueba[i], registrosEntramiento[j]);
            }
        }
    }

    public double distancia_euclidiana(HashMap<String,Object> registro1, HashMap<String,Object> registro2) {
        double sumatoria = 0;
        for (Map.Entry<String, Object> entry : registro1.entrySet()) {
            String key = entry.getKey();
            if(!key.equals("#clase") && !registro1.get("#clase").equals(key)){
                Object value = entry.getValue();
                Object value2 = registro2.get(key);
                if(value instanceof Double){
                    double num1 = (double) value;
                    double num2 = (double) value2;
                    sumatoria += Math.pow(num1 - num2, 2);
                }else{
                    String cadena1 = (String) value;
                    String cadena2 = (String) value2;
                    if(!cadena1.equalsIgnoreCase(cadena2))
                        sumatoria += 1;
                }
            }
        }
        return Math.sqrt(sumatoria);
    }
    
    public String[] KNN(int k){
        this.kNearestIndexes = new int[this.numDatosPrueba][k];
        String[] clases = new String[numDatosPrueba];
        for(int i = 0;i< numDatosPrueba; i++){
            int[] kmenores = getKMenores(i, k);
            clases[i] = getClase(kmenores);
            this.kNearestIndexes[i] = kmenores;
        }
        return clases;
    }
    
    public String getClase(int kmenores[]){
        HashMap<String,Integer> map = new HashMap<>();
        for (int i = 0; i < kmenores.length; i++) {
            HashMap<String,Object> elem = this.registrosEntramiento[kmenores[i]];
            String atributoClase = (String) elem.get("#clase");
            String clase = (String)elem.get(atributoClase);
            if(map.containsKey(clase))
                map.put(clase, map.get(clase) + 1);
            else
                map.put(clase, 1);
        }
        return masRepetido(map);
    }
    
    public String masRepetido(HashMap<String,Integer> map){
        int may = -1;
        String clase = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(may < value){
                may = value;
                clase = key;
            }
        }
        return clase;
    }
    
    
    public int[] getKMenores(int i, int k){
        int[] kmenores = getVectorInicial(k);
        for (int iteracion = 0; iteracion < k; iteracion++) {
            double menor = Double.MAX_VALUE;
            for (int j = 0; j < matriz[0].length; j++) {
                if(matriz[i][j] < menor && !tomado(j, kmenores)){
                    kmenores[iteracion] = j;
                    menor = matriz[i][j];
                }
            }
        }
        return kmenores;
    }
    
    private boolean tomado(int j, int[] kmenores){
        for (int i = 0; i < kmenores.length; i++) {
            if(j == kmenores[i])
                return true;
            if(kmenores[i]==-1)
                return false;
        }
        return false;
    }
    
 
    public int[] getVectorInicial(int k){
        int[] array = new int[k];
        for (int i = 0; i < k; i++) {
            array[i] = -1;
        }
        return array;
    }
    
    public double porcentajeAcierto(String[] clases){
        double aciertos = 0;
        for (int i = 0; i < clases.length; i++) {
            String atributoClase = (String) registrosPrueba[i].get("#clase");
            String clase = (String)registrosPrueba[i].get(atributoClase);
            if(clase.equals(clases[i]))
                aciertos++;
        }
        return aciertos / this.numDatosPrueba * 100;
    }
    
    public HashMap<String,Object>[][] getKNearestNeighbors(){
        HashMap<String,Object>[][] knearest = new HashMap[this.numDatosPrueba][this.kNearestIndexes[0].length];
        for (int i = 0; i < numDatosPrueba; i++) {
            for (int j = 0; j < knearest[0].length; j++) {
                knearest[i][j] = registrosEntramiento[kNearestIndexes[i][j]];
            }
        }
        
        return knearest;
    }
}
    
    
