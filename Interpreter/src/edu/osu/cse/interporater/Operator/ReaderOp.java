/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater.Operator;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.interporater.Variable;
import edu.osu.cse.parser.*;
import java.util.*;

/**
 * 
 * Read from system console and save the input value to a given variable
 */
public class ReaderOp 
        implements Operator
{
  /**
  * Read from system console and save the input value to a given variable
  * @param va
   * @throws edu.osu.cse.parser.ParserException
  */
  @Override
  public int evaluation(Stack<ComparableNode> va) throws ParserException
  {
    if(va.size()<0) throw new ParserException("Missing variable");
    Scanner scanner = new Scanner(System.in);
    while(!va.isEmpty())
    {
      ComparableNode node = va.pop();
      if(node != null && node instanceof Variable)
      {
        Variable a = (Variable)node;
        boolean done = false;
        while(!done)
        {
          System.out.print(a.getName()+"=?");
          int value = 0;
          try
          {
            value = scanner.nextInt();
            done = true;
            a.setvalue(value);
          }
          catch(InputMismatchException ex)
          {
            System.out.println("Incorrect data type, please input an valid integer!");
            scanner.next();
          }
        }
      }
    }
    
    return 1;
  }
  
  
  
}
