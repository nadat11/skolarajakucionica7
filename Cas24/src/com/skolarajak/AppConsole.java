package com.skolarajak;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vozilo;
import com.skolarajak.servisi.AdministriranjeVozila;

public class AppConsole {
	static final AdministriranjeVozila administracijaVozila = new AdministriranjeVozila(); 
	public static void main(String[] args) throws ResultNotFoundException {
		Date datum = new Date();
		System.out.println("Pocetak rada aplikacije: " + datum.toString());

		// generisi vozila

		 administracijaVozila.generisi(); // generisi sluzi za inicijalizaciju 

		System.out.println("----------------Glavna programska petlja-------------------");

		Scanner in = new Scanner(System.in);
		while (1 == 1) { // obicno je glavna petlja korisnickog interfejsa bezkonacna, petlja drzi
							// aplikaciju zivom, neki tread mora da drzi app zivom
			prikaziOpcije();
			
			String s = in.nextLine();
			System.out.println("You entered a string " + s);
			System.out.println(">:");
			switch (s) {
			case "0":
				opcija0();
				break;
			case "1":
				opcija1();
				break;
			case "2":
				opcija2();
				break;
			}
			if ("kraj".equals(s)) {
				System.out.println("Kraj rada, hvala!");
				break; // iz while petlje
			}
		}
	} 

	private static void opcija0() throws ResultNotFoundException{
		List<Vozilo> vozila = administracijaVozila.dajSvaVozila();
		System.out.println("========IZLISTAJ VOZILA=========");
		System.out.println("Ukupno vozila: " + vozila.size());
		izlistajVozila(vozila);
	}

	private static void opcija1() {
		// izdvoj euro 3 vozila
		System.out.println("========IZLISTAJ EURO 3 VOZILA=========");
		List<Vozilo> euro3Vozila = administracijaVozila.euro3Vozila(); // persistovali smo data storage na nivou aplikacije sto ne valja!!!!!
		System.out.println(euro3Vozila.size());                   
		izlistajVozila(euro3Vozila);
		System.out.println("--------------------------------------------------");
	}

	private static void opcija2() {
		// izdvoj aktivna vozila
		System.out.println("========IZLISTAJ AKTIVNA VOZILA=========");
		List<Vozilo> aktivnaVozila = administracijaVozila.aktivnaVozila(); // izdvoji euro3 vozila
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

	private static void izlistajVozila(List<Vozilo> vozila) { 
		vozila.forEach(AppConsole::printVozilo); 
	}

	private static void printVozilo(Vozilo vozilo) {
		System.out.println("Godiste: " + vozilo.getGodisteProizvodnje() + " Aktivno: " + vozilo.isAktivno()
				+ " Registarski broj: " + vozilo.getRegistarskiBroj());
	}
}
