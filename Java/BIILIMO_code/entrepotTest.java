package BIILIMO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class entrepotTest {

	 static Entrepot E;
	 static Lot l1 ;
	 static Lot l2 ;
	 static Lot l3 ;
	 static Lot l4 ;
	 
	 @BeforeEach
	 void setInitest() {
		 ArrayList<String> types = new ArrayList<String>();
		 types.add("vis");
		 types.add("planches");
		 types.add("boulons");
		 types.add("fer");
		 types.add("poignets");
		 E = new Entrepot(3,10,1000,types);
		 l1 = new Lot(2,"vis" , 5 , 10);
		 l2 = new Lot(1,"vis" , 5 , 5);
		 l3 = new Lot(1,"boulons" , 5 , 5);
		 l4 = new Lot(2,"fer" , 5 , 5);
		 
	 }
	 @AfterEach
	 void setTest() {
		 E=null;
		 l1=null;
		 l2=null;
		 l3=null;
		 l4=null;
	 }
	 
	 /** On teste l'ajout des lots et la modification du volume disponible dans le bloc et la rangee
	  * Ne rajoute pas le lot si volume trop grand ou pas assez de place  
	  * */
	 @Test
	 void TestAjoutL () {
		 Lot l5 = new Lot(10,"vis" , 5 , 10);
		 boolean b=E.ajoutL(l5);
		 assertEquals(b,false);// volume trop grand
		 //Test que les lots ont bien ete range a leur place
		 b=E.ajoutL(l1);
		 assertEquals(b,true);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getTLot().get(0),l1);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getTLot().get(1),l1);
		 assertEquals(E.getTrange().get(0).getV_dispo(),8);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getV_dispo(),3);
		 
		 b=E.ajoutL(l2);
		 assertEquals(b,true);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getTLot().get(2),l2);
		 assertEquals(E.getTrange().get(0).getV_dispo(),7);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getV_dispo(),2);
		 
		 b=E.ajoutL(l3);
		 assertEquals(b,true);
		 assertEquals(E.getTrange().get(2).getBloc().get(0).getTLot().get(0),l3);
		 assertEquals(E.getTrange().get(2).getV_dispo(),9);
		 assertEquals(E.getTrange().get(2).getBloc().get(0).getV_dispo(),9);
		 
		 b=E.ajoutL(l4);
		 assertEquals(b,true);
		 assertEquals(E.getTrange().get(0).getBloc().get(1).getTLot().get(0),l4);
		 assertEquals(E.getTrange().get(0).getBloc().get(1).getTLot().get(1),l4);
		 assertEquals(E.getTrange().get(0).getV_dispo(),5);
		 assertEquals(E.getTrange().get(0).getBloc().get(1).getV_dispo(),3);
		 
		 assertEquals(E.ajoutL(l5),false);// pas assez de place 
		 
	 }
	 /** On teste que le nombre d'ouvriers requis pour ajouter les lots
	  * cas = 1 : cas de base , on a besoin que d'un personnel 
	  * cas = 2 : on a besoin que de deux personnel car on eleve un lot pour faire plus de place
	  * cas = (-1) : pas assez de place pour stocker le lot ou volume trop grand
	  * */
	 @Test
	 void TestnbOuvrierRStockerL(){
		 Lot l5 = new Lot(10,"vis" , 5 , 10);
		 assertEquals(E.nbOuvrierRStockerL(l5),-1);
		 E.ajoutL(l5);
		 assertEquals(E.nbOuvrierRStockerL(l1),1);
		 E.ajoutL(new Lot(2,"vis" , 5 , 10));
		 E.ajoutL(new Lot(2,"vis" , 5 , 10));
		 assertEquals(E.nbOuvrierRStockerL(l1),2);
		 E.ajoutL(new Lot(1,"vis" , 5 , 10));
		 assertEquals(E.nbOuvrierRStockerL(l1),-1);
		 
	 }
	 /** On teste qu'on peut rajouter un lot meme si pas assez de volume dispo, en enlevant ou en coupant le dernier lot pour faire de la place, 
	  * si pas assez de place meme en enlevant le dernier lot ( ou vdipso==0) doit renvoyer false
	  * teste aussi que volume dispo est mis a jour dans bloc et rangee 
	  * 
	  * */
	 @Test
	 void TestremplacerFinL() {
		 
		 E.ajoutL(new Lot(2,"vis" , 5 , 10));
		 E.ajoutL(new Lot(2,"vis" , 5 , 10));
		 //System.out.println(E);
		 assertEquals(E.remplacerFinL(l1),true);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getTLot().get(4),l1);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getV_dispo(),0);
		 E.retirerL(l1);
		 assertEquals(E.remplacerFinL(new Lot(4,"vis" , 5 , 10)),false);
	 }
	 /**
	  * Test pour la meme fonction precedente, juste verifie qu'on peut bien couper le lot pour faire de la place
	  * */
	 @Test
	 void Test2remplacerFinL() {
		 E.ajoutL(new Lot(4,"vis" , 5 , 10));
		 assertEquals(E.nbOuvrierRStockerL(new Lot(2,"vis" , 5 , 10)),2);
		 E.remplacerFinL(new Lot(2,"vis" , 5 , 10));
		 
	 }
	 
	 /**
	  * Test qu'on retire bien un lot et qu'il renvoi bien le bon prix total du lot, tout en mettant a jour vdispo
	  * */
	 @Test
	 void TestretirerL() {
		 E.ajoutL(l1);
		 assertEquals(E.retirerL(l1),(2*10));
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getTLot().get(0),null);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getV_dispo(),5);
		 assertEquals(E.getTrange().get(0).getV_dispo(),10);
	 }
	 
	 /**
	  * Teste le nombre de personnel requis pour retiner n lots, teste aussi le cas avec pas assez de lots
	  */
	 @Test
	 void TestnbOuvrierR() {
		 assertEquals(E.nbOuvrierR("vis",1),-1);
		 E.ajoutL(l1);
		 E.ajoutL(l2);
		 assertEquals(E.nbOuvrierR("vis",4),-1);
		 assertEquals(E.nbOuvrierR("vis",3),2);
		 assertEquals(E.nbOuvrierR("vis",2),2);
		 assertEquals(E.nbOuvrierR("vis",1),1);
		
	 }
	 
	 /**
	  * Teste le prix de construction pour un volume donne pour un certain type de le lot, si pas assez de lot si retourne -1
	  */
	 @Test
	 void TestprixConstructionV() {
		 assertEquals(E.prixConstructionV("vis",1),-1);
		 E.ajoutL(l1);
		 E.ajoutL(l2);
		 assertEquals(E.prixConstructionV("vis",1),5);
		 assertEquals(E.prixConstructionV("vis",2),15);
		 assertEquals(E.prixConstructionV("vis",3),25);
		 assertEquals(E.prixConstructionV("vis",4),-1);

	 }
	 
	 /**
	  * Teste qu on retire bien le volume de lots a enlever suivant le nombre de types qu'on veut enlever, teste qu'on renvoi bien aussi le pon prix 
	  * renvoi -1 si pas assez de lots
	  */
	 
	 @Test
	 void TestretirerT() {
		 assertEquals(E.retirerT("vis",1),-1);
		 E.ajoutL(l1);
		 E.ajoutL(l2);
		 assertEquals(E.retirerT("vis",2),15);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getTLot().get(0),l1);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getTLot().get(1),null);
		 
	 }
	 
	 /**
	  * Teste qu on retire bien le n fois les lots a enlever suivant le nombre de personnel dont on dispose teste qu'on renvoi bien aussi le pon prix
	  */
	 @Test
	 void TestretirerP() {
		 assertEquals(E.retirerP("vis",1),-1);
		 E.ajoutL(l1);
		 E.ajoutL(l2);
		 assertEquals(E.retirerP("vis",2),25);
		 assertEquals(E.getTrange().get(0).getBloc().get(0).getTLot().get(0),null);
		 
	 }
	 
	 /**
	  * Teste qu'on renvoi bien le volume Total contenu dans les n derniers Lot sinon renvoi -1 si pas de lots possible a compter
	  */
	 @Test
	 void TestcompterP() {
		 assertEquals(E.compterP("vis",1),-1);
		 E.ajoutL(l1);
		 E.ajoutL(l2);
		 assertEquals(E.compterP("vis",2),3);
		 assertEquals(E.compterP("vis",1),1);
		 System.out.println(E);
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

	 
}
