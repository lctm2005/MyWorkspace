package ipseeker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 资源管理类
 * 
 * @author James-li
 *
 * 2012-8-24
 */
public class IPFileManager {

	private RandomAccessFile ipFile;	// 随机文件访问类
	
	private final byte[] buf;
	private final byte[] b3;
	
	//纯真IP文件路径
	private static final String IP_FILE = IPSeeker.class
			.getResource("QQWry.dat").toString().substring(5);
	
	public IPFileManager(){
		try {
			ipFile = new RandomAccessFile(IP_FILE, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(IPSeeker.class.getResource("QQWry.dat"));
			System.out.println(IP_FILE);
			System.out.println(" IP地址信息文件没有找到，IP显示功能将无法使用");
			ipFile = null;
		}
		b3 = new byte[3];
		buf = new byte[100];
	}
	
	/**
	 * 获取原始资源ipFile
	 * @return
	 */
	public RandomAccessFile getIpFile(){
		return ipFile;
	}
	
	/**
	 * 根据移动文件游标到pos所指的偏移处
	 * @param pos
	 * @throws IOException
	 */
	public void seek(long pos) throws IOException {
		ipFile.seek(pos);
	}
	
	/**
	 * 从文件当前位置读取1字节
	 * @return
	 * @throws IOException
	 */
	public byte readByte() throws IOException {
		return ipFile.readByte();
	}
	
	/**
	 * Returns the current offset in this file.
	 * @return
	 * @throws IOException
	 */
	public long getFilePointer() throws IOException {
		return ipFile.getFilePointer();
	}
	
	/**
	 * 判断文件是否已打开
	 * @return
	 */
	public boolean isFileOpened(){
		return ipFile != null;
	}
	
	/**
	 * 关闭文件
	 */
	public void closeFile(){
		try {
			ipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			closeFile();
		} 
		ipFile = null;
	}
	
	/**
	 * 从offset位置读取4个字节为一个long，因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
	 * @param offset
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	public long readLong4(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ret |= (ipFile.readByte() & 0xFF);
			ret |= ((ipFile.readByte() << 8) & 0xFF00);
			ret |= ((ipFile.readByte() << 16) & 0xFF0000);
			ret |= ((ipFile.readByte() << 24) & 0xFF000000);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 从offset位置读取3个字节为一个long，
	 * 因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
	 * 
	 * @param offset
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	public long readLong3(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 从当前位置读取3个字节转换成long
	 * 
	 * @return
	 */
	public long readLong3() {
		try {
			return readLong3(ipFile.getFilePointer());
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 从offset位置读取四个字节的ip地址放入ip数组中，
	 * 读取后的ip为big-endian格式，但是文件中是little-endian形式，将会进行转换
	 * 
	 * @param offset
	 * @param ip
	 */
	public void readIP(long offset, byte[] ip) {
		try {
			ipFile.seek(offset);
			ipFile.readFully(ip);
			byte temp = ip[0];
			ip[0] = ip[3];
			ip[3] = temp;
			temp = ip[1];
			ip[1] = ip[2];
			ip[2] = temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从offset偏移处读取一个以0结束的字符串
	 * 
	 * @param offset
	 * @return 读取的字符串，出错返回空字符串
	 */
	public String readString(long offset) {
		try {
			ipFile.seek(offset);
			int i;
			for (i = 0, buf[i] = ipFile.readByte(); buf[i] != 0; buf[++i] = ipFile.readByte());
			if (i != 0)
				return Utils.getString(buf, 0, i, "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
