package td5;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GarageTest2 {
	@Test
	public void garageEquality() throws Exception {
		 Garage garage1 = new Garage();
		 Garage garage2 = new Garage();

		 Car car1 = new Car("BMW", 90000);
		 Car car2 = new Car("Lada",6500, 2);
		 Car car3 = new Car("Lada", 5500, 1);
		 Bike bike1 = new Bike("Scott");
		 Bike bike2 = new Bike("Merlin",50);
		 Bike bike3 = new Bike("Merlin");
		 
		 garage1.add(car1);
		 garage1.add(bike1);
		 garage1.add(car2);
		 garage1.add(bike2);
		 garage1.add(car3);        
		 garage1.add(bike3);
		 
		 garage2.add(bike1);
		 garage2.add(car1);    
		 garage2.add(car3); 
		 garage2.add(car2);
		 garage2.add(bike3);
		 garage2.add(bike2);       
		 
		 assertEquals(garage1, garage2);
	}

	

}
