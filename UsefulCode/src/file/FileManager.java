package file;

import static util.ObjectUtils.isNotNull;
import static util.ObjectUtils.isNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import log.ILogger;
import log.SystemLogger;

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
			logger.error("source is null or target");
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
				logger.error("File is already exist");
				return false;
			}
			return file.createNewFile();
		} catch (IOException e) {
			logger.error("Create file failed",e);
			return false;
		}
	}

	/**
	 * * 创建文件
	 * 
	 * @param file
	 *            待创建文件
	 * @param inputStream
	 *            输入流
	 * @return 成功返回true，失败返回false
	 */
	public static boolean create(File file, InputStream inputStream) {
		if (isNull(file) || isNull(inputStream)) {
			logger.error("file is null or inputStream is null");
			return false;
		}
		if (file.exists()) {
			logger.error("File is already exist");
			return false;
		}
		return cover(file, inputStream);
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
		if (isNull(file) || isNull(content)) {
			logger.error("file is null or content is null");
			return false;
		}
		if (file.exists()) {
			logger.error("File is already exist");
			return false;
		}
		return cover(file, content);
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
		return cover(file, content);
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
		if (isNull(file) || isNull(inputStream)) {
			logger.error("file is null or inputStream is null");
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
	 * 覆盖文件
	 * 
	 * @param file
	 *            目标文件
	 * @param content
	 *            文件内容
	 * @return 成功返回true，失败返回false
	 */
	public static boolean cover(File file, String content) {
		if (isNull(file) || isNull(content)) {
			logger.error("file is null or content is null");
			return false;
		}
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(content.getBytes());
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
	 * * 覆盖文件
	 * 
	 * @param file
	 *            目标文件
	 * @param content
	 *            文件内容
	 * @return 成功返回true，失败返回false
	 */
	public static boolean cover(File file, byte[] content) {
		if (isNull(file) || isNull(content)) {
			logger.error("file is null or content is null");
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
			logger.error("file is nul or append is nulll");
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
			logger.error("file is nul or append is nulll");
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
			logger.error("file is nul or inputStream is nulll");
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
}
