package pakAG;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;


public class dbKontsultak {
    private static final int max_akatsak = 3;
    private static final long blokeo_denbora = 15 * 1000;
    private static HashMap<String, Integer> akats_kop = new HashMap<>();
    private static HashMap<String, Long> blokeatutakoErabiltzaileak = new HashMap<>();
    
    
	/**
	 * Erabiltzaile bat sortu nahi dugunean, datu basean erabiltzaile hori badagoen begiratzen du
	 * @param erabiltzailea
	 * @return
	 */
    public static boolean erablitzaileaBadago(String erabiltzailea) {
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT erabiltzailea FROM langilea WHERE erabiltzailea = '" + erabiltzailea + "'");
            if (rs.next()) {
                konexioa.konexioaItxi();
                return true;
            } else {
                konexioa.konexioaItxi();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Langilea datu basean sortzeko funtzioa
     * @param langilea
     */
    public static void langileaSartu(Langilea langilea) {
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            String sql_create = "INSERT INTO langilea (`langilea_nan`, `langilea_izena`, `langilea_abizena`, `langilea_telefonoa`, `erabiltzailea`, `pasahitza`, `mota`) " +
                    "VALUES ('" + langilea.nan + "', '" + langilea.izena + "', '" + langilea.abizena + "', '" + langilea.telefonoa + "', '" + langilea.erabiltzailea + "', '" + langilea.pasahitza + "', 'langilea')";
            stmt.executeUpdate(sql_create);
            JOptionPane.showMessageDialog(null, "datuak ondo sartu dira.");
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saioa hasteko funtzioa. 3 aldiz pasahitza gaziki sartzean erabiltzailea blokeatzen da.
     * @param erabiltzailea
     * @param pasahitza
     * @return
     */
    public static boolean saioaHasi(String erabiltzailea, String pasahitza) {
        if (blokeatutakoErabiltzaileak.containsKey(erabiltzailea)) {
            long blokeo_denbora = blokeatutakoErabiltzaileak.get(erabiltzailea);
            if (System.currentTimeMillis() - blokeo_denbora < blokeo_denbora) {
                JOptionPane.showMessageDialog(null, "Erabiltzailea blokeatuta dago. Saiatu berriro beranduago.");
                return false;
            } else {
                blokeatutakoErabiltzaileak.remove(erabiltzailea);
            }
        }

        if (!akats_kop.containsKey(erabiltzailea)) {
            akats_kop.put(erabiltzailea, 0);
        }

        int saiakerak = akats_kop.get(erabiltzailea);
        if (saiakerak >= max_akatsak) {
            JOptionPane.showMessageDialog(null, "Erabiltzailea edo pasahitza gaizki sartu duzu 3 aldiz. Erabiltzailea blokeatu egingo da 15 segundoz.");
            blokeatutakoErabiltzaileak.put(erabiltzailea, System.currentTimeMillis());
            return false;
        }

        if (balidatu(erabiltzailea, pasahitza)) {
            JOptionPane.showMessageDialog(null, "Saioa hasi duzu. Ongi etorri: " + erabiltzailea);
            return true;
        } else {
            akats_kop.put(erabiltzailea, saiakerak + 1);
            JOptionPane.showMessageDialog(null, "Erabiltzailea edo pasahitza okerra da. Saiatu berriro.");
            return false;
        }
    }

    /**
     * Pasahitza eta erabiltzailea ondo dauden begiratzen du
     * @param erabiltzailea
     * @param pasahitza
     * @return
     */
    private static boolean balidatu(String erabiltzailea, String pasahitza) {
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM langilea WHERE erabiltzailea = '" + erabiltzailea + "' AND pasahitza = '" + pasahitza + "' AND mota = 'kudeatzailea'");
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Datu basetik entregatu diren pakete guztiak ateratzen ditu.
     * @return
     */
    public static List<Historiala> paketeHistoriala() {


    	    List<Historiala> historialaList = new ArrayList<>();
    	    try {
    	        Statement stmt = konexioa.konexioaHasi().createStatement();
    	        ResultSet rs = stmt.executeQuery("SELECT `historiala`.`entrega_data`, `historiala`.`oharra`, `bezeroa`.`bezeroa_izena`, `bezeroa`.`bezeroa_abizena`, `bezeroa`.`bezeroa_helbidea`, `langilea`.`langilea_izena`, `langilea`.`langilea_abizena` "
    	                + "FROM `historiala` "
    	                + "INNER JOIN `bezeroa` ON `historiala`.`bezeroa_nan` = `bezeroa`.`bezeroa_nan` "
    	                + "INNER JOIN `langilea` ON `historiala`.`langilea_nan` = `langilea`.`langilea_nan` ");
        	while (rs.next()) {
        	    String data = rs.getString("entrega_data");
        	    String helbidea = rs.getString("bezeroa_helbidea");
        	    String izena = rs.getString("bezeroa_izena") + " " + rs.getString("bezeroa_abizena");
        	    String langilea = rs.getString("langilea_izena") + " " + rs.getString("langilea_abizena");
        	    String oharra;
        	    
        	    if (rs.getString("oharra") == null) {
        	    	oharra = "Ez dago oharrik";
        	    }
        	    else {
        	    	oharra = rs.getString("oharra");
        	    }
        	    
        	    Historiala h = new Historiala(data, izena, helbidea, oharra, langilea);
        	    historialaList.add(h);
        	}
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historialaList;
    }
    
    /**
     * Datu basetik entregatu diren pakete guztiak erabiltzaileak nahi bezala ordenatuta ateratzen ditu.
     * @return
     */
    public static List<Historiala> historialaOrdenatuta(String zutabea) {


    	    List<Historiala> historialaList = new ArrayList<>();
    	    try {
    	        Statement stmt = konexioa.konexioaHasi().createStatement();
    	        ResultSet rs = stmt.executeQuery("SELECT `historiala`.`entrega_data`, `historiala`.`oharra`, `bezeroa`.`bezeroa_izena`, `bezeroa`.`bezeroa_abizena`, `bezeroa`.`bezeroa_helbidea`, `langilea`.`langilea_izena`, `langilea`.`langilea_abizena` "
    	                + "FROM `historiala` "
    	                + "INNER JOIN `bezeroa` ON `historiala`.`bezeroa_nan` = `bezeroa`.`bezeroa_nan` "
    	                + "INNER JOIN `langilea` ON `historiala`.`langilea_nan` = `langilea`.`langilea_nan` "
    	                + "ORDER BY `historiala`.`" + zutabea + "` ASC");
        	while (rs.next()) {
        	    String data = rs.getString("entrega_data");
        	    String helbidea = rs.getString("bezeroa_helbidea");
        	    String izena = rs.getString("bezeroa_izena") + " " + rs.getString("bezeroa_abizena");
        	    String langilea = rs.getString("langilea_izena") + " " + rs.getString("langilea_abizena");
        	    String oharra;
        	    
        	    if (rs.getString("oharra") == null) {
        	    	oharra = "Ez dago oharrik";
        	    }
        	    else {
        	    	oharra = rs.getString("oharra");
        	    }
        	    
        	    Historiala h = new Historiala(data, izena, helbidea, oharra, langilea);
        	    historialaList.add(h);
        	}
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historialaList;
    }
 
    
    /**
     * Datu basetik entregatu diren paketeak erabiltzaileak erabakitako moduan filtratuta itzultzen ditu.
     * @return
     */
    public static List<Historiala> historialaFiltratu(String zutabea, String balorea) {


    	    List<Historiala> historialaList = new ArrayList<>();
    	    try {
    	        Statement stmt = konexioa.konexioaHasi().createStatement();
    	        ResultSet rs = stmt.executeQuery("SELECT `historiala`.`entrega_data`, `historiala`.`oharra`, `bezeroa`.`bezeroa_izena`, `bezeroa`.`bezeroa_abizena`, `bezeroa`.`bezeroa_helbidea`, `langilea`.`langilea_izena`, `langilea`.`langilea_abizena` "
    	                + "FROM `historiala` "
    	                + "INNER JOIN `bezeroa` ON `historiala`.`bezeroa_nan` = `bezeroa`.`bezeroa_nan` "
    	                + "INNER JOIN `langilea` ON `historiala`.`langilea_nan` = `langilea`.`langilea_nan` "
    	                + "WHERE `historiala`.`" + zutabea + "` = '" + balorea + "'");
        	while (rs.next()) {
        	    String data = rs.getString("entrega_data");
        	    String helbidea = rs.getString("bezeroa_helbidea");
        	    String izena = rs.getString("bezeroa_izena") + " " + rs.getString("bezeroa_abizena");
        	    String langilea = rs.getString("langilea_izena") + " " + rs.getString("langilea_abizena");
        	    String oharra;
        	    
        	    if (rs.getString("oharra") == null) {
        	    	oharra = "Ez dago oharrik";
        	    }
        	    else {
        	    	oharra = rs.getString("oharra");
        	    }
        	    
        	    Historiala h = new Historiala(data, izena, helbidea, oharra, langilea);
        	    historialaList.add(h);
        	}
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historialaList;
    }
    /**
     * Erabiltzaile guztiekin lista bat itzultzen du.
     * @return
     */
    public static List<Langilea> getErabiltzaileak() {
        List<Langilea> erabiltzaileaList = new ArrayList<>();
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `langilea` WHERE `mota` = 'langilea'");
            while (rs.next()) {
                String nan = rs.getString("langilea_nan");
                String izena = rs.getString("langilea_izena");
                String abizena = rs.getString("langilea_abizena");
                String erabiltzailea = rs.getString("erabiltzailea");
                String pasahitza = rs.getString("pasahitza");
                String telefonoa = rs.getString("langilea_telefonoa");
                Langilea erab = new Langilea(nan, izena, abizena, erabiltzailea, pasahitza, telefonoa);
                erabiltzaileaList.add(erab);
            }
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return erabiltzaileaList;
    }
    
    
    /**
     * Erabiltzaile guztien nan eta izen abizenekin osatutako lista bat itzultzen du.
     * @return
     */
    public static List<String> getErabiltzaileak2() {
        List<String> erabiltzaileaList = new ArrayList<>();
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `langilea` WHERE `mota` = 'langilea'");
            while (rs.next()) {
                String langilea = rs.getString("langilea_izena") + " " + rs.getString("langilea_abizena") + " ," + rs.getString("langilea_nan");
                erabiltzaileaList.add(langilea);
            }
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return erabiltzaileaList;
    }
    

    
    /**
     * Langile bat datu basetik ezabatzen duen funtzioa.
     * @param data
     * @param orduaHasi
     */
    public static void langileaEzabatu(String nan) {
        Connection conn = konexioa.konexioaHasi();
        PreparedStatement pstmt = null;

        try {
        	String sql = "DELETE FROM langilea WHERE langilea_nan = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nan);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            konexioa.konexioaItxi();
        }
    }
    
	/**
	 * Datu basean dagoen langilearen informazioa editatzen du.
	 * @param nan = nan-a kontsulta egiteko
	 * @param izena
	 * @param abizena
	 * @param telefonoa
	 * @param erabiltzailea
	 * @param pasahitza
	 * @param nanBerria = nan berria
	 */
    public static void langileaEditatu(String nan, String izena, String abizena, String telefonoa, String erabiltzailea, String pasahitza, String nanBerria) {
        try {
            Connection conn = konexioa.konexioaHasi();
            String sql = "UPDATE langilea SET langilea_nan = ?, langilea_izena = ?, langilea_abizena = ?, langilea_telefonoa = ?, erabiltzailea = ?, pasahitza = ? WHERE langilea_nan = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nanBerria);
            statement.setString(2, izena);
            statement.setString(3, abizena);
            statement.setString(4, telefonoa);
            statement.setString(5, erabiltzailea);
            statement.setString(6, pasahitza);
            statement.setString(7, nan);
            statement.executeUpdate();
            konexioa.konexioaItxi(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *Datu basean dauden pakete guztiekin osatutako lista bat itzultzen du. 
     * @return
     */
    public static List<Paketea> getPaketeak() {
        List<Paketea> paketeaList = new ArrayList<>();
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT langilea.langilea_izena, langilea.langilea_abizena, bezeroa.bezeroa_helbidea, paketea.entrega_data, paketea.ID "
                    + "FROM langilea INNER JOIN paketea ON langilea.langilea_nan = paketea.langilea_nan "
                    + "INNER JOIN bezeroa ON paketea.bezeroa_nan = bezeroa.bezeroa_nan");

            while (rs.next()) {
                String helbidea = rs.getString("bezeroa_helbidea");
                
                String izena = rs.getString("langilea_izena");

                String abizena = rs.getString("langilea_abizena");
                 
                String entrega_data = rs.getString("entrega_data");
                
                int id = rs.getInt("ID");

                Paketea paketea = new Paketea(entrega_data, izena + " " + abizena, helbidea, id);
                paketeaList.add(paketea);
            }
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paketeaList;
    }

    
    /**
     * Datu basean dauden bezeroen izenak itzultzen ditu
     * @return
     */
    public static List<String> getBezeroaIzena() {
        List<String> bezeroaList = new ArrayList<>();
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT bezeroa.bezeroa_izena, bezeroa.bezeroa_abizena, bezeroa_nan FROM bezeroa");

            while (rs.next()) {
                String bezeroa = rs.getString("bezeroa_izena") + " " + rs.getString("bezeroa_abizena") + ", " + rs.getString("bezeroa_nan");
                bezeroaList.add(bezeroa);
            }
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bezeroaList;
    }
    
    
    
    /**
     * Paketeak datu basean sartzeko funtzioa.
     * @param erab
     */
    public static void paketeaSartu(Paketea paketea) {
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            String sql_create = "INSERT INTO `paketea` (`ID`, `entrega_data`, `langilea_nan`, `bezeroa_nan`, `zein_entregatu`, `entregatuta`) VALUES (NULL, " + "'" + paketea.entrega_data  +"', '" + paketea.langilea_nan +"', '" + paketea.bezeroa_nan +"', '0', '0')";
            stmt.executeUpdate(sql_create);
            JOptionPane.showMessageDialog(null, "datuak ondo sartu dira.");
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Datu basetik paketa ezabatzako funtzioa.
     * @param erab
     */
    public static void paketeaEzabatu(int id) {
    	 Connection conn = konexioa.konexioaHasi();
         PreparedStatement pstmt = null;

         try {
         	String sql = "DELETE FROM paketea WHERE ID = ? ";
             pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1, id);
             pstmt.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             konexioa.konexioaItxi();
         }
    }
    
	/**
	 * Datu basean dauden paketeen informazioa editatzko funtzioa.
	 * @param entrega_data
	 * @param nanBezeroa
	 * @param nanLangilea
	 * @param id
	 */
    public static void paketeaEditatu(String entrega_data, String nanBezeroa, String nanLangilea, int id) {
        try {
            Connection conn = konexioa.konexioaHasi();
            String sql = "UPDATE paketea SET langilea_nan = ?, entrega_data = ?, bezeroa_nan = ? WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nanLangilea);
            statement.setString(2, entrega_data);
            statement.setString(3, nanBezeroa);
            statement.setInt(4, id);
            statement.executeUpdate();
            konexioa.konexioaItxi(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Historiala taularen zutabeen izenak itzultzen ditu
     * @return
     */
    public static List<String> historialaZutabeak() {
        List<String> historiala = new ArrayList<>();
        try {
            Statement stmt = konexioa.konexioaHasi().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'pakag' AND TABLE_NAME = 'historiala'");

            while (rs.next()) {
                historiala.add(rs.getString("COLUMN_NAME"));
            }
            konexioa.konexioaItxi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiala;
    }
}
