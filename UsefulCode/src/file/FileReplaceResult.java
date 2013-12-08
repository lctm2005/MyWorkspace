package file;

import java.io.File;

/**
 * 文件内容替换结果
 * @author James
 *
 */
public class FileReplaceResult {
	
	private File file;		//替换的文件
	private int count;	//替换次数
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "FileReplaceResult [target=" + file.getAbsolutePath() + "," + count + " occurrence was replaced]";
	}
	
}
