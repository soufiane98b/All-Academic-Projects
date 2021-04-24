package BIILIMO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

class ChefTest{
	
	ArrayList<Chef> listChef;
	Chef c1;
	Chef c2;
	Chef c3;
	Chef c4;
	
	@BeforeEach
	public void initListeChef() {
		listChef = new ArrayList<Chef>();
		c1 = new Chef("Malak", "Raphael", "Brico");
		c2 = new Chef("Boustique", "Soufiane", "Brico");
		c3 = new Chef("Malak", "Raphael", "Brico", c1.getId(), c1.getDispo());
		c4 = new Chef("Gilbert", "Hugo", "Stock");
		listChef.add(c1);
		listChef.add(c2);
		listChef.add(c3);
		listChef.add(c4);
	}
	
	@AfterEach
	public void undefListeChef() {
		listChef = null;
		c1 = null; c2 = null; c3 = null;
		Chef.setCpt(0);
		Chef.setListChef(new ArrayList<Chef>());
		Ouvrier.setCpt(0);
	}
	
	@Test
	void equalsReturnsTrueIfEquals() {
		c4 = c1;
		assertFalse(c1.equals(c2));
		assertTrue(c1.equals(c3));
		assertTrue(c1.equals(c4));
	}
	
	@Test 
	public void nombreDePlaceDansEquipesReturnsNumberOfNotEmptySlotOfWorkers() {
		listChef = Chef.getListChef();
		listChef.add(c1);
		listChef.add(c2);
		listChef.add(c3);
		listChef.add(c4);
		assertEquals(16, Chef.nombreDePlaceDansEquipes());
		listChef.get(1).equipe.set(0, new Ouvrier("Michel", "Dupont", "Cuisine", listChef.get(0)));
		listChef.get(1).equipe.set(1, new Ouvrier("Michelle", "Duponte", "Cuisine", listChef.get(0)));
		assertEquals(14, Chef.nombreDePlaceDansEquipes());
	}
	
	@Test
	void licencierSupprimeUnChefDeLaListeEtRedirigeSonEquipe_RenvoieTrueSiLeChefEstBienLicencie() {
		listChef = Chef.getListChef();
		listChef.add(c1);
		listChef.add(c2);
		listChef.add(c3);
		listChef.add(c4);
		listChef.get(1).equipe.set(0, new Ouvrier("Michel", "Dupont", "Cuisine", listChef.get(0)));
		listChef.get(1).equipe.set(1, new Ouvrier("Michelle", "Duponte", "Cuisine", listChef.get(0)));
		assertTrue(c2.licencier());
		//Ici, c1 a recuperer les ouvriers de c2
		ArrayList<Chef> resultListChef = new ArrayList<Chef>();
		resultListChef.add(c1);
		resultListChef.add(c3);
		resultListChef.add(c4);
		assertEquals(listChef, resultListChef);
		//Cas où on ne peut pas licencier car redirection impossible de l'equipe des ouvriers
		Chef.setListChef(new ArrayList<Chef>());
		listChef = Chef.getListChef();
		listChef.add(c4);
		listChef.get(0).equipe.set(0, new Ouvrier("Michel", "Dupont", "Cuisine", listChef.get(0)));
		assertFalse(c4.licencier());
	}

	@Test
	void recruterAjouteChefAListe_RenvoieTrueSiChefAjoute() {
		assertTrue(Chef.recruter("Malak", "Raphael", "Brico"));
		ArrayList<Chef> resultListChef = new ArrayList<Chef>();
		resultListChef.add(new Chef("Malak", "Raphael", "Brico", Chef.getListChef().get(0).getId(), Chef.getListChef().get(0).getDispo()));
		assertEquals(Chef.getListChef(), resultListChef);
		//La suite montre que le chef d'equipe recrute est place a la fin de la liste de chefs
		assertTrue(Chef.recruter("Boustique", "Soufiane", "Stock"));
		resultListChef.add(0, new Chef("Boustique", "Soufiane", "Stock", Chef.getListChef().get(1).getId(), Chef.getListChef().get(1).getDispo()));
		assertNotEquals(Chef.getListChef(), resultListChef);
	}
	
	@Test
	void estDispoRetourneLePremierChefDisponiblePourUneCertaineSpecialite_NullSinon() {
		//La liste est vide donc personne n'est disponible
		assertNull(Chef.estDispo("Brico"));
		assertNull(Chef.estDispo("Stock"));
		//En ajoutant un chef Brico
		Chef.recruter("Malak", "Raphael", "Brico");
		assertNull(Chef.estDispo("Stock"));
		assertEquals(Chef.estDispo("Brico"), new Chef("Malak", "Raphael", "Brico", Chef.getListChef().get(0).getId(), Chef.getListChef().get(0).getDispo()));
	}
	
	@Test
	void soustraireDisponibilite_EnleveUnAToutLePersonnelQuiADispoNonNul() {
		listChef = Chef.getListChef();
		Chef.recruter("Malak", "Raphael", "Brico");
		Ouvrier.recruter("Boustique", "Soufiane", "Cuisine");
		Ouvrier.recruter("Gilbert", "Hugo", "Salon");
		Ouvrier.recruter("Nourry", "Charles", "Salon");
		Chef.soustraireDisponibilite();
		assertTrue(listChef.get(0).getDispo()==0 && listChef.get(0).equipe.get(0).getDispo() == 0 && listChef.get(0).equipe.get(1).getDispo() == 0 && listChef.get(0).equipe.get(2).getDispo() == 0);
		listChef.get(0).equipe.get(0).setDispo(2);
		listChef.get(0).equipe.get(1).setDispo(3);
		Chef.soustraireDisponibilite();
		assertTrue(listChef.get(0).getDispo()==0 && listChef.get(0).equipe.get(0).getDispo() == 1 && listChef.get(0).equipe.get(1).getDispo() == 2 && listChef.get(0).equipe.get(2).getDispo() == 0);
	}
	
	@Test
	void listChefDispoRetourneListeChefDispoAvecSpecialite_AugmenteLeurDispo_LaTailleNeDepassePasLePremierParametre() {
		assertEquals(Chef.listChefDispo(1, "Stock", 1).size(), 0);
		assertEquals(Chef.listChefDispo(1, "Brico", 1).size(), 0);
		listChef = Chef.getListChef();
		listChef.add(c1);
		listChef.add(c2);
		listChef.add(c3);
		listChef.add(c4);
		assertEquals(Chef.listChefDispo(1, "Stock", 1).get(0), listChef.get(3));
		//Hugo Gilbert est maintenant occupe
		assertEquals(Chef.listChefDispo(1, "Stock", 1).size(), 0);
	}
	
	@Test
	void payer10eurosChefEt5eurosOuvrier() {
		listChef = Chef.getListChef();
		listChef.add(c1);
		listChef.add(c2);
		listChef.add(c4);
		Ouvrier.recruter("Nourry", "Charles", "Salon");
		ArrayList<String> types = new ArrayList<String>();
		 types.add("vis");
		 types.add("planches");
		 types.add("boulons");
		 types.add("fer");
		 types.add("poignets");
		Entrepot E = new Entrepot(5, 5, 100, types);
		Chef.payer(E);
		assertTrue(E.getTresorie() == 100 - 3*10 - 5 && listChef.get(0).equipe.get(0).getSalaire() == 5 && listChef.get(0).getSalaire() == 10 && listChef.get(1).getSalaire() == 10 && listChef.get(2).getSalaire() == 10);
	}
	
	@Test
	void tousDispoouPasRetourneTrueSiPersonnelEntierementDispo_FalseSinon() {
		listChef = Chef.getListChef();
		listChef.add(c1);
		listChef.add(c2);
		listChef.add(c4);
		Ouvrier.recruter("Nourry", "Charles", "Salon");
		assertTrue(Chef.tousDispoouPas());
		listChef.get(0).equipe.get(0).setDispo(2);
		assertFalse(Chef.tousDispoouPas());
	}
	
	@Test
	void premierOuvrierDisponibleLicencier() {
		listChef = Chef.getListChef();
		listChef.add(c1);
		Ouvrier.recruter("Boustique", "Soufiane", "Cuisine");
		Ouvrier.recruter("Nourry", "Charles", "Salon");
		Ouvrier.recruter("Gilbert", "Hugo", "Chambre");
		Chef.premierOuvrierDisponibleLicencier();
		assertNull(listChef.get(0).equipe.get(0));
		Chef.premierOuvrierDisponibleLicencier();
		assertNull(listChef.get(0).equipe.get(1));
	}
	
	@Test
	void premierChefDisponibleLicencierEssaieDeLicencierLePremierChefDispoAvecEquipeVideRenvoieTrueSiIlEnLicencieUn() {
		listChef = Chef.getListChef();
		listChef.add(c1);
		listChef.add(c2);
		Ouvrier.recruter("Nourry", "Charles", "Salon");
		assertTrue(Chef.premierChefDisponibleLicencier());
		//Le premier chef etait disponible donc il a redirige l'ouvrier de son equipe dans celle du 2ème chef
		assertTrue(listChef.size() == 1 && listChef.get(0).getNom() == "Boustique" && listChef.get(0).equipe.get(0) != null);
	}
}