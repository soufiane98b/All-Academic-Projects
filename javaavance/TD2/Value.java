package fr.dauphine.javaavance.td2;

public class Value extends Expr {
	double r;
	
	Value(double n){
		r=n;
	}
	@Override
	double eval(){
		return this.r;
	}

}
