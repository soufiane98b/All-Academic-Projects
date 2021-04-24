package BIILIMO;

/** Un ChefBrico est un objet Chef de specialite Brico. Il construit des meubles de n'importe quelle pièce de la maison.
 * @author Raphael MALAK
 * @version 1.0.1
 * @deprecated
 * @see BIILIMO.Chef
 */
public class ChefBrico extends Chef{
	
	//CONSTRUCTEUR-----------------------------------------------------------------------------------------------------------
	/** Cree un nouveau chefBrico
	 * @param nom Nom du ChefBrico
	 * @param prenom Prenom du ChefBrico
	 * @deprecated
	 */
	public ChefBrico(String nom, String prenom) {
		super(nom, prenom, "Brico");
	}
	
	//METHODES--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Construit le meuble passe en parametre.
	 * @param E L'entrepot sur lequel les lots necessaires a la construction du meuble vont etre pris.
	 * @param m Le meuble qui doit être construit.
	 * @deprecated
	 */
	public void construireUnMeuble(Entrepot E, Meuble m) {}
	// On suppose que ce personnel est disponible

}