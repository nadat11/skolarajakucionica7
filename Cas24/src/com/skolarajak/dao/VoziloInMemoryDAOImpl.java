package com.skolarajak.dao;

import java.util.HashMap;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.RandomUtils;

// interfejs vezuje DAO za nivo iznad 

public class VoziloInMemoryDAOImpl implements VoziloInMemoryDAO {
	private static final HashMap<String, Vozilo> registrovanaVozila = new HashMap<String, Vozilo>(); // data storage je hash map

	@Override
	public Vozilo create(Vozilo vozilo) {
		String registarskiBroj = kreirajRegistarskiBroj();
		vozilo.setRegistarskiBroj(registarskiBroj);
		registrovanaVozila.put(vozilo.getRegistarskiBroj(), vozilo);
		return vozilo;
	}

	@Override
	public Vozilo read(String registarskiBroj) throws ResultNotFoundException {
		Vozilo vozilo = registrovanaVozila.get(registarskiBroj); // procitali smo vozilo 
		if( vozilo == null) { // proverili smo da li je null izbacili smo izuzetak ili 
			throw new ResultNotFoundException("Objekat nije prodnadjen");
		}
		return vozilo; // smo vratili vozilo ako nije null
	}

	@Override
	public Vozilo update(Vozilo vozilo) {
		registrovanaVozila.put(vozilo.getRegistarskiBroj(), vozilo); // upis u bazu
		// vozilo = read(vozilo.getRegistarskiBroj()); //ako bi imali bazu, polja koja su promenjena citao bi DAO i moramo da doadamo read, ne objekta vec polja
		return vozilo;
	}

	@Override
	public void delete(String registarskiBroj) {
		registrovanaVozila.remove(registarskiBroj);
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
