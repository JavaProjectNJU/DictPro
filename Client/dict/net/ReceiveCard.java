package dict.net;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

import net.Message.Message;

public class ReceiveCard implements Runnable{

	private Socket socket;
	public ReceiveCard(Socket socket){
		this.socket = socket;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectInputStream cardStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			Message msg = (Message)cardStream.readObject();
			Message.Send_Card cardData = (Message.Send_Card)(msg.data);
			BufferedImage image = Message.bytesToImage(cardData.card);
			System.out.println("recive a card");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
