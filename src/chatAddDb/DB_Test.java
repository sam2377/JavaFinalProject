package chatAddDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DB_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			DBHandler dbHandler = new DBHandler();
			
		
			dbHandler.connect();
			
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add("ccc");
			arrayList.add("ddd");
			//arrayList.add("ccc");
			
			ArrayList<ChatRoom> chatRooms = dbHandler.getInitInfo("ccc");
			for (ChatRoom chatRoom : chatRooms) {
				System.out.println(chatRoom.code + "  " + chatRoom.roomName);
			}
			
			arrayList = dbHandler.getReceiverList("FD8138D8B0F930955131F5E87D1C93D1");
			for (String r : arrayList) {
				System.out.println(r);
			}
			/*
			for(int i = 0; i < 100; ++i) {
				dbHandler.storeRecord("A91A9B24B702EA9C48C4AE5585860854", "aaa", Integer.toString(i));
				System.out.println(i);
				TimeUnit.SECONDS.sleep(1);
			}
			
			ArrayList<Record> record = dbHandler.getRecord("A91A9B24B702EA9C48C4AE5585860854");
			for (Record data : record) {
				System.out.println(data.sender + " : " + data.MSG);
				TimeUnit.SECONDS.sleep(1);
			}
			*/
			

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
