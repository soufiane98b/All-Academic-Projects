package TD6;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Assert;

public class Panel implements Iterator<Integer>, Iterable<Integer>,List<Integer>{
	private static int mini;
	private static int nextV;
	private static int max;
	
	public static  Panel panel1(int min , int maxi ) {
		
		if (min > maxi) {
		      throw new IllegalArgumentException("min must be <= max");
		    }
		mini = min;
		nextV = min;
		max = maxi;
		return new Panel();
		
		
	}
	public static Panel panel1_2(int min , int maxi ) {
		if (min > maxi) {
		      throw new IllegalArgumentException("min must be <= max");
		    }
		mini = min;
		nextV = min;
		max = maxi;
	    
	    Iterator<Integer> iterator = new Iterator<Integer>() {

	      @Override
	      public boolean hasNext() {
	    	  return nextV <= max;
	      }

	      @Override
	      public Integer next() {
	    	  if (!hasNext()) {
			      throw new NoSuchElementException();
			    }
			return Integer.valueOf(nextV++);
	      }
	    }; 

	    return new Panel();
	  }
	
	
	public static  Panel panel2(int min , int maxi ) {
		
		if (min > maxi) {
		      throw new IllegalArgumentException("min must be <= max");
		    }
		mini = min;
		nextV = min;
		max = maxi;
		return (Panel) new Panel().iterator();
		
		
	}
	
	public static List<Integer> panel(int min , int maxi) {
		if (min > maxi) {
		      throw new IllegalArgumentException("min must be <= max");
		    }
		mini = min;
		nextV = min;
		max = maxi;
		return (List<Integer>) new Panel();
		
		
		
	}
	

	@Override
	public boolean hasNext() {
		return nextV <= max;
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
		      throw new NoSuchElementException();
		    }
		return Integer.valueOf(nextV++);
	}

	public int getMax() {
		return max;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		
		return this;
	}
		
	@Override
	public Integer get(int index) {
		return mini+index;
	}
	
	
	public int size() {
		return max-mini+1;
	
	}
	
	//---------------------------------------------------------------------------------------------
		
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean add(Integer e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(int index, Collection<? extends Integer> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Integer set(int index, Integer element) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void add(int index, Integer element) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Integer remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ListIterator<Integer> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Integer> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ListIterator<Integer> listIterator() {
		
		
		return  this.listIterator();
	}
	
	

}
