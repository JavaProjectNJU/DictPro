package dict.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;

import net.Message.Message;

public class ListenMessage implements Runnable{
	private static Map requestMap;
	private Socket socket;
	private ObjectInputStream objIn;
	public ListenMessage(Map requestMap, Socket socket){
		System.out.println("ctrating listen msg~~");
		this.requestMap = requestMap;
		this.socket = socket;
		
		
		System.out.println("ctrated listen msg~~");
	}
	@Override
public void run() {

		
		while(true){
			Message msg = null;
			if(socket == null || socket.isClosed())//
				break;
			try{
				System.out.println("listenning msg");

				if(objIn == null)
					objIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));	
				
				msg = (Message)(objIn.readObject());
				//char eof = fromClient.readChar();//将文件结束符读出来；
				//fromClient.close();
				
				System.out.println("receive a msg");
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				objIn = null;//重置输入流
				e.printStackTrace();
			}
			if(msg == null)
				continue;
			if(msg.reply){// themsg is a reply
				System.out.println("reply to:"+ msg.id);
				synchronized(requestMap){
					requestMap.put(msg.id, msg);
				}
			}else{//then it is a request 
				System.out.println("a msg from b: id="+msg.id);
				
			}
		
		}
	
	}
	
}
