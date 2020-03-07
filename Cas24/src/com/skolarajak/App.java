package com.skolarajak;

import java.util.List;

import com.skolarajak.model.Vozilo;
import com.skolarajak.servisi.GenerisiVozila;

public class App {

	public static void main(String[] args) {
		GenerisiVozila generisiVozila = new GenerisiVozila();
		List<Vozilo> vozila = generisiVozila.generisi();
		for (Vozilo vozilo : vozila) {

			System.out.println("Godiste: " + vozilo.getGodisteProizvodnje() + " Aktivno: " + vozilo.isAktivno()
					+ " Registarski broj: " + vozilo.getRegistarskiBroj());

		}
	}
}
