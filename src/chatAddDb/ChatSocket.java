package chatAddDb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatSocket extends Thread {

	DBHandler dbHandler;
	Socket socket;
	String id;
	String hcode;

	public ChatSocket(Socket s) throws ClassNotFoundException, SQLException {
		this.socket = s;
		dbHandler = new DBHandler();
		dbHandler.connect();
	}

	public void outprint(String out) {

		try {
			PrintStream pw;
			pw = new PrintStream(socket.getOutputStream(), true, "UTF-8");
			pw.println(out);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String msg;

			while ((msg = br.readLine()) != null) {
				System.out.println("server receive:" + msg);
				if (msg.contains(Identifier.CreateAccount)) {
					msg = msg.replace(Identifier.CreateAccount,"");
					String[] split_line = msg.split(",");
					
					if(dbHandler.register(split_line[0], split_line[1])) {
						outprint(Identifier.CreateAccountS);
					}else {
						outprint(Identifier.CreateAccountF);
					}
 				} else if (msg.contains(Identifier.CheckAccount)) {
					msg = msg.replace(Identifier.CheckAccount, "");
					String[] split_line = msg.split(",");

					if (dbHandler.login(split_line[0], split_line[1])) {
						outprint(Identifier.LoginSuccess);
						id = split_line[0];
					} else {
						outprint(Identifier.LoginFailure);
					}
					System.out.println("現在使用者個數: " + ChatManager.CSList.size());
					for (int i = 0; i < ChatManager.CSList.size(); i++) {
						System.out.printf("使用者%d IP Address: %s, ID: %s\n", i + 1,
								ChatManager.CSList.get(i).socket.getInetAddress().toString(),
								ChatManager.CSList.get(i).id);
					}
				} else if (msg.contains(Identifier.ID)) {
					outprint(Identifier.ID + id);
				} else if (msg.contains(Identifier.Initialize)) {
					ArrayList<String> friendlist = dbHandler.getFriendList(id);
					for (int i = 0; i < friendlist.size(); i++) {
						outprint(Identifier.FriendData + friendlist.get(i));
					}
					ArrayList<ChatRoom> chatRoom = dbHandler.getInitInfo(id);
					for (int i = 0; i < chatRoom.size(); i++) {
						outprint(Identifier.ChatroomData + chatRoom.get(i).code + "," + chatRoom.get(i).roomName);
					}
					ArrayList<Record> record = dbHandler.getRecord(id);
					for (int i = 0; i < record.size(); i++) {
						outprint(Identifier.RecordData + record.get(i).sender + "," + record.get(i).MSG);
					}
					outprint(Identifier.StateTwo);
				} else if(msg.contains(Identifier.AddFriend)){
					msg = msg.replace(Identifier.AddFriend, "");
					if(dbHandler.addFriend(id, msg)) {
						outprint(Identifier.AddFriendS + msg);
					}else {
						outprint(Identifier.AddFriendF);
					}
				} else {
					ServerListener.chatManager.Sendmessage(this, id + ": " + msg);
				}
			}
			// br.close();

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {

		} finally {
			ChatManager.CSList.remove(this);
			System.out.println("現在使用者個數: " + ChatManager.CSList.size());
			for (int i = 0; i < ChatManager.CSList.size(); i++) {
				System.out.printf("使用者%d IP Address: %s, ID: %s\n", i + 1,
						ChatManager.CSList.get(i).socket.getInetAddress().toString(), ChatManager.CSList.get(i).id);
			}
		}
	}
}