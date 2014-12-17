package dict.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenCard implements Runnable{
	private ServerSocket listenSocket;
	private static int localPort = 8005;
	public ListenCard(){
		try {
			listenSocket = new ServerSocket(localPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				Socket newCard;
			
				newCard = listenSocket.accept();
			
				ReceiveCard reveive = new ReceiveCard(newCard);
				Thread t = new Thread(reveive);
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
