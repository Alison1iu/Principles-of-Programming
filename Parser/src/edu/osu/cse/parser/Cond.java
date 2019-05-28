package edu.osu.cse.parser;

import java.util.ArrayList;

import edu.osu.cse.token.GrammerToken;
import edu.osu.cse.token.Tokenizer;

public class Cond 
extends BaseNode
{
	private static final ArrayList<Integer> COND_START_ID;
	static 
	{
		COND_START_ID = new ArrayList<>();
		COND_START_ID.add(Tokenizer.SS_NOTEQUAL);
		COND_START_ID.add(Tokenizer.SS_lBOX_BRACKET);
		//comp signature
		COND_START_ID.add(Tokenizer.SS_l_BRACKET);
	}
	
	public Cond(BaseNode parent)
	{
		super(Node_Type.cond, 0, parent);
	}

	@Override
	protected boolean isTokenTypeMatch(int tokenid) 
	{
		return checkTypeLimit(tokenid, COND_START_ID);
	}

	@Override
	protected ArrayList<Integer> getNodeTypeLimit() 
	{
		// TODO Auto-generated method stub
		return COND_START_ID;
	}

	@Override
	protected boolean parsingValue(Tokenizer tokenizer) throws ParserException
	{
		if(checkSignature(tokenizer))
		{ //<comp>  | !<cond> 
		//| [ <cond> and <cond> ]  | [ <cond> or <cond> ]  
			GrammerToken token = tokenizer.currentToken();
			if(token.getTokenID() == Tokenizer.SS_NOTEQUAL)
			{//!<cond>
				GeneralSSNode node = new GeneralSSNode(this);
				node.parsingValue(tokenizer);
				addChildNode(node);
				//AddNodeToLineFormat(node, NodeLineFormat.SEP_CONNECT);
                
				Cond cond1 = new Cond(this);
				if(!cond1.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<cond>"));
				addChildNode(cond1);
                //AddNodeToLineFormat(cond1);
				//let's output it as !()
				return true;
			}
			else if(token.getTokenID() == Tokenizer.SS_lBOX_BRACKET)
			{ //[ <cond> and <cond> ]  | [ <cond> or <cond> ]
				
				//parsing '['
				GeneralSSNode node = new GeneralSSNode(this);
				node.parsingValue(tokenizer);
				addChildNode(node);
                //AddNodeToLineFormat(node);
				//parsing <cond>
				Cond cond1 = new Cond(this);
				if(!cond1.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<cond>"));
				addChildNode(cond1);
				//AddNodeToLineFormat(cond1);
				//parsing "and" / "or"
				if(checkTokenType(tokenizer, Tokenizer.RW_AND) 
						|| checkTokenType(tokenizer, Tokenizer.RW_OR))
				{
					GeneralRWNode node1 = new GeneralRWNode(this);
					node1.parsingValue(tokenizer);
					addChildNode(node1);
					//AddNodeToLineFormat(node1);
				}
				else
				{
					throw new ParserException(createErrorMessage(tokenizer, "and/or"));
				}
				//parsing <cond>
				Cond cond2 = new Cond(this);
				if(!cond2.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<cond>"));
				addChildNode(cond2);
				//AddNodeToLineFormat(cond2);
				//parsing ']'
				if(!checkTokenType(tokenizer, Tokenizer.SS_RBOX_BRACKET)) throw new ParserException(createErrorMessage(tokenizer, "]"));
				GeneralSSNode node2 = new GeneralSSNode(this);
				node2.parsingValue(tokenizer);
				addChildNode(node2);
				//AddNodeToLineFormat(node2);
				return true;
				
			}
			else if(token.getTokenID() == Tokenizer.SS_l_BRACKET)
			{//<comp>
				Comp compnode = new Comp(this);
				if(!compnode.parsingValue(tokenizer)) throw new ParserException(createErrorMessage(tokenizer, "<comp>"));
				addChildNode(compnode);
                //AddNodeToLineFormat(compnode);
				return true;
			}
		}
		return false;
	}

}
