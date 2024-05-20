package pakAG;

public class Langilea {

	public String nan;
	public String izena;
	public String abizena;
	public String erabiltzailea;
	public String pasahitza;
	public String telefonoa;


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

}