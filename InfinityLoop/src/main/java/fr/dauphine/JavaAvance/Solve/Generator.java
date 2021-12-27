package fr.dauphine.JavaAvance.Solve;


import java.util.ArrayList;
import java.util.Random;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.DisplayUnicode;
import fr.dauphine.JavaAvance.GUI.Grid;

/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	private static Grid filledGrid;

	/**
	 * @param output
	 *            		file name
	 * @throws IOException
	 *             - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	
	public static void main(String [] args) throws Exception {
		/*
		Grid g =new  Grid(3,3);
		for(int i=0;i<g.getHeight();i++) {
			for(int j=0;j<g.getWidth();j++) {
				Piece p = new Piece(i,j,PieceType.ONECONN,Orientation.NORTH);
				g.setPiece(i, j, p);
				
			}
			
		}*/
		
		
		
		
		
		//System.out.println(g);
		//System.out.println(g.isBorderLine(0, 1));
	
		
		
		
		//VOID,ONECONN,BAR,TTYPE,FOURCONN,LTYPE
		//Piece p = new Piece(0,0,PieceType.LTYPE,Orientation.NORTH);
		
		
		//System.out.println(DisplayUnicode.getUnicodeOfPiece(p.getType(), p.getOrientation()));
		
		
		
	}
	public static void generateLevel(String fileName, Grid inputGrid) {
		Grid g = inputGrid;
		for(int i=0;i<inputGrid.getHeight();i++) {
			for(int j=0;j<inputGrid.getWidth();j++) {
				ArrayList<Integer> LP = g.piecePossible(i,j);
				int d = (int) (Math.random() * LP.size());
				Piece p = new Piece(i,j,PieceType.getTypefromValue(LP.get(d), Orientation.NORTH);
				
				System.out.println(d);
				
				
			}
			
		}

		
	}
	public static int[] copyGrid(Grid filledGrid, Grid inputGrid, int i, int j) {
		Piece p;
		int hmax = inputGrid.getHeight();
		int wmax = inputGrid.getWidth();

		if (inputGrid.getHeight() != filledGrid.getHeight())
			hmax = filledGrid.getHeight() + i; // we must adjust hmax to have the height of the original grid
		if (inputGrid.getWidth() != filledGrid.getWidth())
			wmax = filledGrid.getWidth() + j;

		int tmpi = 0;// temporary variable to stock the last index
		int tmpj = 0;

		// DEBUG System.out.println("copyGrid : i =" + i + " & j = " + j);
		// DEBUG System.out.println("hmax = " + hmax + " - wmax = " + wmax);
		for (int x = i; x < hmax; x++) {
			for (int y = j; y < wmax; y++) {
				// DEBUG System.out.println("x = " + x + " - y = " + y);
				p = filledGrid.getPiece(x - i, y - j);
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(p);
				inputGrid.setPiece(x, y, new Piece(x, y, p.getType(), p.getOrientation()));
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(inputGrid.getPiece(x, y));
				tmpj = y;
			}
			tmpi = x;
		}
		//DEBUGSystem.out.println("tmpi =" + tmpi + " & tmpj = " + tmpj);
		return new int[] { tmpi, tmpj };
	}

}