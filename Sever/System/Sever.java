package System;

import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataBase.UserManager;
import net.Message.*;


public class Sever {
	ThreadPool pool;
	ServerSocket serverSocket;
	static final int port = 8000;
	Map msgMap;
	UserManager uManager; 
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
		ServeTask.setMsgMap(msgMap);
		ServeTask.setUserManager(uManager);
	}
	public void start(){
		
		while(true){
			try {
				Socket socket = serverSocket.accept();
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
