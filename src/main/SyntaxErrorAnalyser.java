/**
 * 
 */
package main;

import main.CompilerError;


public class SyntaxErrorAnalyser {
	private CompilerError err;
	private String line;
	
	
	public SyntaxErrorAnalyser(CompilerError perr, String pline) {
		err = perr;
		line = pline;
		analyse();
	}
	
	
	private void analyse() {
		// Parentheses 
		if (analyseParentheses('(', ')')) {
			return;
		}
		if (analyseParentheses('[', ']')) {
			return;
		}
				
	}


	private boolean analyseParentheses(char open, char close) {
		// CHECK ignore strings and comments implemented right?
		System.out.println("analyseParentheses " + open + " , " + close );
		System.out.println(line);
		boolean inString = false;
		int count = 0;
		char lastc = 0;
		char c = 0;
		for (int i=0; i < line.length(); i++) {
			lastc = c;
			c = line.charAt(i);
			
			if (inString) {
				if (c == '"' && lastc != '\\') {
					inString = false;
				}
			} else {
			
				if (c == '/' && lastc == '/') {
					//comment
					break;
				} else if (c == '"') {
					inString = true;
				} else if (c == open) {
					count++;
				} else	if (c == close) {
					count--;
					if (count < 0) {
						err.message = "Encountered \""+close+"\" without \""+open+"\".\n";
						err.getPosition().columnstart=i;
						err.getPosition().columnend=i+1;				
						return true;
					}
				}
			}
		}
		if (count > 0) {
			err.message = "Missing " + new LingualNumber(count) + " \""+close+"\"\n";
			err.getPosition().columnstart=line.length()-2;
			err.getPosition().columnend=line.length()-1;
		}
		assert count == 0;
		return false;
	}


	public String toString() {

		return err.toString();

	}
}
