package com.skolarajak.dao;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public Vlasnik read(String brojVozackeDozvole) throws ResultNotFoundException { //napravili smo dekoder
		XMLDecoder decoder = null;
		try {
			decoder= new XMLDecoder(new BufferedInputStream(new FileInputStream(getFileName(brojVozackeDozvole)))); //insatncirali dekodera buffer and input file readera , get metoda trazi u c tempu file pod nazivom brojVozacke dozvole sa xml ekstenzijom
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File dvd.xml not found"); // ako nismo nasli file
		}
		Vlasnik vlasnik = (Vlasnik)decoder.readObject(); //ako smo nasli decoder.read object onda ga kastujemo jer se radi o tipu Vlasnik
		return vlasnik; // i onda ga vratimo
	}

	@Override
	public Vlasnik update(Vlasnik vlasnik) { // update je create za postojeci fajl osim sto ne setujemo kljuc i slucajne vrednosti iz createa
		XMLEncoder encoder = null; // deklaracija xml enkoder
		try {
			encoder = new XMLEncoder (new BufferedOutputStream(new FileOutputStream(getFileName(vlasnik.getBrojVozackeDozvole()))));// u insatncama vlasnika radimo sa  file operacija
		}catch (FileNotFoundException fileNotFound) { //
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(vlasnik);
		encoder.close(); //zatvra sve prethodne file stremove
		return vlasnik;
		}

	@Override
	public void delete(String brojVozackeDozvole) {
		File file = new File(getFileName(brojVozackeDozvole)) ;
		file.delete();
	}

	@Override
	public List<Vlasnik> getAll() throws ResultNotFoundException {
		// selektovati sve fajlove iz temp dir i ucitati ih
//		List<String> brojeviVozackeDozvole = new ArrayList<String>(); // result s bice lista svih fajl imena sa .xml ekstenzijom
		List<Vlasnik> vlasnici = new ArrayList<Vlasnik>();
		
		File[] files = new File(FILE_ROOT).listFiles(); // new File objekat napravljen je da pokazuje na temp dir ima metodu listFiles()
		
		for (File file : files) { // iteriramo kroz objekte 
			if (file.isFile()) { // ako je file file 
				String fileName = file.getName();
				String brojVozackeDozvole = fileName.substring(0, fileName.lastIndexOf("."));
				vlasnici.add(this.read(brojVozackeDozvole));                  //add(fileName.substring(0,fileName.lastIndexOf("."))); //dodaj u listu
			}
		}
		return vlasnici;
	}

	@Override
	public long count()throws ResultNotFoundException  { 
		return this.getAll().size();
	}

	@Override
	public List<Vlasnik> getAllVlasniciAktivnihVozila() throws ResultNotFoundException {
		return  getAll()				
				.stream().filter(v -> v.getVozilo().isAktivno()).collect(Collectors.toList());
		
	}
	private String getFileName(String brojVozackeDozvole){
		return FILE_ROOT + brojVozackeDozvole + EXTENZIJA;
	}

}
