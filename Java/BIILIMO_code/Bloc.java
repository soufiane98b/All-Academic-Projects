package BIILIMO;

//IMPORT-------------------------------------------------------------------------------------------------------------------------------------------
import java.util.ArrayList;

/**
 * Un Bloc est une classe, qui fait reference un bloc de notre rangee, cette classe permet de compartimenter nos rangees selon des types, un bloc ne peut avoir qu'un seul type.
 * @author Boustique Soufiane
 * @version 1.0.2
 * */

public class Bloc {
	//ATTRIBUTS---------------------------------------------------------------------------------------------------------------------------------------
	
	/**Tableau qui contient nos Lots.
	 */
	private ArrayList<Lot> T_lot;
	/**Taille de notre Bloc.
	 */
	private final int taille;
	/**Le type de notre Bloc, un bloc est defini comme ayant un type.
	 */
	private final  String Type;
	/**Le volume disponible dans notre Bloc, sera mis a jour durant l'execution.
	 */
	private int V_dispo;
	/**Le numero de notre Bloc.
	 */
	private final int num_b;
	
	// CONSTRUCTEUR ---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Cree un nouveau Bloc et ajoute autant de valeurs null a notre Bloc que sa taille, chaque valeur null fait reference a une case vide
	 * @param t type de notre Bloc
	 * @param num numero de notre Bloc
	 * @param taille la taille de notre Bloc
	 * */
	public Bloc(String t ,int num,int taille) {
		Type=t;
		V_dispo = taille;
		num_b=num;
		this.taille=taille;
		T_lot = new ArrayList<Lot>();
		for(int i=0;i<taille;i++) {
			T_lot.add(null);
		}
		
	}
	
	// GETERS  --------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @return la taille de notre Bloc
	 * */
	public int getTaille() {
		return taille;
	}
	/**
	 * @return la type de notre Bloc
	 * */
	public String getType() {
		return Type;
	}
	/**
	 * @return la volume disponible dans notre Bloc
	 * */
	public int getV_dispo() {
		return V_dispo;
	}
	/**
	 * @return la numero de notre Bloc
	 * */
	public int getNum_b() {
		return num_b;
	}
	/**
	 * @return la tableau de lots de notre Bloc
	 * */
	public ArrayList<Lot> getTLot() {
		return T_lot;
	}
	
	//SETERS ----------------------------------------------------------------------------------------------------------------------------------------
	
	/** Modifie le volume disponible dans notre Bloc
	 * @param v_dispo fait reference au volume actuelement disponible
	 * */
	public void setV_dispo(int v_dispo) {
		V_dispo = v_dispo;
	}
	
	//METHODES--------------------------------------------------------------------------------------------------
	
	/** Ajoute un Lot a la fin de notre bloc
	 * @param L le lot a ajouter
	 * @return false si pas assez de places pour rajouter un Lot sinon renvoi true
	 *  */
	public boolean ajoutL(Lot L) throws IllegalArgumentException {
		if(Type.equals(L.getType())==false)throw new IllegalArgumentException() ; 
		if(V_dispo<L.getVolume()) {//Plus de places
			return false;
		}
		else {
			for(int i=0;i<taille;i++) {
				if(T_lot.get(i)==null) {
					for(int j=0;j<L.getVolume();j++) {
						 T_lot.set(j+i,L);
					}
					V_dispo=V_dispo-L.getVolume();
					return true;
				}	
			}
						
		}
		return false;
	}
	
	/** S'utilise dans le cas ou ajoutL renvoi false et donc on supprime le dernier lot pour ajouter un plus grand si assez de place , utilise 2 personnel ( pour supprimer et ajouter) durant le meme pas de temps
	 * @param L le lot a ajouter
	 * @return  false si pas assez de places meme en supprimant le dernier(ou une partie) du lot et donc on accepte pas le lot pour rajouter un Lot sinon renvoi true
	 *  */
	public boolean remplacerFinL(Lot L)throws IllegalArgumentException {
			if(Type.equals(L.getType())==false || V_dispo>=L.getVolume())throw new IllegalArgumentException() ;// soit le type est faux soit il faut utiliser methode ajoutL
			if(L.getVolume()>taille || V_dispo==0 )return false; // car dans tous les cas on ne peut rajouter un volume superieur a la taille du bloc donc on refuse
			int ilast=last();// ne peut etre egal a -1 le last car sinon en contradiction avec les autres
			Lot Last = T_lot.get(ilast);
			int difference=Last.getVolume()+V_dispo-L.getVolume(); 
			if(difference<0)return false;//en renvoi false car meme en elevant le dernier on a pas assez de place donc on vire le lot
			int V_a_enlever=Last.getVolume()-difference;// ce qu il faut enlever du dernier Lot
			for(int i=ilast;V_a_enlever!=0;i--) {
				T_lot.set(i,null);
				V_a_enlever--;
			}
			Last.setVolume(difference);
			V_dispo=L.getVolume();
			if(ajoutL(L)==false)throw new IllegalArgumentException() ;// faute de codage sinon
			//V_dispo=0 car mnt tout le bloc est rempli 
			return true;
			 
		}
		
	/** On retire un Lot du Bloc en remplacant ses occurences par des valeurs null
	 * @param L le lot a retirer
	 * @return  renvoi le prix du Lot
	 * */
	public double retirerL(Lot L) throws IllegalArgumentException {
		if(Type.equals(L.getType())==false)throw new IllegalArgumentException() ;
		int I=T_lot.indexOf(L);
		if(I!=(-1)) {
			T_lot.set(I,null);
			int V =L.getVolume();
			if(V>1) {// on supprime les occurences dans le reste de la rangee
				for(int i=1;i<V;i++) {
					T_lot.set(I+i,null);						
				}
			}
			V_dispo=V_dispo+L.getVolume();
			return L.getPrix()*L.getVolume();
		}
		System.out.println("on essaye d'enlever un lot qui n'existe pas dans la rangee");
		throw new IllegalArgumentException();
			
	}
	
	/** Retire n fois ce type de lot de la fin 
	 * @param n fait reference aux volume de lots a enlever suivant le volume qu'on veut enlever
	 * @return  renvoi le prix des Lot sinon renvoi -1 si pas lots possible a retirer
	 * */
	public double retirerT(int n) throws IllegalArgumentException{
		if(n>taille-V_dispo) { return -1;}
		int T=taille-V_dispo-1;
		double prix=0;
		for(int i=T;i>T-n;i--) {
			Lot l = T_lot.get(i);
			l.setVolume(l.getVolume()-1);
			prix=prix+l.getPrix();
			T_lot.set(i,null);
			
		}
		V_dispo=V_dispo+n;
		return prix;
	}
	
	/** Retire n fois ce type de lot de la fin, deuxième version de retirerT
	 * @param n fait reference aux nombre de lots a enlever 
	 * @return  renvoi le prix des Lot sinon renvoi -1 si pas lots possible a retirer
	 * */
	public double retirerP(int n) throws IllegalArgumentException{
		if(this.nbOuvrierR(n)==-1)return -1;
		int last;
		double T=0;
		for(int i=0;i<n;i++) {
			last=last();
			if(last<0) throw new IllegalArgumentException();
			T=T+retirerL(T_lot.get(last));

		}
		return T;
	}
	
	/** @param n fait reference aux n derniers lots
	 * @return  renvoi le volume Total contenu dans les n derniers Lot sinon renvoi -1 si pas lots possible a compter
	 * */
	public int compterP(int n) throws IllegalArgumentException{
		if(this.nbOuvrierR(n)==-1)return -1;
		int last=last();
		int T=0;
		int cmpt=0;
		for(int i=0;i<n;i++) {
			if(last<0) throw new IllegalArgumentException();
			if(last-cmpt<0) {System.out.println("erreur CompterP");throw new IllegalArgumentException();}
			T=T+T_lot.get(last-cmpt).getVolume();
			cmpt=cmpt+T_lot.get(last-cmpt).getVolume();

		}
		return T;
	}
	
	/** @param n fait reference aux n derniers lots
	 * @return  renvoi le nombre de personnel requis pour retirer un n fois type de lot , renvoi -1 si pas assez de lot dans le bloc
	 * */ 
	public int nbOuvrierR(int n) {
		if(n>taille-V_dispo) { return -1;}
		int T=taille-V_dispo-1;
		ArrayList<Integer> A = new ArrayList<>() ;
		for(int i=T;i>T-n;i--) {
			int ID=T_lot.get(i).getId();
			if(A.contains(ID)==false)A.add(ID);
		}
		return A.size();
	}
	
	/** @param n fait reference aux volume a retirer 
	 * @return  renvoi le prix de construction pour un volume donne pour un certain type de le lot, si pas assez de lot si retourne -1
	 * */ 
	public double prixConstructionV(int n) {
		if(n>taille-V_dispo) { return -1;}
		int T=taille-V_dispo-1;
		double PrixT=0;
		for(int i=T;i>T-n;i--) {
			PrixT=PrixT+T_lot.get(i).getPrix();
		}
		return PrixT;
	}
	
	/** @param L fait au Lot
	 * @return  renvoi le nombre d'ouvrier requis pour stocker le lot ou -1 si pas assez de place pour stocker Lot
	 * */ 
	public int nbOuvrierRStockerL(Lot L){
		if(Type.equals(L.getType())==false)throw new IllegalArgumentException() ; 
		if(L.getVolume()>taille || V_dispo==0)return -1;
		if(V_dispo<L.getVolume()) {
			int ilast=last();
			Lot Last = T_lot.get(ilast);
			int difference=Last.getVolume()+V_dispo-L.getVolume(); 
			if(difference<0)return -1;
			return 2;
		}
		return 1;
	}
	
	/** @return  retourne l'index du dernier element non nul, si retourne -1 alors tous les elements sont nuls
	 * */ 
	public int last() {
		if(V_dispo==0)return taille -1;
		return T_lot.indexOf(null)-1;
	}
	
	/** Permet d'afficher un lot sous la forme: B numero_du_bloc volumeDisponible || Nos lots ||
	 * */
	@Override
	public String toString() {
		String F="||"+"B"+num_b+":"+V_dispo
				+","+Type+"||";
		for(int i=0;i<taille;i++) {
			if(T_lot.get(i)!=null)F=F+"["+T_lot.get(i).toString()+"]"+",";
			else {F=F+"Vide,";}
		}
		F=F+"|| ";
		return F;
	}

}
