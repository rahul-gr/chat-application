package com.span.bfs.chatApplication;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerPage {
	static Vector ClientSockets;
	static Vector LoginNames;

	ServerPage() throws Exception {
		ServerSocket soc = new ServerSocket(5555);
		ClientSockets = new Vector();
		LoginNames = new Vector();

		while (true) {
			Socket CSoc = soc.accept();
			AcceptClient obClient = new AcceptClient(CSoc);
		}
	}

	public static void main(String args[]) throws Exception {

		ServerPage ob = new ServerPage();
	}

	class AcceptClient extends Thread {
		int flag = 1;
		Socket ClientSocket;
		DataInputStream din;
		DataOutputStream dout;

		AcceptClient(Socket CSoc) throws Exception {
			ClientSocket = CSoc;

			din = new DataInputStream(ClientSocket.getInputStream());
			dout = new DataOutputStream(ClientSocket.getOutputStream());

			String LoginName = din.readUTF();
			for (int i = 0; i < LoginNames.size(); i++) {
				if (LoginName == LoginNames.elementAt(i)) {
					flag = 0;
					break;
				}
			}
			if (flag == 1) {
				System.out.println("User Logged In :" + LoginName);
				LoginNames.add(LoginName);
				ClientSockets.add(ClientSocket);
				start();
			} else {
				System.out.println("login name is alreday taken by other user");
				System.out.println("pleawse login by diffent Login Name");
			}
		}

		public void run() {
			while (true) {

				try {
					String msgFromClient = new String();
					msgFromClient = din.readUTF();
					StringTokenizer st = new StringTokenizer(msgFromClient);
					String Sendto = st.nextToken();
					String MsgType = st.nextToken();
					int iCount = 0;

					if (MsgType.equals("LOGOUT")) {
						for (iCount = 0; iCount < LoginNames.size(); iCount++) {
							if (LoginNames.elementAt(iCount).equals(Sendto)) {
								LoginNames.removeElementAt(iCount);
								ClientSockets.removeElementAt(iCount);
								System.out.println("User " + Sendto
										+ " Logged Out ...");
								break;
							}
						}

					} else {
						String msg = "";
						while (st.hasMoreTokens()) {
							msg = msg + " " + st.nextToken();
						}
						for (iCount = 0; iCount < LoginNames.size(); iCount++) {
							if (LoginNames.elementAt(iCount).equals(Sendto)) {
								Socket tSoc = (Socket) ClientSockets
										.elementAt(iCount);
								DataOutputStream tdout = new DataOutputStream(
										tSoc.getOutputStream());
								tdout.writeUTF(msg);
								break;
							}
						}
						if (iCount == LoginNames.size()) {
							dout.writeUTF("I am offline");
						} else {

						}
					}
					if (MsgType.equals("LOGOUT")) {
						break;
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}
}
