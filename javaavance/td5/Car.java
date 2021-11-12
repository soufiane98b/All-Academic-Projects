package td5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import fr.dauphine.javaavance.td1.Circle;
import fr.dauphine.javaavance.td1.Point;

public class Car extends Vehicule {
	private final String brand;
	private final Long value;
	private int lvl_old;
	private Long discount;
	
	
	@Override
	public String getBrand() {
		return brand;
	}
	@Override
	public long getValue() {
		return value;
	}
	
	Car(String b, Long v) throws Exception{
		brand=b;
		if ( v < 0 ) {
			  throw new IllegalArgumentException("Valeur Négative non accepté");
			}
		value=(long) v;

			
	}
	Car(String b, int v) throws Exception{
		brand=b;
		if ( v < 0 ) {
			  throw new IllegalArgumentException("Valeur Négative non accepté");
			}


		value=(long) v;
	}
	

	Car(String b, Long v, int old) throws Exception{
		brand=b;
		if ( v < 0 ) {
			  throw new IllegalArgumentException("Valeur Négative non accepté");
			}
		lvl_old=old;
		
		value=v-old*1000;
	}
	Car(String b, int v, int old) throws Exception{
		brand=b;
		if ( v < 0 ) {
			  throw new IllegalArgumentException("Valeur Négative non accepté");
			}
		lvl_old=old;
		
		value=(long) (v-old*1000);
	}
	
	Car(String b, Long v, int old,Long dis) throws Exception{
		brand=b;
		if ( v < 0 ) {
			  throw new IllegalArgumentException("Valeur Négative non accepté");
			}
		lvl_old=old;
		discount=dis;
		if(!discount.equals(null)) value = discount;
		else {
			value=v-old*1000;
		}
		
	

			
	}
	
	@Override
	public String toString() {
		return "La marque est "+brand+" et la valeur est "+value;
	}

	public int getLvl_old() {
		return lvl_old;
	}

	public void setLvl_old(int lvl_old) {
		this.lvl_old = lvl_old;
		
	}
	@Override
	public boolean equals(Object c) {
		if (!(c instanceof Car)) {
		    return false;
		  }
		return ((Car) c).getBrand().equals(brand) && ((Car) c).getValue()==value;
	}
	
	@Override
	public int hashCode() {
        return getBrand().hashCode();
    }
	
	/*HashSet Checks for two methods hashCode() and equals() before adding any Object. 
	 * First it checks for the method hashCode(),if it returns the hashcode which is same with any of the object in Set,
	 *  then it checks for the equals method for that object,which internally compares the references for both objects 
	 *  i.e this.obj1==obj.If these are the same references in that case it returns true means it is a duplicate value. 
	 *  We can add duplicate non-final objects by overriding HashCode and equals method. In HashCode() you can return 
	 *  same hashcode in case of same parameters.
	*/
	
	
	public static void main(String [] args) throws Exception {
		
		/*
		try {
			Car a = new Car("Audi",10000);
			Car b = new Car("BMW",9000);
			Car c = new Car("BMW",9000);
			Car d = a;
			ArrayList<Car> list = new ArrayList<>();
			list.add(a);
			list.add(b);
			HashSet<Car> set = new HashSet<Car>();
			set.add(b);
			System.out.println();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
		
	}
	
	
	
	

}
