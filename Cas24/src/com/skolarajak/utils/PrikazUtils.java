package com.skolarajak.utils;

import java.util.List;
import com.skolarajak.model.Vlasnik;
import com.skolarajak.model.Vozilo;

public class PrikazUtils {
	public static void izlistajVozila(List<Vozilo> vozila) { 
		vozila.forEach(PrikazUtils::printVozilo); 
	}
	
	public static void izlistajVlasnici(List<Vlasnik> vlasnici) { 
		vlasnici.forEach(PrikazUtils::printVlasnik); 
	}
	
	public static void printVozilo(Vozilo vozilo) {
		System.out.println(vozilo.toString());
	}
	
	public static void printVlasnik(Vlasnik vlasnik) {
		System.out.println(vlasnik.toString());
}
}
