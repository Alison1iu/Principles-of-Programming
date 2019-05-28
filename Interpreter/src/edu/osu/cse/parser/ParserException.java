package edu.osu.cse.parser;

import edu.osu.cse.token.GrammerToken;

public class ParserException extends Exception
{	

	public ParserException(String error)
	{
		super(error);
	}
    
    /**
     * token can;t be null
     * @param token 
     */
    public ParserException(GrammerToken token)
    {
      super(token.getErrorMessage());
    }
    
}
