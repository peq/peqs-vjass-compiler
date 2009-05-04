package main;

import java.util.List;

public class ExprAddition extends ExprBinaryOp implements Expression {
	
	
	
	ExprAddition(Expression pleft, Expression pright) {
		super(pleft, pright);
	}

	
	public boolean check() {
		return left.isSubTypeOf(Type.tReal)
			&& right.isSubTypeOf(Type.tReal)
			|| left.isSubTypeOf(Type.tString)
			&& right.isSubTypeOf(Type.tString);
	}

//	@Override
	public boolean isSubTypeOf(Type p) {
		if (left.isSubTypeOf(Type.tInteger) && right.isSubTypeOf(Type.tInteger)) {
			return p.isSuperTypeOf(Type.tInteger);
		} else if (left.isSubTypeOf(Type.tReal) && right.isSubTypeOf(Type.tReal)) {
			return p.isSuperTypeOf(Type.tReal);
		} else if(left.isSubTypeOf(Type.tString) && right.isSubTypeOf(Type.tString)) {
			return p.isSuperTypeOf(Type.tString);
		}
		return false;
	}

//	@Override
	public Expression compile() {
		left = left.compile();
		right = right.compile();
		
		if (!check()) {
			new CompilerError(this, "Type mismatch. Cannot add a " + left.getType().name + " and a " 
					+ right.getType().name + "." );
		}
		
		if (left instanceof ExprNumber && right instanceof ExprNumber) {
			return ((ExprNumber)left).add((ExprNumber)right);
		} else if (left instanceof ExprString && right instanceof ExprString) {
			// TODO this might not always be right....
			// return this;
			return new ExprString(((ExprString)left), ((ExprString)right));
		}
		return this;
	}

//	@Override
	public void print(StringBuilder sb) {
		sb.append("(");
		left.print(sb);
		sb.append("+");
		right.print(sb);
		sb.append(")");
	}


	


	



}
