package main;

import java.util.LinkedList;
import java.util.List;

public class ExprBinaryOp implements WithPosition {
	Expression left, right;
	Position pos;
	
	ExprBinaryOp(Expression pleft, Expression pright){
		left = pleft;
		right = pright;
		pos = new Position(pleft.getPosition(), pright.getPosition());
	}
	
	public Type getType() {
		return Type.getSmallestCommonSuperType(left.getType(), right.getType());
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
		l.add(left);
		l.add(right);
		return l;
	}
}
