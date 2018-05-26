package ch2.lucenedemo.preprocess;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class FileProprecess {
 /**
 * 两个参数，一个是要被处理的源文件
 * 另一个是吃力后的文件输出路径
 */
public static void preprocess(File file,String output) {
	try {
		splitToSmallFiles(charactorProcess(file,outputDir+"output.all"),outputDir);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
 /**
  * 对文件字符进行全角/半角处理
  */
public static File charactorProcess(File file,String destFile)
     throws Exception {
	BufferedWriter writer = new BufferedWriter(new FileWriter(destFile));
	BufferedReader reader = new BufferedReader(new FileReader(file));
	String line = reader.readLine();
	while (line != null) {
		if (!line.equals("\r\n"))  {
			String newline = replace(line);
			writer.write(newline);
			writer.newLine();
		}
	    line = reader.readLine();
	}
	reader.close();
	writer.close();
	
	return new File(destFile);
}

/**
 *拆分成小文件
 */

public static void splitToSmallFiles(File file,String outputpath) throws IOException {
	
	int filePointer = 0;
	
	int MAX_SIZE = 10240;
	
	BufferedWriter writer = null;
	BufferedReader reader = new BufferedReader(new FileReader(file));
	StringBuffer buffer = new StringBuffer();
	String Line = reader.readLine();
	
	while  (line != null)  {
		buffer.append(line).append("\r\n");
		if (buffer.toString().getBytes().length >= MAX_SIZE)
		{
			writer = new BufferedWriter(new FileWriter(outputpath + "output" + filePointer + ".txt"));
			writer.write(buffer.toString());
			writer.close();
			filePointer++;
			
			buffer = new StringBuffer();
		}
		line = reader.readLine();
	}
	writer = new BufferedWriter(new FileWriter(outputpath + "output" + filePointer + ".txt"));
	writer.write(buffer.toString());
	writer.close();
}
 /**
  * 全角\半角的转换
  */
private static String replace(String line)  {
	HashMap map = new HashMap();
	map.put(",","、");
	map.put("，",".");
	
	int length = line.length();
	for (int i = 0 ; i<length; i++)  {
		String charact = line.substring(i, i + 1);
		if (map.get(charat) != null) {
			line = line.replace(charat,(String) map.get(charat));
			
		}
	}
	return line;
}
}
