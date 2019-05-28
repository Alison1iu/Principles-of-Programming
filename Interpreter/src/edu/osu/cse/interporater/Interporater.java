/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater;

import edu.osu.cse.parser.*;
import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class Interporater 
{

  
  //Global Varaible defination, this is the container for defined variable. 
  private HashMap<String, Variable> _globalVariable = new HashMap<>();
  
  //param stack, all node will use this stack to store and receive data 
  private Stack<ComparableNode> _param = new Stack<>();
  
  
  /**
   * Create a new instance of Interporater 
   */
  public Interporater()
  {
  }
  /**
   * Check if this variable is already defined. 
   * @param v
   * @return 
   */
  public boolean hasVariable(Variable v)
  {
    return _globalVariable.containsKey(v.getName());
  }
  
  /**
   * Check if a given variable's name is already defined. 
   * @param v
   * @return 
   */
  public boolean hasVariable(String v)
  {
    return _globalVariable.containsKey(v);
  }
  
  /**
   * Push a value into parameter stack
   * @param value
   * @throws ParserException 
   */
  public void pushValue(Value value) throws ParserException
  {
    _param.push(value);
  }
  
  /**
   * push a variable into parameter stack
   * @param v
   * @throws ParserException 
   */
  public void pushVariable(Variable v) throws ParserException
  {
    _param.push(v);
  }
  
  /**
   * pop the top element from parameter stack 
   * @return 
   */
  public ComparableNode popParamStack()
  {
    return _param.pop();
  }
  
  /**
   * peek the top element from parameter stack 
   * @return 
   */
  public ComparableNode peekParamStack()
  {
    return _param.peek();
  }
  
  /**
   * Get parameter stack
   * @return 
   */
  public Stack<ComparableNode> getParamStack()
  {
    return _param;
  }
  
  /**
   * Register variable this should only be called by Decl Node
   * @param va 
   * @throws edu.osu.cse.parser.ParserException 
   */
  public void registerVariable(Variable va)throws ParserException
  {
    if(va == null) return;
    if(_globalVariable.containsKey(va.getName()))
    {
      throw new ParserException("SEVERE: try to define duplicate variable,"+ va.getName()+" is already defined");
    }
    _globalVariable.put(va.getName(), va);
  }
  
  /**
   * Retrieve a variable which corresponding to a ID token. 
   * If not found, exception will be thrown. 
   * @param id
   * @return 
   * @throws edu.osu.cse.parser.ParserException 
   */
  public Variable getVariable(ID id) throws ParserException
  {
    GrammerToken token = id.getToken();
    if(token == null)
    {//error message;
      throw new ParserException("Null pointer exception when create Identifier");
    }
    String name = token.getTokenValue();
    if(name == null) 
    {//error message;
      throw new ParserException("SEVERE: [Line "+token.getLineNo()+"] Identifier is null");
    }
    Variable va = _globalVariable.get(name);
    if(va == null)
    {
      throw new ParserException("SEVERE: [Line "+token.getLineNo()+"] Using undeclared variable "+ name);
    }
    else va.setCurrentID(id);
    return va;
  }
   
  
  /**
   * This will print the usage requirements and exit.
   */
  private static void usage()
  {
      System.out.println("Usage: java Interporater program1.core");
  }
  
  
  /**
   * main method
   * @param argv 
   */
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
          Interporater inter = new Interporater();
          Tokenizer tk = new Tokenizer(argv[0]);
          Prog treeRoot = new Prog(null, inter);
          treeRoot.createTree(tk);
          treeRoot.evaluation(inter);
          tk.close();
        }
        catch(ParserException e)
        {
          System.out.println(e.getMessage());
        }
        catch (IOException ex) 
        {
          System.out.println("Incorrect file! please check your test file");
          usage();
        }
      }
	}
}
