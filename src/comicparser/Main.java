package comicparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import comicparser.Bean.Chapter;
import comicparser.Config.Const;
import comicparser.Parser.DM5Parser;
import comicparser.Parser.Parser;
import comicparser.Thread.TaskThread;
import comicparser.Utils.TextUtils;

public class Main
{

	public static void main(String[] args)
	{

		try
		{
			System.out.println("输入漫画目录页地址");
			// 解析
			String comicUrl = new Scanner(System.in).nextLine();
			Parser parser = new DM5Parser();
			List<Chapter> chapters = parser.getChapters(comicUrl);
			System.out.println(chapters.get(0).getBelong() + "解析完毕\n共" + chapters.size() + "章节");
			int snumber = TextUtils.getInsideNumber("输入起始章节：", 1, chapters.size());
			int enumber = TextUtils.getInsideNumber("输入结束章节：", snumber, chapters.size());
			int cutNumber = TextUtils.getInsideNumber("请输入切割数量（仅横向,1到4）", 1, 4);
			Const.cutNumber = cutNumber;
			ExecutorService service = Executors.newFixedThreadPool(2);
			ArrayList<Future<String>> futures = new ArrayList<>();
			for (int i = snumber - 1; i < enumber; i++)
			{
				futures.add(service.submit(new TaskThread(chapters.get(i), parser)));
			}

			while (true)
			{
				boolean allDone = true;
				for (Future<String> future : futures)
				{
					if (!future.isDone())
						allDone = false;
				}
				if (allDone)
				{
					System.out.println("下载完成");
					service.shutdown();
					break;
				}

			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		System.gc();

	}

}
