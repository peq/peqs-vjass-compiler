package main;

import java.util.LinkedList;
import java.util.List;

public class ExprMember implements Expression {
	Expression object;
	Expression member;
	Position pos;
	
	public ExprMember(Expression pobject, Expression pmember) {
			object = pobject;
			member = pmember;
			pos = new Position(pobject, pmember);
	}



	
	public Expression compile() {
		// TODO implement Member
		return null;
	}

	
	public Type getType() {
		// TODO implement Member
		return null;
	}

	
	public boolean isSubTypeOf(Type p) {
		// TODO implement Member
		return false;
	}

	
	public void print(StringBuilder sb) {
		// TODO implement Member
		
	}

	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos = p;
	}

	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		l.add(member);
		l.add(object);
		return l;
	}
	
}
