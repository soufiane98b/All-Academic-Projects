package TD8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class E4 {
	
	public static <T> List<T> fusion(List<?> l1 ,List<?> l2 ){
		if(( l1.size() != l2.size() ) || l1.isEmpty() || l2.isEmpty() ) return null;
		List<T> fusion = new ArrayList<>();
		for(int i=0;i<l1.size();i++) {
			fusion.add(i, (T) l1.get(i));
			fusion.add(i+1, (T) l2.get(i));
		}
		return fusion ;
	}
	
	public static void main(String[] args) {
		List<String> l1= Arrays.asList("C", "rc");
		List<StringBuilder> l2= Arrays.asList(new StringBuilder("a ma"), new StringBuilder("he!"));
		List<? extends CharSequence> r1=fusion(l1,l2);
		List<?> r2=fusion(l1,l2);
		List<Integer> l3 = Arrays.asList(1,2);
		List<Integer> l4 = Arrays.asList(10,20);
		List<Integer> r3 = fusion(l3,l4);
		List<?> r4 = fusion(l1,l3);
		
	}

}
