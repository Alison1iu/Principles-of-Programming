
import java.io.*;

public class GrammerToken
{
	
  public enum GrammerTokenType
  {
	_unknown, _id_, _rw_, _int_, _ss_, _eof_  
  };	
	
  private int _tokenId;
  private String _value;
  private int _lineNo;
  private GrammerTokenType _type = GrammerTokenType._unknown;
  
  /**
   * Create a new instance of GrammerToken
   * @param value
   * @param flag
   * @param lineno
   * @param type
   */
  public GrammerToken(String value, int flag, int lineno, GrammerTokenType type)
  {
    _tokenId = flag;
    _value = value;
    _lineNo = lineno;
    _type = type;
  }
  

  /**
   * Get token type of this instance
   * @return
   */
  public GrammerTokenType getTokenType()
  {
	return _type;
  }
  
  /**
   * Get id code of this instance
   * @return
   */
  public int getTokenID()
  {
	return _tokenId;
  }
  
  /**
   * write output
   * @param out
   * @throws IOException
   */
  public void writeout(OutputStream out) throws IOException
  {
    StringBuilder builder = new StringBuilder();
    builder.append(Integer.toString(_tokenId)).append("\n");
    out.write(builder.toString().getBytes());
  }
  
  /**
   *  write error output
   * @param out
   * @throws IOException
   */
  public void writeError(OutputStream out) throws IOException
  {  
    String msg = "Unknown";
    //switch on enum doesn't build for some strange reason. let's do the old fashion
    if(_type == GrammerTokenType._id_)
    {
	  msg = "ID";
	}
    else if(_type == GrammerTokenType._int_)
	{
	  msg = "Integer";
	}
    else if(_type == GrammerTokenType._rw_)
    {
      msg = "Reserved Word";
    }
    else if(_type == GrammerTokenType._ss_)
    {
      msg = "Special Symbol";
    }
    else if(_type == GrammerTokenType._eof_)
    {
      msg = "End of File";	
    }
    StringBuilder builder = new StringBuilder();
    builder.append("Error: [Line ").append(Integer.toString(_lineNo)).append("] Invalid ");
    builder.append(msg).append(" token ?").append(_value).append("\n");
    out.write(builder.toString().getBytes());
  }
}