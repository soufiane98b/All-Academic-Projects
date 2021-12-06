package TD8;

public class Max {
	
	public static int  myMax(int ... nb) {
		int max = nb[0];
		for(int i=1;i<nb.length;i++) {
			if(nb[i]>max)max=nb[i];
		}
		
		return max;
		
		
		
	}
	
	public static <T extends Comparable<T>> T myMax1(T ... nb) {
		try {
			T max = nb[0];
		
		for(int i=1;i<nb.length;i++) {
			if(nb[i].compareTo(max)>0)max =nb[i];
			}
		return max;
		}
		catch(ClassCastException e) {
			return null;
		}

		
	}
	
	public static void main(String [] args)  {
		System.out.println(myMax());
	}

}
