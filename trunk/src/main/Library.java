package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Library extends Scope {

	
	
	static Map<String,Library> LibraryMap = new HashMap<String,Library>();
	static List<Library> LibraryList = new LinkedList<Library>(); 
	
	public Library(String pname, List<Token> reqs) {
		super(pname);
		for (Token t : reqs) {
			requirements.add(new LibraryLink(t, this));
		}
		LibraryMap.put(name, this);
		LibraryList.add(this);
	}

	public Library(String pname, Token pinit, List<Token> reqs) {
		this(pname, reqs);
		init = new FunctionLink(pinit.image, this, new Position(pinit));
	}

	
	static Queue<LibraryLink> librarylinks = new LinkedList<LibraryLink>();
	
	public static void getLink(LibraryLink libraryLink) {
		librarylinks.add(libraryLink);
	}

	static void doLibraryLinksQueue() {
		for (LibraryLink ll : librarylinks) {
			Library l = search(ll);
			
			if (l == null) {
				new CompilerError(ll, "The required library " + ll.name + " could not be found.");
			} else {
				ll.setLibrary(l);
			}
		}
	}

	private static Library search(LibraryLink ll) {
		Library l = LibraryMap.get(ll.name);
		return l;
	}



}
