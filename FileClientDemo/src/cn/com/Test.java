package cn.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Test {

	/**
	 * @param args
	 * 陈十三
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		BufferedReader brInput = null;
		PrintWriter out = null;
		Socket s = null;
		
		brInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入网站域名");
		try {
			String fileName = brInput.readLine();
			
			s = new Socket(InetAddress.getByName(fileName),80);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(),true);
			out.println("GET /index.html HTTP/1.0\r\nHOST: "+fileName+"\r\n\r\n");
			String line = null;
//			line = br.readLine();
//			String[] resposneTemp = line.split(" ");
//			if(resposneTemp[1].equals("200")) {
				while((line = br.readLine()) != null) {
					System.out.println(line);
				}
//			}else if(resposneTemp[1].equals("404")) {
//				System.out.println("<html><body>404 页面丢失</body></html>");
//				
//			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(brInput != null) {
				try {
					brInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out != null) {
				out.close();
			}
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
