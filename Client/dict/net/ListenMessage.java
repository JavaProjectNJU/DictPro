package dict.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

import System.UserInfo;
import net.Message.Message;

public class ListenMessage implements Runnable{
	private static Map requestMap;
	private Socket socket;
	private ObjectInputStream objIn;
	private JButton msgButton;
	private JList msgList;
	private ArrayList<Message> msgs;
	public ListenMessage(Map requestMap, final Socket socket, JButton msgButton, JList msgList, ArrayList msgs){
		System.out.println("ctrating listen msg~~");
		this.requestMap = requestMap;
		this.socket = socket;
		this.msgButton = msgButton;
		this.msgList = msgList;
		this.msgs = msgs;
		System.out.println("ctrated listen msg~~");
	}
	@Override
public void run() {

		
		while(true){
			Message msg = null;
			if(socket == null || socket.isClosed())//
				break;
			try{
				System.out.println("listenning msg");

				if(objIn == null)
					objIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));	
				
				msg = (Message)(objIn.readObject());
				//char eof = fromClient.readChar();//将文件结束符读出来；
				//fromClient.close();
				
				System.out.println("receive a msg");
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				objIn = null;//重置输入流
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			if(msg == null)
				continue;
			if(msg.reply){// themsg is a reply
				System.out.println("reply to:"+ msg.id);
				synchronized(requestMap){
					if(requestMap.containsKey(msg.id))//有相应的请求才会加入，否则说明该返回已经超时，丢弃
						requestMap.put(msg.id, msg);
				}
				
			}else{//then it is a request 
				System.out.println("a msg from b: id="+msg.id);
				//msgList.add(name, comp);
				synchronized(msgs){
					msgs.add(msg);
				}
				DefaultListModel<Message> dlist = new DefaultListModel<Message>();
				dlist.removeAllElements();
				synchronized(msgs){
					for(Message m:msgs){
						dlist.addElement(m);
					}
				}
				msgList.setModel(dlist);//更新列表中的元素
				synchronized(msgs){
					msgButton.setText(msgs.size() +" Message");
				}
			}
		
		}
	
	}
	
}
