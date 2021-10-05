package fr.dauphine.javaavance.td1;

import java.util.ArrayList;

public class Ring {
	Point center;
	int radius_min;
	int radius_max;
	
	Ring(Point c,int min, int max){
		c=center;
		radius_min=min;
		radius_max=max;
		
	}
	
	@Override
	public boolean equals(Object o) {
		Ring p = (Ring) o;
		if(p.center==this.center && p.radius_min==radius_min && p.radius_max==radius_max)return true;
		return false;
		
	}

	@Override
	public String toString() {
		return "center : "+center.toString()+","+"raidus min : "+ radius_min+","+"radius_max : "+radius_max;
		
	}
	
	public boolean contain(Point p) {
		Circle c1= new Circle(center,radius_min);
		Circle c2= new Circle(center,radius_max);
		if(c1.contain(p)==false && c2.contain(p)==true)return true;
		return false;
	}
	
	boolean contains(Point p, ArrayList<Ring> RS ) {
		for (Ring r : RS) {
			if(r.contain(p))return true;
		}
		return false;
	}
}
