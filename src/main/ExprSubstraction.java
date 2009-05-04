package main;

public class ExprSubstraction extends ExprBinaryOp implements Expression {

	ExprSubstraction(Expression pleft, Expression pright) {
		super(pleft, pright);
	}


	public boolean check() {
		return left.isSubTypeOf(Type.tReal)
			&& right.isSubTypeOf(Type.tReal);
	}

	
	public Expression compile() {
		left = left.compile();
		right = right.compile();
		
		if (!check()) {
			new CompilerError(this, "Type mismatch. Cannot substract a " + left.getType().name + " and a " 
					+ right.getType().name + "." );
		}
		
		if (left instanceof ExprNumber && right instanceof ExprNumber) {
			return ((ExprNumber)left).sub((ExprNumber)right);
		} 
		return this;
	}

	
	public boolean isSubTypeOf(Type p) {
		if (left.isSubTypeOf(Type.tInteger) && right.isSubTypeOf(Type.tInteger)) {
			return p.isSuperTypeOf(Type.tInteger);
		} else if (left.isSubTypeOf(Type.tReal) && right.isSubTypeOf(Type.tReal)) {
			return p.isSuperTypeOf(Type.tReal);
		}
		return false;
	}

	
	public void print(StringBuilder sb) {
		sb.append("(");
		left.print(sb);
		sb.append("-");
		right.print(sb);
		sb.append(")");
		
	}

}
