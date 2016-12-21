package comicparser.Parser;

import java.util.ArrayList;
import java.util.List;

import comicparser.Bean.Chapter;
import comicparser.Bean.ImageUrl;

public abstract class Parser
{
	/**
	 * 获取漫画章节
	 * @param menuUrl 目录页地址
	 * @return 章节列表
	 */
	public abstract  List<Chapter> getChapters(String menuUrl);
	
	/**
	 *  获取某一章节所有图片地址
	 * @param chapterUrl 章节地址
	 * @return
	 */
	public abstract List<ImageUrl> getImagers(Chapter chapter);
	
	/**
	 * 获取漫画名
	 * @param menuUrl 目录页地址
	 * @return 漫画名
	 */
	public abstract String getComicName(String menuUrl);
	
	

}
