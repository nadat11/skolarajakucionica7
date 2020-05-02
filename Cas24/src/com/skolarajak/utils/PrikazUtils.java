package com.skolarajak.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import com.skolarajak.model.Vlasnik;
import com.skolarajak.model.Vozilo;

public class PrikazUtils {
	private static String FILE_ROOT = "c:/tmp/izvestaj.txt";
	public static String SEPARATOR = ";";
	public static void izlistajVozila(List<Vozilo> vozila) {
		vozila.forEach(PrikazUtils::printVozilo);
	}

	public static void izlistajVozilaUDatoteku(List<Vozilo> vozila) throws IOException {
		Path path =Paths.get(FILE_ROOT);
		try (BufferedWriter writer= Files.newBufferedWriter(path /* StandardOpenOption.APPEND*/)){ //newBufferedWriter opcija append dodaje u file ne stvara se novi 
			writer.write("godina" + PrikazUtils.SEPARATOR + "status" + PrikazUtils.SEPARATOR + "reg" + PrikazUtils.SEPARATOR + "ime" );
			writer.newLine();
			for(Vozilo v : vozila) {
				  writer.write(v.toCSV());//"\r\n");
				  writer.newLine();//toCSV i generisan file import u Excel
			  }
		}
	}
	public static void izlistajVozilaIzDatoteke() throws IOException { //citanje iz file-a
		Path path =Paths.get(FILE_ROOT);
		String thisLine = null;
		try (BufferedReader reader= Files.newBufferedReader(path)) // try with resourses
		{ 
			while ((thisLine = reader.readLine()) != null ) { // zatvoren blok try readLine cita i setuje thihLine proverava da li je prva linija null (null je na kraju)
				System.out.println("*****" + thisLine); //nakon citanja prve linije,kursor je na drugoj liniji, ispisujemo prvu liniju, ulazimo u while petlju
			}
			
		}
	}
	public static void izlistajVlasnici(List<Vlasnik> vlasnici) {
		vlasnici.forEach(PrikazUtils::printVlasnik);
	}

	public static void izlistajVlasnikeUDatoteku(List<Vlasnik> vlasnici) throws IOException  {
		/*
		 * Get the file name reference 
		 * Path path =Paths.get("c:/output.txt");
		 * 
		 * Use try-with-resourse to get auto-closeable writer instance 
		 * try (BufferedWriter writer = Files.newBufferedWriter(path)){
		 * writer.write("Hello World!!"); } }
		 */
		Path path =Paths.get(FILE_ROOT);
		try (BufferedWriter writer= Files.newBufferedWriter(path)){
			  for(Vlasnik v : vlasnici) {
				  writer.write(v.toString()+"\r\n");
				  writer.newLine();//  writer.write(v.toString()+"\r\n");   // umesto to String menja se u toCSV
				  //getBrojVozackeDozvole() + SEPARATOR+ v.getIme());
				//  writer.newLine(); // line separator
			  };//.write(vlasnici.); 
	}
}
	public static void printVozilo(Vozilo vozilo) {
		System.out.println(vozilo.toString());
	}

	public static void printVlasnik(Vlasnik vlasnik) {
		System.out.println(vlasnik.toString());
	}
}
