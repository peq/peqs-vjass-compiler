package main;

public class FunctionLink implements WithPosition {
	public FunctionLink(String pname, Scope pscope, Position ppos, Function pcalledFrom) {
		name = pname;
		scope = pscope;
		pos = ppos;
		calledFrom = pcalledFrom;
		Function.getLink(this);
	}
	
	public FunctionLink(String pname, Scope pscope, Position ppos) {
		this(pname, pscope, ppos, Function.current);
	}

	public FunctionLink(Function f, Function pcalledFrom) {
		name = f.name;
		scope = f.scope;
		pos = f.pos;		
		calledFrom = pcalledFrom;
		
		setFunc(f);
	}

	Function calledFrom;
	
	private Function func;
	String name;
	Scope scope;
	Position pos;
	
	Function getFunc() {
		return func;
	}
	
	void setFunc(Function f) {
		func = f;
		if (calledFrom != null) {
			Function.link(calledFrom, f);
		}
	}
	
	
	public Position getPosition() {
		return pos;
	}
	
	public void setPosition(Position p) {
		pos = p;
	}
	public void print(StringBuilder sb) {
		if (func != null) {
			sb.append(func.name);
		} else {
			sb.append("<unknown function>");
		}
		
	}
	public Type getType() {
		if (func != null) {
			return func.getType();
		}
		return Type.tUnknown;
	}
	
}
