package comicparser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import comicparser.Bean.Chapter;
import comicparser.Bean.ImageUrl;
import comicparser.Config.Const;
import comicparser.Utils.DecryptionUtils;
import comicparser.Utils.TextUtils;

public class DM5Parser extends Parser
{

	@Override
	public List<Chapter> getChapters(String comicUrl)
	{

		Document doc;
		try
		{
			doc = Jsoup.connect(comicUrl).ignoreContentType(true).userAgent(Const.agents[0]).timeout(10000).get();
			Element chapters_html = doc.body().getElementById("cbc_1");
			String title = doc.body().getElementById("mhinfo").getElementsByClass("new_sub").get(0).text();
			List<Chapter> chapters = new ArrayList<>();
			Chapter chapter;
			Elements els = chapters_html.getElementsByClass("tg");
			for (int i = els.size()-1; i >=0; i--)
			{
				Element element = els.get(i);
				chapter = new Chapter();
				chapter.setBelong(title);
				chapter.setIndex(i + "");
				chapter.setMid(element.attr("href").replace("/", ""));
				chapter.setName(element.attr("title"));
				chapter.setUrl(Const.URL_DM5 + chapter.getMid());
				chapters.add(chapter);
			}
			return chapters;

		} catch (IOException e1)
		{
			System.out.println(comicUrl + "\n解析错误,错误原因\n" + e1.toString());
		}

		return null;
	}

	@Override
	public List<ImageUrl> getImagers(Chapter chapter)
	{
		List<ImageUrl> imgs = null;
		try
		{

			int index = new Random().nextInt(4);
			Document doc_mh = Jsoup.connect(chapter.getUrl()).userAgent(Const.agents[index]).timeout(10000)
					.header("Connection", "keep-alive").get();
			imgs = parseImages(doc_mh.toString());
			System.out.println(chapter + "读取完毕，共" + imgs.size() + "张，开始解析图片");
			for (int i = 0; i < imgs.size(); i++)
			{
				ImageUrl urls = imgs.get(i);
				boolean hasGet = false;
				int count = 0;
				while (!hasGet)
				{
					try
					{

						Response res = Jsoup.connect(urls.getUrl()[0]).userAgent(Const.agents[index]).timeout(10000)
								.header("Connection", "keep-alive").header("Referer", Const.URL_DM5)
								.ignoreContentType(true).execute();
						String url_js = res.body();
						if (!TextUtils.isEmpity(url_js))
						{
							String page_url = DecryptionUtils.evalDecrypt(url_js).split(",")[0];
							imgs.get(i).setUrl(page_url);
							hasGet = true;
							Thread.sleep(new Random().nextInt(300));
							break;
						}
						else
						{
							System.out.println(url_js);
						}
					} catch (Exception e)
					{

						System.out.println(
								chapter + "第" + (i + 1) + "页第" + (count + 1) + "次解析错误,重新解析\n错误：" + e.toString());
						hasGet = false;

					}
					if (count >= 9)
					{
						System.out.println(chapter + "第" + (i + 1) + "页第" + (count + 1) + "次下载错误,跳过该页");
						break;
					}

				}

			}
			System.out.println(chapter.getName()+"解析完毕！");
		} catch (Exception e)
		{
			System.out.println(chapter.getName() + "解析错误\n错误：" + e.toString());

		}
		return imgs;
	}

	@Override
	public String getComicName(String menuUrl)
	{
		
		return null;
	}

	public List<ImageUrl> parseImages(String html)
	{
		List<ImageUrl> list = new ArrayList<>();
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

}
