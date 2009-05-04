package main;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ExprArrayVariable implements Expression {
	VariableLink variabelink;
	List<Expression> arrayindexes;
	Position pos;
	
	public ExprArrayVariable(Token pname, Variablespace variablespace, List<Expression> parrayindexes) {
		variabelink = new VariableLink(pname.image, variablespace, new Position(pname));
		arrayindexes = parrayindexes;
		pos = new Position(new Position(pname), parrayindexes.get(parrayindexes.size()-1).getPosition());
	}
	
	

	public ExprArrayVariable(Token t, List<Expression> parrayindexes) {
		this(t, Variable.currentVariablespace, parrayindexes);
	}



//	@Override
	public boolean isSubTypeOf(Type p) {
		return variabelink.var.isSubTypeOf(p);
	}

//	@Override
	public Expression compile() {
		boolean indexescorrect = true;
		if (arrayindexes.size() != variabelink.var.arraysize.size()) {
			new CompilerError(this, "Expected " + variabelink.var.arraysize.size() + " indexes but found " 
					+ arrayindexes.size() + ".");
			indexescorrect = false;
		}
		
		ListIterator<Expression> it = arrayindexes.listIterator();
		while (it.hasNext()) {
			Expression current = it.next();
			current = current.compile();
			
			if (!current.isSubTypeOf(Type.tInteger) ) {
				new CompilerError(current, "Arrayindexes have to be of type integer, found " 
						+ current.getType().name + ".");
			}
			
			it.set(current);
		}
		// TODO extended array storage
		if (indexescorrect) {
			Expression index = arrayindexes.get(0);
			for (int i = 0; i < variabelink.var.arraysize.size()-1; i++) {
				int size = variabelink.var.arraysize.get(i);
				Expression val = arrayindexes.get(i+1);
				index = new ExprAddition(index, new ExprMultiplication(new ExprInteger(val, size),val));
			}
			arrayindexes.clear();
			arrayindexes.add(index.compile());
		}
		return this;
	}

//	@Override
	public void print(StringBuilder sb) {
		// not valid Jass
		sb.append(variabelink.var.name);
		for (Expression arrayindex : arrayindexes) {
			sb.append("[");
			arrayindex.print(sb);
			sb.append("]");
		}
	}

//	@Override
	public Type getType() {
		return variabelink.getType();
	}

//	@Override
	public Position getPosition() {
		return pos;
	}

//	@Override
	public void setPosition(Position p) {
		pos = p;
	}
	
	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		for (Expression arrayindex : arrayindexes) {
			l.add(arrayindex);
		}
		return l;
	}

}
