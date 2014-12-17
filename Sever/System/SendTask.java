package System;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
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
		/*try {
			toTarget = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
		
		while(true){
			
			try {
				if(socket.isClosed())
					break;
				if(!msgBox.isEmpty()){
					System.out.println("server send task is sending");
					
					if(toTarget == null){
						toTarget =  new ObjectOutputStream(socket.getOutputStream());
					}
					synchronized(msgBox){
						//socket.setSendBufferSize(2);
						toTarget.writeObject(msgBox.get(0));
						toTarget.flush();
						
						//socket.getOutputStream().flush();
						System.out.println("the msg id:" + msgBox.get(0).id);
						msgBox.remove(0);
					}
					
					System.out.println("server send task is sended");
					//toTarget.close();
				
				}else{
					Thread.sleep(20);
						
				}
			} catch (IOException e) {
					// TODO Auto-generated catch block
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
