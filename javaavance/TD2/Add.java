package fr.dauphine.javaavance.td2;

public class Add extends Expr{
	Value val ;
	
	
	Add(Expr v1, Expr v2){
		val = new Value(v1.eval()+v2.eval());
	}

	@Override
	double eval() {
		return val.r;
	}

}
