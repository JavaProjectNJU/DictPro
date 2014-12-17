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
		this.requestMap = requestMap;
		this.socket = socket;
		System.out.println("ctrating listen msg~~");
		
		System.out.println("ctrated listen msg~~");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			objIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			System.out.println("ctrated object input stream~~");
			while(true){
				System.out.println("running listen msg~~");
				if(objIn == null)
					return;
		
				Message msg = (Message)(objIn.readObject());
				if(msg.reply = true){//when listened the reply send to the request
					System.out.print("receive a reply" + msg.id);
					requestMap.put(msg.id, msg);
				}else{//is a request ,recevice a msg
					Message.Send_Message receviceMsg = (Message.Send_Message)(msg.data);
					System.out.println("from " + receviceMsg.uid +": " + receviceMsg.dialoge);
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
