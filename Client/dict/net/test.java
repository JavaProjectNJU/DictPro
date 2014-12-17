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
		System.out.println("start test~~~");
		for(int i = 0; i < 5; i ++){//try to connect
			try {
				socket = new Socket("localhost", 8000);
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
		System.out.println("create link~~~~");
		ListenMessage msgs = new ListenMessage(requests, socket);
		System.out.println("create listenner~~~~");
		ListenCard card = new ListenCard();
		System.out.println("create cara listenner~~~~");
		Thread tmsg = new Thread(msgs);
		System.out.println("create msg recevice thread~~~~");
		Thread tcard = new Thread(card);
		System.out.println("create card recevice thread~~~~");
		tmsg.start();
		System.out.println("start msg recevice thread~~~~");
		tcard.start();
		System.out.println("start card recevice thread~~~~");

		System.out.println(link.register("jam", "guoruijun", "cruijunguo@gmail.com", 'm'));
		link.setUid("jam");
		link.setPsw("guoruijun");
		System.out.println(link.login());
		System.out.println(link.addFriend("zhangruiyi"));
		System.out.println(link.serach("jam"));
	}
}
