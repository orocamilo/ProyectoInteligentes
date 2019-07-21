
public class Iris{
    private double anchoSepalo;
    private double altoSepalo;
    private double anchoPetalo;
    private double altoPetalo;
    private String clase;

    public Iris(double anchoSepalo, double altoSepalo, double anchoPetalo, double altoPetalo, String clase) {
        this.anchoSepalo = anchoSepalo;
        this.altoSepalo = altoSepalo;
        this.anchoPetalo = anchoPetalo;
        this.altoPetalo = altoPetalo;
        this.clase = clase;
    }

    /**
     * @return the anchoSepalo
     */
    public double getAnchoSepalo() {
        return anchoSepalo;
    }

    /**
     * @param anchoSepalo the anchoSepalo to set
     */
    public void setAnchoSepalo(double anchoSepalo) {
        this.anchoSepalo = anchoSepalo;
    }

    /**
     * @return the altoSepalo
     */
    public double getAltoSepalo() {
        return altoSepalo;
    }

    /**
     * @param altoSepalo the altoSepalo to set
     */
    public void setAltoSepalo(double altoSepalo) {
        this.altoSepalo = altoSepalo;
    }

    /**
     * @return the anchoPetalo
     */
    public double getAnchoPetalo() {
        return anchoPetalo;
    }

    /**
     * @param anchoPetalo the anchoPetalo to set
     */
    public void setAnchoPetalo(double anchoPetalo) {
        this.anchoPetalo = anchoPetalo;
    }

    /**
     * @return the altoPetalo
     */
    public double getAltoPetalo() {
        return altoPetalo;
    }

    /**
     * @param altoPetalo the altoPetalo to set
     */
    public void setAltoPetalo(double altoPetalo) {
        this.altoPetalo = altoPetalo;
    }

    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    

   
}
