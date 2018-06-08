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
		// =======
		// // pageBlock.add(new Label("friends"), "friends");
		// pageBlock.add(my_friend, "friends");
		//
		// // pageBlock.add(new Label("chatrooms"), "chatrooms");
		// chatroom_panel.add(scrollPane, BorderLayout.CENTER);
		// for (int i = 0; i < ChatClient.chatroom.size(); i++) {
		// JButton jButton = new JButton(ChatClient.chatroom.get(i).roomName);
		// jButton.putClientProperty("code", ChatClient.chatroom.get(i).code);
		// chatroom_list_panel.add(jButton);
		// jButton.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// // System.out.println(jButton.getClientProperty("code").toString());
		// if (check == true) {
		// check = false;
		// LogInPage.chatClient
		// .sendMsg(Identifier.FetchRecord +
		// jButton.getClientProperty("code").toString());
		// new SwingWorker<NullType, NullType>() {
		// @Override
		// protected NullType doInBackground() throws Exception {
		// while (true) {
		// if (LogInPage.chatClient.getStage() == 3) {
		// return null;
		// }
		// }
		// }
		//
		// protected void done() {
		// LogInPage.chatClient.setstate(2);
		// chatPage = new ChatPage(jButton.getClientProperty("code").toString());
		// ChatClient.record.clear();
		// chatPage.setVisible(true);
		// }
		//
		// }.execute();
		// }
		// }
		// });
		// }
		// pageBlock.add(chatroom_panel, "chatrooms");
		//
		// addfriend_panel.add(adduser);
		// addfriend_panel.add(adduser_confirm);
		// adduser_confirm.addActionListener(this);
		// // pageBlock.add(new Label("settings"), "settings");
		// pageBlock.add(addfriend_panel, "addfriend");
		//
		// addgroup_panel.add(friend_checkbox_panel, BorderLayout.CENTER);
		// addgroup_confirm.addActionListener(this);
		// addgroup_panel.add(addgroup_confirm, BorderLayout.SOUTH);
		// addgroup_panel.add(group_name, BorderLayout.NORTH);
		//
		// pageBlock.add(addgroup_panel, "addgroup");
		// buttonBlock = new JPanel();
		// buttonBlock.setLayout(new FlowLayout());
		// >>>>>>> refs/remotes/origin/myBranch
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
					// Double-click detected
					// int index = friendList.locationToIndex(evt.getPoint());
					// System.out.println("index: "+index);
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

	public void refresh() {
		fList.addElement(ChatClient.friendlist.get(ChatClient.friendlist.size() - 1));
		rList.addElement(ChatClient.friendlist.get(ChatClient.friendlist.size() - 1));
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
		if (e.getSource() == buildRoom) {
			System.out.println("aaaaaaa");
			JDialog chooseFriend = new JDialog();
			chooseComfirmButton = new JButton("OK");
			friendList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			chooseComfirmButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == chooseComfirmButton) {
						int[] index = friendList.getSelectedIndices();
						// how to know username
						String roomName = "userID";
						for (int x : index) {
							System.out.println(fList.get(x));
							roomName = roomName.concat(", " + fList.get(x));
						}
						messagePage = new MessagePage(roomName);
						messagePage.setVisible(true);
						rList.addElement(roomName);
					}

				}
			});
			chooseFriend.setLocation(this.getX() + 200, this.getY() + 150);
			chooseFriend.setSize(400, 300);
			chooseFriend.add(fScroll, BorderLayout.CENTER);
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

	// =======
	// if (e.getSource() == addfriend) {
	// cardLayout.show(pageBlock, "addfriend");
	// }
	// if (e.getSource() == addgroup) {
	// cardLayout.show(pageBlock, "addgroup");
	// }
	// if (e.getSource() == adduser_confirm) {
	// if (!adduser.getText().equals("")) {
	// LogInPage.chatClient.sendMsg(Identifier.AddFriend + adduser.getText());
	// }
	// }
	// if (e.getSource() == addgroup_confirm) {
	// if (!group_name.getText().equals("")) {
	// String msg = group_name.getText() + ":" + LogInPage.chatClient.getUserId();
	// for (int i = 0; i < checkBoxs.size(); i++) {
	// if (checkBoxs.get(i).isSelected()) {
	// msg = new
	// StringBuilder().append(msg).append(",").append(checkBoxs.get(i).getText()).toString();
	// }
	// }
	// LogInPage.chatClient.sendMsg(Identifier.AddGroup + msg);
	// // System.out.println(Identifier.AddGroup + msg);
	// } else {
	// JOptionPane.showMessageDialog(LogInPage.logInPage, "��줣�ର��");
	// }
	// }
	// }
	//
	// MessageDialogue mDialogue;
	// JButton friendA;
	// JPanel buttonBlock, pageBlock;
	// JButton friends, chatRoom, addfriend, addgroup;
	// CardLayout cardLayout;
	//
	// JTextArea my_friend;
	//
	// JPanel chatroom_panel = new JPanel();
	// JPanel chatroom_list_panel = new JPanel(new GridLayout(0, 10));
	// JScrollPane scrollPane = new JScrollPane(chatroom_list_panel,
	// JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	// JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	static ChatPage chatPage;
	//
	// JTextField adduser = new JTextField();
	// JButton adduser_confirm = new JButton("�[�J");
	// JPanel addfriend_panel = new JPanel(new GridLayout());
	//
	// JButton addgroup_confirm = new JButton("�Ыظs��");
	// JTextField group_name = new JTextField();
	// JPanel addgroup_panel = new JPanel(new BorderLayout());
	// JPanel friend_checkbox_panel = new JPanel();
	// ArrayList<JCheckBox> checkBoxs = new ArrayList<JCheckBox>();
	//
	Boolean check = true;
	//
	// public void refresh() {
	// my_friend.append(ChatClient.friendlist.get(ChatClient.friendlist.size() - 1)
	// + "\n");
	// JCheckBox checkbox = new
	// JCheckBox(ChatClient.friendlist.get(ChatClient.friendlist.size() - 1));
	// checkBoxs.add(checkbox);
	// friend_checkbox_panel.add(checkbox);
	// }
	// >>>>>>> refs/remotes/origin/myBranch
}