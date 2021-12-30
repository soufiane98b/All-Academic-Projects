package fr.dauphine.JavaAvance.Solve;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.GUI.Grid;


public class Checker {
	
	public static boolean isSolution(String nameFile) throws IOException {
		Path path = Paths.get(nameFile);
		List<String> lines = Files.readAllLines(path);
		int w=Integer.parseInt(lines.get(0));
		int h=Integer.parseInt(lines.get(1));
		Grid g=new Grid(w,h);
		
		int cmpt=2;
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				int p_nb=Integer.parseInt(lines.get(cmpt).substring(1,2));
				int p_ori=Integer.parseInt(lines.get(cmpt).substring(3,4));
				Piece p = new Piece(i,j,p_nb,p_ori);
				g.setPiece(i, j, p);
				cmpt++;		
				}
		}
		
		for(int i=0;i<g.getHeight();i++) {
			for(int j=0;j<g.getWidth();j++) {
				if(g.isTotallyConnected(g.getPiece(i, j))==false)return false;
				}
			}
		return true;
	}
	
	public static boolean isSolutiong(Grid g) throws IOException {
		for(int i=0;i<g.getHeight();i++) {
			for(int j=0;j<g.getWidth();j++) {
				if(g.isTotallyConnected(g.getPiece(i, j))==false)return false;
				}
			}
		return true;
	}
	
	
	public static Grid buildGrid(String nameFile) throws IOException {
		Path path = Paths.get(nameFile);
		List<String> lines = Files.readAllLines(path);
		int w=Integer.parseInt(lines.get(0));
		int h=Integer.parseInt(lines.get(1));
		Grid g=new Grid(w,h);
		
		int cmpt=2;
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				int p_nb=Integer.parseInt(lines.get(cmpt).substring(1,2));
				int p_ori=Integer.parseInt(lines.get(cmpt).substring(3,4));
				Piece p = new Piece(i,j,p_nb,p_ori);
				g.setPiece(i, j, p);
				cmpt++;		
				}
		}
		return g;
	}
	
		
	
	
}
		
	
		


	

