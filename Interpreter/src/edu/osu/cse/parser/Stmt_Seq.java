package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class Stmt_Seq 
extends BaseNode
{
	public Stmt_Seq(BaseNode parent)
	{
		super(Node_Type.stmt_seq, 0, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) {
		// TODO Auto-generated method stub
		return checkTypeLimit(tokenid, getNodeTypeLimit());
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() {
		// TODO Auto-generated method stub
		return Stmt.STMT_OP_ID;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer)throws ParserException 
	{			
		//now let's see if we have more sequence or not. 
		while(checkSignature(tokenizer))
		{
			Stmt stmt = new Stmt(this);
			if(stmt.parsingValue(tokenizer))
			{
				addChildNode(stmt);
				//_nodeSeperator.add(BaseNode.SEP_LINEBREAK);
			}
		}
		return true;
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    ArrayList<BaseNode> children  = getChildren();
    for(int i=0; i<children.size(); i++)
    {
      if(children.get(i) != null) 
        children.get(i).evaluation(ip);
    }
  }
}
