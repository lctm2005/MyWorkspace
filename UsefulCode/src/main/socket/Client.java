package socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * �ͻ���
 * 
 * @author licong
 * 
 */
public class Client {
	// TODOҪ��
	private static final String IP = "127.0.0.1"; 	// IP
	private static final int port = 10000; 			// �˿�
	private final static String EOF = "bye"; 		// socket�رձ��
	private Socket socket; // socket����
	private DataInputStream socketInputStream = null; // Socket������
	private PrintStream socketOutputStream = null; // Socket�����
	private DataInputStream keybordInputStream = null; // ����������

	/**
	 * ���캯��
	 * 
	 * @throws Exception
	 */
	public Client() throws Exception {
		try {
			socket = new Socket(IP, port);
			socketInputStream = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			socketOutputStream = new PrintStream(new BufferedOutputStream(
					socket.getOutputStream()));
			keybordInputStream = new DataInputStream(new BufferedInputStream(
					System.in));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			destructor();
		}
	}

	/**
	 * ����
	 */
	@SuppressWarnings("deprecation")
	public void connect() {
		try {
			String line = keybordInputStream.readLine();
			while (!EOF.equals(line)) {
				socketOutputStream.print(line);
				socketOutputStream.flush();
				System.out.println("Server:" + socketInputStream.readLine());
				line = keybordInputStream.readLine();
			}
			keybordInputStream.close();
			socketOutputStream.close();
			socketInputStream.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destructor();
		}

	}

	/**
	 * α��������</br> 
	 * �����ر���Դ
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
				System.out.println("close socket failed");
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws IOException {
//		try {
//			Client client = new Client();
//			client.connect();
//		} catch (Exception e) {
//			System.out.println("connect server failed");
//			e.printStackTrace();
//		}
		BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		do{
			line = is.readLine();
			System.out.println("Clinet:" + line);
		}while(!"bye".equals(line));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((keybordInputStream == null) ? 0 : keybordInputStream
						.hashCode());
		result = prime * result + ((socket == null) ? 0 : socket.hashCode());
		result = prime
				* result
				+ ((socketInputStream == null) ? 0 : socketInputStream
						.hashCode());
		result = prime
				* result
				+ ((socketOutputStream == null) ? 0 : socketOutputStream
						.hashCode());
		return result;
	}


	
	
}