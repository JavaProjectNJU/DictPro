package System;

import word.Word;
import net.FromBaidu;
import net.WordEngine;
import DataBase.DataBase;
import DataBase.User;
import DataBase.UserManager;

public class Entry {
	public static void main(String[] args)
	{
		System.out.println("Hello,This is the sever!!");
		//DataBase.connect();
		//User usr = new User();
		//usr.createUser("roy3", "123456");
		//WordEngine baidu = new FromBaidu();
		//baidu.search("give");
		UserManager.delFriend("zhangry868", "roy");
		UserManager.addFriend("zhangry868", "roy");
		System.out.println(UserManager.friendJudge("zhangry868", "roy"));
		ThreadPool thPool = new ThreadPool(10);
		thPool.addTask(new PrintMessage("Hello1"));
		thPool.addTask(new PrintMessage("World1"));
		thPool.addTask(new PrintMessage("Hello2"));
		thPool.addTask(new PrintMessage("World2"));
	}
}

class PrintMessage extends Task
{
	String str;
	PrintMessage(String _str){
		str = _str;
	}
	@Override
	public Task[] taskCore() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean needExecuteImmediate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return new String("PrintMessage");
	}
	
	public void run()
	{
		while(true)
			System.out.println(str);
	}
}