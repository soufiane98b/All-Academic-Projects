package BIILIMO;

//IMPORT-------------------------------------------------------------------------------------------------------------------------------------------
import java.util.ArrayList;

/**
 * Un Entrepot est une classe, qui fait reference à un Entrepot, cette classe contient les fonctions principales qui manipulent la gestion du stock. 
 * Elle permet aussi d'initailiser le personnel de depart.
 * Cette classe n'a pas des attributs ou méthodes static car on suppose qu'on peut avoir plusieurs entrepots et donc on a besoin de les gerer separement.
 * @author Boustique Soufiane 
 * @version 1.0.2
 * */

public class Entrepot {
	//ATTRIBUTS---------------------------------------------------------------------------------------------------------------------------------------
	
	/**Le nombre de nos rangees de l'Entrepot.
	 */
	private final int m_range;
	/**La taille d'une rangee de l'Entrepot.
	 */
	private final int Taille ; 
	/**La Tresorie  de l'Entrepot.
	 */
	private double tresorie;
	/**Tableau qui contient nos rangees.
	 */
	private ArrayList<Range> T_range;
	/**Tableau qui contient tous les types possibles.
	 *Le type dans la case i modulo m_range se trouve dans la rangée i.
	 * NB: demander à l'utilisateur les types à définir au début de l'execution.
	 */
	private ArrayList<String> types;
	
	// CONSTRUCTEUR ---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Cree un nouveau Entrepot et Initialise nos rangées
	 * @param m nombre de nos rangees
	 * @param n la taille d'une range
	 * @param treso tresorie initiale
	 * @param types les types a initialiser
	 * */
	public Entrepot(int m,int n,double treso,ArrayList<String> types) throws IllegalArgumentException{
		if(m<=0 || n<=0 || treso<=0 || types.size()==0) {
			System.out.println("erreur dans la definition de l'entrepot");
			throw new IllegalArgumentException();
		}
		m_range=m;
		Taille=n;
		tresorie=treso;
		this.types= types;
		T_range = new ArrayList<Range>();
		int j;
		for(int i=0;i<m_range;i++) {
			ArrayList<String> R_types = new ArrayList<>();
			j=i;
			while(j<types.size()) {
				R_types.add(types.get(j));
				j=j+m_range;
			}
			T_range.add(new Range(Taille,i,R_types));
		}
		
	} 
	
	// GETERS  --------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @return la taille d'une rangee
	 * */
	public int getM_range() {
		return m_range; 
	}
	/**
	 * @return la tresorie
	 * */
	public double getTresorie() {
		return tresorie;
	}
	/**
	 * @return le tableau de nos rangees
	 * */
	public ArrayList<Range>  getTrange(){
		return T_range;
	}
	/**
	 * @return le tableau de nos tous nos types
	 * */
	public ArrayList<String> getTypes() {
		return types;
	}
	/**
	 * @return la taille d'une rangee
	 * */
	public int getTaille() {
		return Taille;
	}
	
	//SETTERS --------------------------------------------------------------------------------------------------------------------------------------
	
	/** Mets a jour notre tresorie
	 * @param tresorie nouvelle tresorie
	 * */
	public void setTresorie(double tresorie)  {
		this.tresorie = tresorie;
		
	}
	
	//METHODES --------------------------------------------------------------------------------------------------
	
	/** Ajoute un Lot a la fin de notre bloc dans la rangee de son type
	 * @param L le lot a ajouter
	 * @return false si pas assez de places pour rajouter un Lot sinon renvoi true
	 * @see Range#ajoutL(Lot L)
	 *  */
	public boolean ajoutL(Lot L) throws IllegalArgumentException {
		int i = types.indexOf(L.getType());
		if(i==-1) throw new IllegalArgumentException();// On rajoute le Lot de type inconnu
		i=i%m_range;// on a mnt accés sa range
		return T_range.get(i).ajoutL(L);
		
		
	}
	/** @param L fait au Lot
	 * @return  renvoi le nombre d'ouvrier requis pour stocker le lot ou -1 si pas assez de place pour stocker Lot
	 * @see Range#nbOuvrierRStockerL(Lot L)
	 * */
	public int nbOuvrierRStockerL(Lot L)throws IllegalArgumentException  {
		int i = types.indexOf(L.getType());
		if(i==-1) throw new IllegalArgumentException();
		i=i%m_range;// on a mnt accés sa range
		return T_range.get(i).nbOuvrierRStockerL(L);
	}
	
	/** S'utilise dans le cas ou ajoutL renvoi false et donc on supprime le dernier lot pour ajouter un plus grand si assez de place , utilise 2 personnel ( pour supprimer et ajouter) durant le meme pas de temps
	 * @param L le lot a ajouter
	 * @return  false si pas assez de places meme en supprimant le dernier(ou une partie) du lot et donc on accepte pas le lot pour rajouter un Lot sinon renvoi true
	 * @see Range#remplacerFinL(Lot L)
	 *  */
	public boolean remplacerFinL(Lot L) throws IllegalArgumentException {
		int i = types.indexOf(L.getType());
		if(i==-1) throw new IllegalArgumentException();
		i=i%m_range;
		return T_range.get(i).remplacerFinL(L);
	}
	
	/** On retire un Lot du Bloc en remplacant ses occurences par des valeurs null
	 * @param L le lot a retirer
	 * @return  renvoi le prix du Lot
	 * @see Range#retirerL(Lot L)
	 * */
	public double retirerL(Lot L) throws IllegalArgumentException {
		int  i= types.indexOf(L.getType());
		if(i==-1) throw new IllegalArgumentException();
		i=i%m_range;
		return T_range.get(i).retirerL(L);
	}
	
	/** @param n fait reference aux n derniers lots
	 * @return  renvoi le nombre de personnel requis pour retirer un n fois type de lot , renvoi -1 si pas assez de lot dans le bloc
	 * @see 	Range#nbOuvrierR(String type,int n)
	 * */ 
	public int nbOuvrierR(String type,int n) {
		int i = types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// type inconnu
		i=i%m_range;
		return T_range.get(i).nbOuvrierR(type,n);
	}
	
	/** @param n fait reference aux volume à retirer 
	 * @return  renvoi le prix de construction pour un volume donne pour un certain type de le lot, si pas assez de lot si retourne -1
	 * @see	    Range#prixConstructionV(String type,int n)
	 * */ 
	public double prixConstructionV(String type,int n) {
		int i = types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// type inconnu
		i=i%m_range;
		return T_range.get(i).prixConstructionV(type,n);
	}
	
	/** Retire n fois ce type de lot de la fin 
	 * @param n fait reference aux volume de lots a enlever suivant le volume qu'on veut enlever
	 * @return  renvoi le prix des Lot sinon renvoi -1 si pas lots possible à retirer
	 * @see	    Range#retirerT(String type,int n)
	 * */
	public double retirerT(String type,int n)throws IllegalArgumentException {
		int  i= types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// ce type n existe pas dans l'entrepot
		i=i%m_range;
		return T_range.get(i).retirerT(type, n);
	}
	
	/** Retire n fois ce type de lot de la fin, deuxième version de retirerT
	 * @param n fait reference aux nombre de lots a enlever suivant le nombre de personnel dont on dispose
	 * @return  renvoi le prix des Lot sinon renvoi -1 si pas lots possible à retirer
	 * @see	    Range#retirerP(String type,int n)
	 * */
	public double retirerP(String type,int n)throws IllegalArgumentException {
		int i=types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// ce type n existe pas dans l'entrepot
		i=i%m_range;
		return T_range.get(i).retirerP(type, n);
	}
	
	/** @param n fait reference aux n derniers lots
	 * @return  renvoi le volume Total contenu dans les n derniers Lot sinon renvoi -1 si pas de lots possible à compter
	 * @see	    Range#compterP(String type,int n)
	 * */
	public int compterP(String type,int n) throws IllegalArgumentException{
		int i=types.indexOf(type);
		if(i==-1) throw new IllegalArgumentException();// ce type n existe pas dans l'entrepot
		i=i%m_range;
		return T_range.get(i).compterP(type, n);
	}
	
	/** Permet d'afficher un Entrepot sous la forme: tresorie numero_du_bloc volumeDisponible || Nos rangees ||
	 * */
	@Override
	public String toString() {
		String S="tresorie="+tresorie+"\n";
		for(int i=0;i<m_range;i++) {
			S=S+T_range.get(i).toString()+"\n";
			
		}
		return S;
	}

	//METHODES PRINCIPALES : EN RELATION AVEC CLASSE PERSONNEL --------------------------------------------------------------------------------------------------

	/** Rajoute un lot ou coupe si besoin tout en gerant le personnel
	 * @param L fait reference au Lot a ajouter
	 * @return  renvoi true si on a reussi a ajouter le lot, false dans le cas contraire
	 * */
	public boolean stockerL(Lot L) throws  IllegalArgumentException{
		Chef stockC=Chef.estDispo("Stock");
		Ouvrier stockO=Ouvrier.estDispoPourEntrepot();
		if(stockC==null && stockO==null) return false;
		if(ajoutL(L)==true) {
			if(stockC!=null) {stockC.setDispo(1);return true;}
			stockO.setDispo(1);
			return true;
		}
		else {
			if(Ouvrier.nombreOuvriersEtChefStockDispo(2)==false) return false;
			if(remplacerFinL(L)==false)return false;
			ArrayList<Chef> Chefs = Chef.listChefDispo(2,"Stock",1);
			if(Chefs.size()==2)return true;
			Ouvrier.listOuvrier(2-Chefs.size(),"",1);
			return true;
			
		}
	}
	
	/** Initialise le Personnel avec 1 chef Stock et 2 ouvriers sans type
	 * @return  renvoi false en cas d'erreur
	 * */
	public static boolean InitialiserPersonnelP2()throws  IllegalArgumentException {
		try {
			boolean b=Chef.recruter("Freeman", "Morgan","Brico") && Ouvrier.recruter("Boon", "Dany", "sans") && Ouvrier.recruter("Afgoustidis", "Alexandre", "sans") ;
			if(b==false) {System.out.println("erreur dans initialisation du personnel de la stratégie 1");throw new IllegalArgumentException();}
			return true;
		}
		catch(Exception e) {
			return false;
			
		}
		
	}	
	
	/** Initialise le Personnel avec 1 chef Stock et 1 ouvrier sans type
	 * @return  renvoi false en cas d'erreur
	 * */
	public static boolean InitialiserPersonnelP1()throws  IllegalArgumentException {
		try {
			boolean b=Chef.recruter("Freeman", "Morgan","Brico") && Ouvrier.recruter("Boon", "Dany", "sans");
			if(b==false) {System.out.println("erreur dans initialisation du personnel de la stratégie 2");throw new IllegalArgumentException();}
			return true;
		}
		catch(Exception e) {
			return false;
			
		}
		
	}

}

