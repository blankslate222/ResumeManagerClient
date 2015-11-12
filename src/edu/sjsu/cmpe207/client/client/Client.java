package edu.sjsu.cmpe207.client.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import edu.sjsu.cmpe207.client.bean.ClientOp;

public class Client {

	private ClientOp clientOp;

	public ClientOp getClientOp() {
		return clientOp;
	}

	public void setClientOp(ClientOp clientOp) {
		this.clientOp = clientOp;
	}

	private int checkCommandLineArgs(String[] commandLineArgs) {
		int port = 0;
		if (commandLineArgs.length < 3) {
			this.printUsage();
			return -1;
		} else {
			this.clientOp.setHost(commandLineArgs[0]);
		}
		port = Integer.parseInt(commandLineArgs[1]);
		if (port < 0 && port > 65535) {
			this.printUsage();
			return -1;
		} else {
			this.clientOp.setPort(port);
		}
		return 0;
	}

	private int processCommandLineArgs(String[] args) {
		String operationDetails = args[2];

		if ("1".equals(operationDetails.substring(0, 1))) {
			if (args.length < 4) {
				this.printUsage();
				return -1;
			} else {
				this.getClientOp().setClientOperation(1);
			}
		}

		if ("2".equals(operationDetails.substring(0, 1))) {
			if (args.length < 4) {
				this.printUsage();
				return -1;
			} else {
				this.getClientOp().setClientOperation(2);
			}
		}

		if ("3".equals(operationDetails)) {
			this.printUsage();
			return -1;
		}

		this.getClientOp().setMessage(operationDetails);
		return 0;
	}

	private void printUsage() {
		// TODO
		// usage: server-ip, server-port, 12-character-input, file-name
		String usage = "Usage details - application run command with arguments:\n";
		usage += "java Client host port message [path]\n";
		usage += "host : ip address of server\n";
		usage += "port : port number the server is listening on\n";
		usage += "message : 12 character string\n";
		usage += "message : op(1 char) + UserID(10 char) + file version(1 char)\n";
		usage += "op : 1 = upload file - specify [file] argument - specify file version\n";
		usage += "op : 2 = download file - specify proper file version - same as uploaded file version\n";
		usage += "UserId : currently only digits(0-90 allowed - use the same UserId to upload/download\n";
		usage += "file version : specify version(single digit only 0 - 9)\n";
		usage += "[path] - path/to/file/fileName - optional\n";
		usage += "when uploading - specify path to target file\n";
		usage += "when downloading - specify path to download location \n";
		System.out.println(usage);
	}

	private int sendMessage(String message) throws IOException {
		BufferedReader br = null;
		PrintWriter pw = null;
		String line = "";
		int result = 0;
		Socket cli = getClientOp().getSocket();
		if (cli == null) {
			return -1;
		}
		try {

			br = new BufferedReader(new InputStreamReader(cli.getInputStream()));
			pw = new PrintWriter(cli.getOutputStream(),
					true);
			pw.println(message);

			System.out.println("Data Received:");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.equals("bye")) {
					pw.println("bye");
					result = -1;
					break;
				}
				if (line.equals("ack")) {
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Failed to send message");
			System.out.println(e.getMessage());
			throw new IOException(e);
		}
//		} finally {
//			try {
//				//cli.shutdownInput();
//				//cli.shutdownOutput();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return result;
	}

	private int initiateCommunication() {
		int result = 0;
		Socket client = null;
		try {
			client = new Socket(this.clientOp.getHost(), this.getClientOp()
					.getPort());
			this.clientOp.setSocket(client);
			result = sendMessage("000000000000");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = -1;
			e.printStackTrace();
		}
		return result;
	}

	private void closeSocket() throws IOException {
		this.clientOp.getSocket().close();
	}

	private int sendRequestDetails(String details) {
		int result = 0;
		try {
			result = sendMessage(details);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = -1;
			e.printStackTrace();
		}
		return result;
	}

	private void handleCommunication(String[] args) {
		ClientOpManager manager = null;
		setClientOp(new ClientOp());

		if (checkCommandLineArgs(args) < 0) {
			return;
		}

		if (processCommandLineArgs(args) < 0) {
			return;
		}

		try {

			if (initiateCommunication() < 0) {
				System.out.println("Stopping client...");
				return;
			}

			if (sendRequestDetails(args[2]) < 0) {
				System.out.println("could not communicate request details");
				return;
			}
			manager = new ClientOpManager(clientOp.getSocket());

			switch (getClientOp().getClientOperation()) {
			case 1:
				manager.setTargetFilePath(args[3]);
				if (manager.uploadFile() < 0) {
					System.out.println("upload failed");
				}
				break;
			case 2:
				manager.setTargetFilePath(args[3]);
				if (manager.downloadFile() < 0) {
					System.out.println("download failed");
				}
				break;
			case 3:
				if (sendRequestDetails(args[2]) < 0) {
					System.out.println("could not get usage");
				}
				break;
			default:
				sendRequestDetails("333333333333");
				break;
			}

			closeSocket();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				closeSocket();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {
		Client client = new Client();
		client.handleCommunication(args);
	}
}
