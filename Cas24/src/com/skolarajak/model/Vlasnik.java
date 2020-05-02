package com.skolarajak.model;

import com.skolarajak.utils.PrikazUtils;

public class Vlasnik {
	
	private String ime;
	private String prezime;
	private String brojVozackeDozvole;
	private  Vozilo vozilo;

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getBrojVozackeDozvole() {
		return brojVozackeDozvole;
	}

	public void setBrojVozackeDozvole(String brojVozackeDozvole) {
		this.brojVozackeDozvole = brojVozackeDozvole;
	}

	public Vozilo getVozilo() {
		return vozilo;
	}

	public void setVozilo(Vozilo vozilo) {
		this.vozilo = vozilo;
	}

public String toString() {
		return "Ime: " + this.getIme() 
		+  " Prezime: " + this.getPrezime()
		 +  " Broj dozovole: " + this.getBrojVozackeDozvole() 
           +     " Reg broj vozila " + this.vozilo.getRegistarskiBroj()
              +  " Status vozila: " +this.vozilo.isAktivno();
}
	public String toCSV() {
		return this.getIme() + PrikazUtils.SEPARATOR
				+ this.getPrezime() + PrikazUtils.SEPARATOR
				+ this.getBrojVozackeDozvole() + PrikazUtils.SEPARATOR
				+ this.vozilo.getRegistarskiBroj() + PrikazUtils.SEPARATOR
				+ this.vozilo.isAktivno();
	}
}
