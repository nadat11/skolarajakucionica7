package com.skolarajak.dao;

import java.util.List;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vozilo;

public interface VoziloInMemoryDAO {
	Vozilo create(Vozilo vozilo);

	Vozilo read(String registarskiBroj) throws ResultNotFoundException;

	Vozilo update(Vozilo vozilo);

	void delete(String registarskiBroj);
	
	List<Vozilo> getAll() throws ResultNotFoundException; // napravili smo novi matod koji se korisit u servisu, vrati sva vozila iz liste, ili nema nista 
	List<Vozilo> getEuro3Vozila(); // upit
	List<Vozilo> getAktivnaVozila(); // upit
	
	
	long count();
}
