package edu.osu.cse.parser;

import java.io.*;
import java.util.ArrayList;

import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;

/**
 * This is the base object. 
 * It defines the basic behave of all Node.
 * 
 * 
 * It includes three abstract methods:
 * 
 * isTokenTypeMatch(int tokenid), getNodeTypeLimit(), and parsingValue(Tokenizer tokenizer). 
 * 
 * isTokenTypeMatch will be used to check a given token id is matching with the instance of current Node .
 * getNodeTypeLimit is used to retrieve the type of GrammerToken which will be allowed for the Node.
 * parsingValue is used to construct the instance of current Node. 
 * 
 * @author 
 */


public abstract class BaseNode
{
	public enum Node_Type
	{
		prog, decl_seq, stmt_seq, decl, id_list, stmt, assign, if_node, loop,
		in, out, cond, comp, exp, term, fac, comp_op, id, int_node, rw_node, ss_node
	};
	    
	/**
	 * Add Type limit to the give type list. this is a utility call.  we use it to create
	 * the type limit for individual Node. 
	 * @param value
     * @param typelimit
	 */
	protected static void addTypeLimit(int value, ArrayList<Integer> typelimit)
	{
      if(!typelimit.contains(value))
      {
        typelimit.add(value);
      }
	}
	

	/**
	 * 
	 * @param tokenID
	 * @param tlimite
	 * @return
	 */
	protected static boolean checkTypeLimit(int tokenID, ArrayList<Integer> tlimite)
	{
		if(tlimite == null || tlimite.isEmpty()) return true;
		return tlimite.contains(tokenID);
	}
	
	//parent
	protected BaseNode _parent = null;
	
	//multiple token, combination type. 
	private ArrayList<BaseNode> _childvalue = new ArrayList<>();
	/**
	 * the separator between two child node. It can be whitespace, line break, or nothing.
	 * we use the separator to control the print format.  
	 */
    private ArrayList<Integer> _seperator = new ArrayList<>();
    
    /**
     * print format set up. for node like If, Loop and other, the child node need to be indented. 
     * we use this value to control. 
     */
	protected int _indent = 0;
	
	/**
	 * The leaf of parsing tree. 
	 * Single token node. this is used when current node is single token type, such as Reserved word,
	 * Identifier, Integer, and  etc.
	 */
	protected GrammerToken _token = null;
	
	/**
	 * if current node is single token type, the signatureID will be the id of that token. otherwise it is 0.
	 * This is a performance setting. we could use the child list. 
	 */
	protected int _signatureID = 0;
	
	/**
	 * the type of this node
	 */
	protected Node_Type _type; 
	

	
	/**
	 * Create a new instance of BaseNode
     * @param type
     * @param signature
	 * @param tokenType
     * @param parent
	 */
	public BaseNode(Node_Type type, int signature, BaseNode parent)
	{
      _type = type;
      _signatureID = signature;
      _parent = parent;
	}
    
    /**
     * Add child node, and at the same time set up the print format
     * @param node 
     */
    protected void addChildNode(BaseNode node)
    {
      _childvalue.add(node);
      //let's do the prefix separator 
      if(node instanceof Decl || node instanceof Stmt)
      {
        _seperator.add((int)'\n');
      }
      else if(_seperator.isEmpty())
      {
        _seperator.add(0);
      }
      else
      {
        if(node._token != null)
        {
          int nodeid = node._token.getTokenID();
          if( nodeid== Tokenizer.SS_SEPERATOR 
                  || nodeid == Tokenizer.SS_TERMINOR)
          {
            _seperator.add(0);
          }
          else if(nodeid == Tokenizer.RW_BEGIN 
                  || nodeid == Tokenizer.RW_ELSE || nodeid == Tokenizer.RW_END)
          {
            _seperator.add((int)'\n');
          }
          else
          {
            _seperator.add(32);
          }
        }
        else
        {
          if(_childvalue.size() == 1)
          {//we just add nothing for the first child
            _seperator.add(0);
          }
          else
          {
            _seperator.add(32);
          }
        }
      }
    }
    
    /**
     * check if this instance has child node or not
     * @return 
     */
    protected boolean hasChildNode()
    {
      return !_childvalue.isEmpty();
    }

    
    /**
     * Get child Node at index. 
     * If the index is outside of child list, return null 
     * @param index
     * @return
     */
    protected BaseNode getChildNode(int index)
    {
    	if(index < 0 || index >= _childvalue.size()) return null;
    	return _childvalue.get(index);
    }
    
	/**
	 * check if a given token id is matching with this node type
	 * @param tokenid
	 * @return
	 */
	protected abstract boolean isTokenTypeMatch(int tokenid);
	
	/**
	 * return the GrammerToken Type list, which this node allows.
	 * @return
	 */
	protected abstract ArrayList<Integer> getNodeTypeLimit();
	
	/**
	 * Read in GrammerToken from Tokenizer and create this node. 
	 * @param tokenizer
	 * @return
	 * @throws ParserException
	 */
	protected abstract boolean parsingValue(Tokenizer tokenizer) throws ParserException;
	
	/**
	 * Check if current token in Tokenizer can match with this Node. 
	 * @param tokenizer
	 * @return
     * @throws java.io.IOException
	 */
	protected boolean checkSignature(Tokenizer tokenizer) throws ParserException
	{
		GrammerToken token = tokenizer.currentToken();
		if(token == null)
        {
          return false;
        }
        else if (token.getTokenID() == Tokenizer.TOKEN_EOF)
        {
          return false;
        }
        else if(token.getTokenID() > Tokenizer.TOKEN_EOF)
        {
          throw new ParserException(token);
        }
		return isTokenTypeMatch(token.getTokenID()); 
	}
	
	/**
	 * Check if current token in Tokenizer can match with this targetID. 
	 * @param tokenizer
	 * @param targetID
	 * @return
	 */
	protected boolean checkTokenType(Tokenizer tokenizer, int targetID)
	{
		GrammerToken token = tokenizer.currentToken();
		if(token == null || token.getTokenID() >= Tokenizer.TOKEN_EOF) return false;
		return token.getTokenID() == targetID;
	}
	

	
	/**
	 * add indent value.
	 * Note: the indent value is the total from node root all way to current node. 
	 * @param value
	 */
	protected void addIndent(int value)
	{
		_indent += value;
	}
	
	/**
	 * set indent value.
	 * force indent to be the give value. 
	 * Note:
	 * This is used to create print out for this node only. It could cause problem, we may need to
	 * find a better way. 
	 * @param value
	 */
	protected void setIndent(int value)
	{
		_indent = value;
	}
	
	/**
	 * Print this instance to output. it can be file out or screen out
     * @param out
	 * @throws IOException
	 */
	protected void writeoutNode(OutputStream out) throws IOException
	{
        for(int i=0; i<_indent; i++)
        {
          out.write(' ');
        }
        
        if(_token != null)
		{
          _token.writeoutToken(out);
		}
		else
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			for(int i=0; i<_childvalue.size(); i++)
			{
				BaseNode child = _childvalue.get(i);
				if(child != null)
                {
                  if(_seperator.get(i) != 0)
                  {
                    bout.write(_seperator.get(i));
                  }
                  child.writeoutNode(bout);
                }
			}
			out.write(bout.toByteArray());
		}
	}
	
	/**
	 * Get parent node of this instance
	 * @return
	 */
	BaseNode getParent()
	{
		return _parent;
	}
    
    
    /**
     * 
     * @param tk
     * @param expected
     * @return 
     */
    protected String createErrorMessage(Tokenizer tk, String expected)
    {
      StringBuilder builder = new StringBuilder();
      builder.append("Parsing <").append(getClass().getSimpleName()).append("> Node, expect:\"").append(expected);
      builder.append("\", but receive ");
      GrammerToken token = tk.currentToken();
      if(token == null)builder.append("null");
      else
      { 
        builder.append(token.getTokenValue()).append("\n");
        builder.append("Error occur at line ");
        builder.append(Integer.toString(token.getLineNo())).append(".");
      }
      return builder.toString();
    }
	
}
