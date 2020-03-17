package com.skolarajak.dao;

import com.skolarajak.exceptions.dao.ResultNotFoundException;
import com.skolarajak.model.Vozilo;

public interface VoziloInMemoryDAO {
	Vozilo create(Vozilo vozilo);

	Vozilo read(String registarskiBroj) throws ResultNotFoundException;

	Vozilo update(Vozilo vozilo);

	void delete(String registarskiBroj);

	long count();
}
