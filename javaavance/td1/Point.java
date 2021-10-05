package fr.dauphine.javaavance.td1;

import java.util.ArrayList;

public class Point {
	 int x ;
	 int y ;
	
	void M1(){
		Point p=new Point(0,0);
		System.out.println(p.x+" "+p.y);
	}
	
	Point(int x, int y){
		x=this.x;
		y=this.y;
	}
	
	Point(Point p2){
		p2.x=this.x;
		p2.y=this.y;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
	boolean isSameAs(Point p){
		if (this.x==p.x && this.y==p.y ) return true;
		return false;
		
	}
	@Override
	public boolean equals(Object o) {
		Point p = (Point) o;
		return isSameAs(p);
		
		
	}
	
	void translate(int dx, int dy){
		x=x+dx;
		y=y+dy;
	}
	

	
	
	public static void main(String args[]) {
		Point p1=new Point(1,2);
		Point p2=p1;
		Point p3=new Point(1,2);
		ArrayList<Point> list = new ArrayList<>();
		list.add(p1);
		System.out.println(list.indexOf(p1));
		System.out.println(list.indexOf(p3));
		
		Polyline pts = new Polyline(5);
		
		pts.Add(p1);
		pts.Add(null);
		System.out.println(pts.nbPoints());
		
	}



}
