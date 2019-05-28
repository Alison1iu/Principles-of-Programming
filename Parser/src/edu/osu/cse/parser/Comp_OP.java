package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;

//compare operator
public class Comp_OP 
	extends BaseNode
{ 
	/**
	 * it can be only one of those types "!="  "=="  "<"  ">"  "<="  ">="
	 */
	private static final ArrayList<Integer> COMP_OP_ID;
	static 
	{
		COMP_OP_ID = new ArrayList<>();
		COMP_OP_ID.add(Tokenizer.SS_NOTEQUAL);
		COMP_OP_ID.add(Tokenizer.SS_EQUAL);
		COMP_OP_ID.add(Tokenizer.SS_LESS);
		COMP_OP_ID.add(Tokenizer.SS_GREAT);
		COMP_OP_ID.add(Tokenizer.SS_LESSEQUAL);
		COMP_OP_ID.add(Tokenizer.SS_GREATEQUAL);
	}
		
	
	public Comp_OP(BaseNode parent)
	{//there is no special signature for this node.
		super(Node_Type.comp_op, 0, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		return checkTypeLimit(tokenid, COMP_OP_ID);
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		return COMP_OP_ID;
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
