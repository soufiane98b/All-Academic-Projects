package td5;

public class Bike extends Vehicule {
	private final String brand;
	private int value=100;
	private int discount;
	
	
	@Override
	public String getBrand() {
		return brand;
	}

	public Bike(String b) {
		brand=b;
	}
	public Bike(String b, int dis) {
		discount = dis;
		brand=b;
		value= value - discount;
		
	}
	@Override
	public String toString() {
		return "La marque est "+brand+" et la valeur est "+value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
