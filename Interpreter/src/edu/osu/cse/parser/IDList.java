package edu.osu.cse.parser;


import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Variable;
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
  
    private boolean _isDecl = false;
	
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
            id.setDecl(_isDecl);
            id.parsingValue(tokenizer);
            addChildNode(id);

            while(checkTokenType(tokenizer, Tokenizer.SS_SEPERATOR))
            {
              GeneralSSNode ssnode = new GeneralSSNode(this);
              ssnode.parsingValue(tokenizer);
              addChildNode(ssnode);
              //_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
              ID nid = new ID(this);
              nid.setDecl(_isDecl);
              if(!nid.parsingValue(tokenizer))
              {
                  throw new ParserException(createErrorMessage(tokenizer, "<id>"));
              }
              addChildNode(nid);
            }
		}
		return hasChildNode();
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    ArrayList<BaseNode> children = getChildren();
    for(int i=0; i<children.size(); i++)
    {
      if(children.get(i) != null) children.get(i).evaluation(ip);
    }
  }
  
  /**
   * 
   * @param flag 
   */
  public void setDecl(boolean flag)
  {
    _isDecl = flag;
  }
  
//  /**
//   * 
//   * @param ip
//   * @return
//   * @throws ParserException 
//   */
//  public ArrayList<Variable> getVariableList(Interporater ip) throws ParserException
//  {
//    ArrayList<Variable> list = new ArrayList<>();
//    ArrayList<BaseNode> children = getChildren();
//    for(int i=0; i<children.size(); i++)
//    {
//      BaseNode node = children.get(i);
//      if(node != null && node instanceof ID)
//      {
//        list.add(((ID)node).getVariable(ip));
//      }
//    }
//    return list;
//  }	
	
}
