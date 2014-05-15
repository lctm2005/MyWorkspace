package com.licong.ipseeker;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Stack;

/**
 * 用来读取QQwry.dat文件，以根据ip获得好友位置，QQwry.dat的格式是
 * 
 *  一. 文件头，共8字节 
 *  	1. 第一个起始IP的绝对偏移， 4字节
 *  	2. 最后一个起始IP的绝对偏移， 4字节 
 *  
 *  二. &quot;结束地址/国家/区域&quot;记录区 四字节ip地址后跟的每一条记录分成两个部分
 *  		1. 国家记录
 *  		2. 地区记录 但是地区记录是不一定有的。
 *  	而且国家记录和地区记录都有两种形式 
 *  		1. 以0结束的字符串
 *  		2. 4个字节，一个字节可能为0x1或0x2 
 *  			a.为0x1时，表示在绝对偏移后还跟着一个区域的记录，注意是绝对偏移之后，而不是这四个字节之后
 *   			b. 为0x2时，表示在绝对偏移后没有区域记录
 * 		不管为0x1还是0x2，后三个字节都是实际国家名的文件内绝对偏移
 * 		如果是地区记录，0x1和0x2的含义不明，但是如果出现这两个字节，也肯定是跟着3个字节偏移，如果不是 则为0结尾字符串
 * 
 *  三.&quot;起始地址/结束地址偏移&quot;记录区
 *  	1. 每条记录7字节，按照起始地址从小到大排列 
 *  		a. 起始IP地址，4字节 
 *  		b. 结束ip地址的绝对偏移，3字节
 * 
 * 注意，这个文件里的ip地址和所有的偏移量均采用little-endian格式，而java是采用 big-endian格式的，要注意转换
 * 
 */
public class IPSeeker {
	
	/**
	 * 内部类
	 * 用来封装ip相关信息，目前只有两个字段，ip所在的国家和地区
	 */
	private class IPLocation {
		public String country; 	// 国家
		public String area; 	// 地区
		public IPLocation() {
			country = area = "未知地区";
		}

		public IPLocation getCopy() {
			IPLocation ret = new IPLocation();
			ret.country = country;
			ret.area = area;
			return ret;
		}
	}
	
	/**
	 * 内部类
	 * 缓存IP及地址信息
	 * 最大缓存20个IP
	 * 超过容量时，清除最早加入缓存的IP信息，然后放入新的IP信息
	 */
	private class IPCache {
		private final Hashtable<String, IPLocation> ipCache;	//IP信息缓存
		private static final int MAX_SIZE = 20;					//缓存最大容量
		private Stack<String> ipStatk;							//IP堆栈
		
		public IPCache(){
			ipCache = new Hashtable<String, IPLocation>(MAX_SIZE);	//只存20个IP地址信息
			ipStatk = new Stack<String>();
		}
		
		/**
		 * 判断缓存中是否有查询的IP
		 * @param ip 查询的IP
		 * @return
		 */
		public boolean containsIP(String ip){
			return ipCache.contains(ip);
		}
		
		/**
		 * 从缓存中获取IPLocation
		 * @param ip
		 * @return
		 */
		public IPLocation getIPLocation(String ip) {
			return ipCache.get(ip);
		}
		
		/**
		 * 存入IPLocation
		 * @param ip
		 * @param loc
		 */
		public void put(String ip, IPLocation loc) {
			if(ipCache.containsKey(ip)) {
				return;
			}
			if(ipCache.size() == MAX_SIZE) {
				ipCache.remove(ipStatk.pop());
			}
			ipStatk.push(ip);
			ipCache.put(ip, loc);	
		}
	}
	
	private final IPCache ipCache = new IPCache();		// IP缓存

	private static IPSeeker instance = new IPSeeker();	// 单例
	
	private long ipBegin, ipEnd;						// 起始地区的开始和结束的绝对偏移
	
	private final IPFileManager ipFileManager = new IPFileManager();	//资源管理类

	private static final byte AREA_FOLLOWED = 0x02; 	// 模式2 AREA_FOLLOWED

	private static final byte NO_AREA = 0x01; 			// 模式1 NO_AREA

	private static final int START_OFFSET = 0;			// 起始偏移

	private static final int REDIRECT_LENGTH = 1;		// 重定向标志位长度(1字节)

	private static final int OFFSET_LENGTH = 3;			// 偏移长度(3字节)

	private static final int IP_LENGTH = 4;				// IP长度(4字节)

	private static final int INDEX_LENGTH = 7; 			// 索引区(7字节)=起始IP(4字节)+IP记录偏移(3字节)

	/**
	 * 私有构造函数
	 */
	private IPSeeker() {
		// 如果打开文件成功，读取文件头信息
		if (ipFileManager.isFileOpened()) {
			ipBegin = ipFileManager.readLong4(START_OFFSET);
			ipEnd = ipFileManager.readLong4(IP_LENGTH);
			if (ipBegin == -1 || ipEnd == -1) {
				ipFileManager.closeFile();
			}
		}
	}

	/**
	 * @return 单一实例
	 */
	public static IPSeeker getInstance() {
		return instance;
	}

	/**
	 * 根据IP得到国家名
	 * 
	 * @param ip  ip的字节数组形式
	 * @return 国家名字符串
	 */
	private IPLocation getIPLocation(String ip) {
		if (!ipFileManager.isFileOpened())
			return new IPLocation();
		if (ipCache.containsIP(ip)) {
			IPLocation loc = ipCache.getIPLocation(ip);
			return loc;
		} else {
			IPLocation loc = getIPLocation(Utils.getIpByteArrayFromString(ip));
			ipCache.put(ip, loc.getCopy());
			return loc;
		}
	}

	/**
	 * 根据ip搜索ip信息文件，得到IPLocation结构，所搜索的ip参数从类成员ip中得到
	 * 
	 * @param ip
	 *            要查询的IP
	 * @return IPLocation结构
	 */
	private IPLocation getIPLocation(byte[] ip) {
		IPLocation loc = null;
		long offset = locateIP(ip);
		if (offset != -1)
			loc = getIPLocation(offset);
		if (loc == null) {
			loc = new IPLocation();
			loc.country = "未知国家";
			loc.area = "未知地区";
		}
		return loc;
	}


	/**
	 * 把类成员ip和beginIp比较，注意这个beginIp是big-endian的
	 * 
	 * @param ip
	 *            要查询的IP
	 * @param beginIp
	 *            和被查询IP相比较的IP
	 * @return 相等返回0，ip大于beginIp则返回1，小于返回-1。
	 */
	private int compareIP(byte[] ip, byte[] beginIp) {
		for (int i = 0; i < IP_LENGTH; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if (r != 0)
				return r;
		}
		return 0;
	}

	/**
	 * 把两个byte当作无符号数进行比较
	 * 
	 * @param b1
	 * @param b2
	 * @return 若b1大于b2则返回1，相等返回0，小于返回-1
	 */
	private int compareByte(byte b1, byte b2) {
		if ((b1 & 0xFF) > (b2 & 0xFF)) // 比较是否大于
			return 1;
		else if ((b1 ^ b2) == 0)// 判断是否相等
			return 0;
		else
			return -1;
	}

	/**
	 * 这个方法将根据ip的内容，定位到包含这个ip国家地区的记录处，返回一个绝对偏移 方法使用二分法查找。
	 * 
	 * @param ip
	 *            要查询的IP
	 * @return 如果找到了，返回结束IP的偏移，如果没有找到，返回-1
	 */
	private long locateIP(byte[] ip) {
		int res;												//当前索引位置Ip和要查询的Ip的比较结果
		byte[] curIp = new byte[IP_LENGTH]; ;		//当前索引位置的IP
		// 比较第一个ip项
		ipFileManager.readIP(ipBegin, curIp);
		res = compareIP(ip, curIp);
		if (res == 0)
			return ipBegin;
		else if (res < 0)
			return -1;
		long middle = 0;					//二分法中间索引
		// 开始二分搜索
		for (long top = ipBegin, end = ipEnd; top < end;) {
			middle = getMiddleOffset(top, end);
			ipFileManager.readIP(middle, curIp);
			res = compareIP(ip, curIp);
			if (res > 0)
				top = middle;
			else if (res < 0) {
				if (middle == end) {
					end -= INDEX_LENGTH;
					middle = end;
				} else
					end = middle;
			} else
				return ipFileManager.readLong3(middle + IP_LENGTH);
		}
		// 如果循环结束了，那么i和j必定是相等的，这个记录为最可能的记录，但是并非
		// 肯定就是，还要检查一下，如果是，就返回结束地址区的绝对偏移
		middle = ipFileManager.readLong3(middle + IP_LENGTH);
		ipFileManager.readIP(middle, curIp);
		res = compareIP(ip, curIp);
		if (res <= 0)
			return middle;
		else
			return -1;
	}

	/**
	 * 得到begin偏移和end偏移中间位置记录的偏移
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private long getMiddleOffset(long begin, long end) {
		long records = (end - begin) / INDEX_LENGTH;
		records >>= 1; 
		if (records == 0)
			records = 1;
		return begin + records * INDEX_LENGTH;
	}

	/**
	 * 给定一个ip国家地区记录的偏移，返回一个IPLocation结构
	 * 
	 * @param offset
	 * @return
	 */
	private IPLocation getIPLocation(long offset) {
		try {
			// 跳过4字节ip
			ipFileManager.seek(offset + IP_LENGTH);
			// 读取第一个字节判断是否标志字节
			byte b = ipFileManager.readByte();
			//IP地址信息
			IPLocation loc = new IPLocation();
			
			if (b == NO_AREA) {
				long countryOffset = ipFileManager.readLong3();	// 读取国家偏移
				ipFileManager.seek(countryOffset);	// 跳转至偏移处
				b = ipFileManager.readByte();	// 再检查一次标志字节，因为这个时候这个地方仍然可能是个重定向
				if (b == AREA_FOLLOWED) {
					loc.country = ipFileManager.readString(ipFileManager.readLong3());
					ipFileManager.seek(countryOffset + REDIRECT_LENGTH
							+ OFFSET_LENGTH);
				} else {
					loc.country = ipFileManager.readString(countryOffset);
				}
				loc.area = readArea(ipFileManager.getFilePointer());	// 读取地区标志
			} else if (b == AREA_FOLLOWED) {
				loc.country = ipFileManager.readString(ipFileManager.readLong3());
				loc.area = readArea(offset + IP_LENGTH + REDIRECT_LENGTH + OFFSET_LENGTH);
			} else {
				loc.country = ipFileManager.readString(ipFileManager.getFilePointer() - REDIRECT_LENGTH);
				loc.area = readArea(ipFileManager.getFilePointer());
			}
			return loc;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 从offset偏移开始解析后面的字节，读出一个地区名
	 * 
	 * @param offset
	 * @return 地区名字符串
	 * @throws IOException
	 */
	private String readArea(long offset) throws IOException {
		ipFileManager.seek(offset);
		byte b = ipFileManager.readByte();
		if (b == 0x01 || b == 0x02) {
			long areaOffset = ipFileManager.readLong3(offset + REDIRECT_LENGTH);
			if (areaOffset == 0)
				return "未知地区";
			else
				return ipFileManager.readString(areaOffset);
		} else
			return ipFileManager.readString(offset);
	}

	public String getAddress(String ip) {
		IPLocation loc = getIPLocation(ip);
		String country = loc.country.equals(" CZ88.NET") ? "" : loc.country;
		String area = loc.area.equals(" CZ88.NET") ? "" : loc.area;
		String address = country + " " + area;
		return address.trim();
	}
}