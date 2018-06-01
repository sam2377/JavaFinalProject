package chatAddDb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Timer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
 
public class ServerListener extends Thread{
 
    public static ChatManager chatManager = new ChatManager();
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5050);
            while (true) {
                Socket socket = serverSocket.accept();
                ChatSocket cs = new ChatSocket(socket);
                cs.start();
                chatManager.addsocket(cs);
            }
         
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
     
}