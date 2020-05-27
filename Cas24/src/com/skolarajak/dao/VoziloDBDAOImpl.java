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
import com.skolarajak.utils.Konstante;
import com.skolarajak.utils.SysUtils;

public class VoziloDBDAOImpl implements VoziloDAO {
	public VoziloDBDAOImpl() throws ClassNotFoundException {
		Class.forName(DBUtils.myDriver); 
	}
	
	
	public Vozilo create(Vozilo vozilo) {
		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "insert into vozilo (registarskiBroj, godisteProizvodnje, status, vlasnikId)"
					+ " values (?, ?, ?, ?)";
																												
			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
																			
			preparedStmt.setString(1, vozilo.getRegistarskiBroj()); 															
			preparedStmt.setInt(2, vozilo.getGodisteProizvodnje());
			preparedStmt.setBoolean(3, vozilo.isAktivno());
			preparedStmt.setString(4, vozilo.getVlasnik().getBrojVozackeDozvole());
			

			// execute the preparedstatement
			preparedStmt.execute(); // izvrsi
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Exception e) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		return vozilo;
	}


	@Override
	public Vozilo read(String registarskiBroj) throws ResultNotFoundException {
		Vlasnik vlasnik = new Vlasnik();// trebaju zajedno jer se uvezuju
		Vozilo vozilo = new Vozilo ();
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "select * from vlasnik, vozilo where registarskiBroj=?"
					+ " and vlasnik.brojVozackeDozvole=vozilo.vlasnikId"; // veza vlasnika i vozila
		
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			preparedStmt.setString(1, registarskiBroj); //dovde je priprema sql statemanta

			// process the results
			ResultSet rs = preparedStmt.executeQuery(); // izvrsavanje statmenta
			while (rs.next())
			{

				vlasnik.setBrojVozackeDozvole(rs.getString("brojVozackeDozvole")); // broj vozacke dozvole stize iz upita
				vlasnik.setIme(rs.getString("ime")); // text je naziv kolone
				vlasnik.setPrezime(rs.getString("prezime"));
				
				vozilo.setRegistarskiBroj(registarskiBroj);
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
	
		return vozilo;
	};

	@Override
	public Vozilo update(Vozilo vozilo) {
		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "update vozilo set registarskiBroj=?, godisteProizvodnje=?, status=?, vlasnikId=?"
					+ " where registarskiBroj = ?";

			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			preparedStmt.setString(1, vozilo.getRegistarskiBroj());
			preparedStmt.setInt(2, vozilo.getGodisteProizvodnje());
			preparedStmt.setBoolean(3, vozilo.isAktivno()); 
			preparedStmt.setString(4, vozilo.getVlasnik().getBrojVozackeDozvole()); 
			preparedStmt.setString(5, vozilo.getRegistarskiBroj()); 


			// execute the preparedstatement
			preparedStmt.executeUpdate(); // izvrsi
			
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Exception e) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		return vozilo;
	}

	@Override
	public void delete(String registarskiBroj) {
		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "delete from vlasnik where registarskiBroj=?";

			// create the mysql insert preparedstatement za ? ? ? ?
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			preparedStmt.setString(1, registarskiBroj); 

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
	public List<Vozilo> getAll() throws ResultNotFoundException {
    List<Vozilo> vozila = new ArrayList<Vozilo>();
		
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
				
				vozila.add(vozilo);
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Throwable t) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(t.getMessage());
		}
	
		return vozila;
	}
	
	@Override
	public List<Vozilo> getEuro3Vozila() throws ResultNotFoundException {
		List<Vozilo> vozila = new ArrayList<Vozilo>();
		
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = "select * from vlasnik, vozilo"
					+ " WHERE vlasnik.brojVozackeDozvole=vozilo.vlasnikId "
					+ "and vozilo.godisteProizvodnje>?"; // veza vlasnika i vozila
		
			PreparedStatement preparedStmt = conn.prepareStatement(query); 
			preparedStmt.setInt(1, Konstante.EURO_3_GODISTE); 
			
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
				
				vozila.add(vozilo);
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Throwable t) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(t.getMessage());
		}
	
		return vozila;
	}
	

	@Override
	public List<Vozilo> getAktivnaVozila() throws ResultNotFoundException {
		List<Vozilo> vozila = new ArrayList<Vozilo>();
		
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
				
				vozila.add(vozilo);
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Throwable t) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(t.getMessage());
		}
	
		return vozila;
	}
	
	@Override //isti je kod kao kod vlasnika
	public long count() throws ResultNotFoundException {
		long count = 0;
		try {

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = " select count(*) as broj from vozilo"; // insert sql comande bez ID jer je auto increment

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
	public List<Vozilo> getAllVozilaCijeImeVlasnikaSadrziSlovoA() throws ResultNotFoundException {
		List<Vozilo> vozila = new ArrayList<Vozilo>();
		
		try {
			long startTime = System.nanoTime();
			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass
			SysUtils.printDuration(startTime);
		
			//za obavljanje transakcija se koriste rollback, commit na nivou konekcije
			//con.setAutoCommit(false); con.commit(); con.rollback()
			
			
			// the mysql insert statement
			String query = "select * from vlasnik, vozilo"
					+ " WHERE vlasnik.brojVozackeDozvole=vozilo.vlasnikId "
					+ "and lower(vlasnik.ime) like '%a%'";
			
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
				
				vozila.add(vozilo);
			}
			rs.close(); // sve sto smo otvorili zatvaramo resultset, preparestatment, connection
			preparedStmt.close();
			conn.close(); // zatvori konekciju
		} catch (Throwable t) { // ako ima greska
			System.err.println("Got an exception!");
			System.err.println(t.getMessage());
		}
	
		return vozila;
	}
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		return C3poDataSource.getConnection();
		//return DriverManager.getConnection(DBUtils.myUrl, "root", "test");
	}
}
