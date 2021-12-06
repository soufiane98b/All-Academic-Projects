package TD8;

import java.util.Arrays;
import java.util.List;

public class E5 {
	
	
	
	public static <T> void echange(List<T> l , int a , int b) {
		
		T tmp = l.get(a);
		l.set(a, l.get(b));
		l.set(b, tmp);
		
		
	}
	
	public static void main(String[] args) {
		
		List<String> l1= Arrays.asList("C", "rc");
		echange(l1,0,1);
		
		System.out.println(l1);
	}

}
