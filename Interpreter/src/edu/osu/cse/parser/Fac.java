package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import java.util.ArrayList;

import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;

public class Fac 
extends BaseNode
{
	public static final ArrayList<Integer> FAC_START_ID;
	static 
	{
		FAC_START_ID = new ArrayList<>();
		FAC_START_ID.add(Tokenizer.SS_l_BRACKET);
		FAC_START_ID.add(Tokenizer.TOKEN_IDENTIFIER);
		FAC_START_ID.add(Tokenizer.TOKEN_INT);
	}
	
	public Fac(BaseNode parent)
	{
		super(Node_Type.fac, 0, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		// TODO Auto-generated method stub
		return checkTypeLimit(tokenid, FAC_START_ID);
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		// TODO Auto-generated method stub
		return FAC_START_ID;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer) throws ParserException
	{
		GrammerToken token = tokenizer.currentToken();
		
		if(token == null)
		{
			return false;
		}
		else if(token.getTokenID() == Tokenizer.TOKEN_IDENTIFIER)
		{
			ID node = new ID(this);
			if(!node.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<id>"));
			addChildNode(node);
			return true;
		}
		else if(token.getTokenID() == Tokenizer.TOKEN_INT)
		{
			IntNode node = new IntNode(this);
			if(!node.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<int>"));
			addChildNode(node);
			return true;
		}
		else if(token.getTokenID() == Tokenizer.SS_l_BRACKET)
		{
			//parsing "("
			GeneralSSNode lnode = new GeneralSSNode(this);
			lnode.parsingValue(tokenizer);
			addChildNode(lnode);
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			//parsing exp
			Exp expnode = new Exp(this);
			if(!expnode.parsingValue(tokenizer))
			{
				throw new ParserException(createErrorMessage(tokenizer, "<exp>"));
			}
			addChildNode(expnode);
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			//parsing ")"
			if(checkTokenType(tokenizer, Tokenizer.SS_R_BRACKET))
			{
				GeneralSSNode rnode = new GeneralSSNode(this);
				rnode.parsingValue(tokenizer);
				addChildNode(rnode);
				return true;
			}
			else
			{
				throw new ParserException(createErrorMessage(tokenizer, ")"));
			}
			
		}
		return false;
		
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    ArrayList<BaseNode> children = getChildren();
    
    if(children == null || children.size() < 1)
    {
        throw new ParserException("Fac node has no child node!");
    }
    
    BaseNode firstChild = children.get(0);
    if(firstChild == null)
    {
      throw new ParserException("Null pointer appears in Fac node");
    }
    //we can use size to find it out, but to make it clean to read, we still use type to check. 
    if(firstChild instanceof ID)
    {
        firstChild.evaluation(ip);
    }
    else if(firstChild instanceof IntNode)
    {
        firstChild.evaluation(ip);
    }
    else if(firstChild instanceof GeneralSSNode)
    {
        //now the second node should be the expression
        //we don;t need to check, because the parser already did
        //parsing "("
        if(children.size() != 3)
        {
          throw new ParserException("Incorrect child node, it should be (Exp)");
        }
        children.get(1).evaluation(ip);
    }
  }
}
