package fr.dauphine.javaavance.td2;

public class Sqrt extends Expr{
	Value val ;
	
	Sqrt(Expr v1){
		val = new Value(Math.sqrt(v1.eval()));
	}

	@Override
	double eval() {
		return val.r;
	}
}
