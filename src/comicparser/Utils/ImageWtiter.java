package comicparser.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 图片写入类
 * 
 * @author DJN
 *
 */
public class ImageWtiter
{
	/**
	 * 图片写入方法
	 * 
	 * @param url
	 *            图片url
	 * @param path
	 *            路径
	 * @param filename
	 *            图片名
	 * @param numbers
	 *            分隔数量（1~N）N<图片宽度
	 * @throws Exception
	 */
	public static void wtite(String url, String path, String filename, int numbers) throws Exception
	{
		URL image_url = null;
		HttpURLConnection con = null;
		BufferedImage image = null;
		image_url = new URL(url);
		con = (HttpURLConnection) image_url.openConnection();
		con.connect();
		image = ImageIO.read(con.getInputStream());
		int width = image.getWidth();
		int heigh = image.getHeight();
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		BufferedImage image_chip;
		for (int i = 0; i < numbers; i++)
		{
			file = new File(path + (i < 10 ? "00" : "0") + filename + "_" + (numbers - i) + ".jpg");
			int x = i == 0 ? 0 : width / numbers * i;// 左上角x
			int mwidth = i == numbers - 1 ? width - x : width / numbers;// 当前碎片宽度
			image_chip = image.getSubimage(x, 0, mwidth, heigh);
			// float quality = (float) ((mwidth / heigh) / 0.74F);//
			// if (quality > 1)//如果为首页
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
