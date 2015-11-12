package edu.sjsu.cmpe207.client.bean;

import java.net.Socket;

public class ClientOp {
	private int clientOperation;
	private String host;
	private int port;
	private Socket socket;
	private String message;

	public void setClientOperation(int op) {
		this.clientOperation = op;
	}

	public int getClientOperation() {
		return this.clientOperation;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port2) {
		this.port = port2;
	}

	public void setSocket(Socket sock) {
		this.socket = sock;
	}

	public Socket getSocket() {
		return this.socket;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
