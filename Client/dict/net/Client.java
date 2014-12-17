package dict.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import net.Message.Message;

public class Client {
	public static LinkToServer getLink(){
		Map requests = new HashMap<Integer, Message >();
		Socket socket = null;
		System.out.println("start test~~~");
		for(int i = 0; i < 5; i ++){//try to connect
			try {
				socket = new Socket("192.168.253.1", 8000);
				//socket = new Socket("localhost", 8000);
				socket.setTcpNoDelay(true);
				System.out.println("create socket~~~");
				if(socket != null)
					break;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		LinkToServer link = new LinkToServer(requests);
		
		ListenMessage msgs = new ListenMessage(requests, socket);
		
		ListenCard card = new ListenCard();
	
		Thread tmsg = new Thread(msgs);
		
		
		Thread tcard = new Thread(card);
		tmsg.start();
		tcard.start();
	
		return link;
	}
}
