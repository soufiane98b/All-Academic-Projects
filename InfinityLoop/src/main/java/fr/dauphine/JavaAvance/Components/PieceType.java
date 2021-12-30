package fr.dauphine.JavaAvance.Components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

//import fr.dauphine.JavaAvance.GUI.VOID;

/**
 * 
 * Type of the piece enum
 * 
 */
//Each Type has a number of connectors and a specific value
public enum PieceType {
	VOID,ONECONN,BAR,TTYPE,FOURCONN,LTYPE ;
	
	public ArrayList<Orientation> getListOfPossibleOri() {
		ArrayList<Orientation> L = new ArrayList<Orientation>();
		switch(this) {
		case VOID :
			L.add(Orientation.NORTH);
			return L;
		case ONECONN:
			L.add(Orientation.NORTH);
			L.add(Orientation.EAST);
			L.add(Orientation.WEST);
			L.add(Orientation.SOUTH);
			return L;
		case BAR:
			L.add(Orientation.NORTH);
			L.add(Orientation.EAST);
			return L;
		case FOURCONN:
			L.add(Orientation.NORTH);
			return L;
		case LTYPE:
			L.add(Orientation.NORTH);
			L.add(Orientation.EAST);
			L.add(Orientation.WEST);
			L.add(Orientation.SOUTH);
			return L;
		case TTYPE:
			L.add(Orientation.NORTH);
			L.add(Orientation.EAST);
			L.add(Orientation.WEST);
			L.add(Orientation.SOUTH);
			return L;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	
	

	public Orientation getOrientation(Orientation north) {
		//if(!this.getListOfPossibleOri().contains(north) ) throw new IllegalArgumentException();
		switch(north) {
		case NORTH:
			return Orientation.NORTH;
		case SOUTH:
			return Orientation.SOUTH;
		case EAST:
			return Orientation.EAST;
		case WEST:
			return Orientation.WEST;
		default:
			throw new IllegalArgumentException();
			
		}
	}

	LinkedList<Orientation> setConnectorsList(Orientation orientation) {
		LinkedList<Orientation> L = new LinkedList<Orientation>();
		switch(this) {
		case VOID :
			return L;
		case ONECONN:
			L.add(orientation);
			return L;
		case BAR:
			L.add(orientation);
			L.add(orientation.turn90().turn90());
			return L;
		case FOURCONN:
			L.add(Orientation.NORTH);
			L.add(Orientation.SOUTH);
			L.add(Orientation.EAST);
			L.add(Orientation.WEST);
			return L;
		case LTYPE:
			L.add(orientation);
			L.add(orientation.turn90());
			return L;
		case TTYPE:
			L.add(orientation);
			L.add(orientation.turn90());
			L.add(orientation.turn90().turn90().turn90());
			return L;
		default:
			throw new IllegalArgumentException();
		}
	}

	public static PieceType getTypefromValue(int typeValue) {
		switch(typeValue) {
		case 0 :
			return VOID;
		case 1:
			return ONECONN;
		case 2:
			return BAR;
		case 4:
			return FOURCONN;
		case 5:
			return LTYPE;
		case 3:
			return TTYPE;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public static int getValuefromType(PieceType type) {
		switch(type) {
		case VOID :
			return 0;
		case ONECONN:
			return 1;
		case BAR:
			return 2;
		case FOURCONN:
			return 4;
		case LTYPE:
			return 5;
		case TTYPE:
			return 3;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	
	

	public int getNbConnectors() {
		switch(this) {
		case VOID :
			return 0;
		case ONECONN:
			return 1;
		case BAR:
			return 2;
		case FOURCONN:
			return 4;
		case LTYPE:
			return 2;
		case TTYPE:
			return 3;
		default:
			throw new IllegalArgumentException();
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
