package comicparser.Utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IOUtils
{
	static OkHttpClient client = new OkHttpClient();
	public  static void main(String[] args)
	{
		
		try
		{
			System.out.println(run("http://www.dm5.com/m6445/"));
		} catch (IOException e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}
	
	static String run(String url) throws IOException {
		  Request request = new Request.Builder()
		      .url(url)
		      .build();

		  Response response = client.newCall(request).execute();
		  return response.body().string();
		}

}
