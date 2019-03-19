package cn.com.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SessionThread extends Thread {
	private Socket s;
	private BufferedReader br;
	private PrintWriter out;
	private BufferedReader brFile;
	
	public SessionThread(Socket s) {
		this.s = s;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		String[] requestTemp = null;
		try {
			String request = br.readLine();
//			判断响应报文是否符合http协议
			requestTemp = request.split(" ");
			if(requestTemp.length != 3) {
				return;
			}
			if(!(requestTemp[0].equalsIgnoreCase("get") || requestTemp[0].equalsIgnoreCase("post"))) {
				return;
			}
			if(!requestTemp[1].startsWith("/")) {
				return;
			}
			if(!(requestTemp[2].equalsIgnoreCase("http/1.0") || requestTemp[2].equalsIgnoreCase("HTTP/1.1"))) {
				return;
			}
			//...
			String fileName = requestTemp[1].substring(1);
//			获取请求者的信息
			String ip = s.getInetAddress().getHostAddress();
			System.out.println(ip+"请求了"+fileName);
			File f = new File(fileName);
			if(f.exists()) {
				out.println("HTTP/1.0 200 OK\r\nContent-type: text/html;\r\n\r\n");
				brFile = new BufferedReader(new FileReader(f));
				String line = null;
				while((line = brFile.readLine()) != null) {
					out.println(line);
				}
			}else {
				out.println("HTTP/1.0 404 OK\r\nContent-type: text/html;\r\n\r\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("HTTP/1.0 500 OK\r\nContent-type: text/html;\r\n\r\n");
			e.printStackTrace();
		} finally {
//			关闭资源
			if(brFile != null) {
				try {
					brFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out != null) {
				out.close();
			}
			if(requestTemp[2].equalsIgnoreCase("http/1.0")) {
				if(s != null) {
					try {
						s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		} 
	}
}
