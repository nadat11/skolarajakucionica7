package com.skolarajak.utils;
// tehnicke metode 

public class RandomUtils {
	private static final int SLOVO_A = 65;
	private static final int SLOVO_Z = 90;

	public static String slucajnoSlovo() {
		char c = (char) slucajanBrojUIntervalu(SLOVO_A, SLOVO_Z); // interval izmedju 60 i 90 ASCII kod slova
		return String.valueOf(c);
	}

	public static int slucajanBrojUIntervalu(int min, int max) { // matematicka funkciju koja generise slucajan broj u
		// intervalu
		return (int) (Math.random() * (max - min) + min);
	}
}
