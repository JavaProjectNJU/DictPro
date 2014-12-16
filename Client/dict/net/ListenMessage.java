package dict.net;

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
		this.requestMap = requestMap;
		this.socket = socket;
		try {
			objIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				if(socket != null)
					socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				if(objIn == null)
					return;
		
				Message msg = (Message)(objIn.readObject());
				if(msg.reply = true){//when listened the reply send to the request
					requestMap.put(msg.id, msg);
				}else{//is a request ,recevice a msg
					
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
