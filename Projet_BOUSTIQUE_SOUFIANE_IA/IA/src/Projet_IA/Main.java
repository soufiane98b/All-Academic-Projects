package Projet_IA;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;






public class Main {
	
	static double best_cout_A=0;
	static Scanner s=new Scanner(System.in);
	static int nb,a,i;
	
	public static void main(String [] args) throws Exception {
		
		System.out.println("Combien de ville désirez vous generer alétoirement (choix entre 2 et 40 ) ?");
		nb=s.nextInt();
		while(nb<2 || nb>100) {
			System.out.print("nombre de ville trop grand ou trop petit, réesayez ");
			nb=s.nextInt();
			}
		System.out.println("Tapez 1 pour executer A* , 2 pour Hill-Climbing et 3 pour les deux ");
		a=s.nextInt();
		while(a!=1 && a!=2 && a!=3) {
			System.out.print("mauvaise saisie, réesayez");
			a=s.nextInt();
		}
		System.out.println("Tapez 1 pour generer des images , 0 sinon, attention avec les images le temps de compilation augmente ");
		i=s.nextInt();
		while(i!=0 && i!=1) {
			System.out.print("mauvaise saisie, réesayez");
			i=s.nextInt();
		}
		
		
		SetVille Sinitiale = genererSetVilleAlea(nb);
		if(a==1)simulerAstarAlea(Sinitiale);
		if(a==2)simulerHillClimAlea(Sinitiale);
		if(a==3) {
			simulerAstarAlea(Sinitiale);
			simulerHillClimAlea(Sinitiale);
		}
		
		
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
		
	}
	
	/* Genere aléatoirement un setVille initiale, en fonction du nombre de ville souhaité 
	 * Attention pour hilleClimbing la ville initiale est inclus dans le setVille mais pas dans Astar
	 * car on considère que la ville initiale est déja visité ( donc on enleve ville init dans Astar )
	*/
	static SetVille genererSetVilleAlea(int nb) {
		//Ville initiale 
		
		SetVille init = new SetVille();

		
		for(int i=0;i<nb;i++) {
			int x = (int) (Math.random() * 700);
			int y = (int) (Math.random() * 700);
			Ville v = new Ville(x,y,""+i);
			init.Set.add(v);
		}
		
		return init;  
		
	}
	
	static void simulerAstarAlea(SetVille setInit) throws Exception {
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
		Astar ast = new Astar(setInit.Set.get(0),setInit,null);
		System.out.println("\t---Voici l'etat initiale de A*--- \n\n"+ast.state_int+"\n");
		System.out.println("\t---Execution de A*--- \n");
		long startTime1 = System.nanoTime();
		ast.f_Astar();
        long endTime1 = System.nanoTime();
        long duration1 = (endTime1 - startTime1);
		System.out.println("\n\t--- Cout distance minimale du meilleur chemin  de A*--- \n");
		ast.path_and_cout();
		best_cout_A=ast.cout_distance;
		System.out.println(ast.cout_distance);
		System.out.println("\n\t--- Voici le chemin Optimal de A*--- \n");
		for(State s : ast.path) {
			System.out.println(s);
		}
		System.out.println("\n\t--- Voici le temps d'execution de A*--- \n");
		System.out.println(duration1/1000000 + " ms");
		if(i==1)new View(ast.meilleur_chemin,"A*");
		
		
		
		
		
		
	}
	
	static void simulerHillClimAlea(SetVille setInit) throws Exception {
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
		HillClimbing hc = new HillClimbing(setInit);
		System.out.println("\t---Voici l'etat initiale de Hill Climbing--- \n\n"+hc.init+"\n");
		System.out.println("\t---Execution de Hill Climbing--- \n");
		long startTime1 = System.nanoTime();
		hc.fHC();
        long endTime1 = System.nanoTime();
        long duration1 = (endTime1 - startTime1);
		System.out.println("\n\t--- Cout distance minimale du meilleur chemin de Hill Climbing--- \n");
		System.out.println(State_circuit.cout_d(hc.best.current));
		System.out.println("\n\t--- Voici la chemin Optimal de Hill Climbing--- \n");
		System.out.println(hc.best);
		hc.best.current.Set.add(hc.best.current.Set.get(0));
		System.out.println("\n\t--- Voici le temps d'execution de Hill Climbing--- \n");
		System.out.println(duration1/1000000 + " ms");
		
		System.out.println("\n\t--- Voici le taux d'amélioration par rapport à la solution(état) initiale--- \n");
		double taux =State_circuit.cout_d(hc.init.current)/State_circuit.cout_d(hc.best.current);
		taux=taux-1;
		System.out.println(taux*100+" %");
		
		System.out.println("\n\t--- Voici l'écart à l'optimum (A*) exprimé en taux (si A* est executé)-- \n");
		double taux1 =best_cout_A/State_circuit.cout_d(hc.best.current);
		taux1=taux1-1;
		System.out.println(taux1*100+" %");
		
		if(i==1)new View(hc.best.current,"Hill Climbing");
		
	}

	
}
