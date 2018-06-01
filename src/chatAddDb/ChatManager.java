package chatAddDb;

import java.util.ArrayList;


public class ChatManager {
 
    public static ArrayList<ChatSocket> CSList = new ArrayList<ChatSocket>();
    public void addsocket(ChatSocket socket) {
        CSList.add(socket);
    }
     
    public void Sendmessage(ChatSocket socket,String msg){
        for (int i = 0; i < CSList.size(); i++) {
            ChatSocket cs = CSList.get(i);
            if(socket.hcode.equals(cs.hcode) && socket.hcode!=null && cs.hcode!=null)
            	cs.outprint(msg);
        }
    }
}