package BIILIMO;

/**
 * Un Lot est une classe, qui fait reference a un Lot de notre Entrepot.
 * @author Boustique Soufiane
 * @version 1.0
 * */
public class Lot {
	//ATTRIBUTS--------------------------------------------------------------------------------------------------------------------------------------
	
	/** Nombre qui caraterise notre Lot par un identifiant unique qui s'incremente a la creation de nouveau Lot.
	 */
	private final int Id;
	/** Volume de notre Lot.
	 */
	private int volume;
	/** Type de notre Lot.
	 */
	private final String type;
	/** Poids de notre Lot pour un volume egal a 1.
	 */
	private final double poids;
	/** Prix de notre Lot pour un volume egal a 1.
	 */
	private final double prix;
	/** Compte le nombre de Lot cree.
	 */
	private static int cmpt_lot=0;
	
	
	// CONSTRUCTEUR ---------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Cree un nouveau Lot
	 * @param v  volume de depart du lot
	 * @param t  type du lot
	 * @param pd poids de notre lot
	 * @param pr prix de notre lot
	 * */
	public Lot(int v , String t , double pd , double pr) throws IllegalArgumentException{
		if(v<=0 || pd <=0 || pr <= 0) {
			System.out.println("erreur dans la definition du lot ");
			throw new IllegalArgumentException();
		}
		cmpt_lot++;
		Id=cmpt_lot;
		volume=v;
		type=t;
		poids=pd;
		prix=pr;

	}
	
	//GETERS --------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @return l'id de notre lot
	 * */
	public int getId() {
		return Id;
	}
	/**
	 * @return le prix de notre lot pour volume egal a 1
	 * */
	public double getPrix() {
		return prix;
	}
	/**
	 * @return le poids de notre lot pour volume egal a 1	
	 * */
	public double getPoids() {
		return poids;
	}
	/**
	 * @return le type de notre lot	
	 * */
	public String getType() {
		return type;
	}
	/**
	 * @return le volume de notre lot	
	 * */
	public int getVolume() {
		return volume;
	}
	
	//SETERS ----------------------------------------------------------------------------------------------------------------------------------------
	
	/** Modifie le volume de notre Lot
	 * @param le nouveau volume	
	 * */
	public void setVolume(int i) {
		volume=i;
	}

	//METHODES--------------------------------------------------------------------------------------------------
	
	/** Permet d'afficher un lot sous la forme: [id du lot, volume du lot]
	 * */
	@Override
	public String toString() {
		return Id+","+volume+"";

	}

	

}
