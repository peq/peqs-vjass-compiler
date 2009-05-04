package main;

public class LingualNumber{
	int i;
	public LingualNumber(int j) {
		i = j;
	}
	
	public String toString() {
		switch (i) {
			case 0 : 
				return "zero";
			case 1 :
				return "one";
			case 2:
				return "two";
			case 3: 
				return "three";
			default:
				return Integer.toString(i);
			
		}
		
	}

}
