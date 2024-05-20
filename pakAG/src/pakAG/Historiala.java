package pakAG;

public class Historiala {
    private String entrega_data;
    private String bezeroaIzena;
    private String bezeroaHelbidea;
    private String oharra;

	/**
	 * Entergatu diren paketeen historialaren eraikitzailea
	 * @param entrega_data
	 * @param bezeroaIzena
	 * @param bezeroaHelbidea
	 * @param oharra
	 */
    public Historiala(String entrega_data, String bezeroaIzena, String bezeroaHelbidea,String oharra) {
        this.entrega_data = entrega_data;
        this.bezeroaIzena = bezeroaIzena;
        this.bezeroaHelbidea = bezeroaHelbidea;
        this.oharra = oharra;
    }



    /**
     * Entregatu diren paketeak string batera itzultzen du
     */
    @Override
    public String toString() {
        return " Bezeroa: " + bezeroaIzena + "<br>" +
               " Helbidea: " + bezeroaHelbidea + "<br>" +
               " Data: " + entrega_data + "<br>" +
               " Oharra: " + oharra + "<br>" +
               "--------------------<br>";
    }
}