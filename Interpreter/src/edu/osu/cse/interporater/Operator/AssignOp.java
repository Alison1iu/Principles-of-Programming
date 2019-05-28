/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater.Operator;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.interporater.Value;
import edu.osu.cse.interporater.Variable;
import edu.osu.cse.parser.ParserException;
import java.util.Stack;


public class AssignOp 
        implements Operator
{
  /**
   * Consume top 2 element from stack.
   * Assign the value of first element to second element. 
   * if second element is not Variable, exception will be thrown. 
   * @param va
   * @return
   * @throws ParserException 
   */
  @Override
  public int evaluation(Stack<ComparableNode> va) throws ParserException 
  {
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    if(va.size() < 2) throw new ParserException("Assign operator need variable and value");
    ComparableNode value = va.pop();
    ComparableNode name = va.pop();
    
    int avalue = value.getValue();
    if(name instanceof Variable)
    {
      ((Variable) name).setvalue(avalue);
      return avalue;
    }
    else
    {
      throw new ParserException("Assign operator need variable");
    }
  } 
}
