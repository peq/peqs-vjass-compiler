package main;

import java.util.LinkedList;
import java.util.List;

public class ExprBoolean implements Expression {
	boolean val;
	Position pos;
	
	ExprBoolean(Token t) {
		val = t.image.equals("true") ? true : false;
		pos = new Position(t);
	}
	
	public ExprBoolean() {
	}

	public ExprBoolean(Expression left, Expression right, boolean b) {
		val = b;
		pos = new Position(left, right);
	}

	public ExprBoolean(WithPosition a, boolean b) {
		val = b;
		pos = new Position(a);
	}

	public static ExprBoolean and(ExprBoolean a, ExprBoolean b) {
		ExprBoolean eb = new ExprBoolean();
		eb.val = a.val && b.val;
		eb.pos = new Position(a, b);
		return eb;
	}
	
	public static ExprBoolean or(ExprBoolean a, ExprBoolean b) {
		ExprBoolean eb = new ExprBoolean();
		eb.val = a.val || b.val;
		eb.pos = new Position(a, b);
		return eb;
	}
	





//	@Override
	public boolean isSubTypeOf(Type p) {
		return (p==Type.tBoolean);
	}



//	@Override
	public Expression compile() {
		return this;
	}

//	@Override
	public void print(StringBuilder sb) {
		sb.append(val ? "true" : "false");		
	}

//	@Override
	public Type getType() {
		return Type.tBoolean;
	}

//	@Override
	public Position getPosition() {
		return pos;
	}

//	@Override
	public void setPosition(Position p) {
		pos = p;
	}
	
	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		return l;
	}

}
