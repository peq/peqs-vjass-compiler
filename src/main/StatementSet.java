package main;

import java.util.List;

public class StatementSet extends StatementAbstract {
	Expression right;
	Expression left;
	
	public StatementSet(Expression pleft, Variablespace v, Expression pright, List<Statement> ls) {
		super(null, ls);
		left = pleft;
		right = pright;
		pos = new Position(pleft.getPosition(), pright.getPosition());
	}



	public StatementSet(Expression l, Expression e, List<Statement> ls) {
		this(l, Variable.currentVariablespace, e, ls);
	}



	
	public Statement compile() {
		right = right.compile();
		Expression oldleft = left;
		left = left.compile();
		
		if (left == null) {
			new CompilerError(this, "could not compile right side of set Statement.");
			return this;	
		}
		
		if (left.getType() == null) {
			new CompilerError(this, "could not find Type.");
			return this;		
		}
		if (!right.isSubTypeOf(left.getType())) {
			StringBuilder sb = new StringBuilder();
			print(sb);
			new CompilerError(this, "Type mismatch in set-Statement. Cannot convert from " +
					right.getType().name + " to " + left.getType().name + " in\n" + sb);
		}
		
		// TODO check if left side of set is a variable
		// TODO check if left side of set is not constant
		
		return this;
	}

	
	public void print(StringBuilder sb) {
		sb.append("set ");
		left.print(sb);
		sb.append(" = ");
		right.print(sb);
		sb.append("\n");
	}



}
