package BIILIMO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

class OuvrierTest {
	
	ArrayList<Ouvrier> listOuvrier;
	Ouvrier o1, o2, o3, o4;

	@BeforeEach
	public void initOuvrier() {
		listOuvrier = new ArrayList<Ouvrier>();
		o1 = new Ouvrier("Malak", "Raphael", "Cuisine");
		o2 = new Ouvrier("Boustique", "Soufiane", "Cuisine");
		o3 = new Ouvrier("Malak", "Raphael", "Cuisine", o1.getId(), o1.getDispo(), o1.getMonChef());
		o4 = new Ouvrier("Gilbert", "Hugo", "Salon");
		listOuvrier.add(o1);
		listOuvrier.add(o2);
		listOuvrier.add(o3);
		listOuvrier.add(o4);
	}
	
	@AfterEach
	public void undefOuvrier() {
		listOuvrier = null;
		o1 = null; o2 = null; o3 = null;
		Chef.setCpt(0);
		Chef.setListChef(new ArrayList<Chef>());
		Ouvrier.setCpt(0);
	}
	
	@Test
	void equalsReturnsTrueIfEquals() {
		o4 = o1;
		assertFalse(o1.equals(o2));
		assertTrue(o1.equals(o3));
		assertTrue(o1.equals(o4));
	}
	
	@Test
	void equalsForArrayListReturnsTrueIfEquals() {
		ArrayList<Ouvrier> list = new ArrayList<Ouvrier>();
		list.add(o3);
		list.add(o2);
		list.add(o3);
		list.add(o4);
		assertTrue(listOuvrier.equals(list));
		list.set(3, new Ouvrier("Malik", "Raphael", "Cuisine"));
		assertFalse(listOuvrier.equals(list));
		//A partir d'ici, montre que 2 equipes ayant les mêmes ouvriers mais a des places differentes ne sont pas les mêmes
		list.set(3, null);
		listOuvrier.set(3, null);
		assertTrue(listOuvrier.equals(list));
		list.set(0, null);
		list.set(1, null);
		assertFalse(listOuvrier.equals(list));
	}
	
	@Test
	void trouverUneEquipePourNouveau_AttribueEquipePourNouvelOuvrier_RenvoieTrueSiEquipeAttribue() {
		ArrayList<Chef> listChef = Chef.getListChef();
		listChef.add(new Chef("Malak", "Raphael", "Brico"));
		assertTrue(Ouvrier.trouverUneEquipePourNouveau("Boustique", "Soufiane", "Cuisine"));
		ArrayList<Chef> resultListChef = new ArrayList<Chef>();
		resultListChef.add(new Chef("Malak", "Raphael", "Brico", listChef.get(0).getId(), listChef.get(0).getDispo()));
		resultListChef.get(0).equipe.set(0, new Ouvrier("Boustique", "Soufiane", "Cuisine", 1, 0, listChef.get(0)));
		assertEquals(listChef, resultListChef);
		assertTrue(Ouvrier.trouverUneEquipePourNouveau("Gilbert", "Hugo", "Salon"));
		assertTrue(Ouvrier.trouverUneEquipePourNouveau("Nourry", "Charles", "salle de bain"));
		assertTrue(Ouvrier.trouverUneEquipePourNouveau("Afgoustidis", "Alexandre", "WC"));
		//On ne peut plus rajouter d'ouvrier quand toutes les equipes sont pleines
		assertFalse(Ouvrier.trouverUneEquipePourNouveau("IMPO", "SSIBLE", "chambre"));
	}
	
	@Test
	void trouverUneEquipePourAncien_AttribueEquipePourAncienOuvrier_RenvoieTrueSiEquipeAttribue() {
		ArrayList<Chef> listChef = Chef.getListChef();
		listChef.add(new Chef("Malak", "Raphael", "Brico"));
		listChef.add(new Chef("Gilbert", "Hugo", "Brico"));
		Ouvrier.trouverUneEquipePourNouveau("Boustique", "Soufiane", "Cuisine");
		assertTrue(Ouvrier.trouverUneEquipePourAncien(listChef.get(0).getEquipe().get(0)));
		assertEquals(listChef.get(0).getEquipe().get(0), listChef.get(1).getEquipe().get(0));
	}
	
	@Test
	void recruterAjouteOuvrierAListe_RenvoieTrueSiOuvrierAjoute() {
		//On ne peut pas recruter d'ouvrier s'il n'y a pas de chef d'equipe
		assertFalse(Ouvrier.recruter("Malak", "Raphael", "Salon"));
		ArrayList<Chef> listChef = Chef.getListChef();
		listChef.add(new Chef("Malak", "Raphael", "Brico"));
		assertTrue(Ouvrier.recruter("Boustique", "Soufiane", "Cuisine"));
		ArrayList<Chef> resultListChef = new ArrayList<Chef>();
		resultListChef.add(new Chef("Malak", "Raphael","Brico", 1, 0));
		resultListChef.get(0).equipe.set(0, new Ouvrier("Boustique", "Soufiane", "Cuisine", 1, 0, resultListChef.get(0)));
		//Montre qu'il est ajoute a la première place disponible
		assertEquals(listChef, resultListChef);
	}
	
	@Test
	void placeDansEquipe_RetournLeNombreDePlaceNullDansUneEquipe() {
		assertEquals(0, Ouvrier.placeDansEquipe(listOuvrier));
		listOuvrier.set(3, null);
		assertEquals(1, Ouvrier.placeDansEquipe(listOuvrier));
	}
	
	@Test
	void licencierSupprimeUnOuvrierDeSonEquipe_RenvoieTrueSiOuvrierLicencie() {
		assertFalse(o2.licencier());
		ArrayList<Chef> listChef = Chef.getListChef();
		listChef.add(new Chef("Malak", "Raphael", "Brico"));
		listChef.get(0).equipe.set(0, o1);
		listChef.get(0).equipe.set(1, o2);
		listChef.get(0).equipe.set(2, o3);
		listChef.get(0).equipe.set(3, o4);
		o2.setMonChef(listChef.get(0));
		o3.setMonChef(listChef.get(0));
		o4.setMonChef(listChef.get(0));
		assertTrue(o2.licencier());
		ArrayList<Chef> resultListChef = new ArrayList<Chef>();
		resultListChef.add(new Chef("Malak", "Raphael", "Brico", 1, 0));
		resultListChef.get(0).equipe.set(0, o1);
		resultListChef.get(0).equipe.set(2, o3);
		resultListChef.get(0).equipe.set(3, o4);
		assertEquals(resultListChef, listChef);
	}
	
	@Test
	void estDispoPourConstruireRetourneLePremierOuvrierDisponiblePourUneCertaineSpecialite_NullSinon() {
		assertNull(Ouvrier.estDispoPourConstruire("Cuisine"));
		ArrayList<Chef> listChef = Chef.getListChef();
		Chef.recruter("Malak", "Raphael", "Brico");
		Ouvrier.recruter("Boustique", "Soufiane", "Cuisine");
		Ouvrier.recruter("Gilbert", "Hugo", "Salon");
		Ouvrier.recruter("Nourry", "Charles", "Salon");
		//Selectionnera le premier sur la liste, ici Hugo Gilbert
		assertEquals(Ouvrier.estDispoPourConstruire("salon"), listChef.get(0).equipe.get(1));
		listChef.get(0).equipe.get(0).licencier();
		assertNull(Ouvrier.estDispoPourConstruire("Cuisine"));
	}
	
	@Test
	void estDispoPourEntrepotRetourneLePremierOuvrierDisponible_NullSinon() {
		assertNull(Ouvrier.estDispoPourEntrepot());
		ArrayList<Chef> listChef = Chef.getListChef();
		Chef.recruter("Malak", "Raphael", "Brico");
		Ouvrier.recruter("Boustique", "Soufiane", "Cuisine");
		//Selectionnera le premier sur la liste, ici Hugo Gilbert
		assertEquals(Ouvrier.estDispoPourEntrepot(), listChef.get(0).equipe.get(0));
		listChef.get(0).equipe.get(0).setDispo(1);
		assertNull(Ouvrier.estDispoPourEntrepot());
	}
	
	@Test
	void nombreOuvriersEtChefStockDispo_RetourneBooleanAssezDePersonnelPourStock() {
		assertFalse(Ouvrier.nombreOuvriersEtChefStockDispo(1));
		ArrayList<Chef> listChef = Chef.getListChef();
		Chef.recruter("Malak", "Raphael", "Brico");
		Ouvrier.recruter("Boustique", "Soufiane", "Cuisine");
		Ouvrier.recruter("Gilbert", "Hugo", "Salon");
		Ouvrier.recruter("Nourry", "Charles", "Salon");
		assertTrue(Ouvrier.nombreOuvriersEtChefStockDispo(3));
		assertFalse(Ouvrier.nombreOuvriersEtChefStockDispo(4));
		listChef.get(0).equipe.get(0).licencier();
		assertFalse(Ouvrier.nombreOuvriersEtChefStockDispo(3));
		listChef.add(new Chef("Potter", "Harry", "Stock"));
		assertTrue(Ouvrier.nombreOuvriersEtChefStockDispo(3));
	}
	
	@Test
	void listOuvrierRetourneListeOuvrierDispoAvecSpecialite_AugmenteLeurDispo_LaTailleNeDepassePasLePremierParametre() {
		assertEquals(Ouvrier.listOuvrier(1, "", 1).size(), 0);
		ArrayList<Chef> listChef = Chef.getListChef();
		Chef.recruter("Malak", "Raphael", "Brico");
		Ouvrier.recruter("Boustique", "Soufiane", "Cuisine");
		Ouvrier.recruter("Gilbert", "Hugo", "Salon");
		Ouvrier.recruter("Nourry", "Charles", "Salon");
		assertEquals(Ouvrier.listOuvrier(1, "salon", 0).get(0), listChef.get(0).equipe.get(1));
		assertEquals(Ouvrier.listOuvrier(3, "cuisine", 2).get(0), listChef.get(0).equipe.get(0));
		//L'ouvirer Soufiane Boustique est maintenant occupe
		assertEquals(Ouvrier.listOuvrier(3, "cuisine", 2).size(), 0);
	}
}
