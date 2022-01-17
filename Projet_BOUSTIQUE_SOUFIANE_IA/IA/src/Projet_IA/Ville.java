package Projet_IA;

public class Ville {
	
	public double x;
	public double y;
	public String Name;
	
	Ville(double x,double y , String Name){
		this.x=x;
		this.y=y;
		this.Name=Name;
		
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Ville(this.x,this.y,this.Name);
	}
	
	@Override
	public String toString() {
		return "{"+Name+":"+"("+x+","+y+")"+"}";
	}
	
	double distance(Ville v){
		return Math.sqrt( (v.x-this.x)*(v.x-this.x) +   (v.y-this.y)*(v.y-this.y) );
		
	}
	
	@Override
	public boolean equals(Object O) {    
		Ville v = (Ville) O;
		if(v.x==this.x  && v.y==this.y && v.Name.equals(this.Name)) return true;
		return false;
	}
	

	

}