package net.Message;

import java.io.Serializable;
import java.util.ArrayList;

import com.sun.prism.Image;

import word.Word;

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

class LoginMsg extends MsgData implements Serializable{
	String uid;
	String psw;
}

class LogoutMsg extends MsgData implements Serializable{
	String uid;
	String psw;
}

class RegsiterMsg extends MsgData implements Serializable{
	String uid;
	String psw;
	String email;
	char sex;
}

class ChangePsw extends MsgData implements Serializable{
	String uid;
	String oldpsw;
	String newpsw;
}

class AddFriend extends MsgData implements Serializable{
	String uid;
	String psw;
	String friend_uid;
}

class DelFriend extends MsgData implements Serializable{
	String uid;
	String psw;
	String friend_uid;
}

class OnlineFriend extends MsgData implements Serializable{
	String uid;
	String psw;
	ArrayList<String> friendList;
}

class Serach extends MsgData implements Serializable{
	Word word;
}

class Add_Praise extends MsgData implements Serializable{
	String uid;
	String psw;
	Word word;
}

class Del_Praise extends MsgData implements Serializable{
	String uid;
	String psw;
	Word word;
}

class Send_Card extends MsgData implements Serializable{
	String uid;
	String psw;
	String targetuid;
	Image card;
}

class Send_Message extends MsgData implements Serializable{
	String uid;
	String psw;
	String targetuid;
	String dialoge;
}
class ReplyData extends MsgData implements Serializable{
	boolean success;
}
