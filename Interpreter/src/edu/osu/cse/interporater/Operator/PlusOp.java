/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater.Operator;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.parser.ParserException;
import java.math.BigInteger;
import java.util.Stack;


public class PlusOp 
        implements Operator
{

  /**
   * Consume top 2 element from stack and return the plus result. 
   * @param va
   * @return
   * @throws ParserException 
   */
  @Override
  public int evaluation(Stack<ComparableNode> va) throws ParserException 
  {
     if(va.size() < 2) throw new ParserException("Plus need two value");
     int right = va.pop().getValue();
     int left = va.pop().getValue();
     
     //check number overflow
      BigInteger bl = new BigInteger(Integer.toString(left));
      BigInteger br = new BigInteger(Integer.toString(right));
      BigInteger value = bl.add(br);
      if(value.longValue() != (left + right))
      {
        throw new ParserException("Calculation value is outside of integer range:"+value.toString() );
      }
     
     return left + right;
  }
  
}
