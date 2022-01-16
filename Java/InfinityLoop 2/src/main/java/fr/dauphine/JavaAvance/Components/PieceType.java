package fr.dauphine.JavaAvance.Components;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	VOID,ONECONN,BAR,TTYPE,FOURCONN,LTYPE;
	// Each Type has a number of connectors and a specific value
	public Orientation getOrientation(Orientation orientation) {
		if (!this.getListOfPossibleOri().contains(orientation))
			throw new IllegalArgumentException();
		switch(orientation) {
		case NORTH :
			return Orientation.NORTH;
		case SOUTH :
			return Orientation.SOUTH;
		case EAST:
			return Orientation.EAST;
		case WEST:
			return Orientation.WEST;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public ArrayList<Orientation> getListOfPossibleOri() {
		ArrayList<Orientation> listo = new ArrayList<Orientation>();
		switch (this) {
		case VOID :
			listo.add(Orientation.NORTH);
			break;
		case BAR:
			listo.add(Orientation.NORTH);
			listo.add(Orientation.EAST);
			break;
		case FOURCONN:
			listo.add(Orientation.NORTH);
			break;
		case LTYPE:
			listo.add(Orientation.NORTH);
			listo.add(Orientation.EAST);
			listo.add(Orientation.SOUTH);
			listo.add(Orientation.WEST);
			break;
		case ONECONN:
			listo.add(Orientation.NORTH);
			listo.add(Orientation.EAST);
			listo.add(Orientation.SOUTH);
			listo.add(Orientation.WEST);
			break;
		case TTYPE:
			listo.add(Orientation.NORTH);
			listo.add(Orientation.EAST);
			listo.add(Orientation.SOUTH);
			listo.add(Orientation.WEST);
			break;
		default:
			throw new IllegalArgumentException();
		}
		return listo;
	}
	
	public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
		if (!this.getListOfPossibleOri().contains(orientation))
			throw new IllegalArgumentException();
		LinkedList<Orientation> listo = new LinkedList<Orientation>();
		switch (this) {
		case VOID :
			break;
		case BAR:
			listo.add(orientation);
			listo.add(orientation.turn90().turn90());
			break;
		case FOURCONN:
			listo.add(Orientation.NORTH);
			listo.add(Orientation.EAST);
			listo.add(Orientation.SOUTH);
			listo.add(Orientation.WEST);
			break;
		case LTYPE:
			listo.add(orientation);
			listo.add(orientation.turn90());
			break;
		case ONECONN:
			listo.add(orientation);
			break;
		case TTYPE:
			listo.add(orientation);
			listo.add(orientation.turn90());
			listo.add(orientation.turn90().turn90().turn90());
			break;
		default:
			throw new IllegalArgumentException();
		}
		return listo;
	}
	
	public static PieceType getTypefromValue(int typeValue) {
		switch (typeValue) {
		case 0:
			return VOID;
		case 1:
			return ONECONN;
		case 2:
			return BAR;
		case 3:
			return TTYPE;
		case 4:
			return FOURCONN;
		case 5:
			return LTYPE;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public int getValuefromType() {
		switch (this) {
		case VOID:
			return 0;
		case ONECONN:
			return 1;
		case BAR:
			return 2;
		case TTYPE:
			return 3;
		case FOURCONN:
			return 4;
		case LTYPE:
			return 5;
		default:
			throw new IllegalArgumentException();
		}
	}

	public int getNbConnectors() {
		return this.setConnectorsList(Orientation.NORTH).size();
	}

}
