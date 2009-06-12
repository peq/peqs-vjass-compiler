package main;

// TODO check if function main takes nothing returns nothing
// TODO check if initializers take nothing returns nothing

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Function implements Variablespace, WithPosition, Compilable {
	

	
	static Function functionMain;
	boolean constant;
	String name;
	List<Parameter> parameters;
	TypeLink returntype;
	Scopemodifier scopemodifier;
	Scope scope;
	Position pos;
	
	Map<String,Local> LocalMap = new HashMap<String,Local>();
	List<Local> LocalList;
	List<Statement> body;
	
	List<Function> calls;
	List<Function> calledBy;
	/*
	public Function(Scopemodifier pscopemodifier, boolean pconstant,
			Token pfuncname, List<Parameter> pparameters, Token preturntype,
			List<Local> plocals, List<Statement> pbody) {
		scopemodifier = pscopemodifier;
		constant = pconstant;
		name = pfuncname.image;
		parameters = pparameters;
		scope = Scope.current;
		returntype = new TypeLink(scope, preturntype.image, new Position(preturntype));
		LocalList = plocals;	
		body = pbody;
		
		for (Local l : LocalList) {
			LocalMap.put(l.name, l);
		}
		scope.addFunction(this);
		
	}
*/
	public Function(Scopemodifier pscopemodifier, boolean pconstant,
			Token pfuncname, List<Parameter> pparameters, Token preturntype) {
		scopemodifier = pscopemodifier;
		constant = pconstant;
		name = pfuncname.image;
		parameters = pparameters;
		scope = Scope.current;
		returntype = new TypeLink(scope, preturntype.image, new Position(preturntype));
		Function.current = this;
		scope.addFunction(this);
		
		pos = new Position(pfuncname);
		
		calls = new LinkedList<Function>();
		calledBy = new LinkedList<Function>();
		
		Variable.currentVariablespace = this;
		
		// TODO check for double names
		
		if (name.equals("main")) {
			
			Function.functionMain = this;
			
		}
	}

	static void link(Function from, Function to) {
		 from.calls.add(to);
		 to.calledBy.add(from);
	}
	
	static Queue<FunctionLink> functionlinks = new LinkedList<FunctionLink>();
	static Function current = null;
	
	public static void getLink(FunctionLink functionLink) {
		Function f = Function.search(functionLink);
		
		if (f  == null) {
			functionlinks.add(functionLink);
		} else {
			functionLink.setFunc(f); 
		}
	}

	private static Function search(FunctionLink functionLink) {
		Scope currentscope = functionLink.scope;
		while (currentscope != null) {
			Function f = currentscope.searchFunction(functionLink);
			if (f != null) {
				return f;
			}
			currentscope = currentscope.parentScope;
		}
		return null;
	}
	
	static void doFunctionLinksQueue() {
		for (FunctionLink fl : functionlinks) {
			Function f = search(fl);
			
			if (f == null) {
				new CompilerError(fl, fl.name + " could not be resolved to a function.");
			} else {
				fl.setFunc(f);
			}
		}
	}

	
	public Variable searchVariable(VariableLink variableLink) {
		
		Local l = LocalMap.get(variableLink.name);
		if (l!= null) {
			return l;
		}
		for (Parameter p : parameters) {
			if (p.name.equals(variableLink.name)) {
				return p;
			}
		}
		return null; 
	}

	
	public Variablespace getParent() {
		return scope;
	}

	
	public Scope getScope() {
		return scope;
	}

	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos=p;
		
	}

	
	public Function compile() {
		if (LocalList != null){
			ListIterator<Local> it = LocalList.listIterator();
			
			while(it.hasNext()) {
				Local l = it.next();
				l = l.compile();
				it.set(l);
			}
		}
		if (body != null ){
			ListIterator<Statement> it = body.listIterator();
			
			while(it.hasNext()) {
				Statement s = it.next();
				s = s.compile();
				it.set(s);
			}
		}
		// TODO check if function really is constant
		
		//check locals:
		Set<Local> defined = new HashSet<Local>();
		if (LocalList != null) {
			for (Local l : LocalList) {
				if (l.initial != null) {
					List<VariableLink> used = new ExpressionCollector(l.initial).getChildVariabeLinks();
					for (VariableLink vl : used) {
						if (vl.var.variablespace.equals(this) && !defined.contains(vl.var)) {
							new CompilerError(vl, "Variable " + vl.name + " used before it has been defined.");
						}
					}
				}
				defined.add(l);
			}
		}
		return this;
	}

	
	public void print(StringBuilder sb) {
		if (constant) {
			sb.append("constant ");
		}
		sb.append("function ");
		sb.append(name);
		sb.append(" takes ");
		boolean first = true;
		for (Parameter p : parameters) {
			if (!first) {
				sb.append(",");
			}
			first = false;
			p.print(sb);			
		}
		if (first) {
			sb.append("nothing");
		}
		
		sb.append(" returns ");
		returntype.print(sb);
		sb.append("\n");
		
		if (LocalList != null) {
			for (Local l : LocalList) {
				l.print(sb);
			}
		}
		if (body != null) {
			for (Statement s : body) {
				if (s!=null) {
					s.print(sb);
				}
			}
		}
		sb.append("endfunction\n");
	}
	
	private void print(StringBuilder sb, String location) {
		if (!pos.file.equals(location)) {
			return;
		}
		print(sb);
		
	}

	public void finish(List<Local> plocals, List<Statement> pbody) {
		LocalList = plocals;	
		body = pbody;
		
		for (Local l : LocalList) {
			LocalMap.put(l.name, l);
		}
		
		Function.current = null;
	}

	static List<Function> allFunctions = new LinkedList<Function>();
	static List<Function> allFunctionsSorted = new LinkedList<Function>();
	
	public static void compileAll(Scope s) {
		ListIterator<Function> fit = s.FunctionList.listIterator();
		while (fit.hasNext()) {
			Function f = fit.next();
			f = f.compile();
			allFunctions.add(f);
			fit.set(f);
		}
		
		for (Scope c : s.childScopes) {
			compileAll(c);
		}
		
	}
	
	public static void compileAll() {
		compileAll(Scope.mainscope);
	}

	int sortedIndex = 0;
	static Stack<Function> reorderStack = new Stack<Function>();
	
	void reorder(){
//		if (!reorderStack.empty()) { 
//			System.out.println(reorderStack.peek().name + " -> " +  name + " ( " + sortedIndex+ " )");
//		} else {
//			System.out.println("start -> " +  name + " ( " + sortedIndex+ " )");
//		}
		
		reorderStack.push(this);
		
		if (sortedIndex != 0) {
			StringBuilder sb = new StringBuilder();
			if (sortedIndex < 0) {
				Function cur = reorderStack.pop();
				while (!reorderStack.empty()) {				
					
					sb.append("\n <-- ");
					sb.append(cur.name);
					cur = reorderStack.pop();
					if (cur == this) {
						break;
					}
						
				}
				sb.append("\n <-- ");
				sb.append(name);
			
				new CompilerError(this, "Detected Loop in function calls:\n" + sb);
			}
		} else {
			
			sortedIndex = -1;
			if (calls.size() == 0) {
				allFunctionsSorted.add(this);
				sortedIndex = allFunctionsSorted.size();
			} else {
				for (Function called : calls) {
					if (called != this) {
						called.reorder();
					}
				}
				allFunctionsSorted.add(this);
				sortedIndex = allFunctionsSorted.size();
			}
		}
		if (!reorderStack.empty() && reorderStack.peek() == this) {
			reorderStack.pop();
		}
	}
	
	public static void reorderAll() {
		if (functionMain == null) {
			new CompilerError(new Position(0,0,1,0), "Missing Main-Function");
			return;
		}
		Function.functionMain.reorder();
	}

	public static void printAll(StringBuilder sb) {
		for (Function f : allFunctionsSorted) {
			f.print(sb);
		}
		
	}
	
	public String toString() {
		return "Function " + name;
	}

	public Type getType() {
		if (returntype != null) {
			return returntype.getType();
		}
		return Type.tUnknown;
	}

	public static void printAll(StringBuilder sb, String location) {
		for (Function f : allFunctionsSorted) {
			f.print(sb, location);
		}
		
	}

	
}
