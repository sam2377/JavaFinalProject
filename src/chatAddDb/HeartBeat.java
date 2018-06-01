package chatAddDb;

import java.io.PrintStream;

import java.net.Socket;

public class HeartBeat extends Thread{
	@Override
	public void run() {
		while(true) {
			System.out.println(ChatManager.CSList.size());
			for(int i=0;i<ChatManager.CSList.size();i++) {
				Socket socket = ChatManager.CSList.get(i).socket;
		        System.out.println(socket.getInetAddress().toString()+"connect");
			}
			try {
				sleep(1000);
			} catch (Exception e) {
				System.out.println("Exception");
			}
		}	
	}
}
