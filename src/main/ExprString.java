package main;

import java.util.LinkedList;
import java.util.List;

public class ExprString implements Expression {
	String val;
	Position pos;
	
	public ExprString(Token t) {
		val = t.image.substring(1, t.image.length()-1);
		pos = new Position(t);
	}
	
	public ExprString(ExprString a, ExprString b) {
		val = a.val + b.val;
		pos = new Position(a, b);
	}
	


	
	public ExprString(WithPosition a, String pval) {
		val = pval;
		pos = new Position(a);
	}

	public Expression compile() {
		return this;
	}

	
	public Type getType() {
		return Type.tString;
	}

	
	public boolean isSubTypeOf(Type p) {
		return Type.tString.isSubTypeOf(p);
	}

	
	public void print(StringBuilder sb) {
		sb.append("\"");
		sb.append(val);
		sb.append("\"");
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
