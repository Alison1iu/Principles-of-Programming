package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;

public class Stmt 
extends BaseNode
{	//<assign> | <if> | <loop> | <in> | <out>
	
	public static final ArrayList<Integer> STMT_OP_ID;
	static 
	{
		STMT_OP_ID = new ArrayList<>();
		STMT_OP_ID.add(Tokenizer.TOKEN_IDENTIFIER);
		STMT_OP_ID.add(Tokenizer.RW_IF);
		STMT_OP_ID.add(Tokenizer.RW_WHILE);
		STMT_OP_ID.add(Tokenizer.RW_READ);
		STMT_OP_ID.add(Tokenizer.RW_WRITE);
	}
	
	public Stmt(BaseNode parent)
	{
		super(Node_Type.stmt, 0, parent);
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
		return checkTypeLimit(tokenid, STMT_OP_ID);
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		return STMT_OP_ID;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer)throws ParserException 
	{
		if(checkSignature(tokenizer))
		{
			GrammerToken token = tokenizer.currentToken();
			if(token.getTokenID() == Tokenizer.TOKEN_IDENTIFIER)
			{
				Assign node = new Assign(this);
				if(!node.parsingValue(tokenizer)) return false;
				addChildNode(node);
				return true;
			}
			else if(token.getTokenID() == Tokenizer.RW_IF)
			{
				If node = new If(this);
				if(!node.parsingValue(tokenizer)) return false;
				addChildNode(node);
				return true;
			}
			else if(token.getTokenID() == Tokenizer.RW_WHILE)
			{
				Loop node = new Loop(this);
				if(!node.parsingValue(tokenizer)) return false;
				addChildNode(node);
				return true;
			}
			else if(token.getTokenID() == Tokenizer.RW_READ)
			{
				In node = new In(this);
				if(!node.parsingValue(tokenizer)) return false;
				addChildNode(node);
				return true;
			}
			else if(token.getTokenID() == Tokenizer.RW_WRITE)
			{
				Out node = new Out(this);
				if(!node.parsingValue(tokenizer)) return false;
				addChildNode(node);
				return true;
			}
			return false;
		}
		return false;
	}
}
