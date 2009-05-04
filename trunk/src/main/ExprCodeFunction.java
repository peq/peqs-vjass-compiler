package main;

import java.util.LinkedList;
import java.util.List;

public class ExprCodeFunction implements Expression {
	Position pos;
	FunctionLink functionlink;
	
	public ExprCodeFunction(Token n) {
		pos = new Position(n);
		functionlink = new FunctionLink(n.image, Scope.current, pos);
		
	}

//	@Override
	public Expression compile() {
		// CHECK check if function is takes nothing returns nothing|boolean ?
		return this;
	}

//	@Override
	public Type getType() {
		return Type.tCode;
	}

//	@Override
	public boolean isSubTypeOf(Type p) {
		return Type.tCode.isSubTypeOf(p);
	}

//	@Override
	public void print(StringBuilder sb) {
		sb.append("function ");
		functionlink.print(sb);
	}

//	@Override
	public Position getPosition() {
		return pos;
	}

//	@Override
	public void setPosition(Position p) {
		pos=p;
	}

	
	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		return l;
	}
}
