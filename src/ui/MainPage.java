package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client_Test.Chatroom;
import client_Test.Record;

public class MainPage extends JFrame implements ActionListener {


	public MainPage() {

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setLayout(null);
		setVisible(true);
		setTitle("Main");

		my_friend = new JTextArea("好友:\n");
		my_friend.setEditable(false);
		for (int i = 0; i < ChatClient.friendlist.size(); i++) {
			my_friend.append(ChatClient.friendlist.get(i) + "\n");
		}

		pageBlock = new JPanel();
		cardLayout = new CardLayout();
		pageBlock.setLayout(cardLayout);
		// pageBlock.add(new Label("friends"), "friends");
		pageBlock.add(my_friend, "friends");
		pageBlock.add(new Label("chatrooms"), "chatrooms");

		addfriend_panel.add(adduser);
		addfriend_panel.add(adduser_confirm);
		adduser_confirm.addActionListener(this);
		// pageBlock.add(new Label("settings"), "settings");
		pageBlock.add(addfriend_panel, "addfriend");
		buttonBlock = new JPanel();
		buttonBlock.setLayout(new FlowLayout());
		friends = new JButton("friends");
		friends.addActionListener(this);
		chatRoom = new JButton("chatrooms");
		chatRoom.addActionListener(this);
		addfriend = new JButton("addfriend");
		addfriend.addActionListener(this);
		buttonBlock.add(friends);
		buttonBlock.add(chatRoom);
		buttonBlock.add(addfriend);

		add(pageBlock, BorderLayout.CENTER);
		add(buttonBlock, BorderLayout.NORTH);
		// JPanel friendList = new JPanel();
		// friendList.setBackground(Color.BLACK);
		// friendList.setLocation(50, 50);
		// friendList.setSize(100, 400);
		// friendA = new JButton("FriendA");
		// friendA.addActionListener(this);
		// friendList.add(friendA);
		// JButton friendB = new JButton("FriendB");
		// friendB.addActionListener(this);
		// friendList.add(friendB);
		// JButton friendC = new JButton("FriendC");
		// friendB.addActionListener(this);
		// friendList.add(friendC);
		// add(friendList);

	}

	public class MessageDialogue extends JPanel {

		public MessageDialogue() {
			setBackground(Color.black);
			setSize(500, 500);
			setVisible(true);
			JTextArea textBlock = new JTextArea("gmflkreslirfj;soeifemrikmf");
			textBlock.setSize(this.WIDTH - 200, this.HEIGHT - 200);
			textBlock.setLocation(50, 50);
			textBlock.setBackground(Color.white);
			add(textBlock);
			JTextArea typingBlock = new JTextArea("ilfjsifsirjf");
			typingBlock.setSize(400, 100);
			typingBlock.setLocation(50, 375);
			add(typingBlock);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == friendA) {
			System.out.println("here");
			mDialogue = new MessageDialogue();
			mDialogue.setLocation(225, 50);
			mDialogue.setBackground(Color.black);
			mDialogue.setSize(500, 500);
			mDialogue.setVisible(true);

			this.add(mDialogue);
			// pp = new JPanel();
			// pp.setVisible(true);
			// pp.setSize(50,50);
			// pp.setLocation(225, 50);
			// pp.setBackground(Color.black);
			// JButton ss = new JButton("SSS");
			// pp.add(ss);
			// add(pp);

		}
		if (e.getSource() == friends) {
			cardLayout.show(pageBlock, "friends");
		}
		if (e.getSource() == chatRoom) {
			cardLayout.show(pageBlock, "chatrooms");
		}
		if (e.getSource() == addfriend) {
			cardLayout.show(pageBlock, "addfriend");
		}
		if (e.getSource() == adduser_confirm) {
			if (!adduser.getText().equals("")) {
				LogInPage.chatClient.sendMsg(Identifier.AddFriend + adduser.getText());
			}
		}
	}

	MessageDialogue mDialogue;
	JButton friendA;
	JPanel buttonBlock, pageBlock;
	JButton friends, chatRoom, addfriend;
	CardLayout cardLayout;
	JTextArea my_friend;

	JTextField adduser = new JTextField();
	JButton adduser_confirm = new JButton("加入");
	JPanel addfriend_panel = new JPanel(new GridLayout());

	
	public void refresh() {
		my_friend.append(ChatClient.friendlist.get(ChatClient.friendlist.size()-1) + "\n");
	}
}