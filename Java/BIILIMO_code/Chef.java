package BIILIMO;

//IMPORT-------------------------------------------------------------------------------------------------------------------
import java.util.ArrayList;
import java.util.Iterator;

/** Un Chef est un objet qui permet de deplacer des lots ou bien de construire des meubles suivant sa specialite.
 * Un Chef Stock peut deplacer les lots (stocker, deplacer et enlever de l'entrepot).
 * Un Chef Brico peut construire des meubles quelle que soit la specialite requise
 * @author Raphael Malak 
 * @version 1.0.2
 * @see BIILIMO.Personnel

*/
public class Chef implements Personnel{
	
	//ATTRIBUTS-------------------------------------------------------------------------------------------------------------
	
	/**Specialite du Chef (Stock ou Brico).
	 */
	private String specialite; 
	/**Nom du Chef.
	 */
	String nom;
	/**Prenom du Chef.
	 */
	String prenom;
	/**Identifiant du Chef, celui-ci est unique a chaque Chef.
	 */
	private int id;
	/** compteur pour incrementer les identifiants des chefs
	 */
	private static int cpt; 
	/**Equipe du Chef de 4 ouvriers maximum.
	 */
	ArrayList<Ouvrier> equipe;
	/**Disponibilite du Chef.
	 * Vaut 0 si le chef est dispo ou un entier positif n si il lui reste n iterations de travail
	 */
	private int dispo;
	/**
	 * Liste de tous les Chef recrutes non licencies.
	 */
	private static ArrayList<Chef> listChef = new ArrayList<Chef>();
	/**
	 * Salaire du Chef.
	 */
	private int salaire;
	
	//CONSTRUCTEUR-----------------------------------------------------------------------------------------------------------
	
	/**
	 * Cree un nouveau chef d'equipe
	 * @param nom Nom du Chef
	 * @param prenom Prenom du Chef
	 * @param specialite Specialite du Chef
	 * @throws IllegalArgumentException
	 */
	public Chef(String nom, String prenom, String specialite)throws IllegalArgumentException  {
		setSalaire(0);
		this.nom = nom;
		this.prenom = prenom;
		this.specialite = specialite;
		this.id = ++cpt;
		equipe = new ArrayList<Ouvrier>();
		for(int i = 0; i<4; i++) {
			equipe.add(null);
		}
		
		dispo = 0;
		if(specialite.equalsIgnoreCase("stock")==false && specialite.equalsIgnoreCase("brico")==false)throw new IllegalArgumentException();
		if(specialite.equalsIgnoreCase("stock")==true)this.specialite="Stock";
		if(specialite.equalsIgnoreCase("brico")==true)this.specialite="Brico";
	}
	
	/**Creer un Chef avec les attributs passes en parametre.
	 * Utile dans la methode licencier pour ne pas incrementer l'identifiant lors de la creation d'un nouveau chef
	 * @param nom
	 * @param prenom
	 * @param specialite
	 * @param id
	 * @param dispo
	 * @throws IllegalArgumentException
	 */
	public Chef(String nom, String prenom, String specialite, int id, int dispo)throws IllegalArgumentException  {
		setSalaire(0);
		this.nom = nom;
		this.prenom = prenom;
		this.specialite = specialite;
		this.id = id;
		equipe = new ArrayList<Ouvrier>();
		for(int i = 0; i<4; i++) {
			equipe.add(null);
		}
		this.dispo = dispo;
		if(specialite.equalsIgnoreCase("stock")==false && specialite.equalsIgnoreCase("brico")==false)throw new IllegalArgumentException();
		if(specialite.equalsIgnoreCase("stock")==true)this.specialite="Stock";
		if(specialite.equalsIgnoreCase("brico")==true)this.specialite="Brico";
	}
	
	//GETTER----------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @return Le nom du Chef.
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * 
	 * @return Le prenom du Chef.
	 */
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * 
	 * @return La specialite du Chef.
	 */
	public String getSpecialite() {
		return specialite;
	}
	
	/**
	 * 
	 * @return L'identifiant du Chef.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return La disponibilite du Chef. Elle vaut 0 si il est disponible.
	 */
	public int getDispo() {
		return dispo;
	}

	/**
	 * 
	 * @return Renvoie la liste des chefs recrutes non licencies.
	 */
	public static ArrayList<Chef> getListChef(){
		return listChef;
	}

	/**
	 * 
	 * @return Renvoie l'equipe du Chef.
	 */
	public ArrayList<Ouvrier> getEquipe() {
		return equipe;
	}
	
	/**
	 * 
	 * @return Le salaire d'un Chef.
	 */
	public int getSalaire() {
		return salaire;
	}
	//SETTER----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Modifie la liste des Chef recrutes non licencies
	 * @param listChef Une liste de Chef
	 */
	public static void setListChef(ArrayList<Chef> listChef) {
		Chef.listChef = listChef;
	}

	public static void setCpt(int cpt) {
		Chef.cpt = cpt;
	}
	
	/**
	 * Modifie la disponibilite du Chef.
	 * @param dispo Entier positif.
	 */
	public void setDispo(int dispo) {
		this.dispo = dispo;
	}
	
	/**
	 * Modifie le salaire du Chef.
	 * @param salaire Entier positif
	 */
	public void setSalaire(int salaire) {
		this.salaire = salaire;
	}
	
	//REDEFINISSIONS--------------------------------------------------------------------------------------------------------
	
	//Utile dans la methode licencier a  l'appel de la methode remove(Object o) de ArrayList sur une ArrayList de Chef
	/**
	 * Redefinition de la methode equals de la classe Object
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Chef){
		      Chef a = (Chef) o;
		      if(id == a.id){
		        return true;
		      }
		    }
		    return false;
	}
	
	//METHODES--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Pour afficher un chef d'equipe
	 */
	@Override
	public String toString() {
		ArrayList<Ouvrier> sousEquipe = new ArrayList<Ouvrier>(); //Prend uniquement les ouvriers et pas les places libres dans l'equipe
		for(int i = 0; i < 4; i++) {
			if(equipe.get(i) != null) {
				sousEquipe.add(equipe.get(i));
			}
		}
		if(sousEquipe.size() == 0) {
			return(nom + " " + prenom +", S="+salaire+ " ,D="+dispo+", Chef " + specialite + " sans equipe affiliee");
		}
		return(nom + " " + prenom +", S="+salaire+" ,D="+dispo+ ", Chef " + specialite + " de l'equipe numero" + id + " --> "+ sousEquipe.toString());
	}
	
	/**
	 * Recruter un chef d'equipe dont la specialite est donnee et renvoie true si le recrutement a bien eu lieu
	 * @param nom Nom du Chef.
	 * @param prenom Prenom du Chef.
	 * @param specialite Specialite du Chef (Brico ou Stock).
	 * @return true quand le recrutement est un succes, false sinon.
	 * @see BIILIMO.Personnel#recruter(String, String, String)
	 */
	public static boolean recruter(String nom, String prenom, String specialite) {
		Chef a = new Chef(nom, prenom, specialite);
		listChef.add(a);
		return true;
	}
	
	/**
	 * Compte le nombre de places disponibles dans toutes les equipes des chefs dans listChef.
	 * @return Un entier correspondant au nombre de places vides dans toutes les equipes des Chef.
	 */
	public static int nombreDePlaceDansEquipes() {
		int som = 0;
		for(int i = 0; i < listChef.size(); i++) {
			for(int j = 0; j < 4; j++) {
				if(listChef.get(i).equipe.get(j) == null) {
					som++;
				}
			}
		}
		return som;
	}
	
	/**
	 * Licencie le chef d'equipe sauf si la redirection de son equipe vers d'autres Chef de listChef est impossible.
	 * @return true quand le licenciement est un succes, false sinon.
	 * @see BIILIMO.Personnel#licencier()
	 * @see BILLIMO.Ouvrier#l
	 */
	public boolean licencier() {
		if(equipe.get(0) == null && equipe.get(1) == null && equipe.get(2) == null && equipe.get(3) == null) {
			listChef.remove(new Chef(nom, prenom, specialite, id, dispo));
			return true;
		}
		//S'il y a assez de places pour transferer les ouvriers restant dans les autres equipes
		if(4  - Ouvrier.placeDansEquipe(equipe) <= nombreDePlaceDansEquipes() - Ouvrier.placeDansEquipe(equipe)) {
			for(int i = 0; i < 4; i++) {
				if(equipe.get(i) != null) {
					Ouvrier.trouverUneEquipePourAncien(new Ouvrier(equipe.get(i).getNom(), equipe.get(i).getPrenom(), equipe.get(i).getSpecialite(), equipe.get(i).getId(), equipe.get(i).getDispo(), equipe.get(i).getMonChef()));	
				}
			}
			listChef.remove(new Chef(nom, prenom, specialite, id, dispo));
			return true;
		}
		//on ne fait rien sinon car on ne peut pas licencier plusieurs personnes le meme tour
		return false;
	}

	/**
	 * Renvoie le premier chef d'equipe disponible ayant une certaine specialite ou null si il n'y a pas de chef d'equipe de ce type disponible
	 * @param specialite Specialite du Chef cherche (Stock ou Brico).
	 * @return le premier Chef disponible, null sinon.
	 * @see BIILIMO.Personnel#estDispo(String)
	 */
	public static Chef estDispo(String specialite) {
		Iterator<Chef> it = listChef.iterator();
		while (it.hasNext()){
			Chef a = it.next();
			if(a.specialite.equals(specialite) && a.dispo == 0) {
				return(a); //Juste apres, il faudra incrementer a.dispo de (dureeAction - 1) a.dispo 
				          //et a  chaque iteration, soustraire 1 jusqu'a  0
			}
		}
		return null;
	}
	
	/**
	 * Appele a chaque iteration, pour soustraire 1 a l'attribut dispo de chaque Personnel (chef ou ouvrier) qui travaille dans listChef.
	 */
	public static void soustraireDisponibilite() {
		Iterator<Chef> it = listChef.iterator();
		while (it.hasNext()){
			Chef a = it.next();
			if(a.dispo != 0) {
				a.dispo --;
			}
			for(int i = 0; i < 4; i++) {
				if(a.equipe.get(i) != null && a.equipe.get(i).getDispo() != 0) {
					a.equipe.get(i).setDispo(a.equipe.get(i).getDispo() - 1);
				}
			}
		}
	}
	
	/**
	 *  renvoi une arrayList de chef de la specialite voulue au nb(au moin) voulue ou null si pas de chef dispo entierement
	 * @param nb Nombre de Chef desires.
	 * @param specialite Specialite desiree.
	 * @param duree La duree qui va etre incrementee a chaque Chef present dans la liste de retour.
	 * @return arrayList de au moins nb chef de la specialite voulue ou null si pas assez de chef dispo.
	 * @see BIILIMO.Chef#estDispo(String)
	 */
	static ArrayList<Chef> listChefDispo(int nb, String specialite,int duree){
		ArrayList<Chef> Liste = new ArrayList<Chef>();
		Chef p;
		for(int i=0;(i<nb && (p=estDispo(specialite))!=null);i++) {
			Liste.add(p);
			p.setDispo(duree);
			
		}
		return Liste;
	}
	
	/**
	 * Paye tout le personnel (10 euros chaque chef et 5 euros chaque ouvrier.
	 * @param e L'entrepot sur lequel travaille le personnel qui doit etre paye.
	 * @see BIILIMO.Entrepot
	 */
	public static void payer(Entrepot e) {
		e.setTresorie(e.getTresorie() - 10*listChef.size());
		for(int i = 0; i < listChef.size(); i++) {
			listChef.get(i).setSalaire(listChef.get(i).getSalaire()+10);
			for(int j = 0; j < 4; j++) {
				if(listChef.get(i).equipe.get(j) != null) {
					e.setTresorie(e.getTresorie() - 5);
					listChef.get(i).equipe.get(j).setSalaire(listChef.get(i).equipe.get(j).getSalaire()+5);
				}
			}
		}
	}
	
	/**
	 * Verifie si tout le personnel de listChef est disponible.
	 * @return true si tout le personnel de listChef est disponible, false sinon.
	 */
	public static boolean tousDispoouPas() {
		for(int i = 0; i < listChef.size(); i++) {
			if(listChef.get(i).dispo != 0) {
				return false;
			}
			for(int j = 0; j < 4; j++) {
				if(listChef.get(i).equipe.get(j)!= null && listChef.get(i).equipe.get(j).getDispo() != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Cherche le premier Ouvrier dans listChef disponible et le licencie.
	 * @see BIILIMO.Ouvrier#licencier()
	 */
	public static void premierOuvrierDisponibleLicencier() {
		for(int i=0;i < listChef.size(); i++) {
			for(int j = 0; j < 4; j++) {
				if(listChef.get(i).equipe.get(j)!= null && listChef.get(i).equipe.get(j).getDispo() == 0) {
					listChef.get(i).equipe.get(j).licencier();
					return;
					}
			
			}
		}
	
	}
	
	/**
	 * Cherche le premier Chef dans listChef disponible et le licencie si possible.
	 * @return true si le licenciement n'a pas pu s'effectuer ou si il n'y a pas de Chef disponible.
	 * @see BIILIMO.Chef#licencier()
	 */
	public static boolean premierChefDisponibleLicencier() {
		for(int i=0;i < listChef.size(); i++) {
			if(listChef.get(i).dispo==0 && listChef.get(i).licencier()==true) {
				return true;
			}
		}
		return false;
	
	}

}
