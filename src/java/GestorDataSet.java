
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author camilo
 */
public class GestorDataSet {
    private int numDatosEntrenamiento;
    private int numDatosPrueba;
    private HashMap<String,Object>[] dataSet;
    private HashMap<String,Object>[] dataModel;
    private HashMap<String,Object>[] dataTest;

    public GestorDataSet(double porcentajeEntramiento, String data) {
        this.dataSet = obtenerDatos(data);
        this.numDatosEntrenamiento = (int) Math.round(dataSet.length * (porcentajeEntramiento/100));
        this.numDatosPrueba = dataSet.length - numDatosEntrenamiento ;
        this.dataModel = new HashMap[numDatosEntrenamiento];
        this.dataTest = new HashMap[numDatosPrueba];
        establecerRegistrosPruebaEntrenamiento();
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
    
    private void establecerRegistrosPruebaEntrenamiento(){
        int j = 0;
        int k = 0;
        boolean[] elegidosPrueba = elegirRegistros();
        
        for (int i = 0; i < elegidosPrueba.length; i++) {
            if(elegidosPrueba[i]){
                dataTest[j] = dataSet[i];
                j++;
            }else{
                dataModel[k] = dataSet[i];
                k++;
            }
        }
    }
    
    private boolean[] elegirRegistros() {
        boolean[] elegidosPrueba = new boolean[dataSet.length];
        for (int i = 0; i < dataTest.length; i++) {
            int indice = (int) Math.round(Math.random()*(dataSet.length-1));
            while(elegidosPrueba[indice])
                indice = (int) Math.round(Math.random()*(dataSet.length-1));
            elegidosPrueba[indice] = true;
        }
        return elegidosPrueba;
    }

    public HashMap<String, Object>[] getDataModel() {
        return dataModel;
    }

    public HashMap<String, Object>[] getDataSet() {
        return dataSet;
    }

    public HashMap<String, Object>[] getDataTest() {
        return dataTest;
    }

    public int getNumDatosEntrenamiento() {
        return numDatosEntrenamiento;
    }

    public int getNumDatosPrueba() {
        return numDatosPrueba;
    }
}
