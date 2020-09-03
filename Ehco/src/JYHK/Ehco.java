package JYHK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.InputStreamReader;

public class Ehco {
	public static void main(String[] arg) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(5003);
			System.out.println("*** 접속 대기중 ***");
			Socket sock = server.accept();
			InetAddress inetaddr=sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + "님이 접속하셨습니다");
			InputStream in = sock.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			OutputStream out = sock.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			
			String line = null;
			
			while((line=br.readLine()) != null){
				System.out.println("client부터받은 문자열 출력: + line");
				
				pw.println(line);
				pw.flush();
			}
			pw.close();
			br.close();
			sock.close();
		} catch (Exception e) {
		}
		if (!server.isClosed()) {
			try {
				server.close();
			} catch(IOException el) {
				
			}
		}
	}
}
