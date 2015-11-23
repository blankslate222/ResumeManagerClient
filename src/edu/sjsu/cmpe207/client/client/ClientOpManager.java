package edu.sjsu.cmpe207.client.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientOpManager {
	private String targetFilePath;
	private Socket socket;

	public ClientOpManager() {

	}

	public ClientOpManager(Socket sock, String path) {
		this.socket = sock;
		this.targetFilePath = path;
	}

	public ClientOpManager(Socket sock) {
		this.socket = sock;
	}

	public void setTargetFilePath(String path) {
		this.targetFilePath = path;
	}

	public int uploadFile() {
		int result = 0;
		InputStream is = null;
		OutputStream os = null;
		byte[] buf = null;

		try {
			is = new FileInputStream(targetFilePath);
			os = this.socket.getOutputStream();
			buf = new byte[8192];
			int len = 0;
			while ((len = is.read(buf)) != -1) {
				os.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			result = -1;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = -1;
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	@SuppressWarnings("unused")
	public int downloadFile() {
		int result = 0;
		InputStream is = null;
		FileOutputStream os = null;
		byte[] buf = new byte[8192];
		int len = 0;

		try {
			is = this.socket.getInputStream();
			os = new FileOutputStream(targetFilePath);

			while ((len = is.read(buf)) != -1) {
				os.write(buf);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
