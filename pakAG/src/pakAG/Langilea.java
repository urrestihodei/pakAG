package pakAG;

public class Langilea {

	private String ID;
	public String nan;
	public String izena;
	public String abizena;
	public String erabiltzailea;
	public String pasahitza;
	public String telefonoa;
	private String enterga_data;
	private String helbidea;


	/**
	 * langilearen eraikitzailea
	 * @param n = langilearen nan-a
	 * @param i = langilearen izena
	 * @param a = langilearen abizena
	 * @param e = langilearen erabiltzailea
	 * @param p = langilearen pasahitza
	 * @param t = langilearen telefonoa
	 */
	public Langilea(String n, String i, String a, String e, String p, String t){
		this.nan = n;
		this.izena = i;
		this.abizena = a;
		this.erabiltzailea = e;
		this.pasahitza = p;
		this.telefonoa = t;
	}
	
	/**
	 * langilearen eraikitzailea
	 * @param i = langilearen izena
	 * @param a = langilearen abizena
	 * @param e = paketearen entrega data
	 * @param helbidea 
	 */
	public Langilea(String i, String e, String h){
		this.ID = i;
		this.enterga_data = e;
		this.helbidea = h;
	}

	/**
	 * langilearen eraikitzailea nan eta izenarekin
	 * @param n
	 * @param i
	 */
    public Langilea(String n, String i) {
    	this.nan = n;
		this.izena = i;
	}

    /**
     * langilearen erabiltzailea itzultzen du
     * @return
     */
	public String getErabiltzailea() {
        return erabiltzailea;
    }

	/**
	 * Langilearen pasahitza itzultzen du
	 * @return
	 */
    public String getPasahitza() {
        return pasahitza;
    }
    
    /**Langilearen nan-a itzultzen du
     * 
     * @return
     */
    public String getLangileaNAN() {
        return nan;
    }
    
    /**
     * Langilearen abizena itzultzen du
     * @return
     */
    public String getAbizena() {
        return abizena;
    }

    /**
     * Langilearen izena itzultzen du
     * @return
     */
    public String getIzena() {
        return izena;
    }
    
    /**
     * Langilearen telefonoa itzultzen du
     * @return
     */
    public String getTelefonoa() {
        return telefonoa;
    }

    /**
     * Paketearen ID-a itzultzen du
     * @return
     */
	public String getID() {
		return ID;
	}

	/**
	 * Paketea entregatu behar zaion bezeroaren nan-a itzultzen du
	 * @return
	 */
	public String getBezeroaHelbidea() {
		return helbidea;
	}

	/**
	 * Entregatu behar den paketearen entrega-data itzultzen du
	 * @return
	 */
	public String getEntregaData() {
		return enterga_data;
	}

}