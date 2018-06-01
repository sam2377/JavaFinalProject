package client_Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient extends Thread {

	private String id;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private Socket socket = null;
	private ArrayList<String> friendlist = new ArrayList<String>();
	private ArrayList<Chatroom> chatroom = new ArrayList<Chatroom>();
	private ArrayList<Record> record = new ArrayList<Record>();
	
	private volatile int state = 0;

	public ChatClient() {
		try {
			socket = new Socket("140.116.111.113", 5050);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("connect error");
		}
	}
	
	public void disconnect() {
		System.out.println("disconnect");
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.close();
		state = 2;
	}
	
	public void reconnect() {
		System.out.println("reconnect");
		try {
			socket = new Socket("140.116.111.113", 5050);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("connect error");
		}
		state = 3;
	}

	public int getStage() {
		return state;
	}
	
	public void run() {
		String msg = null;
		while (true) {
			switch (state) {
			case 0:
				try {
					if (br.ready()) {
						msg = br.readLine();
						if(msg.equals(Identifier.LoginFailure)) {
							System.out.println("LoginFailure");
						}else if(msg.equals(Identifier.LoginSuccess)) {
							System.out.println("LoginSuccess");
							sendMsg(Identifier.ID);
							state = 1;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case 1:
				try {
					if (br.ready()) {
						msg = br.readLine();
						System.out.println("msg: "+ msg);
						if(msg.contains(Identifier.ID)){
							msg = msg.replace(Identifier.ID, "");
							id = msg;
							sendMsg(Identifier.Initialize);
						}else if(msg.contains(Identifier.FriendData)){
							msg = msg.replace(Identifier.FriendData, "");
							friendlist.add(msg);
						}else if(msg.contains(Identifier.ChatroomData)) {
							msg = msg.replace(Identifier.ChatroomData, "");
							String[] split_line = msg.split(",");
							chatroom.add(new Chatroom(split_line[0], split_line[1]));
						}else if(msg.contains(Identifier.RecordData)) {
							msg = msg.replace(Identifier.RecordData, "");
							String[] split_line = msg.split(",");
							record.add(new Record(split_line[0], split_line[1]));
						}else if(msg.contains(Identifier.StateThree)) {
							state = 2;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case 2:
				break;
			case 3:
				break;
			}
		}
	}
	public void sendMsg(String msg) {
		pw.println(msg);
		pw.flush();
	}
	
	public String getUserId() {
		return id;
	}
	
	public ArrayList<String> getFriendlist() {
		return friendlist;
	}
	
	public ArrayList<Chatroom> getChatroom() {
		return chatroom;
	}
	
	public ArrayList<Record> getRecord(){
		return record;
	}
}
