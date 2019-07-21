import java.io.Serializable;


import java.util.ArrayList;


public class Neurona implements Serializable{
	private static final long serialVersionUID = 8729854615844306332L;

	public ArrayList<Sinapsis> entradas, salidas;
	private double resultado;
	private double gradiente;
	private double p_umbral;
	private double delta_p_umbral = 0.0;
	
	public Neurona(boolean conUmbral) {
		if(conUmbral) {
			p_umbral=Math.random();
		}
		this.entradas = new ArrayList<Sinapsis>();
		this.salidas = new ArrayList<Sinapsis>();
	}
	
	public double salidaNeurona() {
		double suma = p_umbral; //Resultado neurona umbral = 1 * p_umbral
		for(int i=0; i<entradas.size();i++) {
			suma += entradas.get(i).inicio.resultado * entradas.get(i).peso;
		}
	
		resultado = Math.tanh(suma); 
		return resultado;
	}
	public double salidaNeuronaDerivada() {
		return 1-(resultado*resultado); 
	}
	
	public void calculaGradientesNeuronaOculta(){
	
		double suma = 0.0;
		for(int i=0; i<salidas.size(); i++) {
			suma+=salidas.get(i).peso*salidas.get(i).fin.gradiente;
		}
		gradiente = suma*salidaNeuronaDerivada();
	}
	
	
	public void calculaGradientesNeuronaSalida(double valorEsperado) {
		gradiente = (valorEsperado-resultado) * salidaNeuronaDerivada();
	}
	
	
	public void actualizarPesos(double razonAprendizaje, double momento) {
		for(int i=0; i<entradas.size(); i++) {
			double deltaPesoViejo = entradas.get(i).deltaWeight;
			double deltaPesoNuevo = razonAprendizaje * entradas.get(i).inicio.resultado * gradiente + momento * deltaPesoViejo;
			entradas.get(i).deltaWeight = deltaPesoNuevo;
			entradas.get(i).peso += deltaPesoNuevo;
		}
	
		double deltaPesoViejo = delta_p_umbral;
		double deltaPesoNuevo = razonAprendizaje * resultado * gradiente + momento * deltaPesoViejo;
		delta_p_umbral = deltaPesoNuevo;
		p_umbral += deltaPesoNuevo;
	}
	
	public double getResultado() {
		return resultado;
	}
	
	public void setResultado(double resultado) {
		this.resultado = resultado;
	}
	
	public double getGradiente() {
		return gradiente;
	}
	
	public ArrayList<Sinapsis> getEntradas(){
		return entradas;
	}
	
	public ArrayList<Sinapsis> getSalidas(){
		return salidas;
	}
}
