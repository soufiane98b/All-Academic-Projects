package BIILIMO;

//IMPORT-------------------------------------------------------------------------------------------------------------------------------------------
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Permet d'interagir avec l'utilisatuer en proposant un Menu. On doit au debut de l'execution rentrer les prametres pour initaliser l'Entrepot.
 * @author Soufiane Boustique , Raphael Malak
 * @version 1.0.2
 * */

public class Interraction {
	//ATTRIBUTS---------------------------------------------------------------------------------------------------------------------------------------
	
	/** Le nombre de piece possible.
	 */
	static String [] Pieces = {"cuisine", "chambre", "salle a manger", "salon", "salle de bain", "WC"};
	private static Double p;
	private static ArrayList<String> types = new ArrayList<String>();
	static Scanner s=new Scanner(System.in);
	/** Les consignes a executer.
	 */
	private static ArrayList<String> listeConsigne= new ArrayList<>();
	/** Liste d'attente des meubles.
	 */
	private static ArrayList<Meuble> listeAttente= new ArrayList<Meuble>();
	/** Lots non stockes.
	 */
	private static ArrayList<Lot> lotNS= new ArrayList<Lot>();
	private static BufferedReader reader;
	private static BufferedWriter writer ;
	/** Stocke dans un string le contenue de l'execution a chaque pas de temps.
	 */
	private static String Sortie="";
	/** Nos pas temps durant l'execution.
	 */
	private static int temps=0;
	/** Notre tresorie Initiale, on la garde au cas ou veut appliquer Menu 9.
	 */
	private static double Tresorie;
	/** Numero de nos fichiers qu'on cree.
	 */
	private static int numF=0;
	/** identifiant des consignes qu'on cree aleatoirement.
	 */
	private static int idconsigneAlea=0;
	
	//METHODES STATIC --------------------------------------------------------------------------------------------------
	
	/** 
	 * Permet l'initialisation de notre de notre entrepot grace aux donnees rentrees par l'utilisateur erreur definition de l'entrepot verifie dans la classe Entrepot
	 * @return Entrepot initialise
	 */
	public static Entrepot PrintEtRentrerDonneesInitial() throws FileNotFoundException {
		
		System.out.println("Bonjour, dans ce programme vous allez simuler le fonctionnement d'un entrepot.");
		System.out.println("D'abord renseigner les informations suivantes sur l'entrepot.");
		System.out.println("Quelle est le nombre de rangees de votre entrepot: ");
		int m=s.nextInt();
		while(m<=0) {
			System.out.print("erreur dans la dimension, donnez un autre nombre ");
			m=s.nextInt();
			}
		
		System.out.println("Quelle est le nombre de cases par rangee: ");
		int n=s.nextInt();
		while(n<=0) {
			System.out.print("erreur dans la dimension, donnez un autre nombre ");
			n=s.nextInt();
		}
		System.out.println("Quelle est votre tresorie initiale: ");
		double treso= s.nextDouble();
		Tresorie=treso;
		System.out.println("Combien de types different possede un entrepot: ");
		int nbt=s.nextInt();
		while(m*n<nbt || nbt<=0) {
			System.out.print("trop de type ou pas assez, donnez un autre nombre ");
			nbt=s.nextInt();
			}
		ArrayList<String> types = new ArrayList<String> ();
		System.out.print("Tapez 1 pour charger vos types depuis un fichier sinon tapez 0 : ");
		int f=s.nextInt();
		if(f==1)types=chargerTypes(nbt);
		else {
			String type;
			for(int i=1;i<=nbt;i++) {
				System.out.print("Quelle est le nom de votre type "+i+": ");
				type=s.next();
				while(types.contains(type)) {
					System.out.print("vous avez deja rentree ce type, ecrivez un autre: ");
					type=s.next();
				
				}
				types.add(type);
			
			}
		}
		Entrepot E = new Entrepot(m,n,treso,types);
		return E;
		
	}
	
	/** 
	 * @return les types qu'on a recuperer dans un fichier.
	 * @see Interraction#PrintEtRentrerDonneesInitial()
	 */
	public static ArrayList<String> chargerTypes(int nbt) throws FileNotFoundException{
		s.nextLine();
		System.out.println("Quel est le nom de vote fichier? : ");
		String fichier=s.nextLine();
		File file = new File(fichier);
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			ArrayList<String> types= new ArrayList<>();
			while(line != null) {
				types.add(line.replaceAll(" ", ""));
				line = reader.readLine();
			}
			if(types.size()!=nbt) {System.out.println("Erreur fichier : vous n'avez pas rentre "+nbt+" types");throw new FileNotFoundException();}
			return types;
			
		}
		catch(Exception e){
			System.out.println("impossible de trouver le fichier");
			e.printStackTrace();
			throw new FileNotFoundException();
		}
	}
	
	/** 
	 * Affiche un menu sur le terminal, pour afficher des parametres ou executer des consignes.
	 */
	public static void printMenuInterraction() {
		System.out.println("-----------------\nVoici le Menu\n------------------");
		System.out.println("Tapez 0 pour sortir du menu et arreter la simulation");
		System.out.println("Tapez 1 pour lire vos consignes depuis un fichier");
		System.out.println("Tapez 2 pour lire vos consignes depuis la console");
		System.out.println("Tapez 3 pour generer des consignes aleatoires dans un fichier");
		System.out.println("Tapez 4 pour afficher l'etat de votre entrepot");
		System.out.println("Tapez 5 pour afficher les lots non stockes");
		System.out.println("Tapez 6 pour afficher les meubles construits ");
		System.out.println("Tapez 7 pour afficher les meubles non construis ");
		System.out.println("Tapez 8 pour generer un fichier qui affiche l'etat global de votre execution a chaque pas de temps");
		System.out.println("Tapez 9 pour pour recommencer l'execution on ne gardant que les donnees initial de l'entrepot");//ie en vide l'entrepot et le fichier de sortie
  }
	/** 
	 * @param i numero du menu a executer
	 * @param E entrepot sur lequel  s'effectue l'execution
	 */	
	public static Entrepot executionMenu(int i,Entrepot E) throws IllegalArgumentException {
		switch (i) {
    		case(0):
    			return E;
    		case (1):
    			s.nextLine();
    			System.out.println("Quel est le nom de vote fichier? : ");
    			String fichier=s.nextLine();
    			File file = new File(fichier);
    			listeConsigne = lireEtVerifierFichier(file,E);
    			executerStrategie(E);
    			return E;
    		case (2):
    			int continuer=0;
    			while(continuer==0) {
    				s.nextLine();
    				int alea=0;
    				do {
    				System.out.println("Voulez vous genrer une consigne aleatoirement ? si oui tapez 1 sinon 0: ");
    				alea=s.nextInt();
    				}
    				while(alea!=0 && alea !=1);
    				String consigne;
    				if(alea==1) {
    					consigne=genererConsigneAlea(E);
    					System.out.println("Voici votre consigne : "+consigne);
    				}
    				else {
    					s.nextLine();
    					System.out.println("Tapez votre consigne : ");
    					consigne =s.nextLine();
    				}
    				while(verifieSyntaxeConsigne(consigne,E)==false) {
    					System.out.println("Erreur dans votre consigne , ressayer : ");
    					consigne =s.nextLine();
    				}
    				listeConsigne.add(consigne.replaceAll(" ", ""));
    				//NB: l execution commencera quand toutes les consignes seront rentree , notez que executer consigne pas consigne est different d'exectuer plusieurs a la suite
    				System.out.print("Tapez un chiffre pour executer vos/votre consigne ou tapez 0 pour rajouter une autre consigne : ");
    				continuer=s.nextInt();
    			}
    			executerStrategie(E);
    			return E;
    		case (3):
    			System.out.println("Combien de consignes ? : ");
    			int nb=s.nextInt();
    			while(nb<=0) {
    				System.out.println("Erreur de saisie, reesayer  : ");
    				nb=s.nextInt();
    			}
    			genererFichierConsigneAlea(E,nb);
    			return E;
    		case (4):
    			System.out.println(E);
    			return E;
    		case (5):
    			System.out.println(lotNS);
    			return E;
    		case (6):
    			System.out.println(Meuble.ListeTousMeublesC);
    			return E;
    		case (7):
    			System.out.println(Meuble.ListeTousMeublesNC);
    			return E;
    		case(8):
    			genererFichierSortie();
    			return E;
    		case(9):
    			Sortie="";
    			temps=0;
    			lotNS.clear();
    			Meuble.ListeTousMeublesNC.clear();
    			Meuble.ListeTousMeublesC.clear();
    			Entrepot E1= new Entrepot(E.getM_range(),E.getTaille(),Tresorie,E.getTypes());
    			return E1;
    		default:
    			System.out.println("Cette valeur ne fait pas partie des possibilites");
		}
		return null;
	}
	
	/** On execute la strategie qu'on souhaite
	 * @param E entrepot sur lequel  s'effectue l'execution d'une strategie
	 */	
	public static void executerStrategie(Entrepot E) {
		int strategie;
		//s.nextLine();
		System.out.println("Quel est votre strategie 1 ou 2  ou 3 ou 4 (voire rapport pour le detail des strategies) ? : ");
		strategie=s.nextInt();
		while(strategie<1 || strategie>4) {
			//s.nextLine();
			System.out.println("Quel est votre strategie 1 ou 2 ou 3 ou 4 ? : ");
			strategie=s.nextInt();
		}
		if(strategie==3) {executerS3(E);return;}
		if(strategie==4) {executerS4(E);return;}
		int strategieP;
		//s.nextLine();
		System.out.println("Quel est votre strategie de recrutement d'ouvrier 1 ou 2  ? : ");
		strategieP=s.nextInt();
		while(strategie<1 || strategie>2) {
			//s.nextLine();
			System.out.println("Quel est votre strategie de recrutement d'ouvrier 1 ou 2  ? :  ");
			strategieP=s.nextInt();
		}

		if(strategie==1)executerS1(E,strategieP);
		if(strategie==2)executerS2(E,strategieP);
		
	}
	
	//METHODES POUR VERIFIER CONSIGNE --------------------------------------------------------------------------------------------------

	/** 
	 * Verifie la syntaxe du fichier 
	 * @param file lit tout le fichier entre par l'utilisateur
	 * @param E entrepot sur lequel  s'effectue l'execution
	 * @return renvoie une ArrayList de chaines de caracteres ou chaque element correspond a 1 ligne du fichier(consigne) 
	 */	
	public static ArrayList<String> lireEtVerifierFichier(File file,Entrepot E)  throws IllegalArgumentException {
		try {
			reader =  new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			ArrayList<Integer> IDs = new ArrayList<>();
			while(line != null) {
				// on verifie la syntaxe de la consigne
				if(verifieSyntaxeConsigne(line,E)==false) {System.out.println("erreur dans la consigne :"+line+", corriger votre fichier puis relancer le programme");throw new IllegalArgumentException();}
				// on verifie que le numero de la consigne n'est pas deja present
				int id=Integer.valueOf(line.replaceAll(" ","").split("[<>]")[1]);
				if(IDs.contains(id)) {System.out.println("erreur numero id consigne se repete :"+id+", corriger votre fichier puis relancer le programme");throw new IllegalArgumentException();}
				IDs.add(id);
				//on ajoute la consigne verifie 
				//System.out.println(line);
				listeConsigne.add(line.replaceAll(" ",""));
				line = reader.readLine();
			}
			reader.close();
			return listeConsigne;
		}
		catch(Exception e){
			System.out.println("impossible de trouver le fichier");
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
	}
	
	
	
	/** verifie syntaxe d'une consigne
	 * @param E entrepot sur lequel  s'effectue l'execution
	 * @param c correspon a une consigne
	 * @return faux si mauvaise syntaxe sinon vrai
	 * */
	public static boolean verifieSyntaxeConsigne(String c,Entrepot E) {
		
		if(verifierConsigneRien(c)==false && verifierConsigneStockerLot(c,E)==false &&  verifierConsigneConstruireMeuble(c,E)==false)return false;
		return true;
	}
	
	/** syntaxe : id> rien
	 * @param consigne correspon a une consigne de type rien
	 * @return faux si mauvaise syntaxe sinon vrai
	 * */
	public static boolean verifierConsigneRien(String consigne) {
		int p1,p2,p3;
		String s=consigne.replaceAll(" ","");
		p1=s.indexOf("<");
		p2=s.indexOf(">");
		p3=s.indexOf("rien");
		if(p1==-1 || p2==-1 || p3==-1 || p1!=0 || p2>p3|| (p2+1)!=p3)return false;
		try {
			String subint=(String) s.subSequence(p1+1,p2);
			p1=Integer.parseInt(subint);
			s=s.replaceFirst("<","");s=s.replaceFirst(">","");s=s.replaceFirst("rien","");s=s.replaceFirst(subint,"");
			if(! s.equals(""))return false;
			return true;
		}
		catch (Exception e) {
			return false;
		}
		
	}
	/** syntaxe : id> lot nom> poids> prix> volume>
	 * @param E entrepot sur lequel  s'effectue l'execution
	 * @param consigne correspon a une consigne de type lot
	 * @return faux si mauvaise syntaxe sinon vrai
	 * */
	public static boolean verifierConsigneStockerLot(String consigne,Entrepot E) {
		int p1,p2,p3;
		String s=consigne.replaceAll(" ","");
		p1=s.indexOf("<");
		p2=s.indexOf(">");
		p3=s.indexOf("lot");
		if (p1==-1 || p2==-1 || p3==-1 || p1>p2 || p2>p3 || (p2+1)!=p3 )return false;
		try {
			String subint=(String) s.subSequence(p1+1,p2);
			p1=Integer.parseInt(subint);
			s=s.replaceFirst("<","");s=s.replaceFirst(">","");s=s.replaceFirst("lot","");s=s.replaceFirst(subint,"");
			//<nom><poids><prix><volume>
			s=verifierProchainEntreGuillemets(s,"s","type",E);
			types.clear();
			if(s.equals("non"))return false;
			s=verifierProchainEntreGuillemets(s,1.1,"",E);
			if(s.equals("non"))return false;
			s=verifierProchainEntreGuillemets(s,1.1,"",E);
			if(s.equals("non"))return false;
			s=verifierProchainEntreGuillemets(s,1,"",E);
			if(s.equals("non"))return false;
			
			if(!s.equals(""))return false;
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
		
		
	}
	/** syntaxe : id> meuble nom> pieceMaison> dureeConstruction> typeLot1> volumeLot1> typeLot2> volumeLot2> ...
	 * @param E entrepot sur lequel  s'effectue l'execution
	 * @param consigne correspond a une consigne de type meuble
	 * @return faux si mauvaise syntaxe sinon vrai
	 * */
	public static boolean verifierConsigneConstruireMeuble(String consigne,Entrepot E) {
		int p1,p2,p3;
		String s=consigne.replaceAll(" ","");
		p1=s.indexOf("<");
		p2=s.indexOf(">");
		p3=s.indexOf("meuble");
		if(p1==-1 || p2==-1 || p3==-1 || p1!=0 || p2>p3|| (p2+1)!=p3)return false;
		try {
			String subint=(String) s.subSequence(p1+1,p2);
			p1=Integer.parseInt(subint);
			s=s.replaceFirst("<","");s=s.replaceFirst(">","");s=s.replaceFirst("meuble","");s=s.replaceFirst(subint,"");
			//<nom> <pieceMaison> <dureeConstruction> <typeLot1> <volumeLot1> <typeLot2> <volumeLot2> ...
			s=verifierProchainEntreGuillemets(s,"s","",E);
			if(s.equals("non"))return false;
			s=verifierProchainEntreGuillemets(s,"s","pieceMaison",E);
			if(s.equals("non"))return false;
			s=verifierProchainEntreGuillemets(s,1,"",E);
			if(s.equals("non") || s.equals(""))return false;
			//<typeLot1> <volumeLot1> <typeLot2> <volumeLot2> ...
			int cmpt=0;
			for(int i=1;s.equals("")==false;i++) {
				if(i%2==1) {//<typeLot1>
					cmpt++;
					s=verifierProchainEntreGuillemets(s,"s","type",E);
					if(s.equals("non"))return false;
				}
				else {//<volumeLot1>
					cmpt++;
					s=verifierProchainEntreGuillemets(s,1,"",E);
					if(s.equals("non"))return false;
					
				}
				
			}
			if(cmpt%2==0) {types.clear();return true;}
			return false;
		}
		catch (Exception e4) {
			return false;
		}
	
	}
	
	/** verifie le prochain entre guillemets est bien du type  voulue
	 * @param consigne correspon a une consigne
	 * @param e le type qu'on veut verifier
	 * @param condition a verifier
	 * @param E entrepot sur lequel  s'effectue l'execution
	 * @return renvoi consigne sans le premier 'e==type' et sinon renvoi "non"
	 * */
	public static String verifierProchainEntreGuillemets(String consigne,Object e,String condition,Entrepot E) {
		int p1=consigne.indexOf("<"),p2=consigne.indexOf(">");
		if(p1!=0 ||p2<p1)return "non";
		if(e instanceof String) {
			try {
				String substring=(String) consigne.subSequence(p1+1,p2);
				if(condition.equals("pieceMaison") && isPieceMaison(substring)==false)return "non";
				if(condition.equals("type") && E.getTypes().contains(substring)==false)return "non";
				if(condition.equals("type")) {
					if(types.contains(substring))return "non";
					types.add(substring);
					}
				if(substring.equals(""))return "non";
				consigne=consigne.replaceFirst("<","");consigne=consigne.replaceFirst(">","");consigne=consigne.replaceFirst(substring,"");
				return consigne;
			}
			catch (Exception e1) {
				return "non";
			}
		
		}
		if(e instanceof Integer) {
			try {
				String subint=(String) consigne.subSequence(p1+1,p2);
				p1=Integer.parseInt(subint);
				if(p1<=0)return "non";
				consigne=consigne.replaceFirst("<","");consigne=consigne.replaceFirst(">","");consigne=consigne.replaceFirst(subint,"");
				return consigne;
			}
			catch (Exception e2) {
				return "non";
			}
		}
		if(e instanceof Double) {
			try {
				String subint=(String) consigne.subSequence(p1+1,p2);
				p = Double.parseDouble(subint);
				if(p<=0)return "non";
				consigne=consigne.replaceFirst("<","");consigne=consigne.replaceFirst(">","");consigne=consigne.replaceFirst(subint,"");
				return consigne;
			}
			catch (Exception e2) {
				return "non";
			}
		}
		
		return "non";
		
	}
		
	/** @param Piece a verifier
	 * @return faux si mal ecrie sinon vrai
	 * */
	public static boolean isPieceMaison(String Piece) {
		boolean b =false;
		for(int i=0;i<Pieces.length;i++) {
			if(Pieces[i].replaceAll(" ", "").equalsIgnoreCase(Piece.replaceAll(" ","")))b=true;
		}
		return b;
	}
	
	//METHODES POUR EXECUTER CONSIGNE --------------------------------------------------------------------------------------------------
	
	/** @param C consigne a executer
	 * */
	public static void executerConsigneRien(String C) {
		return;	
	}
	/** Ajoute le lot dans l'entrepot, et le renvoi que si il a pas pu le rajouter
	 *  @param C consigne de type Lot a executer
	 * */
	public static Lot executerConsigneLot(String C,Entrepot E) {
		int id=Integer.parseInt(C.substring(indexOf(C,'<',1)+1,indexOf(C,'>',1)));
		String type = C.substring(indexOf(C,'<',2)+1,indexOf(C,'>',2));
		double poids=Double.parseDouble(C.substring(indexOf(C,'<',3)+1,indexOf(C,'>',3)));
		double prix=Double.parseDouble(C.substring(indexOf(C,'<',4)+1,indexOf(C,'>',4)));
		int volume=Integer.parseInt(C.substring(indexOf(C,'<',5)+1,indexOf(C,'>',5)));
		Lot L = new Lot(volume,type , poids, prix);
		if(E.stockerL(L)==false) return L;
		return null;// a reuissi a le stocker
		
	}
	/**
	 *  @param C consigne de type Meuble a executer
	 *  @return retourne le meuble initialise
	 * */
	public static Meuble executerConsigneMeuble(String C) {
		int id=Integer.parseInt(C.substring(indexOf(C,'<',1)+1,indexOf(C,'>',1)));
		String nom = C.substring(indexOf(C,'<',2)+1,indexOf(C,'>',2));
		String pieceMaison = C.substring(indexOf(C,'<',3)+1,indexOf(C,'>',3));
		int duree=Integer.parseInt(C.substring(indexOf(C,'<',4)+1,indexOf(C,'>',4)));
		Meuble m=new Meuble(nom,pieceMaison,duree);
		String type;
		int volume,last=C.lastIndexOf('<');
		int i=5;
		for(i=5;indexOf(C,'<',i+1)!=last;i=i+2) {
			
			type=C.substring(indexOf(C,'<',i)+1,indexOf(C,'>',i));
			volume=Integer.parseInt(C.substring(indexOf(C,'<',i+1)+1,indexOf(C,'>',i+1)));
			m.getComposant().put(type,volume);
		}
		type=C.substring(indexOf(C,'<',i)+1,indexOf(C,'>',i));
		volume=Integer.parseInt(C.substring(indexOf(C,'<',i+1)+1,indexOf(C,'>',i+1)));
		m.getComposant().put(type,volume);
		return m;

	}
	/**
	 *  @param C consigne 
	 *  @param S caractere a trouver
	 *  @param i eme apparation du caractere S dans C
	 *  @return retourne l'index du i eme symbole dans C
	 * */
	public static int indexOf(String C,char S,int i) {
		if(i==1) {
			return C.indexOf(S);
		}
		return C.indexOf(S)+indexOf((String) C.subSequence(C.indexOf(S)+1,C.length()),S,i-1)+1;
	}

	//---------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Genere un fichier de sortie qui affiche nos donnees a chaque pas de temps
	 * */
	public static void genererFichierSortie() {
		try {
			numF++;
			writer=new BufferedWriter(new FileWriter(new File("sortie"+numF),true));
			writer.write(Sortie);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//METHODE pour generer comande aleatoire ---------------------------------------------------------------------------------------------------
	
	/**
	 * Genere une consigne aleatoirement
	 * 0 :consigne rien
	 * 1 :consigne lot
	 * 2 :consigne meuble
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * */

	public static String genererConsigneAlea(Entrepot E) {
		Random random = new Random();
		int n_consigne = random.nextInt(3);
		String consigne="";
		switch(n_consigne) {
			case 0:
				consigne=genererConsigneRienAlea();
				break;
			case 1:
				consigne=genererConsigneLotAlea(E);
				break;
			case 2:
				consigne=genererConsigneMeubleAlea(E);
				break;
		}
		return consigne;
	}
	
	/**
	 * @return une consigne de type rien genere aleatoirement
	 * */
	public static String genererConsigneRienAlea() {
		String c_rien="<"+idconsigneAlea+">"+" rien";
		idconsigneAlea++;
		return c_rien;
	}
	
	/**
	 * Le poids sera entre 1 25
	 * Le prix sera entre 10 et 59
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @return une consigne de type Lot genere aleatoirement
	 * */
	public static String genererConsigneLotAlea(Entrepot E) {
		Random random = new Random();
		int n_typeAlea=random.nextInt(E.getTypes().size());
		int poids=random.nextInt(25)+1;
		int prix=random.nextInt(50)+10;
		int volume=random.nextInt(E.getTrange().get(n_typeAlea%E.getM_range()).getBloc().get(0).getTaille()-1)+1;
		String c_lot="<"+idconsigneAlea+">"+"lot"+"<"+E.getTypes().get(n_typeAlea)+">"+"<"+poids+">"+"<"+prix+">"+"<"+volume+">";
		idconsigneAlea++;
		return c_lot;
	}
		
	/**
	 * Le nombre de type du meuble ne depassera pas 4
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @return une consigne de type Meuble genere aleatoirement
	 * */	
	public static String genererConsigneMeubleAlea(Entrepot E) {
		Random random = new Random();
		int n_piece=random.nextInt(6);
		int duree=random.nextInt(50)+1;// on fixe max duree =50
		String c_meuble="<"+idconsigneAlea+">"+"meuble"+"<m"+idconsigneAlea+">"+"<"+Pieces[n_piece]+">"+"<"+duree+">";
		int nb_Tlot;
		if(E.getTypes().size()>4) nb_Tlot=random.nextInt(4)+1;
		else nb_Tlot=random.nextInt(E.getTypes().size()-1)+1;
		int cmpt=0;
		ArrayList<Integer> tableauNumeroType= new ArrayList<Integer>(); 
		while(cmpt!=nb_Tlot) {
			int num_type=random.nextInt(E.getTypes().size());
			if(tableauNumeroType.contains(num_type)==false) {
				tableauNumeroType.add(num_type);
				cmpt++;
			}
		}
		int volume;
		for(int i=0;i<tableauNumeroType.size();i++) {
			c_meuble=c_meuble+"<"+E.getTypes().get(tableauNumeroType.get(i))+">";
			volume=random.nextInt(E.getTrange().get(tableauNumeroType.get(i)%E.getM_range()).getBloc().get(0).getTaille()-1)+1;
			c_meuble=c_meuble+"<"+volume+">";
		}
		
		idconsigneAlea++;
		return c_meuble;
	}
	/**
	 * Genere un fichier de consigne cree aleatoirement
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @param nbConsigne le nombre de consigne qu'on veut generer
	 * */	
	public static void genererFichierConsigneAlea(Entrepot E,int nbConsigne) {
		try {
			if(nbConsigne==0)return;
			String finaleC=genererConsigneAlea(E);
			writer=new BufferedWriter(new FileWriter(new File("consigneAlea"+numF),true));
			System.out.println("Voici le nom de votre fichier : consigneAlea"+numF);
			for(int i=0;i<nbConsigne-1;i++) {
				finaleC=finaleC+"\n"+genererConsigneAlea(E);
			}
			writer.write(finaleC);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//METHODES SUR LES STRATEGIES ------------------------------------------------------------------------------------------
	
	//Notre strategie principale est base sur des lots qui sont toujours bien ranges, donc on a besoin que de 2 ouvrier minimum pour ajouter un lot ou l'enlever
	
	/** STRATEGIE 1 : COMPLEXITE + 
	 * Avoir selon le choix 2 ou 1 ouvrier et chef brico qui gere tout l'entrepot sans fonction est rentable
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @param SP choix de l'initialisation du personnel
	 * */
	public static void executerS1(Entrepot E,int SP) {
		Chef.getListChef().clear();
		if(SP==1)Entrepot.InitialiserPersonnelP1();
		if(SP==2)Entrepot.InitialiserPersonnelP2();
		Sortie=Sortie+"\t\t Execution de la Strategie 1."+SP;
		Sortie=Sortie+"\t ---------------------Temps : "+temps+"---------------------\n"+E+"\n";
		Sortie=Sortie+Chef.getListChef()+"\n";
		String Consigne;
		Meuble m;
		for(int i=0;i<listeConsigne.size();i++) {
			temps++;
			Consigne=listeConsigne.get(i);
			if(verifierConsigneRien(Consigne)) {
				executerConsigneRien(Consigne);
				executerListeAttente1(E);
			}
			if(verifierConsigneStockerLot(Consigne,E)) {
				Lot L=executerConsigneLot(Consigne,E);
				if(L!=null) {lotNS.add(L);}
				executerListeAttente1(E);
			}
			if(verifierConsigneConstruireMeuble(Consigne,E)) {
				m=executerConsigneMeuble(Consigne);
				listeAttente.add(m);
				executerListeAttente1(E);
				
			}
			
			Chef.payer(E);// on paye nos ouvriers
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"--------------------- Consigne="+Consigne+"\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			Chef.soustraireDisponibilite();
		}
		//ON A FINI DE LIRE TOUTES LES CONSIGNES, ON EXECUTE L'ATTENTE
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"LES CONSIGNES ONT ETE LUE, ON EXECUTE LA LISTE D'ATTENTE DES MEUBLES\n";
		while(listeAttente.size()!=0) {
			temps++;
			executerListeAttente1(E);
			Chef.payer(E);// on paye nos ouvriers
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"---------------------"+"\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			Chef.soustraireDisponibilite();
		}
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"LES CONSIGNES ONT ETE LUES ET LA LISTE D'ATTENTE EST FINIE, ON ATTEND LA FIN DE CONSTRUCTION DES DERNIERS MEUBLES\n";
		while(Chef.tousDispoouPas()==false) {
			temps++;
			Chef.payer(E);
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"---------------------\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits ( pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			if(Chef.premierChefDisponibleLicencier()==false)Chef.premierOuvrierDisponibleLicencier();
			Chef.soustraireDisponibilite();
		}
		Chef.premierChefDisponibleLicencier();
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"VOICI L'ETAT DE VOS DONNEES FINAL\n";
		Sortie=Sortie+"\t---------------------Temps : "+temps+"---------------------\n";
		Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
		Sortie=Sortie+"Les meubles non construits (pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		listeConsigne.clear();
	}
	
	/** 
	 * Execute notre liste d'attente associe a la strategie 1
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @see Interraction#executerS1(Entrepot E,int SP)
	 * */
	public static void executerListeAttente1(Entrepot E) {
		Meuble m1;
		for(int j=0;j<listeAttente.size();j++) {
			m1=listeAttente.get(j);
			int oui=m1.construireUnMeuble(E);
			if(oui==1) {
				listeAttente.remove(m1);j=j-1;
				Meuble.ListeTousMeublesC.add(m1);
			}
			if(oui==-1) {
				Meuble.ListeTousMeublesNC.add(m1);
				listeAttente.remove(m1);
				j=j-1;
			}
			if(j!=-1 && listeAttente.get(j).getComposant().size()!=0){
				break;
			}//on finit d'apporter tous les lots requis pour le meuble j avant de passer a j+1, on est aussi sur que tout notre personnel est occupe
		}
	}
	
	
	
	/** STRATEGIE 2 : COMPLEXITE +++
	 * Avoir selon le choix 2 ou 1 ouvrier de base et un  chef brico qui gere tout l'entrepot 
	 * et RECRUTE UN OU DEUX OUVRIER EN PLUS SELON LE BESOIN ET LE LICENCIE ET APPLIQUE FONCTION EST RENTABLE 1
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @param SP choix de l'initialisation du personnel
	 * @see Meuble#estRentable(Entrepot E)
	 * */
	public static void executerS2(Entrepot E,int SP)throws IllegalArgumentException {
		Chef.getListChef().clear();
		if(SP==1)Entrepot.InitialiserPersonnelP1();
		if(SP==2)Entrepot.InitialiserPersonnelP2();
		Sortie=Sortie+"\t\t Execution de la Strategie 2."+SP;
		Sortie=Sortie+"\t ---------------------Temps : "+temps+"---------------------\n"+E+"\n";
		Sortie=Sortie+Chef.getListChef()+"\n";
		String Consigne;
		Meuble m;
		boolean recruter=false;
		for(int i=0;i<listeConsigne.size();i++) {
			temps++;
			Consigne=listeConsigne.get(i);
			if(verifierConsigneRien(Consigne)) {
				executerConsigneRien(Consigne);
				executerListeAttente(E);
			}
			if(verifierConsigneStockerLot(Consigne,E)) {
				Lot L;
				L=executerConsigneLot(Consigne,E);
				if(L!=null) {// on sait qu'il n'a pas pu etre stocke soit par manque de place ou personnel
					int  nor=E.nbOuvrierRStockerL(L);// doit renvoyer -1 si pas assez de place 
					if(nor==2 && recruter==false) {// on sait qu on a assez de place et on ajuste besoin de 2 ouvrier 
						Ouvrier.recruter("sami", "boustique", "sans");
						if(executerConsigneLot(Consigne,E)!=null) {
							System.out.println("erreur");// pas cense arriver
							throw new IllegalArgumentException();
						};
						recruter=true;
					}
					else {lotNS.add(L);}
				}
				executerListeAttente(E);
			}
			if(verifierConsigneConstruireMeuble(Consigne,E)) {
				m=executerConsigneMeuble(Consigne);
				listeAttente.add(m);
				executerListeAttente(E);
				
			}
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS2();// plus d'ouvrier dispo avec brico ==0
				executerListeAttente(E);
			}
			
			Chef.payer(E);// on paye nos ouvriers
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"--------------------- Consigne="+Consigne+"\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			Chef.soustraireDisponibilite();
			if(recruter==true) {Chef.getListChef().get(0).getEquipe().get(1).licencier();recruter=false;}
			
		}
		//ON A FINI DE LIRE TOUTES LES CONSIGNES, ON EXECUTE L'ATTENTE
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"LES CONSIGNES ONT ETE LUE, ON EXECUTE LA LISTE D'ATTENTE DES MEUBLES\n";
		while(listeAttente.size()!=0) {
			temps++;
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS2();// plus d'ouvrier dispo avec brico ==0
				executerListeAttente(E);
			}
			
			executerListeAttente(E);
			Chef.payer(E);
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"---------------------"+"\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			Chef.soustraireDisponibilite();
			if(recruter==true) {Chef.getListChef().get(0).getEquipe().get(SP).licencier();recruter=false;}
			
			
		}
		//ON A FINI LA LISTE D'ATTENTE ET LES CONSIGNES, ET ON FINI LA CONSTRUCTION DES MEUBLES TOUT EN SUPPRIMANT LES OUVRIERS QUI FONT RIEN
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"LES CONSIGNES ONT ETE LUES ET LA LISTE D'ATTENTE EST FINIE, ON ATTEND LA FIN DE CONSTRUCTION DES DERNIERS MEUBLES\n";
		while(Chef.tousDispoouPas()==false) {
			temps++;
			Chef.payer(E);
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"---------------------\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			if(Chef.premierChefDisponibleLicencier()==false)Chef.premierOuvrierDisponibleLicencier();
			Chef.soustraireDisponibilite();
		}
		Chef.premierChefDisponibleLicencier();
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"VOICI L'ETAT DE VOS DONNEES FINAL\n";
		Sortie=Sortie+"\t---------------------Temps : "+temps+"---------------------\n";
		Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
		Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		listeConsigne.clear();
	}
	
	/** ON RECRUTE UN OUVRIER POUR ACCELERER L'ACHEMINEMENT DES LOTS AINSI ON PEUT CONSTRUIRE LES MEUBLES PLUS RAPIDEMENT 
	 * ET PERDRE MOINS D'ARGENT PAR TOUR
	 * @return vrai si on a pu recruter sinon faux
	 * */
	static boolean recruterUnautreOuvirerS2() {
		if(Chef.getListChef().get(0).getDispo()==0 && listeAttente.size()!=0) {
			Ouvrier.recruter("stalone", "silvester", "sans");
			return true;
		}
		return false;
	}
	/** 
	 * Execute notre liste d'attente associe a la strategie 2 ou 3
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @see Interraction#executerS2(Entrepot E,int SP)
	 * @see Interraction#executerS3(Entrepot E)
	 * */
	public static void executerListeAttente(Entrepot E) {
		Meuble m1;
		for(int j=0;j<listeAttente.size();j++) {
			m1=listeAttente.get(j);
			if(m1.estRentable(E)==false && m1.isDebute()==false) {
				Meuble.ListeTousMeublesNC.add(m1);
				listeAttente.remove(m1);
				j=j-1;
			}
			
			int oui=m1.construireUnMeuble(E);
			if(oui==1) {
				listeAttente.remove(m1);j=j-1;
				Meuble.ListeTousMeublesC.add(m1);
			}
			if(j!=-1 && listeAttente.get(j).getComposant().size()!=0){
				break;
			}//on finit d'apporter tous les lots requis pour le meuble j avant de passer a j+1, on est aussi sur que tout notre personnel est occupe
		}
	}
	
	
	/** STRATEGIE 3 : COMPLEXITE +++++
	 * Avoir un ouvrier et un chef brico de base et recrute au maximum 3 autres ouvriers selon le besoin  applique fonction est rentable 1 
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @see Meuble#estRentable(Entrepot E)
	 * */
	public static void executerS3(Entrepot E)throws IllegalArgumentException {
		Chef.getListChef().clear();
		Entrepot.InitialiserPersonnelP1();
		Sortie=Sortie+"\t\t Execution de la Strategie 3";
		Sortie=Sortie+"\t ---------------------Temps : "+temps+"---------------------\n"+E+"\n";
		Sortie=Sortie+Chef.getListChef()+"\n";
		String Consigne;
		Meuble m;
		boolean licencier=false;
		boolean recruter=false;
		for(int i=0;i<listeConsigne.size();i++) {
			temps++;
			Consigne=listeConsigne.get(i);
			if(verifierConsigneRien(Consigne)) {
				executerConsigneRien(Consigne);
				executerListeAttente(E);
			}
			if(verifierConsigneStockerLot(Consigne,E)) {
				Lot L;
				L=executerConsigneLot(Consigne,E);
				if(L!=null) {// on sait qu'il n'a pas pu etre stocke soit par manque de place ou personnel
					int  nor=E.nbOuvrierRStockerL(L);// doit renvoyer -1 si pas assez de place 
					if(nor==2 && recruter==false) {// on sait qu on a assez de place et on ajuste besoin de 2 ouvrier 
						Ouvrier.recruter("sami", "boustique", "sans");
						if(executerConsigneLot(Consigne,E)!=null) {
							System.out.println("erreur");// pas cense arriver
							throw new IllegalArgumentException();
						};
						recruter=true;
					}
					else {
						lotNS.add(L);
					}
				}
				executerListeAttente(E);
			}
			if(verifierConsigneConstruireMeuble(Consigne,E)) {
				m=executerConsigneMeuble(Consigne);
				listeAttente.add(m);
				executerListeAttente(E);
				
			}
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS2();// plus d'ouvrier dispo avec brico ==0
				executerListeAttente(E);
			}
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS3etExecuterConstructionMeuble(E);
			}
			// ON PASSE AU PAS DE TEMPS SUIVANT 
			Chef.payer(E);// on paye nos ouvriers
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"--------------------- Consigne="+Consigne+"\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			Chef.soustraireDisponibilite();
			licencier=licencierUnautreOuvirerSansS3();
			if(licencier==false ) licencier=licencierUnautreOuvirerS3();
			recruter=false;
			licencier=false;
		}
		
		
		//ON A FINI DE LIRE TOUTES LES CONSIGNES, ON EXECUTE L'ATTENTE
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"LES CONSIGNES ONT ETE LUE, ON EXECUTE LA LISTE D'ATTENTE DES MEUBLES\n";
		while(listeAttente.size()!=0) {
			temps++;
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS2();// plus d'ouvrier dispo avec brico ==0
				executerListeAttente(E);
			}
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS3etExecuterConstructionMeuble(E);
			}
			executerListeAttente(E);
			Chef.payer(E);
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"---------------------"+"\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			Chef.soustraireDisponibilite();
			licencier=licencierUnautreOuvirerSansS3();
			if(licencier==false) licencier=licencierUnautreOuvirerS3();
			recruter=false;
			licencier=false;
			
		}
		//ON A FINI LA LISTE D'ATTENTE ET LES CONSIGNES, ET ON FINI LA CONSTRUCTION DES MEUBLES TOUT EN SUPPRIMANT LES OUVRIERS QUI FONT RIEN
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"LES CONSIGNES ONT ETE LUES ET LA LISTE D'ATTENTE EST FINIE, ON ATTEND LA FIN DE CONSTRUCTION DES DERNIERS MEUBLES\n";
		while(Chef.tousDispoouPas()==false) {
			temps++;
			Chef.payer(E);// on paye nos ouvriers
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"---------------------\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			if(Chef.premierChefDisponibleLicencier()==false)Chef.premierOuvrierDisponibleLicencier();
			Chef.soustraireDisponibilite();
			
		}
		Chef.premierChefDisponibleLicencier();
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"VOICI L'ETAT DE VOS DONNEES FINAL\n";
		Sortie=Sortie+"\t---------------------Temps : "+temps+"---------------------\n";
		Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
		Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		listeConsigne.clear();
	}
	
	/** ON RECRUTE  UN AUTRE OUVRIER POUR CONSTRUIRE UN MEUBLE SI LA DURE DE CE MEUBLE EST INFERIEUR AU LA DURE DU CHEF BRICO 
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @return vrai si on a pu recruter sinon faux
	 * */
	static boolean recruterUnautreOuvirerS3etExecuterConstructionMeuble(Entrepot E) {
		for(int i=0;i<listeAttente.size();i++) {
			if(listeAttente.get(i).getD_Constru()<Chef.getListChef().get(0).getDispo()&& listeAttente.get(i).getComposant().size()==0 && Ouvrier.placeDansEquipe(Chef.getListChef().get(0).getEquipe())>1) {
				for(int j=1;j<4;j++) {
					Ouvrier O=Chef.getListChef().get(0).getEquipe().get(j);
					if(O!=null && O.getNom().equals("Mercury")) {
						Ouvrier.recruter("Cena", "John",listeAttente.get(i).getPiece());
						Meuble m1=listeAttente.get(i);
						if(m1.construireUnMeuble(E)==1) {
							listeAttente.remove(m1);
							Meuble.ListeTousMeublesC.add(m1);
						}
						return true;
					}
				}
				Ouvrier.recruter("Mercury", "Fredy",listeAttente.get(i).getPiece() );
				Meuble m1=listeAttente.get(i);// cette fonction appelle le premier personnel disponible
				if(m1.construireUnMeuble(E)==1) {
					listeAttente.remove(m1);
					Meuble.ListeTousMeublesC.add(m1);
				}
				return true;
			}
		}
		return false;
	}
	static boolean licencierUnautreOuvirerS3() {
		for(int j=1;j<4;j++) {
			Ouvrier O=Chef.getListChef().get(0).getEquipe().get(j);
			if(O!=null && O.getSpecialite().equals("sans")==false && O.getDispo()==0 ) {
				O.licencier();
				return true;
			}
		}
		return false;
	}
	static boolean licencierUnautreOuvirerSansS3() {
		for(int j=1;j<4;j++) {
			Ouvrier O=Chef.getListChef().get(0).getEquipe().get(j);
			if(O!=null && O.getSpecialite().equals("sans")==true && O.getDispo()==0 ) {
				O.licencier();
				return true;
			}
		}
		return false;
	}
	
	
	/** STRATEGIE 4 : COMPLEXITE +++++
	 * MEME QUE LA STRATEGIE 3 APPLIQUE JUSTE FONCTION EST RENTABLE 2  
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @see Meuble#estRentable2(Entrepot E)
	 * */
	public static void executerS4(Entrepot E)throws IllegalArgumentException {
		Chef.getListChef().clear();
		Entrepot.InitialiserPersonnelP1();
		Sortie=Sortie+"\t\t Execution de la Strategie 4";
		Sortie=Sortie+"\t ---------------------Temps : "+temps+"---------------------\n"+E+"\n";
		Sortie=Sortie+Chef.getListChef()+"\n";
		String Consigne;
		Meuble m;
		boolean licencier=false;
		boolean recruter=false;
		for(int i=0;i<listeConsigne.size();i++) {
			temps++;
			Consigne=listeConsigne.get(i);
			if(verifierConsigneRien(Consigne)) {
				executerConsigneRien(Consigne);
				executerListeAttente2(E);
			}
			if(verifierConsigneStockerLot(Consigne,E)) {
				Lot L;
				L=executerConsigneLot(Consigne,E);
				if(L!=null) {// on sait qu'il n'a pas pu etre stocke soit par manque de place ou personnel
					int  nor=E.nbOuvrierRStockerL(L);// doit renvoyer -1 si pas assez de place 
					if(nor==2 && recruter==false) {// on sait qu on a assez de place et on ajuste besoin de 2 ouvrier 
						Ouvrier.recruter("sami", "boustique", "sans");
						if(executerConsigneLot(Consigne,E)!=null) {
							System.out.println("erreur");// pas cense arriver
							throw new IllegalArgumentException();
						};
						recruter=true;
					}
					else {
						lotNS.add(L);
					}
				}
				executerListeAttente2(E);
			}
			if(verifierConsigneConstruireMeuble(Consigne,E)) {
				m=executerConsigneMeuble(Consigne);
				listeAttente.add(m);
				executerListeAttente2(E);
				
			}
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS2();// plus d'ouvrier dispo avec brico ==0
				executerListeAttente2(E);
			}
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS3etExecuterConstructionMeuble(E);
			}
			// ON PASSE AU PAS DE TEMPS SUIVANT 
			Chef.payer(E);// on paye nos ouvriers
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"--------------------- Consigne="+Consigne+"\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			Chef.soustraireDisponibilite();
			licencier=licencierUnautreOuvirerSansS3();
			if(licencier==false ) licencier=licencierUnautreOuvirerS3();
			recruter=false;
			licencier=false;
		}
		
		
		//ON A FINI DE LIRE TOUTES LES CONSIGNES, ON EXECUTE L'ATTENTE
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"LES CONSIGNES ONT ETE LUE, ON EXECUTE LA LISTE D'ATTENTE DES MEUBLES\n";
		while(listeAttente.size()!=0) {
			temps++;
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS2();// plus d'ouvrier dispo avec brico ==0
				executerListeAttente2(E);
			}
			if(recruter==false) {
				recruter=recruterUnautreOuvirerS3etExecuterConstructionMeuble(E);
			}
			executerListeAttente2(E);
			Chef.payer(E);
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"---------------------"+"\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			Chef.soustraireDisponibilite();
			licencier=licencierUnautreOuvirerSansS3();
			if(licencier==false) licencier=licencierUnautreOuvirerS3();
			recruter=false;
			licencier=false;
			
		}
		//ON A FINI LA LISTE D'ATTENTE ET LES CONSIGNES, ET ON FINI LA CONSTRUCTION DES MEUBLES TOUT EN SUPPRIMANT LES OUVRIERS QUI FONT RIEN
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"LES CONSIGNES ONT ETE LUES ET LA LISTE D'ATTENTE EST FINIE, ON ATTEND LA FIN DE CONSTRUCTION DES DERNIERS MEUBLES\n";
		while(Chef.tousDispoouPas()==false) {
			temps++;
			Chef.payer(E);// on paye nos ouvriers
			Sortie=Sortie+"\n\t---------------------Temps : "+temps+"---------------------\n";
			Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
			Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
			if(Chef.premierChefDisponibleLicencier()==false)Chef.premierOuvrierDisponibleLicencier();
			Chef.soustraireDisponibilite();
			
		}
		Chef.premierChefDisponibleLicencier();
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		Sortie=Sortie+"VOICI L'ETAT DE VOS DONNEES FINAL\n";
		Sortie=Sortie+"\t---------------------Temps : "+temps+"---------------------\n";
		Sortie=Sortie+E+"\n"+Chef.getListChef()+"\n"+"Les Meubles construits ou en cours de constrution : "+Meuble.ListeTousMeublesC+"\n";
		Sortie=Sortie+"Les meubles non construits (pas rentable ou pas assez de lots) : "+Meuble.ListeTousMeublesNC+"\nLots non stockes : "+lotNS+"\n";
		Sortie=Sortie+"\n----------------------------------------------------------------------------\n";
		listeConsigne.clear();
		
	}
	
	/** 
	 * Execute notre liste d'attente associe a la strategie 4
	 * @param E  entrepot sur lequel  s'effectue l'execution
	 * @see Interraction#executerS4(Entrepot E)
	 * */
	public static void executerListeAttente2(Entrepot E) {
		Meuble m1;
		for(int j=0;j<listeAttente.size();j++) {
			m1=listeAttente.get(j);
			if(m1.estRentable2(E)==false && m1.isDebute()==false) {
				Meuble.ListeTousMeublesNC.add(m1);
				listeAttente.remove(m1);
				j=j-1;
			}
			
			int oui=m1.construireUnMeuble(E);
			if(oui==1) {
				listeAttente.remove(m1);j=j-1;
				Meuble.ListeTousMeublesC.add(m1);
			}
			if(j!=-1 && listeAttente.get(j).getComposant().size()!=0){
				break;
			}//on finit d'apporter tous les lots requis pour le meuble j avant de passer a j+1, on est aussi sur que tout notre personnel est occupe
		}
	}
	
	
	
}