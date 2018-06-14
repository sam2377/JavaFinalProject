package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.lang.model.type.NullType;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.*; /* for nimbus look and feel */


public class LogInPage extends JFrame implements ActionListener {

	static LogInPage logInPage;
	static MainPage mainPage;
	static ChatClient chatClient = null;

	private JTextField username = null;
	private JTextField password = null;
	private JLabel icon = null;

	private String id;

	// master commit

	public static void main(String[] args) {
		
		
		try {
    			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        			if ("Nimbus".equals(info.getName())) {
            			UIManager.setLookAndFeel(info.getClassName());
            			break;
        			}
    			}
		} catch (Exception e) {} /* set up nimbus look and feel */
		
		
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
		
		icon = new JLabel("CHAT BOX");
		icon.setFont(new java.awt.Font("CHAT BOX", 1 , 50));
		icon.setLocation(265, 100);
		icon.setSize(400, 100);
		add(icon);
		
		username = new JTextField("<Your Username>");
		username.setForeground(new java.awt.Color(204, 204, 204));
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setLocation(300, 200);
		username.setSize(200, 50);

		add(username);
		password = new JTextField("<Your Password>");
		password.setForeground(new java.awt.Color(204, 204, 204));
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setLocation(300, 250);
		password.setSize(200, 50);
		add(password);
		
		username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(username.getForeground()!= Color.BLACK){
                    if(username.getText().equals("<Your Username>")){
                        username.setText("");
                    }
                }
                username.setForeground(Color.BLACK);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
            	if(username.getText().isEmpty()==true){
                    username.setText("<Your Username>");
                    username.setCaretPosition(0);
                    username.setForeground(new java.awt.Color(204,204,204));
                }
            }
        });
		password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(password.getForeground()!= Color.BLACK){
                    if(password.getText().equals("<Your Password>")){
                        password.setText("");
                    }
                }
                password.setForeground(Color.BLACK);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
            	if(password.getText().isEmpty()==true){
                    password.setText("<Your Password>");
                    password.setCaretPosition(0);
                    password.setForeground(new java.awt.Color(204,204,204));
                }
            }
        });
		
		
//		username.addFocusListener(new FocusListener() {
//			public void focusLost(FocusEvent e) {
//
//			}
//			public void focusGained(FocusEvent e) {
//				username.setText("");
//			}
//		});
//		password.addFocusListener(new FocusListener() {
//			public void focusLost(FocusEvent e) {
//	
//			}
//			public void focusGained(FocusEvent e) {
//				password.setText("");
//			}
//		});

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
				mainPage = new MainPage();
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
				JOptionPane.showMessageDialog(LogInPage.logInPage, "欄位不能為空");
			}
		}
		if (e.getSource() == signUp) {

			if (!username.getText().equals("") && !password.getText().equals("")) {
				chatClient.sendMsg(
						Identifier.CreateAccount + username.getText().toString() + "," + password.getText().toString());
			}else {
				JOptionPane.showMessageDialog(LogInPage.logInPage, "欄位不能為空");
			}
		}

	}

	JButton logIn;
	JButton signUp;
}
