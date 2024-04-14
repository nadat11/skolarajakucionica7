package com.skolarajak.servisi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.Konstante;

// Service layers
public class AdministriranjeVozila {
	private static final boolean STATUS = true;
	private static final double PRAG_RASPODELE_AKTIVNIH_VOZILA = (double) 0.4; // kontrolisemo da li hocemo da imamo
	private static final int SLOVO_A= 65;                                       // vise aktivnih i neaktivnih
	private static final int SLOVO_Z= 90;
	
	public List<Vozilo> generisi() {
		List<Vozilo> vozila = new ArrayList<Vozilo>();
		for (int i = 0; i < Konstante.UKUPAN_BROJ_VOZILA_U_SISTEMU; i++) {
			int godinaProizvodnje = dodeliGodinuProizvodnje();
			Vozilo vozilo = new Vozilo(godinaProizvodnje); // i promenjeno u godinaProizvodnje, da bi konstante iz
															// metode radile
			vozilo.setAktivno(Math.random() > PRAG_RASPODELE_AKTIVNIH_VOZILA); // logicki izraz, ako je veca od 0.5 bice
																				// true, ako je manje jednako od 0.5
																				// false
			vozilo.setRegistarskiBroj("Reg-" + i + slucajnoSlovo() + slucajnoSlovo());
			vozila.add(vozilo);
		}
		return vozila;
	}

	public List<Vozilo> euro3Vozila(List<Vozilo> vozila) { // pocetna lista sadrzi sva vozila
		/*
		 * List<Vozilo> euro3Vozila = new ArrayList<Vozilo>(); // filtrirana privremena
		 *                                                        lista rezultata samo euro 3 vozila
		 * for(Vozilo vozilo : vozila) { // proveri elemente u listi
		 *  if(vozilo.getGodisteProizvodnje() >= Konstante.EURO_3_GODISTE) { // ako odgovara uslovu 
		 *  euro3Vozila.add(vozilo);                                         dodaje se u privremenu listu euro 3 vozila 
		 * //                                                               
		 * }
		 * }
		 */

		// Java 8 lambda izraz
		List<Vozilo> euro3Vozila = vozila.stream() // stream sadrzi listu vozila
				.filter(v -> v.getGodisteProizvodnje() >= 2000) // filterisi v element liste po kriterijumu za svaki
																// element postoji v kod koga sledi da je godiste vece
																// od 2000, ekvivalent if funkciji
				.collect(Collectors.toList()); // rezultat vrati u listu kolektor u list
		return euro3Vozila; // vrati euro 3 vozilo godiste >= 2000
	}
	
	//?????
	
	public List<Vozilo> aktivnaVozila(List<Vozilo> vozila){
		return null;
	}

	private int dodeliGodinuProizvodnje() {
//		int godina =(int) (Math.random()*(Konstante.MAX_VOZILO_GODISTE - Konstante.MIN_VOZILO_GODISTE + 1) 
//				+ Konstante.MIN_VOZILO_GODISTE); //vraca slucajnu godinu izmadju 1960-2000

		// za godine smo koristili matematicku funkciju za interval izmedju odredjenih
		// godina min i max
		int godina = slucajanBrojUIntervalu(Konstante.MIN_VOZILO_GODISTE, Konstante.MAX_VOZILO_GODISTE);
		return godina;
	}

	private String slucajnoSlovo() {
		char c = (char) slucajanBrojUIntervalu(SLOVO_A, SLOVO_Z); // interval izmedju 60 i 90 ASCII kod slova
		return String.valueOf(c);
	}

	private int slucajanBrojUIntervalu(int min, int max) { // matematicka funkciju koja generise slucajan broj u
															// intervalu
		return (int) (Math.random() * (max - min) + min);
	}

}
