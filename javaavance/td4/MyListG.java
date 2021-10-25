package td4;

public class MyListG<E> {
	CellG<E> head=null;
	
	
	//Exemple, après on fait de meme pour le reste 
	void add(E s) {
		CellG<E> tmp=head;
		head= new CellG(s,tmp);
		
		
	}
	public static void main(String args[]) {
		MyListG<String> l = new MyListG<String>();
		l.add("toto");
		System.out.println(l.head.s.toString());
		
	}

}
