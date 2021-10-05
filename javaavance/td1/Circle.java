package fr.dauphine.javaavance.td1;
import java.lang.Math;
import java.util.ArrayList;

public class Circle {
	Point center;
	int radius;
	
	Circle(int x, int y,int r){
		center = new Point(x,y);
		radius=r;
	}
	Circle (Point c, int r){
		center =c;
		radius=r;
	}
	
	@Override
	public String toString() {
		return "center : "+center.toString()+","+"raidus : "+ radius+","+"area : "+area();
		
	}
	
	void translate(int dx, int dy){
		center.translate(dx, dy);
	}
	
	double area() {
		return 3.14*radius*radius;
	}
	
	boolean contain(Point p) {
		if (Math.pow((p.x-center.x),2) + Math.pow((p.y-center.y),2) <= Math.pow(radius,2))return true;
		
		return false;
	}
	
	boolean contains(Point p, ArrayList<Circle> CS ) {
		for (Circle c : CS) {
			if(c.contain(p))return true;
		}
		
		return false;
	}
	
		
	

}
