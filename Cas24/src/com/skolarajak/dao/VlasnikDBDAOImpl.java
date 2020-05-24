package com.skolarajak.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vlasnik;
import com.skolarajak.utils.RandomUtils;

public class VlasnikDBDAOImpl implements VlasnikDAO {
	// create a mysql database connection
	private final String myDriver = "com.mysql.jdbc.Driver"; // drajver iz classpatha jar file
	private final String myUrl = "jdbc:mysql://localhost:3306/ams"; // port za bazu

	public VlasnikDBDAOImpl() throws ClassNotFoundException {

		Class.forName(myDriver); // ucitava u JVM classu myDriver
	}

	@Override
	public Vlasnik create(Vlasnik vlasnik) {

		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = " insert into vlasnik (brojVozackeDozvole, ime, prezime)" + " values (?, ?, ?)"; // insert
																												// sql
																												// comande
																												// bez
																												// ID
																												// jer
																												// je
																												// auto
																												// increment

			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); // prepare znaci da se insert napravi
																			// sematski jednom i onda se izvrsava
			preparedStmt.setString(1, vlasnik.getBrojVozackeDozvole()); // moramo da kazemo tip prepare statmenta ovde
																		// su sva tri String
			preparedStmt.setString(2, vlasnik.getIme());
			preparedStmt.setString(3, vlasnik.getPrezime());

			// execute the preparedstatement
			preparedStmt.execute(); // izvrsi
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Exception e) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		return vlasnik;
	}

	@Override
	public Vlasnik read(String brojVozackeDozvole) throws ResultNotFoundException {
		Vlasnik vlasnik = new Vlasnik();
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = " select * from vlasnik where brojVozackeDozvole=?"; // insert sql comande bez ID jer je auto
																				// increment

			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); // prepare znaci da se insert napravi
																			// sematski jednom i onda se izvrsava
			preparedStmt.setString(1, brojVozackeDozvole); // moramo da kazemo tip prepare statmenta ovde su sva tri
															// String

			// process the results
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {

				vlasnik.setBrojVozackeDozvole(brojVozackeDozvole);
				vlasnik.setIme(rs.getString("ime")); // text je naziv kolone
				vlasnik.setPrezime(rs.getString("prezime"));
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Exception e) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		return vlasnik;

	}

	@Override
	public Vlasnik update(Vlasnik vlasnik) {

		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = " update vlasnik set ime=?, prezime=? where brojVozackeDozvole=?";

			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			preparedStmt.setString(1, vlasnik.getIme());
			preparedStmt.setString(2, vlasnik.getPrezime());
			preparedStmt.setString(3, vlasnik.getBrojVozackeDozvole()); 

			// execute the preparedstatement
			preparedStmt.execute(); // izvrsi
			
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Exception e) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		return vlasnik;
	}

	@Override
	public void delete(String brojVozackeDozvole) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Vlasnik> getAll() throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() throws ResultNotFoundException {
		long count = 0;
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = " select count (*) as from vlasnik"; // insert sql comande bez ID jer je auto increment

			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); // prepare znaci da se insert napravi
																			// sematski jednom i onda se izvrsava

			// process the results
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				count = rs.getLong("broj"); // text je naziv kolone
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Exception e) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		return count;
	}

	@Override
	public List<Vlasnik> getAllVlasniciAktivnihVozila() throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException {

		return DriverManager.getConnection(myUrl, "root", "test");
	}

}
