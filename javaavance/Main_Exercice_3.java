package td4;

import java.util.HashMap;

import fr.dauphine.javaavance.td1.Circle;
import fr.dauphine.javaavance.td1.Point;


public class Main_Exercice_3 {
	
	public static void main(String [] args) {
		
	
		HashMap<Point, Circle> m = new HashMap<>();
		Point p1 = new Point(100,150);
		Circle c1 = new Circle(p1,100);
		m.put(p1, c1);
		System.out.println(m.containsKey(p1));
		System.out.println(m.containsKey(new Point(1,2)));
	
		
	}
		

}
