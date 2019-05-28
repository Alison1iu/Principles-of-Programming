/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater.Operator;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.parser.ParserException;
import java.util.Stack;

/**
 *
 * @author yangl
 */
public class MinusOp 
        implements Operator
{

  /**
   * Consume top 2 element from stack and return the minus result. 
   * @param va
   * @return
   * @throws ParserException 
   */
  @Override
  public int evaluation(Stack<ComparableNode> va) throws ParserException 
  {
    if(va.size() < 2) throw new ParserException("Minus need two value");
    int right = va.pop().getValue();
    int left = va.pop().getValue();
    return left - right;
  }
}
