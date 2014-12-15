package System;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Sever {
	ThreadPool pool;
	ServerSocket serverSocket;
	static final int port = 8000;
	
	public Sever(){
		pool = new ThreadPool();
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public void start(){
		while(true){
			try {
				Socket socket = serverSocket.accept();
				ServeTask newTask = new ServeTask(socket);
				pool.addTask(newTask);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}
