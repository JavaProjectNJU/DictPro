package System;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import DataBase.DictionaryManager;
import DataBase.UserManager;
import net.Message.*;


public class Sever {
	ThreadPool pool;
	static int port;
	Map msgMap;
	UserManager uManager; 
	DictionaryManager dictm;
	public Sever(){
		
		
		msgMap = new HashMap<String,List>();
		uManager = new UserManager();
		dictm = new DictionaryManager();
		ServeTask.setMsgMap(msgMap);
		ServeTask.setUserManager(uManager);
		ServeTask.setDictionaryManager(dictm);
		//读取配置
		BufferedInputStream bis;
		try {
			bis = new BufferedInputStream(
					new FileInputStream("Sever\\server.conf.properties"));
			Properties properties = new Properties();
			properties.load(bis);
			port = Integer.valueOf(properties.getProperty("server_port", "8888"));
			int workerNum = Integer.valueOf(properties.getProperty("server_threadworker", "10"));
			pool = new ThreadPool(workerNum);
			bis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("配置文件不存在，请检查");
			System.exit(-1);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("配置文件格式异常，请检查");
			System.exit(-1);
			e.printStackTrace();
		}
		
		
	}
	public void start(){
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			
			while(true){
				try {
					System.out.println("server is running");
					Socket socket = serverSocket.accept();
					System.out.println("when create :ip1:"+socket.getInetAddress().getHostAddress()+"port:"+socket.getPort());
					socket.setTcpNoDelay(true);
					//System.out.println("when create :ip2:"+socket.getInetAddress().getHostAddress()+"port:"+socket.getPort());
					System.out.println("server accept a socket");
					ArrayList<Message> msgBox = new ArrayList<Message>();
					ServeTask newTask = new ServeTask(socket, msgBox);
				
					SendTask sendTask = new SendTask(socket, msgBox);
					pool.addTask(newTask);
					pool.addTask(sendTask);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
