package System;

import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataBase.DictionaryManager;
import DataBase.UserManager;
import net.Message.*;


public class Sever {
	ThreadPool pool;
	ServerSocket serverSocket;
	static final int port = 8000;
	Map msgMap;
	UserManager uManager; 
	DictionaryManager dictm;
	public Sever(){
		pool = new ThreadPool();
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		msgMap = new HashMap<String,List>();
		uManager = new UserManager();
		dictm = new DictionaryManager();
		ServeTask.setMsgMap(msgMap);
		ServeTask.setUserManager(uManager);
		ServeTask.setDictionaryManager(dictm);
	}
	public void start(){
		
		while(true){
			try {
				System.out.println("server is running");
				Socket socket = serverSocket.accept();
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
		
	}
	
}
