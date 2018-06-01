package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SignUpPage extends JFrame implements ActionListener{
	
	public SignUpPage() {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Sign Up");
		JTextField username = new JTextField();
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setLocation(300, 200);
		username.setSize(200, 25);
		add(username);
		JTextField password = new JTextField("<Your Password>");
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setLocation(300, 235);
		password.setSize(200, 25);
		add(password);
		
		username.addFocusListener(new FocusListener() {
			
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					username.setText("<Your Username>");
				}
				
			
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					username.setText("");
				}
			});
		
		finish = new JButton("Finish");
		finish.setLocation(350, 270);
		finish.setSize(100, 25);
		finish.addActionListener(this);
		add(finish);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finish) {
			LogInPage logInPage = new LogInPage();
			logInPage.setVisible(true);
			setVisible(false);
		}
	}
	
	JButton finish;
}
