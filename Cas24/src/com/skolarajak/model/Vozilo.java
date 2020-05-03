package com.skolarajak.model;

import com.skolarajak.utils.PrikazUtils;

public class Vozilo {
	private Integer godisteProizvodnje;
	private boolean aktivno;
	private String registarskiBroj;
	private Vlasnik vlasnik;
	
	public Vozilo() {
		this.godisteProizvodnje = 0;
	}

	public void setGodisteProizvodnje(Integer godisteProizvodnje) {
		this.godisteProizvodnje = godisteProizvodnje;
	}

	public Vlasnik getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(Vlasnik vlasnik) {
		this.vlasnik = vlasnik;
	}

	
	public Vozilo(Integer godisteProizvodnje) {
		this.godisteProizvodnje = godisteProizvodnje;
	}

	public Integer getGodisteProizvodnje() {
		return godisteProizvodnje;
	}

	public boolean isAktivno() {
		return aktivno;
	}

	public void setAktivno(boolean aktivno) {
		this.aktivno = aktivno;
	}

	public String getRegistarskiBroj() {
		return registarskiBroj;
	}

	public void setRegistarskiBroj(String registarskiBroj) {
		this.registarskiBroj = registarskiBroj;
	}

	@Override
	public String toString() {
		return "Godiste: " + this.getGodisteProizvodnje() + " Aktivno: " + this.isAktivno() + " Registarski broj: "
				+ this.getRegistarskiBroj() + " Ime vlasnika: " + this.vlasnik.getIme();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aktivno ? 1231 : 1237);
		result = prime * result + godisteProizvodnje;
		result = prime * result + ((registarskiBroj == null) ? 0 : registarskiBroj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vozilo other = (Vozilo) obj;
		if (aktivno != other.aktivno)
			return false;
		if (godisteProizvodnje != other.godisteProizvodnje)
			return false;
		if (registarskiBroj == null) {
			if (other.registarskiBroj != null)
				return false;
		} else if (!registarskiBroj.equals(other.registarskiBroj))
			return false;
		return true;
	}

	public String toCSV() {
		return this.getGodisteProizvodnje() + PrikazUtils.SEPARATOR + this.isAktivno() + PrikazUtils.SEPARATOR
				+ this.vlasnik.getIme();
	}
}
