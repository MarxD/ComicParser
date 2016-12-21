package comicparser.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils
{
	public static boolean isEmpity(String str)
	{
		if(str==null||str.trim().equals(""))
			return true;
		else
			return false;
		
	}
	
	public static boolean equals(String strA,String strB)
	{
		return strA.equals(strB);
	}
	
	/**
	 * 获取含补位的数字文件名 如 13→013
	 * @param maxPage 最大页数，以此为基准补0
	 * @param pageNumber 当前页数，等于最大页数时不补0
	 * @return 补全后的字符串
	 */
	public static String getFixNumber(int maxPage,int pageNumber)
	{
		StringBuilder builder = new StringBuilder(maxPage+"");
		int places = builder.length();
		builder = new StringBuilder(pageNumber+"");
		for(int i=0;i<places-builder.length();i++)
		{
			builder.insert(0, "0");
		}
		return builder.toString();
	}
	
	
	 public static boolean isEmpty(String... args) {
	        for (String arg : args) {
	            if (arg == null || arg.isEmpty()) {
	                return true;
	            }
	        }
	        return false;
	    }

	    public static String getSplit(String str, String regex, int position) {
	        String[] array = str.split(regex);
	        if (position < 0) {
	            position = array.length + position;
	        }
	        return position < 0 && position >= array.length ? null : array[position];
	    }

	    public static String format(String format, Object... args) {
	        return String.format(Locale.getDefault(), format, args);
	    }

	    public static String getProgress(int progress, int max) {
	        return format("%d/%d", progress, max);
	    }

	    public static String getDateStringWithSuffix(String suffix) {
	        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date()).concat(".").concat(suffix);
	    }

	    public static String match(String regex, String input, int group) {
	        try {
	            Pattern pattern = Pattern.compile(regex);
	            Matcher matcher = pattern.matcher(input);
	            if (matcher.find()) {
	                return matcher.group(group);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static String[] match(String regex, String input, int... group) {
	        try {
	            Pattern pattern = Pattern.compile(regex);
	            Matcher matcher = pattern.matcher(input);
	            if (matcher.find()) {
	                String[] result = new String[group.length];
	                for (int i = 0; i != result.length; ++i) {
	                    result[i] = matcher.group(group[i]);
	                }
	                return result;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static List<String> matchAll(String regex, String input, int group) {
	        LinkedList<String> list = new LinkedList<>();
	        try {
	            Pattern pattern = Pattern.compile(regex);
	            Matcher matcher = pattern.matcher(input);
	            while (matcher.find()) {
	                list.add(matcher.group(group));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	    /**
	     * 提示输入指定范围内的数字
	     * @param msg 提示信息
	     * @param startnumber 起始数字
	     * @param endnumber 结束数字
	     * @return
	     */
	    public static int getInsideNumber(String msg, int startnumber, int endnumber)
		{
			int number;
			while (true)
			{
				System.out.println(msg);
				try
				{
					int snumber = new Scanner(System.in).nextInt();
					number = Integer.valueOf(snumber);
					if (number > endnumber || number < startnumber)
					{
						System.out.println("请输入正确数字！");
					} else
					{
						break;
					}
				} catch (Exception e)
				{
					System.out.println("请输入正确数字！");
				}

			}
			return number;
		}
	    
}
