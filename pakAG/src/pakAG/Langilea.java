package pakAG;

public class Langilea {

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
	public Langilea(String i, String a, String e, String h){
		this.izena = i;
		this.abizena = a;
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
    public String getNan() {
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
     * Langilearen string batera itzultzen du
     */
    @Override
    public String toString() {
        return "<html>" +
               izena + " " + abizena + "<br>" +
               " Nan: " + nan + "<br>" +
               " Telefonoa: " + telefonoa + "<br>" +
               "--------------------<br>" +
               "</html>";
    }
    
    /**
     * Langilearen string batera itzultzen du
     */
    public String toString2() {
        return izena + " " + abizena + "<br>" +
               " Enterga data: " + enterga_data + "<br>" +
               " Helbidea: " + helbidea + "<br>" +
               "--------------------<br>";
    }

}
