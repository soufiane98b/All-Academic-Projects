package fr.dauphine.JavaAvance.Components;


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
	
	public Orientation turn90() {
		switch (this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public static Orientation getOrifromValue(int orientationValue) {
		switch (orientationValue) {
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
	
	public int getValuefromOri() {
		switch (this) {
		case NORTH:
			return 0;
		case EAST:
			return 1;
		case SOUTH:
			return 2;
		case WEST:
			return 3;
		default:
			throw new IllegalArgumentException();
		}
	}

	public Orientation getOpposedOrientation() {
		switch (this) {
		case NORTH:
			return SOUTH;
		case EAST:
			return WEST;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		default:
			throw new IllegalArgumentException();
		}
	}

	public int[] getOpposedPieceCoordinates(Piece p) {
		int[] coordinates = new int[2];
		switch (this) {
		case NORTH:
			coordinates[0] = p.getPosY() - 1;
			coordinates[1] = p.getPosX();
			return coordinates;
		case EAST:
			coordinates[0] = p.getPosY();
			coordinates[1] = p.getPosX() + 1;
			return coordinates;
		case SOUTH:
			coordinates[0] = p.getPosY() + 1;
			coordinates[1] = p.getPosX();
			return coordinates;
		case WEST:
			coordinates[0] = p.getPosY();
			coordinates[1] = p.getPosX() - 1;
			return coordinates;
		default:
			throw new IllegalArgumentException();
		}
	}
}
