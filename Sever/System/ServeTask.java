package System;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import sun.net.www.content.text.plain;
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
	static private Map msgMap;//share with all the server
	//static private UserManager uManager;//share with all the server
	static private DictionaryManager dictManager;
	static private int idCreater = 0;
	byte[] buff = null;
	public ServeTask(Socket socket, ArrayList<Message> msgBox){
		userSocket = socket;
		try {
			userSocket.setTcpNoDelay(true);
			
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.msgBox = msgBox;
		
		
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

		
		while(true){
			Message msg = null;
			if(userSocket == null || userSocket.isClosed())//
				break;
			try{
				System.out.println("server running a servertask");

				if(fromClient == null)
					fromClient = new ObjectInputStream(new BufferedInputStream(userSocket.getInputStream()));	
				
				msg = (Message)(fromClient.readObject());
				//char eof = fromClient.readChar();//将文件结束符读出来；
				//fromClient.close();
				
				System.out.println("server readed a msg");
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				fromClient = null;//重置输入流
				e.printStackTrace();
				break;
			}
			if(msg == null)
				continue;
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
				case Message.USER_INFO:getUserInfo(msg);break;
				case Message.IS_ONLINE:getOnlineState(msg);break;
				default:;
				}
				
			}
		
		}
	
	}
	private void getOnlineState(Message msg){
		Message.IsOnline isonline = (Message.IsOnline)(msg.data);
		msg.reply = true;
		msg.type = Message.IS_ONLINE;
		if(user == null || !user.isOn() || !UserManager.identityVerify(isonline.uid, isonline.psw)){
			isonline.isOnline = false;
			System.out.println("Failed");
		}else{
			isonline.isOnline = true;
			System.out.println("Is Online!!!!");
		}
		msgBox.add(msg);
	}
	
	private void getUserInfo(Message msg){
		Message.Info uinfo = (Message.Info)(msg.data);
		msg.reply = true;
		msg.type = Message.USER_INFO;
		if(user == null || !user.isOn() || !UserManager.identityVerify(uinfo.uid, uinfo.psw)){
			uinfo.myself = null;
		}else{
			//uinfo.myself = new UserInfo(user);
			uinfo.myself = UserManager.getUserInfo(user.getAccount());
		}
		synchronized(msgBox){
			msgBox.add(msg);
		}
	}
	private void updateOnlineFriend(Message msg) {
		// TODO Auto-generated method stub
		Message.OnlineFriend onlineFriend = (Message.OnlineFriend)(msg.data);
		msg.reply = true;
		if(user == null || !user.isOn() || !UserManager.identityVerify(onlineFriend.uid, onlineFriend.psw)){

			onlineFriend.friendList = null;
			System.out.println("cant get friend list");
		}else{
			System.out.println("then can get friend list");
			//onlineFriend.friendList = user.getFriendOnline();
			onlineFriend.friendList = user.getFriendOnline();
			System.out.println(user.getAccount()+"&&&&&&&&&&&&&&&&");
		//	for(UserInfo i :user.getFriendOnline())
			//	i.display();
		//	for(UserInfo i :onlineFriend.friendList)
			//	i.display();
		}
		synchronized(msgBox){
			msgBox.add(msg);
		}
	}
	private void delPrise(Message msg) {
		// TODO Auto-generated method stub
		Message.Del_Praise delPrise = (Message.Del_Praise)(msg.data);
		Message reply = new Message();
		reply.id = msg.id;
		reply.reply = true;
		reply.type = Message.DEL_PRAISE;
		Message.ReplyData data = reply.new ReplyData();
		reply.data = data;
		if(user == null || !user.isOn() || !UserManager.identityVerify(delPrise.uid, delPrise.psw) || !dictManager.DelPraise(delPrise.uid, delPrise.word, delPrise.source)){
			data.success = false;
		}else{
			data.success = true;
		}
		synchronized(msgBox){
			msgBox.add(reply);
		}
	}
	private void addPrise(Message msg) {
		// TODO Auto-generated method stub
		Message.Add_Praise addPrise = (Message.Add_Praise)(msg.data);
		Message reply = new Message();
		reply.id = msg.id;
		reply.reply = true;
		reply.type = Message.ADD_PRAISE;
		Message.ReplyData data = reply.new ReplyData();
		reply.data = data;
		if(user == null || !user.isOn() || !UserManager.identityVerify(addPrise.uid, addPrise.psw) || !dictManager.AddPraise(addPrise.uid, addPrise.word, addPrise.source)){
			data.success = false;
		}else{
			data.success = true;
		}
		synchronized(msgBox){
			msgBox.add(reply);
		}
	}
	private void delFriend(Message msg) {
		// TODO Auto-generated method stub
		Message.DelFriend delFriend = (Message.DelFriend)(msg.data);
		Message reply = new Message();
		reply.id = msg.id;
		reply.reply = true;
		reply.type = Message.DEL_FRIEND;
		Message.ReplyData data = reply.new ReplyData();
		
		reply.data = data;
		if(user == null || !user.isOn() || !UserManager.identityVerify(delFriend.uid, delFriend.psw) || !user.delFriend(delFriend.friend_uid)){
			data.success = false;
		}else{
			data.success = true;
		}
		synchronized(msgBox){
			msgBox.add(reply);
		}
	}
	private void addFriend(Message msg) {
		// TODO Auto-generated method stub
		Message.AddFriend addFriend = (Message.AddFriend)(msg.data);
		Message reply = new Message();
		reply.id = msg.id;
		reply.reply = true;
		reply.type = Message.ADD_FRIEND;
		Message.ReplyData data = reply.new ReplyData();
		
		reply.data = data;
		if(user == null || !user.isOn() || !UserManager.identityVerify(addFriend.uid, addFriend.psw) 
				|| !user.addFriend(addFriend.friend_uid)){
			data.success = false;
		}else{
			data.success = true;
		}
		synchronized(msgBox){
			msgBox.add(reply);
		}
	}
	private void sendCard(Message msg) {
		// TODO Auto-generated method stub
		System.out.println("Enter Send card^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		Message.Send_Card sendCard = (Message.Send_Card)(msg.data);
		Message ipReply = new Message();
		ipReply.id = msg.id;
		ipReply.reply = true;
		ipReply.type = Message.IP_DATA;
		Message.IpData ipdata = ipReply.new IpData();
		ipReply.data = ipdata;
		if(user == null || !user.isOn() || !UserManager.identityVerify(sendCard.uid, sendCard.psw)
				|| !dictManager.saveWordCard(sendCard.uid, sendCard.targetuid, sendCard.card)){
			ipdata.Ip = null;
			System.out.println("card user is null?"+(user == null));
			if(user != null)
				System.out.println("card user is on?" + user.isOn());
			System.out.println("identity ?"  +UserManager.identityVerify(sendCard.uid, sendCard.psw) );
			
			System.out.println("card dont save!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}else{//认证成功
			System.out.println("target id:" + sendCard.targetuid);
			//ipdata.Ip = uManager.getOtherOnlineUser(sendCard.targetuid).IpAddr;
			UserManager.getOtherOnlineUser(sendCard.targetuid).display();
			
			ipdata.Ip = UserManager.getOtherOnlineUser(sendCard.targetuid).IpAddr;
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ipdata.Ip);
		}
					
		synchronized(msgBox){
			msgBox.add(ipReply);
		}
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
		if(user == null || !user.isOn() || !UserManager.identityVerify(sendMsg.uid, sendMsg.psw)){
			data.success = false;//if the user id offline or the psw is wrong then dont forword
		}
		else{
			synchronized(msgMap){//获得目标信箱地址
				targetMsgBox = (ArrayList<Message>)msgMap.get(sendMsg.targetuid);
			}
			if(targetMsgBox != null){//在线用户可以发送
				msg.id = idCreater ++;
				msg.reply = false;
				msg.type = Message.SEND_MESSAGE;
				sendMsg.psw = null;//for security the psw shouldnt be forword
				synchronized(targetMsgBox){
					targetMsgBox.add(msg);
				}
				data.success = true;
			}else{//不能发送
				data.success = false;
			}
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
		//System.out.println(user.isOn());
		if(user != null && user.isOn()){
			dictManager.hasPraised(user.getAccount() ,ans.word);
		}
	
		
		
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
		if(UserManager.changePassword(chPsw.uid, chPsw.oldpsw, chPsw.newpsw)){//check 
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
		if(UserManager.identityVerify(logout.uid, logout.psw)){
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
		UserInfo newusr = new UserInfo(null, rMsgData.uid, rMsgData.psw);
		newusr.setByteImage(rMsgData.head);
		newusr.setEmail(rMsgData.email);
		newusr.setSex(rMsgData.sex);
		//System.out.println("register msg: uid-"+rMsgData.uid+" psw-"+rMsgData.psw+" email-"+rMsgData.email+ " sex-"+rMsgData.sex);
		if(UserManager.createUser(newusr)){
			//user = new User(rMsgData.uid, rMsgData.psw);
			
			System.out.println("server register successful");
			//synchronized(msgMap){
			//	msgMap.put(user.getAccount(), msgBox);//add msBox to map then others can send msg
			//}
			redata.success = true;
		}else{//create faile
			System.out.println("server register failed");
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
		boolean login = user.login(userSocket.getInetAddress().getHostAddress(), userSocket.getPort());
		if(login){//login successful
			synchronized(msgMap){
				msgMap.put(user.getAccount(), msgBox);
			}
			user.getFriendOnline();
			data.success = true;
			
		}else{// login faild
			data.success = false;
			
		}
		synchronized(msgBox){
			msgBox.add(replyMsg);
		}
		
	}
	public static void setMsgMap(Map msgMap){
		ServeTask.msgMap = msgMap;
	}
	
	/*public static void setUserManager(UserManager um){
		ServeTask.uManager = um;
	}*/
	public static void setDictionaryManager(DictionaryManager dictm){
		ServeTask.dictManager = dictm;
	}
}
