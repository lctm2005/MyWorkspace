package file;

import static util.ObjectUtils.isNotNull;
import static util.ObjectUtils.isNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import log.ILogger;
import log.SystemLogger;
import util.StringUtils;

/**
 * 文件管理器
 * 
 * @author James-li
 * 
 *         2013-6-26
 */
public class FileManager {

	private static ILogger logger = new SystemLogger(FileManager.class);

	/**
	 * 重命名
	 * @param source		源文件
	 * @param newName  新名字
	 * @return
	 */
	public static boolean rename(File source, String newName) {
		if (isNull(source) || StringUtils.isEmpty(newName)) {
			logger.error("source is null or newName is empty");
			return false;
		}
		if (!source.exists()) {
			logger.error(source.getAbsolutePath() + " is not exist");
			return false;
		}
		String path = source.getAbsolutePath();
		String name = source.getName();
		int index = name.indexOf('.');
		if(-1 != index) {
			return source.renameTo(new File(path.substring(0, path.indexOf(name)) + File.separator + newName +name.substring(index) ));
		}
		return source.renameTo(new File(path.substring(0, path.indexOf(name)) + File.separator + newName));
	}
	
	/**
	 * 复制文件
	 * 
	 * @param source
	 *            源文件
	 * @param target
	 *            目的文件
	 * @return 成功返回true，失败返回false
	 */
	public static boolean copy(File source, File target) {
		if (isNull(source) || isNull(target)) {
			logger.error("source is null or target is null");
			return false;
		}
		if (!source.exists()) {
			logger.error(source.getAbsolutePath() + " is not exist");
			return false;
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(source));
			bos = new BufferedOutputStream(new FileOutputStream(target));
			byte[] buffer = new byte[1024 * 5];
			int len;
			while ((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
			return true;
		} catch (IOException e) {
			logger.error("Copy file failed", e);
			return false;
		} finally {
			if (isNotNull(bis)) {
				try {
					bis.close();
				} catch (IOException e) {
					logger.error("Close BufferedInputStream failed", e);
				}
			}
			if (isNotNull(bos)) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("Close BufferedOutputStream failed", e);
				}
			}
		}
	}

	/**
	 * 剪切文件
	 * 
	 * @param source
	 *            源文件
	 * @param target
	 *            目的文件
	 * @return 成功返回true，失败返回false
	 */
	public static boolean cut(File source, File target) {
		if (copy(source, target)) {
			delete(source);
			return true;
		}
		return false;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 *            待删除文件
	 * @return 成功返回true，失败返回false
	 */
	public static boolean delete(File file) {
		if (isNull(file)) {
			logger.error("file is null");
			return false;
		}
		if (file.exists()) {
			return file.delete();
		}
		return true;
	}
	

	/**
	 * * 创建空文件
	 * 
	 * @param file
	 *            待创建文件
	 * @return 成功返回true，失败返回false
	 */
	public static boolean create(File file) {
		if (isNull(file)) {
			logger.error("file is null");
			return false;
		}
		try {
			if(file.exists()) {
				logger.error("File is already exist:" + file.getAbsolutePath());
				return false;
			}
			return file.createNewFile();
		} catch (IOException e) {
			logger.error("Create file failed",e);
			return false;
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 *            待创建文件
	 * @param inputStream
	 *            输入流
	 * @return 成功返回true，失败返回false
	 */
	public static boolean create(File file, InputStream inputStream) {
		if (isNull(inputStream)) {
			logger.error("inputStream is null");
			return false;
		}
		if(!create(file)) {
			return false;
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(inputStream);
			bos = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buffer = new byte[1024 * 5];
			int len;
			while ((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
			return true;
		} catch (IOException e) {
			logger.error("Create file failed", e);
			return false;
		} finally {
			if (isNotNull(bis)) {
				try {
					bis.close();
				} catch (IOException e) {
					logger.error("Close BufferedInputStream failed", e);
				}
			}
			if (isNotNull(bos)) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("Close BufferedOutputStream failed", e);
				}
			}
		}
	}

	/**
	 * * 创建文件
	 * 
	 * @param file
	 *            待创建文件
	 * @param content
	 *            文件内容
	 * @return 成功返回true，失败返回false
	 */
	public static boolean create(File file, byte[] content) {
		if (isNull(file) || isNull(content)) {
			logger.error("file is null or content is null");
			return false;
		}
		if (file.exists()) {
			logger.error("File is already exist");
			return false;
		}
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(content);
			bos.flush();
			return true;
		} catch (IOException e) {
			logger.error("Create file failed", e);
			return false;
		} finally {
			if (isNotNull(bos)) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("Close BufferedOutputStream failed", e);
				}
			}
		}
	}
	
	/**
	 * * 创建文件
	 * 
	 * @param file
	 *            待创建文件
	 * @param content
	 *            文件内容
	 * @return 成功返回true，失败返回false
	 */
	public static boolean create(File file, String content) {
		return create(file, content.getBytes());
	}
	

	/**
	 * 覆盖文件
	 * 
	 * @param file
	 *            目标文件
	 * @param inputStream
	 *            输入流
	 * @return 成功返回true，失败返回false
	 */
	public static boolean cover(File file, InputStream inputStream) {
		if(!delete(file)) {
			return false;
		}
		return create(file, inputStream);
	}

	/**
	 * 覆盖文件
	 * 
	 * @param file
	 *            目标文件
	 * @param content
	 *            文件内容
	 * @return 成功返回true，失败返回false
	 */
	public static boolean cover(File file, String content) {
		if(!delete(file)) {
			return false;
		}
		return create(file, content);
	}

	/**
	 * * 覆盖文件
	 * 
	 * @param file
	 *            目标文件
	 * @param content
	 *            文件内容
	 * @return 成功返回true，失败返回false
	 */
	public static boolean cover(File file, byte[] content) {
		if(!delete(file)) {
			return false;
		}
		return create(file, content);
	}

	/**
	 * 追加文件
	 * 
	 * @param file
	 *            目标
	 * @param append
	 *            追加内容
	 * @return 成功返回true，失败返回false
	 */
	public static boolean append(File file, String append) {
		if (isNull(file) || isNull(append)) {
			logger.error("file is nul or append is null");
			return false;
		}
		RandomAccessFile randomFile = null;
		try {
			randomFile = new RandomAccessFile(file, "rw");
			long fileLength = randomFile.length();
			randomFile.seek(fileLength);
			randomFile.write(append.getBytes());
			return true;
		} catch (IOException e) {
			logger.error("Append file failed", e);
			return false;
		} finally {
			if (isNotNull(randomFile)) {
				try {
					randomFile.close();
				} catch (IOException e) {
					logger.error("Close RandomAccessFile failed", e);
				}
			}
		}
	}

	/**
	 * 追加文件
	 * 
	 * @param file
	 *            目标
	 * @param append
	 *            追加内容
	 * @return 成功返回true，失败返回false
	 */
	public static boolean append(File file, byte[] append) {
		if (isNull(file) || isNull(append)) {
			logger.error("file is nul or append is null");
			return false;
		}
		RandomAccessFile randomFile = null;
		try {
			randomFile = new RandomAccessFile(file, "rw");
			long fileLength = randomFile.length();
			randomFile.seek(fileLength);
			randomFile.write(append);
			return true;
		} catch (IOException e) {
			logger.error("Append file failed", e);
			return false;
		} finally {
			if (isNotNull(randomFile)) {
				try {
					randomFile.close();
				} catch (IOException e) {
					logger.error("Close RandomAccessFile failed", e);
				}
			}
		}
	}

	/**
	 * 追加文件
	 * 
	 * @param file
	 *            目标
	 * @param inputStream
	 *            追加输入流
	 * @return 成功返回true，失败返回false
	 */
	public static boolean append(File file, InputStream inputStream) {
		if (isNull(file) || isNull(inputStream)) {
			logger.error("file is nul or inputStream is null");
			return false;
		}
		BufferedInputStream bis = null;
		RandomAccessFile randomFile = null;
		try {
			bis = new BufferedInputStream(inputStream);
			randomFile = new RandomAccessFile(file, "rw");
			long fileLength = randomFile.length();
			randomFile.seek(fileLength);
			byte[] buffer = new byte[1024 * 5];
			int len;
			while ((len = bis.read(buffer)) != -1) {
				randomFile.write(buffer, 0, len);
			}
			return true;
		} catch (IOException e) {
			logger.error("Append file failed", e);
			return false;
		} finally {
			if (isNotNull(bis)) {
				try {
					bis.close();
				} catch (IOException e) {
					logger.error("Close BufferedInputStream failed", e);
				}
			}
			if (isNotNull(randomFile)) {
				try {
					randomFile.close();
				} catch (IOException e) {
					logger.error("Close RandomAccessFile failed", e);
				}
			}
		}
	}
	
	/**
	 * 替换文本内容
	 * @param file			目标文件
	 * @param target		查找目标
	 * @param replaceText	替换为
	 * @param result		替换结果，当不关心结果时可以为null
	 * @return 成功返回true，失败返回false
	 * @see FileReplaceResult
	 */
	public static boolean replace(File file, String target, String replaceText, FileReplaceResult result) {
		//TODO 对目录的情况
		if (isNull(file) || StringUtils.isEmpty(target) || isNull(replaceText)) {
			logger.error("file is nul or target is empty or replaceText is null");
			return false;
		}
		BufferedReader br = null;
		BufferedWriter bw = null;
		//创建临时文件
		File temp = new File(file.getAbsolutePath() + ".tmp");
		create(temp);
		int count = 0;
		if(isNotNull(result)) {
			result.setFile(file);
		}
		try {
			br = new BufferedReader(new FileReader(file));
			bw = new BufferedWriter(new FileWriter(temp));
			String line = null;
			while(StringUtils.isNotEmpty(line = br.readLine())) {
				String newLine = line.replaceAll(target, replaceText);
				if(isNotNull(result) && !newLine.equals(line)) {
					count++;
				}
				bw.write(newLine + "\n");
			}
			if(isNotNull(result)) {
				result.setCount(count);
			}
		} catch (FileNotFoundException e) {
			logger.error("Not found " + file.getAbsolutePath(), e);
			return false;
		} catch (IOException e) {
			logger.error("replace " + file.getAbsolutePath() + " failed" , e);
			return false;
		}finally {
			if(isNotNull(br)) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Close BufferedReader failed", e);
				}
			}
			if(isNotNull(bw)) {
				try {
					bw.close();
				} catch (IOException e) {
					logger.error("Close BufferedReader failed", e);
				}
			}
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(temp);
			if(!cover(file,fis)) {
				return false;
			}
		} catch (FileNotFoundException e) {
			logger.error("Not found " + temp.getAbsolutePath(), e);
			return false;
		} finally {
			if(isNotNull(fis)) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Close FileInputStream failed", e);
				}
			}
		}
		return delete(temp);
	}
	
	/**
	 * 搜索文本内容
	 * @param file			目标文件
	 * @param target		查找目标
	 * @param replaceText	替换为
	 * @param result		搜索结果，当不关心结果时可以为null
	 * @return 成功返回true，失败返回false
	 * @see FileReplaceResult
	 */
	public static boolean search(File file, String target, FileSearchResult result) {
		//TODO 对目录的情况
		if (isNull(file) || StringUtils.isEmpty(target) || isNull(result)) {
			logger.error("file is nul or target is empty or result is null");
			return false;
		}
		BufferedReader br = null;
		//创建临时文件
		int count = 0;
		result.setFile(file);
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while(StringUtils.isNotEmpty(line = br.readLine())) {
				if(-1 != line.indexOf(target)) {
					result.setLine(line);
					count++;
				}
			}
			if(isNotNull(result)) {
				result.setCount(count);
			}
		} catch (FileNotFoundException e) {
			logger.error("Not found " + file.getAbsolutePath(), e);
			return false;
		} catch (IOException e) {
			logger.error("replace " + file.getAbsolutePath() + " failed" , e);
			return false;
		}finally {
			if(isNotNull(br)) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Close BufferedReader failed", e);
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		FileSearchResult result = new FileSearchResult();
		search(new File("D:\\h.log"), "File", result);
		System.out.println(result);
	}
	
}
