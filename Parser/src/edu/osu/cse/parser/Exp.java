package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;
import edu.osu.cse.parser.BaseNode.Node_Type;

public class Exp 
extends BaseNode
{	
	public Exp(BaseNode parent)
	{
		super(Node_Type.exp, 0, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		return checkTypeLimit(tokenid, getNodeTypeLimit());
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		return Fac.FAC_START_ID;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer)throws ParserException
	{
		Term facNode = new Term(this);
		if(facNode.parsingValue(tokenizer))
		{
			addChildNode(facNode);
			
			if(checkTokenType(tokenizer, Tokenizer.SS_PLUS)
					|| checkTokenType(tokenizer, Tokenizer.SS_MINUS))
			{
				//_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
				//parsing '+' or  '-'
				GeneralSSNode ssnode = new GeneralSSNode(this);
				ssnode.parsingValue(tokenizer);
				addChildNode(ssnode);
				//_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
				//now let's call parsing value again
				Exp exp = new Exp(this);
				if(!exp.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<exp>"));
				addChildNode(exp);
			}
			return true;
		}
		return false;
	}
}
