package com.skolarajak.dao;

import java.util.HashMap;

import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.RandomUtils;

// interfejs vezuje DAO za nivo iznad 

public class VoziloInMemoryDAOImpl implements VoziloInMemoryDAO {
	private static final HashMap<String, Vozilo> registrovanaVozila = new HashMap<String, Vozilo>();

	@Override
	public Vozilo create(Vozilo vozilo) {
		String registarskiBroj = kreirajRegistarskiBroj();
		vozilo.setRegistarskiBroj(registarskiBroj);
		registrovanaVozila.put(vozilo.getRegistarskiBroj(), vozilo);
		return vozilo;
	}

	@Override
	public Vozilo read(String registarskiBroj) {
		return registrovanaVozila.get(registarskiBroj);
	}

	@Override
	public Vozilo update(Vozilo vozilo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String registarskiBroj) {
		// TODO Auto-generated method stub

	}

	private String kreirajRegistarskiBroj() {

		String registarskiBroj = ""; // deklaracija iza petlje da bi se uradio return statment

		while (1 == 1) { // 2 varijanta beskrajna petlja iz koje se izlazi ako nadjemo jedinstven broj
			registarskiBroj = "Reg-" + RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo(); // kreiramo
																									// registarski broj
			if (!VoziloInMemoryDAOImpl.registrovanaVozila.containsKey(registarskiBroj)) { // proverimo da li se ne
																							// nalazi u mapi
				VoziloInMemoryDAOImpl.registrovanaVozila.put(registarskiBroj, null); // ako nije u mapi dodajemo ga u
																						// mapu
				break;
			} else {
				System.out.println("*************DUPLICAT**************" + registarskiBroj);
			}
		}
		return registarskiBroj;
	}

	@Override
	public long count() {
		return VoziloInMemoryDAOImpl.registrovanaVozila.keySet().size();
	}

}
