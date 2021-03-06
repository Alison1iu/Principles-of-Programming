package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Operator.CompOperator;
import edu.osu.cse.interporater.Value;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class Comp
extends BaseNode
{
    private Fac _left = null;
    private Comp_OP _comp = null;
    private Fac _right = null;
  
	public Comp(BaseNode parent)
	{
		super(Node_Type.comp, Tokenizer.SS_l_BRACKET, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		// TODO Auto-generated method stub
		return tokenid == _signatureID;
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer) throws ParserException
	{  //(<fac> <comp-op> <fac>)
		if(checkSignature(tokenizer))
		{
			//parsing "("
			GeneralSSNode ssnode = new GeneralSSNode(this);
			ssnode.parsingValue(tokenizer);
			addChildNode(ssnode);
            //AddNodeToLineFormat(ssnode);
            
			//parsing <fac>
			Fac lfac = new Fac(this);
			if(!lfac.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<fac>"));
			addChildNode(lfac);
            _left = lfac;
			//AddNodeToLineFormat(lfac);
            
			//parsing <comp-op>
			Comp_OP compop = new Comp_OP(this);
			if(!compop.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<comp-op>"));
			addChildNode(compop);
            _comp = compop;
			//AddNodeToLineFormat(compop);
            
			//parsing <fac>
			Fac rfac = new Fac(this);
			if(!rfac.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<fac>"));
			addChildNode(rfac);
            _right = rfac;
			//AddNodeToLineFormat(rfac);
            
			//parsing ")"
			if(!checkTokenType(tokenizer, Tokenizer.SS_R_BRACKET)) return false;
			GeneralSSNode ssnode1 = new GeneralSSNode(this);
			ssnode1.parsingValue(tokenizer);
			addChildNode(ssnode1);
			//AddNodeToLineFormat(ssnode1, NodeLineFormat.SEP_CONNECT);
            
			return true;
		}
		return false;
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    if(_left == null || _right == null || _comp == null) throw new ParserException("Comp node need <fac> <comp-op> <fac>");
    _left.evaluation(ip);
    _comp.evaluation(ip);
    _right.evaluation(ip);
    CompOperator op = _comp.getOperator();
    int value = op.evaluation(ip.getParamStack());
    ip.pushValue(new Value(value));
  }
}
