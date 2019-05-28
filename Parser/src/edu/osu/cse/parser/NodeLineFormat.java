/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.cse.parser;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author 
 */
public class NodeLineFormat 
{
  public static final int SEP_WHITESPACE = ' ';
  public static final int SEP_TAB = '\t';
  public static final int SEP_LINEBREAK = '\n';
  public static final int SEP_CONNECT = 0;
  
  int _indent = 0;
  private final ArrayList<BaseNode> _nodeList = new ArrayList();
  private final ArrayList<Integer> _nodeSeperator = new ArrayList<>();
  
  /**
   * 
   * @param indent 
   */
  public NodeLineFormat(int indent)
  {
    _indent = indent;
  }
  
  /**
   * 
   * @param node
   * @param seperator 
   */
  public void addNode(BaseNode node, int seperator)
  {
    _nodeList.add(node);
    _nodeSeperator.add(seperator);
  }
  
  /**
   * using default separator (white space)
   * @param node
   */
  public void addNode(BaseNode node)
  {
    _nodeList.add(node);
    _nodeSeperator.add(SEP_WHITESPACE);
  }
  
  
  /**
   * 
   * @param out 
   * @throws java.io.IOException 
   */
  public void writeOutLine(OutputStream out)throws IOException
  {
    for(int i=0; i<_indent; i++)
    {
      out.write(SEP_WHITESPACE);
    }
    for(int i=0; i<_nodeList.size(); i++)
    {
      BaseNode base = _nodeList.get(i);
      base.writeoutNode(out);
      if(i< _nodeSeperator.size() && _nodeSeperator.get(i) != 0)
      {
        out.write(_nodeSeperator.get(i));
      }
    }
  }
}
