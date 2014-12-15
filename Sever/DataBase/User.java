package DataBase;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.Icon;

import word.Word;

public class User implements Serializable{
	protected String nickname;
	protected String account;
	protected String pw;
	protected ArrayList<User> onlineFriend;
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
		onlineFriend = new ArrayList<User>();
	}
	
	public User(String nickname,String account,String Pw)
	{
		this.nickname = nickname;
		this.account = account;
		this.pw = Pw;
		onlineFriend = new ArrayList<User>();
	}
	
	public boolean login()
	{
		boolean success = false;
		success = UserManager.identityVerify(account, pw);
		on = true;
		date = new Date(System.currentTimeMillis());
		if(success)
			UserManager.addOnlineUser(this);
		return success;
	}
	
	public boolean logout()
	{
		boolean success = false;
		on = false;
		success = UserManager.identityVerify(account, pw);
		if(success)
			UserManager.delOnlineUser(this);
		return success;
	}
	
	public boolean addFriend(String _account)
	{
		//A more complex Implement Needed!!!!!!!!!!!!!!!!!!!!!!!
		boolean success = false;
		success = UserManager.friendJudge(this.account, _account) | UserManager.friendJudge(_account, this.account);
		if(!success)
		{
			UserManager.addFriend(this.account, _account);
		}
		return success;
	}
	
	public boolean delFriend(String _account)
	{
		//A more complex Implement Needed!!!!!!!!!!!!!!!!!!!!!!!
		boolean success = false;
		success = UserManager.friendJudge(this.account, _account);
		if(success)
			UserManager.delFriend(this.account, _account);
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
		ArrayList<User> temp = UserManager.getOnlineUser();
		for(int i = 0;i < temp.size();i ++)
			if(temp.get(i).isFriend(this.account))
				onlineFriend.add(temp.get(i));
	}
	
	public ArrayList<User> getFriendOnline()
	{
		updateFriendOnline();
		return onlineFriend;
	}
	
	public Word SearchWord(String word)
	{
		boolean getword = DictionaryManager.searchWord(word);
		if (!getword) {
			DictionaryManager.onlineSearchWord(word);
		}
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
}

