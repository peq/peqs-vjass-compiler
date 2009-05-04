package main;

import java.util.LinkedList;
import java.util.List;

public class ExprVariable implements Expression {
	VariableLink varlink;
	String varname;
	Position pos;
	
	public ExprVariable(Token pvarname) {
		varname = pvarname.image;
		pos = new Position(pvarname);
		varlink = new VariableLink(varname, Variable.currentVariablespace, new Position(pos));
	}


	
	public Expression compile() {
		// TODO compile variable
		Variable v = varlink.var;
		if (v != null) {
			if (v.constant) {
//				v.initial = v.initial.compile();
				if (v.initial instanceof ExprInteger) {
					return new ExprInteger(this, ((ExprInteger)v.initial).val);
				} else if (v.initial instanceof ExprReal) {
					return new ExprReal(this, ((ExprReal)v.initial).val);
				} else if (v.initial instanceof ExprBoolean) {
					return new ExprBoolean(this, ((ExprBoolean)v.initial).val);
				} else if (v.initial instanceof ExprString) {
					return new ExprString(this, ((ExprString)v.initial).val);						
				}
//				return v.initial;
			}
		}
		return this;
	}

	
	public Type getType() {
		return varlink.getType();
	}

	
	public boolean isSubTypeOf(Type p) {
		return varlink.isSubTypeOf(p);
	}

	
	public void print(StringBuilder sb) {
		varlink.print(sb);
	}

	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos = p;
	}

	public List<Expression> getChilds() {
		List<Expression> l = new LinkedList<Expression>();
		return l;
	}
	
}
