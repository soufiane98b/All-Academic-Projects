package TD8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class E3 {
	
	public static <T extends CharSequence> List<Integer> listLength(List<T> list) {
		ArrayList<Integer> length=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++) {
			CharSequence seq=(CharSequence)list.get(i);
			length.add(seq.length());
			}
		return length;
	}
	
	public static List<Integer> listLength1(List<? extends CharSequence > list) {
		ArrayList<Integer> length=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++) {
			CharSequence seq=(CharSequence)list.get(i);
			length.add(seq.length());
			}
		return length;
	}
	
	
	
	
	public static void main(String[] args) {
		
	}

}
