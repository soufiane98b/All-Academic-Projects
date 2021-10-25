package td4;

public class MyList {
	Cell head;
	
	MyList(){
		head=null;
	}
	
	
	public boolean Contains(Object o){
		Cell tmp=head;
		while(tmp!=null){
			if(tmp.s.equals(o))return true;
			tmp=tmp.suivant;
		}
		return false;
	}
	
	
	
	void add(Object s) {
		Cell tmp=head;
		
		try {
			s.toString();
			
		}
		catch(NullPointerException exp) {
			throw new NullPointerException();
		}
		head= new Cell(s,tmp);
		
		
	}
	
	int size() {
		if(head==null)return 0;
		int cmpt=0;
		Cell tmp=head;
		while(tmp!=null){
			cmpt++;
			tmp=tmp.suivant;
		}		
		return cmpt;
		
		
	}
	
	@Override
	public String toString() {
		String chaine="";
		if(head==null)return chaine;
		Cell tmp=head;
		while(tmp!=null){
			chaine=chaine+tmp.s;
			tmp=tmp.suivant;
			if(tmp!=null)chaine=chaine+",";
		}		
		return chaine;
		
	}
	
	public void addLast(Object s) {
		try {
			s.toString();
			
		}
		catch(NullPointerException exp) {
			throw new NullPointerException();
		}
		if(head==null) {
			head= new Cell(s,null);
			return;
			}
		Cell tmp=head;
		while(tmp.suivant!=null) {
			tmp=tmp.suivant;
		}
		tmp.suivant= new Cell(s,null);
	}
	
	// On considère i=0 comme le head 
	public void add(Object s, int i) {
		if((this.size()-1)<i)return;
		if(i==(this.size()-1)) {
			this.addLast(s);
			return;
		}
		int cmpt=0;
		Cell tmp=head;
		while(cmpt!=i) {
			tmp=tmp.suivant;
			cmpt++;
		}
		Cell c= new Cell(s,tmp.suivant);
		tmp.suivant=c;
	}
	
	// On considère i=0 comme le head 
	public Object get(int index) throws  IllegalArgumentException  {
		int cmpt=0;
		Cell tmp=head;
		while(cmpt!=index) {
			try {
				tmp=tmp.suivant;
					
			}
			catch(NullPointerException exp) {
				throw new IllegalArgumentException();
			}
			cmpt++;
				
		}
		return tmp.s;
	}
		
	 public int sumLetters() {
		 int cmpt=0;
		 Cell tmp=head;
		 int cmpt2=0;
		 while(tmp!=null){
			 if(tmp.s instanceof Integer) cmpt2=(int) tmp.s;
			 if(tmp.s instanceof String) cmpt2=((String) tmp.s).length();
			 cmpt=cmpt+cmpt2;
			 tmp=tmp.suivant;
		}
		 return cmpt;
		 
	 }
	
	public static void main(String args[]) {
		MyList l = new MyList();
		l.addLast(3);
		System.out.println(l.Contains(3));
		
	}
	

}
