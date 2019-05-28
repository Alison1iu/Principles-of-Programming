package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class In 
extends BaseNode
{
	public In(BaseNode parent)
	{
		super(Node_Type.in, Tokenizer.RW_READ, parent);
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
	{
		// read <id-list>;
		if(checkSignature(tokenizer))
		{   //parsing "read"
			GeneralRWNode node = new GeneralRWNode(this);
			node.parsingValue(tokenizer);
			addChildNode(node);
			//_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
			
			//parsing <id-list>
			IDList idlist = new IDList(this);
			if(!idlist.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<id-list>"));
			addChildNode(idlist);
			
			if(!checkTokenType(tokenizer, Tokenizer.SS_TERMINOR))throw new ParserException(createErrorMessage(tokenizer, ";"));
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			GeneralSSNode node1 = new GeneralSSNode(this);
			node1.parsingValue(tokenizer);
			addChildNode(node1);
			
            return true;
		}
		return false;
	}
	
	
}
