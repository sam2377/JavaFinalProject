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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import client_Test.Chatroom;
import client_Test.Record;

public class MainPage extends JFrame implements ActionListener {


	public MainPage() {

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Main");
		
		getRootPane().setBorder(new LineBorder(new Color(0, 0, 0, 0), 30));
		pageBlock = new JPanel();
		buttonBlock = new JPanel();
		cardLayout = new CardLayout();
		friendsPanel = new JPanel(new BorderLayout());
		chatroomsPanel = new JPanel(new BorderLayout());
		pageBlock.setLayout(cardLayout);
			//?:add(Component comp, String str);
		pageBlock.add(friendsPanel, "friends");
		pageBlock.add(chatroomsPanel, "chatrooms");
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
		textin = new JTextField();
		fList = new DefaultListModel();
		friendList = new JList(fList);
		fScroll = new JScrollPane(friendList);
		addFriend.addActionListener(this);
		for(int i = 0; i < ChatClient.friendlist.size(); ++i) {
			fList.addElement(ChatClient.friendlist.get(i));
		}
		friendList.setFixedCellHeight(100);
		friendsPanel.add(addFriend, BorderLayout.SOUTH);
		friendsPanel.add(textin, BorderLayout.NORTH);
		friendsPanel.add(fScroll, BorderLayout.CENTER);
		friendList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		            // Double-click detected
//		            int index = friendList.locationToIndex(evt.getPoint());
//		            System.out.println("index: "+index);
		            messagePage = new MessagePage(friendList.getSelectedValue().toString());
		            messagePage.setVisible(true);
		        } 
		    }
		});
		
		buildRoom = new JButton("Build");
		rList = new DefaultListModel();
		roomList = new JList(rList);
		rScroll = new JScrollPane(roomList);
		buildRoom.addActionListener(this);
		roomList.setFixedCellHeight(100);
		chatroomsPanel.add(buildRoom, BorderLayout.NORTH);
		chatroomsPanel.add(rScroll, BorderLayout.CENTER);
		roomList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		            // Double-click detected
		            messagePage = new MessagePage(roomList.getSelectedValue().toString());
		            messagePage.setVisible(true);
		        } 
		    }
		});
	}
	
//	public void addItem(String str) {
//        for (int i = 0; i < fList.size(); i++) {
//            if (fList.get(i).equals(str))
//                return;
//        }
//        fList.addElement(str);
//        textin.setText("");
//    }
	
	
	public void refresh() {
		fList.addElement(ChatClient.friendlist.get(ChatClient.friendlist.size()-1));
		rList.addElement(ChatClient.friendlist.get(ChatClient.friendlist.size()-1));
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
				textin.setText("");
			}
		}
		if(e.getSource() == buildRoom) {
			System.out.println("aaaaaaa");
			JDialog chooseFriend = new JDialog();
			chooseComfirmButton = new JButton("OK");
			friendList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			chooseComfirmButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == chooseComfirmButton) {
						int[] index = friendList.getSelectedIndices();
						//how to know username
						String roomName = "userID";
						for(int x  : index) {
							System.out.println(fList.get(x));
							roomName = roomName.concat(", " + fList.get(x));
						}
						messagePage = new MessagePage(roomName);
			            messagePage.setVisible(true);
			            rList.addElement(roomName);
					}
					
				}
			});
			chooseFriend.setLocation(this.getX()+200, this.getY()+150);
			chooseFriend.setSize(400, 300);
			chooseFriend.add(fScroll,BorderLayout.CENTER);
			chooseFriend.add(chooseComfirmButton, BorderLayout.SOUTH);
			chooseFriend.setVisible(true);

		}
	}

	
	
	
	JPanel buttonBlock, pageBlock, friendsPanel, chatroomsPanel;
	JButton friends, chatRoom, settings, addFriend, buildRoom, chooseComfirmButton;
	CardLayout cardLayout;
	DefaultListModel<String> fList, rList;
	JTextField textin;
	JList friendList, roomList;
	JScrollPane fScroll, rScroll;
	MessagePage messagePage;
	

	

}