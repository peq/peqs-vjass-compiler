package main;


public class LibraryLink implements WithPosition {

	private Library lib;
	
	String name;
	Position pos;
	
	
	
	public LibraryLink(Token t, Library library) {
		
		name = t.image;
		System.out.println("new library link: " + name);
		pos = new Position(t);
		Library.getLink(this);
		
	}

	
	
	
	@Override
	public Position getPosition() {
		return pos;
	}

	@Override
	public void setPosition(Position p) {
		pos = p;
	}





	public Library getLib() {
		return lib;
	}




	public void setLibrary(Library l) {
		lib = l;		
	}

}
