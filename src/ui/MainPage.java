package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
			JCheckBox checkbox = new JCheckBox(ChatClient.friendlist.get(i));
			checkBoxs.add(checkbox);
			friend_checkbox_panel.add(checkbox);
		}

		pageBlock = new JPanel();
		cardLayout = new CardLayout();
		pageBlock.setLayout(cardLayout);
		// pageBlock.add(new Label("friends"), "friends");
		pageBlock.add(my_friend, "friends");

		// pageBlock.add(new Label("chatrooms"), "chatrooms");
		chatroom_panel.add(scrollPane, BorderLayout.CENTER);
		for (int i = 0; i < ChatClient.chatroom.size(); i++) {
			JButton jButton = new JButton(ChatClient.chatroom.get(i).roomName);
			jButton.putClientProperty("code", ChatClient.chatroom.get(i).code);
			chatroom_list_panel.add(jButton);
			jButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// System.out.println(jButton.getClientProperty("code").toString());
					if (check == true) {
						check = false;
						LogInPage.chatClient
								.sendMsg(Identifier.FetchRecord + jButton.getClientProperty("code").toString());
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
								chatPage = new ChatPage(jButton.getClientProperty("code").toString());
								ChatClient.record.clear();
								chatPage.setVisible(true);
							}

						}.execute();
					}
				}
			});
		}
		pageBlock.add(chatroom_panel, "chatrooms");

		addfriend_panel.add(adduser);
		addfriend_panel.add(adduser_confirm);
		adduser_confirm.addActionListener(this);
		// pageBlock.add(new Label("settings"), "settings");
		pageBlock.add(addfriend_panel, "addfriend");

		addgroup_panel.add(friend_checkbox_panel, BorderLayout.CENTER);
		addgroup_confirm.addActionListener(this);
		addgroup_panel.add(addgroup_confirm, BorderLayout.SOUTH);
		addgroup_panel.add(group_name, BorderLayout.NORTH);

		pageBlock.add(addgroup_panel, "addgroup");
		buttonBlock = new JPanel();
		buttonBlock.setLayout(new FlowLayout());
		friends = new JButton("friends");
		friends.addActionListener(this);
		chatRoom = new JButton("chatrooms");
		chatRoom.addActionListener(this);
		addfriend = new JButton("addfriend");
		addfriend.addActionListener(this);
		addgroup = new JButton("addgroup");
		addgroup.addActionListener(this);
		buttonBlock.add(friends);
		buttonBlock.add(chatRoom);
		buttonBlock.add(addfriend);
		buttonBlock.add(addgroup);

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
		if (e.getSource() == addgroup) {
			cardLayout.show(pageBlock, "addgroup");
		}
		if (e.getSource() == adduser_confirm) {
			if (!adduser.getText().equals("")) {
				LogInPage.chatClient.sendMsg(Identifier.AddFriend + adduser.getText());
			}
		}
		if (e.getSource() == addgroup_confirm) {
			if (!group_name.getText().equals("")) {
				String msg = group_name.getText() + ":" + LogInPage.chatClient.getUserId();
				for (int i = 0; i < checkBoxs.size(); i++) {
					if (checkBoxs.get(i).isSelected()) {
						msg = new StringBuilder().append(msg).append(",").append(checkBoxs.get(i).getText()).toString();
					}
				}
				LogInPage.chatClient.sendMsg(Identifier.AddGroup + msg);
				// System.out.println(Identifier.AddGroup + msg);
			} else {
				JOptionPane.showMessageDialog(LogInPage.logInPage, "欄位不能為空");
			}
		}
	}

	MessageDialogue mDialogue;
	JButton friendA;
	JPanel buttonBlock, pageBlock;
	JButton friends, chatRoom, addfriend, addgroup;
	CardLayout cardLayout;

	JTextArea my_friend;

	JPanel chatroom_panel = new JPanel();
	JPanel chatroom_list_panel = new JPanel(new GridLayout(0, 10));
	JScrollPane scrollPane = new JScrollPane(chatroom_list_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	static ChatPage chatPage;

	JTextField adduser = new JTextField();
	JButton adduser_confirm = new JButton("加入");
	JPanel addfriend_panel = new JPanel(new GridLayout());

	JButton addgroup_confirm = new JButton("創建群組");
	JTextField group_name = new JTextField();
	JPanel addgroup_panel = new JPanel(new BorderLayout());
	JPanel friend_checkbox_panel = new JPanel();
	ArrayList<JCheckBox> checkBoxs = new ArrayList<JCheckBox>();

	Boolean check = true;

	public void refresh() {
		my_friend.append(ChatClient.friendlist.get(ChatClient.friendlist.size() - 1) + "\n");
		JCheckBox checkbox = new JCheckBox(ChatClient.friendlist.get(ChatClient.friendlist.size() - 1));
		checkBoxs.add(checkbox);
		friend_checkbox_panel.add(checkbox);
	}
}