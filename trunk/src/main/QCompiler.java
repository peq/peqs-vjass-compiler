package main;

import gui.Gui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;


import com.zthread.libmpq.MPQ;
import com.zthread.libmpq.MPQFile;

public class QCompiler {

	static String currentFile = "";
	static Gui gui;
	static Thread guiThread;
	
	public QCompiler(String[] args) {
		// LATER Compiler Klasse mit richtigen Argumenten
	}

	InputStream mapscriptStream;
	
	public void parse() {
		
        //Qjass parser = new Qjass(System.in);
		
		StopWatch stopwatch = new StopWatch();
		
		mapscriptStream = getMapScript();
		gui = new Gui(mapscriptStream, "war3map.j");
		guiThread = new Thread(gui);
		guiThread.start();
		
		//initialize some classes
		Scope.init();
		
		
		//common.j
		currentFile = "common.j";
		{
			String filename =  "common.j";
			System.out.println(stopwatch.printElapsedTime() + "  parsing " + filename);
			FileInputStream fstream = null;
			try {
				fstream = new FileInputStream(filename);
			} catch (FileNotFoundException e2) {
				System.out.println("Could not open file " + filename);
				System.exit(0);
			}
			
			parseStream(fstream);
			
			try {
				fstream.close();
			} catch (IOException e) {
				System.out.println("Could not close file " + filename);
			}
		}	
		
		//blizzard.j
		currentFile = "blizzard.j";
		{
			
			String filename =  "Blizzard.j";
			System.out.println(stopwatch.printElapsedTime() + "  parsing " + filename);
			FileInputStream fstream = null;
			try {
				fstream = new FileInputStream(filename);
			} catch (FileNotFoundException e2) {
				System.out.println("Could not open file " + filename);
				System.exit(0);
			}
			
			parseStream(fstream);
			
			try {
				fstream.close();
			} catch (IOException e) {
				System.out.println("Could not close file " + filename);
			}
		}
		
		//mapscript
		currentFile = "war3map.j";
		
		mapscriptStream = getMapScript();
		
		parseStream(mapscriptStream);
		
		try {
			mapscriptStream.close();
		} catch (IOException e) {
			System.out.println("Could not close file " + currentFile);
		}
		
		System.out.println(stopwatch.printElapsedTime() + "  compiling...");
		
        //compile
        compile();
        System.out.println(stopwatch.printElapsedTime() + "  compiling done...");
        
        
        
        
        // TODO filter out everything which is not from mapscript
        gui.showErrors();
        System.out.println(stopwatch.printElapsedTime() + "  printing...");
        
        //print resulting script:
        print();
        
        System.out.println(stopwatch.printElapsedTime() + "  printing done...");
        
        
        
        System.out.println(stopwatch.printElapsedTime() + "  done.");
	}
	
	InputStream getMapScript() {
		final boolean usemap = false;
		if (usemap) {
			String mapfilename = "test.w3x";
			
			//System.out.println(stopwatch.printElapsedTime() + "  parsing mapscript of " + mapfilename);
			MPQ mpq = null;
			
			try {
				mpq = new MPQ(mapfilename);
			} catch (Exception e3) {
				System.out.println("Could not open map " + mapfilename);
				System.exit(0);
			}
			
			MPQFile mapscript = null;
			
			mapscript = mpq.getFile("war3map.j");
			
			byte[] mapscriptdata = mapscript.getData();
			
			ByteArrayInputStream mapscriptdatastream = new ByteArrayInputStream(mapscriptdata);
			
			mpq.closeArchive();
			
			return mapscriptdatastream;
			
		} else {
			String filename =  "test.j";
			//System.out.println(stopwatch.printElapsedTime() + "  parsing " + filename);
			FileInputStream fstream = null;
			try {
				fstream = new FileInputStream(filename);
			} catch (FileNotFoundException e2) {
				System.out.println("Could not open file " + filename);
				System.exit(0);
			}
			
			return fstream;
		}
	}
	
	
	static Qjass parser = null;
	
	
	
	public void parseStream(InputStream stream) {
		boolean noErrors = false;
		if (parser == null) {
			parser = new Qjass(stream);
		} else {
			Qjass.ReInit(stream);
		}
		
        while (!noErrors) {
                try {
                        Qjass.Input();
                        noErrors = true;
                } catch (TokenMgrError e) {
                        new CompilerError(Qjass.token, "Lexer-Error:\n" + e.getMessage());
                        try {
							Qjass.error_skipto(Qjass.NEWLINE);
						} catch (ParseException e1) {
							new CompilerError(e1, "Parser-Skip-Error:\n" + e.getMessage());
						}
                } catch (ParseException e) {

                        new CompilerError(e, "Parser-Error:\n" + e.getMessage());
                        try {
							Qjass.error_skipto(Qjass.NEWLINE);
						} catch (ParseException e1) {
							new CompilerError(e1, "Parser-Skip-Error:\n" + e.getMessage());
						}
                } catch (Exception e) {
                		new CompilerError(Qjass.token, "unexpected Error:\n" + e.getMessage());
                        System.out.println(" # unexpected Error:");
                        System.out.println(" at " + Qjass.token.image + " - " + new Position(Qjass.token));
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                }
        }
	}
	
	
	public void compile() {
		//process links
		Library.doLibraryLinksQueue();
		Type.doTypeLinksQueue();
		Variable.doVariableLinksQueue();		
		Function.doFunctionLinksQueue();
		
		//compile
		Global.compileAll();
		Function.compileAll();
		Type.compileAll();
		
		//reorder globals
		Global.reorderAll();
		
		//check globals
		Global.check("war3map.j");
		
		
		//reorder functions
		Scope.addInitializerFakeCalls();
		Function.reorderAll();
		
		
		//show errors:
		
		//CompilerError.PrintErrors("common.j");
		//CompilerError.PrintErrors("blizzard.j");
		
	
		
		
	}
	
	public void print() {
		StringBuilder sb = new StringBuilder();
		Global.printAll(sb, "war3map.j");
		Function.printAll(sb, "war3map.j");
		//System.out.print(sb);
		//String outputfilename = "D:/vjassi/testresult.j";
		String outputfilename = "testresult.j";
		
		try {
	        File file = new File(outputfilename);
	    
	        // Create file if it does not exist
	        boolean success = file.createNewFile();
	        if (success) {
	            // File did not exist and was created
	        } else {
	            // File already exists
	        }
	    } catch (IOException e) {
	    	System.out.println("Could not create " + outputfilename + ".");
			System.exit(0);
	    }

		
		
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(outputfilename);
		} catch (FileNotFoundException e) {
			System.out.println("Could not open " + outputfilename + " for writing.");
			System.exit(0);
		}
		OutputStreamWriter out = new OutputStreamWriter(os);
		for (int i=0; i<sb.length(); i++) {
			char c = sb.charAt(i);
			try {
				out.append(c);
			} catch (IOException e) {
				System.out.println("Could not read to file somehow.");
				System.exit(0);
			}
		}
		
		try {
			out.close();
		} catch (IOException e1) {
			System.out.println("Could not close file-stream-writer.");
			System.exit(0);
		}
		
		try {
			os.close();
		} catch (IOException e) {
			System.out.println("Could not close file.");
			System.exit(0);
		}
	}

}
