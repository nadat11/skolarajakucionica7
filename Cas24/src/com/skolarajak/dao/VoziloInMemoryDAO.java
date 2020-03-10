package com.skolarajak.dao;

import com.skolarajak.model.Vozilo;

public interface VoziloInMemoryDAO {
	Vozilo create(Vozilo vozilo);

	Vozilo read(String registarskiBroj);

	Vozilo update(Vozilo vozilo);

	void delete(String registarskiBroj);

	long count();
}
