package ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ChatCellRenderer extends DefaultListCellRenderer{
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Chatroom) {
            setText(((Chatroom)value).roomName);
        }
        return this;
    }
}
