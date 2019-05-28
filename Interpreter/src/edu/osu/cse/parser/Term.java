package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Operator.MultiplyOp;
import edu.osu.cse.interporater.Value;
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
                
//                Term chnode = new Term(this);
//                chnode.parsingValue(tokenizer);
//                addChildNode(chnode);
				parsingValue(tokenizer);
			}
			return true;
		}
		return false;
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException
  {
    ArrayList<BaseNode> children = getChildren();

    BaseNode node1 = children.get(0);
    node1.evaluation(ip);
    for(int i=1; i<children.size();)
    {
      if(i<children.size())
      {
        BaseNode nodestar = children.get(i);
        if(nodestar == null || !(nodestar instanceof GeneralSSNode))
        {
          throw new ParserException("Incorrect syntax in Term Node");
        }
        i++; //skip * 
        if(i<children.size())
        {
          BaseNode node2 = children.get(i);
          node2.evaluation(ip);
          i++;
          MultiplyOp operator = new MultiplyOp();
          int value = operator.evaluation(ip.getParamStack());
          ip.pushValue(new Value(value));
        }
        else
        {
          throw new ParserException("Incorrect syntax in Term Node, missing term node after star");
        }
      }
    }
  }
}
