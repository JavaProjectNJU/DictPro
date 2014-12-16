package dict.net;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import net.Message.Message;
import System.UserInfo;
import word.UnionWord;

public class LinkToServer {
	private static String ip = "localhost";
	private static int port = 8000;
	private static int idCreater = 0;
	private Socket socket;
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	private String uid;
	private String psw;
	private ObjectOutputStream objOut;
	private static Map requestMap;
	public LinkToServer(Map requestMap){
		try {
			socket = new Socket(ip, port);
			objOut = new ObjectOutputStream(socket.getOutputStream());
			this.requestMap = requestMap;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public LinkToServer(Map requestMap, String uid, String psw){
		this(requestMap);
		this.uid =uid;
		this.psw = psw;
	}
	public void setUid(String uid){
		this.uid = uid;
	}
	public void setPsw(String psw){
		this.psw = psw;
	}
	public boolean login(){
		Message loginMessage = new Message();
		loginMessage.id = idCreater ++;
		loginMessage.reply = false;
		loginMessage.type = Message.LOGIN;
		Message.LoginMsg data = loginMessage.new LoginMsg();
		loginMessage.data = data;
		data.uid = uid;
		data.psw = psw;
		
		return false;
	}
	public boolean logout(){
		return false;
	}
	public boolean register(String uid, String psw, String email, String sex){
		return false;
	}
	public boolean changePsw(String newpsw){
		return false;
	}
	public UnionWord serach(String word){
		return null;
		
	}
	public boolean addPrise(String word, int type){
		return false;
	}
	public boolean delPrise(String word, int type){
		return false;
	}
	public boolean addFriend(String friendUid){
		return false;
	}
	public boolean delFriend(String friendUid){
		return false;
	}
	public boolean sendText(String text){
		return false;
	}
	public boolean sendCard(BufferedImage image, String uid){
		return false;
	}
	public ArrayList<UserInfo> getOnlineFriend(){
		return null;
	}
	public UserInfo getDetail(){
		return null;
	}
	
}
