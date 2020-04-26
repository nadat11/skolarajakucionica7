package com.skolarajak.dao;

import java.util.List;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vlasnik;

public interface VlasnikDAO {
	Vlasnik create(Vlasnik vlasnik);

	Vlasnik read(String brojVozackeDozvole) throws ResultNotFoundException;

	Vlasnik update(Vlasnik vlasnik);

	void delete(String brojVozackeDozvole);

	List<Vlasnik> getAll() throws ResultNotFoundException;
	long count();
	
	List<Vlasnik> getAllVlasniciAktivnihVozila() throws ResultNotFoundException; 
}
