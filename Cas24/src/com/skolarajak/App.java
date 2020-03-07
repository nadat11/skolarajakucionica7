package com.skolarajak;

import java.util.Date;
import java.util.List;

import com.skolarajak.model.Vozilo;
import com.skolarajak.servisi.AdministriranjeVozila;

public class App {

	public static void main(String[] args) {
		Date datum = new Date();
		System.out.println("Pocetak rada aplikacije: "+ datum.toString());
		AdministriranjeVozila generisiVozila = new AdministriranjeVozila();
		List<Vozilo> vozila = generisiVozila.generisi();
		System.out.println(vozila.size());
		for (Vozilo vozilo : vozila) {

			System.out.println("Godiste: " + vozilo.getGodisteProizvodnje() + " Aktivno: " + vozilo.isAktivno()
					+ " Registarski broj: " + vozilo.getRegistarskiBroj());

		}
	}
}
