package main;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Variable implements WithPosition, Compilable {
	static Variablespace currentVariablespace = Scope.mainscope;
	
	// TODO detect non unique variable-names
	
	static List<Integer> defaultArraySize() {
		List<Integer> l = new LinkedList<Integer>();
		l.add(8192);
		return l;
	}
	
	String name="";
	TypeLink typelink=null;
	Variablespace variablespace=null;
	boolean array=false;
	boolean constant;
	List<Integer> arraysize = new LinkedList<Integer>();
	//String typename;
	Expression initial;
	Position pos;
	
	/*Variable(String pname, TypeLink ptype, Variablespace pscope, boolean parray, List<Integer> parraysize, Expression pinitial, Position ppos) {
		name = pname;
		typelink = ptype;
		scope = pscope;
		array = parray;
		arraysize = parraysize;
		initial = pinitial;
		pos = ppos;
		
	}*/
	
	public Variable(Variablespace pvariablespace, String pname, String ptypename, boolean parray,
			boolean pconstant, List<Integer> parraysize, Expression pinitial, Position ppos) {
		// TODO register variable
		name = pname;
		variablespace = pvariablespace;
		array = parray;
		constant = pconstant;
		arraysize = parraysize;
		if (arraysize.size() == 0) {
			arraysize = Variable.defaultArraySize();
		}
		initial = pinitial;
		pos = Position.WholeLine(ppos);
		typelink = new TypeLink(variablespace.getScope(), ptypename, ppos);
	}

	public boolean isSubTypeOf(Type t) {
		return typelink.isSubTypeOf(t);
	}
	
	
	
	public Type getType() {
		return typelink.getType();
	}

	static Queue<VariableLink> variablelinks = new LinkedList<VariableLink>();
	
	public static void getLink(VariableLink variableLink) {
		variableLink.var = search(variableLink);
		if (variableLink.var == null) {
			variablelinks.add(variableLink);
		}
		
	}

	private static Variable search(VariableLink variableLink) {
		Variablespace currentscope = variableLink.variablespace;
		while (currentscope != null) {
			Variable v = currentscope.searchVariable(variableLink);
			if (v != null) {
				return v;
			}
			currentscope = currentscope.getParent();
		}
		return null;
	}
	
	
	static void doVariableLinksQueue() {
		for (VariableLink vl : variablelinks) {
			
			vl.var = search(vl);
			if (vl.var == null) {
				new CompilerError(vl, vl.name + " cannot be resolved to a Variable.");
			}
		}
	}
	
	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos = p;
	}

	
	public Variable compile() {
		// stub
		throw new RuntimeException("compile not implemented for Class Variable.");
		
	}

	
	public void print(StringBuilder sb) {
		sb.append(name);
		
	}
	
}
