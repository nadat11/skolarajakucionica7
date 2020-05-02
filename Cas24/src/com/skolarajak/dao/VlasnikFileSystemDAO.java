package com.skolarajak.dao;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vlasnik;
import com.skolarajak.utils.RandomUtils;

public class VlasnikFileSystemDAO implements VlasnikDAO {
	private static String EXTENZIJA  = ".xml";
	private static String FILE_ROOT = "c:/tmp/"; // lokacija xml filea

	@Override
	public Vlasnik create(Vlasnik vlasnik) { //metoda kreira vlasnika  a za update ne setujemo kljuc i slucajne vrednosti iz createa
		vlasnik.setBrojVozackeDozvole(String.valueOf(System.currentTimeMillis()));
		vlasnik.setIme(RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo());
		vlasnik.setPrezime(RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo());
		//pre enkodiranja vlasnik mora da bude napravljen
		
		XMLEncoder encoder = null; // deklaracija xml enkoder
		try {
			encoder = new XMLEncoder (new BufferedOutputStream(new FileOutputStream(FILE_ROOT + vlasnik.getBrojVozackeDozvole() + EXTENZIJA)));// u insatncama vlasnika radimo sa  file operacija
		}catch (FileNotFoundException fileNotFound) { //
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(vlasnik);
		encoder.close(); //zatvra sve prethodne file stremove
		return vlasnik;
	}

	private char[] currentTimeMillis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vlasnik read(String brojVozackeDozvole) throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vlasnik update(Vlasnik vlasnik) { // update je create za postojeci fajl osim sto ne setujemo kljuc i slucajne vrednosti iz createa
		XMLEncoder encoder = null; // deklaracija xml enkoder
		try {
			encoder = new XMLEncoder (new BufferedOutputStream(new FileOutputStream(FILE_ROOT + vlasnik.getBrojVozackeDozvole() + EXTENZIJA)));// u insatncama vlasnika radimo sa  file operacija
		}catch (FileNotFoundException fileNotFound) { //
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(vlasnik);
		encoder.close(); //zatvra sve prethodne file stremove
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
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Vlasnik> getAllVlasniciAktivnihVozila() throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
