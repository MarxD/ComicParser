package comicparser.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSplitUtils
{
	/**
	 * 图片分割方法
	 * 
	 * @param path
	 *            输出文件路径
	 * @param filname
	 *            输出文件名 (主文件名，实际输出文件将以“文件名+下划线+分割数”,如传入 机器猫010 ，
	 *            分割数为2，则会产生两个文件--机器猫010_1.jpg，机器猫010_2.jpg)
	 * @param image
	 *            图片对象
	 * @param numbers
	 *            分割数量（横向）
	 * @throws IOException
	 *             读写错误
	 */
	public static void Split(String path, String filname, BufferedImage image, int numbers) throws IOException
	{
		int width = image.getWidth();
		int heigh = image.getHeight();
		File file = null;
		BufferedImage image_chip;
		for (int i = 0; i < numbers; i++)
		{
			file = new File(path + filname + "_" + (numbers - i) + ".jpg");
			// 我用的时候主要为了切割日漫，所以其他分页漫画如果选择切割的话左右会颠倒，不切割不影响
			// 可改为 new File(path + filname + "_" + (i+1) + ".jpg");

			int x = i == 0 ? 0 : width / numbers * i;
			// 左上角x，使用图片宽度直接除以片数

			int mwidth = i == numbers - 1 ? width - x : width / numbers;
			// 当前碎片宽度，图片可能不能整除，所以当为最后一片碎片的时候会直接计算剩下的宽度，使用计算值

			image_chip = image.getSubimage(x, 0, mwidth, heigh);
			// 一般使用只会用到横向切割，所以y坐标些为0，切片高度直接使用图片高度

			
			
			//下面的打码部分是缩放为kindle屏幕尺寸，实验后发现使用漫画转换工具更好，故注释，可删除
			
			// float quality = (float) ((mwidth / heigh) / 0.74F);
			// if (quality > 1)//如果为首页，则不缩放，直接切割
			ImageIO.write(image_chip, "JPEG", file);
			// else//拉伸为kindle屏幕尺寸
			// {
			// BufferedImage afterimage = new BufferedImage(1072, 1448,
			// BufferedImage.TYPE_INT_RGB);
			// Graphics2D g2d = afterimage.createGraphics();
			// g2d.drawImage(image_chip, 0, 0, 1072, 1448, null);
			// ImageIO.write(afterimage, "JPEG", file);
			// }

			// ScaleUtils.resize(image_chip, file, 1072, quality);
		}
	}

}
