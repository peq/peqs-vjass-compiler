package main;

import gui.Gui;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class CompilerError implements WithPosition {
	String message;
	private Position p;

	
	public boolean isSyntaxError = false;
	
	static public List<CompilerError> errors = new LinkedList<CompilerError>();
	
	public CompilerError(WithPosition problem, String pmessage) {
		message = pmessage;
		if (problem != null) {
			p=problem.getPosition();
			if (p == null) {
				p = (new Position(0,0,0,0));
			}
		} else {
			p = (new Position(0,0,0,0));
		}
		errors.add(this);
	}

	public CompilerError(Token token, String pmessage) {
		message = pmessage;
		p = new Position(token);
		errors.add(this);
	}

	public CompilerError(ParseException e, String pmessage) {
		message = pmessage;
		Token next = e.currentToken.next;
		p = new Position(next.beginLine, next.beginColumn, next.endLine, next.endColumn);
		
		errors.add(this);
	}

	static void PrintErrors() {
		for (CompilerError err : errors) {
			System.out.println(err.p.toString() + " --------\n" + err.message);
		}
	}
	
//	@Override
	public Position getPosition() {
		return p;
	}

//	@Override
	public void setPosition(Position pp) {
		p=pp;		
	}

//	public static void PrintErrors(InputStream instream, String location) {
//		//Gui gui = new Gui(instream, location);
//		QCompiler.gui.showErrors();
//		/*for (CompilerError err : errors) {
//			if (err.getPosition().file.equals(location)) {
//				System.out.println(err.getPosition().toString() + " --------\n" + err.message);
//			}
//		}
//		*/
//	}

	public String toString() {
		String msg = message;
		
		return getPosition().toString() + "   " +msg;
	}




}
