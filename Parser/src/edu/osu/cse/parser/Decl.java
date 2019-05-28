package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class Decl 
extends BaseNode
{
	//<decl> ::= int <id-list>;
	//we break it into "int" "idlist" "teminator" 
	
	public Decl(BaseNode parent)
	{
      super(Node_Type.decl, Tokenizer.RW_INT, parent);
      BaseNode aparent = _parent;
      while(aparent != null)
      {
        if(aparent._indent != 0)
        {
          _indent = aparent._indent;
          break;
        }
        aparent = aparent._parent;
      }
      _indent += 2;
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		return tokenid == _signatureID;
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer) throws ParserException
	{
		if(checkSignature(tokenizer))
		{
			//parsing "int"
			GeneralRWNode intnode = new GeneralRWNode(this);
			intnode.parsingValue(tokenizer);
			addChildNode(intnode);
			//_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
			//parsing <id>
			IDList idlist = new IDList(this);
			if(idlist.parsingValue(tokenizer))
			{
				addChildNode(idlist);
                //_nodeSeperator.add(BaseNode.SEP_CONNECT);
            }
            //parsing ";"
            if(checkTokenType(tokenizer, Tokenizer.SS_TERMINOR))
            {   //consume the terminator
                GeneralSSNode ssnode = new GeneralSSNode(this);
                ssnode.parsingValue(tokenizer);
                addChildNode(ssnode);
                return true;
            }
			
			throw new ParserException(createErrorMessage(tokenizer, ";"));
		}
		
		// TODO Auto-generated method stub
		return false;
	}
}
