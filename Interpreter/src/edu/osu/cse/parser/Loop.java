package edu.osu.cse.parser;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.interporater.Interporater;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;
import edu.osu.cse.parser.BaseNode.Node_Type;

public class Loop 
extends BaseNode
{
    private Cond _cond = null;
    private Stmt_Seq _stmtseq = null;
  
	public Loop(BaseNode parent)
	{
		super(Node_Type.loop, Tokenizer.RW_WHILE, parent);
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
	{ //while <cond> loop <stmt-seq> end;
		if(checkSignature(tokenizer))
		{
			//parsing "while"
			GeneralRWNode node = new GeneralRWNode(this);
			node.parsingValue(tokenizer);
			addChildNode(node);
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			//parsing <cond>
			Cond cond = new Cond(this);
			if(!cond.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<cond>"));
			addChildNode(cond);
            _cond = cond;
			//_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
			//parsing "loop"
			if(!checkTokenType(tokenizer, Tokenizer.RW_LOOP)) throw new ParserException(createErrorMessage(tokenizer, "loop"));
			GeneralRWNode node1 = new GeneralRWNode(this);
			node1.parsingValue(tokenizer);
			addChildNode(node1);
			//_nodeSeperator.add(BaseNode.SEP_LINEBREAK);
			//parsing <stmt-seq>
			Stmt_Seq stmt = new Stmt_Seq(this);
			if(!stmt.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "loop <stmt-seq>"));
			addChildNode(stmt);
            _stmtseq = stmt;
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			//parsing end
			if(!checkTokenType(tokenizer, Tokenizer.RW_END)) throw new ParserException(createErrorMessage(tokenizer, "end"));
			GeneralRWNode node3 = new GeneralRWNode(this);
			node3.parsingValue(tokenizer);
			addChildNode(node3);
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);	
			//parsing end
			if(!checkTokenType(tokenizer, Tokenizer.SS_TERMINOR)) throw new ParserException(createErrorMessage(tokenizer, ";"));
			GeneralSSNode node4 = new GeneralSSNode(this);
			node4.parsingValue(tokenizer);
			addChildNode(node4);
			
			return true;
		}
		
		// TODO Auto-generated method stub
		return false;
	}

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    //while <cond> loop <stmt-seq> end;
      if(_cond == null || _stmtseq == null) throw new ParserException("Syntax error in while loop");
      while(true)
      {
        _cond.evaluation(ip);
        ComparableNode value = ip.popParamStack();
        if(value.isValue(0)) break;
        _stmtseq.evaluation(ip);
      }
  }
}
