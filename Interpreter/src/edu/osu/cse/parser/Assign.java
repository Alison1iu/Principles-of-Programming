package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Operator.AssignOp;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class Assign 
extends BaseNode
{
  
    Assign _assign = null;
    
	public Assign(BaseNode parent)
	{
      super(Node_Type.assign, Tokenizer.TOKEN_IDENTIFIER, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		// TODO Auto-generated method stub
		return tokenid == _signatureID;
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
		// <id> = <exp>;
		if(checkSignature(tokenizer))
		{
			//parsing <id>
			ID id = new ID(this);
			if(!id.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<id>"));
			addChildNode(id);
            //AddNodeToLineFormat(id);
			
			//parsing "="
			if(!checkTokenType(tokenizer, Tokenizer.SS_ASSIGN)) throw new ParserException(createErrorMessage(tokenizer, "="));
			GeneralSSNode node = new GeneralSSNode(this);
			node.parsingValue(tokenizer);
			addChildNode(node);
			//AddNodeToLineFormat(node);
			
			Exp exp = new Exp(this);
			if(!exp.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<exp>"));
			addChildNode(exp);
			//AddNodeToLineFormat(exp, NodeLineFormat.SEP_CONNECT);
			
			//parsing ";"
			if(!checkTokenType(tokenizer, Tokenizer.SS_TERMINOR)) throw new ParserException(createErrorMessage(tokenizer, ";"));
			GeneralSSNode node1 = new GeneralSSNode(this);
			node1.parsingValue(tokenizer);
			addChildNode(node1);
			//AddNodeToLineFormat(node1, NodeLineFormat.SEP_CONNECT);
            
			return true;
		}
		return false;
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    AssignOp assign = new AssignOp();
    //we need to get variable. and the value
    ArrayList<BaseNode> children = getChildren();
    for(int i=0; i<children.size(); i++)
    {
      if(children.get(i) != null) 
        children.get(i).evaluation(ip);
    }
    //now the last two param in the parameter stack should be varaible and value
    assign.evaluation(ip.getParamStack());
  }
	
	
}
