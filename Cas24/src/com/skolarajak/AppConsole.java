package com.skolarajak;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.skolarajak.model.Vozilo;
import com.skolarajak.servisi.AdministriranjeVozila;

public class AppConsole {
	static final AdministriranjeVozila administracijaVozila = new AdministriranjeVozila(); // pretvoreno u staticku
																							// metodu da bude vidljiva u
																							// privatnim metodama za
																							// euro2 i aktivna vozila i
																							// na nivou je cele
																							// aplikacije kada je
																							// staticna

	public static void main(String[] args) {
		Date datum = new Date();
		System.out.println("Pocetak rada aplikacije: " + datum.toString());

		// generisi vozila

		List<Vozilo> vozila = administracijaVozila.generisi(); // kreirali smo model sa dummy podacima

		System.out.println("----------------Glavna programska petlja-------------------");

		Scanner in = new Scanner(System.in);
		while (1 == 1) { // obicno je glavna petlja korisnickog interfejsa bezkonacna, petlja drzi
							// aplikaciju zivom, neki tread mora da drzi app zivom
			prikaziOpcije();
			System.out.println(">:");
			String s = in.nextLine();
			System.out.println("You entered a string " + s);
			switch (s) {

			case "0":
				opcija0(vozila);
				break;
			case "1":
				opcija1(vozila);
				break;
			case "2":
				opcija2(vozila);
				break;
			}
			if ("kraj".equalsIgnoreCase(s)) {
				System.out.println("Kraj rada, hvala!");
				break; // iz while petlje
			}
		}
	} // izlaz iz main metode

	private static void opcija0(List<Vozilo> vozila) {
		System.out.println("========IZLISTAJ VOZILA=========");
		System.out.println("Ukupno vozila: " + vozila.size());
		izlistajVozila(vozila);
	}

	private static void opcija1(List<Vozilo> vozila) {
		// izdvoj euro 3 vozila
		System.out.println("========IZLISTAJ EURO 3 VOZILA=========");
		List<Vozilo> euro3Vozila = administracijaVozila.euro3Vozila(vozila); // izdvoji euro3 vozila
		System.out.println(euro3Vozila.size());
		izlistajVozila(euro3Vozila);
		System.out.println("--------------------------------------------------");
	}

	private static void opcija2(List<Vozilo> vozila) {
		// izdvoj aktivna vozila
		System.out.println("========IZLISTAJ AKTIVNA VOZILA=========");
		List<Vozilo> aktivnaVozila = administracijaVozila.aktivnaVozila(vozila); // izdvoji euro3 vozila
		System.out.println(aktivnaVozila.size());
		izlistajVozila(aktivnaVozila);
	}

	private static void prikaziOpcije() { // rad sa app iz konzole
		System.out.println("--------------------------------------------------");
		System.out.println("0 -> Izlistaj vozila");
		System.out.println("1 -> Izlistaj euro 3 vozila");
		System.out.println("2 -> Izlistaj aktivna vozila");
		System.out.println("kraj -> izlaz iz aplikacije");
		System.out.println("--------------------------------------------------");
	}

	private static void izlistajVozila(List<Vozilo> vozila) { // pripada static void main metodi zato i ona mora biti
																// staticka
		/*
		 * for (Vozilo vozilo : vozila) { printVozilo(vozilo); }
		 */

		vozila.forEach(AppConsole::printVozilo); // kroz listu stampamo za svaki element klase App, lambda izraz ali
		// ima samo jedan ulazni parametar u metodi printVozilo
		// metoda mora da ima samo jedan parametar da bi ovaj izraz radio za svaki
		// elemenat
	}

	private static void printVozilo(Vozilo vozilo) {
		System.out.println("Godiste: " + vozilo.getGodisteProizvodnje() + " Aktivno: " + vozilo.isAktivno()
				+ " Registarski broj: " + vozilo.getRegistarskiBroj());
	}
}
