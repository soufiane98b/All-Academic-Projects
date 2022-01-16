package fr.dauphine.JavaAvance.Components;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Handling of pieces with general functions
 */
public class Piece {
	private int posX;// j
	private int posY;// i
	private PieceType type;
	private Orientation orientation;
	private LinkedList<Orientation> connectors;
	private ArrayList<Orientation> possibleOrientations;

	private boolean isFixed;

	public Piece(int posY, int posX) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.VOID;
		this.orientation = type.getOrientation(Orientation.NORTH);
		this.connectors = type.setConnectorsList(Orientation.NORTH);
		this.isFixed = false; // Is there any orientation for the piece
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public Piece(int posY, int posX, PieceType type, Orientation orientation) {
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.orientation = type.getOrientation(orientation);
		this.connectors = type.setConnectorsList(orientation);
		this.isFixed = false;
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public Piece(int posY, int posX, int typeValue, int orientationValue) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.getTypefromValue(typeValue);
		this.orientation = type.getOrientation(Orientation.getOrifromValue(orientationValue));
		this.connectors = type.setConnectorsList(Orientation.getOrifromValue(orientationValue));
		this.isFixed = false;
		this.possibleOrientations = type.getListOfPossibleOri();
	}

	public void setPossibleOrientations(ArrayList<Orientation> possibleOrientations) {
		this.possibleOrientations = possibleOrientations;
	}

	public ArrayList<Orientation> getPossibleOrientations() {
		return this.possibleOrientations;
	}

	public LinkedList<Orientation> getInvPossibleOrientation() {
		LinkedList<Orientation> invPossibleOrientations = new LinkedList<>();
		for (Orientation ori : this.getPossibleOrientations()) {
			invPossibleOrientations.addFirst(ori);
		}
		return invPossibleOrientations;
	}

	public void deleteFromPossibleOrientation(Orientation ori) {
		if (this.possibleOrientations.contains(ori)) {
			this.possibleOrientations.remove(ori);
		}
	}

	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
	
	public void setFixedOri(int orientationValue) {
		this.setOrientation(orientationValue);
		this.possibleOrientations = new ArrayList<Orientation> ();
		this.possibleOrientations.add(this.orientation);
		this.isFixed = true;
	}

	public boolean isFixed() {
		return isFixed;
	}

	public int getPosX() { // get j
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() { // get i
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}

	public void setOrientation(int orientationValue) {
		this.orientation = type.getOrientation(Orientation.getOrifromValue(orientationValue));
		this.connectors = type.setConnectorsList(this.orientation);
	}
	
	public void setOrientation(Orientation orientation) {
		this.orientation = type.getOrientation(orientation);
		this.connectors = type.setConnectorsList(this.orientation);
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public LinkedList<Orientation> getConnectors() {
		return connectors;
	}
	
	public void setConnectorsList(LinkedList<Orientation> connectors) {
		this.connectors = new LinkedList<Orientation> (connectors);
	}

	public boolean hasTopConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.NORTH) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRightConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.EAST) {
				return true;
			}
		}
		return false;
	}

	public boolean hasBottomConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.SOUTH) {
				return true;
			}
		}
		return false;
	}

	public boolean hasLeftConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.WEST) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Turn the piece 90° on the right and redefine the connectors's position
	 */
	public void turn() {
		int ori = this.getPossibleOrientations().indexOf(this.getOrientation());
		if (ori == this.getPossibleOrientations().size() - 1) ori = 0;
		else ori++;
		this.setOrientation(this.getPossibleOrientations().get(ori));
	}
	
	
	
	@Override
	public String toString() {
		String s = "[" + this.getPosY() + ", " + this.getPosX() + "] " + this.getType() + " ";
		for (Orientation ori : this.getConnectors()) {
			s += " " + ori.toString();
		}
		s += " Orientation / " + this.getOrientation();
		return s;
	}
	@Override
	public boolean equals(Object obj) {
		Piece p = (Piece) obj;
		if(posX==p.posX && posY==p.posY && type==p.type && orientation==p.orientation && isFixed==p.isFixed())return true;
		return false;
	}
	
	@Override
	public Object clone() {
		if (!(this instanceof Piece))
			return null;
		Piece p = new Piece(((Piece) this).posY, ((Piece) this).posX, ((Piece) this).type,((Piece) this).orientation);
		return p;
	}
	
}
