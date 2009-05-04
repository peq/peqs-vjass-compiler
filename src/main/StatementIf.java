package main;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class StatementIf extends StatementAbstract {
	Expression cond;
	List<Statement> body;
	List<Expression> elseifconds;
	List<List<Statement>> elseifbodys;
	List<Statement> elsebody;
	
	
	public StatementIf(Expression pcond, List<Statement> pbody,
			List<Expression> pelseifconds, List<List<Statement>> pelseifbodys,
			List<Statement> pelsebody, List<Statement> ls) {
		super(null, ls);
		cond = pcond;
		body = pbody;
		elseifconds = pelseifconds;
		elseifbodys = pelseifbodys;
		elsebody = pelsebody;
		if (pelsebody != null && pelsebody.size() > 0) {
			pos = new Position(pcond.getPosition(), pelsebody.get(pelsebody.size()-1).getPosition());
		} else {
			pos = new Position(pcond.getPosition());
		}
	}

	
	public Statement compile() {
		
		cond = cond.compile();
		if (!cond.isSubTypeOf(Type.tBoolean)) {
			new CompilerError(cond, "The if condition is not of type boolean.");
		}
		
		if (body != null) {
			ListIterator<Statement> it = body.listIterator();
			while (it.hasNext()) {
				Statement s = it.next();
				it.set(s.compile());
			}
		}
		if (elseifconds != null){
			ListIterator<Expression> it = elseifconds.listIterator();
			while (it.hasNext()) {
				Expression e = it.next();
				e = e.compile();
				if (!e.isSubTypeOf(Type.tBoolean)) {
					new CompilerError(e, "The elseif condition is not of type boolean.");
				}
				it.set(e);
			}
		}
		if (elseifbodys != null) {
			for (List<Statement> ls : elseifbodys) {
		
				ListIterator<Statement> it = ls.listIterator();
				while (it.hasNext()) {
					Statement s = it.next();
					it.set(s.compile());
				}
			}
		}
		if (elsebody != null){
			ListIterator<Statement> it = elsebody.listIterator();
			while (it.hasNext()) {
				Statement s = it.next();
				it.set(s.compile());
			}
		}
		return this;
	}

	
	public void print(StringBuilder sb) {
		sb.append("if ");
		cond.print(sb);
		sb.append(" then\n");
		for (Statement s : body) {
			s.print(sb);
		}
		int i = 0;
		for (Expression c : elseifconds) {
			
			sb.append("elseif ");
			c.print(sb);
			sb.append(" then\n");
			for (Statement s : elseifbodys.get(i)) {
				s.print(sb);
			}
			i++;
		}
		
		if (elsebody!=null && elsebody.size() > 0) {
			sb.append("else\n");
			for (Statement s : elsebody) {
				s.print(sb);
			}
		}
		sb.append("endif\n");
	}



}
