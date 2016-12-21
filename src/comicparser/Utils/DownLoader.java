package comicparser.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import comicparser.Bean.ImageUrl;
import comicparser.Config.Const;
import comicparser.Parser.DM5Parser;
import comicparser.Parser.Parser;

public class DownLoader
{
	// Referer:http://www.dm5.com

	public static String getImageUrl(String url, int comicIndex)
	{

		String result_msg;
		try
		{

			int index = new Random().nextInt(4);
			Document doc_mh = Jsoup.connect(url).userAgent(Const.agents[index]).timeout(10000)
					.header("Connection", "keep-alive").get();
			String chapter = TextUtils.match("var DM5_CTITLE = \"(.*?)\";", doc_mh.toString(), 1);
			File file_dir = new File(Const.COMICS_PATH + chapter);
			if (!file_dir.exists())
				file_dir.mkdir();
			List<ImageUrl> imgs;
			imgs = DeCoder.parseImages(doc_mh.toString());
			System.out.println(chapter + "读取完毕，共" + imgs.size() + "张，开始解析图片");
			for (int i = 0; i < imgs.size(); i++)
			{
				ImageUrl urls = imgs.get(i);
				boolean hasGet = false;
				URL image_url = null;
				HttpURLConnection con = null;
				BufferedImage image = null;
				int count = 0;
				while (!hasGet)
				{
					try
					{

						Response res = Jsoup.connect(urls.getUrl()[0]).userAgent(Const.agents[index])
								.header("Connection", "keep-alive").header("Referer", Const.URL_DM5)
								.ignoreContentType(true).execute();
						String url_js = res.body();
						if (!TextUtils.isEmpity(url_js))
						{
							String page_url = DecryptionUtils.evalDecrypt(url_js).split(",")[0];
							image_url = new URL(page_url);
							con = (HttpURLConnection) image_url.openConnection();
							con.connect();
							image = ImageIO.read(con.getInputStream());
							ImageSplitUtils.Split(file_dir.getAbsolutePath() + "/", i + "", image, 2);
							hasGet = true;
							Thread.sleep(2000);
							break;
						}
						Thread.sleep(1000);
					} catch (Exception e)
					{
						result_msg = e.toString();
						System.out.println(
								chapter + "第" + (i + 1) + "页第" + (count + 1) + "次下载错误,重新解析\n错误：" + e.toString());
						hasGet = false;

					}
					if (count >= 9)
					{
						System.out.println(chapter + "第" + (i + 1) + "页第" + (count + 1) + "次下载错误,跳过该页");
						break;
					}

				}

			}
			result_msg = chapter + "下载完成";

		} catch (Exception e)
		{
			result_msg = e.toString();
			System.out.println(url + "解析错误\n错误：" + e.toString());

		}
		return result_msg;
	}

}