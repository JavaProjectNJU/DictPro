package dict.net;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.Message.Message;

public class test {
	public static void main(String[] args){
		Map requests = new HashMap<Integer, Message >();
		Socket socket = null;
		System.out.println("start test~~~");
		String ip = null;
		int server_port = 8888;
		int card_port = 8005;
		BufferedInputStream bis;
		try {
			bis = new BufferedInputStream(
					new FileInputStream("Client/client.conf.properties"));
			Properties properties = new Properties();
			properties.load(bis);
			ip = properties.getProperty("server_ip", "127.0.0.1");
			server_port = Integer.valueOf(properties.getProperty("server_port", "8888"));
			card_port = Integer.valueOf(properties.getProperty("card_port", "8005"));
		}catch(FileNotFoundException e){
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < 5; i ++){//try to connect
			try {
				//socket = new Socket("192.168.253.1", 8000);
				socket = new Socket(ip, server_port);
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
		LinkToServer link = new LinkToServer(requests, socket);
		System.out.println("create link~~~~");
		ListenMessage msgs = new ListenMessage(requests, socket);
		System.out.println("create listenner~~~~");
		ListenCard card = new ListenCard(card_port);
		System.out.println("create cara listenner~~~~");
		Thread tmsg = new Thread(msgs);
		tmsg.start();
		System.out.println("create msg recevice thread~~~~");
		Thread tcard = new Thread(card);
		System.out.println("create card recevice thread~~~~");
		
		System.out.println("start msg recevice thread~~~~");
		tcard.start();
		System.out.println("start card recevice thread~~~~");

		System.out.println(link.register("jam", "guoruijun", "cruijunguo@gmail.com", 'm'));
		System.out.println("IP:"+socket.getLocalAddress().getHostAddress()+"post:"+ socket.getLocalPort());
		link.setUid("jam");
		link.setPsw("guoruijun");
		System.out.println(link.login());
		System.out.println(link.addFriend("zhangruiyi"));
		System.out.println(link.serach("jam").getWordBaidu().getExplain().get(0));
	}
}
