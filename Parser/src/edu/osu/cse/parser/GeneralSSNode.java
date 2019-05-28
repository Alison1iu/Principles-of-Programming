package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;
import edu.osu.cse.parser.BaseNode.Node_Type;

public class GeneralSSNode extends BaseNode
{	
	public GeneralSSNode(BaseNode parent)
	{
		super(Node_Type.ss_node, 0, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		// TODO Auto-generated method stub
		return tokenid >= Tokenizer.TOKEN_SS[0]
				&& tokenid <= Tokenizer.TOKEN_SS[Tokenizer.TOKEN_SS.length - 1];
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
			return true;
		}
		throw new ParserException(createErrorMessage(tokenizer, "Special Symbol"));
	}
	
}
