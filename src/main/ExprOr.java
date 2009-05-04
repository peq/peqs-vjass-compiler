package main;

public class ExprOr extends ExprBinaryOp implements Expression {


	
	ExprOr(Expression pleft, Expression pright) {
		super(pleft, pright);
	}



	
	public boolean isSubTypeOf(Type p) {
		return Type.tBoolean.isSubTypeOf(p);
	}

	
	public Expression compile() {
		left = left.compile();
		right = right.compile();
		
		if (!left.isSubTypeOf(Type.tBoolean)) {
			new CompilerError(left, "Type mismatch. The left side of operator <or> has to be of type boolean." );
		}
		if (!right.isSubTypeOf(Type.tBoolean)) {
			new CompilerError(right, "Type mismatch. The right side of operator <or> has to be of type boolean." );
		}
		
		if (left instanceof ExprBoolean && right instanceof ExprBoolean) {
			return ExprBoolean.or(((ExprBoolean)left), ((ExprBoolean)right));
		}
		return this;
	}

	
	public void print(StringBuilder sb) {
		sb.append("(");
		left.print(sb);
		sb.append(" or ");
		right.print(sb);
		sb.append(")");
		
	}

}
