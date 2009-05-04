package main;

import java.util.List;

public abstract class StatementAbstract implements Statement, WithPosition {
	Position pos;
	List<Statement> myList;
	
	public StatementAbstract(Position ppos, List<Statement> pmyList) {
		pos = ppos;
		myList = pmyList;
	}
	
	
	
	public void insertAfter(Statement s) {
		int mypos = myList.indexOf(this);
		myList.add(mypos, s);
	}

	
	public void insertBefore(Statement s) {
		int mypos = myList.indexOf(this);
		myList.add(mypos+1, s);
	}

	public void insertBefore(List<Statement> sl) {
		int mypos = myList.indexOf(this);
		for (Statement s : sl) {
			myList.add(mypos, s);
		}
	}
	public void insertAfter(List<Statement> sl) {
		int mypos = myList.indexOf(this);
		for (Statement s : sl) {
			myList.add(mypos+1, s);
		}
	}
	
	
	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos = p;
	}

}
