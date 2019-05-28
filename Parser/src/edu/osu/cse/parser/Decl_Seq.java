package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class Decl_Seq 
extends BaseNode
{
	public Decl_Seq(BaseNode parent)
	{
		super(Node_Type.decl_seq, Tokenizer.RW_INT, parent);
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
      while(checkSignature(tokenizer))
      {
          Decl decl = new Decl(this);
          if(!decl.parsingValue(tokenizer)) return false;
          addChildNode(decl);
      }
      return hasChildNode();
	}
}
