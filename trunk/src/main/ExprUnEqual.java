package main;

public class ExprUnEqual extends ExprBinaryOp implements Expression {

	ExprUnEqual(Expression pleft, Expression pright) {
		super(pleft, pright);
	}


	public boolean check() {
		return Type.getSmallestCommonSuperType(left.getType(), right.getType()) != null;
	}

	
	public Expression compile() {
		left = left.compile();
		right = right.compile();
		
		if (!check()) {
			new CompilerError(this, "Type mismatch. Cannot compare a " + left.getType().name + " with a " 
					+ right.getType().name + "." );
		}
		
		if (left instanceof ExprNumber && right instanceof ExprNumber) {
			return new ExprBoolean(left, right, ((ExprNumber)left).compare((ExprNumber)right) != 0);
		} else if (left instanceof ExprBoolean && right instanceof ExprBoolean) {
			return new ExprBoolean(left, right, ((ExprBoolean)left) != ((ExprBoolean)right));
		} else if (left instanceof ExprString && right instanceof ExprString) {
			return new ExprBoolean(left, right, (!((ExprString)left).equals((ExprString)right)));
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
		sb.append(" != ");
		right.print(sb);
		sb.append(")");
	}

}
