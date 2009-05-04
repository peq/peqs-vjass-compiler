package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Scope implements Variablespace {
	
	String name;
	FunctionLink init;
	Scope parentScope;
	List<Scope> childScopes = new LinkedList<Scope>();
	
	
	List<LibraryLink> requirements = new LinkedList<LibraryLink>(); 
	
	boolean globalsprinted = false;
	Map<String,Global> GlobalMap = new HashMap<String,Global>();
	List<Global> GlobalList = new LinkedList<Global>(); 
	
	Map<String,Function> FunctionMap = new HashMap<String,Function>();
	List<Function> FunctionList = new LinkedList<Function>(); 
	
	Map<String,Type> TypeMap = new HashMap<String,Type>();
	List<Type> TypeList = new LinkedList<Type>(); 
	
	static List<Function> initializers = new LinkedList<Function>();
	
	
	
	public static Scope mainscope = null;
	public static Scope current = null;
	
	static void init() {
		mainscope = new Scope("mainscope");
		current = mainscope;
	}
	
	public Scope(String pname) {
		name = pname;
		if (current != null) {
			parentScope = current;
			parentScope.childScopes.add(this);
		}
		current = this;
		Variable.currentVariablespace = this;
	}

	public Scope(String pname, String pinitname) {
		this(pname);
		init = new FunctionLink(pinitname, this, new Position(Qjass.token));
	}

	void addFunction(Function f) {
		if (f.scopemodifier == Scopemodifier.ScopeDefault) {
			Scope.mainscope.FunctionList.add(f);
			Scope.mainscope.FunctionMap.put(f.name, f);
		} else if (f.scopemodifier == Scopemodifier.ScopePrivate) {
			FunctionList.add(f);
			FunctionMap.put(f.name, f);
		} else if (f.scopemodifier == Scopemodifier.ScopePublic) {
			Scope.mainscope.FunctionList.add(f);
			Scope.mainscope.FunctionMap.put(f.scope.name + "_" + f.name, f);
			FunctionMap.put(f.name, f);
		}
		
	}


	void addGlobal(Global g) {
		if (g.scopemodifier.equals(Scopemodifier.ScopeDefault)) {
			Scope.mainscope.GlobalMap.put(g.name, g);
//			Scope.mainscope.GlobalList.add(g);
		} else if (g.scopemodifier.equals(Scopemodifier.ScopePrivate)) {
			GlobalMap.put(g.name, g);
//			GlobalList.add(g);
		} else if (g.scopemodifier.equals(Scopemodifier.ScopePublic)) {
			Scope.mainscope.GlobalMap.put(name + "_" + g.name, g);
//			Scope.mainscope.GlobalList.add(g);
			GlobalMap.put(g.name, g);
		}
		GlobalList.add(g);
		
	}
	
	void addType(Type t) {
		if (t.scopemodifier.equals(Scopemodifier.ScopeDefault)) {
			Scope.mainscope.TypeMap.put(t.name, t);
			Scope.mainscope.TypeList.add(t);
		} else if (t.scopemodifier.equals(Scopemodifier.ScopePrivate)) {
			TypeMap.put(t.name, t);
			TypeList.add(t);
		} else if (t.scopemodifier.equals(Scopemodifier.ScopePublic)) {
			Scope.mainscope.TypeMap.put(name + "_" + t.name, t);
			Scope.mainscope.TypeList.add(t);
			TypeMap.put(t.name, t);
		}
		
	}
	
	public Function searchFunction(FunctionLink functionLink) {
		return FunctionMap.get(functionLink.name);
	}

	
	public Variable searchVariable(VariableLink variableLink) {
		return GlobalMap.get(variableLink.name);
	}

	
	public Variablespace getParent() {
		return parentScope;
	}

	public Type searchType(TypeLink typeLink) {
		return TypeMap.get(typeLink.name);
	}

	
	public Scope getScope() {
		return this;
	}

	public static void addInitializerFakeCalls(Scope s) {
		Function main = Function.functionMain;
		assert main != null;
		
		if (s.init != null) {
			Function sinit = s.init.getFunc();
			if (sinit != null) {
				StatementCall call = new StatementCall( new ExprFunction(sinit, main), main.body);
				call.compileMethod = StatementCall.CompileMethod.execute;
				if (main.body != null) {
					main.body.add(call);
				}
			}
		}
		
		for (Scope c : s.childScopes) {
			addInitializerFakeCalls(c);
		}
		
		
	}
	
	public static void addInitializerFakeCalls() {
		if (Function.functionMain == null) {
			new CompilerError(Qjass.token, "no main function found");
			return;
		}
		addInitializerFakeCalls(Scope.mainscope);
	}
	
	public String toString() {
		return "Scope " + name;
	}

	public Type searchType(String typename) {
		return TypeMap.get(typename);
	}
}
