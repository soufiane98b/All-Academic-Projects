package td5;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class Garage {
	private ArrayList<Vehicule> myG = new ArrayList<>();
	private static int cmpt=0;
	private final int id=cmpt;
	
	
	Garage(){
		cmpt++;
	}
	
	public void add(Vehicule c) {
		Objects.requireNonNull(c, "la voiture doit etre non nulle ");
		myG.add(c);
		Collections.sort(this.myG,Vehicule.ComparatorName);
		Collections.sort(this.myG,Vehicule.ComparatorValue);
	}
	
	public void AjouterV(Vehicule c){
		Objects.requireNonNull(c, "la voiture doit etre non nulle ");
		myG.add(c);
	}
	
	
	public int getId() {
		return id;
	}
	public ArrayList<? super Vehicule> getL() {
		return myG;
	}
	
	@Override
	public String toString() {
		StringBuilder str= new StringBuilder();

		str.append("vous etes dans le garage numéro "+id+" et voici ses vehicules: \n");
		for(int i=0;i<myG.size();i++) {
			int j=i+1;
			if(myG.get(i) instanceof Car)str.append("Voiture "+": "+myG.get(i).toString()+"\n");
			if(myG.get(i) instanceof Bike)str.append("Bike "+": "+myG.get(i).toString()+"\n");
		}
		return str.toString();
		
	}
	
	public int ValeurG() {
		int S= 0;
		for(int i=0;i<myG.size();i++) {
			S=(int) (S+((Car) myG.get(i)).getValue());

		}
		return S;
	}
	
	public Car firstCarByBrand(String brand) {
		for(int i=0;i<myG.size();i++) {
			if (((Car) myG.get(i)).getBrand().equals(brand))return (Car) myG.get(i);

		}
		return null;
	}
	
	public void remove(Car c) {
		myG.remove(c);
		
	}
	void protectionism(String brand) {
		for(int i=0;i<myG.size();i++) {
			if((( (Vehicule) myG.get(i)).getBrand().equals(brand))) { 
				myG.remove(i);
				i--;
			}
		}
		
	}
	
	@Override
	public boolean equals(Object c) {
		if (!(c instanceof Garage)) {
		    return false;
		  }
		Garage g = (Garage) c;
		return g.getL().equals(myG);
	}
	
	
	
	public static void main(String [] args) {
		
		try {
			Car a = new Car("Audi",1000);
			Bike b = new Bike("Audi");
			Car c = new Car("BMW",9000);
			Garage g = new Garage();
			g.add(a);
			g.add(b);
			g.add(c);
			System.out.println(g.toString());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
