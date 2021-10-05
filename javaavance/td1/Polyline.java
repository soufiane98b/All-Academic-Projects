package fr.dauphine.javaavance.td1;

import java.util.ArrayList;

public class Polyline {
	private ArrayList<Point> points;
	private int max_p=0;
	
	Polyline(int max){
		points = new ArrayList<Point>();
		
	}
	
	void Add(Point p){
		if (points.size()<max_p) {points.add(p);}
		
	}
	
	int pointCapacity() {
		return max_p;
	}
	
	int nbPoints() {
		return points.size();
	}
	
	boolean contains(Point ps) {
		for (Point p : points) {
			if(ps.isSameAs(p))return true;
			
		}
		return false;
		
	}
	
	
	
}
