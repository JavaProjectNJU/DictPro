package DataBase;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.Icon;

import System.UserInfo;
import word.Word;

public class User implements Serializable{
	protected String nickname;
	protected String account;
	protected String pw;
	protected String email;
	protected ArrayList<UserInfo> onlineFriend;
	protected boolean on = false;
	protected boolean sex = true;
	protected Icon image = null;
	protected Date date = null;		//date是上次上线时间
	protected String strdate; 	//date的字符串表示

	public User(){}
	public User(String account,String Pw)
	{
		this.account = account;
		this.pw = Pw;
		onlineFriend = new ArrayList<UserInfo>();
	}
	
	public User(String nickname,String account,String Pw)
	{
		this.nickname = nickname;
		this.account = account;
		this.pw = Pw;
		onlineFriend = new ArrayList<UserInfo>();
	}
	
	protected User(User usr) {
		// TODO Auto-generated constructor stub
		nickname = usr.nickname;
		account = usr.account;
		pw = null;
		onlineFriend = usr.onlineFriend;
		on = usr.on;
		sex = usr.sex;
		image = usr.image;
		date = usr.date;		//date是上次上线时间
		strdate = usr.strdate; 
	}
	
	public boolean login(String ip,int port)
	{
		boolean success = false;
		success = UserManager.identityVerify(account, pw);
		on = true;
		date = new Date(System.currentTimeMillis());
		if(success)
		{
			UserInfo tmpUserInfo = new UserInfo(this);
			tmpUserInfo.setprot(port);
			tmpUserInfo.setIpAddr(ip);
			UserManager.addOnlineUser(tmpUserInfo);
		}
		return success;
	}
	
	public boolean logout()
	{
		boolean success = false;
		on = false;
		success = UserManager.identityVerify(account, pw);
		if(success)
		{
			UserInfo tmpUserInfo = new UserInfo(this);
			UserManager.delOnlineUser(tmpUserInfo);
		}
		return success;
	}
	
	public boolean addFriend(String _account)
	{
		//A more complex Implement Needed!!!!!!!!!!!!!!!!!!!!!!!
		boolean success = false;
		success = UserManager.friendJudge(this.account, _account) | UserManager.friendJudge(_account, this.account);
		if(!success)
		{
			success = UserManager.addFriend(this.account, _account);
		}
		return success;
	}
	
	public boolean delFriend(String _account)
	{
		//A more complex Implement Needed!!!!!!!!!!!!!!!!!!!!!!!
		boolean success = false;
		success = UserManager.friendJudge(this.account, _account);
		if(success)
			success = UserManager.delFriend(this.account, _account);
		return success;
	}
	
	public boolean isFriend(String _account)
	{
		boolean success = false;
		success = UserManager.identityVerify(account, pw);
		if(success)
			success = UserManager.friendJudge(account, _account);
		if(!success)
			success = UserManager.friendJudge(_account, account);
		return success;
	}
	
	public boolean changePassword(String oldPw,String newPw){
		boolean change = false;
		change = UserManager.changePassword(account, oldPw, newPw);
		return change;
	}
	
	public void updateFriendOnline()
	{
		onlineFriend.clear();
		ArrayList<UserInfo> temp = UserManager.getOnlineUser();
		for(int i = 0;i < temp.size();i ++)
			if(temp.get(i).isFriend(this.account))
				onlineFriend.add(temp.get(i));
	}
	
	public ArrayList<UserInfo> getFriendOnline()
	{
		updateFriendOnline();
		return onlineFriend;
	}
	
	public Word SearchWord(String word)
	{
		return new Word();
	}
	
	public void display()
	{
		System.out.println("用户名:       "+account);
		System.out.println("昵称:        "+nickname);
		System.out.println("密码:        "+pw);
		System.out.println("在线:        "+on);
		System.out.println("性别:        "+sex);
		System.out.println("上次登录:     "+date);
	}
	
	public UserInfo UpdateUserInfo()
	{
		
		return null;	
	}
		
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public Icon getImage() {
		return image;
	}
	public void setImage(Icon image) {
		this.image = image;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStrdate() {
		return strdate;
	}
	public void setStrdate(String strdate) {
		this.strdate = strdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

