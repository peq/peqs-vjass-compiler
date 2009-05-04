package main;

import java.util.LinkedList;
import java.util.List;

public class ExprCodeMethod implements Expression {
	Position pos;
	FunctionLink functionlink;
	
	public ExprCodeMethod(Token n, Token nn) {
		pos = new Position(new Position(n), new Position(nn));
		
	}
	
	// TODO implement methdocalls

	//@Override
	public Expression compile() {
		
		return null;
	}

	//@Override
	public Type getType() {
		return null;
	}

	//@Override
	public boolean isSubTypeOf(Type p) {
		return false;
	}

	//@Override
	public void print(StringBuilder sb) {

	}

	//@Override
	public Position getPosition() {
		return pos;
	}

	//@Override
	public void setPosition(Position p) {
		pos = p;
	}

	
	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		return l;
	}
}
