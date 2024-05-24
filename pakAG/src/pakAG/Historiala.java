package pakAG;

public class Historiala {
    private String entrega_data;
    private String bezeroaIzena;
    private String bezeroaHelbidea;
    private String oharra;
    private String langileaIzena;

	/**
	 * Entergatu diren paketeen historialaren eraikitzailea
	 * @param entrega_data
	 * @param bezeroaIzena
	 * @param bezeroaHelbidea
	 * @param oharra
	 * @param langilea 
	 */
    public Historiala(String entrega_data, String bezeroaIzena, String bezeroaHelbidea, String oharra, String langilea) {
        this.entrega_data = entrega_data;
        this.bezeroaIzena = bezeroaIzena;
        this.bezeroaHelbidea = bezeroaHelbidea;
        this.oharra = oharra;
        this.langileaIzena = langilea;
    }


    /**
     * Paketea entregatu den helbidea itzultzen du.
     * @return
     */
	public Object getBezeroaHelbidea() {
		return bezeroaHelbidea;
	}

	/**
	 * Paketea banatzean banatzaileak jarritako oharra itzultzen du
	 * @return
	 */
	public Object getOharra() {
		return oharra;
	}

	/**
	 * Paketea entregatu den data itzultzen du
	 * @return
	 */
	public Object getEntregaData() {
		return entrega_data;
	}

	/**
	 * Paketea entregatu duen langilearen izena itzultzen du
	 * @return
	 */
	public String getLangileaIzena() {
		return langileaIzena;
	}

	/**
	 * Paketea entregatu zaion bezeroaren izena itzultzen du
	 * @return
	 */
	public String getBezeroaIzena() {
		return bezeroaIzena;
	}
}