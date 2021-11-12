package td5;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import td4.MyList;

public class CarTest {
	
	

	@Test(expected=IllegalArgumentException.class)
	public void testNewCar() throws Exception {
		Car c = new Car("Audi",-3);
	}
	
	@Test
	public void test1() throws Exception {
		Car a = new Car("Audi",10000);
		Car b = new Car("BMW",9000);
		Car c = new Car("BMW",9000);
		Car d = a;
		assertEquals(false,a==b);
		assertEquals(false,b==c);
		assertEquals(true,a==d);
		assertEquals(false,a.equals(b));
		assertEquals(true,b.equals(c));
		assertEquals(true,a.equals(d));
	}
	
	@Test
	public void test2() throws Exception {
		Car a = new Car("Audi",10000);
		Car b = new Car("BMW",9000);
		Car c = new Car("BMW",9000);
		ArrayList<Car> list = new ArrayList<>();
		list.add(a);
		list.add(b);
		assertEquals(0,list.indexOf(a));
		assertEquals(1,list.indexOf(b));
		assertEquals(1,list.indexOf(c));
		assertEquals(true,b.equals(c));
		
		
	}
	@Test
	public void test3() throws Exception {
		Car b = new Car("BMW",9000);
		Car c = new Car("BMW",9000);
		HashSet<Car> set = new HashSet<Car>();
		set.add(b);
		assertEquals(true,set.contains(c));
		
		
	}
	

}
