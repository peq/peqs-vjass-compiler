package main;

import java.util.List;


public interface Expression extends WithPosition,Compilable {
	//this is just a basic expression
	
	//type of this expression
	Type getType();
	
	//is SubTypeOf p?
	boolean isSubTypeOf(Type p);
	
	//converts the expression to a String:
	void print(StringBuilder sb);
	
	//Checks Types and such stuff, returns false on error:
	//boolean check() throws Exception;
	
	//Eliminates vJass-Constructs and simplifies Expressions
	//also detects type-errors and stuff like that
	Expression compile();

	List<Expression> getChilds();
}
