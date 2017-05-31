package Prijemni;

public class Global {
	private  int ind;
	private int ps;
	private int slobodna_baza;
	
	
	public Global(){
		ind = 0;
		ps = 0;
		slobodna_baza = 0;
		
	}
	
	public void IndFalse(){
		ind= 0;
	}
	
	public void IndTrue(){
		ind = 1;
	}
	
	public int getInd(){
		return ind;
	}
	
	public void False(){
		ps= 0;
	}
	
	public void True(){
		ps= 1;
	}
	
	public int vrednost(){
		return ps;
	}
	
	public void bazaFalse(){
		slobodna_baza = 0;
	}
	
	public void bazaTrue(){
		slobodna_baza = 1;
	}
	
	public int bazaVrednost(){
		return slobodna_baza;
	}
	

	
}

 
