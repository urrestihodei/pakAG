package pakAG;

public class Paketea {

	public String entrega_data;
	public String izena;
	public String helbidea;
	public String bezeroa_nan;
	public String langilea_nan;
	public int id;


	/**
	 * erabiltzailearen eraikitzailea
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
	 * paketearen eraikitzailea
	 * @param d = entrega_data
	 * @param bn = bezeroaren nan-a
	 * @param ln = langilearen nan-a
	 */
	public Paketea(String d, String bn, String ln){
		this.entrega_data = d;
		this.bezeroa_nan = bn;
		this.langilea_nan = ln;
	}

	public int getID() {
		return id;
	}
    
    /**
     * Paketearen informazioa string batera itzultzen du
     */
    @Override
    public String toString() {
        return "<html>" +
                izena + "<br>" +
                " Helbidea: " + helbidea + "<br>" +
                " Enterga data: " + entrega_data + "<br>" +
                "--------------------<br>" +
                "</html>";
    }


    /**
     * paketearen entrega data itzultzen du.
     * @return
     */
	public String getEntrega_data() {
		return entrega_data;
	}

}
