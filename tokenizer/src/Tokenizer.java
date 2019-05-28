
import java.io.*;

import GrammerToken.GrammerTokenType;



/**
 *  Get the tokens from the input stream and displays the token number given above to the output stream one number per line.
 * @author Alison (Yutong) Liu
 */
public class Tokenizer 
{
  public static final String RESERVED_WORDS [] ={"program", "begin", "end", "int", "if", "then", "else", "while", 
"loop", "read", "write", "and", "or"};
    
  public static final String SPECIAL_SYMBOL [] = {";",",","=","!","[","]","(",")","+","-","*","!=","==",">=","<=",">","<"};
  
 
  public static final int TOKEN_RW[];
  static 
  {
    TOKEN_RW = new int[RESERVED_WORDS.length];
    
    for(int i=0; i<RESERVED_WORDS.length; i++)
    {
     TOKEN_RW[i] = i+1; 
    }
  }
  
  public static final int TOKEN_SS[];
  static
  {
    TOKEN_SS = new int[SPECIAL_SYMBOL.length];
    for(int i=0; i<SPECIAL_SYMBOL.length; i++)
    {
      TOKEN_SS[i] = i+RESERVED_WORDS.length;
    }
  }
  
  /**
   * assign value as globel vaule
   */
  
  //ID for Integer Token
  public static final int TOKEN_INT = SPECIAL_SYMBOL.length + SPECIAL_SYMBOL.length;
  public static final int MAX_INT =  8;
  
  //ID code for Identifier 
  public static final int TOKEN_IDENTIFIER = TOKEN_INT + 1;
  public static final int MAX_IDENTIFIER = 8;
  
  //ID code for EOF
  public static final int TOKEN_EOF = 33;
  
  //bad token signature
  public static final int BAD_ID_TOKEN = 0xFFFF;
  public static final int BAD_RW_TOKEN = 0xFFFE;
  public static final int BAD_SS_TOKEN = 0xFFFD;
  public static final int BAD_IN_TOKEN = 0xFFFC;
  
  //current line count. The line will be count at the 0xc and 0xd. 
  private int _currentLine = 1;
  private InputStream _rin = null;
  
  
  private GrammerToken _currentToken = null;
  
  
  //white space.
  private int _currentChar = 32;
  
  /**
   * Return whether the current token is a white space
   * @param ch
   * @return 
   */
  private boolean checkWhitespace(int ch)
  {
    if(Character.isWhitespace(ch))
    {
      if( ch == 0xd || ch == 0xc )  //ch == 0xa 
      { 
        _currentLine++;
      }
      return true;
    }
    return false;
  }
  
  /**
   * Return whether the current token is a special character
   * ';',',','=','!','[',']','(',')','+','-','*','!','>','<'
   * @param ch
   * @return 
   */
  private boolean isSpecialCharacter(int ch)
  {
    //!
    if(ch == 33) return true;
    //()*+,-
    else if( ch > 39 && ch < 46) return true;
    //;<=>
    else if( ch > 58 && ch < 63) return true;
    //[
    else if( ch == 91) return true;
    //]
    else if( ch == 93) return true;
    return false;
  }
  
  /**
   * break the CORE language into the into the reserved word categorie
   * @param ch
   * @return 
   */
  private GrammerToken getReservedWord(int ch) throws IOException
  {
    StringBuilder builder = new StringBuilder();
    builder.append((char)ch);
   
    while(((_currentChar = _rin.read()) != -1)&& !checkWhitespace(_currentChar))
    {
      if(isSpecialCharacter(_currentChar))
      {
        break;
      }
      else
      {
        builder.append((char)_currentChar);
      }
    }
    
    int flag = BAD_RW_TOKEN;
    String tokenvalue = builder.toString();
    for(int i=0; i<RESERVED_WORDS.length; i++)
    {
      if(RESERVED_WORDS[i].equals(tokenvalue))
      {
        flag = TOKEN_RW[i];
        break;
      }
    }
    return new GrammerToken(tokenvalue, flag, _currentLine, GrammerTokenType._rw_);
    
  }
  /**
   * break the CORE language into the into the identifier categories
   * @param ch
   * @return
   * @throws IOException 
   */
  private GrammerToken getIdentifier(int ch) throws IOException
  {
    StringBuilder builder = new StringBuilder();
    builder.append((char)ch);
   
    int flag = TOKEN_IDENTIFIER;
    int cnt = 1;
    while(((_currentChar = _rin.read()) != -1)&& !checkWhitespace(_currentChar))
    {
      if(isSpecialCharacter(_currentChar))
      {
        break;
      }
      if(ch <= 'A' || ch >='Z')
      {
        flag = BAD_ID_TOKEN;
      }
      builder.append((char)_currentChar);
      cnt++;
    }
    
    if(cnt > MAX_IDENTIFIER) flag = BAD_ID_TOKEN;
    
    String tokenvalue = builder.toString();
    return new GrammerToken(tokenvalue, flag, _currentLine, GrammerTokenType._id_);
  }
  /**
   *  break the CORE language into the into the integer categorie
   * @param ch
   * @return
   * @throws IOException 
   */
  private GrammerToken getInteger(int ch) throws IOException
  {
    StringBuilder builder = new StringBuilder();
    builder.append((char)ch);
   
    int flag = TOKEN_INT;
    int cnt = 1;
    while(((_currentChar = _rin.read()) != -1)&& !checkWhitespace(_currentChar))
    {
      if(isSpecialCharacter(_currentChar))
      {
        break;
      }
      if(!Character.isDigit(_currentChar))
      {
        flag = BAD_IN_TOKEN;
      }
      builder.append((char)_currentChar);
      cnt++;
    }
    
    if(cnt > MAX_INT) flag = BAD_IN_TOKEN;
    
    String tokenvalue = builder.toString();
    return new GrammerToken(tokenvalue, flag, _currentLine, GrammerTokenType._int_);
  }
    
  /**
   * get special symbol
   * @param ch
   * @return
   * @throws IOException 
   */
  private GrammerToken getSpecialSymbol(int ch) throws IOException
  {
    StringBuilder builder = new StringBuilder();
    builder.append((char)ch);
   
    
    _currentChar = _rin.read();
    if(isSpecialCharacter(_currentChar))
    {//first let's try if two character togather 
      builder.append((char)_currentChar);
      int flag = BAD_SS_TOKEN;
      String tokenvalue = builder.toString();
      for(int i=0; i<SPECIAL_SYMBOL.length; i++)
      {
        if(SPECIAL_SYMBOL[i].equals(tokenvalue))
        {
          flag = TOKEN_SS[i];
          break;
        }
      }
      if(flag != BAD_SS_TOKEN)
      {
        _currentChar = 32;
        return new GrammerToken(tokenvalue, flag, _currentLine, GrammerTokenType._ss_);
      }
      //reset the builder back to one char
      builder = new StringBuilder();
      builder.append((char)ch);
    }
    
    int flag = BAD_SS_TOKEN;
    String tokenvalue = builder.toString();
    for(int i=0; i<SPECIAL_SYMBOL.length; i++)
    {
      if(SPECIAL_SYMBOL[i].equals(tokenvalue))
      {
        flag = TOKEN_SS[i];
        break;
      }
    }
    return new GrammerToken(tokenvalue, flag, _currentLine, GrammerTokenType._ss_);
  }
  
  
  /**
   *  break the CORE language into the into the four categories
   * @return 
   * @throws java.io.IOException 
   */
  protected GrammerToken parserToken()
          throws IOException
  {

    //Note: here we have to call isWhitespace instead of check whitespace, otherwise we will double the line no 
    if(Character.isWhitespace(_currentChar))
    {
      while(((_currentChar = _rin.read()) != -1) && checkWhitespace(_currentChar));
    }
    
    if(_currentChar == -1)
    {
      return new GrammerToken(null, TOKEN_EOF, _currentLine, GrammerTokenType._eof_);
    }
    
    if(_currentChar >='a' && _currentChar <='z')
    {//check RESERVED_WORDS
      return getReservedWord(_currentChar);
    }
    else if(_currentChar >='A' && _currentChar<='Z')
    {//check Identifier
      return getIdentifier(_currentChar);
    }
    else if(Character.isDigit(_currentChar))
    {//check integer value
      return getInteger(_currentChar);
    }
    else
    {//check the SPECIAL_SYMBOL
      return getSpecialSymbol(_currentChar);
    }
  }
  
  /**
   * Get the current token
   * @return 
   */
  public GrammerToken currentToken()
  {
    if(_currentToken == null)
    {
      nextToken();
    }
    return _currentToken;
  }
  
  /**
   * move to next token
   */
  public void nextToken()
  {
    try
    {
      _currentToken = parserToken();
    }
    catch(IOException e)
    {
      _currentToken = null;
    }
  }
  
  /**
   * 
   * @param file
   * @throws FileNotFoundException 
   */
  public Tokenizer(String file) throws FileNotFoundException
  {
    _rin = new FileInputStream(file);
  }
  
  /**
   * This will print the usage requirements and exit.
   */
  private static void usage()
  {
    System.out.println("Usage: java Tokenizer program1.core");
  }
  
  /**
   * close the input source.
   */
  public void close()
  {
	  try
	  {
		  if(_rin != null)
		  {
			  _rin.close();
			  _rin = null;
		  }
	  }
	  catch(IOException ex)
	  {
		  
	  }
  }
  
  
  /**
   * Main function, it take one argument as test file name
   * @param argv
   */
  public static void main(String [] argv)
  {
    if(argv == null || argv.length < 1)
    {
      usage();
    }
    else
    {
      try 
      {
        Tokenizer tk = new Tokenizer(argv[0]);
        while(tk.currentToken() != null)
        {
          GrammerToken token = tk.currentToken();
          if(token.getTokenID() > TOKEN_EOF)
          {
            token.writeError(System.err);
            break;
          }
          else if(token.getTokenID() == TOKEN_EOF)
          {
            token.writeout(System.out);
            break;
          }
          else
          {
            token.writeout(System.out);
          }
          tk.nextToken();
        }
        tk.close();
      } 
      catch (IOException ex) 
      {
    	System.out.println("Incorrect file! please check your test file");
        usage();
      }
    }
  }
}