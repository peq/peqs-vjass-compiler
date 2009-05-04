package main;

import java.util.List;

public class StatementReturn extends StatementAbstract {
	Expression exp;
	Function parentfunc;
	
	public StatementReturn(Expression e, Function f, List<Statement> sl) {
		super(null, sl);
		exp = e;
		parentfunc = f;
		if (e != null) {
			pos = new Position(e.getPosition());
		} else {
			pos = new Position(Qjass.token);
		}
	}

	public StatementReturn(Expression e, List<Statement> sl) {
		this(e, Function.current, sl);
	}

	
	public Statement compile() {
		if (exp == null) {
			if (!parentfunc.returntype.isSubTypeOf(Type.tNothing)) {
				new CompilerError(this, "Type mismatch in return-Statement. This function returns nothing.");
			}
		} else {
			exp = exp.compile();
			if (!exp.isSubTypeOf(parentfunc.returntype.getType())) {
				new CompilerError(this, "Type mismatch in return-Statement. Cannot convert from " +
						exp.getType().name + " to " + parentfunc.returntype.getType().name + ".");
			}
		}
		return this;
	}

	
	public void print(StringBuilder sb) {
		sb.append("return ");
		if (exp != null) { 
			exp.print(sb);
			sb.append("\n");	
		}
	}



}
