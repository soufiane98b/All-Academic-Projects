package Projet_IA;

import java.util.ArrayList;


public class SetVille {
	public ArrayList<Ville> Set = new ArrayList<>();
	
	SetVille(){}
	
	@Override
	public String toString() {
		String s ="{ ";
		for (Ville v : Set ) {
			if(Set.indexOf(v)!=(Set.size()-1))s=s+v+" , ";
			else{s=s+v;}
			
		}
		return s+" }";
			
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		SetVille clone = new SetVille();
		clone.Set = new ArrayList<>(this.Set);
		return clone;
	}
	
}


