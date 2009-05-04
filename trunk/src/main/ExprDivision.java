package main;

public class ExprDivision extends ExprBinaryOp implements Expression {

	ExprDivision(Expression pleft, Expression pright) {
		super(pleft, pright);
	}


	public boolean check() {
		return left.isSubTypeOf(Type.tReal)
			&& right.isSubTypeOf(Type.tReal);
	}

	//@Override
	public Expression compile() {
		left = left.compile();
		right = right.compile();
		
		if (!check()) {
			new CompilerError(this, "Type mismatch. Cannot divide a " + left.getType().name + " by a " 
					+ right.getType().name + "." );
		}
		
		if (left instanceof ExprNumber && right instanceof ExprNumber) {
			return ((ExprNumber)left).div((ExprNumber)right);
		} 
		return this;
	}

	//@Override
	public boolean isSubTypeOf(Type p) {
		if (left.isSubTypeOf(Type.tInteger) && right.isSubTypeOf(Type.tInteger)) {
			return p.isSuperTypeOf(Type.tInteger);
		} else if (left.isSubTypeOf(Type.tReal) && right.isSubTypeOf(Type.tReal)) {
			return p.isSuperTypeOf(Type.tReal);
		}
		return false;
	}

	//@Override
	public void print(StringBuilder sb) {
		sb.append("(");
		left.print(sb);
		sb.append("/");
		right.print(sb);
		sb.append(")");
		
	}

}
