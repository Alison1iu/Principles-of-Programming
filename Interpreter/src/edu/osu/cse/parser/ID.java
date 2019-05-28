package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Variable;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class ID extends BaseNode
{
//	private static final ArrayList<Integer> ID_LIMIT;
//	static
//	{
//		ID_LIMIT = new ArrayList<>();
//		ID_LIMIT.add(Tokenizer.TOKEN_IDENTIFIER);
//	}
    boolean _isDecl = false;
  
	
	public ID(BaseNode parent)
	{
        super(Node_Type.id, Tokenizer.TOKEN_IDENTIFIER, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		return tokenid == _signatureID;
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		return null;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer) throws ParserException
	{
      if(checkSignature(tokenizer)) 
      {
          _token = tokenizer.currentToken();
          tokenizer.nextToken();
          if(!_isDecl && _interporator != null)
          {
            //this will be able to check if a variable is valid or not
            _interporator.getVariable(this);
          }
          
          return true;
      }
      return false;
	}

    /**
     * 
     * @param ip
     * @throws ParserException 
     */
    @Override
    public void evaluation(Interporater ip) throws ParserException 
    {
      ip.pushVariable(ip.getVariable(this));
    }
    
    /**
     * Get declared variable. 
     * @param ip
     * @return
     * @throws ParserException 
     */
    public Variable getVariable(Interporater ip) throws ParserException
    {
      return ip.getVariable(this);
    }
    
    /**
     * 
     */
    public void setDecl(boolean flag)
    {
      _isDecl = flag;
    }
}
