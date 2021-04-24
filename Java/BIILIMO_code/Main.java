package BIILIMO;

import java.io.FileNotFoundException;


public class Main {
	
	public static void main(String args[]) {

		Interraction.s.useDelimiter("[\n]+");
		Entrepot E = null;
		try {
			E = Interraction.PrintEtRentrerDonneesInitial();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Interraction.printMenuInterraction();
		int i = Interraction.s.nextInt();
		while(i!=0) {
			E=Interraction.executionMenu(i,E);
			Interraction.printMenuInterraction();
		    i = Interraction.s.nextInt();

		}
		Interraction.s.close();
		System.out.println("Fin du programme, Bonne journee ");

	}
}
