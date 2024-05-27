package pakAG;

public class Paketea {

	public String entrega_data;
	public String izena;
	public String helbidea;
	public String bezeroa_nan;
	public String langilea_nan;
	public int id;


	/**
	 * Erabiltzailearen eraikitzailea.
	 * @param d = paketearen entrega data
	 * @param i = entrega egin behar duen langilearen izena
	 * @param h = entega egin behar duen helbidea
	 * @param id 
	 */
	public Paketea(String d, String i, String h, int id){
		this.entrega_data = d;
		this.izena = i;
		this.helbidea = h;
		this.id = id;
	}
	
	
	/**
	 * Paketearen eraikitzailea.
	 * @param d = entrega_data
	 * @param bn = bezeroaren nan-a
	 * @param ln = langilearen nan-a
	 */
	public Paketea(String d, String bn, String ln){
		this.entrega_data = d;
		this.bezeroa_nan = bn;
		this.langilea_nan = ln;
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
	 * Pkatea entregatu behar duen langilearen nan-a itzultzen du.
	 * @return izena
	 */
	public String getLangileaIzena() {
		return izena;
	}
	
	/**
	 * Paketea entregatu behar den helbidea itzultzen du.
	 * @return helbidea
	 */
	public String getHelbidea() {
		return helbidea;
	}

}
