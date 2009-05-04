package main;

import java.util.*;

public class Global extends Variable{
	Scope scope;
	Scopemodifier scopemodifier;
	boolean constant;
	
	/*
	Global(String pname, TypeLink ptype, Scope pscope, String ptypename, boolean parray, List<Integer> parraysize, Expression pinitial) {
		super(pname, ptype, pscope, ptypename, parray, parraysize, pinitial);
		
		GlobalMap.put(name, this);
		GlobalList.add(this);
	}
	*/
	public Global(Scopemodifier pscopemodifier, boolean pconstant, Token varname, Token typename, boolean array,
			List<Integer> arraysize, Expression pinitial) {
		super(currentVariablespace, varname.image, typename.image, array, pconstant, arraysize, pinitial, new Position(typename));
		constant = pconstant;
		scope = Scope.current;
		scopemodifier = pscopemodifier;
		Scope.current.addGlobal(this);
		
	}
	/*
	public static void printGlobals() {
		System.out.println("globals");
		for (Global g : GlobalList) {
			if (g.array) {
				System.out.println(g.typename + " array " + g.name);
			} else {
				System.out.println(g.typename + " " + g.name);
			}
		}
		System.out.println("endglobals");
	}
	*/
	
	@Override
	public Global compile() {
		if (initial != null) {
			initial = initial.compile();
			
			if (!initial.isSubTypeOf(typelink.getType())) {
				new CompilerError(
						this, 
						"Type mismatch. Cannot convert " 
						+ initial.getType().name 
						+ " to " 
						+ typelink.getName() 
						+ ".");
			}
		} else if (constant) {
			new CompilerError(this,	"Constant variables require an initial value.");
			constant = false;
		}
		
		return this;		
	}

	@Override
	public void print(StringBuilder sb) {

		if (constant) {
			sb.append("constant ");
		}
		typelink.print(sb);
		sb.append(" ");
		if (array) {
			sb.append("array ");
		}
		
		sb.append(name);
		if (initial != null) {
			sb.append(" = ");
			initial.print(sb);
		}
		// TODO remove comment
		sb.append("// " + scope.name);
		sb.append("\n");
		
	}
	
	public void print(StringBuilder sb, String location) {
		if (!pos.file.equals(location)) {
			return;
		}
		print(sb);
	}
	
	
	public static void compileAll(Scope s) {
		ListIterator<Global> git = s.GlobalList.listIterator();
		while (git.hasNext()) {
			Global g = git.next();
			g = g.compile();
			git.set(g);
		}
		
		for (Scope c : s.childScopes ) {
			compileAll(c);
		}
		
	}
	
	public static void compileAll() {
		compileAll(Scope.mainscope);		
	}

	
	static List<Global> allGlobals = new LinkedList<Global>();
	
	public static void reorderAll() {
		// TODO reorder globals
		reorderAll(Scope.mainscope);
	}

	private static void reorderAll(Scope s) {
		if (s.globalsprinted) {
			return; 
		}
		
		for (LibraryLink ll : s.requirements) {
			if (ll.getLib() != null) {
				reorderAll(ll.getLib());
			}
		}
		
		for (Global g : s.GlobalList) {
			System.out.println("	adding var " + g.name);
			allGlobals.add(g);
		}
		s.globalsprinted = true;
		
		for (Scope c : s.childScopes ) {
			reorderAll(c);
		}	
		
		
		
		
	}
	
	static void check(String location) {
		Set<Global> defined = new HashSet<Global>();
		for (Global g : allGlobals) {
			g.checkVariableLinks(defined, location);
			defined.add(g);
		}
	}
	
	private void checkVariableLinks(Set<Global> defined, String location) {
		if (initial != null) {
			List<VariableLink> used = new ExpressionCollector(initial).getChildVariabeLinks();
			for (VariableLink vl : used) {
				if (vl.var.getPosition().file.equals(location) && !defined.contains(vl.var)) {
					new CompilerError(vl, "Variable " + vl.name + " used before it has been defined.");
				}
			}
		}
	}

	public static void printAll(StringBuilder sb, String location) {
		sb.append("globals\n");
		for (Global g : allGlobals) {
			if (g.getPosition().file.equals(location)) {
				g.print(sb);
			}
		}
		sb.append("endglobals\n");
	}
	
//	public static void printAll(StringBuilder sb, Scope s, String location) {
//		if (s.globalsprinted) {
//			return; 
//		}
//		for (LibraryLink ll : s.requirements) {
//			if (ll.getLib() != null) {
//				printAll(sb, ll.getLib(), location);
//			}
//		}
//		
//		
//		for (Global g : s.GlobalList) {
//			g.print(sb, location);
//		}
//		s.globalsprinted = true;
//		
//		for (Scope c : s.childScopes ) {
//			printAll(sb, c, location);
//		}
//	}
//	
//	public static void printAll(StringBuilder sb, String location) {
//		sb.append("globals\n");
//		printAll(sb, Scope.mainscope, location);
//		sb.append("endglobals\n");
//	}
	
}