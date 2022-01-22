package Projet_IA;

import java.util.ArrayList;
import java.util.Collections;
public class Astar {
	State state_int;
	ArrayList<State> explored = new ArrayList<State>();
	ArrayList<State> path ; // le meilleur circuit qui passe par toutes les villes et revient à la ville initiale
	SetVille meilleur_chemin = new SetVille();
	ArrayList<State> frontier= new ArrayList<State>() ; // no_explored
	ArrayList<Integer> frontiers_size = new ArrayList<Integer>(); // list contain the grow of the frontier size during execution
	
	double cout_distance=-1;
	
	
	
	Astar (State init) throws CloneNotSupportedException{
		state_int =(State) init.clone();
		state_int.notVisited.Set.remove(init.current);
	}
	
	Astar (Ville c,SetVille nv,State f) throws CloneNotSupportedException{
		SetVille clone = (SetVille) nv.clone();
		state_int = new State(c, clone, f,c);
		state_int.notVisited.Set.remove(state_int.current);
		
		
	}
	// on part de l'état initiale ou la ville départ est compté comme non visité du coup elle est marqué
	// ensuite nous générons stockons dans une liste tous les etats prochain( cad ville non visités)
	// et nous selectionons l etat avec min distance plus heuristique dand tous les états non visités ie no_explored
	// et ensuite nous nous arretons que nous nous retrouvons dans la ville initiale 
	// heuristique est calculé par rapport à l'etat grace à algo de prim
	void f_Astar() throws Exception{
		frontier.addAll(state_int.expand());
		int cmpt=0;
		while(true) {
			cmpt++;
			//if(cmpt%1000==0)System.out.println("max frontier actuelle : "+frontier.size());
			//System.out.println("taille Frontier :" +frontier.size()+", Set Frontiere :"+frontier);
			frontiers_size.add(frontier.size());
			State min_no_explored = State.best(frontier);
			//System.out.println(min_no_explored);
			explored.add(min_no_explored);
			//View v = new View(explored);
			if(min_no_explored.current.Name.equals(state_int.current.Name))break; // on est revenu à la ville initiale donc on arrette
			///
			
			for (State s : min_no_explored.expand()) {
				if(!explored.contains(s)) {frontier.add(s);}
			}
			
			//frontier.addAll(min_no_explored.expand()); // le meilleur de la frontier
			frontier.remove(min_no_explored);// on le marque donc enleve de la liste 
			
		}
		System.out.println("Voici la croissance de la taille de la frontière pendant l'exécution :\n");
		System.out.println(frontiers_size);
		System.out.println("\n        ---Voici la taille maximal de la frontier de A* ---     ");
		System.out.println(Collections.max(frontiers_size));
		
		

	}

	void  path_and_cout() {
		ArrayList<State> inverse_path= new ArrayList<State>();
		State tmp = explored.get(explored.size()-1);
		if(tmp!=null) cout_distance = tmp.cout_distance;
		while(tmp!= null) {
			inverse_path.add(tmp);
			tmp=tmp.father;
		}
		// on inverse la liste pour chemin dans le bon ordre, pour l'affichage
		ArrayList<State> result = new ArrayList<State>();
		for(int i=inverse_path.size()-1; i>=0; i--) {
		    result.add(inverse_path.get(i));
		    meilleur_chemin.Set.add(inverse_path.get(i).current);
		}
		
		path= result; 

	}
	
	
	
	
	
	
	
	

}
