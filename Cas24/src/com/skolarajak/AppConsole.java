package com.skolarajak;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vlasnik;
import com.skolarajak.model.Vozilo;
import com.skolarajak.servisi.AdministriranjeVozila;
import com.skolarajak.utils.PrikazUtils;

public class AppConsole {
	static final AdministriranjeVozila administracijaVozila = new AdministriranjeVozila();

	public static void main(String[] args) throws ResultNotFoundException, IOException {
		Date datum = new Date();
		System.out.println("Pocetak rada aplikacije: " + datum.toString());

		administracijaVozila.generisi();

		System.out.println("----------------Glavna programska petlja-------------------");

		Scanner in = new Scanner(System.in);
		while (1 == 1) {
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
			case "3":
				opcija3();
				break;
			case "4":
				opcija4();
				break;
			case "5":
				opcija5();
				break;
			}
			if ("kraj".equals(s)) {
				System.out.println("Kraj rada, hvala!");
				break;
			}
		}
	}

	private static void opcija0() throws ResultNotFoundException {
		List<Vozilo> vozila = administracijaVozila.dajSvaVozila();
		System.out.println("========IZLISTAJ VOZILA=========");
		System.out.println("Ukupno vozila: " + vozila.size());
		PrikazUtils.izlistajVozila(vozila);
	}

	private static void opcija1() {
		// izdvoj euro 3 vozila
		System.out.println("========IZLISTAJ EURO 3 VOZILA=========");
		List<Vozilo> euro3Vozila = administracijaVozila.euro3Vozila(); // persistovali smo data storage na nivou
																		// aplikacije sto ne valja!!!!!
		System.out.println(euro3Vozila.size());
		PrikazUtils.izlistajVozila(euro3Vozila);
		System.out.println("--------------------------------------------------");
	}

	private static void opcija2() {
		// izdvoj aktivna vozila
		System.out.println("========IZLISTAJ AKTIVNA VOZILA=========");
		List<Vozilo> aktivnaVozila = administracijaVozila.aktivnaVozila(); // izdvoji euro3 vozila
		System.out.println(aktivnaVozila.size());
		PrikazUtils.izlistajVozila(aktivnaVozila);
	}

	private static void opcija3() throws ResultNotFoundException {
		// izdvoj sve vlasnike
		System.out.println("========IZLISTAJ SVE VLASNIKE=========");
		List<Vlasnik> vlasnici = administracijaVozila.dajSveVlasnike(); 
		System.out.println("Ukupno vlasnika: " + vlasnici.size());
		PrikazUtils.izlistajVlasnici(vlasnici);
	}
	private static void opcija4() throws ResultNotFoundException, IOException { 
		// izdvoj sve vlasnike
		System.out.println("========IZLISTAJ VLASNIKE SVIH AKTIVNIH VOZILA=========");
		List<Vlasnik> vlasnici = administracijaVozila.dajSveVlasnikeAktivnihVozila(); 
		PrikazUtils.izlistajVlasnici(vlasnici);
		PrikazUtils.izlistajVlasnikeUDatoteku(vlasnici);
	}
	private static void opcija5() throws ResultNotFoundException, IOException {
		// izdvoj sve vlasnike
		System.out.println("========IZLISTAJ SVA VOZILA CIJI VLASNICI IMAJU SLOVO A U IMENU=========");
		List<Vozilo> vozilo = administracijaVozila.dajSvaVozilaCijeImeVlasnikaSadrziSlovoA(); 
		System.out.println(vozilo.size());
		PrikazUtils.izlistajVozila(vozilo);
		PrikazUtils.izlistajVozilaUDatoteku(vozilo);
		PrikazUtils.izlistajVozilaIzDatoteke();
	}

	private static void prikaziOpcije() { // rad sa app iz konzole
		System.out.println("--------------------------------------------------");
		System.out.println("0 -> Izlistaj vozila");
		System.out.println("1 -> Izlistaj euro 3 vozila");
		System.out.println("2 -> Izlistaj aktivna vozila");
		System.out.println("3 -> Izlistaj sve vlasnike");
		System.out.println("4 -> Izlistaj vlasnike svih aktivnih vozila");
		System.out.println("5 -> Izlistaj vozila svih vlasnika cije ime sadrzi slovo A");
		System.out.println("kraj -> izlaz iz aplikacije");
		System.out.println("--------------------------------------------------");
	}

}