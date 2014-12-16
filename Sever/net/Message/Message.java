package net.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import System.UserInfo;
import word.UnionWord;
import word.Word;

public class Message implements Serializable{
	public int id;
	public boolean reply;
	public int type;
	public static final int LOGIN = 1;
	public static final int LOGOUT = 2;
	public static final int REGISTER = 3;
	public static final int CHANGE_PSW = 4;
	public static final int ADD_FRIEND = 5;
	public static final int DEL_FRIEND = 6;
	public static final int UPDATE_FRIEND_ONLINE = 7;
	public static final int SEARCH = 8;
	public static final int ADD_PRAISE = 9;
	public static final int DEL_PRAISE = 10;
	public static final int SEND_CARD = 11;
	public static final int SEND_MESSAGE = 12;
	public MsgData data;
	






	/*
		static final int LOGIN = 1;
		static final int LOGOUT = 2;
		static final int REGISTER = 3;
		static final int CHANGE_PSW = 4;
		static final int ADD_FRIEND = 5;
		static final int DEL_FRIEND = 6;
		static final int UPDATE_FRIEND_ONLINE = 7;
		static final int SEARCH = 8;
		static final int ADD_PRAISE = 9;
		static final int DEL_PRAISE = 10;
		static final int SEND_CARD = 11;
		static final int SEND_MESSAGE = 12;
	*/
	public abstract class MsgData implements Serializable{
	}

	public class LoginMsg extends MsgData implements Serializable{
		public String uid;
		public String psw;
	}

	public class LogoutMsg extends MsgData implements Serializable{
		public String uid;
		public String psw;
	}

	public class RegsiterMsg extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String email;
		public char sex;
	}

	public class ChangePsw extends MsgData implements Serializable{
		public String uid;
		public String oldpsw;
		public String newpsw;
	}

	public class AddFriend extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String friend_uid;
	}

	public class DelFriend extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String friend_uid;
	}

	public class OnlineFriend extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public ArrayList<UserInfo> friendList;
	}

	public class Serach extends MsgData implements Serializable{
		public Word word;
	}

	public class Add_Praise extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public Word word;
	}

	public class Del_Praise extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public UnionWord word;
	}

	public class Send_Card extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String targetuid;
		public byte[] card;
	}

	public class Send_Message extends MsgData implements Serializable{
		public String uid;
		public String psw;
		public String targetuid;
		public String dialoge;
	}
	public class ReplyData extends MsgData implements Serializable{
		public boolean success;
	}

	public byte[] imageToBytes(BufferedImage image){
		byte[] buf = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			buf = baos.toByteArray();
			
		}catch(IOException e){
			
		}
		return buf;
	}
	
	public BufferedImage bytesToImage(byte[] buf){
		BufferedImage image = null;
		try{
			ByteArrayInputStream baos = new ByteArrayInputStream(buf);
			image = ImageIO.read(baos);
		}catch(IOException e){
			
		}
		return image;
	}
}
