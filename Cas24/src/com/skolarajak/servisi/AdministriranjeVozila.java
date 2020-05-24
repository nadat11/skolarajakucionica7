package com.skolarajak.servisi;

import java.util.ArrayList;
import java.util.List;

import com.skolarajak.dao.VlasnikDAO;
import com.skolarajak.dao.VlasnikDBDAOImpl;
import com.skolarajak.dao.VoziloDAO;
import com.skolarajak.dao.VoziloDBDAOImpl;
import com.skolarajak.dao.VoziloFileSystemDAO;
import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vlasnik;
import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.Konstante;
import com.skolarajak.utils.RandomUtils;

public class AdministriranjeVozila {
	private static final boolean STATUS = true;
	private static final double PRAG_RASPODELE_AKTIVNIH_VOZILA = (double) 0.4;

	private VoziloDAO voziloDAO;
	private VlasnikDAO vlasnikDAO;

	// konstruktor i jedina veza sa skladistima!!! 
	public AdministriranjeVozila() {
		voziloDAO = new VoziloFileSystemDAO(); // citamo iz file sistema
		try {
			
			vlasnikDAO = new VlasnikDBDAOImpl();
			voziloDAO = new VoziloDBDAOImpl();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //povezivanje sa DB samo vlasnik
	}

	/*
	 * Vratiti test vozila
	 * 
	 * @return List<Vozilo> test vozila
	 */

	public List<Vozilo> generisi() {
		List<Vozilo> vozila = new ArrayList<Vozilo>();
		try { // hvatamo sve greske
			Vozilo zadnjeVozilo = null;
			for (int i = 0; i < Konstante.UKUPAN_BROJ_VOZILA_U_SISTEMU; i++) {
				int godinaProizvodnje = dodeliGodinuProizvodnje();
				Vozilo vozilo = new Vozilo(godinaProizvodnje);
				vozilo.setRegistarskiBroj(String.valueOf(System.currentTimeMillis()));
				vozilo.setAktivno(Math.random() > PRAG_RASPODELE_AKTIVNIH_VOZILA);
				

				// kreiranje vlasnika
				Vlasnik vlasnik = new Vlasnik();

				vlasnik.setBrojVozackeDozvole(String.valueOf(System.currentTimeMillis()));
				vlasnik.setIme(RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo());
				vlasnik.setPrezime(RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo());
				

				vlasnik = vlasnikDAO.create(vlasnik); //kreiranje vlasnika u BP
				vozilo.setVlasnik(vlasnik);//vozilu smo setovali vlasnika,pre nego sto upisemo vozilo ono mora da ima vezu ka vlasniku
				zadnjeVozilo = voziloDAO.create(vozilo);// i sada se to vozilo upisuje u bazu
			
				vlasnik.setVozilo(zadnjeVozilo);//vlasniku smo setovali vozilo
				
				vlasnik = vlasnikDAO.update(vlasnik); //oba updateujemo
				zadnjeVozilo = voziloDAO.update(zadnjeVozilo);
				
			}

			System.out.println("Vlasnik: " + zadnjeVozilo.getVlasnik().getIme() + " "
					+ zadnjeVozilo.getVlasnik().getPrezime() + " " + zadnjeVozilo.getVlasnik().getBrojVozackeDozvole());

			System.out.println("Ukupno registarskih brojeva: " + voziloDAO.count());
			System.out.println("Ukupno vlasnika: " + vlasnikDAO.count());
			 //uporedjivanje memorijskog dela i file systema, ucitavanje da li je sinhornizovano
			Vlasnik zadnjiVlasnik = zadnjeVozilo.getVlasnik();
			Vlasnik ucitaniVlasnik = vlasnikDAO.read(zadnjiVlasnik.getBrojVozackeDozvole());
			
			System.out.println(zadnjiVlasnik.getBrojVozackeDozvole() + "======" + zadnjiVlasnik.getPrezime()+"-------"+ucitaniVlasnik.getPrezime());
			
			vozila = voziloDAO.getAll();
		} catch (ResultNotFoundException e) {
			System.out.println("OBRISANO");
			System.out.println(e.getMessage());
		}

		return vozila;
	}

	public List<Vozilo> euro3Vozila() throws ResultNotFoundException {

		List<Vozilo> euro3Vozila = voziloDAO.getEuro3Vozila();
		return euro3Vozila;
	}

	public List<Vozilo> aktivnaVozila() throws ResultNotFoundException {
		List<Vozilo> aktivnaVozila = voziloDAO.getAktivnaVozila();
		return aktivnaVozila;
	}

	public List<Vozilo> dajSvaVozila() throws ResultNotFoundException {
		return voziloDAO.getAll();
	}

	public List<Vlasnik> dajSveVlasnike() throws ResultNotFoundException {
		return vlasnikDAO.getAll();
	}
	public List<Vlasnik> dajSveVlasnikeAktivnihVozila() throws ResultNotFoundException {
		return vlasnikDAO.getAllVlasniciAktivnihVozila();
	}
	public List<Vozilo> dajSvaVozilaCijeImeVlasnikaSadrziSlovoA() throws ResultNotFoundException {
		return voziloDAO.getAllVozilaCijeImeVlasnikaSadrziSlovoA();
	}
	public int dodeliGodinuProizvodnje() {
		int godina = RandomUtils.slucajanBrojUIntervalu(Konstante.MIN_VOZILO_GODISTE, Konstante.MAX_VOZILO_GODISTE);
		return godina;
	}

}
