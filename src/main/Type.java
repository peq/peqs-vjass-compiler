package main;

import java.util.LinkedList;
import java.util.Queue;

public class Type {
	public static Type tBoolean = new Type("boolean", null, Scopemodifier.ScopeDefault);


	/** the native type real */
	public static Type tReal = new Type("real", null, Scopemodifier.ScopeDefault);
	/** the native type integer */
	public static Type tInteger = new Type("integer", tReal, Scopemodifier.ScopeDefault);
	/** the native type string */
	public static Type tString = new Type("string", null, Scopemodifier.ScopeDefault);
	/** the native type handle */
	public static Type tHandle = new Type("handle", null, Scopemodifier.ScopeDefault);
	/** the native type code */
	public static Type tCode = new Type("code", null, Scopemodifier.ScopeDefault);
	
	
	/** All structs will extend this abstract type. */
	public static Type tStruct = new Type("struct", tInteger, Scopemodifier.ScopeDefault);
	/** unknown type */
	public static Type tUnknown =  new Type("<unknown type>", null, Scopemodifier.ScopeDefault);
	/** nothing pseudo-type */
	public static Type tNothing =  new Type("nothing", null, Scopemodifier.ScopeDefault);

	
	String name;
	Scope scope;
	boolean ispublic;
	boolean isprivate;
	boolean custom;
	Type superType;



	public Scopemodifier scopemodifier = Scopemodifier.ScopeDefault;
	
	Type(String pname, Type psuperType, Scopemodifier pscopemodifier) {
		name = pname;
		superType = psuperType;
		scope = Scope.current;
		if (pscopemodifier.equals(Scopemodifier.ScopePrivate)) {
			scope.TypeList.add(this);
			scope.TypeMap.put(name, this);
		} else if (pscopemodifier.equals(Scopemodifier.ScopeDefault)) {
			Scope.mainscope.TypeList.add(this);
			Scope.mainscope.TypeMap.put(name, this);
		} else if (pscopemodifier.equals(Scopemodifier.ScopePublic)) {
			Scope.mainscope.TypeList.add(this);
			Scope.mainscope.TypeMap.put(scope.name + "_" + name, this);
			scope.TypeList.add(this);
			scope.TypeMap.put(name, this);
		}
	}
	
	boolean isSubTypeOf(Type t){
		if (this.superType == null) {
			return this == t;
		} else {
			return this == t || this.superType.isSubTypeOf(t);
		}
	}
	
	boolean isSuperTypeOf(Type t){
		return t.isSubTypeOf(this);
	}
	
	static Type getSmallestCommonSuperType(Type a, Type b) {
		Type r = a;
		while (r != null && !r.isSuperTypeOf(b)) {
			r = r.superType;
		}
		if (r==null) {
			r = Type.tUnknown;
		}
		return r;
	}

	static Queue<TypeLink> typelinks = new LinkedList<TypeLink>();


	
	
	public static void getLink(TypeLink typeLink) {
		typeLink.type = Type.search(typeLink);
		if (typeLink.type == null) {
			typelinks.add(typeLink);
		}
		
	}

	private static Type search(TypeLink typeLink) {
		Scope currentscope = typeLink.scope;
		while (currentscope != null) {
			Type t = currentscope.searchType(typeLink);
			if (t != null) {
				return t;
			}
			currentscope = currentscope.parentScope;
		}
		return null;
	}
	
	/** only searches mainscope */
	static Type search(String typename) {
		return Scope.mainscope.searchType(typename);
	}
	
	
	static void doTypeLinksQueue() {
		for (TypeLink tl : typelinks) {
			tl.type = search(tl);
			if (tl.type == null) {
				new CompilerError(tl, tl.name + " cannot be resolved to a type.");
			}
		}
	}

	public void print(StringBuilder sb) {
		sb.append(name);
		
	}

	public static void compileAll() {
		// TODO Auto-generated method stub
		
	}


}
