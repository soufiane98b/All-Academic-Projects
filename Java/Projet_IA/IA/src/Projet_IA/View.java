package Projet_IA;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class View extends JFrame{
	
	public static SetVille chemin;
	public String name;
	
	public View(SetVille c,String n) {
		name = n;
		chemin=c;
		setSize(980,980);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	}
		
	public void paint(Graphics g) {
		
		for (int i=0;i<chemin.Set.size()-1;i++) {
			double sx=chemin.Set.get(i).x;
			double sy=chemin.Set.get(i).y;
			g.setColor(Color.GREEN);
			Ellipse2D.Double circle = new Ellipse2D.Double(sx,sy, 60, 60);
			((Graphics2D) g).fill(circle);
			
			
		}
		for (int i=0;i<chemin.Set.size()-1;i++) {
			double sx=chemin.Set.get(i).x;
			double sy=chemin.Set.get(i).y;
			g.setColor(Color.BLACK);
			((Graphics2D) g).setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
			double c=27;
			((Graphics2D) g).draw(new Line2D.Double(sx+c,sy+c,chemin.Set.get(i+1).x+c,chemin.Set.get(i+1).y+c));

		}

	
	}
	
	
	
	

}
