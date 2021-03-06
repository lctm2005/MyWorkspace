package com.licong.filemanager;

import static com.licong.util.ObjectUtils.isNull;
import java.io.File;

import com.licong.util.StringUtils;

import com.licong.log.ILogger;
import com.licong.log.SystemLogger;

/**
 * 目录管理器
 * 
 * @author James-li
 * 
 *         2013-7-7
 */
public class DirectoryManager {

	private static ILogger logger = new SystemLogger(DirectoryManager.class);

	/**
	 * 复制文件夹
	 * 
	 * @param src
	 *            源文件夹
	 * @param target
	 *            目的文件夹
	 * @return
	 */
	public static boolean copy(File src, File target) {
		if (isNull(src) || isNull(target)) {
			logger.error("src is null or target is null");
			return false;
		}
		if (!src.exists()) {
			logger.error(target.getAbsolutePath() + " is not a exists");
			return false;
		}
		if (!src.isDirectory()) {
			logger.error(target.getAbsolutePath() + " is not a directory");
			return false;
		}
		return realCopy(src, target);
	}

	/**
	 * 复制文件夹
	 * 
	 * @param src
	 *            源文件夹
	 * @param target
	 *            目的文件夹
	 * @return
	 */
	private static boolean realCopy(File src, File target) {
		target.mkdirs();
		String srcPath = getDirectoryPath(src);
		String targetPath = getDirectoryPath(target);
		File[] files = src.listFiles();
		boolean res = true;
		for (File file : files) {
			if (file.isFile()) {
				res = FileManager.copy(file,
						getTarget(file, srcPath, targetPath));
				if (!res) {
					break;
				}
			} else {
				res = realCopy(file, getTarget(file, srcPath, targetPath));
				if (!res) {
					break;
				}
			}
		}
		return res;
	}

	/**
	 * 获取文件夹路径</br> 自动补全文件夹路径后的'/'
	 * 
	 * @param path
	 * @return
	 */
	private static String getDirectoryPath(File file) {
		String path = file.getAbsolutePath();
		if (!path.endsWith(File.separator)) {
			return path + File.separator;
		}
		return path;
	}

	/**
	 * 获取复制目标(文件or文件夹)
	 * 
	 * @param src
	 *            源(文件or文件夹)
	 * @param srcPath
	 *            源文件夹路径
	 * @param targetPath
	 *            目的文件夹路径
	 * @return 复制目标(文件or文件夹)
	 */
	private static File getTarget(File src, String srcPath, String targetPath) {
		String path = src.getAbsolutePath();
		return new File(targetPath + path.substring(srcPath.length()));
	}

	/**
	 * 剪切文件夹
	 * 
	 * @param src
	 *            源文件夹
	 * @param target
	 *            目的文件夹
	 * @return
	 */
	public static boolean cut(File src, File target) {
		copy(src, target);
		return delete(src);
	}

	/**
	 * 删除文件夹
	 * 
	 * @param directory
	 *            目的文件夹
	 * @return
	 */
	public static boolean delete(File directory) {
		if (isNull(directory)) {
			logger.error("target is null");
			return false;
		}
		if (!directory.exists()) {
			logger.error(directory.getAbsolutePath() + " is not a exists");
			return false;
		}
		if (!directory.isDirectory()) {
			logger.error(directory.getAbsolutePath() + " is not a directory");
			return false;
		}
		return deleteDirectory(directory);
	}

	/**
	 * 递归删除文件夹
	 * 
	 * @param directory
	 *            待删除文件夹
	 * @return
	 */
	private static boolean deleteDirectory(File directory) {
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				if (!FileManager.delete(file)) {
					return false;
				}
			} else if (!deleteDirectory(file)) {
				return false;
			}
		}
		return directory.delete();
	}

	/**
	 * 创建文件夹
	 * 
	 * @param directory
	 *            待创建文件夹
	 * @return
	 */
	public static boolean create(File directory) {
		if (isNull(directory)) {
			logger.error("target is null");
			return false;
		}
		return directory.mkdirs();
	}

	/**
	 * 重命名
	 * 
	 * @param source
	 *            源文件夹
	 * @param newName
	 *            新名字
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
		return source.renameTo(new File(path.substring(0,
				path.indexOf(source.getName()))
				+ File.separator + newName));
	}
	
	public static boolean search(File file, String target, FileSearchResult result) {
		if (isNull(file) || StringUtils.isEmpty(target) || isNull(result) || file.isFile()) {
			logger.error("file is null or target is empty or result is null or file is not directory!");
			return false;
		}
		File[] files = file.listFiles();
		if(files.length <= 0) {
			return true;
		}
		for(File f :files) {
			if(f.isDirectory()) {
				if(!search(f, target, result)) {
					return false;
				}
			}else {
				SingleFileSearchResult r = new SingleFileSearchResult();
				if(FileManager.search(f, target, r)) {
					result.put(r);
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
}
