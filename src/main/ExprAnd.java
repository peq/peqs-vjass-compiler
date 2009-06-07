package main;

public class ExprAnd extends ExprBinaryOp implements Expression {

	ExprAnd(Expression pleft, Expression pright) {
		super(pleft, pright);
	}
 

	public boolean check() {
		return left.isSubTypeOf(Type.tBoolean) 
			&& right.isSubTypeOf(Type.tBoolean);
	}

//	@Override
	public boolean isSubTypeOf(Type p) {
		return Type.tBoolean.isSubTypeOf(p);
	}

//	@Override
	public Expression compile() {
		left = left.compile();
		right = right.compile();
		
		if (!left.isSubTypeOf(Type.tBoolean)) {
			new CompilerError(left, "Type mismatch. The left side of operator <and> has to be of type boolean." );
		}
		if (!right.isSubTypeOf(Type.tBoolean)) {
			new CompilerError(right, "Type mismatch. The right side of operator <and> has to be of type boolean." );
		}
		
		if (left instanceof ExprBoolean && right instanceof ExprBoolean) {
			return ExprBoolean.and(((ExprBoolean)left), ((ExprBoolean)right));
		}
		return this;
	}

//	@Override
	public void print(StringBuilder sb) {
		// (left and right)
		sb.append("(");
		left.print(sb);
		sb.append(" and ");
		right.print(sb);
		sb.append(")");
	}

}
