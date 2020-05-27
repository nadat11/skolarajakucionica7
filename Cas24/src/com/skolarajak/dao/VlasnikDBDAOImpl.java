package com.skolarajak.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skolarajak.dao.datasource.C3poDataSource;
import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vlasnik;
import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.DBUtils;

public class VlasnikDBDAOImpl implements VlasnikDAO {
	
	public VlasnikDBDAOImpl() throws ClassNotFoundException {

		Class.forName(DBUtils.myDriver); // ucitava u JVM classu myDriver
	}

	@Override
	public Vlasnik create(Vlasnik vlasnik) {

		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "insert into vlasnik (brojVozackeDozvole, ime, prezime)" + " values (?, ?, ?)"; // insert
																												// sql
																												

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
	};

	@Override
	public Vlasnik read(String brojVozackeDozvole) throws ResultNotFoundException {
		Vlasnik vlasnik = new Vlasnik();
		Vozilo vozilo = new Vozilo ();
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "select * from vlasnik, vozilo where brojVozackeDozvole=?"
					+ " and vlasnik.brojVozackeDozvole=vozilo.vlasnikId"; // veza vlasnika i vozila
		
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			preparedStmt.setString(1, brojVozackeDozvole); 

			// process the results
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {

				vlasnik.setBrojVozackeDozvole(brojVozackeDozvole);
				vlasnik.setIme(rs.getString("ime")); // text je naziv kolone
				vlasnik.setPrezime(rs.getString("prezime"));
				
				vozilo.setRegistarskiBroj(rs.getString("registarskiBroj"));
				vozilo.setGodisteProizvodnje(rs.getInt("godisteProizvodnje"));
				vozilo.setAktivno(rs.getBoolean("status"));
				vozilo.setVlasnik(vlasnik);//uvezano vozilo sa vlasnikom 
				
				vlasnik.setVozilo(vozilo); // obavezno povezivanje sa vlasnikom
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Throwable t) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(t.getMessage());
		}
	
		return vlasnik;
	};

	@Override
	public Vlasnik update(Vlasnik vlasnik) {

		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "update vlasnik set ime=?, prezime=? where brojVozackeDozvole = ?";

			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			preparedStmt.setString(1, vlasnik.getIme());
			preparedStmt.setString(2, vlasnik.getPrezime());
			preparedStmt.setString(3, vlasnik.getBrojVozackeDozvole()); 

			// execute the preparedstatement
			preparedStmt.executeUpdate(); // izvrsi
			
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
		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "delete from vlasnik where brojVozackeDozvole = ?";

			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			preparedStmt.setString(1, brojVozackeDozvole); 

			// execute the preparedstatement
			preparedStmt.execute(); // izvrsi
			
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Exception e) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}

	@Override
	public List<Vlasnik> getAll() throws ResultNotFoundException {
		List<Vlasnik> vlasnici = new ArrayList<Vlasnik>();
		
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "select * from vlasnik, vozilo"
					+ " WHERE vlasnik.brojVozackeDozvole=vozilo.vlasnikId"; // veza vlasnika i vozila
		
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			
			// process the results
			ResultSet rs = preparedStmt.executeQuery(); // slogovi iz baze
			
			while (rs.next())//iteriramo kroz te slogove
			{
				Vlasnik vlasnik = new Vlasnik(); //u petlji za svaki slog kreirati novi par vlasnik vozilo, uvezati ih
				Vozilo vozilo = new Vozilo ();
				
				vlasnik.setBrojVozackeDozvole(rs.getString("brojVozackeDozvole"));
				vlasnik.setIme(rs.getString("ime")); // text je naziv kolone
				vlasnik.setPrezime(rs.getString("prezime"));
				
				vozilo.setRegistarskiBroj(rs.getString("registarskiBroj"));
				vozilo.setGodisteProizvodnje(rs.getInt("godisteProizvodnje"));
				vozilo.setAktivno(rs.getBoolean("status"));
				vozilo.setVlasnik(vlasnik);//uvezano vozilo sa vlasnikom 
				
				vlasnik.setVozilo(vozilo); // obavezno povezivanje sa vlasnikom
				
				vlasnici.add(vlasnik);
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Throwable t) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(t.getMessage());
		}
	
		return vlasnici;
	}
	
	@Override
	public long count() throws ResultNotFoundException {
		long count = 0;
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "select count(*) as broj from vlasnik"; // insert sql comande bez ID jer je auto increment

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
		List<Vlasnik> vlasnici = new ArrayList<Vlasnik>();
		
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "select * from vlasnik, vozilo"
					+ " WHERE vlasnik.brojVozackeDozvole=vozilo.vlasnikId and vozilo.status=1"; // veza vlasnika i vozila
		
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			
			// process the results
			ResultSet rs = preparedStmt.executeQuery(); // slogovi iz baze
			
			while (rs.next())//iteriramo kroz te slogove
			{
				Vlasnik vlasnik = new Vlasnik(); //u petlji za svaki slog kreirati novi par vlasnik vozilo, uvezati ih
				Vozilo vozilo = new Vozilo ();
				
				vlasnik.setBrojVozackeDozvole(rs.getString("brojVozackeDozvole"));
				vlasnik.setIme(rs.getString("ime")); // text je naziv kolone
				vlasnik.setPrezime(rs.getString("prezime"));
				
				vozilo.setRegistarskiBroj(rs.getString("registarskiBroj"));
				vozilo.setGodisteProizvodnje(rs.getInt("godisteProizvodnje"));
				vozilo.setAktivno(rs.getBoolean("status"));
				vozilo.setVlasnik(vlasnik);//uvezano vozilo sa vlasnikom 
				
				vlasnik.setVozilo(vozilo); // obavezno povezivanje sa vlasnikom
				
				vlasnici.add(vlasnik);
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Throwable t) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(t.getMessage());
		}
	
		return vlasnici;
	}
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		return C3poDataSource.getConnection();
		//return DriverManager.getConnection(DBUtils.myUrl, "root", "test");
	}

}
