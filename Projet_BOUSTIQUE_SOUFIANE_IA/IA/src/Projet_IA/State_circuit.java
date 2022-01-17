package Projet_IA;

import java.util.ArrayList;
import java.util.Collection;

public class State_circuit {
	
	public SetVille current; // ciruit hamiltonien actuelle 
	public ArrayList<SetVille> voisin; //tous les  ciruit hamiltonien voisins (comme défini dans le projet, en remplacant 2 arrettes ) 
	
	State_circuit(SetVille c) throws CloneNotSupportedException{
		current=(SetVille) c.clone();
	}
	
	public void  find_voisins() throws CloneNotSupportedException {
		voisin = new ArrayList<>();
		for(int i=0;i<current.Set.size()-1;i++) {
			for(int j=i+1;j<current.Set.size();j++) {
				SetVille nei = (SetVille) current.clone();
				Ville tmp = current.Set.get(i);
				nei.Set.set(i, nei.Set.get(j));// ou current 
				nei.Set.set(j,tmp);
				voisin.add(nei);
			}
		}
	}
	
	public static double cout_d(SetVille c) {
		double sum=0;
		for(int i=0;i<c.Set.size()-1;i++) {
			sum=sum+c.Set.get(i).distance(c.Set.get(i+1));
		}
		sum = sum + c.Set.get(0).distance(c.Set.get(c.Set.size()-1));
		return sum;
	}
	
	
	public SetVille voisin_Min() throws CloneNotSupportedException {
		double valeur_min = cout_d(current);
		SetVille min_voisin = current;
		for(SetVille v:voisin) {
			double tmp=cout_d(v);
			if(tmp<valeur_min) {
				valeur_min = tmp;
				min_voisin=v;
			}
		}
		return min_voisin;
	}
	
	public String toString() {
		return "Cout : "+cout_d(current)+"  State :"+current.toString();
			
	}
		
}

