package fr.dauphine.JavaAvance.Components;

import java.util.HashMap;

/**
 * 
 * Orientation of the piece enum
 * 
 */
public enum Orientation {
	/* Implement all the possible orientations and 
	 *  required methods to rotate
	 */
	NORTH,EAST,SOUTH,WEST;

	Orientation turn90() {
		switch(this){
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case WEST:
			return NORTH;
		case SOUTH:
			return WEST;
		default:
			throw new IllegalArgumentException();
			
		}
	}

	static Orientation getOrifromValue(int orientationValue) {
		switch(orientationValue){
		case 0:
			return NORTH;
		case 1:
			return EAST;
		case 2:
			return SOUTH;
		case 3:
			return WEST;
		default:
			throw new IllegalArgumentException();
			
		}
	}

	public int[] getOpposedPieceCoordinates(Piece p) {
		int [] cc = {p.getPosY(),p.getPosX()};
		switch(this){
		case NORTH:
			cc[0]=p.getPosY()-1; 
			cc[1]=p.getPosX();
			return cc;
		case EAST:
			cc[0]=p.getPosY(); 
			cc[1]=p.getPosX()+1;
		case WEST:
			cc[0]=p.getPosY(); 
			cc[1]=p.getPosX()-1;
		case SOUTH:
			cc[0]=p.getPosY()+1; 
			cc[1]=p.getPosX();
		default:
			throw new IllegalArgumentException();
			
		}
	}

	public Orientation getOpposedOrientation() {
		switch(this){
		case NORTH:
			return SOUTH;
		case EAST:
			return WEST;
		case WEST:
			return EAST;
		case SOUTH:
			return NORTH;
		default:
			throw new IllegalArgumentException();
			
		}
	}
	
	
	

}
