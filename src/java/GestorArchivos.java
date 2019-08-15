
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author camilo
 */
public class GestorArchivos {
    public final String DIRECTORIO = "D:\\Documentos\\NetBeansProjects\\RegresionLineal\\files";
    
    public String obtenerContenido(String fileName) throws FileNotFoundException{
        String ruta = DIRECTORIO + "\\" + fileName;
        File file = new File(ruta);
        Scanner sc = new Scanner(file);
        String contenido = "";
        while(sc.hasNextLine()){
            contenido += sc.nextLine()+ "\n";
        }
        return contenido;
    }
    
    public List<String> getFileNames(){
        List<String> filenames = new LinkedList<>();

        File[] files = new File(DIRECTORIO).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                filenames.add(file.getName());
            }
        }
        return filenames;
    }
}
