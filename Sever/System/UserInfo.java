package System;

import java.io.Serializable;
import java.util.ArrayList;

import DataBase.User;

public class UserInfo extends User implements Serializable{
//	String pw;  
	String IpAddr;	
	public String getIpAddr() {
		return IpAddr;
	}

	public void setIpAddr(String ipAddr) {
		IpAddr = ipAddr;
	}

	int prot;
	
	public UserInfo(User usr){
		super(usr);
	}
	
	public UserInfo(String nickname,String account,String pw){
		super(nickname,account,pw);
	}//UserInfo(String nickname,String account,String pw)

	public UserInfo(String nickname,String account,String pw,String IpAddr,int prot){
		super(nickname,account,pw);
		this.IpAddr=IpAddr;
		this.prot=prot;
	}//UserInfo(String nickname,String account,String pw,long date,String IpAddr,int prot)
	

	public void setprot(int prot){
		this.prot = prot;
	}

	public int getprot(){
		return prot;
	}
	
	public String getstrprot(){
		return Integer.toString(prot);
	}

	public void display(){
		super.display();
		System.out.println("Ip地址:      "+IpAddr);
		System.out.println("端口:        "+prot);
	}//public void display()
	
	public static void main(String[] args)
	{
		User usr1 = new User("Roy Zhang", "ruiyi", "123456"); 
		User usr3 = new User("Hao Wu", "haohao", "123456"); 
		User usr2 = new User("Ruijun Guo", "jam", "123456"); 
		//UserManager.createUser("ruiyi", "123456");
		//UserManager.createUser("haohao", "123456");
		//UserManager.createUser("jam", "123456");
		//usr1.login();
		//usr2.login();
		//usr3.login();
		usr1.delFriend("jam");
		//System.out.println(usr1.isFriend("haohao"));
		//System.out.println(usr1.isFriend("jam"));
		ArrayList<UserInfo> usr = usr1.getFriendOnline();
		for(int i = 0;i < usr.size();i ++)
			usr.get(i).display();
	}
}