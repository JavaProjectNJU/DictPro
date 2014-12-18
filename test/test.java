import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import net.Message.Message;
import net.Message.Message.Send_Message;


public class test {
	public static void main(String[] args){
		try {
			ServerSocket s = new ServerSocket(8888);
			int index = 0;
			Socket socket = s.accept();
			socket.setTcpNoDelay(true);
			System.out.println("when create :ip1:"+socket.getInetAddress().getHostAddress()+"port:"+socket.getPort());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			while(true){
				
				Message msg = new Message();
				msg.id = 10;
				msg.type = Message.SEND_MESSAGE;
				msg.reply = false;
				Message.Send_Message data = msg.new Send_Message();
				msg.data = data;
				
				data.uid = "jam";
				data.dialoge = "hello test" + index;
				index ++;
			
				out.writeObject(msg);
				out.flush();
				Thread.sleep(2000);;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
