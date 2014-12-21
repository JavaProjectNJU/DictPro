package dict.net;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JList;

import System.UserInfo;
import net.Message.Message;

public class Client {
	public static LinkToServer getLink(JList list, JButton msgButton, JList msgList, ArrayList<Message> msgBox){
		BufferedInputStream bis;
		String ip = null;
		int server_port = 8888;
		int card_port = 8005;
		try {
			bis = new BufferedInputStream(
					new FileInputStream("Client\\client.conf.properties"));
			Properties properties = new Properties();
			properties.load(bis);
			
			ip = properties.getProperty("server_ip", "127.0.0.1");
			System.out.println("the ip is :~~~~~~"+ip);
			server_port = Integer.valueOf(properties.getProperty("server_port", "8888"));
			card_port = Integer.valueOf(properties.getProperty("card_port", "8005"));
			 URI uri = new java.net.URI(properties.getProperty("welcome_path"));
			 java.awt.Desktop.getDesktop().browse(uri);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
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
				System.out.println("hahahah"+ip + " "+server_port);
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
		
		ListenMessage msgs = new ListenMessage(requests, socket, msgButton, msgList, msgBox);
		
		ListenCard card = new ListenCard(card_port, msgButton, msgList, msgBox);
	
		RefreshList refresh = new RefreshList(link, list);
		Thread tmsg = new Thread(msgs);
		
		
		Thread tcard = new Thread(card);
		Thread tre = new  Thread(refresh);
		
		//test(link);
		tcard.start();
		tmsg.start();
		tre.start();
		
		
		return link;
	}
	public static void test(LinkToServer link){
		
		System.out.println("login is"+link.login("jam", "guoruijun"));
		while(true){
			ArrayList<UserInfo> friend = link.getOnlineFriend();
			System.out.println("jam get the test::::::::");
			System.out.println("friendlist:" +(friend!=null));
			for(UserInfo i:friend)
				i.display();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
