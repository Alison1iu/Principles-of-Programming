/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater.Operator;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.parser.ParserException;
import java.util.Stack;


public class CompOperator 
        implements Operator
{
  private String _operator = null;
  
  public CompOperator(String name)
  {
    _operator = name;
  }

  /**
   * if the comp operator is true return 1
   * else return false;
   * @param va
   * @return
   * @throws ParserException 
   */
  @Override
  public int evaluation(Stack<ComparableNode> va) throws ParserException 
  {
    if(va.size() < 2) throw new ParserException("CompOperator need two value");
    int right = va.pop().getValue();
    int left = va.pop().getValue();
    
    if("!=".equals(_operator))
    {
      if(left != right) return 1;
      else return 0;
    }
    else if("==".equals(_operator))
    {
      if(left == right) return 1;
      else return 0;
    }
    else if("<".equals(_operator))
    {
      if(left < right) return 1;
      else return 0;
    }
    else if(">".equals(_operator))
    {
      if(left > right) return 1;
      else return 0;
    }
    else if("<=".equals(_operator))
    {
      if(left <= right) return 1;
      else return 0;
    }
    else if(">=".equals(_operator))
    {
      if(left >= right) return 1;
      else return 0;
    }
    else
    {
      throw new ParserException("Unknown comp_op:"+_operator);
    }
  }
  
  
  
}
