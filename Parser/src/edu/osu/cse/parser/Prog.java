package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;

public class Prog 
extends BaseNode
{
	public Prog(BaseNode parent)
	{
		super(Node_Type.prog, Tokenizer.RW_PROGRAM, parent);
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
		if(checkSignature(tokenizer))
		{
			GeneralRWNode prog = new GeneralRWNode(this);
			prog.parsingValue(tokenizer);
			addChildNode(prog);
            
            
			//_nodeSeperator.add(BaseNode.SEP_LINEBREAK);
			//now let's see if we have Decl_Seq
			Decl_Seq del = new Decl_Seq(this);
            if(del.parsingValue(tokenizer))
			{
				addChildNode(del);
				//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			}
			
			if(!checkTokenType(tokenizer, Tokenizer.RW_BEGIN))throw new ParserException(createErrorMessage(tokenizer, "begin"));
			GeneralRWNode begin = new GeneralRWNode(this);
			begin.parsingValue(tokenizer);
			addChildNode(begin);
            begin._indent = 2;
			//_nodeSeperator.add(BaseNode.SEP_LINEBREAK);
			Stmt_Seq stmt = new Stmt_Seq(this);
            stmt._indent = 2;
			if(stmt.parsingValue(tokenizer))
			{
				addChildNode(stmt);
				//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			}
			
			if(!checkTokenType(tokenizer, Tokenizer.RW_END))throw new ParserException(createErrorMessage(tokenizer, "end"));
			GeneralRWNode end = new GeneralRWNode(this);
			end.parsingValue(tokenizer);
			addChildNode(end);
            end._indent = 2;
            //_nodeSeperator.add(BaseNode.SEP_LINEBREAK);
			return true;
		}
		throw new ParserException(createErrorMessage(tokenizer, "prog"));
	}
		
}
