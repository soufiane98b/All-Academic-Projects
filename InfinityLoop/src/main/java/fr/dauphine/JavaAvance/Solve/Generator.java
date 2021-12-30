package fr.dauphine.JavaAvance.Solve;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
	private static Grid solvedGrid;
	
	
	public static void main(String[] args) throws Exception {
		
		int cmpt=0;
		Grid g = new Grid(20,20);
		String name = "/Users/soufiane/Desktop/grid";
		generateLevel(name,g);
		
		///////////////
		System.out.println(solvedGrid);
		Grid toSolve=copyGrid(filledGrid);
		
		//System.out.println(filledGrid);
		
		/*
		ArrayList<Boolean> Allconnected = new ArrayList<Boolean>();
		ArrayList<Orientation> L = new ArrayList<Orientation>();
		Piece p=null;
		p= Solver.tofix_Filter2(partialySolved, L,Allconnected);
		
		
		System.out.println(p);
		System.out.println(L);
		System.out.println(Allconnected.get(0));

		/////
		 */
		
		Grid partialySolved = copyGrid(filledGrid);
		Solver.FixBlop(partialySolved);
		for(int i=0;i<partialySolved.getHeight();i++) {
			for(int j=0;j<partialySolved.getWidth();j++) {
				if(partialySolved.getPiece(i,j).isFixed()==false) {
					//System.out.println(partialySolved.getPiece(i,j));
					cmpt++;}
				}
				
			}
		System.out.println("cmpt :"+cmpt);
		System.out.println(partialySolved);
		System.out.println("Before solving");
		System.out.println(toSolve);
		Grid solved =Solver.solve(toSolve);
		System.out.println("After solving");
		System.out.println(solved);
		System.out.println(Checker.isSolutiong(solved));

	}

	
	/**
	 * @param output
	 *            		file name
	 * @throws IOException
	 *             - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void generateLevel(String fileName, Grid inputGrid) throws Exception {
		Grid g = inputGrid;
		String output = new String();
		output=g.getWidth()+"\n"+g.getHeight()+"\n";
		
		for(int i=0;i<g.getHeight();i++) {
			for(int j=0;j<g.getWidth();j++) {
				g.setPiece(i,j,new Piece(i,j));
				ArrayList<Piece> LP = g.piecePossible(i,j);
				int d = (int) (Math.random() * LP.size());
				g.setPiece(i, j, LP.get(d));
			}
		}
		solvedGrid=copyGrid(g);
		//System.out.println(g);
		//System.out.println(Checker.isSolutiong(g));
		
		for(int i=0;i<g.getHeight();i++) {
			for(int j=0;j<g.getWidth();j++) {
				Piece p = g.getPiece(i,j);
				ArrayList<Orientation> OL = p.getPossibleOrientations();
				int d = (int) (Math.random() * OL.size());
				p= new Piece(i,j,p.getType(),OL.get(d));
				g.setPiece(i, j,p);
				output=output+"("+PieceType.getValuefromType(p.getType())+","+Orientation.getValuefromOri(p.getOrientation())+")\n";
				
				
			}
		}
		//System.out.println(Checker.isSolutiong(g));
		
		Path path = Paths.get(fileName);
		Files.write(path, output.getBytes());
		filledGrid=g;


	}
	
	public static Grid copyGrid(Grid g) {
		Grid copy = new Grid(g.getWidth(),g.getHeight());
		for (int i = 0; i < g.getHeight(); i++) {
			for (int j=0; j < g.getWidth(); j++) {
				Piece current = g.getPiece(i, j);
				PieceType t = current.getType();
				Orientation o = current.getOrientation();
				Piece pcopy = new Piece(i,j,t,o);
				copy.setPiece(i,j,pcopy);
			}
		}
		
		return copy;
	
	}

	
	
	 
	
	
	
	
	
	
	
	
}