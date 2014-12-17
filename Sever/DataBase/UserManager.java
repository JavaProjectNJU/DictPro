package DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import System.UserInfo;

public class UserManager {
	private static ArrayList<UserInfo> onlineUser = new ArrayList<UserInfo>();
	public static boolean createUser(String account,String Pw){
		boolean change = false;
		try {
			Connection conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "insert into USERTABLE(username,password) values('"
					+account+"','"+Pw+"');";
			change = statement.execute(sql);
			DataBase.close(conn);
		} catch (SQLException e) {
			System.out.println("User Exists!");
		}
		return change;
	}
	
	public static boolean addFriend(String account1, String account2){
		boolean change = false;
		try {
			Connection conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "insert into FriendRelation(username1,username2) values('"
					+account1+"','"+account2+"');";
			change = statement.execute(sql);
			DataBase.close(conn);
		} catch (SQLException e) {
			System.out.println("Friendship Exists!");
		}
		return change;
	}
	
	public static boolean delFriend(String account1, String account2){
		boolean change = false;
		try {
			Connection conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "delete from FriendRelation where username1='"
					+account1+"' and username2='"+account2+"';";
			change = statement.execute(sql);
			DataBase.close(conn);
		} catch (SQLException e) {
			System.out.println("Not Exists!");
		}
		return change;
	}
	
	public static boolean changePassword(String account,String oldPw,String newPw){
		boolean change = false;
		try {
			Connection conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select username,password from USERTABLE;";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()){
				if(account.equals(result.getString("username"))
						&&oldPw.equals(result.getString("password"))){
					change = statement.execute("update user set password = '"+newPw+"' where username = '"+account+"';");
					break;
				}
			}
			DataBase.close(conn);
		} catch (SQLException e) {
			System.out.println("Change Password Failed!");
		}
		return change;
	}
	
	@SuppressWarnings("finally")
	public static boolean identityVerify(String account,String Pw){
		boolean isValid = false;
		try {
			Connection conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select username,password from USERTABLE;";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()){
				if(account.equals(result.getString("username"))
						&&Pw.equals(result.getString("password"))){
					isValid = true;
					break;
				}
			}
			DataBase.close(conn);
		} catch (SQLException e) {
			System.out.println("identityVerify, Access Failed!");
		}
		finally{
			return isValid;
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean friendJudge(String account1,String account2){
		boolean isFriend = false;
		try {
			Connection conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select username1,username2 from FriendRelation "
					+ "where username1 = '"+ account1 +"' and username2 = '"+ account2 +"';";
//------------------------------------------------------
			ResultSet result = statement.executeQuery(sql);
			if(result.next())
				isFriend = true;
			DataBase.close(conn);
		} catch (SQLException e) {
			System.out.println("FriendJudge, DataBase Access Failed");
		}
		finally{
			return isFriend;
		}
	}
	
	public static boolean addOnlineUser(UserInfo user){
		boolean change = false;
		change = onlineUser.add(user);
		return change;
	}
	
	public static boolean delOnlineUser(UserInfo user){
		boolean change = false;
		change = onlineUser.remove(user);
		return change;
	}
	
	public static ArrayList<UserInfo> getOnlineUser(){
		return onlineUser;
	}
	
	public static UserInfo getUserInfo(String uid){
		UserInfo usrInfo = null;
		int i;
		for(i = 0;i < onlineUser.size(); i ++)
		{
			if(onlineUser.get(i).getAccount().equals(uid))
			{	
				usrInfo = onlineUser.get(i);
				break;
			}
		}
		return usrInfo;
	}
}