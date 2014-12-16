package System;

import java.awt.List;
import java.net.Socket;
import java.util.ArrayList;

import net.Message.Message;

public class SendTask extends Task implements Runnable{
	private Socket socket;
	ArrayList<Message> msgBox;
	public SendTask(Socket userSocket, ArrayList<Message> userMsgBox){
		socket = userSocket;
		this.msgBox = userMsgBox;
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
	
	public void run(){
		
	}

}
