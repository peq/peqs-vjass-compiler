package main;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ExpressionCollector {

	Expression start;
	
	public ExpressionCollector(Expression pstart) {
		start = pstart;
	}
	

	
	public List<VariableLink> getChildVariabeLinks() {
		List<VariableLink> l = new LinkedList<VariableLink>();
		Stack<Expression> s = new Stack<Expression>();
		
		s.push(start);
		
		while (!s.empty()) {
			Expression current = s.pop();
			if (current instanceof ExprVariable) {
				l.add(((ExprVariable)current).varlink);
			}
			for (Expression child : current.getChilds()) {
				s.push(child);
			}
			
		}
		return l;
	}

}
