package pakAG;

public class Paketea {

	private String entrega_data;
	private String langilea_izena;
	private String helbidea;
	private String bezeroa_nan;
	private String langilea_nan;
	private String bezeroa_izena;
	private int id;


	/**
	 * Erabiltzailearen eraikitzailea.
	 * @param d = paketearen entrega data
	 * @param i = entrega egin behar duen langilearen izena
	 * @param h = entrega egin behar duen helbidea
	 * @param b = paketea jaso behar duen bezeroaren izena
	 * @param id 
	 */
	public Paketea(String d, String i, String h, String b, int id){
		this.entrega_data = d;
		this.langilea_izena = i;
		this.helbidea = h;
		this.bezeroa_izena = b;
		this.id = id;
	}
	
	
	/**
	 * Paketearen eraikitzailea.
	 * @param d = entrega_data
	 * @param bn = bezeroaren nan-a
	 * @param ln = langilearen nan-a
	 */
	public Paketea(String d, String bn, String ln, int id){
		this.entrega_data = d;
		this.bezeroa_nan = bn;
		this.langilea_nan = ln;
		this.id = id;
	}

	/**
	 * Paketearen ID-a itzultzen du.
	 * @return id
	 */
	public int getID() {
		return id;
	}
    
    /**
     * paketearen entrega data itzultzen du.
     * @return entrega_data
     */
	public String getEntrega_data() {
		return entrega_data;
	}

	/**
	 * Paketea entregatu behar duen langilearen izena itzultzen du.
	 * @return izena
	 */
	public String getLangileaIzena() {
		return langilea_izena;
	}
	
	/**
	 * Paketea entregatu behar zaion bezeroaren izena itzultzen du.
	 * @return izena
	 */
	public String getBezeroIzena() {
		return bezeroa_izena;
	}
	
	/**
	 * Paketea entregatu behar den helbidea itzultzen du.
	 * @return helbidea
	 */
	public String getHelbidea() {
		return helbidea;
	}


	/**
	 * Paketea entregatu behar duen langilearen nan-a itzultzen du.
	 * @return
	 */
	public String getLangileaNAN() {
		return langilea_nan;
	}

	/**
	 * Paketea entregatu behar zaion bezeroaren nan-a itzultzen du.
	 * @return izena
	 */
	public String getBezeroaNAN() {
		return bezeroa_nan;
	}

}