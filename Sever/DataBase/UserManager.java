package DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import System.Sever;
import System.UserInfo;

public class UserManager {
	private static ArrayList<UserInfo> onlineUser = new ArrayList<UserInfo>();
	
	@SuppressWarnings("finally")
	public static boolean createUser(String account,String Pw){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "insert into USERTABLE(username,password) values('"
					+account+"','"+Pw+"');";
			change = statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("User Exists!");
		}
		finally{
			DataBase.close(conn);
			return change;
		}
	}
	
	public static boolean createUser(String account,String Pw,String email){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "insert into USERTABLE(username,password,email) values('"
					+account+"','"+Pw+"','"+email+"');";
			change = statement.execute(sql);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				       "好友关系存在", "系统信息", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			DataBase.close(conn);
		}
		return change;
	}
	
	public static boolean addFriend(String account1, String account2){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = null;/*"select * from UserTable where username ='"+ account2 +"';";
			ResultSet result = statement.executeQuery(sql);
			if(!result.next())
			{
				System.out.println("User not exists");
				return false;
			}*/
			sql = "insert into FriendRelation(username1,username2) values('"
					+account1+"','"+account2+"');";
			change = true;
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FriendShip Exists!");
		}
		finally{
			DataBase.close(conn);
		}
		return change;
	}
	
	@SuppressWarnings("finally")
	public static boolean delFriend(String account1, String account2){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "delete from FriendRelation where username1='"
					+account1+"' and username2='"+account2+"';";
			change = statement.execute(sql);	
		} catch (SQLException e) {
			System.out.println("Not Exists!");
		}
		finally{
			DataBase.close(conn);
			return change;
		}
	}
	
	public static boolean delUser(String user){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "delete from UserTable where username='"
					+ user +"';";
			change = statement.execute(sql);	
		} catch (SQLException e) {
			System.out.println("Delete User Not Exists!");
		}
		finally{
			DataBase.close(conn);
		}
		return change;
	}
	
	public static ArrayList<UserInfo> getUserList(){
		Connection conn = null;
		ArrayList<UserInfo> userList = new ArrayList<UserInfo>();
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select * from UserTable";
			ResultSet result = statement.executeQuery(sql);
			//UserInfo usrinfoInfo = new UserInfo(result.getString("nickname"),result.getString("username"),null);
			UserInfo usrinfoInfo;
			while(result.next())
			{
				//
				usrinfoInfo = new UserInfo(result.getString("nickname") ,result.getString("username"),null);
				usrinfoInfo.setEmail(result.getString("email"));
				usrinfoInfo.setOn(result.getBoolean("online"));
				userList.add(usrinfoInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			DataBase.close(conn);
		}
		return userList;
	}
	
	@SuppressWarnings("finally")
	public static boolean changePassword(String account,String oldPw,String newPw){
		boolean change = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
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
		} catch (SQLException e) {
			System.out.println("Change Password Failed!");
		}
		finally{
			DataBase.close(conn);
			return change;
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean identityVerify(String account,String Pw){
		boolean isValid = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
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
		} catch (SQLException e) {
			System.out.println("identityVerify, Access Failed!");
		}
		finally{
			DataBase.close(conn);
			return isValid;
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean friendJudge(String account1,String account2){
		boolean isFriend = false;
		Connection conn = null;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			String sql = "select username1,username2 from FriendRelation "
					+ "where username1 = '"+ account1 +"' and username2 = '"+ account2 +"';";
//------------------------------------------------------
			ResultSet result = statement.executeQuery(sql);
			if(result.next())
				isFriend = true;
		} catch (SQLException e) {
			System.out.println("FriendJudge, DataBase Access Failed");
		}
		finally{
			DataBase.close(conn);
			return isFriend;
		}
	}
	
	public static boolean addOnlineUser(UserInfo user){
		boolean change = false;
		synchronized(onlineUser){
		change = onlineUser.add(user);
		//user.display();
		}
		return change;
	}
	
	public static boolean delOnlineUser(UserInfo user){
		boolean change = false;
		synchronized(onlineUser){
		change = onlineUser.remove(user);
		}
		return change;
	}
	
	public static ArrayList<UserInfo> getOnlineUser(){
		return onlineUser;
	}
	
	public static UserInfo getUserInfo(String uid){
		Connection conn = null;
		UserInfo usrinfoInfo;
		try {
			conn = DataBase.connect();
			Statement statement = conn.createStatement();
			System.out.println(uid);
			String sql = "select * from UserTable where username = '"+ uid +"';";
			ResultSet result = statement.executeQuery(sql);
			if(result.next())
			{
				usrinfoInfo = new UserInfo(result.getString("nickname") ,result.getString("username"),null);
				usrinfoInfo.setEmail(result.getString("email"));
				usrinfoInfo.setOn(result.getBoolean("online"));
				return usrinfoInfo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			DataBase.close(conn);
		}
		return null;
	}
	
	public static UserInfo getOtherOnlineUser(String uid){
		UserInfo usrinfoInfo = null;
		for(int i = 0; i < onlineUser.size(); i ++)
			if(onlineUser.get(i).getAccount().equals(uid))
			{
				usrinfoInfo = onlineUser.get(i);
				break;
			}
		return usrinfoInfo;
	}
	
	public static void main(String[] args){
		User usr = new User("zhangry","123456");
		User usr2 = new User("Jam","123456");
		User usr3 = new User("Haohao","123456");
		usr.login("ip1", 1);
		usr2.login("ip2", 1);
		usr3.login("ip3", 1);
		usr.addFriend("Haohao");
		UserInfo usrinfo = UserManager.getOtherOnlineUser("zhangry");
		usrinfo.display();
	}
}