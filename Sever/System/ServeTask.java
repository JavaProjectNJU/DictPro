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

import word.UnionWord;
import DataBase.DictionaryManager;
import DataBase.User;
import DataBase.UserManager;
import net.*;
import net.Message.*;
import net.Message.Message.*;

public class ServeTask extends Task implements Runnable{
	private Socket userSocket;
	private ObjectInputStream fromClient;
	private ArrayList<Message> msgBox;
	private User user = null;
	private WordEngine baidu, youdao, bing;
	static private Map msgMap;//share with all the server
	static private UserManager uManager;//share with all the server
	static private DictionaryManager dictManager;
	static private int idCreater = 0;
	public ServeTask(Socket socket, ArrayList<Message> msgBox){
		userSocket = socket;
		this.msgBox = msgBox;
		baidu = new FromBaidu();
		youdao = new FromYoudao();
		bing = new FromBing();
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
				synchronized(uManager){//still need some change !!!!!!!!!!!!!
					if(user != null)
						user.logout();
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
		Message.Send_Card sendCard = (Message.Send_Card)(msg.data);
		 
	}
	private void sendMsg(Message msg) {
		
		// TODO Auto-generated method stub
		Message.Send_Message sendMsg = (Message.Send_Message)(msg.data);
		Message reply = new Message();//转发
		reply.id = msg.id;
		reply.reply = true;//转发信息
		reply.type = Message.SEND_MESSAGE;
		Message.ReplyData data = reply.new ReplyData();
		reply.data = data;
		ArrayList<Message> targetMsgBox = null;
		synchronized(msgMap){//获得目标信箱地址
			targetMsgBox = (ArrayList<Message>)msgMap.get(sendMsg.targetuid);
		}
		if(targetMsgBox != null){//在线用户可以发送
			msg.id = idCreater ++;
			msg.reply = false;
			msg.type = Message.SEND_MESSAGE;
			synchronized(targetMsgBox){
				targetMsgBox.add(msg);
			}
			data.success = true;
		}else{//不能发送
			data.success = false;
		}
		synchronized(msgBox){
			msgBox.add(reply);//send the reply to tell the source  wether the msg send successfully
		}
		
	}
	private void search(Message msg) {
		// TODO Auto-generated method stub
		Message.Serach request = (Message.Serach)(msg.data);
		Message reply = new Message();
		reply.id = msg.id;
		reply.reply = true;
		reply.type = Message.SEARCH;
		Message.Serach ans = reply.new Serach();
		reply.data = ans;
		
		ans.word = dictManager.SearchWord(request.word.getWordstr());
	
		
		
		synchronized(msgBox){
			msgBox.add(reply);
		}
	}
	private void changePsw(Message msg) {
		// TODO Auto-generated method stub
		Message.ChangePsw chPsw= (Message.ChangePsw)(msg.data);
		Message reply = new Message();
		reply.id = msg.id;
		reply.type = Message.CHANGE_PSW;
		reply.reply = true;
		Message.ReplyData data = reply.new ReplyData();
		reply.data = data;
		if(uManager.changePassword(chPsw.uid, chPsw.oldpsw, chPsw.newpsw)){//check 
			data.success = true;
			user.setPw(chPsw.newpsw);
		}else{
			data.success = false;//faile to change
		}
		synchronized(msgBox){
			msgBox.add(reply);//send the reply
		}
	}
	private void logout(Message msg) {
		// TODO Auto-generated method stub
		Message.LogoutMsg logout = (Message.LogoutMsg)(msg.data);
		Message reply = new Message();
		reply.id = msg.id;
		reply.reply = true;
		reply.type = Message.LOGOUT;
		Message.ReplyData redata = reply.new ReplyData();
		reply.data = redata;
		if(uManager.identityVerify(logout.uid, logout.psw)){
			user.logout();
			redata.success = true;
			synchronized(msgMap){//then other can send msg
				msgMap.remove(user.getAccount());
			}
			
			//then clear user info
			user = null;
		}else{
			redata.success = false;
			
		}
		
		msgBox.add(reply);//send ack 
	}
	private void register(Message msg) {
		// TODO Auto-generated method stub
		Message.RegsiterMsg rMsgData = (Message.RegsiterMsg)(msg.data);
		Message reply = new Message();
		reply.id = msg.id;
		reply.reply = true;
		reply.type = Message.REGISTER;
		Message.ReplyData redata = reply.new ReplyData();
		reply.data = redata;
		if(uManager.createUser(rMsgData.uid, rMsgData.psw)){
			user = new User(rMsgData.uid, rMsgData.psw);
			
			
			synchronized(msgMap){
				msgMap.put(user.getAccount(), msgBox);//add msBox to map then others can send msg
			}
			user.login();//after create then autologin
			redata.success = true;
		}else{//create faile
			redata.success = false;
		}
		synchronized(msgBox){
			msgBox.add(reply);
		}
	}
	private void login(Message msg) {
		// TODO Auto-generated method stub
		// user info
		
		//
		
		user = new User(((Message.LoginMsg)(msg.data)).uid, ((Message.LoginMsg)(msg.data)).psw);
		Message replyMsg = new Message();
		replyMsg.id = msg.id;
		replyMsg.reply = true;
		replyMsg.type = Message.LOGIN;
		Message.ReplyData data = replyMsg.new ReplyData();
		replyMsg.data = data;
		boolean login = user.login();
		if(login){//login successful
			synchronized(msgMap){
				msgMap.put(user.getAccount(), msgBox);
			}
			user.getFriendOnline();
			data.success = true;
			synchronized(msgBox){
				msgBox.add(replyMsg);
			}
			
			Message online = new Message();
			online.id = msg.id;
			online.reply = true;
			Message.OnlineFriend friend = online.new OnlineFriend();
			online.data = friend;
			friend.friendList = user.getFriendOnline();
			
			synchronized(msgBox){
				msgBox.add(online);
			}
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
	public static void setDictionaryManager(DictionaryManager dictm){
		ServeTask.dictManager = dictm;
	}
}
