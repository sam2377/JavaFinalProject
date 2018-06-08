package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.ScrollPane;
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

import javax.imageio.ImageTypeSpecifier;
import javax.lang.model.type.NullType;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
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
		// ?:add(Component comp, String str);
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
		// Friend list initialization
		for (int i = 0; i < ChatClient.friendlist.size(); ++i) {
			fList.addElement(ChatClient.friendlist.get(i));

		}
		friendList.setFixedCellHeight(100);
		friendsPanel.add(addFriend, BorderLayout.SOUTH);
		friendsPanel.add(textin, BorderLayout.NORTH);
		friendsPanel.add(fScroll, BorderLayout.CENTER);
		friendList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					// Double-click detected to a personal chatroom
					if (checkChatroomIsOnlyOne) {
						checkChatroomIsOnlyOne = false;
						for (int i = 0; i < ChatClient.chatroom.size(); ++i) {
							if (roomList.getSelectedValue().toString().equals(ChatClient.chatroom.get(i).roomName)) {
								String temp = ChatClient.chatroom.get(i).code;
								LogInPage.chatClient.sendMsg(Identifier.FetchRecord + temp);
								new SwingWorker<NullType, NullType>() {
									@Override
									protected NullType doInBackground() throws Exception {
										while (true) {
											if (LogInPage.chatClient.getStage() == 3) {
												return null;
											}
										}
									}

									protected void done() {
										LogInPage.chatClient.setstate(2);
										chatPage = new ChatPage(temp);
										ChatClient.record.clear();
										chatPage.setVisible(true);
									}

								}.execute();
							}
						}
					}

				}
			}
		});

		buildRoom = new JButton("Build");
		rList = new DefaultListModel();
		roomList = new JList(rList);
		rScroll = new JScrollPane(roomList);
		buildRoom.addActionListener(this);
		// Chatroom list initialization
		for (int i = 0; i < ChatClient.chatroom.size(); i++) {
			rList.addElement(ChatClient.chatroom.get(i).roomName);
		}
		roomList.setFixedCellHeight(100);
		chatroomsPanel.add(buildRoom, BorderLayout.NORTH);
		chatroomsPanel.add(rScroll, BorderLayout.CENTER);
		roomList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					// Double-click detected to a chatroom
					if (checkChatroomIsOnlyOne) {
						checkChatroomIsOnlyOne = false;
						for (int i = 0; i < ChatClient.chatroom.size(); ++i) {
							if (roomList.getSelectedValue().toString().equals(ChatClient.chatroom.get(i).roomName)) {
								String temp = ChatClient.chatroom.get(i).code;
								LogInPage.chatClient.sendMsg(Identifier.FetchRecord + temp);
								new SwingWorker<NullType, NullType>() {
									@Override
									protected NullType doInBackground() throws Exception {
										while (true) {
											if (LogInPage.chatClient.getStage() == 3) {
												return null;
											}
										}
									}

									protected void done() {
										LogInPage.chatClient.setstate(2);
										chatPage = new ChatPage(temp);
										ChatClient.record.clear();
										chatPage.setVisible(true);
									}

								}.execute();
							}
						}

					}

				}
			}
		});
	}
	//Update new info to UI
	public void refresh() {
		fList.addElement(ChatClient.friendlist.get(ChatClient.friendlist.size() - 1));
		rList.addElement(ChatClient.chatroom.get(ChatClient.chatroom.size() - 1).roomName);
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
		// Add a new friend
		if (e.getSource() == addFriend) {
			if (!textin.getText().equals("")) {
				LogInPage.chatClient.sendMsg(Identifier.AddFriend + textin.getText());
				textin.setText("");
			}
		}
		// Create a new group room
		if (e.getSource() == buildRoom) {
			JDialog chooseFriend = new JDialog();
			JTextField roomNameText = new JTextField();
			chooseComfirmButton = new JButton("OK");
			friendList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			chooseComfirmButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == chooseComfirmButton) {
						boolean nameIsRepeated = false;
						for (int i = 0; i < ChatClient.chatroom.size(); ++i) {
							if (roomNameText.getText().trim().equals(ChatClient.chatroom.get(i).roomName))
								nameIsRepeated = true;
						}
						if (!roomNameText.getText().equals("") && !nameIsRepeated) {
							int[] index = friendList.getSelectedIndices();
							String msg = roomNameText.getText() + ":" + LogInPage.chatClient.getUserId();
							for (int x : index) {
								System.out.println(fList.get(x));
								msg = msg.concat(", " + fList.get(x));
							}
							LogInPage.chatClient.sendMsg(Identifier.AddGroup + msg);

						} else {
							JOptionPane.showMessageDialog(LogInPage.logInPage, "創建群組失敗");
						}
						friendList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
						friendsPanel.add(fScroll, BorderLayout.CENTER);
						friendList.clearSelection();
						chooseFriend.dispose();
					}

				}
			});
			chooseFriend.setLocation(this.getX() + 200, this.getY() + 150);
			chooseFriend.setSize(400, 300);
			chooseFriend.add(fScroll, BorderLayout.CENTER);
			chooseFriend.add(chooseComfirmButton, BorderLayout.SOUTH);
			chooseFriend.add(roomNameText, BorderLayout.NORTH);
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
	static ChatPage chatPage;
	Boolean checkChatroomIsOnlyOne = true;
}