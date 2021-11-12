package td5;

import java.util.Comparator;

public abstract class Vehicule {
	
	public abstract String getBrand() ;
	public abstract long getValue();
	
	public static Comparator<Vehicule> ComparatorName = new Comparator<Vehicule> () {
		
		@Override
		public int compare(Vehicule v1 , Vehicule v2) {
			return v1.getBrand().compareTo(v2.getBrand());
		}
	};
	
	public static Comparator<Vehicule> ComparatorValue = new Comparator<Vehicule> () {
		
		@Override
		public int compare(Vehicule v1 , Vehicule v2) {
			return   (int) (v1.getValue()-v2.getValue()) ;
		}
	};
	
}
