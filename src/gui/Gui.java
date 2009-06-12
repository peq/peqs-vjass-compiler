package gui;

// TODO clean up this code oO

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.Position;
import javax.swing.text.View;

import main.CompilerError;
import main.SyntaxErrorAnalyser;
import main.TabRemoveInputStream;

class ErrorWindow {

	private static UnderlineHighlighter highlighter = new UnderlineHighlighter(Color.red);
	static JFrame f;
	private static JTextPane textPane;
	private static JList errorlist;
	private static JTextPane errorpane;
	static Vector<Integer> linepos;
	private static DefaultListModel errorlistmodel;
	
	
	
	
	private static WindowListener windowListener = new WindowListener() {

		//@Override
		public void windowActivated(WindowEvent e) {
		}

		//@Override
		public void windowClosed(WindowEvent e) {
			System.out.println("window closed.");
			System.exit(0);
		}

		//@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("window closing...");
			System.exit(0);
		}

		//@Override
		public void windowDeactivated(WindowEvent e) {
		}

		//@Override
		public void windowDeiconified(WindowEvent e) {
		}

		//@Override
		public void windowIconified(WindowEvent e) {
		}

		//@Override
		public void windowOpened(WindowEvent e) {
			System.out.println("\nGUI is ready.");
			ready = true;
		}
		
	};
	
	
	private static ListSelectionListener onErrorSelection = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent l) {
				int selected = errorlist.getSelectedIndex();
				CompilerError err = (CompilerError) errorlist.getModel().getElementAt(selected);
				selectError(err);
			}
	};
	private static boolean ready = false;
	static StringBuilder docsb;
	
	public static void create(InputStream instream, String location) {
		System.out.println("Starting gui...");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception evt) {
			System.out.println("Could not init look and feel.");
		}
		
		System.out.println("GUI - Style set...");

		f = new JFrame("peqs vJass wannabe-compiler");

		
		f.addWindowListener(windowListener );
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		
		textPane.setHighlighter(highlighter);

		new UnderlineHighlighter.UnderlineHighlightPainter(Color.red);


		

		errorlistmodel = new DefaultListModel();
		errorlist = new JList(errorlistmodel);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		
	
		f.getContentPane().setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH; 
		c.weighty = 0.5;
		c.insets = new Insets(5,5,5,5);
		
		JScrollPane scroll_textPane = new JScrollPane(textPane);
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		gridbag.setConstraints(scroll_textPane,c);
		f.getContentPane().add(scroll_textPane);
		
		
		errorpane = new JTextPane();
		errorpane.setText("compiling... \nPlease wait.");
		errorpane.setEditable(false);
		final JScrollPane scroll_errorpane = new JScrollPane(errorpane);
		c.gridx = 0;
		c.gridy = 1;
		gridbag.setConstraints(scroll_errorpane,c);
		f.getContentPane().add(scroll_errorpane);
		
		
		JScrollPane scroll_errorlist = new JScrollPane(errorlist);
		c.gridx = 0;
		c.gridy = 2;
		gridbag.setConstraints(scroll_errorlist,c);
		f.getContentPane().add(scroll_errorlist);
		
		

		
		
		
		System.out.println("GUI - elements created...");
		

		

		// TODO optimize loading text
		
		//textPane.setDocument(doc);
//		try {
//			textPane.read(instream, null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		Document doc = textPane.getDocument();
		docsb = new StringBuilder();
		
		linepos = new Vector<Integer>();
		linepos.add(0);
		linepos.add(0);
		
		int ch;
		int i = 0;
		try {
			while ((ch = instream.read()) >= 0) {
				docsb.append((char)ch);
				if (ch == '\n') {
					linepos.add(i);
				} 
				i++;
			} 
		} catch (IOException e2) {
			// should never happen
			e2.printStackTrace();
		}
		
		//add some lines for errors near to the end
		linepos.add(i);
		linepos.add(i);
		linepos.add(i);
		linepos.add(i);
		
		try {
			doc.insertString(0, docsb.toString(), null);
		} catch (BadLocationException e) {
			// should never happen
			e.printStackTrace();
		}
		
		
		
		System.out.println("GUI - code read...");
		


//		f.pack();
		f.setSize(800, 500);
		
		f.setVisible(true);
		System.out.println("GUI - setVisible(true)...");
	}

	public static void ready(String location) {
//		Vector<CompilerError> errors = new Vector<CompilerError>();
		
		
		System.out.print("waiting for GUI ");
		
		//wait until gui is ready...
		while (!ready ) {
			try {
				System.out.print(".");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("could not sleep");
			}
			
		}
		f.setVisible(false);
		
		
		for (CompilerError err : CompilerError.errors) {
        	if (err.isSyntaxError) {
    			main.Position pos = err.getPosition();
    			String line = "";
    			try {
    				line = ErrorWindow.docsb.substring(
    						ErrorWindow.linepos.get(pos.rowstart), ErrorWindow.linepos.get(pos.rowend+1));
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Could not get linepos." + pos);
					continue;
				}
    			new SyntaxErrorAnalyser(err, line);    			
    		}
        }
		
		
		
		int i=0;
		Vector<CompilerError> errorvec = new Vector<CompilerError>();
		for (CompilerError err: CompilerError.errors) {
			if (err.getPosition().file.equals(location)) {
				
				//errors.add(err);
				//System.out.println("added Error - " + err);
//				errorlistmodel.add(i, err);
				errorvec.add(err);
				i++;
			}
		}
		errorlist.setListData(errorvec);
		errorlist.addListSelectionListener(onErrorSelection);
		
		if (i>0) {
			errorlist.setSelectedIndex(0);
			f.setVisible(true);
		} else {
			System.exit(0);
		}
		f.setVisible(true);
	}
	
	protected static void selectError(CompilerError err) {
		

		
		Document d = errorpane.getEditorKit().createDefaultDocument();
		try {
			d.insertString(0, err.toString(), null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		errorpane.setDocument(d);
		
		
		main.Position pos = err.getPosition();
		
		int start=0;
		int end=0;
		try {
			start = Math.min(linepos.get(pos.rowstart) + pos.columnstart, linepos.get(pos.rowstart+1) - 2);
			end = Math.min(linepos.get(pos.rowend) + err.getPosition().columnend + 1, linepos.get(pos.rowend+1));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("The line of the error could not be found.");
		}
			
			
		if (end <= start) {
			end++;
		}
		
		
		
		try {
			int scrollpos = 0;
			try {
				scrollpos = Math.max(linepos.get(pos.rowstart-2), 0);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("The line of the error could not be found.");
			}
			textPane.scrollRectToVisible(textPane.modelToView(scrollpos));
			try {
				scrollpos = Math.min(linepos.get(pos.rowstart+5), linepos.get(linepos.size()-1));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("The line of the error could not be found.");
			}
			textPane.scrollRectToVisible(textPane.modelToView(scrollpos));

//			textPane.scrollRectToVisible(textPane.modelToView(start));

		} catch (BadLocationException e) {
			System.out.println("could not scroll.");
		} 
//		catch (Error e) {
//			System.out.println("could not scroll - error.");
//		}
//		catch (Exception e) {
//			System.out.println("could not scroll - exception.");
//		}
		
		try {
			highlighter.removeAllHighlights();
			highlighter.addHighlight(start, end);
		} catch (BadLocationException e) {
			// Nothing to do
		}
		
	}

	
}



class UnderlineHighlighter extends DefaultHighlighter {
	public UnderlineHighlighter(Color c) {
		painter = (c == null ? sharedPainter : new UnderlineHighlightPainter(c));
	}

	// Convenience method to add a highlight with
	// the default painter.
	public Object addHighlight(int p0, int p1) throws BadLocationException {
		return addHighlight(p0, p1, painter);
	}

	public void setDrawsLayeredHighlights(boolean newValue) {
		// Illegal if false - we only support layered highlights
		if (newValue == false) {
			throw new IllegalArgumentException(
					"UnderlineHighlighter only draws layered highlights");
		}
		super.setDrawsLayeredHighlights(true);
	}

	// Painter for underlined highlights
	public static class UnderlineHighlightPainter extends
	LayeredHighlighter.LayerPainter {
		public UnderlineHighlightPainter(Color c) {
			color = c;
		}

		public void paint(Graphics g, int offs0, int offs1, Shape bounds,
				JTextComponent c) {
			// Do nothing: this method will never be called
		}

		public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds,
				JTextComponent c, View view) {
			g.setColor(color == null ? c.getSelectionColor() : color);

			Rectangle alloc = null;
			if (offs0 == view.getStartOffset() && offs1 == view.getEndOffset()) {
				if (bounds instanceof Rectangle) {
					alloc = (Rectangle) bounds;
				} else {
					alloc = bounds.getBounds();
				}
			} else {
				try {
					Shape shape = view.modelToView(offs0,
							Position.Bias.Forward, offs1,
							Position.Bias.Backward, bounds);
					alloc = (shape instanceof Rectangle) ? (Rectangle) shape
							: shape.getBounds();
				} catch (BadLocationException e) {
					return null;
				}
			}

			FontMetrics fm = c.getFontMetrics(c.getFont());
			int baseline = alloc.y + alloc.height - fm.getDescent() + 1;
			g.drawLine(alloc.x, baseline, alloc.x + alloc.width, baseline);
			g.drawLine(alloc.x, baseline + 1, alloc.x + alloc.width,
					baseline + 1);

			return alloc;
		}

		protected Color color; // The color for the underline
	}

	// Shared painter used for default highlighting
	protected static final Highlighter.HighlightPainter sharedPainter = new UnderlineHighlightPainter(
			null);

	// Painter used for this highlighter
	protected Highlighter.HighlightPainter painter;
}



public class Gui implements Runnable {
	String location;
	InputStream instream;
	
	
	
	public Gui(InputStream pinstream, String plocation) {
		location = plocation;
		instream = new TabRemoveInputStream(pinstream, 8);
		
	}

	public void showErrors() {
		
		
		ErrorWindow.ready(location);
	}
	
	public void run() {
		ErrorWindow.create(instream, location);
	}
}
