package main;

import java.io.IOException;
import java.io.InputStream;

/** replaces every tab with a single space */
public class TabRemoveInputStream extends InputStream {
	InputStream instream;
	int spacespertab;
	
	int toDo = 0;
	
	public TabRemoveInputStream(InputStream pinstream, int pspacespertab) {
		instream = pinstream;
		spacespertab = pspacespertab;
		toDo = 0;
	}
	
	@Override
	public int read() throws IOException {
		int c;
		if (toDo <= 0) {
			c = instream.read();
			if (c == '\t') {
				c = ' ';
				toDo = spacespertab - 1;
			}
		} else {
			c = ' ';
			toDo--;
		}
		return c;
	}

}
