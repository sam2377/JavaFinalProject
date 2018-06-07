package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MessagePage extends JFrame{
	
	public MessagePage(String friend) {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(friend);
		setFriendName(friend);
		
		JTextArea displayer = new JTextArea();
		JTextField inputText = new JTextField();
		displayer.setEditable(false);
		add(displayer, BorderLayout.CENTER);
		add(inputText, BorderLayout.SOUTH);
		
	}
	public void setFriendName(String str) {
		friendname = str;
	}
	private String friendname;
}
