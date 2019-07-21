import java.io.Serializable;
import java.util.ArrayList;

public class Red implements Serializable {

    private static final long serialVersionUID = 3286199902474469057L;
    private double RAZON_APRENDIZAJE;
    private double MOMENTO;

    private ArrayList<Neurona[]> network;

    public Red(int inputs, int[] capas, int outputs, double razon_Aprendizaje, double momento) throws RuntimeException {
        if (inputs < 1 || outputs < 1 || capas.length < 1 || razon_Aprendizaje <= 0 || momento <= 0) {
            throw new RuntimeException();
        }
        this.RAZON_APRENDIZAJE = razon_Aprendizaje;
        this.MOMENTO = momento;
        this.network = new ArrayList<Neurona[]>(inputs + capas.length + outputs);

        Neurona[] capa_temporal = new Neurona[inputs];
        for (int i = 0; i < inputs; i++) {
            capa_temporal[i] = new Neurona(false);
        }
        network.add(capa_temporal);

        Neurona[] array;
        for (int i = 0; i < capas.length; i++) {
            array = new Neurona[capas[i]];
            for (int j = 0; j < array.length; j++) {
                array[j] = new Neurona(true);
            }
            network.add(array);
        }

        capa_temporal = new Neurona[outputs];
        for (int i = 0; i < outputs; i++) {
            capa_temporal[i] = new Neurona(true);
        }
        network.add(capa_temporal);

        for (int i = 1; i < network.size(); i++) {
            for (int j = 0; j < network.get(i).length; j++) {
                for (int k = 0; k < network.get(i - 1).length; k++) {

                    Sinapsis s = new Sinapsis(network.get(i - 1)[k], network.get(i)[j], Math.random());
                    network.get(i)[j].entradas.add(s);
                    network.get(i - 1)[k].salidas.add(s);
                }
            }
        }
    }

    public double[] epoca(ArrayList<Double> inputs) {
        double[] outs = new double[network.get(network.size() - 1).length];
        if (inputs.size() != network.get(0).length) {
            return null;
        }

        for (int i = 0; i < inputs.size(); i++) {
            network.get(0)[i].setResultado(inputs.get(i));
        }

        for (int i = 1; i < network.size(); i++) {
            for (int j = 0; j < network.get(i).length; j++) {
                network.get(i)[j].salidaNeurona();
            }
        }

        for (int i = 0; i < network.get(network.size() - 1).length; i++) {
            outs[i] = network.get(network.size() - 1)[i].getResultado();
        }
        return outs;
    }

    public void calibrar(double[] valoresObjetivo) {
        if (valoresObjetivo.length != network.get(network.size() - 1).length) {
            return;
        }

        for (int i = 0; i < network.get(network.size() - 1).length; i++) {
            network.get(network.size() - 1)[i].calculaGradientesNeuronaSalida(valoresObjetivo[i]);
        }

        for (int i = network.size() - 2; i > 0; i--) {
            for (int j = 0; j < network.get(i).length; j++) {
                network.get(i)[j].calculaGradientesNeuronaOculta();
            }
        }

        for (int i = network.size() - 1; i > 0; i--) {
            for (int j = 0; j < network.get(i).length; j++) {
                network.get(i)[j].actualizarPesos(RAZON_APRENDIZAJE, MOMENTO);
            }
        }
    }

    public double getMOMENTO() {
        return MOMENTO;
    }

    public double getRAZON_APRENDIZAJE() {
        return RAZON_APRENDIZAJE;
    }

}
