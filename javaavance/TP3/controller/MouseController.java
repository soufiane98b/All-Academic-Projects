package fr.dauphine.javaavance.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseMotionListener{
	
	public int x_m;
	public int y_m;
	

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x_m=e.getX();
		y_m=e.getY();
	}

}
