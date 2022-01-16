package fr.dauphine.JavaAvance.GUI;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Solve.Generator;

public class GridTest {
	Grid g = new Grid(10,10);
	ArrayList<ArrayList<Integer>> L = new ArrayList<>();

	@Before
	public void initGrid() {
		Generator.generateGridSolved(g);
		ArrayList<Integer> l1 = new ArrayList<>();
		l1.add(3);
		l1.add(7);
		ArrayList<Integer> l2 = new ArrayList<>();
		l2.add(2);
		l2.add(10);
		L.add(l1);
		L.add(l2);
		
		
	}
	
	@After
	public void tearDown(){
		g = new Grid(10,10);
		L= new ArrayList<>();
	}

	@Test
	public void fixPiece() {
		Random rand = new Random();
		Piece p = g.getPiece(2,2);
		Orientation ori = p.getOrientation();
		// on fixe toutes les pieces autour 
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				if(i!=2 || j!=2)g.getPiece(i,j).setFixed(true);
			}
		}	
		// retourne la piece aléatoirement
		ArrayList<Orientation> possibOri = p.getPossibleOrientations();
		int or = rand.nextInt(possibOri.size());
		p.setOrientation(possibOri.get(or).getValuefromOri());
		
		// testons mnt si fixPiece marche correctement
		ArrayList<Orientation> L = g.fixPiece(p);
		assertEquals(ori,L.get(0));
				
	}
	
	@Test
	public void copyGrid() {
		Grid copy = g.copyGrid();
		for(int i=0;i<g.getHeight();i++) {
			for(int j=0;j<g.getHeight();j++) {
				assertEquals(copy.getPiece(i, j),g.getPiece(i, j));
			}
		}	
	}
	
	@Test
	public void heuristiqueMax() {
		assertEquals(Grid.heuristiqueMax(L), 1);
	}
	
	@Test
	public void heuristiqueMoyenne() {
		assertEquals(Grid.heuristiqueMoyenne(L),1);
	}
	
	
	@Test
	public void pieceSelection_first() {
		Piece p = g.getPiece(2,2);
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				if(i!=2 || j!=2)g.getPiece(i,j).setFixed(true);
				
			}
		}

		assertEquals(g.pieceSelection_first(false).getKey(),p);

	}
	
	@Test
	public void piece_sortOrientation() {
		Piece p = g.getPiece(2,2);
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				if(i!=2 || j!=2)g.getPiece(i,j).setFixed(true);
				
			}
		}
		ArrayList<Integer> fixedPiece = new ArrayList<Integer>();
		Pair<Piece, ArrayList<Orientation>> po = new Pair<Piece, ArrayList<Orientation>>(p,g.fixPiece(p));
		po= g.piece_sortOrientation(po,fixedPiece);
		assertEquals(po.getKey(),p);
		assertEquals(po.getValue().get(0),p.getOrientation());
		
	}
	
	@Test
	public void isSolved() {
		assertEquals(g.isSolved(),true);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
