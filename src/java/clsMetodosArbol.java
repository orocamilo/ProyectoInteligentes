
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author camilo
 */
public class clsMetodosArbol {
    private Iris[] datos;
    private double[][] rangos;
    private double infoD;
    private HashMap<String,Double> ganancias;
    private HashMap<String,Double> infos;
    private HashMap<String,double[][]> allRangos;
    private HashMap<String,HashMap<String,Object[]>> arbol;
    private String raiz;
    private double suma;


    public clsMetodosArbol() {
        this.ganancias = new HashMap<>();
        this.infos = new HashMap<>();
        this.allRangos = new HashMap();
        this.suma = 0;
        this.infoD = 0;
        this.arbol = new HashMap<>();
      
    }
    
    public void RealizarCalculos(String atributos[], double matrices[][][]){
        
        this.calcularInfoD(matrices[0]);
        for (int i = 0; i < atributos.length; i++) {
            this.calcularPorFila(matrices[i], atributos[i]);
        }
        this.calcularGanancias(atributos);
    }
    
    public void calcularGanancias(String[] atributos){
        for (String atributo : atributos) {
            this.getGanancias().put(atributo, this.getInfoD() - this.getInfos().get(atributo));
        }
    }
    
    public void calcularRangos(double max, double min, int n,String atributo){
        double salto = (max - min)/n;
        salto = (double) Math.round(salto * 100d) / 100d;
        this.setRangos(new double[n][2]);
        double inicio = min;
        for (int i = 0; i < n; i++) {
            this.getRangos()[i][0] = inicio;
            this.getRangos()[i][1] = inicio + salto;
            inicio = this.getRangos()[i][1];
        }
        this.rangos[n-1][1] = max;
        double vector[][] = new double[this.rangos.length][this.rangos[0].length];
        
        for (int i = 0; i < this.rangos.length; i++) {
            for (int j = 0; j < this.rangos[0].length; j++) {
                vector[i][j]=this.rangos[i][j];
            }
        }
        this.allRangos.put(atributo, vector);
    }
    
    public void calcularInfoD(double[][] matriz){
        double array[]=new double[matriz[0].length-1];
        
        for (int j = 0; j < matriz[0].length-1; j++) {
            for (int i = 0; i < matriz.length; i++) {
                array[j] += matriz[i][j];
            }
            setSuma(getSuma() + array[j]);
        }
        this.setInfoD(this.info(array, getSuma(),matriz.length));
    }
    
    public void calcularPorFila(double[][] matriz,String atributo){
        double info = 0;
        for (int i = 0; i < matriz.length; i++) {
            double n = 0;
            for (int j = 0; j < matriz[0].length-1; j++) {
                n += matriz[i][j];
            }
            matriz[i][matriz[0].length-1] = info(matriz[i], n, matriz.length);
            info+= n * matriz[i][matriz[0].length-1];
        }
        
        this.infos.put(atributo, info/this.suma);
    }
    
    public double info(double vector[], double n, int clases){
        double info = 0;
        for (int i = 0;i < vector.length; i++) {
            if(vector[i] > 0)
            info -= vector[i]/n * Math.log(vector[i]/n) / Math.log(clases);
        }
        return info;
    }
    
    
    public void crearArbol(HashMap<String,double[][]> matrices,String clases[]){
        Queue<String[]> cola = new LinkedList();
        raiz = mayorGanancia(cola);
        String padre = raiz;
        String aux[] = {raiz,""};
        cola.add(aux);
        
        while(!cola.isEmpty()){
            String elem[] = cola.poll();
            padre = elem[1];
            if(!elem[0].contains("-")){
                LinkedList<String[]> hijos = new LinkedList<>();
                double ranguitos[][] = this.allRangos.get(elem[0]);
                HashMap<String,Object[]> map = new HashMap<>();
                for (int i = 0; i < ranguitos.length; i++) {
                    String rango[] = {ranguitos[i][0]+"-"+ranguitos[i][1], elem[0]};
                    hijos.add(rango);
                    Object[] data = {i,""};
                    map.put(rango[0], data);
                }
                arbol.put(elem[0], map);
                cola.addAll(hijos);
            }
            else{
                int index = (int) this.arbol.get(padre).get(elem[0])[0];
                double matriz[][] = matrices.get(padre);
                double incertidumbre = matriz[index][matriz[0].length-1];
                Object data[] = arbol.get(padre).get(elem[0]);
                if(incertidumbre == 0){
                    int j = 0;
                    while(j < 0 && matriz[index][j] != 0)
                        j++;
                    data[1] = clases[j];
                }else{
                    String hijo = mayorGanancia(cola);
                    if(!hijo.equals("")){
                        String entry[] = {hijo, elem[0]};
                        cola.add(entry);
                        data[1] = hijo;
                    }
                    else{
                        int j = mayorMatriz(matriz, index);
                        data[1] = clases[j];
                    }
                }
            }
        }
    }
    
    public String mayorGanancia(Queue<String[]> cola){
        double mayor = 0;
        String gm = "";
        for (Entry<String,Double> ganancia : this.ganancias.entrySet()) {
            double valor = ganancia.getValue();
            String key = ganancia.getKey();
            if(valor > mayor && !this.arbol.containsKey(key) && !this.contains(key,cola)){
                mayor = ganancia.getValue();
                gm = ganancia.getKey();
            }
        }
       
        return gm;
    }
    
    public int mayorMatriz(double[][] matriz, int index){
        double mayor = 0;
        int c = 0;
        for(int j = 0;j < matriz[index].length;j++){
            if(matriz[index][j]>mayor){
                mayor=matriz[index][j];
                c = j;
            }
        }
        return c;
    }
    
    public boolean contains(String key,Queue<String[]>cola){
        for (String[] entry : cola) {
            if(entry[0].equals(key))
                return true;
        }
        return false;
    }

    /**
     * @return the datos
     */
    public Iris[] getDatos() {
        return datos;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(Iris[] datos) {
        this.datos = datos;
    }

    /**
     * @return the rangos
     */
    public double[][] getRangos() {
        return rangos;
    }

    /**
     * @param rangos the rangos to set
     */
    public void setRangos(double[][] rangos) {
        this.rangos = rangos;
    }

    /**
     * @return the infoD
     */
    public double getInfoD() {
        return infoD;
    }

    /**
     * @param infoD the infoD to set
     */
    public void setInfoD(double infoD) {
        this.infoD = infoD;
    }

    /**
     * @return the ganancias
     */
    public HashMap<String,Double> getGanancias() {
        return ganancias;
    }

    /**
     * @param ganancias the ganancias to set
     */
    public void setGanancias(HashMap<String,Double> ganancias) {
        this.ganancias = ganancias;
    }

    /**
     * @return the infos
     */
    public HashMap<String,Double> getInfos() {
        return infos;
    }

    /**
     * @param infos the infos to set
     */
    public void setInfos(HashMap<String,Double> infos) {
        this.infos = infos;
    }

    /**
     * @return the suma
     */
    public double getSuma() {
        return suma;
    }

    /**
     * @param suma the suma to set
     */
    public void setSuma(double suma) {
        this.suma = suma;
    }

    public HashMap<String, double[][]> getAllRangos() {
        return allRangos;
    }

    public void setAllRangos(HashMap<String, double[][]> allRangos) {
        this.allRangos = allRangos;
    }

    public HashMap<String, HashMap<String, Object[]>> getArbol() {
        return arbol;
    }

   

    public String getRaiz() {
        return raiz;
    }
    
    
   
}
