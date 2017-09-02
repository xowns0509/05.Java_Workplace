package basic;

import java.io.*;
import java.net.*;

public class SimpleServer {
	
	public final static int PORT = 5000;	// PortNumer : 1024 ~
	
	public static void main( String args[] ) {
		
		ServerSocket 		server = null;
		DataInputStream 	in = null;
		DataOutputStream out = null;
		Socket clientSocket = null;
		
		try{//네트워크는 100% 예외처리 필수. 자체가 자바 외의 것이랑 통신이니까.
			// 1. 서버 소켓 생성
			server = new ServerSocket( PORT );
			System.out.println("SimpleServer started..");
		
			// 2. 클라이언트 접속시 소켓 생성
			// 3. 소켓의 입출력 스트림 얻기
			clientSocket = server.accept();//지가 스스로 클라이언트 접속때까지 기다려. 그리고 클라가 접속하면 소켓 그놈이랑 데이터를 주고 받을 소켓을 만들어줌.
			in = new DataInputStream( clientSocket.getInputStream());
			out = new DataOutputStream( clientSocket.getOutputStream());
		
			// 4. 데이터 전송
				String line = in.readUTF();
				System.out.println("we received : " + line );
				if( line.compareTo("엄태준안녕") == 0 ) {
					out.writeUTF("엄태준 서버왈 반갑습니다." );	
				} else {
					out.writeUTF("클라의 인삿말이 아냐.");
				}
		
			// 5. 소켓닫기
			in.close();//인풋 아웃풋 스트림 먼저 닫은 후
			out.close();
			clientSocket.close();//그다음에 소켓을 닫는 거임. 인풋아웃풋이 소켓보다 밖에 있잖아.
		} catch ( Exception ex ) {
			System.out.println( ex.getMessage() );	
		}
	}	
}