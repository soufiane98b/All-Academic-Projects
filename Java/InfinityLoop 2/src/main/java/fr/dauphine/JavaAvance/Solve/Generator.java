package fr.dauphine.JavaAvance.Solve;


import java.util.ArrayList;
import java.util.Random;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.io.IOException;
/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	private static Grid filledGrid;
	private static Grid solvedGrid;
	
	public static Grid getSolvedGrid() {
		return solvedGrid;
	}

	/**
	 * take a grid with specified heigh and widh and nbcc and put pieces in it, the grid generated is solved thanks to interFilter
	 * if nbcc is specified and valid it creat components
	 * @see interFilter
	 * @param inputGrid    
	 * @return a Grid
	 */
	public static Grid generateGridSolved(Grid inputGrid) {
		inputGrid.initializeGrid(); // Initialisation du Grid avec des pièces VOID
		Random rand = new Random();
		
		if(inputGrid.getNbcc()==-1) {
			for (int i = 0; i < inputGrid.getHeight(); i++) { // Generation d'un solved Grid
				for (int j = 0; j < inputGrid.getWidth(); j++) {
					ArrayList<Pair<PieceType, Orientation>> filter = inputGrid.interFilter(inputGrid.getPiece(i, j));
					Pair<PieceType, Orientation> type = filter.get(rand.nextInt(filter.size()));
					inputGrid.setPiece(i, j, new Piece(i,j,type.getKey(),type.getValue()));
				}
			}
		}
		else {
			if(inputGrid.getHeight()<=inputGrid.getNbcc()) {
				System.out.println("nbcc invalide, lance execution avec nbcc à max possible");
				inputGrid.setNbcc(inputGrid.getHeight()-1);
			}
			int cmpt=0;
			int pas = inputGrid.getHeight()/inputGrid.getNbcc();
			pas--;
			int ComponentDebut=0;
			int ComponentFin=pas;
			int trueHeight=inputGrid.getHeight();
			inputGrid.setHeight(ComponentFin-ComponentDebut+1);
			while(cmpt!=inputGrid.getNbcc()) {
				for (int i = ComponentDebut; i <= ComponentFin; i++) { // Generation d'un solved Grid
					for (int j = 0; j < inputGrid.getWidth(); j++) {
						ArrayList<Pair<PieceType, Orientation>> filter = inputGrid.interFilter(inputGrid.getPiece(i, j));
						Pair<PieceType, Orientation> type = filter.get(rand.nextInt(filter.size()));
						inputGrid.setPiece(i, j, new Piece(i,j,type.getKey(),type.getValue()));
						
					}
				}
				inputGrid.setHeight(inputGrid.getHeight()+(ComponentFin-ComponentDebut+1));
				
				
				ComponentDebut = ComponentFin+1;
				if(cmpt+1==inputGrid.getNbcc()) ComponentFin = ComponentFin+1+pas+(inputGrid.getWidth()%inputGrid.getNbcc());// dernier prend le reste
				else {ComponentFin = ComponentFin+1+pas;}
				cmpt++;
			}
			inputGrid.setHeight(trueHeight);
			
		}
		return inputGrid;
	}
	
	
	/**
	 * build a file of the inputGrid
	 * @param outputFile   
	 * @param inputGrid
	 */
	public static void buildFile(String outputFile, Grid inputGrid) {
		Charset charset = Charset.forName("US-ASCII");
		Path p = FileSystems.getDefault().getPath(outputFile);
		try (BufferedWriter output = Files.newBufferedWriter(p, charset)){
			String text = "";
			text += inputGrid.getWidth() + "\n" + inputGrid.getHeight() + "\n";

			for (int i = 0; i < inputGrid.getHeight(); i++) { // Generation d'un solved Grid
				for (int j = 0; j < inputGrid.getWidth(); j++) {
					text += stringPiece(inputGrid,i,j);
				}
			}
			text = text.substring(0, text.length() - 1);
			output.write(text, 0, text.length());
		}
		catch (IOException e) {
			System.out.println("Erreur fichier");
		}
	}
	
	
		
	/**
	 * take the gird created from generateGridSolved and mix the orientation randomly of it's pieces 
	 * @see generateGridSolved 
	 * @see buildFile
	 * @param fileName
	 * @param inputGrid
	 */
	public static void generateLevel(String fileName, Grid inputGrid) {
			Random rand = new Random();
			inputGrid = generateGridSolved(inputGrid);
			solvedGrid = inputGrid.copyGrid();
			for (int i = 0; i < inputGrid.getHeight(); i++) { // on change posi aléatoirement
				for (int j = 0; j < inputGrid.getWidth(); j++) {
					ArrayList<Orientation> possibOri = inputGrid.getPiece(i, j).getPossibleOrientations();
					int ori = rand.nextInt(possibOri.size());
					inputGrid.getPiece(i, j).setOrientation(possibOri.get(ori).getValuefromOri());
				}
			}
			filledGrid = inputGrid;
			buildFile(fileName, inputGrid);
			System.out.println(inputGrid);
	}
		
	
	/**
	 * @param i
	 * @param j
	 * @param inputGrid
	 * @return a string of the piece for the file
	 */
	public static String stringPiece(Grid inputGrid, int i, int j) {
		return inputGrid.getPiece(i,j).getType().getValuefromType() + " " + inputGrid.getPiece(i,j).getOrientation().getValuefromOri() + "\n";
	}
		

		
	

}