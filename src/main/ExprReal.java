package main;

import java.util.LinkedList;
import java.util.List;

public class ExprReal implements Expression, ExprNumber {
	double val;
	Position pos;
	
	ExprReal(Token t) {
		val = Float.parseFloat(t.image);
		pos = new Position(t);
	}
	
	ExprReal(WithPosition a, WithPosition b, double pval) {
		val = pval;
		pos = new Position(a,b);
	}
	
	

	public ExprReal(WithPosition a, double pval) {
		val = pval;
		pos = new Position(a);
	}

	public Expression compile() {
		return this;
	}

	
	public boolean isSubTypeOf(Type p) {
		return Type.tReal.isSubTypeOf(p);
	}

	
	public ExprNumber add(ExprNumber x) {
		if (x instanceof ExprInteger) {
			return new ExprReal(this, x, val + ((ExprInteger)x).val);
		} else if (x instanceof ExprReal) {
			return new ExprReal(this, x, val + ((ExprReal)x).val);
		}
		return null;
	}

	
	public ExprNumber div(ExprNumber x) {
		// TODO div 0 check
		if (x instanceof ExprInteger) {
			if (((ExprInteger)x).val == 0) {
				new CompilerError(x, "Division by 0.");
				return new ExprInteger(this, x, 1);
			}
			return new ExprReal(this, x, val / ((ExprInteger)x).val);
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
			return new ExprReal(this, x, val * ((ExprInteger)x).val);
		} else if (x instanceof ExprReal) {
			return new ExprReal(this, x, val * ((ExprReal)x).val);
		}
		return null;
	}

	
	public ExprNumber sub(ExprNumber x) {
		if (x instanceof ExprInteger) {
			return new ExprReal(this, x, val - ((ExprInteger)x).val);
		} else if (x instanceof ExprReal) {
			return new ExprReal(this, x, val - ((ExprReal)x).val);
		}
		return null;
	}

	
	public Type getType() {
		return Type.tReal;
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
