package com.skolarajak.servisi;

import java.util.ArrayList;
import java.util.List;
import com.skolarajak.dao.VoziloInMemoryDAOImpl;
import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.Konstante;
import com.skolarajak.utils.RandomUtils;

public class AdministriranjeVozila {
	private static final boolean STATUS = true;
	private static final double PRAG_RASPODELE_AKTIVNIH_VOZILA = (double) 0.4;

	private VoziloInMemoryDAOImpl voziloDAO;

	public AdministriranjeVozila() {
		voziloDAO = new VoziloInMemoryDAOImpl(); // inicijalizacija voziloDAO i podaci stizu iz DAOa
	}

	/*
	 * Vratiti test vozila
	 * 
	 * @return List<Vozilo> test vozila
	 */

	public List<Vozilo> generisi() {
		List<Vozilo> vozila = new ArrayList<Vozilo>(); // sada ovu listu ne punimo u servisu vec je vraca DAO
		try { // hvatamo sve greske
			Vozilo zadnjeVozilo = null; // deklarisanje izvan petlje da bude vidljiva
			for (int i = 0; i < Konstante.UKUPAN_BROJ_VOZILA_U_SISTEMU; i++) {
				int godinaProizvodnje = dodeliGodinuProizvodnje();
				Vozilo vozilo = new Vozilo(godinaProizvodnje);
				vozilo.setAktivno(Math.random() > PRAG_RASPODELE_AKTIVNIH_VOZILA); // logicki izraz
				// vozila.add(vozilo); // iste objekte smo dodali u listu
				zadnjeVozilo = voziloDAO.create(vozilo); // i iste objekte smo dodali u mapu :(
			}

			System.out.println("Ukupno registarskih brojeva: " + voziloDAO.count());
			
			/*System.out.println("Godina proizvodnje poslednjeg registrovanog: " + zadnjeVozilo.getRegistarskiBroj()
					+ " : " + voziloDAO.read(zadnjeVozilo.getRegistarskiBroj()).isAktivno());

			zadnjeVozilo.setAktivno(!zadnjeVozilo.isAktivno()); // ako je aktivno promenimo status na negaciju neaktivno
																// je
			zadnjeVozilo = voziloDAO.update(zadnjeVozilo);
			System.out.println("Godina proizvodnje poslednjeg registrovanog: " + zadnjeVozilo.getRegistarskiBroj()
					+ " : " + voziloDAO.read(zadnjeVozilo.getRegistarskiBroj()).isAktivno());
			voziloDAO.delete(zadnjeVozilo.getRegistarskiBroj());*/

			vozila = voziloDAO.getAll(); // dodali smo listu iz DAOa (iz skladista),  moramo dodati pre ispaljivanja exceptiona
			
			//zadnjeVozilo = voziloDAO.read(zadnjeVozilo.getRegistarskiBroj());

		} catch (ResultNotFoundException e) { // hendlujemo sve greske
			System.out.println("OBRISANO");
			System.out.println(e.getMessage()); // iz konstruktora ResultNotFoundException
		}
		/*
		 * if(zadnjeVozilo != null) {
		 * System.out.println("Godina proizvodnje poslednjeg registrovanog: " +
		 * zadnjeVozilo.getRegistarskiBroj() + " : " + zadnjeVozilo.isAktivno()); } else
		 * { System.out.println("OBRISANO"); }
		 */ // null check
		return vozila; // citamo iz dao kljuc ali ne postoji objekat jer smo ga obrisali null point
						// exeption
	} // moramo eksplicitno da ga proverimo da li je null, ili da bacimo exepption i
		// da ga hvatamo i procesiramo

	public List<Vozilo> euro3Vozila() { // pocetna lista sadrzi sva vozila

		List<Vozilo> euro3Vozila = voziloDAO.getEuro3Vozila(); // pozivamo metodu iz DAO
		return euro3Vozila;
	}

	public List<Vozilo> aktivnaVozila() {
		List<Vozilo> aktivnaVozila = voziloDAO.getAktivnaVozila();
		return aktivnaVozila;
	}
	
	public List<Vozilo> dajSvaVozila() throws ResultNotFoundException {
		return voziloDAO.getAll();
		}

	private int dodeliGodinuProizvodnje() {
		int godina = RandomUtils.slucajanBrojUIntervalu(Konstante.MIN_VOZILO_GODISTE, Konstante.MAX_VOZILO_GODISTE);
		return godina;
	}

}
