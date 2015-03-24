package com.span.bfs.chatApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//login page

public class Log extends JFrame {

	// main method
	public static void main(String[] args) {
		Log frameTabel = new Log();
	}

	JButton blogin = new JButton("Login");
	JPanel panel = new JPanel();
	JTextField txuser = new JTextField("login name", 15);
	JTextField pass = new JTextField("chatter name", 30);

	Log() {
		super("Login Autentification");
		setSize(300, 200);
		setLocation(500, 280);
		panel.setLayout(null);

		txuser.setBounds(70, 30, 150, 20);
		pass.setBounds(70, 65, 150, 20);
		blogin.setBounds(110, 100, 80, 20);

		panel.add(blogin);
		panel.add(txuser);
		panel.add(pass);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		actionlogin();
	}

	public void actionlogin() {
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String puname = txuser.getText();
				String ppaswd = pass.getText();

				if (puname != "" && ppaswd != "") {
					try {
						ClientPage chatClient = new ClientPage(puname, ppaswd);
						chatClient.setup();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					dispose();
				} else {

					JOptionPane.showMessageDialog(null,
							"Wrong Password / Username");
					txuser.setText("");
					pass.setText("");
					txuser.requestFocus();
				}

			}
		});
	}
}
