package com.span.bfs.chatApplication;

import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class ClientPage extends Frame implements Runnable {
	Socket soc;
	TextField textfiled, onlineColour;
	TextArea chatArea, userOnlineDispayArea;
	Button btnSend, btnClose;
	String sendTo;
	String LoginName;
	Thread t = null;
	DataOutputStream dout;
	DataInputStream din;
	Vector name;

	ClientPage(String LoginName, String chatwith) throws Exception {

		super(LoginName);
		this.LoginName = LoginName;
		sendTo = chatwith;
		textfiled = new TextField(10);
		onlineColour = new TextField("Online", 15);
		chatArea = new TextArea(2, 2);
		userOnlineDispayArea = new TextArea(2, 2);
		btnSend = new Button("Send");
		btnClose = new Button("Close");
		soc = new Socket("localhost", 5555);

		din = new DataInputStream(soc.getInputStream());
		dout = new DataOutputStream(soc.getOutputStream());
		dout.writeUTF(LoginName);

		t = new Thread(this);

		t.start();

	}

	void setup() {
		setSize(400, 200);
		setLayout(new GridLayout(2, 2));
		userOnlineDispayArea.setBackground(Color.orange);
		onlineColour.setBackground(Color.green);
		add(chatArea);
		Panel p2 = new Panel();
		Panel p = new Panel();

		p.add(userOnlineDispayArea);
		p.add(textfiled);
		p.add(btnSend);
		p.add(btnClose);
		add(userOnlineDispayArea);
		add(p2);
		add(p);
		p2.add(onlineColour);
		show();
	}

	public boolean action(Event e, Object o) {
		if (e.arg.equals("Send")) {
			try {
				dout.writeUTF(sendTo + " " + "DATA" + " "
						+ textfiled.getText().toString());
				chatArea.append("\n" + LoginName + " Says:"
						+ textfiled.getText().toString());
				textfiled.setText("");

			} catch (Exception ex) {
			}
		} else if (e.arg.equals("Close")) {
			try {
				dout.writeUTF(LoginName + " LOGOUT");
				System.exit(1);
			} catch (Exception ex) {
			}

		}

		return super.action(e, o);
	}

	public void run() {
		while (true) {
			try {
				chatArea.append("\n" + sendTo + " Says :" + din.readUTF());

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
