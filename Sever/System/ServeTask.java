package System;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServeTask extends Task implements Runnable{
	private Socket userSocket;
	private InputStream fromClient;
	private OutputStream toClient;
	public ServeTask(Socket socket){
		userSocket = socket;
		try {
			fromClient = socket.getInputStream();
			toClient = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public Task[] taskCore() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean needExecuteImmediate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}
	public void run() {
		DataInputStream fromClientData = new DataInputStream(fromClient);
		DataOutputStream toClientData = new DataOutputStream(toClient);
		if(toClientData == null || fromClientData == null)
			return;
		while(true){
			try {
				int msgId = fromClientData.readInt();
				boolean direct = fromClientData.readBoolean();
				int type = fromClientData.readInt();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
