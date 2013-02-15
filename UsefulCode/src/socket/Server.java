package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ������,ֻ֧��һ��ͨ��
 * 
 * @author licong
 * 
 */
public class Server {
	private final static int port = 10000; // �˿ں�
	private final static String EOF = "bye"; // socket�رձ��

	private ServerSocket server = null; // Socket����
	private Socket socket = null; // Socket����
	private DataInputStream socketInputStream = null; 	// Socket������
	private PrintStream socketOutputStream = null; 		// Socket�����
	private DataInputStream keybordInputStream = null; 	// ����������

	/**
	 * ���캯��
	 * 
	 * @throws IOException
	 */
	public Server() throws IOException {
		server = new ServerSocket(port);
		System.out.println("Start Server...");
	}

	/**
	 * ��������
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
	 * α��������</br> �����ر���Դ
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
