/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.interporater.Operator;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.parser.ParserException;
import java.util.*;

public interface Operator 
{ //read, write, assign, comp, plus
  /**
   * evaluate the variable in stack and return the results as integer value
   * @param va
   * @return
   * @throws ParserException
   */
  public abstract int evaluation(Stack<ComparableNode> va) throws ParserException;
}
