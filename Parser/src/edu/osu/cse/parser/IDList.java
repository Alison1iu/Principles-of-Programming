package edu.osu.cse.parser;


import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class IDList extends BaseNode 
{
//	private static final ArrayList<Integer> IDList_ID;
//	static 
//	{
//		IDList_ID = new ArrayList<>();
//		IDList_ID.add(Tokenizer.getSSTokenID(","));
//		IDList_ID.add(Tokenizer.TOKEN_IDENTIFIER);
//	}
	
	public IDList(BaseNode parent)
	{
		super(Node_Type.id_list, Tokenizer.TOKEN_IDENTIFIER, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		return tokenid == _signatureID;
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit()
	{
		return null;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer) throws ParserException
	{
		if(checkSignature(tokenizer))
		{
            ID id = new ID(this);
            id.parsingValue(tokenizer);
            addChildNode(id);
          
            while(checkTokenType(tokenizer, Tokenizer.SS_SEPERATOR))
            {
              GeneralSSNode ssnode = new GeneralSSNode(this);
              ssnode.parsingValue(tokenizer);
              addChildNode(ssnode);
              //_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
              ID nid = new ID(this);
              if(!nid.parsingValue(tokenizer))
              {
                  throw new ParserException(createErrorMessage(tokenizer, "<id>"));
              }
              addChildNode(nid);
            }
		}
		return hasChildNode();
	}
	
	
}
