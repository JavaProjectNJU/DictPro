package dict.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import net.Message.Message;

public class test {
	public static void main(String[] args){
		Map requests = new HashMap<Integer, Message >();
		Socket socket = null;
		
		for(int i = 0; i < 5; i ++){//try to connect
			try {
				socket = new Socket("localhost", 8000);
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
		System.out.println(link.register("jam", "guoruijun", "cruijunguo@gmail.com", 'm'));
		link.setUid("jam");
		link.setPsw("guoruijun");
		System.out.println(link.login());
		System.out.println(link.addFriend("zhangruiyi"));
		System.out.println(link.serach("jam"));
	}
}
