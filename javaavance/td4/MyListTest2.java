package td4;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class MyListTest2 {

	@Test
	public void sizeList() {
		MyList l = new MyList();
		l.add("toto");
		l.add("toto");
		assertEquals(2, l.size());
	}
	
	@Test
	public void sizeList2() {
		MyList l = new MyList();
		l.addLast("z");
		l.add("toto");
		l.add("toto");
		l.addLast("ez");
		
		assertEquals(4, l.size());
	}
	
	@Test
	public void printList() {
		MyList l = new MyList();
		l.add("toto");
		l.add("toto");
		l.add("titi");
		assertEquals("titi,toto,toto", l.toString());
	}
	
	@Test
	public void addList() {
		MyList l = new MyList();
		l.add("toto");
		l.add("toto");
		l.add("titi");
		l.addLast("bla");
		l.addLast("bli");
		l.add("doh");
		assertEquals("doh,titi,toto,toto,bla,bli", l.toString());
	}
	
	@Test
	public void addList2() {
		MyList l = new MyList();
		l.addLast("toto2");
		l.add("toto");
		l.add("titi");
		l.addLast("bla");
		l.addLast("bli");
		l.add("doh");
		assertEquals("doh,titi,toto,toto2,bla,bli", l.toString());
	}
	
	@Test
	public void getList1() {
		MyList l = new MyList();
		l.addLast("toto2");
		l.add("toto");
		l.add("titi");
		l.addLast("bla");
		l.addLast("bli");
		l.add("doh");
		assertEquals("doh", l.get(0));
	}
	
	@Test
	public void getList2() {
		MyList l = new MyList();
		l.addLast("toto2");
		l.add("toto");
		l.add("titi");
		l.addLast("bla");
		l.addLast("bli");
		l.add("doh");
		assertEquals("toto", l.get(2));
	}
	
	@Test
	public void sumLetterTest() {
		MyList l = new MyList();
		l.addLast("totoo");
		l.add("toto");
		l.add("titi");
		assertEquals(13, l.sumLetters());
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void getInvalid() {
		MyList l = new MyList();
		l.addLast("toto2");
		l.get(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getInvalid2() {
		MyList l = new MyList();
		l.addLast("toto2");
		l.get(10);
		
	}
	
	@Test(expected=NullPointerException.class)
	public void addNull() {
		MyList l = new MyList();
		l.addLast("totoo");
		l.add(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void addNull2() {
		MyList l = new MyList();
		l.addLast("totoo");
		l.addLast(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void sumNull() {
		MyList l = new MyList();
		l.add("totoo");
		l.add(null);
		assertEquals(5, l.sumLetters());
	}
	/*
	@Test(timeout=1000)
	public void toStringSpeed() {
		MyList l = new MyList();
		for(int i=0;i<100000;i++) {
			l.add(Integer.toString(i));
		}
		l.toString();		
	}*/
	
}
