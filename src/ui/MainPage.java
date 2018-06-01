package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import client_Test.Chatroom;
import client_Test.Record;

public class MainPage extends JFrame implements ActionListener{
	
	private ArrayList<String> friendlist = new ArrayList<String>();
	private ArrayList<Chatroom> chatroom = new ArrayList<Chatroom>();
	private ArrayList<Record> record = new ArrayList<Record>();	
	
	public MainPage(ArrayList<String> friendlists, ArrayList<Chatroom> chatrooms, ArrayList<Record> records) {
		
		this.friendlist = friendlists;
		this.chatroom = chatrooms;
		this.record = records;
				
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLayout(null);
		setVisible(true);
		setTitle("Main");
		
		my_friend = new JTextArea("¦n¤Í:\n");
		my_friend.setEditable(false);
		for(int i=0;i<friendlist.size();i++) {
			my_friend.append(friendlist.get(i) + "\n");
		}
		
		pageBlock = new JPanel();
		cardLayout = new CardLayout();
		pageBlock.setLayout(cardLayout);
//		pageBlock.add(new Label("friends"), "friends");
		pageBlock.add(my_friend,"friends");
		pageBlock.add(new Label("chatrooms"), "chatrooms");
		pageBlock.add(new Label("settings"), "settings");
		
		buttonBlock = new JPanel();
		buttonBlock.setLayout(new FlowLayout());
		friends = new JButton("friends");
		friends.addActionListener(this);
		chatRoom = new JButton("chatrooms");
		chatRoom.addActionListener(this);
		settings = new JButton("settings");
		settings.addActionListener(this);
		buttonBlock.add(friends);
		buttonBlock.add(chatRoom);
		buttonBlock.add(settings);
		
		add(pageBlock, BorderLayout.CENTER);
		add(buttonBlock, BorderLayout.NORTH);
//		JPanel friendList = new JPanel();
//		friendList.setBackground(Color.BLACK);
//		friendList.setLocation(50, 50);
//		friendList.setSize(100, 400);
//		friendA = new JButton("FriendA");
//		friendA.addActionListener(this);
//		friendList.add(friendA);
//		JButton friendB = new JButton("FriendB");
//		friendB.addActionListener(this);
//		friendList.add(friendB);
//		JButton friendC = new JButton("FriendC");
//		friendB.addActionListener(this);
//		friendList.add(friendC);
//		add(friendList);
		

	}
	
	public class MessageDialogue extends JPanel{
		
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
		if(e.getSource() == friendA) {
			System.out.println("here");
			mDialogue = new MessageDialogue();
			mDialogue.setLocation(225, 50);
			mDialogue.setBackground(Color.black);
			mDialogue.setSize(500, 500);
			mDialogue.setVisible(true);
			
			this.add(mDialogue);
//			pp = new JPanel();
//			pp.setVisible(true);
//			pp.setSize(50,50);
//			pp.setLocation(225, 50);
//			pp.setBackground(Color.black);
//			JButton ss = new JButton("SSS");
//			pp.add(ss);
//			add(pp);


		}
		if(e.getSource() == friends) {
			cardLayout.show(pageBlock, "friends");
		}
		if(e.getSource() == chatRoom) {
			cardLayout.show(pageBlock, "chatrooms");
		}
		if(e.getSource() == settings) {
			cardLayout.show(pageBlock, "settings");
		}
		
	}
	MessageDialogue mDialogue;
	JButton friendA;
	JPanel buttonBlock, pageBlock;
	JButton friends, chatRoom, settings;
	CardLayout cardLayout;
	JTextArea my_friend;
}