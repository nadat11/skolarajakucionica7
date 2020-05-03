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
import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.RandomUtils;

public class VoziloFileSystemDAO implements VoziloDAO {
	private static String EXTENZIJA = ".xml";
	private static String FILE_ROOT = "c:/tmpv/"; // izmeniti folder da bismo razdvojili vozila i vlasnike i izbegli veliko vreme ucitavanja

	@Override
	public Vozilo create(Vozilo vozilo) {
		vozilo.setRegistarskiBroj(String.valueOf(System.currentTimeMillis()));

		XMLEncoder encoder = null; // deklaracija xml enkoder
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream(FILE_ROOT + vozilo.getRegistarskiBroj() + EXTENZIJA)));// u insatncama vozila
																								// radimo sa file
																								// operacija
		} catch (FileNotFoundException fileNotFound) { //
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(vozilo);
		encoder.close(); // zatvra sve prethodne file stremove
		return vozilo;
	}

	@Override
	public Vozilo read(String registarskiBroj) throws ResultNotFoundException {
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(getFileName(registarskiBroj)))); // insatncirali
																													// dekodera
																													// buffer
																													// and
																													// input
																													// file
																													// readera
																													// ,
																													// get
																													// metoda
																													// trazi
																													// u
																													// c
																													// tempu
																													// file
																													// pod
																													// nazivom
																													// brojVozacke
																													// dozvole
																													// sa
																													// xml
																													// ekstenzijom
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File dvd.xml not found"); // ako nismo nasli file
		}
		Vozilo vozilo = (Vozilo) decoder.readObject(); // ako smo nasli decoder.read object onda ga kastujemo jer se
														// radi o tipu Vlasnik
		return vozilo; // i onda ga vratimo
	}

	@Override
	public Vozilo update(Vozilo vozilo) {
		XMLEncoder encoder = null; // deklaracija xml enkoder
		try {
			encoder = new XMLEncoder(
					new BufferedOutputStream(new FileOutputStream(getFileName(vozilo.getRegistarskiBroj()))));// u
																												// insatncama
																												// vlasnika
																												// radimo
																												// sa
																												// file
																												// operacija
		} catch (FileNotFoundException fileNotFound) { //
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(vozilo);
		encoder.close(); // zatvara sve prethodne file stremove
		return vozilo;
	}

	@Override
	public void delete(String registarskiBroj) {
		File file = new File(getFileName(registarskiBroj));
		file.delete();
	}

	@Override
	public List<Vozilo> getAll() throws ResultNotFoundException { // napraviti getAll za rad sa CRUD
		List<Vozilo> vozila = new ArrayList<Vozilo>();

		File[] files = new File(FILE_ROOT).listFiles();

		for (File file : files) { // iteriramo kroz objekte
			if (file.isFile()) { // ako je file file
				String fileName = file.getName();
				String brojVozackeDozvole = fileName.substring(0, fileName.lastIndexOf("."));
				vozila.add(this.read(brojVozackeDozvole));
			}
		}
		return vozila;
	}

	@Override
	public List<Vozilo> getEuro3Vozila() throws ResultNotFoundException {
		return this.getAll().stream() // stream sadrzi listu vozila
				.filter(v -> v.getGodisteProizvodnje() >= 2000).collect(Collectors.toList());
	}

	@Override
	public List<Vozilo> getAktivnaVozila() throws ResultNotFoundException { // umesto pristupa HashMapi ide getAll() jer
																			// pristupamo listi
		return this.getAll().stream().filter(v -> v.isAktivno()).collect(Collectors.toList());
	}

	@Override
	public long count() throws ResultNotFoundException {
		// TODO Auto-generated method stub
		return this.getAll().size();
	}

	@Override
	public List<Vozilo> getAllVozilaCijeImeVlasnikaSadrziSlovoA() throws ResultNotFoundException {
		return this.getAll().stream() // filter nad celokupnim skupom vrednosti
				.filter(v -> v.getVlasnik().getIme().contains("A")).collect(Collectors.toList());
	}

	private String getFileName(String registarskiBroj) {
		return FILE_ROOT + registarskiBroj + EXTENZIJA;
	}

}
