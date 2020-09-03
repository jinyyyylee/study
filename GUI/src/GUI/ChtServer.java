package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ChtServer {
	ServerSocket serverSocket;
	PrintWriter writer;
	ArrayList<PrintWriter> UserOutputStream;
	
	public void go() {
		UserOutputStream = new ArrayList<PrintWriter>();
		try {
			serverSocket = new ServerSocket(6004);
			System.out.println("서버 구성완료. 클라이언트로부터 접속을 기다리는중");
			while (true) {
				Socket userSocket = serverSocket.accept();
				System.out.println("클라이언트가 접속함");
				writer = new PrintWriter(userSocket.getOutputStream());
				UserOutputStream.add(writer);
				
				Thread t = new Thread(new UserHandle(userSocket));
				t.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void tellEveryone(String message) {
		Iterator<PrintWriter> it = UserOutputStream.iterator();
		while(it.hasNext()) {
			writer = (PrintWriter) it.next();
			writer.println(message);
			writer.flush();
		}
	}
	class UserHandle implements Runnable{
		Socket userSocket;
		BufferedReader reader;
		
		UserHandle(Socket socket){
			try {
				userSocket = socket;
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(isr);
				
			}catch(IOException e) {
				
			}
		}
		public void run() {
			String message;
			try {
				while((message = reader.readLine()) != null) {
					tellEveryone(message);
				}
			}catch(IOException e) {
				
			}
		}
	}
	public static void main(String ar[]) {
		new ChtServer().go();
	}
}
