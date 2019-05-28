package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;
import edu.osu.cse.parser.BaseNode.Node_Type;

public class IntNode 
extends BaseNode
{	
	public IntNode(BaseNode parent)
	{
		super(Node_Type.int_node, Tokenizer.TOKEN_INT, parent);
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
