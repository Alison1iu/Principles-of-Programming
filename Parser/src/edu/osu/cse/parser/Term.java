package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class Term 
extends BaseNode
{	
	public Term(BaseNode parent)
	{
		super(Node_Type.term, 0, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		// TODO Auto-generated method stub
		return checkTypeLimit(tokenid, getNodeTypeLimit());
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		// TODO Auto-generated method stub
		return Fac.FAC_START_ID;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer)throws ParserException
	{
		// TODO Auto-generated method stub
		Fac facNode = new Fac(this);
		if(facNode.parsingValue(tokenizer))
		{
			addChildNode(facNode);
			if(checkTokenType(tokenizer, Tokenizer.SS_STAR))
			{
				//parsing *
				GeneralSSNode ssnode = new GeneralSSNode(this);
				ssnode.parsingValue(tokenizer);
				addChildNode(ssnode);
				//now let's call parsing value again
				parsingValue(tokenizer);
			}
			return true;
		}
		return false;
	}
}
