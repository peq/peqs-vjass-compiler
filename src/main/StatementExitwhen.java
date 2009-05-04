package main;

import java.util.List;

public class StatementExitwhen extends StatementAbstract {
	Expression exp;
	
	public StatementExitwhen(Expression e, List<Statement> sl) {
		super(e.getPosition(), sl);
		exp = e;
	}

	
	public Statement compile() {
		exp = exp.compile();
		if (!exp.isSubTypeOf(Type.tBoolean)) {
			new CompilerError(this, "Right side of exitwhen is not of type boolean.");
		}
		return this;
	}

	
	public void print(StringBuilder sb) {
		sb.append("exitwhen ");
		exp.print(sb);
		sb.append("\n");
		
	}



}
