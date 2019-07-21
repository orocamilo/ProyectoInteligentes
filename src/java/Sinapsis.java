import java.io.Serializable;
public class Sinapsis implements Serializable{
	private static final long serialVersionUID = 7597241645279827278L;
	public Neurona inicio, fin;        //Neuronas de inicio y fin
	public double peso;                //Peso asociado a la conexion
	public double deltaWeight = 0.0;   //Variacion del peso con respecto al de la epoca anterior

	public Sinapsis(Neurona inicio, Neurona fin, double peso) {
		this.inicio = inicio;
		this.fin = fin;
		this.peso = peso;
	}
}
