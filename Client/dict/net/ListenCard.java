package dict.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenCard implements Runnable{
	private ServerSocket listenSocket;
	private static int localPort = 8005;
	public ListenCard(){
		try {
			System.out.println("creating listencard~~~");
			listenSocket = new ServerSocket(localPort);
			System.out.println("created listencard~~~");
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
				System.out.println("running listencard listencard~~~");
				Socket newCard = listenSocket.accept();
				newCard.setTcpNoDelay(true);
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
