package fr.dauphine.JavaAvance.GUI;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;

/**
 * Grid handler and peces'functions which depends of the grid
 * 
 *
 */
public class Grid {
	private int width; // j
	private int height; // i
	private int nbcc = -1;
	private Piece[][] pieces;
	public int fixedPieces = 0;
	public boolean solvable = true; // To Know if if the Grid is can be solved ( consistent ) 
	
	/**
	 *  Empty Constructor 
	 */
	
	public Grid() {}
	
	/**
	 *  Constructor with specified number of connected component
	 */
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		pieces = new Piece[height][width];
	}

	/**
	 *  Constructor with specified number of connected component
	 */
	public Grid(int width, int height, int nbcc) {
		this.width = width;
		this.height = height;
		this.nbcc = nbcc;
		pieces = new Piece[height][width];
	}
	
	public void initializeGrid() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.pieces[i][j] = new Piece(i,j);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Integer getNbcc() {
		return nbcc;
	}

	public void setNbcc(int nbcc) {
		this.nbcc = nbcc;
	}

	public Piece getPiece(int line, int column) {
		return this.pieces[line][column];
	}

	public void setPiece(int line, int column, Piece piece) {
		this.pieces[line][column] = piece;
	}

	public Piece[][] getAllPieces() {
		return pieces;
	}

	/**
	 * Check if a case is a corner
	 * 
	 * @param line
	 * @param column
	 * @return true if the case is a corner
	 */
	public boolean isCorner(int line, int column) {
		if (line == 0) {
			if (column == 0)
				return true;
			if (column == this.getWidth() - 1)
				return true;
			return false;
		} else if (line == this.getHeight() - 1) {
			if (column == 0)
				return true;
			if (column == this.getWidth() - 1)
				return true;
			return false;
		} else {
			return false;
		}
	}

	/**
	 * Check if a case is member of the first or the last line
	 * 
	 * @param line
	 * @param column
	 * @return true if the case is a corner
	 */
	public boolean isBorderLine(int line, int column) {
		if (line == 0 && column > 0 && column < this.getWidth() - 1) {
			return true;
		} else if (line == this.getHeight() - 1 && column > 0 && column < this.getWidth() - 1) {
			return true;
		}
		return false;

	}

	/**
	 * Check if a case is member of the first or the last column
	 * 
	 * @param line
	 * @param column
	 * @return true if the case is a corner
	 */
	public boolean isBorderColumn(int line, int column) {
		if (column == 0 && line > 0 && line < this.getHeight() - 1) {
			return true;
		} else if (column == this.getWidth() - 1 && line > 0 && line < this.getHeight() - 1) {
			return true;
		}
		return false;

	}

	/**
	 * Check if a piece has a neighbour for its connectors for one orientation
	 * 
	 * @param p
	 *            piece
	 * @return true if there is a neighbour for all connectors
	 */
	public boolean hasNeighbour(Piece p) {
		for (Orientation ori : p.getConnectors()) {
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];// i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];// j
			try {
				if (this.getPiece(oppPieceY, oppPieceX).getType() == PieceType.VOID) {
					return false;
				}

			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Check if a piece has a fixed neighbor for each one of its connecotrs
	 * 
	 * @param p
	 *            the piece
	 * @return true if there is a fixed piece for each connector
	 */
	public boolean hasFixedNeighbour(Piece p) {
		boolean bool = false;
		for (Orientation ori : p.getConnectors()) {
			bool = false;
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];// i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];// j
			try {
				Piece neigh = this.getPiece(oppPieceY, oppPieceX);
				if (neigh.getType() == PieceType.VOID || !neigh.isFixed()) {
					return false;
				}
				if (neigh.isFixed()) {
					for (Orientation oriOppPiece : neigh.getConnectors()) {
						if (ori == oriOppPiece.getOpposedOrientation()) {
							bool = true;
						}
					}
					if (!bool) {
						return false;
					}

				}
			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return bool;
	}

	/**
	 * Check if a piece has a at least one fixed neighbor
	 * 
	 * @param p
	 *            the piece
	 * @return true if there is a fixed piece for each connector
	 */
	public boolean hasAtLeast1FixedNeighbour(Piece p) {
		for (Orientation ori : p.getConnectors()) {
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];// i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];// j
			try {
				Piece neigh = this.getPiece(oppPieceY, oppPieceX);
				if (neigh.isFixed()) {
					for (Orientation oriOppPiece : neigh.getConnectors()) {
						if (ori == oriOppPiece.getOpposedOrientation()) {
							return true;
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * list of neighbors
	 * 
	 * @param p
	 *            the piece
	 * @return the list of neighbors
	 */
	public ArrayList<Piece> listOfNeighbours(Piece p) {
		ArrayList<Piece> lp = new ArrayList<>();
		for (Orientation ori : p.getConnectors()) {
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];// i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];// j

			if (oppPieceY >= 0 && oppPieceY < this.getHeight() && oppPieceX >= 0 && oppPieceX < this.width) {
				if (this.getPiece(oppPieceY, oppPieceX).getType() != PieceType.VOID) {
					lp.add(this.getPiece(oppPieceY, oppPieceX));
				}
			}

		}
		return lp;
	}

	/**
	 * this function returns the number of neighbors
	 * 
	 * @param p
	 * @return the number of neighbors
	 */
	public int numberOfNeibours(Piece p) {
		int X = p.getPosX();
		int Y = p.getPosY();
		int count = 0;
		if (Y < this.getHeight() - 1 && getPiece(Y + 1, X).getType() != PieceType.VOID)
			count++;
		if (X < this.getWidth() - 1 && getPiece(Y, X + 1).getType() != PieceType.VOID)
			count++;
		if (Y > 0 && getPiece(Y - 1, X).getType() != PieceType.VOID)
			count++;
		if (X > 0 && getPiece(Y, X - 1).getType() != PieceType.VOID)
			count++;
		return count;
	}

	/**
	 * this function returns the number of fixed neighbors
	 * 
	 * @param p
	 * @return the number of neighbors
	 */
	public int numberOfFixedNeibours(Piece p) {
		int X = p.getPosX();
		int Y = p.getPosY();
		int count = 0;

		if (Y < this.getHeight() - 1 && getPiece(Y + 1, X).getType() != PieceType.VOID && getPiece(Y + 1, X).isFixed())
			count++;
		if (X < this.getWidth() - 1 && getPiece(Y, X + 1).getType() != PieceType.VOID && getPiece(Y, X + 1).isFixed())
			count++;
		if (Y > 0 && getPiece(Y - 1, X).getType() != PieceType.VOID && getPiece(Y - 1, X).isFixed())
			count++;
		if (X > 0 && getPiece(Y, X - 1).getType() != PieceType.VOID && getPiece(Y, X - 1).isFixed())
			count++;
		return count;
	}

	/**
	 * Check if all pieces have neighbors even if we don't know the orientation
	 * 
	 * @param p
	 * @return false if a piece has no neighbor
	 */
	public boolean allPieceHaveNeighbour() {

		for (Piece[] ligne : this.getAllPieces()) {
			for (Piece p : ligne) {

				if (p.getType() != PieceType.VOID) {
					if (p.getType().getNbConnectors() > numberOfNeibours(p)) {
						return false;
					}
				}

			}
		}
		return true;

	}

	/**
	 * Return the next piece of the current piece
	 * 
	 * @param p
	 *            the current piece
	 * @return the piece or null if p is the last piece
	 */
	public Piece getNextPiece(Piece p) {
		int i = p.getPosY();
		int j = p.getPosX();
		if (j < this.getWidth() - 1) {
			p = this.getPiece(i, j + 1);
		} else {
			if (i < this.getHeight() - 1) {
				p = this.getPiece(i + 1, 0);
			} else {
				return null;
			}

		}
		return p;
	}
	
	/**
	 * Return the next piece of the current piece right2left and bottom2top
	 * 
	 * @param p
	 *            the current piece
	 * @return the piece or null if p is the last piece
	 */
	public Piece getNextPieceInv(Piece p) {

		int i = p.getPosY();
		int j = p.getPosX();
		if (j > 0) {
			p = this.getPiece(i, j - 1);
		} else {
			if (i > 0) {
				p = this.getPiece(i - 1, this.getWidth()-1);
			} else {
				return null;
			}

		}

		return p;

	}

	/**
	 * Check if a piece is connected
	 * 
	 * @param line
	 * @param column
	 * @return true if a connector of a piece is connected
	 */
	public boolean isConnected(Piece p, Orientation ori) {
		int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];// i
		int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];// j
		if (p.getType() == PieceType.VOID)
			return true;
		try {
			for (Orientation oppConnector : this.getPiece(oppPieceY, oppPieceX).getConnectors()) {
				if (oppConnector == ori.getOpposedOrientation()) {
					return true;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return false;
	}

	/**
	 * Check if a piece is totally connected
	 * 
	 * @param line
	 * @param column
	 * @return true if a connector of a piece is connected
	 */
	public boolean isTotallyConnected(Piece p) {
		if (p.getType() != PieceType.VOID) {
			for (Orientation connector : p.getConnectors()) {
				if (!this.isConnected(p, connector)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check if a piece position is valid
	 * 
	 * @param line
	 * @param column
	 * @return true if a connector of a piece is connected
	 */
	public boolean isValidOrientation(int line, int column) {

		Piece tn = this.topNeighbor(this.getPiece(line, column));
		Piece ln = this.leftNeighbor(this.getPiece(line, column));
		Piece rn = this.rightNeighbor(this.getPiece(line, column));
		Piece bn = this.bottomNeighbor(this.getPiece(line, column));

		if (this.getPiece(line, column).getType() != PieceType.VOID) {
			if (line == 0) {
				if (column == 0) {
					if (this.getPiece(line, column).hasLeftConnector())
						return false;
				} else if (column == this.getWidth() - 1) {
					if (this.getPiece(line, column).hasRightConnector())
						return false;
				}
				if (this.getPiece(line, column).hasTopConnector())
					return false;
				if (!this.getPiece(line, column).hasRightConnector() && rn != null && rn.hasLeftConnector())
					return false;
				if (this.getPiece(line, column).hasRightConnector() && rn != null && !rn.hasLeftConnector())
					return false;
				if (!this.getPiece(line, column).hasBottomConnector() && bn != null && bn.hasTopConnector())
					return false;
				if (this.getPiece(line, column).hasBottomConnector() && bn != null && !bn.hasTopConnector())
					return false;

			} else if (line > 0 && line < this.getHeight() - 1) {
				if (column == 0) {
					if (this.getPiece(line, column).hasLeftConnector())
						return false;

				} else if (column == this.getWidth() - 1) {
					if (this.getPiece(line, column).hasRightConnector())
						return false;
				}

				if (!this.getPiece(line, column).hasRightConnector() && rn != null && rn.hasLeftConnector())
					return false;
				if (this.getPiece(line, column).hasRightConnector() && rn != null && !rn.hasLeftConnector())
					return false;
				if (!this.getPiece(line, column).hasBottomConnector() && bn != null && bn.hasTopConnector())
					return false;
				if (this.getPiece(line, column).hasBottomConnector() && bn != null && !bn.hasTopConnector())
					return false;

			} else if (line == this.getHeight() - 1) {
				if (column == 0) {
					if (this.getPiece(line, column).hasLeftConnector())
						return false;
				} else if (column == this.getWidth() - 1) {
					if (this.getPiece(line, column).hasRightConnector())
						return false;
				}
				if (this.getPiece(line, column).hasBottomConnector())
					return false;
				if (!this.getPiece(line, column).hasRightConnector() && rn != null && rn.hasLeftConnector())
					return false;
				if (this.getPiece(line, column).hasRightConnector() && rn != null && !rn.hasLeftConnector())
					return false;

			}
			if (this.getPiece(line, column).hasLeftConnector() && ln == null)
				return false;
			if (this.getPiece(line, column).hasTopConnector() && tn == null)
				return false;
			if (this.getPiece(line, column).hasRightConnector() && rn == null)
				return false;
			if (this.getPiece(line, column).hasBottomConnector() && bn == null)
				return false;
		}

		return true;
	}

	/**
	 * Find the left neighbor
	 * 
	 * @param p
	 * @return the neighbor or null if no neighbor
	 */
	public Piece leftNeighbor(Piece p) {

		if (p.getPosX() > 0) {
			if (this.getPiece(p.getPosY(), p.getPosX() - 1).getType() != PieceType.VOID) {
				return this.getPiece(p.getPosY(), p.getPosX() - 1);
			}
		}
		return null;
	}

	/**
	 * Find the top neighbor
	 * 
	 * @param p
	 * @return the neighbor or null if no neighbor
	 */
	public Piece topNeighbor(Piece p) {

		if (p.getPosY() > 0) {
			if (this.getPiece(p.getPosY() - 1, p.getPosX()).getType() != PieceType.VOID) {
				return this.getPiece(p.getPosY() - 1, p.getPosX());
			}
		}
		return null;
	}

	/**
	 * Find the right neighbor
	 * 
	 * @param p
	 * @return the neighbor or null if no neighbor
	 */
	public Piece rightNeighbor(Piece p) {

		if (p.getPosX() < this.getWidth() - 1) {
			if (this.getPiece(p.getPosY(), p.getPosX() + 1).getType() != PieceType.VOID) {
				return this.getPiece(p.getPosY(), p.getPosX() + 1);
			}
		}
		return null;
	}

	/**
	 * Find the bottom neighbor
	 * 
	 * @param p
	 * @return the neighbor or null if no neighbor
	 */
	public Piece bottomNeighbor(Piece p) {

		if (p.getPosY() < this.getHeight() - 1) {
			if (this.getPiece(p.getPosY() + 1, p.getPosX()).getType() != PieceType.VOID) {
				return this.getPiece(p.getPosY() + 1, p.getPosX());
			}
		}
		return null;
	}
	
	
	/**
	 *  The first Filter give all possible orientation for any piece
	 * 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation
	 */
	
	public static ArrayList<Pair<PieceType, Orientation>> allPieces() {
		ArrayList<Pair<PieceType, Orientation>> allPieces = new ArrayList<Pair<PieceType, Orientation>>();
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.NORTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.EAST));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.NORTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.EAST));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.SOUTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.WEST));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.FOURCONN,Orientation.NORTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.NORTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.EAST));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.SOUTH));
		allPieces.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.WEST));
		
		return allPieces;
	}
	
	/**
	 * Filter for left Neighbor 
	 * @param p 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation according to it's left Neighbor
	 */
	public ArrayList<Pair<PieceType, Orientation>> leftNeighborFilter(Piece p) {
		ArrayList<Pair<PieceType, Orientation>> filter = new ArrayList<Pair<PieceType, Orientation>>();
		Piece ln = this.leftNeighbor(p);
		if (ln != null && ln.hasRightConnector()) {
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.FOURCONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.WEST));
		}
		else {
			filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.EAST));
		}
		return filter;
	}
	/**
	 * Filter for top Neighbor
	 * @param p 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation according to it's top Neighbor
	 */
	public ArrayList<Pair<PieceType, Orientation>> topNeighborFilter(Piece p) {
		ArrayList<Pair<PieceType, Orientation>> filter = new ArrayList<Pair<PieceType, Orientation>>();
		Piece tn = this.topNeighbor(p);
		if (tn != null && tn.hasBottomConnector()) {
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.FOURCONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.WEST));
		}
		else {
			filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.SOUTH));
		}
		return filter;
	}
	
	
	/**
	 * Filter for right Neighbor
	 * @param p 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation according to it's right Neighbor
	 */
	public ArrayList<Pair<PieceType, Orientation>> rightNeighborFilter(Piece p) {
		ArrayList<Pair<PieceType, Orientation>> filter = new ArrayList<Pair<PieceType, Orientation>>();
		Piece rn = this.rightNeighbor(p);
		if (rn != null && rn.hasLeftConnector()) {
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.FOURCONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.EAST));
		}
		else {
			filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.WEST));
		}
		return filter;
	}
	
	/**
	 * Filter for bottom Neighbor
	 * @param p 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation according to it's bottom Neighbor
	 */
	public ArrayList<Pair<PieceType, Orientation>> bottomNeighborFilter(Piece p) {
		ArrayList<Pair<PieceType, Orientation>> filter = new ArrayList<Pair<PieceType, Orientation>>();
		Piece bn = this.bottomNeighbor(p);
		if (bn != null && bn.hasTopConnector()) {
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.FOURCONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.SOUTH));
		}
		else {
			filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.WEST));
		}
		return filter;
	}
	
	
	
	/**
	 * Filter for corner Neighbor
	 * @param p 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation according to it's corner Neighbor
	 */
	public ArrayList<Pair<PieceType, Orientation>> cornerFilter(Piece p) {
		ArrayList<Pair<PieceType, Orientation>> filter = new ArrayList<Pair<PieceType, Orientation>>();
		if (!this.isCorner(p.getPosY(), p.getPosX()))
			return allPieces();
		
		if (p.getPosY() == 0) {
			if (p.getPosX() == 0) { // Coin haut gauche
				filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
				filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
				filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
				filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.EAST));
			}
			else if (p.getPosX() == this.width - 1) { // Coin haut droite
				filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
				filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
				filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
				filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.SOUTH));
			}
		}
		
		else if (p.getPosY() == this.height - 1) {
			if (p.getPosX() == 0) { // Coin bas gauche
				filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
				filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
				filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
				filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.NORTH));
			}
			else if (p.getPosX() == this.width - 1) { // Coin bas droite
				filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
				filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
				filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
				filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.WEST));
			}
		}
		return filter;
	}
	
	/**
	 * Filter for border Neighbor
	 * @param p 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation according to it's border Neighbor
	 */
	public ArrayList<Pair<PieceType, Orientation>> borderFilter(Piece p) {
		ArrayList<Pair<PieceType, Orientation>> filter = new ArrayList<Pair<PieceType, Orientation>>();
		if (!this.isBorderLine(p.getPosY(), p.getPosX()) && !this.isBorderColumn(p.getPosY(), p.getPosX()))
			return allPieces();
		
		if (p.getPosY() == 0) { // Bordure haut
			filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.SOUTH));
		}
		
		else if (p.getPosX() == 0) { // Bordure gauche
			filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.EAST));
		}
		
		if (p.getPosY() == this.height - 1) { // Bordure bas
			filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.EAST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.WEST));
		}
		
		if (p.getPosX() == this.width - 1) { // Bordure droit
			filter.add(new Pair<PieceType, Orientation>(PieceType.VOID,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.ONECONN,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.BAR,Orientation.NORTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.TTYPE,Orientation.WEST));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.SOUTH));
			filter.add(new Pair<PieceType, Orientation>(PieceType.LTYPE,Orientation.WEST));
		}
		
		return filter;
	}
	
	/**
	 * Apply filters for corner, border , left and top Neighbor, this method it's used to generate a grid solved , check class Generator 
	 * With nbcc >=1 we generate just one component, we handle multiple componenent in the class Generator 
	 * @param p 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation according to it's corner, border , left and top Neighbor
	 * @see fr.dauphine.JavaAvance.Solve
	 */
	
	public ArrayList<Pair<PieceType, Orientation>> interFilter(Piece p) {
		ArrayList<Pair<PieceType, Orientation>> filter1 = this.cornerFilter(p);
		ArrayList<Pair<PieceType, Orientation>> filter2 = this.borderFilter(p);
		ArrayList<Pair<PieceType, Orientation>> filter3 = this.leftNeighborFilter(p);
		ArrayList<Pair<PieceType, Orientation>> filter4 = this.topNeighborFilter(p);
		filter1.retainAll(filter2);
		filter1.retainAll(filter3);
		filter1.retainAll(filter4);
		
		
		if(nbcc>=1) { // ici on va juste generer une grid avec 	1 component 
			filter1 = this.nbcFilter(p,filter1);
		}
		
		return filter1;

	}
	
	/**
	 * Apply filters for corner, border , left, top Neighbor and nbcc
	 * @param p 
	 * @return an ArrayList of Pair of piece type and orientation and it's all possible orientation according nbcc 
	 */
	public ArrayList<Pair<PieceType,Orientation>> nbcFilter(Piece p, ArrayList<Pair<PieceType,Orientation>> firstFilter) {
        int nConnectors = 0;
        Piece ln = this.leftNeighbor(p);
        Piece tn = this.topNeighbor(p);
        if (ln != null && ln.hasRightConnector()) nConnectors++;
        if (tn != null && tn.hasBottomConnector()) nConnectors++;
        if (firstFilter.size() > 1) {
            if (nConnectors == 1)
                firstFilter.removeIf(element -> (element.getKey() == PieceType.ONECONN));
            if (nConnectors == 2)
                firstFilter.removeIf(element -> (element.getKey() == PieceType.LTYPE));
        }
        
        return firstFilter;
    }
	
	
	
	
	/**
	 * Take a piece, first get all possible orientations then apply a filter according to its position and neibghors in the grid 
	 * and fix it's position if one position available and return all left possibilites
	 * @param p 
	 * @return an ArrayList of possible Orientation according to it's fixed Neighbor
	 */
	public ArrayList<Orientation> fixPiece(Piece p) {
		ArrayList<Orientation> fixedOrientations = new ArrayList<Orientation>();
		if (p.isFixed()) {
			fixedOrientations.add(p.getOrientation());
			return fixedOrientations;
		}
		PieceType type = p.getType();
		ArrayList<Pair<PieceType, Orientation>> possibilities = new ArrayList<Pair<PieceType, Orientation>>();
		for (Orientation ori : p.getPossibleOrientations())
			possibilities.add(new Pair<PieceType, Orientation>(type,ori));
		if (this.isCorner(p.getPosY(),p.getPosX()))
			possibilities.retainAll(this.cornerFilter(p));
		if (this.isBorderLine(p.getPosY(),p.getPosX()) || this.isBorderColumn(p.getPosY(),p.getPosX()))
			possibilities.retainAll(this.borderFilter(p));
		if ((this.topNeighbor(p) != null && this.topNeighbor(p).isFixed()) || this.topNeighbor(p) == null)
			possibilities.retainAll(this.topNeighborFilter(p));
		if ((this.leftNeighbor(p) != null && this.leftNeighbor(p).isFixed()) || this.leftNeighbor(p) == null)
			possibilities.retainAll(this.leftNeighborFilter(p));
		if ((this.rightNeighbor(p) != null && this.rightNeighbor(p).isFixed()) || this.rightNeighbor(p) == null)
			possibilities.retainAll(this.rightNeighborFilter(p));
		if ((this.bottomNeighbor(p) != null && this.bottomNeighbor(p).isFixed()) || this.bottomNeighbor(p) == null)
			possibilities.retainAll(this.bottomNeighborFilter(p));
		if (possibilities.size() == 0) {
			this.solvable = false;
			return fixedOrientations;
		}
		else {
			p.setOrientation(possibilities.get(0).getValue().getValuefromOri());
			if (possibilities.size() == 1) {
				p.setFixed(true);
				//p.setFixedOri(possibilities.get(0).getValue());
				this.fixedPieces++;
			}
			
		}
		for (Pair<PieceType, Orientation> ori : possibilities) {
			fixedOrientations.add(ori.getValue());
		}
		return fixedOrientations;
	}
	
	
	/**
	 * Fixes all the pieces that can be fixed with fixPiece
	 * @return true if more pieces are fixed  otherwise return false
	 */
	public boolean fixAllPieces()  {
		int fixedPieces = this.fixedPieces;
		//if(fixedPieces!= nbFixed()) throw new Exception ();
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.fixPiece(this.pieces[i][j]);
				if(!this.solvable)return false;
			}
		}
		if (fixedPieces < this.fixedPieces) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return all the neighbors in a list without the void
	 */
	public ArrayList<Piece> initListNeighbors(Piece p) {
        ArrayList<Piece> list = new ArrayList<Piece>();
        Piece tn = this.topNeighbor(p);
        Piece ln = this.leftNeighbor(p);
        Piece rn = this.rightNeighbor(p);
        Piece bn = this.bottomNeighbor(p);
        if (tn != null) list.add(tn);
        if (ln != null) list.add(ln);
        if (rn != null) list.add(rn);
        if (bn != null) list.add(bn);
        return list;
    }
	
	/**
	 * Improvment of fixAllPieces() thanks to IA
	 * @return true if the grid is still solvable otherwise false
	 */
	public boolean fixAllPieces2() {
        ArrayDeque<Pair<Piece, ArrayList<Piece>>> que = new ArrayDeque<>();
        for (Piece[] lp : this.pieces) {
            for (Piece p : lp) {
                if (!p.isFixed())
                    que.add(new Pair<>(p, this.initListNeighbors(p)));
            }
        }
        while (!que.isEmpty()) {
            Pair<Piece, ArrayList<Piece>> pp = que.remove();
            Piece tmp = pp.getKey();
            if (!tmp.isFixed()) {
                this.fixPiece(tmp);
                if (!this.solvable) return false;
                if (tmp.isFixed()) {
                    for (Piece n : pp.getValue()) {
                        que.add(new Pair<>(n, this.initListNeighbors(n)));
                    }
                }
            }
            
        }
        return true;
    }
	
	

	
	
	/**
	 * Method that copy the current grid 
	 * @return the copied grid
	 */
	public Grid copyGrid() {

        Grid copy = new Grid(this.getWidth(),this.getHeight());
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j=0; j < this.getWidth(); j++) {
                Piece current = this.getPiece(i, j);
                PieceType t = current.getType();
                Orientation o = current.getOrientation();
                Piece pcopy = new Piece(i,j,t,o);
                if (current.isFixed()) pcopy.setFixed(true);
                copy.setPiece(i,j,pcopy);
                copy.fixedPieces=this.fixedPieces;

            }
        }

        return copy;

    }
	
	/**
	 * @param L
	 * @return the mean of the list L 
	 */
	public static int moyenne(ArrayList<Integer> L) {
		int M=0;
		for (Integer l :L ) {
			M=M+l;
		}
		return M/L.size();
	}
	
	/**
	 * @param L
	 * @return the max of the list L 
	 */
	public static int max(ArrayList<Integer> L) {
		return Collections.max(L);
	}
	
	/**
	 * @param L
	 * @return the position of the max of the sublist in L 
	 */
	public static int heuristiqueMax(ArrayList<ArrayList<Integer>> L)  {
		int posi=0;
		int max_heuri = max(L.get(0));
		for(int i=0;i<L.size();i++) {
			int tmp =  max(L.get(i));
			if(tmp >max_heuri) {
				max_heuri = tmp;
				posi=i;
			}
		}
				
		return posi;
	}
	
	/**
	 * @param L
	 * @return the position of the max of the mean of the sublist in L 
	 */
	public static int heuristiqueMoyenne(ArrayList<ArrayList<Integer>> L)  {
		int posi=0;
		int max_heuri = moyenne(L.get(0));
		for(int i=0;i<L.size();i++) {
			int tmp =  moyenne(L.get(i));
			if(tmp >max_heuri) {
				max_heuri = tmp;
				posi=i;
			}
		}
				
		return posi;
	}
	
	/**
	 * find the first piece not fixed and sort its possbile orientation 
	 * @see piece_sortOrientation
	 * @param version2  to run the method with a selection of the best orientation thanks to piece_sortOrientation
	 * @return a Pair of the piece and it's orientation
	 */
	public Pair<Piece,ArrayList<Orientation>> pieceSelection_first(boolean version2) {
		boolean solved = true;
		for (Piece[] lp : this.pieces) {
			for (Piece p : lp) {
				if (!this.isTotallyConnected(p)) solved = false;
				if (!p.isFixed()) {
                    Pair<Piece, ArrayList<Orientation>> po = new Pair<Piece, ArrayList<Orientation>>(p,this.fixPiece(p));
                    if (version2) return piece_sortOrientation(po,new ArrayList<Integer>());
                    return po;
                }
			}
		}
		if (solved) return new Pair<Piece, ArrayList<Orientation>>(null,null);
		
		return null;
	}
	
	/**
	 * find the best piece thanks to a heuristic
	 * @see heuristiqueMoyenne , heuristiqueMax
	 * @param version2  to run the method with a selection of the best orientation thanks to piece_sortOrientation
	 * @return a Pair of the piece and it's orientation
	 */
	public Pair<Piece,ArrayList<Orientation>> pieceSelection_best(boolean max)  {
		boolean solved = true;
		ArrayList<ArrayList<Integer>> ListFixedPieces = new ArrayList<>();
		ArrayList<ArrayList<Orientation>> ListOri = new ArrayList<>();
		ArrayList<Piece> NofListPiece = new ArrayList<>();
		int cmpt=0;
		for (Piece[] lp : this.pieces) {
			for (Piece p : lp) {
				if (!this.isTotallyConnected(p)) solved = false;
				if (!p.isFixed()) {
					cmpt++;
                    Pair<Piece, ArrayList<Orientation>> po = new Pair<Piece, ArrayList<Orientation>>(p,this.fixPiece(p));
                    ArrayList<Integer> fixedPiece = new ArrayList<Integer>();
                    po= piece_sortOrientation(po,fixedPiece);
                    ListFixedPieces.add(fixedPiece);
                    ListOri.add(po.getValue());
                    NofListPiece.add(po.getKey());
                }
				if(cmpt>=((this.height*this.width) -this.fixedPieces) ) break; // sert à rien d'aller plus loin car plus de pieces non fixé
			}
		}
		if(NofListPiece.isEmpty()==false) {
			int posi;
			if(max) posi = heuristiqueMax(ListFixedPieces);
			else posi=heuristiqueMoyenne(ListFixedPieces);
			return new Pair<Piece, ArrayList<Orientation>>(NofListPiece.get(posi),ListOri.get(posi));
		}
		
		if (solved) return new Pair<Piece, ArrayList<Orientation>>(null,null); 
		return null;
	}
	
	/**
	 * find the best orientation of piece , the best orientation is defined as the orientation that fixes the maximum of 
	 * pices by fixing this orientation, so we sort our possbile orientation like thanks to fixedPieces
	 * @param  po
	 * @param  fixedPieces
	 * @return a pair of a piece and its sorted orienatation
	 */
	public Pair<Piece,ArrayList<Orientation>> piece_sortOrientation(Pair<Piece,ArrayList<Orientation>> po,ArrayList<Integer> fixedPieces)  {
        Piece p = po.getKey();
        ArrayList<Integer> lo = new ArrayList<Integer>();
        for (Orientation ori : po.getValue()) {
            Grid copyGrid = this.copyGrid();
            Piece pCopy = copyGrid.getPiece(p.getPosY(), p.getPosX());
            pCopy.setOrientation(ori);
            pCopy.setFixed(true);
            copyGrid.fixAllPieces2();
            lo.add(copyGrid.fixedPieces);
        }

        ArrayList<Integer> sortOrientations = new ArrayList<Integer>();
        sortOrientations.addAll(lo);
        Collections.sort(sortOrientations);
        fixedPieces.addAll(sortOrientations);
        ArrayList<Orientation> oriSorted = new ArrayList<Orientation>();
        for (int i = 0; i < lo.size(); i++) {
            int k = lo.indexOf(sortOrientations.get(i));
            sortOrientations.set(i,k);
            oriSorted.add(po.getValue().get(sortOrientations.get(i)));
            lo.set(k, - 1);// EN CAS DE VALEUR EGALES 
        }
        po.setValue(oriSorted);
        return po;
    }
	
	/**
	 * choose randomly a piece among unfixed pieces
	 * @return a pair of a piece and its sorted orienatation
	 */
	public Pair<Piece,ArrayList<Orientation>> pieceSelection_random() {
		boolean solved = true;
		Random r = new Random();
		int rand = r.nextInt((this.width*this.height)-this.fixedPieces);
		int cmpt=0;
		for (Piece[] lp : this.pieces) {
			for (Piece p : lp) {
				if (!this.isTotallyConnected(p)) solved = false;
				if(! p.isFixed()) {
					if(cmpt==rand) {
						return new Pair<Piece, ArrayList<Orientation>>(p,this.fixPiece(p));
					}
					cmpt++;
				}
					
			}
				
		}
		if (solved) return new Pair<Piece, ArrayList<Orientation>>(null,null); 
		return null;
		
	}

	/**
	 * @return a true if a grid is solved otherwise false
	 */
	public boolean isSolved() {
		for (Piece[] lp : this.pieces) {
			for (Piece p : lp) {
				if (!this.isTotallyConnected(p))
					return false;
			}
		}
		return true;
	}
	

	@Override
	public String toString() {

		String s = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				s += DisplayUnicode.getUnicodeOfPiece(pieces[i][j].getType(), pieces[i][j].getOrientation());
			}
			s += "\n";
		}
		return s;
	}

}
