package cn.com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class FileServer {
	private ServerSocket ss;
	private Socket s;
	
	public FileServer() {
		try {
			ss = new ServerSocket(9001);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("服务器已启动");
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				s = ss.accept();
				new SessionThread(s).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
