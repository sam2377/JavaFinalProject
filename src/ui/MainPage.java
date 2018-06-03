package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import client_Test.Chatroom;
import client_Test.Record;

public class MainPage extends JFrame implements ActionListener {


	public MainPage() {

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Main");
		
		getRootPane().setBorder(new LineBorder(new Color(0, 0, 0, 0), 30));
		pageBlock = new JPanel();
		buttonBlock = new JPanel();
		cardLayout = new CardLayout();
		friendsPanel = new JPanel(new BorderLayout());
		pageBlock.setLayout(cardLayout);
			//?:add(Component comp, String str);
		pageBlock.add(friendsPanel, "friends");
		pageBlock.add(new Label("chatrooms"), "chatrooms");
		pageBlock.add(new Label("settings"), "settings");
		
		buttonBlock.setLayout(new GridLayout(3, 1));
		buttonBlock.setBackground(Color.black);
		buttonBlock.setPreferredSize(new Dimension(150, 0));
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
		add(buttonBlock, BorderLayout.WEST);
		
		addFriend = new JButton("Add");
		addFriend.addActionListener(this);
		textin = new JTextField();
		fList = new DefaultListModel();
		friendList = new JList(fList);
		scroll = new JScrollPane(friendList);
		for(int i = 0; i < ChatClient.friendlist.size(); ++i) {
			fList.addElement(ChatClient.friendlist.get(i));
		}
		friendList.setFixedCellHeight(100);
		friendsPanel.add(addFriend, BorderLayout.SOUTH);
		friendsPanel.add(textin, BorderLayout.NORTH);
		friendsPanel.add(scroll, BorderLayout.CENTER);

	}
	
	public void addItem(String str) {
        for (int i = 0; i < fList.size(); i++) {
            if (fList.get(i).equals(str))
                return;
        }
        fList.addElement(str);
        textin.setText("");
    }
		


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == friends) {
			cardLayout.show(pageBlock, "friends");
		}
		if (e.getSource() == chatRoom) {
			cardLayout.show(pageBlock, "chatrooms");
		}
		if (e.getSource() == settings) {
			cardLayout.show(pageBlock, "settings");
		}
		if (e.getSource() == addFriend) {
			if (!textin.getText().equals("")) {
				LogInPage.chatClient.sendMsg(Identifier.AddFriend + textin.getText());
			}
		}
	}


	
	
	JPanel buttonBlock, pageBlock, friendsPanel;
	JButton friends, chatRoom, settings, addFriend;
	CardLayout cardLayout;
	DefaultListModel<String> fList;
	JTextField textin;
	JList friendList;
	JScrollPane scroll;
	

	
	public void refresh() {
//		my_friend.append(ChatClient.friendlist.get(ChatClient.friendlist.size()-1) + "\n");
		fList.addElement(ChatClient.friendlist.get(ChatClient.friendlist.size()-1));
	}
}