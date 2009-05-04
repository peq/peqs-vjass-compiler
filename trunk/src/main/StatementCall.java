package main;

import java.util.List;

public class StatementCall extends StatementAbstract {
	Expression expression;
	
	enum CompileMethod{
		call,execute,evaluate
	}
	
	CompileMethod compileMethod = CompileMethod.call;
	
	public StatementCall(Expression e, List<Statement> ls) {
		super(new Position(e.getPosition()), ls);
		expression = e;
	}

	
	public StatementCall compile() {
		
		expression = expression.compile();
		// CHECK more expressions allowed here?
		if (!(expression instanceof ExprFunction)) {
			// OPTIMIZE better error description ?
			new CompilerError(expression, "Expected function call after <call> but found something else.");
		}
		return this;
	}

	
	public void print(StringBuilder sb) {
		if (compileMethod == CompileMethod.call) {
			sb.append("call ");
			expression.print(sb);
			sb.append("\n");
		} else if (compileMethod == CompileMethod.execute) {
			if (expression instanceof ExprFunction) {
				sb.append("call ExecuteFunc(\"");
				((ExprFunction)expression).functionlink.print(sb);
				sb.append("\")\n");
			} else {
				new CompilerError(expression, "Could not excute function.");
			}
		}
	}



}
