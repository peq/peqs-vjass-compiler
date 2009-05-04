package main;

import java.util.List;
import java.util.ListIterator;

public class StatementLoop extends StatementAbstract {
	List<Statement> body;
	
	
	public StatementLoop(List<Statement> pbody, List<Statement> sl) {
		super(null, sl);
		body = pbody;
		if (pbody.size() > 0) {
			pos = new Position(new Position(pbody.get(0).getPosition()), new Position(pbody.get(pbody.size()-1).getPosition()));
		} else {
			pos = new Position(Qjass.token);
			new CompilerError(this, "Loop without content.");
		}
	}

	
	public Statement compile() {
		boolean hasExitwhen = false;
		ListIterator<Statement> it = body.listIterator();
		while (it.hasNext()) {
			Statement s = it.next();
			s = s.compile();
			if (s instanceof StatementExitwhen) {
				hasExitwhen = true;
			}
			if (s instanceof StatementReturn) {
				hasExitwhen = true;
			}
			it.set(s);
		}
		if (!hasExitwhen) {
			// TODO count exitwhens in conditions
			new CompilerError(this, "Loop without exitwhen (exitwhens in conditions do not count yet).");
		}
		
		return this;
	}

	
	public void print(StringBuilder sb) {
		sb.append("loop\n");
		for (Statement s : body) {
			s.print(sb);
		}
		sb.append("endloop\n");
	}


}
