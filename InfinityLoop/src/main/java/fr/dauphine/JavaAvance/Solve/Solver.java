package fr.dauphine.JavaAvance.Solve;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;

public class Solver {

	
	
	public static Grid solve(Grid g) throws Exception {
		int compteur=0;
		Stack<Grid> stack = new Stack<Grid>();
		//Initialisation
		stack.push(g);
		while(stack.isEmpty()==false) {
			Grid tmp = stack.pop();
			compteur++;
			System.out.println("compteur :"+compteur);
			ArrayList<Boolean> Allconnected = new ArrayList<Boolean>();
			ArrayList<Orientation> L = new ArrayList<Orientation>();
			Piece p=null;
			p= Solver.FixBlop2(tmp, L ,Allconnected);
			if(Allconnected.get(0)==true)return tmp; // resolue
			if(p==null)continue; // tout est fixé mais pas résolue dans passe au prochain élement du stack
			else {// On prend une pice non fixé puis la fixe sur tous ses positions possbile
				for(Orientation l : L) {
					Piece newp = new Piece(p.getPosY(),p.getPosX(),p.getType(),l);
					Grid copy_tmp = Generator.copyGrid(tmp);
					newp.setFixed(true);
					copy_tmp.setPiece(newp.getPosY(), newp.getPosX(), newp);
					if(Checker.isSolutiong(copy_tmp)) return copy_tmp;// pas solution avec modfi Orientation , on peut verifier par meme ocasion que tout est fixe ou pas pour retirer et pas ajouter dans stack
					else {
						stack.push(copy_tmp);
					}
					
				}
			}
			
			
			
			
		}

		return null;
		
	}
	
	
	
		
	public static void FixBlop(Grid g) throws Exception {
		boolean allchanged=false;
		int cmpt=0;
		ArrayList<Orientation> L= new ArrayList<Orientation>();
		while(allchanged==false) {
			cmpt++;
			allchanged=true;//true
			for(int i=0;i<g.getHeight();i++) {
				for(int j=0;j<g.getWidth();j++) {
					Piece p = g.getPiece(i,j);
					if(p.isFixed()==false) {//true
						L=g.piecePossibleResolution(i,j);
						if(L!=null && L.isEmpty()) {
							allchanged=false;
						}
					}
				}
			}
		}
	}
	// Va lui appliquer la résolution de dépendance et renvoyer premier element non fixe avec ses Orientation possbile , si tous element fixé renvoi null et biensur regarde si solution 
	public static Piece FixBlop2(Grid g,ArrayList<Orientation> L, ArrayList<Boolean>  Allconnected) throws Exception {
		Piece P_notfixe =null;
		boolean allchanged=false;
		while(allchanged==false) {
			ArrayList<Orientation> tmp= new ArrayList<Orientation>();
			allchanged=true;
			for(int i=0;i<g.getHeight();i++) {
				for(int j=0;j<g.getWidth();j++) {
					Piece p = g.getPiece(i,j);
					if(p.isFixed()==false) { 
						tmp=g.piecePossibleResolution(i,j);
						if(tmp!=null && tmp.isEmpty()) {
							allchanged=false;
						}
					}					
				}
			}
		}
		Allconnected.add(true);
		boolean first=true;
		if(allchanged==true) {
			for(int i=0;i<g.getHeight();i++) {
				for(int j=0;j<g.getWidth();j++) {
					Piece p = g.getPiece(i,j);
					if(p.isFixed()==false && first==true) {
						P_notfixe =p;
						L.addAll(g.piecePossibleResolution(i,j));
						first=false;
					}
					
					
					if(g.isTotallyConnected(g.getPiece(i, j))==false)Allconnected.set(0,false);
				}
			}
			
		}
		//System.out.println(Allconnected);
		return P_notfixe;		
	}
	
	
	
		

	
	
}
