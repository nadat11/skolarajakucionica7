package com.skolarajak.model;

public class Vozilo {
	private final int godisteProizvodnje;

	private boolean aktivno;
	private String registarskiBroj;
	
	private Vozilo() {
		this.godisteProizvodnje=0;
	}
	
	public Vozilo(int godisteProizvodnje) {
		this.godisteProizvodnje=godisteProizvodnje;
	}
	public int getGodisteProizvodnje() {
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
		return "Vozilo [godisteProizvodnje=" + godisteProizvodnje + ", aktivno=" + aktivno + ", registarskiBroj="
				+ registarskiBroj + "]";
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
	
}
