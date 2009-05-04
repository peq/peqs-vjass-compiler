package main;

import java.util.LinkedList;
import java.util.List;

public class ExprNot implements Expression {
	Expression child;
	Position pos;
	
	public ExprNot(Expression e) {
		child = e;
		pos = new Position(e.getPosition());
	}



	
	public Expression compile() {
		child = child.compile();
		
		if (!child.isSubTypeOf(Type.tBoolean)) {
			new CompilerError(this, "Expected boolean after <not> but found " + child.getType().name + ".");
		}
		
		if (child instanceof ExprBoolean) {
			return new ExprBoolean(this, this, !((ExprBoolean)child).val);
		}
		// OPTIMIZE add more optimizations here -> not not , comparisons
		return child;
	}



	
	public Type getType() {
		return Type.tBoolean;
	}

	
	public boolean isSubTypeOf(Type p) {
		return Type.tBoolean.isSubTypeOf(p);
	}

	
	public void print(StringBuilder sb) {
		sb.append(" not ");
		child.print(sb);
	}

	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		p = pos;
	}

	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		l.add(child);
		return l;
	}
	
}
