package pakAG;

import java.sql.*;

public class konexioa {

	 final static String DB_URL = "jdbc:mysql://localhost/pakag";
	
	 final static String USER = "root";
	
	 final static String PASS = "";
	
	 private static Connection konexioa;
	
	 public static Connection konexioaHasi() {
		 try {
			 if (konexioa == null || konexioa.isClosed()) {
				 konexioa = DriverManager.getConnection(DB_URL, USER, PASS);
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
		 return konexioa;
	 }

	 public static void konexioaItxi() {
		 try {
			 if (konexioa != null && !konexioa.isClosed()) {
				 konexioa.close();
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
	 }
}