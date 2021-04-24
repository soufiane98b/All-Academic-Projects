package BIILIMO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Un Meuble est une collection de paires (type de lot, volume requis) et est associee e une piece de la maison
 * @author Soufiane BOUSTIQUE, Raphael MALAK.
 * @version 1.0.1
 * @see BIILIMO.Lot
 */
public class Meuble {
	//Attributs
	
	/**Nom du Meuble.
	 */
	private String Nom;
	
	/**Piece de la maison du Meuble. Il est forcement une des pieces suivantes : cuisine, chambre, salle a manger, salon, salle de bain, WC.
	 */
	private String Piece;
	
	/**Duree de construction du Meuble.
	 */
	private int Duree;
	
	/**Ensemble des composants necessaires a la construction du meuble. String pour type de lot et Integer pour volume du Lot
	 */
	private HashMap<String,Integer> composant;
	
	/**Enesemble des pieces de la maison disponible.
	 */
	static String [] Pieces ={"cuisine", "chambre", "salle a manger", "salon", "salle de bain", "WC"};
	
	/**Liste des meubles construits.
	 */
	static ArrayList<Meuble> ListeTousMeublesC = new ArrayList<Meuble>();
	
	/**Liste des meubles non construits.
	 */
	static ArrayList<Meuble> ListeTousMeublesNC = new ArrayList<Meuble>();
	
	/**Permet de savoir si on a commence a apporter les lots necessaire a la construction d'un Meuble.
	 */
	private boolean debute;
	
	// CONSTRUCTEUR ------------------------------------------------------------------------------------
	
	/**Cree un nouveau Meuble.
	 * @param nom Nom du Meuble.
	 * @param pieceMaison Piece de la maison du Meuble. Il est forcement une des pieces suivantes : cuisine, chambre, salle a manger, salon, salle de bain, WC.
	 * @param duree Ensemble des composants necessaires a la construction du meuble. String pour type de lot et Integer pour volume du Lot.
	 * @throws IllegalArgumentException
	 */
	public Meuble(String nom,String pieceMaison,int duree)throws IllegalArgumentException {
		debute=false;
		Nom = nom;
		Piece=pieceMaison;
		Duree=duree;
		boolean b =false;
		for(int i=0;i<Pieces.length;i++) {
			if(Pieces[i].replaceAll(" ","").equalsIgnoreCase(Piece))b=true;
		}
		if(duree<=0 || b==false) {System.out.println("erreur duree ou pieces maison");throw new IllegalArgumentException();}
		composant=new HashMap<String,Integer>();
	}

	//--------------------------------------------------------------------------------------------------
	// GETERS-------------------------------------------------------------------------------------------
	
	/**
	 * @return Renvoie le nom du Meuble.
	 */
	public String getNom() {
		return Nom;
	}
	
	/**
	 * @return Renvoie la piece de la maison du Meuble.
	 */
	public String getPiece() {
		return Piece;
	}
	
	/**
	 * @return Renvoie la duree de construction du Meuble.
	 */
	public int getD_Constru() {
		return Duree;
	}
	
	/**
	 * @return Renvoie la liste des composants necessaires a la construction du Meuble.
	 */
	public Map<String,Integer> getComposant() {
		return composant;
	}
	
	/**
	 * @return Renvoie le booleen qui permet de savoir si on a commence a apporter les lots necessaire a la construction d'un Meuble.
	 */
	public boolean isDebute() {
		return debute;
	}

	//SETTERS-------------------------------------------------------------------------------------------
	
	/**Modifie le nom du Meuble.
	 * @param nom Nouveau nom du Meuble.
	 */
	public void setNom(String nom) {
		Nom = nom;
	}
	
	/**Modifie la piece de la maison du Meuble.
	 * @param pieceMaison Nouvelle piece de la maison.
	 */
	public void setPiece(String pieceMaison) {
		Piece = pieceMaison;
	}
	
	/**Modifie la duree de construction du Meuble.
	 * @param d_Constru Nouvelle duree de construction du Meuble.
	 */
	public void setD_Constru(int d_Constru) {
		Duree = d_Constru;
	}
	
	/**Modifie la liste des composants necessaires a la construction d'un Meuble.
	 * @param composant Nouvelle liste des composants necessaires a la construction d'un Meuble.
	 */
	public void setComposant(HashMap<String,Integer> composant) {
		this.composant = composant;
	}
	
	/**Modifie le booleen qui permet de savoir si on a commence a apporter les lots necessaire a la construction d'un Meuble.
	 * @param debute Nouveau booleen qui permet de savoir si on a commence a apporter les lots necessaire a la construction d'un Meuble.
	 */
	public void setDebute(boolean debute) {
		this.debute = debute;
	}
	
	//--------------------------------------------------------------------------------------------------
	//FONCTIONS DE CLASSE-------------------------------------------------------------------------------
	
	/**Calcul le nombre d'ouvriers requis pour construire un meuble. Ne compte PAS l'ouvrier pour la construction.
	 * @param E L'entrepot dans lequel s'execute le programme.
	 * @return Le nombre d'ouvriers requis pour deplacer les lots pour construire le meuble, ou -1 si pas assez de Lot DANS un certain type du Meuble.
	 */
	int nbOuvrierR(Entrepot E) {
		int T=0,c;
		Set<String> Set = composant.keySet();
		for(String i:Set) {
			c=E.nbOuvrierR(i,composant.get(i));
			if(c==-1)return -1;
			T=T+c;
			
		}
		return T;
		
	}
	
	/**Calcul le prix de revenu d'un Meuble.
	 * @param E L'entrepot dans lequel s'execute le programme.
	 * @return Le prix de revenu du meuble.
	 * @see BIILIMO.Entrepot#prixConstructionV(String, int)
	 */
	double prixConstruction(Entrepot E) {
		double P=0,c;
		Set<String> Set = composant.keySet();
		for(String i:Set) {
			c=E.prixConstructionV(i,composant.get(i));
			if(c==-1)return -1;
			P=P+c;
		}
		return P;
	}
	
	/**Essaie d'apporter les lots necessaires a la construction du Meuble et de le construire.
	 * ATTENTION SI PAS ASSEZ DE LOTS, MEUBLE REFUSE.
	 * @param E L'entrepot dans lequel s'execute le programme.
	 * @return -1 si pas assez de lots dans l'entrepot. 1 si il a ete construit directement. 0 si il y a assez de lots dans l'entrepot, mais pas assez de personnel pour pouvoir finir d'apporter les lots necessaire a sa construction et pour le construire (dans ce cas il faut le mettre en attente).
	 * @throws IllegalStateException
	 */
	public int construireUnMeuble(Entrepot E)throws IllegalStateException {
		int nbP=nbOuvrierR(E);// renvoit -1 si pas assez de lots 
		if(nbP==-1)return -1;
		// on commence par les chef Stock car les ouvriers peuvent servir a autre chose
		ArrayList<Chef> LChefStock=Chef.listChefDispo(nbP,"Stock",1);
		ArrayList<Ouvrier> LOuvrier=Ouvrier.listOuvrier(nbP-LChefStock.size(),"",1);// on incremente leur disponibilite
		Chef C_constructeur=Chef.estDispo("Brico");
		Ouvrier O_constructeur=Ouvrier.estDispoPourConstruire(this.Piece);
		if((LChefStock.size()+LOuvrier.size())==nbP && (C_constructeur!=null || O_constructeur!=null )) {// on a tous le personnel requis pour et tous les lots pour contruire le meuble donc on commence la construction
			double Tprix=0,prix;
			Set<String> Set = composant.keySet();
			for(String i:Set) {// ici on retire tous les lots
				if((prix=E.retirerT(i, composant.get(i)))==-1) throw new IllegalStateException();
				Tprix=Tprix+prix;
			}
			E.setTresorie(E.getTresorie()+Tprix); 
			// les ouvriers ont deja eu un pas de temps en plus 
			if(C_constructeur!=null)C_constructeur.setDispo(this.Duree);
			else {O_constructeur.setDispo(this.Duree);}
			this.debute=true;
			this.composant.clear();
			return 1;// le meuble a ete construit
			
		}
		else {// manque une partie du personnel donc on met en attente
			int nbDR= LChefStock.size()+LOuvrier.size();// correspond au personnel total dont on dispose
			int r;
			double Tprix=0,prix;
			HashMap<String,Integer> composant1=(HashMap<String, Integer>) composant.clone();
			Set<String> Set = composant1.keySet();
			for(String i:Set) {// ici on retire une somme de volumes de lots=nbDR
				r=E.nbOuvrierR(i, composant.get(i));
				if(r==-1)throw new IllegalStateException();
				if(nbDR==0)break;
				this.debute=true;// car on a forcement debute la construction (acheminement) des lots pour le meuble
				if(r>nbDR) {
					this.composant.put(i,this.composant.get(i)-E.compterP(i,nbDR));
					prix=E.retirerP(i,nbDR);
					Tprix=Tprix+prix;
					break;
				}
				
				prix=E.retirerT(i,composant.get(i));
				Tprix=Tprix+prix;
				this.composant.remove(i);
				nbDR=nbDR-r;
			}
			// les ouvriers ont deja eu un pas de temps en plus 
			E.setTresorie(E.getTresorie()+Tprix);
			return 0;
		}

	}
	
	/**Calcule la rentabilite du Meuble en supposant que seul un ChefBrico peut construire un Meuble.
	 * @param E L'entrepot dans lequel s'execute le programme.
	 * @return true si le Meuble est rentable, false sinon.
	 */
	public boolean estRentable(Entrepot E) {
		double prix = this.prixConstruction(E);
		if(prix==-1)return false;	
		if(this.nbOuvrierR(E)*5+this.Duree*10<prix)return true;// Nous supposons que c'est un chef brico qui va toujours contruire nos meubles
		return false;
	}
	
	/**Calcule la rentabilite du Meuble en supposant qu'un Meuble peut etre construit par un ChefBrico ou un Ouvrier.
	 * @param E L'entrepot dans lequel s'execute le programme.
	 * @return true si le Meuble est rentable, false sinon.
	 */
	public boolean estRentable2(Entrepot E) {
		double prix = this.prixConstruction(E);
		if(prix==-1)return false;
		if(this.nbOuvrierR(E)*5+this.Duree*(7.5)<prix)return true;
		return false;
	}
	
	/**Affiche un Meuble.
	 */
	@Override
	public String toString() {
		String S="Nom= "+Nom+", ListeLots= "+composant.toString()+", PieceMaison= "+Piece+", Duree= "+Duree;
		return S;
	}
}
