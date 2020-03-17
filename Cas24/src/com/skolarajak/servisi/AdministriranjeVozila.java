package com.skolarajak.servisi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
		voziloDAO = new VoziloInMemoryDAOImpl(); // inicijalizacija voziloDAO da bude InMemoryDAO
	}

	/*
	 * Vratiti test vozila
	 * 
	 * @return List<Vozilo> test vozila
	 */

	public List<Vozilo> generisi() {
		List<Vozilo> vozila = new ArrayList<Vozilo>();
		try { // hvatamo sve greske
		Vozilo zadnjeVozilo = null; // deklarisanje izvan petlje da bude vidljiva
		for (int i = 0; i < Konstante.UKUPAN_BROJ_VOZILA_U_SISTEMU; i++) {
			int godinaProizvodnje = dodeliGodinuProizvodnje();
			Vozilo vozilo = new Vozilo(godinaProizvodnje);
			vozilo.setAktivno(Math.random() > PRAG_RASPODELE_AKTIVNIH_VOZILA); // logicki izraz
			vozila.add(vozilo);
			zadnjeVozilo = voziloDAO.create(vozilo); // snimili smo memoriju vozila
		}

		System.out.println("Ukupno registarskih brojeva: " + voziloDAO.count());
		System.out.println("Godina proizvodnje poslednjeg registrovanog: " + zadnjeVozilo.getRegistarskiBroj() + " : "
				+ voziloDAO.read(zadnjeVozilo.getRegistarskiBroj()).isAktivno());

		zadnjeVozilo.setAktivno(!zadnjeVozilo.isAktivno()); // ako je aktivno promenimo status na negaciju neaktivno je
		zadnjeVozilo = voziloDAO.update(zadnjeVozilo);
		System.out.println("Godina proizvodnje poslednjeg registrovanog: " + zadnjeVozilo.getRegistarskiBroj() + " : "
				+ voziloDAO.read(zadnjeVozilo.getRegistarskiBroj()).isAktivno());
		voziloDAO.delete(zadnjeVozilo.getRegistarskiBroj());
		
			zadnjeVozilo = voziloDAO.read(zadnjeVozilo.getRegistarskiBroj());
		} catch (ResultNotFoundException e) {  // hendlujemo sve greske
			System.out.println("OBRISANO");
			System.out.println(e.getMessage()); // iz konstruktora ResultNotFoundException
		}
	/*	if(zadnjeVozilo != null) {
			System.out.println("Godina proizvodnje poslednjeg registrovanog: " + zadnjeVozilo.getRegistarskiBroj() + " : "
					+ zadnjeVozilo.isAktivno());
		} else {
			System.out.println("OBRISANO");
		}*/ //null check
		return vozila; // citamo iz dao  kljuc ali ne postoji objekat jer smo ga obrisali null point exeption
	} // moramo eksplicitno da ga proverimo da li je null, ili da bacimo exepption i da ga hvatamo i procesiramo

	public List<Vozilo> euro3Vozila(List<Vozilo> vozila) { // pocetna lista sadrzi sva vozila

	
		List<Vozilo> euro3Vozila = vozila.stream() // stream sadrzi listu vozila
				.filter(v -> v.getGodisteProizvodnje() >= 2000) // filterisi v element liste po kriterijumu za svaki
																// element postoji v kod koga sledi da je godiste vece
																// od 2000, ekvivalent if funkciji
				.collect(Collectors.toList()); // rezultat vrati u listu kolektor u list
		return euro3Vozila;
	}

	public List<Vozilo> aktivnaVozila(List<Vozilo> vozila) {
		List<Vozilo> aktivnaVozila = vozila.stream().filter(v -> v.isAktivno()).collect(Collectors.toList());
		return aktivnaVozila;
	}

	private int dodeliGodinuProizvodnje() {
		int godina = RandomUtils.slucajanBrojUIntervalu(Konstante.MIN_VOZILO_GODISTE, Konstante.MAX_VOZILO_GODISTE);
		return godina;
	}

}
