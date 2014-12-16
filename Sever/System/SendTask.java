package System;

import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import DataBase.DictionaryManager;
import DataBase.UserManager;
import net.Message.Message;

public class SendTask extends Task implements Runnable{
	private Socket socket;
	private ArrayList<Message> msgBox;
	private ObjectOutputStream toTarget;
	
	public SendTask(Socket userSocket, ArrayList<Message> userMsgBox){
		socket = userSocket;
		this.msgBox = userMsgBox;
		try {
			toTarget = new ObjectOutputStream(socket.getOutputStream());
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
	
	public void run(){
		try {
			while(true){
				if(!msgBox.isEmpty()){
				
					synchronized(msgBox){
						toTarget.writeObject(msgBox.get(0));
						msgBox.remove(0);
					}
					
				
				}else{
					Thread.sleep(500);
						
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(socket != null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
