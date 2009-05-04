package main;

import java.util.LinkedList;
import java.util.List;

public class ExprInteger implements Expression, ExprNumber {
	int val;
	Position pos;
	
	ExprInteger(Token t, int pval) {
		val = pval;
		pos = new Position(t);
	}
	
	ExprInteger(WithPosition a, WithPosition b, int pval) {
		val = pval;
		pos = new Position(a.getPosition(), b.getPosition());
	}
	
	ExprInteger(WithPosition a, int pval) {
		val = pval;
		pos = new Position(a.getPosition());
	}
	


	
	public Expression compile() {
		return this;
	}

	
	public boolean isSubTypeOf(Type p) {
		return p == Type.tInteger || p == Type.tReal;
	}

	
	public ExprNumber add(ExprNumber x) {
		if (x instanceof ExprInteger) {
			return new ExprInteger(this, x, val + ((ExprInteger)x).val);
		} else if (x instanceof ExprReal) {
			return new ExprReal(this, x, val + ((ExprReal)x).val);
		}
		return null;
	}

	
	public ExprNumber div(ExprNumber x) {
		if (x instanceof ExprInteger) {		
			if (((ExprInteger)x).val == 0) {
				new CompilerError(x, "Division by 0.");
				return new ExprInteger(this, x, 1);
			}
			return new ExprInteger(this, x, val / ((ExprInteger)x).val);
		} else if (x instanceof ExprReal) {
			if (((ExprReal)x).val == 0) {
				new CompilerError(x, "Division by 0.");
				return new ExprReal(this, x, 1);
			}
			return new ExprReal(this, x, val / ((ExprReal)x).val);
		}
		return null;
	}

	
	public ExprNumber mul(ExprNumber x) {
		if (x instanceof ExprInteger) {
			return new ExprInteger(this, x, val * ((ExprInteger)x).val);
		} else if (x instanceof ExprReal) {
			return new ExprReal(this, x, val * ((ExprReal)x).val);
		}
		return null;
	}

	
	public ExprNumber sub(ExprNumber x) {
		if (x instanceof ExprInteger) {
			return new ExprInteger(this, x, val - ((ExprInteger)x).val);
		} else if (x instanceof ExprReal) {
			return new ExprReal(this, x, val - ((ExprReal)x).val);
		}
		return null;
	}

	
	public Type getType() {
		return Type.tInteger;
	}

	
	public void print(StringBuilder sb) {
		sb.append(val);
	}

	
	public double compare(ExprNumber x) {
		if (x instanceof ExprInteger) {
			return val - ((ExprInteger)x).val;
		} else if (x instanceof ExprReal) {
			return val - ((ExprReal)x).val;
		}
		return 0;
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
