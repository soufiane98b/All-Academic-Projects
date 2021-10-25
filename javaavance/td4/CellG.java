package td4;

public class CellG<E> {
	E s;
	CellG<E> suivant=null;
	
	CellG(E s, CellG<E> suivant){
		this.s=s;
		this.suivant = suivant;
		
	} 

}

