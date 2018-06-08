package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPage extends JFrame implements ActionListener {

	JButton send;
	JTextArea textArea;
	JTextField textField;
	JPanel panel;
	JScrollPane jScrollPane;
	String code;

	// public static void main(String[] args) {
	// ChatPage chatPage = new ChatPage();
	// chatPage.setVisible(true);
	// }

	public ChatPage(String cString) {

		code = cString;
		
		setSize(800, 600);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle(cString);

		panel = new JPanel(new GridLayout());
		send = new JButton("send");
		send.addActionListener(this);
		textArea = new JTextArea();
		for (int i = 0; i < ChatClient.record.size(); i++) {
			textArea.append(ChatClient.record.get(i).sender + ": " + ChatClient.record.get(i).MSG + "\n");
		}
		
		textArea.setEditable(false);
		jScrollPane = new JScrollPane(textArea);
		textField = new JTextField();

		panel.add(textField);
		panel.add(send);

		add(panel, BorderLayout.SOUTH);
		add(jScrollPane, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				MainPage.chatPage.setVisible(false);
				LogInPage.chatClient.sendMsg(Identifier.QuitChat);
				System.out.println("close");
				LogInPage.mainPage.checkChatroomIsOnlyOne = true;
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == send) {
			if (!textField.getText().equals("")) {
				refresh(LogInPage.chatClient.getUserId() + ": " + textField.getText());
				// System.out.println("input : " + textField.getText());
				LogInPage.chatClient.sendMsg(textField.getText());
				// LogInPage.chatClient.sendMsg(Identifier.StoreRecord + code + "," +
				// LogInPage.chatClient.getId() + "," + textField.getText());
				textField.setText("");
			}
		}

	}

	public void refresh(String string) {
		textArea.append(string + "\n");
	}
}
