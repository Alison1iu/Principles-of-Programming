/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater;

import edu.osu.cse.parser.*;
import edu.osu.cse.token.GrammerToken;


public class Variable extends ComparableNode
        implements Comparable<Variable>
{
  private ID _id = null;
  private boolean _assignment = false;
  private String _name = null;

  /**
   * create a new instance of Variable
   * This is the object which corresponding connect id with java value;
   * @param id 
   * @throws edu.osu.cse.parser.ParserException 
   */
  public Variable(ID id)throws ParserException
  {
    if(id == null) throw new ParserException("missing id token when create Variable object");
    _id = id;
    GrammerToken token = _id.getToken();
    if(token == null || token.getTokenValue() == null) 
      throw new ParserException("incorrect grammer token, missing grammer token value");
    _name = token.getTokenValue();
  }
  
  /**
   * Set variable value, 
   * @param value 
   */
  public void setvalue(int value)
  {
    _assignment = true;
    _value = value;
  }
  /**
   * Get variable name
   * @return 
   */
  public String getName()
  {
    return _name;
  }


  @Override
  public int compareTo(Variable o) 
  {
    if(_name == null) return -1;
    else 
    {
      return _name.compareTo(o._name);
    }
  }
  
  /**
   * 
   * @return 
   * @throws edu.osu.cse.parser.ParserException 
   */
  @Override
  public int getValue() throws ParserException
  {
    if(!_assignment)
    {
      throw new ParserException(errorMessage());
    }
    return super.getValue();
  }
  
  /**
   * the value of current instance is greater than given Comparable node
   * will return 1
   * equal, return 0
   * small, return -1
   * @param a
   * @return 
   * @throws edu.osu.cse.parser.ParserException 
   */
  @Override
  public int compare(ComparableNode a) throws ParserException
  {
    if(!_assignment)
    {
      throw new ParserException(errorMessage());
    }
    return super.compare(a);
//    if(_value > a.getValue()) return 1;
//    else if(_value == a.getValue()) return 0;
//    else return -1;
  }
  
  
  /**
   * check if its value is same as given value
   * @param value
   * @return 
   * @throws edu.osu.cse.parser.ParserException 
   */
  @Override
  public boolean isValue(int value) throws ParserException
  {
    if(!_assignment)
    {
      throw new ParserException(errorMessage());
    }
    return super.isValue(value);
  }
  
  /**
   * Set the id object which use this variable. 
   * this is just allow us to retrieve line no
   * @param id 
   */
  public void setCurrentID(ID id) 
  {
    _id = id;
  }
  
  /**
   * Create error message
   * @return 
   */
  private String errorMessage()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("SEVERE:");
    if(_id != null)
    {
      GrammerToken token = _id.getToken();
      if(token != null)
        builder.append("[line ").append(token.getLineNo()).append("]");
    }
    builder.append(" Use of variable ").append(_name).append(" before initialization.");
    return builder.toString();
  }
  
}
