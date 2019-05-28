/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.utility.test;

import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.parser.ParserException;
import edu.osu.cse.parser.Prog;
import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class TestInterpolation 
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
	        if (filename.endsWith(".txt"))
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
//	    	normalout = new FileOutputStream("TestResult.txt");
//	    	errorout = normalout;
//	    	closeoutput = true;
//	    	output the result to standard console
	    	normalout = System.out;
	    	errorout = System.err;
//	    	set this flag to avoid close the standard output. 	    	
	    	closeoutput = false;
		    
		    for(int i=0; i<sourceFile.size(); i++)
		    {
              try
              {
		    	String file = sourceFile.get(i);
                System.out.println("Test file:" + file);
		    	Interporater inter = new Interporater();
                Tokenizer tk = new Tokenizer(file);
                Prog treeRoot = new Prog(null, inter);
                treeRoot.createTree(tk);
                treeRoot.evaluation(inter);
                tk.close();
              }
              catch(ParserException ex)
              {
                System.out.println(ex.getMessage());
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
