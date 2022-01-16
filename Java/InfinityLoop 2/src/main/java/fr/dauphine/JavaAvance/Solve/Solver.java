package fr.dauphine.JavaAvance.Solve;

import java.util.ArrayDeque;
import java.util.ArrayList;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.GUI.Grid;

public class Solver {
	
	
	/**
	 * take the inputFile and solve it and store the solution in the outputFile if it's solvable otherwise return null and store 
	 * @param outputFile
	 * @param inputFile
	 * @return return true if the grid is solvable , false otherwise
	 */

	public static boolean solveGridFile(String inputFile, String outputFile) {
		Grid inputGrid = Checker.buildGrid(inputFile);
		System.out.println(inputGrid.getHeight() +""+ inputGrid.getWidth());
		Grid outputGrid = inputGrid.copyGrid();
		//while (outputGrid.fixAllPieces());
		outputGrid.fixAllPieces2();
		System.out.println(outputGrid);
		outputGrid = solveGrid(outputGrid);
		Generator.buildFile(outputFile, outputGrid);
		System.out.println(outputGrid);
		if (outputGrid == null) return false;
		// java jarprojet.jar -g 20x20 -o textGrid.txt
		//java jarprojet.jar -s textGrid.txt -o solvedGrid.txt
		return true;
	}
	
	/**
	 * take a grid and solve it if it's solvable 
	 * @param g_toSolve
	 * @return null if the grid is not solvable otherwise return the solved grid
	 */
	public static Grid solveGrid(Grid g_toSolve) {
		int cpt = 0;
		ArrayDeque<Grid> deque = new ArrayDeque<Grid>();
		deque.push(g_toSolve);
		while (!deque.isEmpty()) {
			Grid g = deque.pop();
			Pair<Piece, ArrayList<Orientation>> po = g.pieceSelection_first(true);
			if (po != null && po.getKey() == null && po.getValue() == null)
				return g;
			else if (po != null && po.getKey() != null && po.getValue() != null) {
				for (Orientation ori : po.getValue()) {
					Grid copyGrid = g.copyGrid();
					Piece p = new Piece(po.getKey().getPosY(),po.getKey().getPosX(),po.getKey().getType(),ori);
					p.setFixed(true);
					copyGrid.setPiece(p.getPosY(), p.getPosX(), p);
					copyGrid.fixedPieces++;
					//while (copyGrid.fixAllPieces());
					copyGrid.fixAllPieces2();
					if (copyGrid.solvable) {
						cpt++;
						int num=(copyGrid.getHeight() * copyGrid.getWidth() - copyGrid.fixedPieces) ;
						System.out.println("nombre de grille testé :"+cpt + ", " + "Nombre de piece non fixé à l'instant t :"+num+ ", " +"taille pile :"+deque.size());
						if (copyGrid.isSolved()) return copyGrid;
						deque.push(copyGrid);
					}
				}
			}
			
		}
		
		return null;
		
	}
	
	/*
	public static void main(String[] args) {
		Grid g = new Grid(100,100);
		g.setNbcc(3);
		Generator.generateLevel("textGrid.txt",g);
		//System.out.println(Generator.solvedGrid);
		boolean solved = solveGridFile("textGrid.txt", "solvedGrid.txt");
		System.out.println("SOLVED :" + solved);
	}*/
	
	
}
