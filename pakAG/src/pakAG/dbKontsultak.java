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
	 * Erabiltzaile bat sortu nahi dugunean, datu basean erabiltzaile hori badagoen
	 * begiratzen du
	 * 
	 * @param erabiltzailea
	 * @return
	 */
	public static boolean erablitzaileaBadago(String erabiltzailea) {
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT erabiltzailea FROM langilea WHERE erabiltzailea = '" + erabiltzailea + "'");
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
	 * 
	 * @param langilea
	 */
	public static void langileaSartu(Langilea langilea) {
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			String sql_create = "INSERT INTO langilea (`langilea_nan`, `langilea_izena`, `langilea_abizena`, `langilea_telefonoa`, `erabiltzailea`, `pasahitza`, `mota`) "
					+ "VALUES ('" + langilea.getLangileaNAN() + "', '" + langilea.getIzena() + "', '" + langilea.getAbizena() + "', '"
					+ langilea.getTelefonoa() + "', '" + langilea.getErabiltzailea() + "', '" + langilea.getPasahitza()
					+ "', 'langilea')";
			stmt.executeUpdate(sql_create);
			JOptionPane.showMessageDialog(null, "datuak ondo sartu dira.");
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saioa hasteko funtzioa. 3 aldiz pasahitza gaziki sartzean erabiltzailea
	 * blokeatzen da.
	 * 
	 * @param erabiltzailea
	 * @param pasahitza
	 * @return
	 */
	public static boolean saioaHasi(String erabiltzailea, String pasahitza) {
		if (blokeatutakoErabiltzaileak.containsKey(erabiltzailea)) {
			// Blokeo denboraren informazioa lortzen da
			long blokeoa = blokeatutakoErabiltzaileak.get(erabiltzailea);
			long pasatako_denbora = System.currentTimeMillis() - blokeoa;
			if (pasatako_denbora < blokeo_denbora) {
				long segunduak_faltan = (blokeo_denbora - pasatako_denbora) / 1000;
				JOptionPane.showMessageDialog(null,
						"Erabiltzailea " + segunduak_faltan + " segundoz blokeatuta dago. Saiatu berriro beranduago.");
				return false;
			} else {
				blokeatutakoErabiltzaileak.remove(erabiltzailea);
				// Saiakera kontagailua 0-ra berriz ezarri
				akats_kop.put(erabiltzailea, 0);
			}
		}

		// Erabiltzailea listan ez badago kontagailua 0ra itzultzen da
		if (!akats_kop.containsKey(erabiltzailea)) {
			akats_kop.put(erabiltzailea, 0);
		}

		int saiakerak = akats_kop.get(erabiltzailea);
		if (saiakerak >= max_akatsak) {
			JOptionPane.showMessageDialog(null,
					"Erabiltzailea edo pasahitza gaizki sartu duzu 3 aldiz. Erabiltzailea blokeatu egingo da 15 segundoz.");
			// Blokeo denboraren hasiera markatzen da
			blokeatutakoErabiltzaileak.put(erabiltzailea, System.currentTimeMillis());
			JOptionPane.showMessageDialog(null, "Erabiltzailea blokeatu da. Saiatu berriro 15 segundu beranduago.");
			return false;
		}

		// Erabiltzailea eta pasahitza balidatu
		if (balidatu(erabiltzailea, pasahitza)) {
			return true;
		} else {
			// Saiakera kopurua eguneratu
			akats_kop.put(erabiltzailea, saiakerak + 1);
			return false;
		}
	}

	/**
	 * Pasahitza eta erabiltzailea ondo dauden begiratzen du
	 * 
	 * @param erabiltzailea
	 * @param pasahitza
	 * @return
	 */
	private static boolean balidatu(String erabiltzailea, String pasahitza) {
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM langilea WHERE erabiltzailea = '" + erabiltzailea
					+ "' AND pasahitza = '" + pasahitza + "' AND mota = 'kudeatzailea'");
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
	 * 
	 * @return historialaList
	 */
	public static List<Historiala> paketeHistoriala() {

		List<Historiala> historialaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT `historiala`.`entrega_data`, `historiala`.`ID`,`historiala`.`oharra`, `bezeroa`.`bezeroa_izena`, `bezeroa`.`bezeroa_abizena`, `bezeroa`.`bezeroa_helbidea`, `langilea`.`langilea_izena`, `langilea`.`langilea_abizena` "
							+ "FROM `historiala` "
							+ "INNER JOIN `bezeroa` ON `historiala`.`bezeroa_nan` = `bezeroa`.`bezeroa_nan` "
							+ "INNER JOIN `langilea` ON `historiala`.`langilea_nan` = `langilea`.`langilea_nan` ");
			while (rs.next()) {
				String data = rs.getString("entrega_data");
				String helbidea = rs.getString("bezeroa_helbidea");
				String izena = rs.getString("bezeroa_izena") + " " + rs.getString("bezeroa_abizena");
				String langilea = rs.getString("langilea_izena") + " " + rs.getString("langilea_abizena");
				String oharra;
				String id = rs.getString("ID");

				if (rs.getString("oharra") == null) {
					oharra = "Ez dago oharrik";
				} else {
					oharra = rs.getString("oharra");
				}

				Historiala h = new Historiala(data, izena, helbidea, oharra, langilea, id);
				historialaList.add(h);
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return historialaList;
	}

	/**
	 * Datu basetik entregatu diren pakete guztiak erabiltzaileak nahi bezala
	 * ordenatuta ateratzen ditu.
	 * 
	 * @return historialaList
	 */
	public static List<Historiala> historialaOrdenatuta(String zutabea) {

		List<Historiala> historialaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT `historiala`.`entrega_data`, `historiala`.`ID`, `historiala`.`oharra`, `bezeroa`.`bezeroa_izena`, `bezeroa`.`bezeroa_abizena`, `bezeroa`.`bezeroa_helbidea`, `langilea`.`langilea_izena`, `langilea`.`langilea_abizena` "
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
				String id = rs.getString("ID");

				if (rs.getString("oharra") == null) {
					oharra = "Ez dago oharrik";
				} else {
					oharra = rs.getString("oharra");
				}

				Historiala h = new Historiala(data, izena, helbidea, oharra, langilea, id);
				historialaList.add(h);
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return historialaList;
	}

	/**
	 * Datu basetik entregatu diren paketeak erabiltzaileak erabakitako moduan
	 * filtratuta itzultzen ditu.
	 * 
	 * @return historialaList
	 */
	public static List<Historiala> historialaFiltratu(String zutabea, String balorea) {

		List<Historiala> historialaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT `historiala`.`entrega_data`, `historiala`.`ID`, `historiala`.`oharra`, `bezeroa`.`bezeroa_izena`, `bezeroa`.`bezeroa_abizena`, `bezeroa`.`bezeroa_helbidea`, `langilea`.`langilea_izena`, `langilea`.`langilea_abizena` "
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
				String id = rs.getString("ID");

				if (rs.getString("oharra") == null) {
					oharra = "Ez dago oharrik";
				} else {
					oharra = rs.getString("oharra");
				}

				Historiala h = new Historiala(data, izena, helbidea, oharra, langilea, id);
				historialaList.add(h);
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return historialaList;
	}

	/**
	 * Langileen datu guztiekin lista bat itzultzen du.
	 * 
	 * @return erabiltzaileaList
	 */
	public static List<Langilea> getLangileak() {
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
	 * Langile guztien nan eta izen abizenekin osatutako lista bat itzultzen du.
	 * 
	 * @return erabiltzaileaList
	 */
	public static List<String> getLangileIzenak() {
		List<String> erabiltzaileaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `langilea` WHERE `mota` = 'langilea'");
			while (rs.next()) {
				String langilea = rs.getString("langilea_izena") + " " + rs.getString("langilea_abizena") + " ,"
						+ rs.getString("langilea_nan");
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
	 * 
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
	 * 
	 * @param nan = nan-a kontsulta egiteko
	 * @param izena
	 * @param abizena
	 * @param telefonoa
	 * @param erabiltzailea
	 * @param pasahitza
	 * @param nanBerria = nan berria
	 */
	public static void langileaEditatu(String nan, String izena, String abizena, String telefonoa, String erabiltzailea,
			String pasahitza, String nanBerria) {
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
	 * Datu basean dauden paketeen datu guztiekin osatutako lista bat itzultzen du.
	 * 
	 * @return paketeaList
	 */
	public static List<Paketea> getPaketeak() {
		List<Paketea> paketeaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT langilea.langilea_izena, langilea.langilea_abizena, bezeroa.bezeroa_helbidea, bezeroa.bezeroa_izena, bezeroa.bezeroa_abizena, paketea.entrega_data, paketea.ID "
							+ "FROM langilea INNER JOIN paketea ON langilea.langilea_nan = paketea.langilea_nan "
							+ "INNER JOIN bezeroa ON paketea.bezeroa_nan = bezeroa.bezeroa_nan");

			while (rs.next()) {
				String helbidea = rs.getString("bezeroa_helbidea");
				String izena = rs.getString("langilea_izena");
				String abizena = rs.getString("langilea_abizena");
				String entrega_data = rs.getString("entrega_data");
				int id = rs.getInt("ID");
				String bezeroIzena = rs.getString("bezeroa_izena") + " " + rs.getString("bezeroa_abizena");

				Paketea paketea = new Paketea(entrega_data, izena + " " + abizena, helbidea, bezeroIzena, id);
				paketeaList.add(paketea);
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paketeaList;
	}

	/**
	 * Datu basean dauden bezeroen izen-abizena eta nan-a itzultzen ditu
	 * 
	 * @return bezeroaList
	 */
	public static List<String> getBezeroaIzena() {
		List<String> bezeroaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT bezeroa.bezeroa_izena, bezeroa.bezeroa_abizena, bezeroa_nan FROM bezeroa");

			while (rs.next()) {
				String bezeroa = rs.getString("bezeroa_izena") + " " + rs.getString("bezeroa_abizena") + ", "
						+ rs.getString("bezeroa_nan");
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
	 * 
	 * @param paketea
	 */
	public static void paketeaSartu(Paketea paketea) {
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			String sql_create = "INSERT INTO `paketea` (`ID`, `entrega_data`, `langilea_nan`, `bezeroa_nan`, `zein_entregatu`, `entregatuta`) VALUES ('" + paketea.getID() + "', "
					+ "'" + paketea.getEntrega_data() + "', '" + paketea.getLangileaNAN() + "', '" + paketea.getBezeroaNAN()
					+ "', '0', '0')";
			stmt.executeUpdate(sql_create);
			JOptionPane.showMessageDialog(null, "datuak ondo sartu dira.");
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Datu basetik paketea ezabatzako funtzioa.
	 * 
	 * @param id = paketearen id-a
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
	 * 
	 * @param entrega_data
	 * @param nanBezeroa
	 * @param nanLangilea
	 * @param id = paketearen id-a
	 */
	public static void paketeaEditatu(String entrega_data, String nanBezeroa, String nanLangilea, int id, int newId) {
		try {
			Connection conn = konexioa.konexioaHasi();
			String sql = "UPDATE paketea SET langilea_nan = ?, entrega_data = ?, bezeroa_nan = ?, ID = ? WHERE ID = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nanLangilea);
			statement.setString(2, entrega_data);
			statement.setString(3, nanBezeroa);
			statement.setInt(4, newId);
			statement.setInt(5, id);
			statement.executeUpdate();
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Historiala taularen zutabeen izenak itzultzen ditu
	 * 
	 * @return historiala
	 */
	public static List<String> historialaZutabeak() {
		List<String> historiala = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'pakag' AND TABLE_NAME = 'historiala'");

			while (rs.next()) {
				historiala.add(rs.getString("COLUMN_NAME"));
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return historiala;
	}

	/**
	 * Historiala taularen zutabeen izenak itzultzen ditu
	 * 
	 * @return historiala
	 */
	public static List<String> langileaHistorialaZutabeak() {
		List<String> historiala = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'pakag' AND TABLE_NAME IN ('bezeroa', 'historiala') "
							+ "AND COLUMN_NAME LIKE '%entrega_data%' OR COLUMN_NAME LIKE '%bezeroa_helbidea%'");

			while (rs.next()) {
				historiala.add(rs.getString("COLUMN_NAME"));
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return historiala;
	}

	/**
	 * Datu basean dauden paketeen datu guztiekin osatutako lista bat itzultzen du.
	 * 
	 * @return langileaList
	 */
	public static List<Langilea> getLangileHistoriala(String nan) {
		List<Langilea> langileaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT historiala.ID, historiala.entrega_data, bezeroa.bezeroa_helbidea "
					+ "FROM langilea " + "INNER JOIN historiala ON langilea.langilea_nan = historiala.langilea_nan "
					+ "INNER JOIN bezeroa ON historiala.bezeroa_nan = bezeroa.bezeroa_nan "
					+ "WHERE langilea.langilea_nan = '" + nan + "'");

			while (rs.next()) {
				String id = rs.getString("ID");
				String entrega_data = rs.getString("entrega_data");
				String helbidea = rs.getString("bezeroa_helbidea");

				Langilea langilea = new Langilea(id, entrega_data, helbidea);
				langileaList.add(langilea);
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return langileaList;
	}

	/**
	 * Datu basean dauden paketeen datu guztiekin osatutako lista bat itzultzen du.
	 * 
	 * @return langileaList
	 */
	public static List<Langilea> langileHistorialaOrdenatu(String zutabea, String nan) {
		List<Langilea> langileaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT historiala.ID, historiala.entrega_data, bezeroa.bezeroa_helbidea "
					+ "FROM langilea " + "INNER JOIN historiala ON langilea.langilea_nan = historiala.langilea_nan "
					+ "INNER JOIN bezeroa ON historiala.bezeroa_nan = bezeroa.bezeroa_nan "
					+ "WHERE langilea.langilea_nan = '" + nan + "' ORDER BY '" + zutabea + "' ASC");

			while (rs.next()) {
				String id = rs.getString("ID");
				String entrega_data = rs.getString("entrega_data");
				String helbidea = rs.getString("bezeroa_helbidea");

				Langilea langilea = new Langilea(id, entrega_data, helbidea);
				langileaList.add(langilea);
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return langileaList;
	}

	/**
	 * Datu basean dauden paketeen datu guztiekin osatutako lista bat itzultzen du.
	 * 
	 * @return langileaList
	 */
	public static List<Langilea> langileHistorialaFiltratu(String zutabea, String balorea, String nan) {
		List<Langilea> langileaList = new ArrayList<>();
		try {
			Statement stmt = konexioa.konexioaHasi().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT historiala.ID, historiala.entrega_data, bezeroa.bezeroa_helbidea "
					+ "FROM langilea " + "INNER JOIN historiala ON langilea.langilea_nan = historiala.langilea_nan "
					+ "INNER JOIN bezeroa ON historiala.bezeroa_nan = bezeroa.bezeroa_nan "
					+ "WHERE langilea.langilea_nan = '" + nan + "'" + "AND " + zutabea + " = '" + balorea + "'");

			while (rs.next()) {
				String id = rs.getString("ID");
				String entrega_data = rs.getString("entrega_data");
				String helbidea = rs.getString("bezeroa_helbidea");

				Langilea langilea = new Langilea(id, entrega_data, helbidea);
				langileaList.add(langilea);
			}
			konexioa.konexioaItxi();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return langileaList;
	}
}