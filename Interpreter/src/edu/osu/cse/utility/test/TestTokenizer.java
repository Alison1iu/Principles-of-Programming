package edu.osu.cse.utility.test;

import edu.osu.cse.parser.ParserException;
import edu.osu.cse.token.Tokenizer;
import edu.osu.cse.token.GrammerToken;
import java.util.*;
import java.io.*;

public class TestTokenizer 
{
	public static final String TEST = "-test";
	
	/**
	 * This will print the usage requirements and exit.
	 */
	private static void usage()
	{
	  System.out.println("Usage: java TestTokenizer [program1.core][-test]");
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args)
	{
		//testExtractImageFromForm();
	    //testDrawingSingleObject();
	    boolean _runTest = false;
	    String _testFile = null;


	    for (int i = 0; i < args.length; i++)
	    {
	        if (args[i].equals(TEST))
	        {
	          _runTest = true;
	          i++;
	          if (i >= args.length)
	          {
	            break;
	          }
	          else
	          {
	            _testFile = args[i];
	          }
	        }
	        else
	        {
	          if (_testFile == null)
	          {
	        	  _testFile = args[i];
	          }
	        }       
	    }

	    ArrayList<String> sourceFile = new ArrayList<>();
	    if (_runTest)
	    {
	      File file = new File(".");
	      File[] filelist = file.listFiles();
	      for (int i = 0; i < filelist.length; i++)
	      {
	        String filename = filelist[i].getName();
	        if (filename.endsWith(".core"))
	        {
	          sourceFile.add(filename);
	        }
	      }
	    }
	    else
	    {
	      if (_testFile == null)
	      {
	        usage();
	      }
	      else
	      {
	        sourceFile.add(_testFile);
	      }
	    }
	    
	    OutputStream normalout = null;
	    OutputStream errorout = null;
	    
	    boolean closeoutput = false;
	    try
	    {	//output the result to file
	    	normalout = new FileOutputStream("TestResult.txt");
	    	errorout = normalout;
	    	closeoutput = true;
//	    	output the result to standard console
//	    	normalout = System.out;
//	    	errorout = System.err;
//	    	set this flag to avoid close the standard output. 	    	
//	    	closeoutput = false;
		    
		    for(int i=0; i<sourceFile.size(); i++)
		    {
		    	String file = sourceFile.get(i);
		    	try 
		        {
		          Tokenizer tk = new Tokenizer(file);
		          while(tk.currentToken() != null)
		          {
		            GrammerToken token = tk.currentToken();
		            if(token.getTokenID() > Tokenizer.TOKEN_EOF)
		            {
		              token.writeError(errorout);
		              break;
		            }
		            else if(token.getTokenID() == Tokenizer.TOKEN_EOF)
		            {
		              token.writeout(normalout);
		              break;
		            }
		            else
		            {
		              token.writeout(normalout);
		            }
		            tk.nextToken();
		          }
		          tk.close();
		        } 
		        catch (IOException ex) 
		        {
		          usage();
		        }
                catch(ParserException e)
                {
                  System.out.println(e.getMessage());
                }
		    }
	    }
	    catch(IOException ex)
	    {
	    	
	    }
	    finally
	    {
	    	try
	    	{
	    		if(closeoutput)
	    		{
	    			normalout.close();
	    		}
	    	}
	    	catch(IOException ex)
	    	{
	    		
	    	}
	    }
	}
}
