package main;

import java.util.LinkedList;
import java.util.List;

public class ExprNegative implements Expression {

	Expression child;
	Position pos;

	public ExprNegative(Expression a) {
		child = a;
		pos = new Position(a.getPosition());
	}



	
	public Expression compile() {
		child = child.compile();
		
		if (!child.isSubTypeOf(Type.tReal)) {
			new CompilerError(this, "Expected number after minus but found " + child.getType().name + ".");
		}
		
		if (child instanceof ExprReal) {
			return new ExprReal(this, this, - ((ExprReal)child).val);
		} else if (child instanceof ExprInteger) {
			return new ExprInteger(child, - ((ExprInteger)child).val);
		}		
		return child;
	}

	
	public Type getType() {
		return child.getType();
	}

	
	public boolean isSubTypeOf(Type p) {
		return child.isSubTypeOf(p);
	}

	
	public void print(StringBuilder sb) {
		sb.append("(-");
		child.print(sb);
		sb.append(")");
	}

	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos = p;
	}

	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		l.add(child);
		return l;
	}
	
}
