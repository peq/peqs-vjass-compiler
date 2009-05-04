package main;

import java.util.LinkedList;
import java.util.List;

public class ExprNull implements Expression {
	Position pos;
	
	public ExprNull(Token t) {
		pos = new Position(t);
	}

	
	public Expression compile() {
		return this;
	}

	
	public Type getType() {
		return Type.tHandle;
	}

	
	public boolean isSubTypeOf(Type p) {
		return p.isSubTypeOf(Type.tCode)
			|| p.isSubTypeOf(Type.tHandle)
			|| p.isSubTypeOf(Type.tString)
			|| p.isSubTypeOf(Type.tStruct);
	}

	
	public void print(StringBuilder sb) {
		sb.append("null");
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
