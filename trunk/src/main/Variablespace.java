package main;

//an VariableSpace is a namespace where variables can be defined (scopes, functions ...)
public interface Variablespace {
	Variablespace getParent();
	Variable searchVariable(VariableLink variableLink);
	Scope getScope();
}
