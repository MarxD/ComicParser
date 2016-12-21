package comicparser.Thread;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import comicparser.Bean.Chapter;
import comicparser.Bean.ImageUrl;
import comicparser.Config.Const;
import comicparser.Parser.Parser;
import comicparser.Utils.ImageWtiter;
import comicparser.Utils.TextUtils;

public class TaskThread implements Callable<String>
{

	private Chapter chapter;
	private Parser parser;

	public TaskThread(Chapter chapter, Parser parser)
	{
		this.chapter = chapter;
		this.parser = parser;
	}

	@Override
	public String call() throws Exception
	{

		List<ImageUrl> pages = parser.getImagers(chapter);
		for (int i = 0; i < pages.size(); i++)
		{
			ImageUrl page = pages.get(i);
			try
			{
				String pageNumber = TextUtils.getFixNumber(pages.size(), i + 1);
				ImageWtiter.wtite(page.getFirstUrl(), Const.COMICS_PATH + chapter.getName() + "/", pageNumber,
						Const.cutNumber);
				System.out.println(chapter.getName() + "第【" + i + "】张下载完毕");
				Thread.sleep(new Random().nextInt(1000) + 500);
			} catch (Exception e)
			{
				System.out.println(chapter.getName() + "第" + (i + 1) + "张下载错误,错误为\n" + e.toString());
			}
		}
		return "【" + chapter + "】下载结束";
	}

}
