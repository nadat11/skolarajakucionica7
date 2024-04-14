package com.skolarajak;

import java.util.Date;
import java.util.List;

import com.skolarajak.model.Vozilo;
import com.skolarajak.servisi.AdministriranjeVozila;

public class App {
	public static void main(String[] args) {
		Date datum = new Date();
		System.out.println("Pocetak rada aplikacije: " + datum.toString());

		// generisi vozila
		AdministriranjeVozila administracijaVozila = new AdministriranjeVozila();
		List<Vozilo> vozila = administracijaVozila.generisi();
		System.out.println(vozila.size());
		izlistajVozila(vozila);
		System.out.println("--------------------------------------------------");
		
		// izdvoj euro 3 vozila
		List<Vozilo> euro3Vozila = administracijaVozila.euro3Vozila(vozila); // izdvoji euro3 vozila
		System.out.println(euro3Vozila.size());
		izlistajVozila(euro3Vozila);
		System.out.println("--------------------------------------------------");
		

		
		// izdvoj aktivna vozila

		List<Vozilo> aktivnaVozila = administracijaVozila.aktivnaVozila(vozila); // izdvoji euro3 vozila
		System.out.println(aktivnaVozila.size());
		izlistajVozila(aktivnaVozila);
	}

	// one metod to rull them all :)
	private static void izlistajVozila(List<Vozilo> vozila) { // pripada static void main metodi zato i ona mora biti
																// staticka
	/*	for (Vozilo vozilo : vozila) {
			printVozilo(vozilo);
		}*/ 
		
		vozila.forEach(App::printVozilo); // kroz listu stampamo za svaki element klase App, lambda izraz ali 
	                                      // ima samo jedan ulazni parametar u metodi printVozilo
									      // metoda mora da ima samo jedan parametar da bi ovaj izraz radio za svaki elemenat
	}

	private static void printVozilo(Vozilo vozilo) {
		System.out.println("Godiste: " + vozilo.getGodisteProizvodnje() + " Aktivno: " + vozilo.isAktivno()
				+ " Registarski broj: " + vozilo.getRegistarskiBroj());
	}
}
