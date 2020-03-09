package com.skolarajak.servisi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.Konstante;

public class AdministriranjeVozila {
	private static final boolean STATUS = true;
	private static final double PRAG_RASPODELE_AKTIVNIH_VOZILA = (double) 0.4; // kontrolisemo da li hocemo da imamo
	private static final int SLOVO_A = 65; // vise aktivnih i neaktivnih
	private static final int SLOVO_Z = 90;

	private static final HashMap<String, String> registarskiBrojevi = new HashMap<String, String>();

	public List<Vozilo> generisi() {
		List<Vozilo> vozila = new ArrayList<Vozilo>();
		for (int i = 0; i < Konstante.UKUPAN_BROJ_VOZILA_U_SISTEMU; i++) {
			int godinaProizvodnje = dodeliGodinuProizvodnje();
			Vozilo vozilo = new Vozilo(godinaProizvodnje); // i promenjeno u godinaProizvodnje, da bi konstante iz
															// metode radile
			vozilo.setAktivno(Math.random() > PRAG_RASPODELE_AKTIVNIH_VOZILA); // logicki izraz, ako je veca od 0.5 bice
																				// true, ako je manje jednako od 0.5
																				// false
			vozilo.setRegistarskiBroj(kreirajRegistarskiBroj());
			vozila.add(vozilo);
		}
		System.out.println("Ukupno registarskih brojeva: " + registarskiBrojevi.keySet().size()); // koliko ima
																									// registarskih
																									// brojeva u
																									// HashMapi
		return vozila;
	}

	public List<Vozilo> euro3Vozila(List<Vozilo> vozila) { // pocetna lista sadrzi sva vozila
		/*
		 * List<Vozilo> euro3Vozila = new ArrayList<Vozilo>(); // filtrirana privremena
		 * lista rezultata samo euro 3 vozila for(Vozilo vozilo : vozila) { // proveri
		 * elemente u listi if(vozilo.getGodisteProizvodnje() >=
		 * Konstante.EURO_3_GODISTE) { // ako odgovara uslovu euro3Vozila.add(vozilo);
		 * //dodaje se u privremenu listu euro 3 vozila } }
		 */

		// Java 8 lambda izraz
		List<Vozilo> euro3Vozila = vozila.stream() // stream sadrzi listu vozila
				.filter(v -> v.getGodisteProizvodnje() >= 2000) // filterisi v element liste po kriterijumu za svaki
																// element postoji v kod koga sledi da je godiste vece
																// od 2000, ekvivalent if funkciji
				.collect(Collectors.toList()); // rezultat vrati u listu kolektor u list
		return euro3Vozila; // vrati euro 3 vozilo godiste >= 2000
	}

	// ?????

	public List<Vozilo> aktivnaVozila(List<Vozilo> vozila) {
//		List<Vozilo> aktivnaVozila = new ArrayList<Vozilo>();
//		for (Vozilo vozilo : vozila) {
//			if (vozilo.isAktivno()) {
//				aktivnaVozila.add(vozilo);
//			}
//		}
//		return aktivnaVozila;
//	}

		List<Vozilo> aktivnaVozila = vozila.stream().filter(v -> v.isAktivno()).collect(Collectors.toList());
		return aktivnaVozila;
	}

	// kreiranje jedinstvenog registarskog broja sa HashMapom
	private String kreirajRegistarskiBroj() {
//		String registarskiBroj ="Reg-" + slucajnoSlovo() + slucajnoSlovo();
//		if (registarskiBrojevi.containsKey(registarskiBroj)) { // provera uslova da li registarski broj sadrzi key
//			System.out.println("*************DUPLICAT**************" + registarskiBroj); // ispisemo regBr ako nadjemo iste registarske brojeve
//			return kreirajRegistarskiBroj(); // onda ponovo pozivamo metodu u rekurziji
//		}
//		registarskiBrojevi.put(registarskiBroj, registarskiBroj); // ako ne sadrzi kljuc onda je nov kljuc i stavljamo ga u mapu, metoda kada je uspesno uradjena regiBroj je ubacen u mapu
//		return registarskiBroj;

		// boolean isJedinstvenRegBroj = false; // nije jedinstven broj

		String registarskiBroj = ""; // deklaracija iza petlje da bi se uradio return statment

		// while(!isJedinstvenRegBroj) { // ako je jedinstven
		while (1 == 1) { // 2 varijanta beskrajna petlja iz koje se izlazi ako nadjemo jedinstven broj
			registarskiBroj = "Reg-" + slucajnoSlovo() + slucajnoSlovo(); // kreiramo registarski broj
			if (!registarskiBrojevi.containsKey(registarskiBroj)) { // proverimo da li se ne nalazi u mapi
				registarskiBrojevi.put(registarskiBroj, registarskiBroj); // ako nije u mapi dodajemo ga u mapu
				// isJedinstvenRegBroj = true; // umesto break-a
				break;
			} else {
				System.out.println("*************DUPLICAT**************" + registarskiBroj);
			}
		}
		return registarskiBroj;
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
