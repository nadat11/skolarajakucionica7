package com.skolarajak.servisi;

import java.util.ArrayList;
import java.util.List;
import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.Konstante;

public class AdministriranjeVozila {
	private static final boolean STATUS=true;
	
	public List<Vozilo> generisi(){
		List<Vozilo> vozila= new ArrayList<Vozilo>();
		for(int i=0; i<Konstante.UKUPAN_BROJ_VOZILA_U_SISTEMU; i++) {
			Vozilo vozilo = new Vozilo(i);
			vozilo.setAktivno(STATUS);
			vozilo.setRegistarskiBroj("Reg-" + i);
			vozila.add(vozilo);
		}
		return vozila;
	}
	public List<Vozilo> euro3Vozila(List<Vozilo> vozila){
		return null;
	}

}
