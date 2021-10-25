package td4;

public class Cell {
	Object s;
	Cell suivant=null;
	
	Cell(Object s, Cell suivant){
		this.s=s;
		this.suivant = suivant;
		
	}

}