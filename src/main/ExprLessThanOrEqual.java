package main;

public class ExprLessThanOrEqual extends ExprBinaryOp implements Expression {

	ExprLessThanOrEqual(Expression pleft, Expression pright) {
		super(pleft, pright);
	}


	public boolean check() {
		return Type.getSmallestCommonSuperType(left.getType(), right.getType()).isSubTypeOf(Type.tReal);
	}

	
	public Expression compile() {
		left = left.compile();
		right = right.compile();
		
		if (!check()) {
			new CompilerError(this, "Type mismatch. Cannot compare a " + left.getType().name + " and a " 
					+ right.getType().name + "." );
		}
		
		if (left instanceof ExprNumber && right instanceof ExprNumber) {
			return new ExprBoolean(left, right, ((ExprNumber)left).compare((ExprNumber)right) <= 0);
		}
		return this;
	}

	
	public boolean isSubTypeOf(Type p) {
		return Type.tBoolean.isSubTypeOf(p);
	}
	
	
	public Type getType() {
		return Type.tBoolean;
	}

	
	public void print(StringBuilder sb) {
		sb.append("(");
		left.print(sb);
		sb.append(" <= ");
		left.print(sb);
		sb.append(")");
		
	}

}
