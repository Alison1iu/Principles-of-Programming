package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Operator.MinusOp;
import edu.osu.cse.interporater.Operator.Operator;
import edu.osu.cse.interporater.Operator.PlusOp;
import edu.osu.cse.interporater.Value;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;
import edu.osu.cse.parser.BaseNode.Node_Type;

public class Exp 
extends BaseNode
{	
    Term _term = null;
    Operator _op = null;
    Exp _exp = null;
    
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
			_term = facNode;
            
			if(checkTokenType(tokenizer, Tokenizer.SS_PLUS)
					|| checkTokenType(tokenizer, Tokenizer.SS_MINUS))
			{
                if(checkTokenType(tokenizer, Tokenizer.SS_PLUS))
                {
                  _op = new PlusOp();
                }
                else 
                {
                  _op = new MinusOp();
                }
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
                _exp = exp;
			}
			return true;
		}
		return false;
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    if(_term == null) throw new ParserException("Missing term when process <exp> node");
    _term.evaluation(ip);
    if(_op != null && _exp != null)
    {
      _exp.evaluation(ip);
      int value = _op.evaluation(ip.getParamStack());
      ip.pushValue(new Value(value));
    }
  }
}
