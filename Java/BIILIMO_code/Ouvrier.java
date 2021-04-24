package BIILIMO;

//IMPORT--------------------------------------------------------------------------------------------------------------------
import java.util.ArrayList;
import java.util.Iterator;

/** Un Ouvrier est un objet qui permet de deplacer des lots ou bien de construire des meubles suivant sa specialite.
 * Tout Ouvrier peut deplacer les lots (stocker, deplacer et enlever de l'entrepot).
 * Un Ouvrier peut construire des meubles seulement si sa specialite est la meme que celle du meuble.
 * @author Raphael Malak
 * @version 1.0.2
 * @see BIILIMO.Personnel

*/
public class Ouvrier implements Personnel{
	
	//ATTRIBUTS-------------------------------------------------------------------------------------------------------------
	
	/**
	 * Sprecialite de l'Ouvrier.
	 */
	private String specialite;
	/**
	 * Nom de l'Ouvrier
	 */
	String nom;
	/**
	 * Prenom de l'Ouvrier
	 */
	String prenom;
	/**
	 * Identifiant de l'Ouvrier. Celui-ci est unique.
	 */
	private int id;
	private static int cpt;
	/**Disponibilite de l'Ouvrier.
	 * Vaut 0 si l'Ouvrier est dispo ou un entier positif n si il lui reste n iterations de travail.
	 */
	private int dispo;
	/**
	 * Le Chef qui dirige son equipe
	 * @see BIILIMO.Chef
	 */
	private Chef monChef; 
	/**
	 * Liste des specialites possibles.
	 */
	static String [] specialites = {"cuisine", "chambre", "salle a manger", "salon", "salle de bain", "WC"};
	/**
	 * Salaire de l'Ouvrier.
	 */
	private int salaire;
	
	//CONSTRUCTEUR-----------------------------------------------------------------------------------------------------------
	
	/**
	 * Cree un nouvel ouvrier standard
	 * @param nom Nom de l'ouvrier.
	 * @param prenom Prenom de l'ouvrier.
	 * @param specialite Specialite de l'ouvrier.
	 * @throws IllegalArgumentException
	 */
	public Ouvrier(String nom, String prenom, String specialite)throws IllegalArgumentException {
		setSalaire(0);
		this.nom = nom;
		this.prenom = prenom;
		this.specialite = specialite;
		this.id = ++cpt;
		dispo = 0;
		monChef = null;
		boolean b =false;
		if(specialite.equals("sans"))return;// ouvrier qui ne deplace que des lots
		for(int i=0;i<specialites.length;i++) {
			if(specialites[i].replaceAll(" ","").equalsIgnoreCase(specialite.replaceAll(" ",""))) {b=true;this.specialite = specialites[i];}
		}
		if(b==false) {System.out.println("erreur specialite");throw new IllegalArgumentException();}
	}
	
	/**
	 * Un nouvel ouvrier dont on connait le chef
	 * @param nom Nom de l'ouvrier.
	 * @param prenom Prenom de l'ouvrier.
	 * @param specialite Specialite de l'ouvrier.
	 * @param monChef Chef de l'ouvrier.
	 * @throws IllegalArgumentException
	 */
	public Ouvrier(String nom, String prenom, String specialite, Chef monChef)throws IllegalArgumentException{
		setSalaire(0);
		this.nom = nom;
		this.prenom = prenom;
		this.specialite = specialite;
		this.id = ++cpt;
		dispo = 0;
		this.monChef = monChef;
		boolean b =false;
		if(specialite.equals("sans"))return;
		for(int i=0;i<specialites.length;i++) {
			if(specialites[i].replaceAll(" ","").equalsIgnoreCase(specialite.replaceAll(" ",""))) {b=true;this.specialite = specialites[i];}
			
		}
		if(b==false) {System.out.println("erreur specialite");throw new IllegalArgumentException();}
	}
	
	/**
	 * Creer un Ouvrier avec les attributs passes en parametre.
	 * @param nom Nom de l'ouvrier.
	 * @param prenom Prenom de l'ouvrier.
	 * @param specialite Specialite de l'ouvrier.
	 * @param id Entier positif. Identifiant de l'ouvrier. Celui-ci est unique.
	 * @param dispo Disponibilite de l'ouvrier. Entier positif.
	 * @param monChef hef de l'ouvrier.
	 * @throws IllegalArgumentException
	 */
	public Ouvrier(String nom, String prenom, String specialite, int id, int dispo, Chef monChef)throws IllegalArgumentException {
		setSalaire(0);
		this.nom = nom;
		this.prenom = prenom;
		this.specialite = specialite;
		this.id = id;
		this.dispo = dispo;
		this.monChef = monChef;
		boolean b =false;
		if(specialite.equals("sans"))return;
		for(int i=0;i<specialites.length;i++) {
			if(specialites[i].replaceAll(" ","").equalsIgnoreCase(specialite.replaceAll(" ",""))) {b=true;this.specialite = specialites[i];}
		}
		if(b==false) {System.out.println("erreur specialite");throw new IllegalArgumentException();}
	}
	
	//GETTER----------------------------------------------------------------------------------------------------------------
	
	/**
	 * @return La specialite de l'Ouvrier.
	 */
	public String getSpecialite() {
		return specialite;
	}
	
	/**
	 * @return Le nom de l'Ouvrier.
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * @return Le prenom de l'Ouvrier.
	 */
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * @return L'identifiant de l'Ouvrier.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return La disponibilite de l'Ouvrier.
	 */
	public int getDispo() {
		return dispo;
	}
	
	/**
	 * @return Le Chef de l'Ouvrier.
	 */
	public Chef getMonChef() {
		return monChef;
	}
	
	/**
	 * @return Le salaire de l'Ouvrier.
	 */
	public int getSalaire() {
		return salaire;
	}

	//SETTER----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Modifie le salaire de l'Ouvrier
	 * @param Entier positif
	 */
	public void setSalaire(int salaire) {
		this.salaire = salaire;
	}
	
	public static void setCpt(int cpt) {
		Ouvrier.cpt = cpt;
	}
	
	/**
	 * Modifie le Chef de l'Ouvrier.
	 * @param monChef Nouveau Chef de l'Ouvrier.
	 */
	public void setMonChef(Chef monChef) {
		this.monChef = monChef;
	}
	
	/**
	 * Modifie la disponibilite de l'Ouvrier.
	 * @param dispo Entier positif. Nouvelle disponibilite.
	 */
	public void setDispo(int dispo) {
		this.dispo = dispo;
	}
	
	//REDEFINISSIONS--------------------------------------------------------------------------------------------------------
	
	/**
	 * Redefinition de la methode equals de la classe Object
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Ouvrier){
			Ouvrier a = (Ouvrier) o;
			if(id == a.id){
				return true;
			}
		}
		return false;
	}
	
	//METHODES--------------------------------------------------------------------------------------------------------------

	/**
	 * Pour afficher un ouvrier
	 */
	public String toString() {
		return(nom +" "+prenom+", S="+salaire +" ,D="+dispo+ " ,Specialite= " + specialite);
	}
	
	/**
	 * Attribuer a un ouvrier qui vient de recruter un chef d'equipe, ici la premiere place possible dans listChef.
	 * @param nom Nom de l'Ouvrier.
	 * @param prenom Prenom de l'Ouvrier.
	 * @param specialite Specialite de l'Ouvrier.
	 * @return true si une place a bien ete attribue, false sinon.
	 * @see BIILIMO.Chef
	 */
	public static boolean trouverUneEquipePourNouveau(String nom, String prenom, String specialite) {
		ArrayList<Chef> listChef = Chef.getListChef();
		Iterator<Chef> it = listChef.iterator();
		while (it.hasNext()){
			Chef a = it.next();
			for(int i = 0; i < 4; i++) {
				if(a.equipe.get(i) == null) {
					a.equipe.set(i, new Ouvrier(nom, prenom, specialite, a));
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Attribuer a un ancien ouvrier un chef d'equipe qui n'est pas son Chef actuel, ici la premiere place possible dans listChef.
	 * @param ouvrier L'Ouvrier a rediriger.
	 * @return true si la redirection a ete faite, fzlse sinon.
	 * @see BIILIMO.Chef
	 */
	public static boolean trouverUneEquipePourAncien(Ouvrier ouvrier) {
		ArrayList<Chef> listChef = Chef.getListChef();
		Iterator<Chef> it = listChef.iterator();
		while (it.hasNext()){
			Chef a = it.next();
			for(int i = 0; (i < 4)&&(a!=ouvrier.getMonChef()); i++) {
				if(a.equipe.get(i) == null) {
					a.equipe.set(i, new Ouvrier(ouvrier.nom, ouvrier.prenom, ouvrier.specialite, ouvrier.id, ouvrier.dispo, a));
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Recruter un Ouvrier dont la specialiteest donnee.
	 * @param nom Nom de l'Ouvrier.
	 * @param prenom Prenom de l'Ouvrier.
	 * @param specialite Specialite de l'Ouvrier.
	 * @return true quand le recrutement est un succes, false sinon.
	 * @see BIILIMO.Personnel#recruter(String, String, String)
	 * @see BIILIMO.Ouvrier#trouverUneEquipePourNouveau(String, String, String)
	 */
	public static boolean recruter(String nom, String prenom, String specialite) {
		if(trouverUneEquipePourNouveau(nom, prenom, specialite) == false){ //toutes les equipes sont pleines
			return false;
		}
		return true;
	}
	
	/**
	 * Compte le nombre de places libres dans une equipe d'ouvriers.
	 * @param listOuvrier Une liste d'Ouvrier
	 * @return Le nombre de places qui valent null dans la liste d'ouvriers en parametres.
	 */
	public static int placeDansEquipe(ArrayList<Ouvrier> listOuvrier) {
		int som = 0;
		for(int i = 0; i < listOuvrier.size(); i++) {
			if(listOuvrier.get(i) == null) {
				som++;
			}
		}
		return som;
	}
	
	/**
	 * Licencie l'Ouvrier et le supprimant de l'equipe de son Chef.
	 * @return true quand le licenciement est un succes, false sinon.
	 * @see BIILIMO.Personnel#licencier()
	 */
	public boolean licencier() {
		if(monChef == null) {
			return false;
		}
		monChef.getEquipe().set(monChef.getEquipe().indexOf(new Ouvrier(nom, prenom, specialite, id, dispo, monChef)), null);
		return true;
	}

	/**
	 * Cherche le premier Ouvrier disponible ayant une certaine specialite dans listChef(utile pour la construction d'un meuble)
	 * @param specialite Specialite recherchee.
	 * @return Renvoie le premier Ouvrier disponible ayant une certaine specialite ou null si il n'y a pas d'ouvrier disponible.
	 * @see BIILIMO.Chef
	 */
	public static Ouvrier estDispoPourConstruire(String specialite) {
		ArrayList<Chef> listChef = Chef.getListChef();
		Iterator<Chef> it = listChef.iterator();
		while (it.hasNext()){
			Chef a = it.next();
			for(int i = 0; i < 4; i++) {
				if(a.equipe.get(i) != null && a.equipe.get(i).specialite.equals(specialite) && a.equipe.get(i).dispo == 0) {
					return(a.equipe.get(i)); //Juste apres, il faudra incrementer a.dispo de (dureeAction - 1) a.dispo 
			                             //et a  chaque iteration, soustraire 1 jusqu'a  0
				}
			}
		}
		return null;
	}
	
	/**
	 * Cherche le premier Ouvrier disponible dans listChef (utile gerer l'entrepot).
	 * @return Renvoie le premier ouvrier disponible ou null si il n'y a pas d'ouvrier disponible.
	 * @see BIILIMO.Chef
	 */
	public static Ouvrier estDispoPourEntrepot() {
		ArrayList<Chef> listChef = Chef.getListChef();
		Iterator<Chef> it = listChef.iterator();
		while (it.hasNext()){
			Chef a = it.next();
			for(int i = 0; i < 4; i++) {
				if(a.equipe.get(i) != null && a.equipe.get(i).dispo == 0) {
					return(a.equipe.get(i)); //Juste apres, il faudra incrementer a.dispo de 1 a.dispo 
			                             //et a  la prochaine iteration, soustraire 1
				}
			}
		}
		return null;
	}
	
	/**
	 * Verifie si il y a assez de personnel en charge de la gestion de l'entrepot dans listChef.
	 * @param nombreNecessaire nombre de personnel requis.
	 * @return true si on a assez de personnel pour pouvoir apporter tout les lots necessaire a la construction d'un meuble en 1 fois, false sinon.
	 * @see BIILIMO.Chef
	 */
	public static boolean nombreOuvriersEtChefStockDispo(int nombreNecessaire){
		int nombre = 0;
		ArrayList<Chef> listChef = Chef.getListChef();
		Iterator<Chef> it = listChef.iterator();
		while (it.hasNext()){
			Chef a = it.next();
			if(a.getSpecialite().equals("Stock")) {
				nombre++;
			}
			for(int i = 0; i < 4; i++) {
				if(a.equipe.get(i) != null && a.equipe.get(i).dispo == 0) {
					nombre++;
				}
			}
		}
		if(nombre >= nombreNecessaire) {
			return true;
		}
		return false;
	}
	
	/**
	 * Cherche au maximum nb Ouvrier pour gerer l'entrepot.
	 * @param nb Entier positif. Nombre d'ouvriers requis.
	 * @param specialite Doit etre "" sinon cela renverra une liste vide.
	 * @param duree Duree de laquelle les ouvriers dans la liste renvoyee auront leur disponibilite incrementee.
	 * @return ArrayList des ouvriers disponible pour une certaine specialite sans depasse nb
	 */
	static ArrayList<Ouvrier> listOuvrier(int nb ,String specialite,int duree){// si specialite=="" alors c'est un ouvrier pour deplacer les lots
		ArrayList<Ouvrier> Liste = new ArrayList<Ouvrier>();
		Ouvrier p;
		if(specialite.equals("")) {
			for(int i=0;(i<nb && (p=estDispoPourEntrepot())!=null);i++) {
				Liste.add(p);
				p.setDispo(duree);
			
			}
		return Liste;
		}
		else {
			for(int i=0;(i<nb && (p=estDispoPourConstruire(specialite))!=null);i++) {
				Liste.add(p);
				p.setDispo(duree);
			
			}
		return Liste;
			
		}
	}

}