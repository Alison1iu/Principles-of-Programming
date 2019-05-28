package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;

public class ID extends BaseNode
{
//	private static final ArrayList<Integer> ID_LIMIT;
//	static
//	{
//		ID_LIMIT = new ArrayList<>();
//		ID_LIMIT.add(Tokenizer.TOKEN_IDENTIFIER);
//	}
	
	
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
			return true;
		}
		return false;
	}

}
