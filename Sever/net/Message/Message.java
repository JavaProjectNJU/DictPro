package net.Message;

import java.io.Serializable;

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
}
