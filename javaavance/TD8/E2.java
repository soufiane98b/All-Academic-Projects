package TD8;

import java.util.Arrays;
import java.util.List;

public class E2 {
	// au début ne marche pas car n'est pas castée
	
	private static <T> void print(List<T> list) {
		for(T o:list)
		System.out.println(o);
		}
	
	private static <T extends CharSequence> void longueur(List<T> list) {
		for(T o:list) {
		System.out.println(o.length());
		}
	}
	
	
	public static void main(String[] args) {
		List<String> list=Arrays.asList("foo", "toto");
		longueur(list);
	}
	


}
