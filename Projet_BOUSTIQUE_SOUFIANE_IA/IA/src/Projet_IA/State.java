package Projet_IA;

import java.util.ArrayList;

public class State {
	// classe pour A*
	public Ville current;
	public SetVille notVisited;
	public State father;
	public Ville initiale;
	public double cout_distance;
	public double heuristic;
	
	@Override
	public String toString() {
		
		double h;
		try {
			//h = this.heuristic()+cout_distance;
			h= cout_distance;
			if(father !=null) return "[ "+ "current : " +current.Name+" , father :  "+father.current.Name+" , cout_d="+h+" , heuri="+heuristic +" , NO : "+notVisited.toString();
			return  "[ "+ "current : " +current.Name+" , father :  null "+" , cout_d="+h+" , heuri="+heuristic+" , NO : "+notVisited.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	@Override 
	public boolean equals(Object obj) {
		State s = (State) obj;
		
		if (this.current.x == s.current.x && this.notVisited.Set.equals(s.notVisited.Set))return true;
		return false;
	}
	
	
	State(Ville c,SetVille nv,State f,Ville init){
		father=f;
		notVisited=nv;
		current=c;
		cout_distance=0;
		initiale=init;
		
	}
	void Goto(Ville to) {
		notVisited.Set.remove(to);
		cout_distance = cout_distance + this.current.distance(to);		
		current=to;
		
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Ville current_clone = (Ville) current.clone();
		SetVille notVisited_clone = (SetVille) notVisited.clone();
		State sclone = new State(current_clone,notVisited_clone,father,initiale);
		sclone.initiale=initiale;
		sclone.cout_distance=cout_distance;
		return sclone;
		
	}
	
	public ArrayList<State> expand() throws Exception{
		ArrayList<State> succ = new ArrayList<State>();
		for(int i=0;i<notVisited.Set.size();i++) {
			State tmp = (State) this.clone();
			tmp.Goto(notVisited.Set.get(i)); // ne pas rajouter s'il existe déjà dans not visited 
			tmp.father=this;
			tmp.heuristic();
			succ.add(tmp);
		}
		if(notVisited.Set.isEmpty()) {
			State tmp = (State) this.clone();
			tmp.Goto(initiale);
			tmp.father=this;
			tmp.heuristic();
			succ.add(tmp);
		}
		
		return succ;
	}
	
	
	
	public static State best(ArrayList<State> liste) throws Exception {
		if(liste.isEmpty()) return null;
		State min = liste.get(0);
		for (State s : liste) {
			//System.out.println(s.heuristic()+s.cout_distance);
			if( ((s.heuristic+s.cout_distance)) < (min.heuristic+min.cout_distance) ) min=s;
		}
		return min;
	}
	
	
	public static Ville l_min_distance(Ville v ,ArrayList<Ville> liste) throws Exception  {
		if(liste.isEmpty()) throw new Exception("Liste vide");
		Ville min = liste.get(0);
		for(Ville e : liste) {
			if(v.distance(e)<v.distance(min)) min =e;	
		}
		return min;
	}
	
	

	public double heuristic () throws Exception {
		ArrayList<Ville> notv = new ArrayList<Ville>(notVisited.Set);
		ArrayList<Ville> path = new ArrayList<Ville>();
		if(current.Name.equals(initiale.Name)==false)notv.add(initiale);
		path.add(current);
		double sum=0;
		while(notv.isEmpty()==false) {
			Ville min = notv.get(0);
			Ville precedent = path.get(0);
			double valeur_min = min.distance(current);
			for(Ville p : path) {
				Ville tmp = State.l_min_distance(p,notv);
				if(tmp.distance(p)<valeur_min) {
					precedent = p;
					min=tmp;
					valeur_min=tmp.distance(p);
				}	
			}
			path.add(min);
			notv.remove(min);
			sum=sum+min.distance(precedent);
		}
		heuristic = sum;
		return sum;
		
	}
	
	
	
	
	
}
