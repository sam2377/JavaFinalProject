package ui;

import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MessageDialogue extends JPanel implements ActionListener{
	
	public MessageDialogue() {
		setBackground(Color.black);
		setSize(500, 500);
		setVisible(true);
		JTextArea textBlock = new JTextArea("gmflkreslirfj;soeifemrikmf");
		textBlock.setSize(400, 300);
		textBlock.setLocation(50, 50);
		textBlock.setBackground(Color.white);
		add(textBlock);
		JTextArea typingBlock = new JTextArea("ilfjsifsirjf");
		typingBlock.setSize(400, 100);
		typingBlock.setLocation(50, 375);
		add(typingBlock);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
