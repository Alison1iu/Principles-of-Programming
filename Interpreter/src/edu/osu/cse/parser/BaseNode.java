package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import java.io.*;
import java.util.ArrayList;

import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;

public abstract class BaseNode
{
  
	public enum Node_Type
	{
		prog, decl_seq, stmt_seq, decl, id_list, stmt, assign, if_node, loop,
		in, out, cond, comp, exp, term, fac, comp_op, id, int_node, rw_node, ss_node
	};
    
    /**
     * the interporator which will evaluate current node, 
     */
    protected Interporater _interporator = null;
	    
	/**
	 * 
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
	
	//multiple token combination
	private ArrayList<BaseNode> _childvalue = new ArrayList<>();
    private ArrayList<Integer> _seperator = new ArrayList<>();
	
	//protected ArrayList<Integer> _nodeSeperator = new ArrayList<>();
	
	//single token node
	protected GrammerToken _token = null;
	
	protected int _signatureID = 0;
	
	protected Node_Type _type; 
	
	protected int _indent = 0;
	
    //protected int _startLine = 0;
	/**
	 * 
     * @param type
     * @param signature
     * @param parent
	 */
	public BaseNode(Node_Type type, int signature, BaseNode parent)
	{
      _type = type;
      _signatureID = signature;
      _parent = parent;
      if(_parent != null)
      {
        _interporator = _parent.getInterporater();
      }
	}
    
    /**
     * 
     * @return 
     */
    protected Interporater getInterporater()
    {
      return _interporator;
    }
    
    /**
     * 
     * @param node 
     */
    protected void addChildNode(BaseNode node)
    {
      _childvalue.add(node);
      //let's do the prefix seperator 
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
          {//we just add zero in 
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
     * 
     * @return 
     */
    protected boolean hasChildNode()
    {
      return !_childvalue.isEmpty();
    }
   
	protected abstract boolean isTokenTypeMatch(int tokenid);
	
	protected abstract ArrayList<Integer> getNodeTypeLimit();
	
	protected abstract boolean parsingValue(Tokenizer tokenizer) throws ParserException;
    
	/**
	 * 
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
	 * 
	 * @param tokenizer
	 * @param targetID
	 * @return
	 */
	protected boolean checkTokenType(Tokenizer tokenizer, int targetID) throws ParserException
	{
		GrammerToken token = tokenizer.currentToken();
		if(token == null || token.getTokenID() >= Tokenizer.TOKEN_EOF) return false;
		return token.getTokenID() == targetID;
	}
		
	/**
	 * 
	 * @param value
	 */
	protected void setIndent(int value)
	{
		_indent += value;
	}
	
	/**
	 * 
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
	 * 
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
    protected String createErrorMessage(Tokenizer tk, String expected) throws ParserException
    {
      StringBuilder builder = new StringBuilder();
      GrammerToken token = tk.currentToken();
      if(token == null)
      {
        builder.append("SEVERE: Unexpected End of File");
      }
      else
      {
        builder.append("SEVERE: Syntax Error: [line "+Integer.toString(token.getLineNo())+"]"
                + " Parsing <").append(getClass().getSimpleName()).append("> Node, expect:\"").append(expected);
        builder.append("\", found ");
        if(token.getTokenID() == Tokenizer.TOKEN_EOF)
        {
          builder.append("~EOF~");
        }
        else
        { 
          builder.append(token.getTokenValue());
        }
      }
      return builder.toString();
    }
    
    /**
     * get the single token, this is called when current instance
     * is single token type 
     * @return 
     */
    public GrammerToken getToken()
    {
      return _token;
    }
    
    /**
     * get the child nodes. this is called when current instance contains
     * multiply children nodes.  
     * @return 
     */
    public ArrayList<BaseNode> getChildren()
    {
      return _childvalue;
    }
    
    /**
     * evaluate current node with interporater
     * @param ip
     * @throws ParserException 
     */
    public abstract void evaluation(Interporater ip) throws ParserException;
            
	
}
