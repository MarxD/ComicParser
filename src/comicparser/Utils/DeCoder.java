package comicparser.Utils;

import java.util.LinkedList;
import java.util.List;

import comicparser.Bean.ImageUrl;

/**
 * 劳资直接从别人源码里复制过来的图片地址解析类
 * @author DJN
 *
 */
public class DeCoder
{
	public static List<ImageUrl> parseImages(String html)
	{
		List<ImageUrl> list = new LinkedList<>();
		String[] rs = TextUtils.match("var DM5_CID=(.*?);\\s*var DM5_IMAGE_COUNT=(\\d+);", html, 1, 2);
		if (rs != null)
		{
			String format = "http://www.dm5.com/m%s/chapterfun.ashx?cid=%s&page=%d";
			String packed = TextUtils.match("eval(.*?)\\s*</script>", html, 1);
			if (packed != null)
			{
				String key = TextUtils.match("comic=(.*?);", DecryptionUtils.evalDecrypt(packed), 1);
				if (key != null)
				{
					key = key.replaceAll("'|\\+", "");
					format = format.concat("&key=").concat(key);
				}
			}
			int page = Integer.parseInt(rs[1]);
			for (int i = 0; i != page; ++i)
			{
				list.add(new ImageUrl(i + 1, TextUtils.format(format, rs[0], rs[0], i + 1), true));
			}
		}
		return list;
	}

	public static List<String> getRealImages(List<ImageUrl> list)
	{
	
		return null;
	}
}
