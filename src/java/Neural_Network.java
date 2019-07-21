/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;


/**
 * This is the neural network. It deals with gathering input, create file, load file,  
 * creating datasets, learning from data sets and calculating output.
 * 
 * @author Group 1
 */
public class Neural_Network {
    
    public NeuralNetwork nnet;
    MomentumBackpropagation learningRule;
    DataSet dataSet;
    DataSetRow dataRow;
    
    BufferedWriter writer;
    
    //Generation integer. Goes up by one, every time the neural network learns.
    public static int generation = 0;   
    
    //File location
    private final String file = "D:\\Documentos\\NetBeansProjects\\RegresionLineal\\IrisNormalizadaEspacio.csv";
    
    //If you are going to use load from file or not.
    public static boolean fromFileEnabled = false; //Files does not work atm.
    
    //Layers
    private int inputs = 4;
    private int outputs = 3;
    private int hiddens = 6;
    
    //Inputs
    static int input_1;
    static int input_2;
    static double desOutput;
    static double output;
    
    //The calculated output being put in an array.
    private double[] outputArray;
      
   
    
    public Neural_Network(int inputs,int hidden, int outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.hiddens = hidden;
        nnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputs, hiddens, outputs);
        learningRule = (MomentumBackpropagation) nnet.getLearningRule();
        
        learningRule.setLearningRate(0.2); 
        learningRule.setMomentum(0.9);
        learningRule.setMaxError(0.01); 
        learningRule.setMaxIterations(4000);
        System.out.print("Creating DataSet...     ");
        dataSet = DataSet.createFromFile(file, inputs, outputs, "\\s");
        System.out.print(dataSet);               
        System.out.println("Data Createad!");
        
        teachNeuralNetwork();
    }
    
    
    /**
     * If there exist a dataset, it will load or else it will create a file for the datasets.
     * 
     */
    public void clearDataSet() {
        dataSet = new DataSet(inputs, outputs);
    }
    
    
    
    /**
     * Uses the dataset for training and updates the generation of the neural network by one.
     * 
     */
    public void teachNeuralNetwork() {
        System.out.print("Learning data...       ");
        
        nnet.learn(dataSet);     //STOPS WHEN THERE IS A 1.0 IN THE TXT FILE.
        
        System.out.println("Data Learnt!");
        
        generation++; 
        
        calculateOutput(); //-> playPong(output);
    }
    
    /**
     * Uses the input and training sets to calculate what the desired output will be.
     * 
     */
    public void calculateOutput() {      
        System.out.print("Output getting calculated...      ");

        System.out.print(dataSet);
        
        for (int i = 0; i < dataSet.size(); i++) { 
            dataRow = dataSet.getRowAt(i);
            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            
            outputArray = nnet.getOutput();     
            output = outputArray[0];            
            
            System.out.println(i);
//            if (!fromFileEnabled) {
//                clearDataSet();
//            }
        }
        System.out.println("Ouput calculated!");
        
//        if (fromFileEnabled) {
//            clearDataSet();
//            fromFileEnabled = false;
//        }  
    }
    
   
    
    /**
     * Loades training data from a file.
     * 
     * @param file file location
     */
    public void loadDataFromFile(String file) {
        fromFileEnabled = true;
        System.out.print("Loading dataSet from file...       ");
        try {   
            dataSet = TrainingSetImport.importFromFile(file, inputs, outputs, ",");
            System.out.println(dataSet);
        } catch (NumberFormatException | IOException ex) {
            System.out.println(ex);
        }
        System.out.println("Finished loading dataSet from file!");
        teachNeuralNetwork();
    } 

    
    
    
    /**
     * Method to return the output of the neural network
     * 
     * @return Output returns the output of the neural network, as it is in this class.
     */
    public double getNeuralNetwork_Output() {
        return output;
    }   

   
    
    /**
     * Method that returns the neural network generation
     * 
     * @return Generation return the generation at the time of getting it.
     */
    public int getGeneration() {
        return generation;
    }    
}
