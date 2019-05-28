package edu.osu.cse.parser;

import java.io.IOException;

import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;

public class Parse 
{
	/**
	 * This will print the usage requirements and exit.
	 */
	private static void usage()
	{
		System.out.println("Usage: java Parse program1.core");
	}
	
	public static void main(String [] argv)
	{
		if(argv == null || argv.length < 1)
	    {
	      usage();
	    }
	    else
	    {
	      try 
	      {
	        Tokenizer tk = new Tokenizer(argv[0]);
	        Prog treeRoot = new Prog(null);
	        treeRoot.parsingValue(tk);
	        treeRoot.writeoutNode(System.out);
	        tk.close();
	      }
          catch(ParserException e)
          {
            System.out.println(e.getMessage());
          }
	      catch (IOException ex) 
	      {
	    	System.out.println("Incorrect file! please check your test file");
	    	ex.printStackTrace();
	        usage();
	      }
	    }
	}
}
