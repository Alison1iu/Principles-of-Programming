package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class GeneralRWNode 
extends BaseNode
{

	public GeneralRWNode(BaseNode parent)
	{
      super(Node_Type.rw_node, 0, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		// TODO Auto-generated method stub
		return tokenid >= Tokenizer.TOKEN_RW[0]
				&& tokenid <= Tokenizer.TOKEN_RW[Tokenizer.TOKEN_RW.length - 1];
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer)throws ParserException 
	{
		if(checkSignature(tokenizer))
		{
			_token = tokenizer.currentToken();
			tokenizer.nextToken();
            int nodeid = _token.getTokenID();
            if(nodeid == Tokenizer.RW_BEGIN 
                  || nodeid == Tokenizer.RW_ELSE || nodeid == Tokenizer.RW_END)
            {
              BaseNode aparent = _parent;
              while(aparent != null)
              {
                if(aparent._indent != 0)
                {
                  _indent = aparent._indent;
                  break;
                }
                aparent = aparent._parent;
              }
            }
			return true;
		}
		throw new ParserException(createErrorMessage(tokenizer, "Reserve Word"));
	}
	
}
