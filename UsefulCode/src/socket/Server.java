package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器,只支持一次通信
 * 
 * @author licong
 * 
 */
public class Server {
	private final static int port = 10000; // 端口号
	private final static String EOF = "bye"; // socket关闭标记

	private ServerSocket server = null; // Socket服务
	private Socket socket = null; // Socket连接
	private DataInputStream socketInputStream = null; 	// Socket输入流
	private PrintStream socketOutputStream = null; 		// Socket输出流
	private DataInputStream keybordInputStream = null; 	// 键盘输入流

	/**
	 * 构造函数
	 * 
	 * @throws IOException
	 */
	public Server() throws IOException {
		server = new ServerSocket(port);
		System.out.println("Start Server...");
	}

	/**
	 * 启动服务
	 */
	@SuppressWarnings("deprecation")
	public void service() {
		try {
			socket = server.accept();
			System.out.println("Start listening...");
			socketInputStream = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			socketOutputStream = new PrintStream(new BufferedOutputStream(
					socket.getOutputStream()));
			keybordInputStream = new DataInputStream(new BufferedInputStream(
					System.in));
			System.out.println("Client:" + socketInputStream.readLine());
			String line = keybordInputStream.readLine();
			while (!EOF.equals(line)) {
				socketOutputStream.print(line);
				socketOutputStream.flush();
				System.out.println("Client:" + socketInputStream.readLine());
				line = keybordInputStream.readLine();
			}
			keybordInputStream.close();
			socketOutputStream.close();
			socketInputStream.close();
			socket.close();
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destructor();
		}
	}

	/**
	 * 伪析构函数</br> 用来关闭资源
	 */
	private void destructor() {
		if (null != keybordInputStream) {
			try {
				keybordInputStream.close();
			} catch (IOException e) {
				System.out.println("close keybordInputStream failed");
				e.printStackTrace();
			}
		}
		if (null != socketOutputStream) {
			socketOutputStream.close();
		}
		if (null != socketInputStream) {
			try {
				socketInputStream.close();
			} catch (IOException e) {
				System.out.println("close socketInputStream failed");
				e.printStackTrace();
			}
		}
		if (null != socket) {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("close Socket failed");
				e.printStackTrace();
			}
		}
		if (null != server) {
			try {
				server.close();
			} catch (IOException e) {
				System.out.println("close ServerSocket failed");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Server().service();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
