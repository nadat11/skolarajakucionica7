package com.skolarajak.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.DBUtils;

public class VoziloDBDAOImpl implements VoziloDAO {
	public VoziloDBDAOImpl() throws ClassNotFoundException {
		Class.forName(DBUtils.myDriver); 
	}
	
	
	public Vozilo create(Vozilo vozilo) {
		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = " insert into vozilo (registarskiBroj, godisteProizvodnje, status, vlasnikId)" 
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vozilo update(Vozilo vozilo) {
		try {
			// create a mysql database connection

			Connection conn = getConnection(); // otvara konekciju za bazu sa username i pass

			// the mysql insert statement
			String query = " update vozilo set registarskiBroj=?, godisteProizvodnje=?, status=?,vlasnikId=?"
					+" where registarskiBroj=? ";

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vozilo> getEuro3Vozila() throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vozilo> getAktivnaVozila() throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Vozilo> getAllVozilaCijeImeVlasnikaSadrziSlovoA() throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	private Connection getConnection() throws ClassNotFoundException, SQLException {

		return DriverManager.getConnection(DBUtils.myUrl, "root", "test");
	}
}
