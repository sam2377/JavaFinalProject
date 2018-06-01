package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.lang.model.type.NullType;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class LogInPage extends JFrame implements ActionListener {

	static LogInPage logInPage;
	static ChatClient chatClient = null;

	private JTextField username = null;
	private JTextField password = null;

	private String id;
	
	//rflogjdfijhgbifgdjhiojoi
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				logInPage = new LogInPage();
				logInPage.setVisible(true);
			}
		});
		chatClient = new ChatClient();
		chatClient.start();
	}

	public LogInPage() {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Log In");
		username = new JTextField("<Your Username>");
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setLocation(300, 200);
		username.setSize(200, 50);
		// input.setText("<Input your name here>");
		add(username);
		password = new JTextField("<Your Password>");
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setLocation(300, 250);
		password.setSize(200, 50);
		add(password);

		logIn = new JButton("Log In");
		logIn.setLocation(350, 300);
		logIn.setSize(100, 25);
		logIn.addActionListener(this);
		add(logIn);
		signUp = new JButton("Sign Up");
		signUp.setLocation(350, 325);
		signUp.setSize(100, 25);
		signUp.addActionListener(this);
		add(signUp);

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
				id = chatClient.getUserId();
				MainPage mainPage = new MainPage(chatClient.getFriendlist(), chatClient.getChatroom(),
						chatClient.getRecord());
				mainPage.setVisible(true);
				setVisible(false);
			}

		}.execute();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logIn) {
			if (!username.getText().equals("") && !password.getText().equals("")) {
				chatClient.sendMsg(
						Identifier.CheckAccount + username.getText().toString() + "," + password.getText().toString());
			}else {
				JOptionPane.showMessageDialog(LogInPage.logInPage, "��줣�ର��");
			}
		}
		if (e.getSource() == signUp) {
			// SignUpPage signUpPage = new SignUpPage();
			// signUpPage.setVisible(true);
			// setVisible(false);
			if (!username.getText().equals("") && !password.getText().equals("")) {
				chatClient.sendMsg(
						Identifier.CreateAccount + username.getText().toString() + "," + password.getText().toString());
			}else {
				JOptionPane.showMessageDialog(LogInPage.logInPage, "��줣�ର��");
			}
		}

	}

	JButton logIn;
	JButton signUp;
}
