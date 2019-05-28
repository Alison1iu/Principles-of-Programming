package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Variable;
import edu.osu.cse.token.GrammerToken;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class Decl 
extends BaseNode
{
	//<decl> ::= int <id-list>;
	//we break it into "int" "idlist" "teminator" 
    private IDList _idlist = null;
	
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
            idlist.setDecl(true);
			if(idlist.parsingValue(tokenizer))
			{
				addChildNode(idlist);
                _idlist = idlist;
            }
            //parsing ";"
            if(checkTokenType(tokenizer, Tokenizer.SS_TERMINOR))
            {   //consume the terminator
                GeneralSSNode ssnode = new GeneralSSNode(this);
                ssnode.parsingValue(tokenizer);
                addChildNode(ssnode);
                
                if(_idlist != null && _interporator != null)
                {
                  ArrayList<BaseNode> children = _idlist.getChildren();
                  for(int i=0; i<children.size(); i++)
                  {
                    BaseNode node = children.get(i);
                    if(node != null && node instanceof ID)
                    {
                      Variable variable = new Variable((ID)node);
                      //now we need to check if we already defined this variable or not
                      if(_interporator.hasVariable(variable))
                      {
                        GrammerToken token = ((ID)node).getToken();
                        throw new ParserException("SEVERE: [line "+token.getLineNo()+"] "+variable.getName()+" already declared");
                      }
                      _interporator.registerVariable(variable);
                    }
                  }
                }
                
                return true;
            }
			throw new ParserException(createErrorMessage(tokenizer, ";"));
		}
		
		// TODO Auto-generated method stub
		return false;
	}

    /**
     * 
     * @param ip
     * @throws ParserException 
     */
    @Override
    public void evaluation(Interporater ip) throws ParserException 
    {
      //do nothing, because we already register with Interporater
    }
    

}
