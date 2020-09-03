package Ehco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		try {
			Socket sock = new Socket("127.0.0.1",5003);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			OutputStream out = sock.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			InputStream in = sock.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line =  null;
			
			while ((line = keyboard.readLine())!=null) {
				if(line.contentEquals("quit")) {
					break;
				}
				pw.println(line);
				pw.flush();
				String echo = br.readLine();
				System.out.println("서버로부터 돌아온 문자열" + echo);
				
			}
			pw.close();
			br.close();
			sock.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
