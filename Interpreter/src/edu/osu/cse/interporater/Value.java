/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater;

import edu.osu.cse.parser.*;
import edu.osu.cse.token.GrammerToken;

public class Value extends ComparableNode
{
//  public static final Value VALUE_GREATER = new Value(1);
//  public static final Value VALUE_EQUAL = new Value(0);
//  public static final Value VALUE_LESS = new Value(-1);
  
  public static final Value VALUE_TRUE = new Value(1);
  public static final Value VALUE_FALSE = new Value(0);
  
  /**
   * Create a new instance of value from IntNode 
   * @param node
   * @throws ParserException 
   */
  public Value(IntNode node)throws ParserException
  {
    if(node == null) throw new ParserException("IntNode is null, can't create value");
    GrammerToken token = node.getToken();
    if(token == null || token.getTokenValue() == null)
      throw new ParserException("Incorrect grammer token when create value object");
    try
    {
      _value = Integer.parseInt(token.getTokenValue());
    }
    catch(NumberFormatException ex)
    {
    }
  }
  /**
   * Create a new instance of value
   * @param value 
   */
  public Value(int value)
  {
    _value = value;
  }
  
  /**
   * 
   * @param value 
   */
  public void setValue(int value)
  {
    _value = value;
  }
}
