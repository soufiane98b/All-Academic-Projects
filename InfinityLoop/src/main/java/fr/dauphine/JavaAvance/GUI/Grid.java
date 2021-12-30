package fr.dauphine.JavaAvance.GUI;

import java.util.ArrayList;

import fr.dauphine.JavaAvance.Components.Orientation;
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

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		pieces = new Piece[height][width];
	}

	// Constructor with specified number of connected component
	public Grid(int width, int height, int nbcc) {
		this.width = width;
		this.height = height;
		this.nbcc = nbcc;
		pieces = new Piece[height][width];
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
	
	public Piece leftNeighbor1(Piece p) {

		return this.getPiece(p.getPosY(), p.getPosX() - 1);
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
	public Piece topNeighbor1(Piece p) {

		return this.getPiece(p.getPosY() - 1, p.getPosX());
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
	public Piece rightNeighbor1(Piece p) {

		return this.getPiece(p.getPosY(), p.getPosX() + 1);
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
	public Piece bottomNeighbor1(Piece p) {

		return this.getPiece(p.getPosY() + 1, p.getPosX());
	}

	
	/**
	 * by soufiane 
	 * @param l
	 * @param c
	 * @return list of possible PieceType according to the position in the grid
	 * @throws Exception 
	 */
	// VOID,ONECONN,BAR,TTYPE,FOURCONN,LTYPE 
	public ArrayList<Piece> piecePossible(int l , int c) throws Exception {
		
		ArrayList<Piece> L = new ArrayList<Piece>();
		Piece p = getPiece(l,c);
		Piece top = topNeighbor(p);
		Piece left = leftNeighbor(p);
		//System.out.println("piece :"+p);
		//System.out.println("top :"+top);
		//System.out.println("left "+left);
		if(this.isCorner(l, c)) {
			if(l==0 && c==0) {
				L.add(new Piece(l,c));
				L.add(new Piece(l,c,PieceType.ONECONN,Orientation.EAST));
				L.add(new Piece(l,c,PieceType.ONECONN,Orientation.SOUTH));
				L.add(new Piece(l,c,PieceType.LTYPE,Orientation.EAST));
				return L;
				
			}
			if(l==0 && c == width-1) {
				if(left==null || left.hasRightConnector()==false){
					L.add(new Piece(l,c));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.SOUTH));
					return L;
				}
				if(left.hasRightConnector()){
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.WEST));
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.SOUTH));
					return L;
				}
			}
			
			if(l==height-1 && c==0) {
				if(top==null || top.hasBottomConnector()==false){
					L.add(new Piece(l,c));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.EAST));
					return L;
				}
				if(top.hasBottomConnector()){
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.NORTH));
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.NORTH));
					return L;

				}
			}
			
			if(l==height-1 && c == width-1) {
		
				if( (top==null || top.hasBottomConnector()==false) && (left==null ||left.hasRightConnector()==false)  ) {
					L.add(new Piece(l,c));
					return L;
				}
				
				if((top==null || top.hasBottomConnector()==false) && (left!=null && left.hasRightConnector()==true)) {
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.WEST));
					return L;
					
				}
				if((top!=null && top.hasBottomConnector()==true) && (left==null ||left.hasRightConnector()==false)) {
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.NORTH));
					return L;
					
				}
				
				if((top!=null && top.hasBottomConnector()==true) && (left!=null && left.hasRightConnector()==true)) {
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.WEST));
					return L;
					
				}
				
			}
		
		}
		
		if(this.isBorderLine(l, c)) {
			if(l==0) {
				if( left==null || left.hasRightConnector()==false) {
					L.add(new Piece(l,c));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.SOUTH));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.EAST));
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.EAST));
					return L;
				}
				if( left.hasRightConnector()==true) {
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.SOUTH));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.WEST));
					L.add(new Piece(l,c,PieceType.BAR,Orientation.EAST));
					L.add(new Piece(l,c,PieceType.TTYPE,Orientation.SOUTH));
					return L;
				}

			}
			if(l==height-1) {
				if( (top==null || top.hasBottomConnector()==false) && (left==null ||left.hasRightConnector()==false) ) {
					L.add(new Piece(l,c));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.EAST));
					return L;
				}
				if((top==null || top.hasBottomConnector()==false) && (left!=null && left.hasRightConnector()==true)) {
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.WEST));
					L.add(new Piece(l,c,PieceType.BAR,Orientation.EAST));
					return L;
					
				}
				if((top!=null && top.hasBottomConnector()==true) && (left==null ||left.hasRightConnector()==false)) {
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.NORTH));
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.NORTH));
					return L;
					
				}
				
				if((top!=null && top.hasBottomConnector()==true) && (left!=null && left.hasRightConnector()==true)) {
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.WEST));
					L.add(new Piece(l,c,PieceType.TTYPE,Orientation.NORTH));
					return L;
					
				}
				
			}

		}
		if(this.isBorderColumn(l, c)) {
			if(c==0) {
				if( top==null || top.hasBottomConnector()==false) {
					L.add(new Piece(l,c));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.SOUTH));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.EAST));
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.EAST));
					return L;
				}
				if( top.hasBottomConnector()==true) {
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.NORTH));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.NORTH));
					L.add(new Piece(l,c,PieceType.BAR,Orientation.NORTH));
					L.add(new Piece(l,c,PieceType.TTYPE,Orientation.EAST));
					return L;
				}

			}
			if(c==width-1) {
				if((top==null || top.hasBottomConnector()==false) && (left==null ||left.hasRightConnector()==false) ) {
					L.add(new Piece(l,c));
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.SOUTH));
					return L;
				}
				
				if((top==null || top.hasBottomConnector()==false) && (left!=null && left.hasRightConnector()==true)) {
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.WEST));
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.SOUTH));
					return L;
					
				}
				if((top!=null && top.hasBottomConnector()==true) && (left==null ||left.hasRightConnector()==false)) {
					L.add(new Piece(l,c,PieceType.ONECONN,Orientation.NORTH));
					L.add(new Piece(l,c,PieceType.BAR,Orientation.NORTH));
					return L;
					
				}
				
				if((top!=null && top.hasBottomConnector()==true) && (left!=null && left.hasRightConnector()==true)) {
					L.add(new Piece(l,c,PieceType.LTYPE,Orientation.WEST));
					L.add(new Piece(l,c,PieceType.TTYPE,Orientation.WEST));
					return L;
					
				}
				
			}

		}
		
		else {
			  
			if((top==null || top.hasBottomConnector()==false) && (left!=null && left.hasRightConnector()==true)){
				L.add(new Piece(l,c,PieceType.ONECONN,Orientation.WEST));
				L.add(new Piece(l,c,PieceType.LTYPE,Orientation.SOUTH));
				L.add(new Piece(l,c,PieceType.BAR,Orientation.EAST));
				L.add(new Piece(l,c,PieceType.TTYPE,Orientation.SOUTH));
				return L;
			}
			
			if((top!=null && top.hasBottomConnector()==true) && (left==null ||left.hasRightConnector()==false)) {
				L.add(new Piece(l,c,PieceType.ONECONN,Orientation.NORTH));
				L.add(new Piece(l,c,PieceType.BAR,Orientation.NORTH));
				L.add(new Piece(l,c,PieceType.LTYPE,Orientation.NORTH));
				L.add(new Piece(l,c,PieceType.TTYPE,Orientation.EAST));
				return L;
			}
			
			if((top!=null && top.hasBottomConnector()==true) && (left!=null && left.hasRightConnector()==true)) {
				L.add(new Piece(l,c,PieceType.LTYPE,Orientation.WEST));
				L.add(new Piece(l,c,PieceType.FOURCONN,Orientation.NORTH));
				L.add(new Piece(l,c,PieceType.TTYPE,Orientation.WEST));
				L.add(new Piece(l,c,PieceType.TTYPE,Orientation.NORTH));
				return L;
			}
			if((top==null || top.hasBottomConnector()==false) && (left==null ||left.hasRightConnector()==false)){
				L.add(new Piece(l,c));
				L.add(new Piece(l,c,PieceType.ONECONN,Orientation.EAST));
				L.add(new Piece(l,c,PieceType.ONECONN,Orientation.SOUTH));
				L.add(new Piece(l,c,PieceType.LTYPE,Orientation.EAST));
				return L;
			}

		}
		if(L.isEmpty()==true) throw new Exception();
		return L;
	
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
	
	
	public boolean change(Piece current,Piece direction,boolean connector ,PieceType type,Orientation ori_true, Orientation ori_false,int l,int c ) {
		
		if(current.isFixed()==false && direction.isFixed()==true && connector==true && current.getType()==type){this.getPiece(l,c).setOrientation(ori_true);this.getPiece(l,c).setFixed(true);return true;}
		if( current.isFixed()==false && direction.isFixed()==true && connector==false && current.getType()==type){this.getPiece(l,c).setOrientation(ori_false);this.getPiece(l,c).setFixed(true);return true;}
		return false;	
	}
	
	public boolean changefalse2(Piece current,Piece direction1,Piece direction2,boolean connector1, boolean connector2 ,PieceType type, Orientation ori_false,int l,int c ) {
		if(current.isFixed()==false &&  direction1.isFixed()==true && direction2.isFixed()==true && connector1==false && connector2==false && current.getType()==type){this.getPiece(l,c).setOrientation(ori_false);this.getPiece(l,c).setFixed(true);return true;}
		return false;	
	}
	
	public boolean changefalse3(Piece current,Piece direction1,Piece direction2,Piece direction3,boolean connector1, boolean connector2 ,boolean connector3,PieceType type, Orientation ori_false,int l,int c ) {
		if(current.isFixed()==false &&  direction1.isFixed()==true && direction2.isFixed()==true && direction3.isFixed()==true  && connector1==false && connector2==false && connector3==false && current.getType()==type){this.getPiece(l,c).setOrientation(ori_false);this.getPiece(l,c).setFixed(true);return true;}
		return false;	
	}
	
	public boolean changetrue2(Piece current,Piece direction1,Piece direction2,boolean connector1, boolean connector2 ,PieceType type, Orientation ori_true,int l,int c ) {
		if(current.isFixed()==false &&  direction1.isFixed()==true && direction2.isFixed()==true && connector1==true && connector2==true && current.getType()==type){this.getPiece(l,c).setOrientation(ori_true);this.getPiece(l,c).setFixed(true);return true;}
		return false;	
	}
	
	public boolean changetrue3(Piece current,Piece direction1,Piece direction2,Piece direction3,boolean connector1, boolean connector2 ,boolean connector3,PieceType type, Orientation ori_true,int l,int c ) {
		if( current.isFixed()==false && direction1.isFixed()==true && direction2.isFixed()==true && direction3.isFixed()==true  && connector1==true && connector2==true && connector3==true && current.getType()==type){this.getPiece(l,c).setOrientation(ori_true);this.getPiece(l,c).setFixed(true);return true;}
		return false;	
	}
	
	public boolean changetruefalse2(Piece current,Piece direction1,Piece direction2,boolean connector1, boolean connector2 ,PieceType type, Orientation ori_true,Orientation ori_false,int l,int c ) {
		if( current.isFixed()==false && direction1.isFixed()==true && direction2.isFixed()==true && connector1==true && connector2==true && current.getType()==type){this.getPiece(l,c).setOrientation(ori_true);this.getPiece(l,c).setFixed(true);return true;}
		if( current.isFixed()==false && direction1.isFixed()==true && direction2.isFixed()==true && connector1==false && connector2==false && current.getType()==type){this.getPiece(l,c).setOrientation(ori_false);this.getPiece(l,c).setFixed(true);return true;}
		return false;	
	}
	public boolean changetruefalseAlt(Piece current,Piece direction1,Piece direction2,boolean connector1, boolean connector2 ,PieceType type, Orientation ori,int l,int c ) {
		if( current.isFixed()==false && direction1.isFixed()==true && direction2.isFixed()==true && connector1==true && connector2==false && current.getType()==type){this.getPiece(l,c).setOrientation(ori);this.getPiece(l,c).setFixed(true);return true;}
		return false;	
	}
	

	
	public boolean changetrue(Piece current,Piece direction,boolean connector ,PieceType type,Orientation ori_true,int l,int c ) {
		if(current.isFixed()==false &&  direction.isFixed()==true && connector==true && current.getType()==type){this.getPiece(l,c).setOrientation(ori_true);this.getPiece(l,c).setFixed(true);return true;}
		return false;
		
	}
	public boolean changefalse(Piece current,Piece direction,boolean connector ,PieceType type,Orientation ori_false,int l,int c ) {
		if( current.isFixed()==false && direction.isFixed()==true && connector==false && current.getType()==type){this.getPiece(l,c).setOrientation(ori_false);this.getPiece(l,c).setFixed(true);return true;}
		return false;
	}
	
	
	////----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public ArrayList<Orientation> piecePossibleResolution(int l , int c) throws Exception {
		
		ArrayList<Orientation> L = new ArrayList<Orientation>();
		Piece p = getPiece(l,c);
		if(p.isFixed()==true)return null;
		//System.out.println("piece :"+p);
		//System.out.println("top :"+top);
		//System.out.println("left "+left);
		
		//----------------------------------------------- TOUJOURS ORIENTATIONS POSSIBLE * 2 ( TRUE ET FALSE ) >= TOTAL POSSIBILITE POUR CHAQUE PIECE
		if(this.isCorner(l, c)) { 
			if(l==0 && c==0) {
				Piece right = rightNeighbor1(p);
				Piece bottom = bottomNeighbor1(p);
				
				// TOUJOURS FIXE 
				if(p.isFixed()==false && p.getType()==PieceType.VOID) {return L;}
				if(p.isFixed()==false && p.getType()==PieceType.LTYPE) {this.getPiece(l,c).setOrientation(Orientation.EAST);this.getPiece(l,c).setFixed(true);return L;}
				
				// RESTE CEUX NON FIXE (ONECONN) ET VIDE 
				// SI VIDE DROITE
				boolean d=change(p,right,right.hasLeftConnector() ,PieceType.ONECONN,Orientation.EAST, Orientation.SOUTH,l,c);
				
				// SI VIDE BAS
				boolean b=change(p,bottom,bottom.hasTopConnector() ,PieceType.ONECONN,Orientation.SOUTH, Orientation.EAST,l,c);			
				
				if(d== true || b==true)return L;
				L.add(Orientation.SOUTH); 
				L.add(Orientation.EAST);
				return L;
			}
			if(l==0 && c == width-1) {
				Piece left = leftNeighbor1(p);
				Piece bottom = bottomNeighbor1(p);
				// TOUJOURS FIXE 
				if(p.isFixed()==false && p.getType()==PieceType.VOID) {return L;}
				if(p.isFixed()==false && p.getType()==PieceType.LTYPE) {this.getPiece(l,c).setOrientation(Orientation.SOUTH);this.getPiece(l,c).setFixed(true);return L;}
				// RESTE CEUX NON FIXE (ONECONN) ET VIDE 
				// SI VIDE GAUCHE
				boolean g=change(p,left,left.hasRightConnector() ,PieceType.ONECONN,Orientation.WEST, Orientation.SOUTH,l,c);
				
				// SI VIDE BAS
				boolean b=change(p,bottom,bottom.hasTopConnector() ,PieceType.ONECONN,Orientation.SOUTH, Orientation.WEST,l,c);
				// RAJOUTER QUE C QUE POUR CAS ONECONN QU'ON A PAS ARRIVE A FIXE EN FONCTION DE SES VOISINS, SI PLUS DE PIECE METTRE DES IF
				if(g== true || b==true)return L;
				L.add(Orientation.WEST); 
				L.add(Orientation.SOUTH);
				return L;
			}
			
			if(l==height-1 && c==0) {
				Piece top = topNeighbor1(p);
				Piece right = rightNeighbor1(p);
				// TOUJOURS FIXE 
				if(p.isFixed()==false && p.getType()==PieceType.VOID) {return L;}
				if(p.isFixed()==false && p.getType()==PieceType.LTYPE) {this.getPiece(l,c).setOrientation(Orientation.NORTH);this.getPiece(l,c).setFixed(true);return L;}
				// RESTE CEUX NON FIXE (ONECONN) ET VIDE 
				// SI VIDE DROIT
				boolean d=change(p,right,right.hasLeftConnector() ,PieceType.ONECONN,Orientation.EAST, Orientation.NORTH,l,c);
				
				// SI VIDE BAS
				boolean b=change(p,top,top.hasBottomConnector() ,PieceType.ONECONN,Orientation.NORTH, Orientation.EAST,l,c);
				// RAJOUTER QUE C QUE POUR CAS ONECONN QU'ON A PAS ARRIVE A FIXE EN FONCTION DE SES VOISINS, SI PLUS DE PIECE METTRE DES IF
				if(d== true || b==true)return L;
				
				L.add(Orientation.EAST); 
				L.add(Orientation.NORTH);
				return L;

				
			}
			
			if(l==height-1 && c == width-1) {
				Piece top = topNeighbor1(p);
				Piece left = leftNeighbor1(p);
				// TOUJOURS FIXE 
				if(p.isFixed()==false && p.getType()==PieceType.VOID) {return L;}
				if(p.isFixed()==false && p.getType()==PieceType.LTYPE) {this.getPiece(l,c).setOrientation(Orientation.WEST);this.getPiece(l,c).setFixed(true);return L;}
				
				// RESTE CEUX NON FIXE (ONECONN) ET VIDE 
				// SI VIDE GAUCHE
				boolean g=change(p,left,left.hasRightConnector() ,PieceType.ONECONN,Orientation.WEST, Orientation.NORTH,l,c);
				
				// SI VIDE Haut
				boolean b=change(p,top,top.hasBottomConnector() ,PieceType.ONECONN,Orientation.NORTH, Orientation.WEST,l,c);
			
				// RAJOUTER QUE C QUE POUR CAS ONECONN QU'ON A PAS ARRIVE A FIXE EN FONCTION DE SES VOISINS, SI PLUS DE PIECE METTRE DES IF
				if(g== true || b==true)return L;
				L.add(Orientation.WEST); 
				L.add(Orientation.NORTH);
				return L;			
				
			}
		
		}
		//-----------------------------------------------
		if(this.isBorderLine(l, c)) {
			if(l==0) {
				Piece left = leftNeighbor1(p);
				Piece right = rightNeighbor1(p);
				Piece bottom = bottomNeighbor1(p);
				// TOUJOURS FIXE 
				if(p.isFixed()==false && p.getType()==PieceType.VOID) {return L;}
				if(p.isFixed()==false && p.getType()==PieceType.TTYPE) {this.getPiece(l,c).setOrientation(Orientation.SOUTH);this.getPiece(l,c).setFixed(true);return L;}
				if(p.isFixed()==false && p.getType()==PieceType.BAR) {this.getPiece(l,c).setOrientation(Orientation.EAST);this.getPiece(l,c).setFixed(true);return L;}
				
				// --- ONECONN --- 
				if(p.getType()==PieceType.ONECONN) {
	
					//  GAUCHE FIXE
					boolean g=changetrue(p,left,left.hasRightConnector() ,PieceType.ONECONN,Orientation.WEST,l,c);
					
					//  DROIT  FIXE
					boolean d=changetrue(p,right,right.hasLeftConnector() ,PieceType.ONECONN,Orientation.EAST,l,c);
					
					//  BAS FIXE
					boolean b=changetrue(p,bottom,bottom.hasTopConnector() ,PieceType.ONECONN,Orientation.SOUTH,l,c);
					
					//  GAUGE DROIT , VIDE GAUCHE BAS , DROIT BAS  (TOUS FALSE)
					boolean gd=changefalse2(p,left,right,left.hasRightConnector(),right.hasLeftConnector() ,PieceType.ONECONN,Orientation.SOUTH,l,c);
					boolean gb=changefalse2(p,left,bottom,left.hasRightConnector(),bottom.hasTopConnector() ,PieceType.ONECONN,Orientation.EAST,l,c);
					boolean db=changefalse2(p,right,bottom,right.hasLeftConnector(),bottom.hasTopConnector()  ,PieceType.ONECONN,Orientation.WEST,l,c);
					
					
					//SINON 
					if(g==true || d==true || b==true || gd==true || gb==true || db==true)return L;
					L.add(Orientation.WEST); 
					L.add(Orientation.SOUTH);
					L.add(Orientation.EAST);
					return L;
		
				}
				// ---  LTYPE --- 
				if(p.getType()==PieceType.LTYPE) {
					
					// DROIT FIXE
					boolean d=change(p,right,right.hasLeftConnector() ,PieceType.LTYPE,Orientation.EAST, Orientation.SOUTH,l,c);
					// GAUCHE FIXE
					boolean g=change(p,left,left.hasRightConnector() ,PieceType.LTYPE,Orientation.SOUTH, Orientation.EAST,l,c);
					
					// BAS FIXE ET DROIT NULL 
					boolean bd = changetruefalseAlt(p,bottom,right,bottom.hasTopConnector(),right.hasLeftConnector(),PieceType.LTYPE,Orientation.SOUTH ,l,c);
					
					// BAS FIXE ET GAUCHE NULL 
					boolean bg = changetruefalseAlt(p,bottom,left,bottom.hasTopConnector(),left.hasRightConnector(),PieceType.LTYPE,Orientation.EAST ,l,c);
					
					if(g==true || d==true || bd==true || bg==true)return L;
					L.add(Orientation.EAST); 
					L.add(Orientation.SOUTH);
					return L;

				}
				
				System.out.println(this.getPiece(l,c).getType());

			}
			if(l==height-1) {
				Piece left = leftNeighbor1(p);
				Piece right = rightNeighbor1(p);
				Piece top = topNeighbor1(p);
				// TOUJOURS FIXE 
				if(p.isFixed()==false && p.getType()==PieceType.VOID) {this.getPiece(l,c).setFixed(true);return L;}
				if(p.isFixed()==false && p.getType()==PieceType.TTYPE) {this.getPiece(l,c).setOrientation(Orientation.NORTH);this.getPiece(l,c).setFixed(true);return L;}
				if(p.isFixed()==false && p.getType()==PieceType.BAR) {this.getPiece(l,c).setOrientation(Orientation.EAST);this.getPiece(l,c).setFixed(true);return L;}
				// --- ONECONN --- 
				if(p.getType()==PieceType.ONECONN) {
	
					//  GAUCHE FIXE
					boolean g=changetrue(p,left,left.hasRightConnector() ,PieceType.ONECONN,Orientation.WEST,l,c);
					
					//  DROIT  FIXE
					boolean d=changetrue(p,right,right.hasLeftConnector() ,PieceType.ONECONN,Orientation.EAST,l,c);
					
					//  HAUT FIXE
					boolean b=changetrue(p,top,top.hasBottomConnector() ,PieceType.ONECONN,Orientation.NORTH,l,c);
					
					//  GAUGE DROIT ,  GAUCHE BAS , DROIT BAS  (TOUS FALSE)
					boolean gd=changefalse2(p,left,right,left.hasRightConnector(),right.hasLeftConnector() ,PieceType.ONECONN,Orientation.NORTH,l,c);
					boolean gb=changefalse2(p,left,top,left.hasRightConnector(),top.hasBottomConnector() ,PieceType.ONECONN,Orientation.EAST,l,c);
					boolean db=changefalse2(p,right,top,right.hasLeftConnector(),top.hasBottomConnector()  ,PieceType.ONECONN,Orientation.WEST,l,c);
					
					//SINON 
					if(g==true || d==true || b==true || gd==true || gb==true || db==true)return L;
					L.add(Orientation.WEST); 
					L.add(Orientation.NORTH);
					L.add(Orientation.EAST);
					return L;
		
				}
				// ---  LTYPE --- 
				if(p.getType()==PieceType.LTYPE) {
					
					// DROIT FIXE
					boolean d=change(p,right,right.hasLeftConnector() ,PieceType.LTYPE,Orientation.NORTH, Orientation.WEST,l,c);
					// GAUCHE FIXE
					boolean g=change(p,left,left.hasRightConnector() ,PieceType.LTYPE,Orientation.WEST, Orientation.NORTH,l,c);
					
					// HAUT FIXE ET DROIT NULL
					boolean hd = changetruefalseAlt(p,top,right,top.hasBottomConnector(),right.hasLeftConnector(),PieceType.LTYPE,Orientation.WEST ,l,c);
					
					// HAUT FIXE ET GAUCHE NULL 
					boolean hg = changetruefalseAlt(p,top,left,top.hasBottomConnector(),left.hasRightConnector(),PieceType.LTYPE,Orientation.NORTH ,l,c);
				
					//SINON
					if(g==true || d==true || hd==true || hg==true)return L;
					L.add(Orientation.WEST); 
					L.add(Orientation.NORTH);
					return L;

				}
				
			}

		}
		//-----------------------------------------------
		if(this.isBorderColumn(l, c)) {
			if(c==0) {
				Piece right = rightNeighbor1(p);
				Piece top = topNeighbor1(p);
				Piece bottom = bottomNeighbor1(p);
				
				// TOUJOURS FIXE 
				if(p.isFixed()==false && p.getType()==PieceType.VOID) {return L;}
				if(p.isFixed()==false && p.getType()==PieceType.TTYPE) {this.getPiece(l,c).setOrientation(Orientation.EAST);this.getPiece(l,c).setFixed(true);return L;}
				if(p.isFixed()==false && p.getType()==PieceType.BAR) {this.getPiece(l,c).setOrientation(Orientation.NORTH);this.getPiece(l,c).setFixed(true);return L;}
				
				
				if(p.getType()==PieceType.ONECONN) {
					
					//  BAS FIXE
					boolean g=changetrue(p,bottom,bottom.hasTopConnector() ,PieceType.ONECONN,Orientation.SOUTH,l,c);
					
					//  DROIT  FIXE
					boolean d=changetrue(p,right,right.hasLeftConnector() ,PieceType.ONECONN,Orientation.EAST,l,c);
					
					//  HAUT FIXE
					boolean b=changetrue(p,top,top.hasBottomConnector() ,PieceType.ONECONN,Orientation.NORTH,l,c);
					
					//  BAS DROIT ,  BAS HAUT , HAUT DROIT  (TOUS FALSE)
					boolean gd=changefalse2(p,bottom,right,bottom.hasTopConnector(),right.hasLeftConnector() ,PieceType.ONECONN,Orientation.NORTH,l,c);
					boolean gb=changefalse2(p,bottom,top,bottom.hasTopConnector(),top.hasBottomConnector() ,PieceType.ONECONN,Orientation.EAST,l,c);
					boolean db=changefalse2(p,right,top,right.hasLeftConnector(),top.hasBottomConnector()  ,PieceType.ONECONN,Orientation.SOUTH,l,c);
					
					//SINON 
					if(g==true || d==true || b==true || gd==true || gb==true || db==true)return L;
					L.add(Orientation.SOUTH); 
					L.add(Orientation.NORTH);
					L.add(Orientation.EAST); 
					return L;
		
				}
				// ---  LTYPE --- 
				if(p.getType()==PieceType.LTYPE) {
					
					// HAUT FIXE
					boolean d=change(p,top,top.hasBottomConnector() ,PieceType.LTYPE,Orientation.NORTH, Orientation.EAST,l,c);
					// BAS FIXE
					boolean g=change(p,bottom,bottom.hasTopConnector() ,PieceType.LTYPE,Orientation.EAST, Orientation.NORTH,l,c);
					
					// DROIT FIXE ET HAUT NULL
					boolean dh = changetruefalseAlt(p,right,top,right.hasLeftConnector(),top.hasBottomConnector(),PieceType.LTYPE,Orientation.EAST ,l,c);
					
					
					// DROIT FIXE ET BAS NULL 
					boolean db = changetruefalseAlt(p,right,bottom,right.hasLeftConnector(),bottom.hasTopConnector(),PieceType.LTYPE,Orientation.NORTH ,l,c);
					
				
					//SINON
					if(g==true || d==true || dh==true || db==true)return L;
					L.add(Orientation.EAST); 
					L.add(Orientation.NORTH);
					return L;

				}
				
			}
			if(c==width-1) {
				Piece left = leftNeighbor1(p);
				Piece top = topNeighbor1(p);
				Piece bottom = bottomNeighbor1(p);
				
				// TOUJOURS FIXE 
				if(p.isFixed()==false && p.getType()==PieceType.VOID) {return L;}
				if(p.isFixed()==false && p.getType()==PieceType.TTYPE) {this.getPiece(l,c).setOrientation(Orientation.WEST);this.getPiece(l,c).setFixed(true);return L;}
				if(p.isFixed()==false && p.getType()==PieceType.BAR) {this.getPiece(l,c).setOrientation(Orientation.NORTH);this.getPiece(l,c).setFixed(true);return L;}
				
				
				if(p.getType()==PieceType.ONECONN) {
					
					//  BAS FIXE
					boolean g=changetrue(p,bottom,bottom.hasTopConnector() ,PieceType.ONECONN,Orientation.SOUTH,l,c);
					
					//  GAUCHE  FIXE
					boolean d=changetrue(p,left,left.hasRightConnector() ,PieceType.ONECONN,Orientation.WEST,l,c);
					
					//  HAUT FIXE
					boolean b=changetrue(p,top,top.hasBottomConnector() ,PieceType.ONECONN,Orientation.NORTH,l,c);
					
					//  BAS DROIT ,  BAS HAUT , HAUT DROIT  (TOUS FALSE)
					boolean gd=changefalse2(p,bottom,left,bottom.hasTopConnector(),left.hasRightConnector() ,PieceType.ONECONN,Orientation.NORTH,l,c);
					boolean gb=changefalse2(p,bottom,top,bottom.hasTopConnector(),top.hasBottomConnector() ,PieceType.ONECONN,Orientation.WEST,l,c);
					boolean db=changefalse2(p,left,top,left.hasRightConnector(),top.hasBottomConnector()  ,PieceType.ONECONN,Orientation.SOUTH,l,c);
					
					//SINON 
					if(g==true || d==true || b==true || gd==true || gb==true || db==true)return L;
					L.add(Orientation.SOUTH); 
					L.add(Orientation.NORTH);
					L.add(Orientation.WEST); 
					return L;
		
				}
				// ---  LTYPE --- 
				if(p.getType()==PieceType.LTYPE) {
					
					// HAUT FIXE
					boolean d=change(p,top,top.hasBottomConnector() ,PieceType.LTYPE,Orientation.WEST, Orientation.SOUTH,l,c);
					// BAS FIXE
					boolean g=change(p,bottom,bottom.hasTopConnector() ,PieceType.LTYPE,Orientation.SOUTH, Orientation.WEST,l,c);
					
					// GAUCHE FIXE ET HAUT NULL
					boolean gh = changetruefalseAlt(p,left,top,left.hasRightConnector(),top.hasBottomConnector(),PieceType.LTYPE,Orientation.SOUTH,l,c);
					
					
					// GAUCHE FIXE ET BAS NULL
					boolean gb = changetruefalseAlt(p,left,bottom,left.hasRightConnector(),bottom.hasTopConnector(),PieceType.LTYPE,Orientation.WEST ,l,c);
				
					//SINON
					if(g==true || d==true || gh==true || gb==true)return L;
					L.add(Orientation.WEST); 
					L.add(Orientation.SOUTH);
					return L;

				}
								
				
			}

		}
		//-----------------------------------------------
		if(!this.isBorderColumn(l, c) && !this.isBorderLine(l, c) && !this.isCorner(l, c) ) {
			Piece right = rightNeighbor1(p);
			Piece left = leftNeighbor1(p);
			Piece top = topNeighbor1(p);
			Piece bottom = bottomNeighbor1(p);
			
			// TOUJOURS FIXE 
			if(p.isFixed()==false && p.getType()==PieceType.VOID) {return L;}
			if(p.isFixed()==false && p.getType()==PieceType.FOURCONN) {this.getPiece(l,c).setOrientation(Orientation.NORTH);this.getPiece(l,c).setFixed(true);return L;}
			
			
			
			if(p.getType()==PieceType.ONECONN) {
				//  HAUT FIXE
				boolean h=changetrue(p,top,top.hasBottomConnector() ,PieceType.ONECONN,Orientation.NORTH,l,c);
				
				//  BAS FIXE
				boolean b=changetrue(p,bottom,bottom.hasTopConnector() ,PieceType.ONECONN,Orientation.SOUTH,l,c);
				
				//  GAUCHE  FIXE
				boolean g=changetrue(p,left,left.hasRightConnector() ,PieceType.ONECONN,Orientation.WEST,l,c);
				
				//  DROIT FIXE
				boolean d=changetrue(p,right,right.hasLeftConnector() ,PieceType.ONECONN,Orientation.EAST,l,c);
				
				//  3 FIXE FAUX 
				boolean f1=changefalse3(p,bottom,left,right,bottom.hasTopConnector(),left.hasRightConnector(),right.hasLeftConnector() ,PieceType.ONECONN,Orientation.NORTH,l,c);
				boolean f2=changefalse3(p,bottom,top,left,bottom.hasTopConnector(),top.hasBottomConnector(),left.hasRightConnector() ,PieceType.ONECONN,Orientation.EAST,l,c);
				boolean f3=changefalse3(p,left,top,right,left.hasRightConnector(),top.hasBottomConnector(),right.hasLeftConnector() ,PieceType.ONECONN,Orientation.SOUTH,l,c);
				boolean f4=changefalse3(p,bottom,top,right,bottom.hasTopConnector(),top.hasBottomConnector(),right.hasLeftConnector()  ,PieceType.ONECONN,Orientation.WEST,l,c);
				
				//SINON 
				if(h==true || b==true || g==true || d==true || f1==true || f2==true || f3==true || f4==true)return L;
				L.add(Orientation.EAST); 
				L.add(Orientation.SOUTH); 
				L.add(Orientation.NORTH);
				L.add(Orientation.WEST);
				return L;
	
			}
			if(p.getType()==PieceType.BAR) {
				
				// HAUT
				boolean h = change(p,top,top.hasBottomConnector() ,PieceType.BAR,Orientation.NORTH, Orientation.EAST,l,c);
				// BAS
				boolean b = change(p,bottom,bottom.hasTopConnector() ,PieceType.BAR,Orientation.NORTH, Orientation.EAST,l,c);
				// DROIT
				boolean d=change(p,right,right.hasLeftConnector() ,PieceType.BAR,Orientation.EAST, Orientation.NORTH,l,c);
				// GAUCHE
				boolean g=change(p,left,left.hasRightConnector() ,PieceType.BAR,Orientation.EAST, Orientation.NORTH,l,c);
				
				if(h==true || b==true || d==true || g==true)return L;
				L.add(Orientation.EAST); 
				L.add(Orientation.NORTH);
				return L;
				
			}
			if(p.getType()==PieceType.TTYPE) {
				// HAUT FAUX
				boolean h=changefalse(p,top,top.hasBottomConnector() ,PieceType.TTYPE,Orientation.SOUTH,l,c);
				
				// BAS FAUX
				boolean b=changefalse(p,bottom,bottom.hasTopConnector() ,PieceType.TTYPE,Orientation.NORTH,l,c);
				
				// DROIT FAUX
				boolean d=changefalse(p,right,right.hasLeftConnector() ,PieceType.TTYPE,Orientation.WEST,l,c);
				
				
				// GAUCHE FAUX
				boolean g=changefalse(p,left,left.hasRightConnector() ,PieceType.TTYPE,Orientation.EAST,l,c);
				
				// 3 FIXE VRAIE 
				boolean f1=changetrue3(p,bottom,left,right,bottom.hasTopConnector(),left.hasRightConnector(),right.hasLeftConnector() ,PieceType.TTYPE,Orientation.SOUTH,l,c);
				boolean f2=changetrue3(p,bottom,top,left,bottom.hasTopConnector(),top.hasBottomConnector(),left.hasRightConnector() ,PieceType.TTYPE,Orientation.WEST,l,c);
				boolean f3=changetrue3(p,left,top,right,left.hasRightConnector(),top.hasBottomConnector(),right.hasLeftConnector() ,PieceType.TTYPE,Orientation.NORTH,l,c);
				boolean f4=changetrue3(p,bottom,top,right,bottom.hasTopConnector(),top.hasBottomConnector(),right.hasLeftConnector()  ,PieceType.TTYPE,Orientation.EAST,l,c);
				
				if(h==true || b==true || g==true || d==true || f1==true || f2==true || f3==true || f4==true)return L;
				
				L.add(Orientation.EAST); 
				L.add(Orientation.NORTH);
				L.add(Orientation.WEST); 
				L.add(Orientation.SOUTH);
				return L;
				
			}
			if(p.getType()==PieceType.LTYPE) {

				boolean hd=changetruefalse2(p,top,right,top.hasBottomConnector(),right.hasLeftConnector() ,PieceType.LTYPE,Orientation.NORTH,Orientation.SOUTH,l,c);
				boolean db=changetruefalse2(p,right,bottom,right.hasLeftConnector(),bottom.hasTopConnector(),PieceType.LTYPE,Orientation.EAST,Orientation.WEST,l,c);
				boolean bg=changetruefalse2(p,bottom,left,bottom.hasTopConnector(),left.hasRightConnector() ,PieceType.LTYPE,Orientation.SOUTH,Orientation.NORTH,l,c);
				boolean gh=changetruefalse2(p,left,top,left.hasRightConnector(),top.hasBottomConnector() ,PieceType.LTYPE,Orientation.WEST,Orientation.EAST,l,c);
				
				//
				
				changetruefalseAlt(p,right,bottom,right.hasLeftConnector(),bottom.hasTopConnector(),PieceType.LTYPE,Orientation.NORTH ,l,c);
				
				boolean hdf=changetruefalseAlt(p,top,right,top.hasBottomConnector(),right.hasLeftConnector() ,PieceType.LTYPE,Orientation.WEST,l,c);
				boolean hdf1=changetruefalseAlt(p,top,right,right.hasLeftConnector(),top.hasBottomConnector(),PieceType.LTYPE,Orientation.EAST,l,c);
				
				boolean dbf=changetruefalseAlt(p,right,bottom,right.hasLeftConnector(),bottom.hasTopConnector(),PieceType.LTYPE,Orientation.NORTH,l,c);
				boolean dbf1=changetruefalseAlt(p,right,bottom,bottom.hasTopConnector(),right.hasLeftConnector(),PieceType.LTYPE,Orientation.SOUTH,l,c);
				
				boolean bgf=changetruefalseAlt(p,bottom,left,bottom.hasTopConnector(),left.hasRightConnector() ,PieceType.LTYPE,Orientation.EAST,l,c);
				boolean bgf1=changetruefalseAlt(p,bottom,left,left.hasRightConnector(),bottom.hasTopConnector(),PieceType.LTYPE,Orientation.WEST,l,c);
				
				boolean ghf=changetruefalseAlt(p,left,top,left.hasRightConnector(),top.hasBottomConnector() ,PieceType.LTYPE,Orientation.SOUTH,l,c);
				boolean ghf1=changetruefalseAlt(p,left,top,top.hasBottomConnector(),left.hasRightConnector(),PieceType.LTYPE,Orientation.NORTH,l,c);
				
				
				
				
				if(hdf1==true || dbf1==true || bgf1==true || ghf1==true)return L;
				if(hd==true || db==true || bg==true || gh==true || hdf==true || dbf==true || bgf==true || ghf==true)return L;
				L.add(Orientation.EAST); 
				L.add(Orientation.NORTH);
				L.add(Orientation.WEST); 
				L.add(Orientation.SOUTH);
				return L;
			}
			


		}
		System.out.println("ATTENTIONNNNN");
		return null;
	
	}
		
		
		
		
		
		
		

	
	

}
