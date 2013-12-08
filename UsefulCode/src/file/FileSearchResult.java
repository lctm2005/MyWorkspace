package file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件搜索结果
 * @author James
 *
 */
public class FileSearchResult {

	private List<String> lines = new ArrayList<String>();
	private int count;
	private File file;
	
	public List<String> getLines() {
		return lines;
	}
	public void setLine(String line) {
		this.lines.add(line);
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("FileSearchResult[").append(count).append(" searched]\n");
		str.append("Occurrences:\n");
		for(String line : lines) {
			str.append(line).append("\n");
		}
		return str.toString();
	}
	
}
