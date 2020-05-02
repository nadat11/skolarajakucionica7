package com.skolarajak.servisi;

import java.util.ArrayList;
import java.util.List;

import com.skolarajak.dao.VlasnikDAO;
import com.skolarajak.dao.VlasnikFileSystemDAO;
import com.skolarajak.dao.VlasnikInMemoryDAOImpl;
import com.skolarajak.dao.VoziloDAO;
import com.skolarajak.dao.VoziloInMemoryDAOImpl;
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

	// konstruktor
	public AdministriranjeVozila() {
		voziloDAO = new VoziloInMemoryDAOImpl(); // citamo iz memorije
		vlasnikDAO = new VlasnikFileSystemDAO(); //povezivanje servisa sa storage layerom, citamo iz file-a
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
				vozilo.setAktivno(Math.random() > PRAG_RASPODELE_AKTIVNIH_VOZILA);
				zadnjeVozilo = voziloDAO.create(vozilo);

				// kreiranje vlasnika
				Vlasnik vlasnik = new Vlasnik();
				vlasnik = vlasnikDAO.create(vlasnik);
				vlasnik.setVozilo(zadnjeVozilo); 
				
				vlasnik = vlasnikDAO.update(vlasnik); 
				
				zadnjeVozilo.setVlasnik(vlasnik); //vozilo radi sa memorijom 
				zadnjeVozilo = voziloDAO.update(zadnjeVozilo);
			}

			System.out.println("Vlasnik: " + zadnjeVozilo.getVlasnik().getIme() + " "
					+ zadnjeVozilo.getVlasnik().getPrezime() + " " + zadnjeVozilo.getVlasnik().getBrojVozackeDozvole());

			System.out.println("Ukupno registarskih brojeva: " + voziloDAO.count());
			vozila = voziloDAO.getAll();
		} catch (ResultNotFoundException e) {
			System.out.println("OBRISANO");
			System.out.println(e.getMessage());
		}

		return vozila;
	}

	public List<Vozilo> euro3Vozila() {

		List<Vozilo> euro3Vozila = voziloDAO.getEuro3Vozila();
		return euro3Vozila;
	}

	public List<Vozilo> aktivnaVozila() {
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
	private int dodeliGodinuProizvodnje() {
		int godina = RandomUtils.slucajanBrojUIntervalu(Konstante.MIN_VOZILO_GODISTE, Konstante.MAX_VOZILO_GODISTE);
		return godina;
	}

}
