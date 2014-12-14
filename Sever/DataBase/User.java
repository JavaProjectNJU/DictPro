package DataBase;

import java.util.ArrayList;

public class User {
	private String account;
	private String pw;
	private ArrayList<User> onlineFriend;
	User(){}
	User(String account,String Pw)
	{
		this.account = account;
		this.pw = Pw;
		onlineFriend = new ArrayList<User>();
	}
	public boolean login()
	{
		boolean success = false;
		success = UserManager.identityVerify(account, pw);
		if(success)
			UserManager.addOnlineUser(this);
		return success;
	}
	
	public boolean logout()
	{
		boolean success = false;
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
			UserManager.friendJudge(account, _account);
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
}

