package fr.dauphine.javaavance.view;

import java.awt.Dimension;
import java.awt.Graphics;


import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.dauphine.javaavance.td1.Circle;
import fr.dauphine.javaavance.td1.Point;

public class MyDisplay extends JPanel {
	public static void main(String []args){
		JFrame frame = new JFrame("Java Avanc´e - Graphic Display");
		frame.setSize(new Dimension(500,500));		 
		MyDisplay d = new MyDisplay();
	    frame.add(d);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawLine(100, 1000, 1000, -500);
		Circle c = new Circle(100,100,1000);
		g.drawOval(c.Center().abs(), c.Center().ord(), c.Radius(), c.Radius());
	}

}