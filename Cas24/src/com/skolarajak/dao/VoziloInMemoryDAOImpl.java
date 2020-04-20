package com.skolarajak.dao;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vozilo;
import com.skolarajak.utils.RandomUtils;

public class VoziloInMemoryDAOImpl implements VoziloDAO {  
	private static final HashMap<String, Vozilo> registrovanaVozila = new HashMap<String, Vozilo>(); 

	@Override
	public Vozilo create(Vozilo vozilo) {
		String registarskiBroj = kreirajRegistarskiBroj();
		vozilo.setRegistarskiBroj(registarskiBroj);
		registrovanaVozila.put(vozilo.getRegistarskiBroj(), vozilo);
		return vozilo;
	}

	@Override
	public Vozilo read(String registarskiBroj) throws ResultNotFoundException {
		Vozilo vozilo = registrovanaVozila.get(registarskiBroj); 
		if (vozilo == null) { 
			throw new ResultNotFoundException("Objekat nije prodnadjen");
		}
		return vozilo; 
	}

	@Override
	public Vozilo update(Vozilo vozilo) {
		registrovanaVozila.put(vozilo.getRegistarskiBroj(), vozilo); 
	
		return vozilo;
	}

	@Override
	public void delete(String registarskiBroj) {
		registrovanaVozila.remove(registarskiBroj);
	}

	private String kreirajRegistarskiBroj() {

		String registarskiBroj = ""; 
		while (1 == 1) { 
			registarskiBroj = "Reg-" + RandomUtils.slucajnoSlovo() + RandomUtils.slucajnoSlovo(); 																									
			if (!VoziloInMemoryDAOImpl.registrovanaVozila.containsKey(registarskiBroj)) { 
				VoziloInMemoryDAOImpl.registrovanaVozila.put(registarskiBroj, null); 
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

	@Override
	public List<Vozilo> getAll() throws ResultNotFoundException { 
		return VoziloInMemoryDAOImpl.registrovanaVozila.values() 
				.stream().collect(Collectors.toList()); 
	}

	@Override
	public List<Vozilo> getEuro3Vozila() {
		// TODO Auto-generated method stub
		return VoziloInMemoryDAOImpl.registrovanaVozila.values().stream() // stream sadrzi listu vozila
				.filter(v -> v.getGodisteProizvodnje() >= 2000).collect(Collectors.toList());
	}

	@Override
	public List<Vozilo> getAktivnaVozila() {
		// TODO Auto-generated method stub
		return VoziloInMemoryDAOImpl.registrovanaVozila.values().stream() 
				.filter(v -> v.isAktivno()).collect(Collectors.toList());
	}

}
