package edu.osu.cse.parser;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Operator.WriterOp;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;
import edu.osu.cse.parser.BaseNode.Node_Type;
import java.util.Stack;

public class Out extends BaseNode
{
    IDList _param = null;
  
	public Out(BaseNode parent)
	{
		super(Node_Type.out, Tokenizer.RW_WRITE, parent);
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
		// write <id-list>;
		if(checkSignature(tokenizer))
		{   //parsing "write"
			GeneralRWNode node = new GeneralRWNode(this);
			node.parsingValue(tokenizer);
			addChildNode(node);
			//_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
			//parsing <id-list>
			IDList idlist = new IDList(this);
			if(!idlist.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<id-list>"));
			addChildNode(idlist);
            _param = idlist;
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			if(!checkTokenType(tokenizer, Tokenizer.SS_TERMINOR))throw new ParserException(createErrorMessage(tokenizer, ";"));
			
			GeneralSSNode node1 = new GeneralSSNode(this);
			node1.parsingValue(tokenizer);
			addChildNode(node1);
			
            return true;
		}
		return false;
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    Stack<ComparableNode> param = new Stack<>();
    if(_param == null || _param.getChildren().isEmpty())
    {
      return;
    }
    
    ArrayList<BaseNode> children = _param.getChildren();
    //let's reverse reading it. 
    for(int i=children.size() - 1; i>=0; i--)
    {
      BaseNode node  = children.get(i);
      if(node != null && node instanceof ID)
      {
        param.push(((ID)node).getVariable(ip));
      }
    }
    WriterOp write = new WriterOp();
    write.evaluation(param);
  }
	
	
}
