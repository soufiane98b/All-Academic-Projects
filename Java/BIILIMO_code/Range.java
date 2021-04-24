package BIILIMO;

//IMPORT-------------------------------------------------------------------------------------------------------------------------------------------
import java.util.ArrayList;

/**
 * Une Range est une classe, qui fait reference a une rangee de l'Entrepot, cette classe contient un certain nombre de types de l'entrepot.
 * Elle permet de mieux compartimenter l'Entrepot. 
 * @author Boustique  Soufiane
 * @version 1.0.2
 * */

public class Range {
	//ATTRIBUTS---------------------------------------------------------------------------------------------------------------------------------------
	
	/** Tableau de nos Blocs.
	 */
	private ArrayList<Bloc> T_Bloc;
	/** Tableau des types de la rangee.
	 * Il y a autant de types que de bloc dans la range.
	 */
	private ArrayList<String> types;
	/** Volume disponible dans notre Rangee.
	 */
	private int V_dispo;
	/** Taille de notre Rangee.
	 */
	private static int Taille;
	/** Numero de notre Rangee.
	 */
	private final int num_r; 
	
	// CONSTRUCTEUR ---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Cree un nouvelle rangee et Initialise nos Blocs
	 * @param T taille d'une range
	 * @param n numero de la rangee
	 * @param t types de notre range
	 * */
	public Range(int T,int n,ArrayList<String> t) {
		V_dispo=T;
		Taille=T;
		num_r=n;
		types = t;
		int d;
		int reste ;
		if(types.size()==0) {d=0;reste=0;}
		else {
			d = Taille/types.size();
			reste = Taille%types.size();
			}
		T_Bloc =new ArrayList<Bloc>();
		for(int i=0;i<types.size();i++) {
			if(reste!=0) {
				T_Bloc.add(new Bloc(types.get(i),i,d+1));
				reste--;
			}
			else{T_Bloc.add(new Bloc(types.get(i),i,d));}
			
		}
		
	}
	

	// GETERS  --------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @return Tableau de nos types
	 * */
	public ArrayList<String> getTypes() {
		return types;
	}
	/**
	 * @return Taille de notre rangee
	 * */
	public static int getTaille() {
		return Taille;
	}
	/**
	 * @return Volume disponible dans la rangee
	 * */
	public int getV_dispo() {
		return V_dispo;
	}
	/**
	 * @return Tableau de nos Bloc
	 * */
	public ArrayList<Bloc> getBloc() {
		return T_Bloc;
	}
	/**
	 * @return Numero de la rangee
	 * */
	public int getNum_r() {
		return num_r;
	}
	
	//SETTERS --------------------------------------------------------------------------------------------------------------------------------------
	
	/** Mets a jour notre volume disponible dans la rangee
	 * @param v_dispo le nouveau volume disponible
	 * */
	public void setV_dispo(int v_dispo) {
		V_dispo = v_dispo;
	}
	
	//METHODES --------------------------------------------------------------------------------------------------
	
	/** Ajoute un Lot a la fin de notre bloc dans la Bloc de son type
	 * @param L le lot a ajouter
	 * @return false si pas assez de places pour rajouter un Lot sinon renvoi true
	 * @see Bloc#ajoutL(Lot L)
	 *  */
	public boolean ajoutL(Lot L) throws IllegalArgumentException { 
		int  i= types.indexOf(L.getType());
		if(i==-1) throw new IllegalArgumentException();// On rajoute le Lot dans la mauvaison range
		boolean B=T_Bloc.get(i).ajoutL(L);
		if(B==true)V_dispo=V_dispo-L.getVolume();
		return B;
		
	}
	
	/** S'utilise dans le cas ou ajoutL renvoi false et donc on supprime le dernier lot pour ajouter un plus grand si assez de place , utilise 2 personnel ( pour supprimer et ajouter) durant le meme pas de temps
	 * @param L le lot a ajouter
	 * @return  false si pas assez de places meme en supprimant le dernier(ou une partie) du lot et donc on accepte pas le lot pour rajouter un Lot sinon renvoi true
	 * @see Bloc#remplacerFinL(Lot L)
	 *  */
	public boolean remplacerFinL(Lot L)throws IllegalArgumentException {
		int  i= types.indexOf(L.getType());
		if(i==-1) throw new IllegalArgumentException();// On rajoute le Lot dans la mauvaison range
		Bloc b=T_Bloc.get(i);
		boolean B=b.remplacerFinL(L);
		if(B==true) {
			V_dispo=V_dispo-b.getV_dispo();
			b.setV_dispo(0);
			}
		return B;
	}
	
	/** On retire un Lot du Bloc en remplacant ses occurences par des valeurs null
	 * @param L le lot a retirer
	 * @return  renvoi le prix du Lot
	 * @see Bloc#retirerL(Lot L)
	 * */
	public double retirerL(Lot L) throws IllegalArgumentException {
		int  i= types.indexOf(L.getType());
		if(i==-1) throw new IllegalArgumentException();// On retire le Lot dans la mauvaison range
		V_dispo=V_dispo+L.getVolume();
		return T_Bloc.get(i).retirerL(L);
	}
	
	/** @param L fait au Lot
	 * @return  renvoi le nombre d'ouvrier requis pour stocker le lot ou -1 si pas assez de place pour stocker Lot
	 * @see Bloc#nbOuvrierRStockerL(Lot L)
	 * */
	public int nbOuvrierRStockerL(Lot L){
		int  i= types.indexOf(L.getType());
		if(i==-1) throw new IllegalArgumentException();// On rajoute le Lot dans la mauvaison range
		return T_Bloc.get(i).nbOuvrierRStockerL(L);
	}
	
	/** Retire n fois ce type de lot de la fin, deuxième version de retirerT
	 * @param n fait reference aux nombre de lots a enlever 
	 * @return  renvoi le prix des Lot sinon renvoi -1 si pas lots possible a retirer
	 * @see	    Bloc#retirerP(int n)
	 * */
	public double retirerP(String type,int n)throws IllegalArgumentException{
		int  i= types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// ce type n existe pas dans cette range
		double prix = T_Bloc.get(i).retirerP(n);
		V_dispo=0;
		for(int j=0;j<T_Bloc.size();j++) {
			V_dispo=V_dispo+T_Bloc.get(j).getV_dispo();
		}
		return prix;
	}
	
	/** Retire n fois ce type de lot de la fin 
	 * @param n fait reference aux volume de lots a enlever 
	 * @return  renvoi le prix des Lot sinon renvoi -1 si pas lots possible a retirer
	 * @see	    Bloc#retirerT(int n)
	 * */
	public double retirerT(String type,int n)throws IllegalArgumentException {
		int  i= types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// ce type n existe pas dans cette range
		double prix=T_Bloc.get(i).retirerT(n);
		if(prix!=-1)V_dispo=V_dispo+n;
		return prix;
	}
	
	/** @param n fait reference aux n derniers lots
	 * @return  renvoi le volume Total contenu dans les n derniers Lot sinon renvoi -1 si pas lots possible a compter
	 * @see	    Bloc#compterP(int n)
	 * */
	public int compterP(String type,int n) throws IllegalArgumentException{
		int  i= types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// ce type n existe pas dans cette range
		return T_Bloc.get(i).compterP(n);
		
	}
	
	/** @param n fait reference aux n derniers lots
	 * @return  renvoi le nombre de personnel requis pour retirer un n fois type de lot , renvoi -1 si pas assez de lot dans le bloc
	 * @see 	Bloc#nbOuvrierR(int n)
	 * */ 
	public int nbOuvrierR(String type,int n) {
		int  i= types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// ce type n existe pas dans cette range
		return T_Bloc.get(i).nbOuvrierR(n);
	}
	
	/** @param n fait reference aux volume a retirer 
	 * @return  renvoi le prix de construction pour un volume donne pour un certain type de le lot, si pas assez de lot si retourne -1
	 * @see	    Bloc#prixConstructionV(int n)
	 * */ 
	public double prixConstructionV(String type,int n) {
		int  i= types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// ce type n existe pas dans cette range
		return T_Bloc.get(i).prixConstructionV(n);
		
	}
	
	/** Permet d'afficher une rangee sous la forme: R numero_de_la_rangee volumeDisponible || Nos Blocs ||
	 * */
	@Override
	public String toString() {
		String F ="R"+num_r+",VD="+V_dispo+" ";
		for(int i=0;i<types.size();i++) {
			F=F+T_Bloc.get(i).toString();
		}
		return F;
		
	}

}