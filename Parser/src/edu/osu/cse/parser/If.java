package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class If 
extends BaseNode
{
	public If(BaseNode parent)
	{
		super(Node_Type.if_node, Tokenizer.RW_IF, parent);
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
	protected boolean parsingValue(Tokenizer tokenizer)throws ParserException 
	{ //if <cond> then <stmt-seq> end;  
	  //| if <cond> then <stmt-seq> else <stmt-seq> end; 
		if(checkSignature(tokenizer))
		{
			//parsing "if"
			GeneralRWNode node = new GeneralRWNode(this);
			node.parsingValue(tokenizer);
			addChildNode(node);
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			//parsing <cond>
			Cond cond = new Cond(this);
			if(!cond.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<cond>"));
			addChildNode(cond);
			//_nodeSeperator.add(BaseNode.SEP_WHITESPACE);
			//parsing "then"
			if(!checkTokenType(tokenizer, Tokenizer.RW_THEN)) throw new ParserException(createErrorMessage(tokenizer, "then"));
			GeneralRWNode node1 = new GeneralRWNode(this);
			node1.parsingValue(tokenizer);
			addChildNode(node1);
			//_nodeSeperator.add(BaseNode.SEP_LINEBREAK);
			//parsing <stmt-seq>
			Stmt_Seq stmt = new Stmt_Seq(this);
			if(!stmt.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "then stmt-seq"));
			addChildNode(stmt);
			
			//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			//look for else
			if(checkTokenType(tokenizer, Tokenizer.RW_ELSE)) 
			{
				GeneralRWNode node2 = new GeneralRWNode(this);
				node2.parsingValue(tokenizer);
				addChildNode(node2);
				//_nodeSeperator.add(BaseNode.SEP_LINEBREAK);
				//parsing <stmt-seq>
				Stmt_Seq stmt1 = new Stmt_Seq(this);
				if(!stmt1.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "else <stmt-seq>"));
				addChildNode(stmt1);
				//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			}
			
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
}
