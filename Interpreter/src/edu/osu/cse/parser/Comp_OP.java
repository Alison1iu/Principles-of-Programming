package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Operator.CompOperator;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

//compare operator
public class Comp_OP 
	extends BaseNode
{ 
    private CompOperator _operator = null;
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

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    //this will not do compare, the compare will happen in it parent.
    _operator = new CompOperator(_token.getTokenValue());
  }
  
  public CompOperator getOperator()
  {
    return _operator;
  }

}
