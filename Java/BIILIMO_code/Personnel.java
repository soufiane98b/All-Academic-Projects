package BIILIMO;

/** Un Personnel est un objet qui permet de deplacer des lots ou bien de construire des meubles suivant sa specialite.
 * @author Raphael Malak
 * @version 1.0.1
*/
public interface Personnel {
	//METHODES--------------------------------------------------------------------------------------------------------------
	
	/** Recrute un personnel (chef ou ouvrier) selon une certaine specialite.
	 * @param nom nom du personnel a recruter.
	 * @param Prenom prenom du personnel a recruter.
	 * @param specialite specialite du personnel a recruter.
	 * @return true quand le recrutement est un succès, false sinon. 
	 */
	static boolean recruter(String nom, String Prenom, String specialite) {
		return false;
	}
	
	/** Licencie un personnel (chef ou ouvrier).
	 * @return true quand le licenciement est un succès, false sinon.
	 */
	boolean licencier();
	
	/**Cherche le premier personnel disponible (chef ou ouvrier)
	 * @param specialite la specialite cherchee
	 * @return le premier personnel disponible, null sinon
	 */
	static Personnel estDispo(String specialite) {
		return null;
	}
	
}