package edu.osu.cse.parser;

import edu.osu.cse.interporater.ComparableNode;
import edu.osu.cse.interporater.Interporater;
import edu.osu.cse.interporater.Value;
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

    /**
     * 
     * @param ip
     * @throws ParserException 
     */
    @Override
    public void evaluation(Interporater ip) throws ParserException 
    {
      ArrayList<BaseNode> children = getChildren();
      if(children.size() < 1) throw new ParserException("Missing parameter when interporate the Cond node");
      BaseNode firstnode = children.get(0);
      
      if(firstnode instanceof GeneralSSNode)
      {//the first node has to be one of those ! [ (
        if(((GeneralSSNode)firstnode).isSameNode("!"))
        { //next should be the cond node
          if(children.size() != 2)
          {
            throw new ParserException("Incorrect Syntax when interporate the Cond node, it should be !<cond>");
          }
          children.get(1).evaluation(ip);
          ComparableNode value = ip.popParamStack();
          if(value.isValue(0))
          {
            ip.pushValue(new Value(1));
          }
          else
          {
            ip.pushValue(new Value(0));
          }
        }
        else if(((GeneralSSNode)firstnode).isSameNode("["))
        {
          if(children.size() < 4)throw new ParserException("Incorrect Syntax when interporate the Cond node, it should be [<cond> and/or <cond>]"); 
          BaseNode condnode = children.get(1);
          condnode.evaluation(ip);
          //now let get the value back
          ComparableNode value1 = ip.popParamStack();
          BaseNode andor = children.get(2);
          condnode = children.get(3);
          condnode.evaluation(ip);
          //now let get the value back
          ComparableNode value2 = ip.popParamStack();
          if(andor instanceof GeneralRWNode)
          {
            int value = 0;
            if(((GeneralRWNode)andor).isSameNode("and"))
            {
              value = value1.getValue() & value2.getValue();
            }
            else if(((GeneralRWNode)andor).isSameNode("or"))
            {
              value = value1.getValue() | value2.getValue();
            }
            else 
            {
              throw new ParserException("Incorrect keyword when interporate the Cond node, it should be [<cond> and/or <cond>]"); 
            }
            ip.pushValue(new Value(value));
          }
          else 
          {
            throw new ParserException("Incorrect Syntax when interporate the Cond node, it should be [<cond> and/or <cond>]"); 
          }
          
        }
        else
        {
          throw new ParserException("Incorrect Syntax when interporate the Cond node");
        }
      }
      else if(firstnode instanceof Comp)
      {
        firstnode.evaluation(ip);
      }
      else
      {
         throw new ParserException("Incorrect Syntax when interporate the Cond node");
      }
      
    }

}
