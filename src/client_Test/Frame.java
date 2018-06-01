package client_Test;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.lang.model.type.NullType;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Frame {

	String id;
	ArrayList<String> friendlist;
	ArrayList<Chatroom> chatroom;
	ArrayList<Record> record;
	
	static ChatClient chatClient = null;
	JFrame frame = null;
	
	JLabel tmp;
	JButton menu_btn,chatroom_btn,add_btn,create_btn; 
	JPanel menu_panel,chatroom_panel,add_panel,create_panel,button_panel;	
	CardLayout cardLayout = new CardLayout();
	final JPanel cardPanel = new JPanel(cardLayout);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame();
			}
		});
		chatClient = new ChatClient();
		chatClient.start();
	}

	public Frame() {
		frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btn = new JButton("Login!");
		JTextField accountTF = new JTextField(10);
		accountTF.setLocation(100, 100);
		accountTF.setSize(200, 50);
		JTextField passwordTF = new JTextField(10);
		passwordTF.setLocation(100, 200);
		passwordTF.setSize(200, 50);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chatClient.sendMsg(Identifier.CheckAccount + accountTF.getText().toString() + ","
						+ passwordTF.getText().toString());
			}
		});
		btn.setLocation(300, 400);
		btn.setSize(200, 50);
		frame.setLayout(null);
		frame.add(btn);
		frame.add(accountTF);
		frame.add(passwordTF);
		frame.setVisible(true);

		new SwingWorker<NullType, NullType>() {
			@Override
			protected NullType doInBackground() throws Exception {
				while (true) {
					if (chatClient.getStage() == 2) {
						return null;
					}
				}
			}

			protected void done() {
				frame.getContentPane().removeAll();
				frame.repaint();
				id = chatClient.getUserId();		
				friendlist = chatClient.getFriendlist();
				chatroom = chatClient.getChatroom();
				record = chatClient.getRecord();
				menu();
				if(chatClient.getFriendlist() == null);
					System.out.println("yes");
			}

		}.execute();
	}
	
	public void menu() {
		
		frame.setLayout(new BorderLayout());
		
		menu_btn = new JButton("首頁");
		chatroom_btn = new JButton("聊天室");
		add_btn = new JButton("加入好友");
		create_btn = new JButton("創建群組");
		
		button_panel = new JPanel();
		menu_panel = new JPanel();
		chatroom_panel = new JPanel();
		add_panel = new JPanel();
		create_panel = new JPanel();
		
		button_panel.add(menu_btn);
		button_panel.add(chatroom_btn);
		button_panel.add(add_btn);
		button_panel.add(create_btn);
		
		tmp = new JLabel("好友:" + System.getProperty("line.separator"));	
		menu_panel.add(tmp);
		chatroom_panel.add(new Label("World"));
		add_panel.add(new Label("Add"));
		create_panel.add(new Label("create"));
		
		cardPanel.add(menu_panel,"menu_panel");
		cardPanel.add(chatroom_panel,"chatroom_panel");
		cardPanel.add(add_panel,"add_panel");
		cardPanel.add(create_panel,"create_panel");
		
		menu_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				cardLayout.show(cardPanel, "menu_panel");
			}
		});
		
		chatroom_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "chatroom_panel");
			}
		});
		
		add_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "add_panel");
			}
		});
		
		create_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "create_panel");
			}
		});
		
		
		frame.add(button_panel,BorderLayout.NORTH);
		frame.add(cardPanel, BorderLayout.CENTER);
		frame.setTitle(id);
		frame.setVisible(true);
		
		

	}

}
