package comicparser.Exception;

public class MyException extends Exception
{
	String error;

	public MyException(String str)
	{
		this.error = str;
	}
	
	@Override
	public String toString()
	{
		return error.toString();
	}

}
