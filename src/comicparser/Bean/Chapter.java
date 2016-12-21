package comicparser.Bean;

/**
 * 章节实体类
 * 
 * @author DJN
 *
 */
public class Chapter
{
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getMid()
	{
		return mid;
	}

	public void setMid(String mid)
	{
		this.mid = mid;
	}

	public String getBelong()
	{
		return belong;
	}

	public void setBelong(String belong)
	{
		this.belong = belong;
	}

	String name;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getIndex()
	{
		return index;
	}

	public void setIndex(String index)
	{
		this.index = index;
	}

	String url;
	String mid;
	String belong;
	String index;

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return belong+"-"+name;
	}
}
