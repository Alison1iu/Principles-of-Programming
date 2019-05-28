/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater.Operator;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.interporater.Variable;
import edu.osu.cse.parser.ParserException;
import java.util.*;

public class WriterOp 
        implements Operator
{

  /**
   * display a given variable name and value
   * @param va
   * @return
   * @throws ParserException 
   */
  @Override
  public int evaluation(Stack<ComparableNode> va) throws ParserException 
  {
    if(va.size()<0) throw new ParserException("Missing variable when write out");
    
    while(!va.isEmpty())
    {
      ComparableNode node = va.pop();
      if(node != null && node instanceof Variable)
      {
        Variable a = (Variable)node;
        System.out.println(a.getName()+"="+a.getValue());
      }
    }
    return 1;
  }
  
}
