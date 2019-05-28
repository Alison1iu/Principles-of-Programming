/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater;

import edu.osu.cse.parser.ParserException;



public class ComparableNode 
{
  protected int _value = 0;
  
  /**
   * the value of current instance is greater than given Comparable node
   * will return 1
   * equal, return 0
   * small, return -1
   * @param a
   * @return 
   */
  public int compare(ComparableNode a) throws ParserException
  {
    if(_value > a._value) return 1;
    else if(_value == a._value) return 0;
    else return -1;
  }
  
  /**
   * 
   * @return 
   * @throws java.text.ParseException 
   */
  public int getValue() throws ParserException
  {
    return _value;
  }
  
  /**
   * check if its value is same as given value
   * @param value
   * @return 
   */
  public boolean isValue(int value) throws ParserException
  {
    return _value == value;
  }
}
