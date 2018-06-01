package chatAddDb;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.sql.Connection;

public class DBHandler {
	
	private String datasource = "jdbc:mysql://140.116.82.52:3306/2018JavaProject?characterEncoding=utf8";
	private String user = "java";
	private String password = "ncku";
	
	private Connection conn;
	
	
	public DBHandler() throws ClassNotFoundException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	public void connect() throws SQLException{
		try {
			conn = DriverManager.getConnection(datasource, user, password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean confirmAccount(String account) {
		boolean result = false;
		try {
			Statement statement = conn.createStatement();
			statement.executeQuery("SELECT account FROM user WHERE account = '" + account + "'");
			if(statement.getResultSet().next()) {
				result = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean login(String account, String password) {
		boolean result = false;
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT null FROM user WHERE account = ? AND password = ?");
			statement.setString(1, account);
			statement.setString(2, password);
			statement.executeQuery();
			if(statement.getResultSet().next()) {
				result = true;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean register(String account, String password) {
		boolean result = false;
		try {
			if(!confirmAccount(account)) {
				PreparedStatement statement = conn.prepareStatement("INSERT INTO user(account,password) VALUES(?,?)");
				statement.setString(1, account);
				statement.setString(2, password);
				if(statement.executeUpdate() == 1) {
					result = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean isFriend(String user, String friend) {
		boolean result = false;
		if(confirmAccount(user) && confirmAccount(friend)) {
			try {
				PreparedStatement statement = conn.prepareStatement("SELECT null FROM friendMap WHERE user = ? AND friend = ?");
				statement.setString(1, user);
				statement.setString(2, friend);
				statement.executeQuery();
				if(statement.getResultSet().next()) {
					result = true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean addFriend(String user, String friend) {
		boolean result = false;
		if(confirmAccount(user) && confirmAccount(friend) && !isFriend(user, friend)) {
			try {
				PreparedStatement statement = conn.prepareStatement("INSERT INTO friendMap(user,friend) VALUES(?,?)");
				statement.setString(1, user);
				statement.setString(2, friend);
				if(statement.executeUpdate() == 1) {
					result = true;
				}
				statement.setString(1, friend);
				statement.setString(2, user);
				if(!result || statement.executeUpdate() != 1) {
					result = false;
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add(user);
		arrayList.add(friend);
		createChatRoom(arrayList, null);
		return result;
	}
	
	public ArrayList<String> getFriendList(String user) {
		ArrayList<String> friendList = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT friend FROM friendMap WHERE user = ?");
			statement.setString(1, user);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();
			while(resultSet.next()) {
				friendList.add(resultSet.getString("friend"));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return friendList;
	}
	
	public ArrayList<ChatRoom> getInitInfo(String user) {
		ArrayList<ChatRoom> initInfo = new ArrayList<>();
		try {
			ArrayList<String> friendList = this.getFriendList(user);
			for (String friend : friendList) {
				String string = null;
				if(user.compareTo(friend) <= 0) {
					string = user + friend;
				} else {
					string = friend + user;
				}
				String code = MD5(string);
				initInfo.add(new ChatRoom(code, friend));
			}
			PreparedStatement statement = conn.prepareStatement("SELECT code, GroupName FROM RoomMap WHERE GroupName IS NOT NULL AND member = ?");
			statement.setString(1, user);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();
			while(resultSet.next()) {
				initInfo.add(new ChatRoom(resultSet.getString("code"), resultSet.getString("GroupName")));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return initInfo;
	}
	
	public void createChatRoom(ArrayList<String> memberList,  String GroupName) {
		Collections.sort(memberList);
		String string = memberList.get(0);
		for(int i = 1; i< memberList.size(); ++i) {
			string += memberList.get(i);
		}
		if(GroupName != null) {
			string += GroupName;
		}
		String code = MD5(string);
		if(codeExist(code)) {
			return;
		}
		
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO RoomMap(code,GroupName,member) VALUES(?,?,?)");
			statement.setString(1, code);
			if(GroupName == null) {
				statement.setNull(2, Types.VARCHAR);
			} else {
				statement.setString(2, GroupName);
			}
			for (String member : memberList) {
				statement.setString(3, member);
				statement.executeUpdate();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean codeExist(String code) {
		boolean result = false;
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT null FROM RoomMap WHERE code = ?");
			statement.setString(1, code);
			statement.executeQuery();
			if(statement.getResultSet().next()) {
				result = true;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public void arrangeRecord(String code) {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT null FROM Record WHERE code = ?");
			statement.setString(1, code);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();
			resultSet.last();
			int n = resultSet.getRow();
			int howManyToKeep = 20;
			if(n > howManyToKeep) {
				PreparedStatement statement2 = conn.prepareStatement("DELETE FROM Record WHERE code = ? ORDER BY time ASC LIMIT ?");
				statement2.setString(1, code);
				statement2.setInt(2, n - howManyToKeep);
				statement2.execute();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void storeRecord(String code, String sender, String MSG) {
		try {
		PreparedStatement statement = conn.prepareStatement("INSERT INTO Record(code,sender,MSG) VALUES(?,?,?)");
		statement.setString(1, code);
		statement.setString(2, sender);
		statement.setString(3, MSG);
		statement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public ArrayList<Record> getRecord(String code) {
		arrangeRecord(code);
		ArrayList<Record> record = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT sender, MSG FROM Record WHERE code = ?");
			statement.setString(1, code);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();
			while(resultSet.next()) {
				Record data = new Record(resultSet.getString("sender"), resultSet.getString("MSG"));
				record.add(data);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return record;
	}
	
	public ArrayList<String> getReceiverList(String code) {
		ArrayList<String> receiverList = new ArrayList<>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT member FROM RoomMap WHERE code = ?");
			statement.setString(1, code);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();
			while(resultSet.next()) {
				receiverList.add(resultSet.getString("member"));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return receiverList;
	}
	
	public static String MD5(String string) {
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] barr = md.digest(string.getBytes());
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < barr.length; ++i) {
				sb.append(Byte2Hex(barr[i]));
			}
			md5 = sb.toString().toUpperCase();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return md5;
	}
	
	private static String Byte2Hex(byte b) {
		String[] h={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
		int i = b;
		if(i < 0) {
			i += 256;
		}
		return h[i/16] + h[i%16];
	}
	
}
