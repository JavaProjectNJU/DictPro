package System;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataBase.User;
import DataBase.UserManager;
import net.Message.Message;
import net.Message.Message.*;

public class ServeTask extends Task implements Runnable{
	private Socket userSocket;
	private ObjectInputStream fromClient;
	private ArrayList<Message> msgBox;
	private User user = null;
	static private Map msgMap;//share with all the server
	static private UserManager uManager;//share with all the server
	public ServeTask(Socket socket, ArrayList<Message> msgBox){
		userSocket = socket;
		this.msgBox = msgBox;
		try {
			fromClient = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public Task[] taskCore() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean needExecuteImmediate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}
	public void run() {
		DataInputStream fromClientData = new DataInputStream(fromClient);
		if(fromClientData == null)
			return;

		
		while(true){
			try {
				Message msg = (Message)(fromClient.readObject());
				if(msg.reply){// themsg is a reply
					
				}else{//then it is a request 
					switch(msg.type){
					case Message.LOGIN:login(msg);break;
					case Message.REGISTER:register(msg);break;
					case Message.LOGOUT:logout(msg);break;
					case Message.CHANGE_PSW:changePsw(msg);break;
					case Message.SEARCH:search(msg);break;
					case Message.SEND_MESSAGE:sendMsg(msg);break;
					case Message.SEND_CARD:sendCard(msg);break;
					case Message.ADD_FRIEND:addFriend(msg);break;
					case Message.ADD_PRAISE:addPrise(msg);break;
					case Message.DEL_FRIEND:delFriend(msg);break;
					case Message.DEL_PRAISE:delPrise(msg);break;
					case Message.UPDATE_FRIEND_ONLINE:updateOnlineFriend(msg);break;
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{//if any exception has been throws then we shoule logout the user
				synchronized(uManager){
					if(user != null)
						uManager.delOnlineUser(user);
				}
				synchronized(msgMap){
					if(msgBox != null){
						msgMap.remove(user.getAccount());
					}
				}
				try {
					fromClient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user = null;
			}
		}
	}
	private void updateOnlineFriend(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void delPrise(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void addPrise(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void delFriend(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void addFriend(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void sendCard(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void sendMsg(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void search(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void changePsw(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void logout(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void register(Message msg) {
		// TODO Auto-generated method stub
		
	}
	private void login(Message msg) {
		// TODO Auto-generated method stub
		
		user = new User(((Message.LoginMsg)(msg.data)).uid, ((Message.LoginMsg)(msg.data)).psw);
		Message replyMsg = new Message();
		replyMsg.id = msg.id;
		replyMsg.reply = true;
		Message.ReplyData data = new Message.ReplyData();
		boolean login = user.login()
		if(login){//login successful
			uManager.addOnlineUser(user);
			msgMap.put(user.getAccount(), msgBox);
			user.getFriendOnline();
			data.success = true;
			synchronized(msgBox){
				msgBox.add(replyMsg);
			}
			
			Message online = new Message();
			online.id = msg.id;
			online.reply = true;
			Message.OnlineFriend friend = new Message.OnlineFriend();
			friend.friendList = user.getFriendOnline();
		}else{// login faild
			data.success = false;
			synchronized(msgBox){
				msgBox.add(replyMsg);
			}
		}
		
	}
	public static void setMsgMap(Map msgMap){
		ServeTask.msgMap = msgMap;
	}
	
	public static void setUserManager(UserManager um){
		ServeTask.uManager = um;
	}
}
