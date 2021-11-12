package td5;

import static org.junit.Assert.*;

import org.junit.Test;

public class GarageTest {

	@Test
	public void testId()  {
		Garage g = new Garage();
		Garage g1 = new Garage();
		Garage g2 = new Garage();
		assertEquals(g.getId()+1,g1.getId());
		assertEquals(g.getId()+2,g2.getId());

	}
	
	@Test
	public void testValue() throws Exception {
		Car a = new Car("Audi",10000);
		Car b = new Car("BMW",9000);
		Garage g = new Garage();
		g.AjouterV(a);
		g.AjouterV(b);
		assertEquals(19000,g.ValeurG());
		
		
	}
	
	
	@Test
	public void testFirstCarByBrand() throws Exception {
		Car a = new Car("Audi",10000);
		Car b = new Car("Audi",9000);
		Garage g = new Garage();
		g.AjouterV(a);
		g.AjouterV(b);
		assertEquals(a,g.firstCarByBrand("Audi"));
		assertEquals(null,g.firstCarByBrand("BMW"));
		
		
	}
	@Test
	public void testRemove() throws Exception {
		Car a = new Car("Audi",10000);
		Car b = new Car("Audi",9000);
		Garage g = new Garage();
		g.AjouterV(a);
		g.AjouterV(b);
		g.remove(b);
		assertEquals(a,g.getL().get(0));
		
		
	}
	
	@Test
	public void testProtectionism() throws Exception {
		Car a = new Car("Audi",10000);
		Car c = new Car("BMW",9000);
		Bike bi = new Bike("Audi");
		Car b = new Car("Audi",10000);
		Garage g = new Garage();
		g.AjouterV(a);
		g.AjouterV(b);
		g.AjouterV(c);
		g.AjouterV(bi);
		g.protectionism("Audi");
		assertEquals("BMW",((Car) g.getL().get(0)).getBrand());
		assertEquals(1,g.getL().size());
		
	}
	
	


}
