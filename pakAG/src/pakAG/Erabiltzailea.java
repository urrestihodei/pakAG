package pakAG;

public class Erabiltzailea {

	private String erabiltzaileIzena;
    private String pasahitza;

    public Erabiltzailea(String erabiltzaileIzena, String pasahitza) {
        this.erabiltzaileIzena = erabiltzaileIzena;
        this.pasahitza = pasahitza;
    }

    public String getErabiltzaileIzena() {
        return erabiltzaileIzena;
    }

    public String getPasahitza() {
        return pasahitza;
    }
}
