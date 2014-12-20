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

import javax.swing.JList;

import net.Message.Message;

public class Client {
	public static LinkToServer getLink(JList list){
		BufferedInputStream bis;
		String ip = null;
		int server_port = 8888;
		int card_port = 8005;
		try {
			bis = new BufferedInputStream(
					new FileInputStream("Client/client.conf.properties"));
			Properties properties = new Properties();
			properties.load(bis);
			ip = properties.getProperty("server_ip", "127.0.0.1");
			System.out.println(ip);
			server_port = Integer.valueOf(properties.getProperty("server_port", "8888"));
			card_port = Integer.valueOf(properties.getProperty("card_port", "8005"));
		}catch(FileNotFoundException e){
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map requests = new HashMap<Integer, Message >();
		Socket socket = null;
		System.out.println("start test~~~");
		for(int i = 0; i < 5; i ++){//try to connect
			try {
				socket = new Socket(ip, server_port);
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
		LinkToServer link = new LinkToServer(requests, socket);
		
		ListenMessage msgs = new ListenMessage(requests, socket);
		
		ListenCard card = new ListenCard(card_port);
	
		RefreshList refresh = new RefreshList(link, list);
		Thread tmsg = new Thread(msgs);
		
		
		Thread tcard = new Thread(card);
		Thread tre = new  Thread(refresh);
		tmsg.start();
		tcard.start();
		tre.start();
	
		return link;
	}
}
