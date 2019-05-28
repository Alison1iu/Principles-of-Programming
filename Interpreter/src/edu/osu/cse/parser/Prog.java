package edu.osu.cse.parser;

import edu.osu.cse.interporater.Interporater;
import java.util.ArrayList;

import edu.osu.cse.token.Tokenizer;
import java.io.IOException;
import java.io.OutputStream;

public class Prog 
extends BaseNode
{
    private Decl_Seq _decl = null;
    
    private Stmt_Seq _body = null;
  
    /**
     * 
     * @param parent 
     */
	public Prog(BaseNode parent)
	{
		super(Node_Type.prog, Tokenizer.RW_PROGRAM, parent);
	}
    
    /**
     * 
     * @param parent
     * @param ip 
     */
    public Prog(BaseNode parent, Interporater ip)
	{
		super(Node_Type.prog, Tokenizer.RW_PROGRAM, parent);
        _interporator = ip;
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
                _decl = del;
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
                _body = stmt;
				addChildNode(stmt);
				//_nodeSeperator.add(BaseNode.SEP_CONNECT);
			}
			if(!checkTokenType(tokenizer, Tokenizer.RW_END))
            {
              throw new ParserException(createErrorMessage(tokenizer, "id, if, while, read, or write"));
            }
            //throw new ParserException(createErrorMessage(tokenizer, "end"));
			GeneralRWNode end = new GeneralRWNode(this);
			end.parsingValue(tokenizer);
			addChildNode(end);
            end._indent = 2;
            
            if(tokenizer.currentToken() != null && tokenizer.currentToken().getTokenID() != Tokenizer.TOKEN_EOF )
            {
              throw new ParserException(createErrorMessage(tokenizer, "33"));
            }
            
            //_nodeSeperator.add(BaseNode.SEP_LINEBREAK);
			return true;
		}
		throw new ParserException(createErrorMessage(tokenizer, "prog"));
	}
    
    /**
     * 
     * @return 
     */
    public Decl_Seq getDecl()
    {
      return _decl;
    }
    
    /**
     * 
     * @return 
     */
    public Stmt_Seq getBody()
    {
      return _body;
    }

  @Override
  public void evaluation(Interporater ip) throws ParserException 
  {
    if(_decl != null) _decl.evaluation(ip);
    if(_body != null) _body.evaluation(ip);
  }
  
  /**
   * Create program tree;
   * @param tokenizer
   * @throws ParserException 
   */
  public void createTree(Tokenizer tokenizer) throws ParserException
  {
    parsingValue(tokenizer);
  }
  
  /**
   * Write the tree out with correct format
   * @param out
   * @throws IOException 
   */
  public void writeFormatedTree(OutputStream out) throws IOException
  {
    writeoutNode(out);
  }
		
}
