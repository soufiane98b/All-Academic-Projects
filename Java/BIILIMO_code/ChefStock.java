package BIILIMO;

/** Un ChefStock est un objet Chef de specialite Stock. Il peut deplacer les lots (stocker, deplacer et enlever de l'entrepost).
 * @author Raphael MALAK
 * @version 1.0.1
 * @deprecated
 * @see BIILIMO.Chef
 */
public class ChefStock extends Chef{
	
	//CONSTRUCTEUR-----------------------------------------------------------------------------------------------------------
	
	/** Cree un nouveau ChefStock.
	 * @param nom Nom du ChefStock.
	 * @param prenom Prenom du ChefStock.
	 * @deprecated
	 */
	public ChefStock(String nom, String prenom) {
		super(nom, prenom, "Stock");
	}
	
	//METHODES--------------------------------------------------------------------------------------------------------------
	
	
	/**Ranger un lot que l'on vient de recevoir
	 * @param lot Lot a ranger.
	 * @deprecated
	 */
	public void rangerLot(Lot lot) {
		
	}
	
	/** Apporte un lot necessaire a la construction d'un meuble.
	 * @param lot Lot a apporter.
	 * @deprecated
	 */
	public void amenerLotPourMeuble(Lot lot) {
		
	}
	
	
}