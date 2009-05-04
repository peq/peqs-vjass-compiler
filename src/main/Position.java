package main;

public class Position {
	public String file ="";
	public int columnstart;
	public int columnend;
	public int rowstart;
	public int rowend;
	
	Position(Position p) {
		file = p.file;
		columnstart = p.columnstart;
		columnend = p.columnend;
		rowstart = p.rowstart;
		rowend = p.rowend;
	}
	
	Position(Token t) {
		file = QCompiler.currentFile;
		columnstart = t.beginColumn;
		columnend = t.endColumn;
		rowstart = t.beginLine;
		rowend = t.endLine;
	}
	
	Position(Position p, Position q) {
		file = p.file;
		//start:
		if (p.rowstart < q.rowstart) {
			rowstart = p.rowstart;
			columnstart = p.columnstart;
		} else if (p.rowstart > q.rowstart) {
			rowstart = q.rowstart;
			columnstart = q.columnstart;
		} else {
			rowstart = q.rowstart;
			columnstart = Math.min(p.columnstart, q.columnstart);
		}
		//end: 
		if (p.rowend > q.rowend) {
			rowend = p.rowend;
			columnend = p.columnend;
		} else if (p.rowend < q.rowend) {
			rowend = q.rowend;
			columnend = q.columnend;
		} else {
			rowend = q.rowend;
			columnend = Math.max(p.columnend, q.columnend);
		}
	}

	public Position(WithPosition a, WithPosition b) {
		this(a.getPosition(), b.getPosition());
	}
	
	public Position(int i, int j, int k, int l) {
		file = QCompiler.currentFile;
		columnstart = j;
		columnend = l;
		rowstart = i;
		rowend = k;
	}

	public Position(WithPosition a) {
		this(a.getPosition());
	}

	public String toString() {
		return file + "  Line " + rowstart + ":" + columnstart + " to " +  rowend + ":" + columnend;		
	}

	public static Position WholeLine(Position ppos) {
		Position p = new Position(ppos);
		p.columnstart = 0;
		// OPTIMIZE get whole line
		p.columnend = 100;
		return p;
	}
}
