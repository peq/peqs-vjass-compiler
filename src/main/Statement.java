package main;

import java.util.List;

public interface Statement extends Compilable, WithPosition {
	Function parentfunction = null;
	Statement parentstatement = null;
	
	Statement compile();
	
	void insertBefore(Statement s);
	void insertAfter(Statement s);
	void insertBefore(List<Statement> sl);
	void insertAfter(List<Statement> sl);
	
}
