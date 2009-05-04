package main;

import java.util.LinkedList;
import java.util.List;

public class ExprThis implements Expression {
	Position pos;
	
	
	ExprThis() {
		pos = new Position(Qjass.token);
	}


	
	public Expression compile() {
		return this;
	}

	
	public Type getType() {
		// TODO implement this
		return null;
	}

	
	public boolean isSubTypeOf(Type p) {
		// TODO implement this
		return false;
	}

	
	public void print(StringBuilder sb) {
		// TODO implement this		
	}

	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos = p;
	}
	
	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		return l;
	}

}
