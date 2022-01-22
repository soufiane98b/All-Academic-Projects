package Projet_IA;

import java.util.ArrayList;

public class HillClimbing {
	
	State_circuit init;
	State_circuit best;
	ArrayList<State_circuit> path = new ArrayList<State_circuit> ();
	
	
	
	HillClimbing(SetVille c) throws CloneNotSupportedException{
		init = new State_circuit(c);
	}
	
	// Verstappen est mieux 
	public void fHC() throws CloneNotSupportedException {
		best= new State_circuit (init.current);
		path.add(best);
		while(true) {
			System.out.println(best);
			best.find_voisins();
			SetVille VoisinMin = best.voisin_Min();
			if(State_circuit.cout_d(VoisinMin)<State_circuit.cout_d(best.current)) {
				State_circuit tmp = new State_circuit(VoisinMin);
				path.add(tmp);
				best=tmp;
			}
			else break;
		}
		
	}
	
	
	
	
	
	

}
