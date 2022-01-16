package fr.dauphine.JavaAvance.Solve;



import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.GUI.Grid;


public class Checker {
	
	
	/**
	 * Check if a file representing a grid is solved
	 * @param filename    
	 * @return true if solved , false otherwise
	 */
	public static boolean isSolved(String filename) throws NullPointerException {
		Grid checkGrid = buildGrid(filename);
		if (checkGrid == null)
			throw new NullPointerException();
		for (Piece[] lp : checkGrid.getAllPieces()) {
			for (Piece p : lp) {
				if (!checkGrid.isTotallyConnected(p))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * take a file and convert it in a grid 
	 * @param filename    
	 * @return a Grid 
	 */
	public static Grid buildGrid(String fileName) {
		Grid checkGrid = null;
		Charset charset = Charset.forName("US-ASCII");
		Path p = FileSystems.getDefault().getPath(fileName);
		try (BufferedReader readBuff = Files.newBufferedReader(p, charset)){
			String ligne = "";
			
			int width = 0, height = 0;
			if ((ligne = readBuff.readLine()) != null)
				width = Integer.parseInt(ligne);
			if ((ligne = readBuff.readLine()) != null)
				height = Integer.parseInt(ligne);
			
			checkGrid = new Grid(width, height);
			int i = 0, j = 0;
			while ((ligne = readBuff.readLine()) != null) {
				String[] pieceformat = ligne.split(" ");
				checkGrid.setPiece(i, j , new Piece(i, j, Integer.parseInt(pieceformat[0]), Integer.parseInt(pieceformat[1])));
				j++;
				if (j == width) {
					j = 0; i++;
				}
			}
			if (i != height && j != width)
				throw new IOException();
		} catch (NumberFormatException | IOException | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return checkGrid;
	}
	
	
}
