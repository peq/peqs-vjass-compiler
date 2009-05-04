package main;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ExprFunction implements Expression {
	Position pos;
	FunctionLink functionlink;
	List<Expression> parameters;
	
	public ExprFunction(Token pfuncname, List<Expression> pparameters) {
		parameters = pparameters;
		functionlink = new FunctionLink(pfuncname.image, Scope.current, new Position(pfuncname));
		if (pparameters.size() > 0){
			pos = new Position(new Position(pfuncname), pparameters.get(pparameters.size()-1).getPosition());
		} else {
			pos = new Position(pfuncname);
		}
		
	}



	public ExprFunction(Function sinit, Function calledFrom, List<Expression> pparameters) {
		// fake Pos
		pos = new Position(0,0,0,0);
		
		parameters = pparameters;
		functionlink = new FunctionLink(sinit, calledFrom);
	}

	public ExprFunction(Function sinit, Function calledFrom) {
		this(sinit, calledFrom, new LinkedList<Expression>());
	}


	
	public Expression compile() {
		ListIterator<Expression> it = parameters.listIterator();
		
		int max = 0;
		if (functionlink.getFunc() != null) {
			max = Math.max(parameters.size(), functionlink.getFunc().parameters.size());
		
		
			if (parameters.size() > functionlink.getFunc().parameters.size()) {
				new CompilerError(this, "Too many parameters passed to function " 
						+ functionlink.getFunc().name + "." );
			} else if (parameters.size() < functionlink.getFunc().parameters.size()) {
				new CompilerError(this, "Too few parameters passed to function " 
						+ functionlink.getFunc().name + "." );
			}
		}
		
		
		int i=0;
		while (it.hasNext()) {
			Expression current = it.next();
			current = current.compile();
			if (i < max) {
				Parameter t = functionlink.getFunc().parameters.get(i);
				if (!current.isSubTypeOf(t.getType())) {
					if (t == null) {
						new CompilerError(current, "Could not find Function parameter " + i + " for function "
								+ functionlink.name + "."); 
					} else {
							StringBuilder sb = new StringBuilder();
							current.print(sb);
							new CompilerError(current, "Type mismatch. Expected " + t.getType().name 
									+ " as Parameter " + (i+1)  
									+ " \"" + t.name + "\" for function " + functionlink.getFunc().name + " but found " 
									+ current.getType().name + "\n" + sb );
					}
				}
			} else {
				new CompilerError(current, "Unexpected Parameter.");
			}
			it.set(current);
			i++;
		}
		return this;
	}

	
	public Type getType() {
		if (functionlink.getFunc() != null) {
			return functionlink.getType();
		}
		return Type.tUnknown;
	}

	
	public boolean isSubTypeOf(Type p) {
		if (functionlink.getFunc() != null) {
			return functionlink.getFunc().returntype.isSubTypeOf(p);
		}
		return false;
	}

	
	public void print(StringBuilder sb) {
		functionlink.print(sb);
		sb.append("(");
		boolean first = true;
		for (Expression parameter : parameters) {
			if (!first) {
				sb.append(", ");
			}
			first = false;
			parameter.print(sb);
		}
		sb.append(")");
		
	}

	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos = p;		
	}
	
	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		for (Expression p : parameters) {
			l.add(p);
		}
		return l;
	}

}
