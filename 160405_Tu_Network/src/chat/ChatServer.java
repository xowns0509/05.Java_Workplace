package chat;

import java.io.*;
import java.util.*;
import java.net.*;

public class ChatServer implements Runnable {
	ArrayList vc = new ArrayList();

	public void run() {
		ServerSocket ss = null;
		try{
			ss = new ServerSocket(4444);//중복으로 구동하거나 중복으로 구동하지 않았는데도 콘솔창에 already... 하면 다른 프로그램이 이 포트를 사용하는 경우임. JVM이 포트를 못얻은 상태.
		}catch( Exception e ) {
			System.out.println(e);
		}

		while(true) {
			try{
				Socket s = ss.accept();
				System.out.println("Client 가 접속시도 :" + s );
				ChatService cs = new ChatService(s);
				cs.start();
				vc.add(cs);

			} catch( Exception e ) { }
		}
	}  // run ends

	public static void main( String [] arg ) {
		ChatServer cs = new ChatServer();
		new Thread(cs).start();
		
	}

	class ChatService extends Thread {
		String myname = "quest";
		BufferedReader in;
		OutputStream out;
		ChatService( Socket s ) {
			try{
				in = new BufferedReader( new InputStreamReader(s.getInputStream()));
				out = s.getOutputStream();
			}catch( Exception e ) { }
		}// 생성자 종료

		public void run() {
			while(true) {
				try{
					String msg = in.readLine();//read라인은 역슬래시를 만나면 그 전까지 다 읽어들임.
					// /enter 바보\n 중 '/enter 바보'까지
					if( msg == null || msg.equals("")/*공백왔을 때 처리 안함*/) return;
					StringTokenizer st = new StringTokenizer(msg);//공백시 자동 쪼개기
					if( st.countTokens() > 1 ) {
						String temp = st.nextToken();

						if( temp.equalsIgnoreCase("/name" )) {
							temp = st.nextToken();
							putMessageAll(myname + "님의 이름이 " + temp + "으로 바뀌었습니다.");
							myname = temp;
							changeList();
							continue;
						}

						else if(temp.equals("/enter")){//여기 temp
							temp = st.nextToken();
							myname = temp;
							putMessageAll(myname + "님이 입장했어");
							changeList();
							continue;
						}
						
						else if(temp.equals("/exit")){
							temp = st.nextToken();
							myname = temp;
							putMessageAll(myname + "님이 퇴장했어");
							vc.remove(this);
							

//							for(int i = 0;  i <vc.size() ; i++){
//								if(myname.equals(vc.get(i).myname)){
//									vc.remove(i);
//								}
//							}

							changeList();
							//나중에 리스트처리
							continue;
						}
					}
					putMessageAll( myname + ">" + msg );
					
				}catch( Exception ex ) { return; }

			}
		}// run ends

		// 서버에 클라이언트가 접속시 사용자명들을 전송할 함수
		// [예] "/member 사용자1 사용자2 사용자3\n"
		void changeList(){
			String msg = "/member ";
			for(int i = 0;  i <vc.size() ; i++){
				ChatService cs = (ChatService)vc.get(i);
				msg += cs.myname + " ";
			}
			putMessageAll(msg + "\n");
		}

		void putMessageAll( String msg ) {
			for( int i =0 ; i<vc.size() ; i++ ) {
				ChatService cs = ( ChatService ) vc.get(i);

				try {
					cs.putMessage(msg);
					System.out.println(msg);
				}catch( Exception e ) {
					vc.remove(i--);
				}
			}
		} // putMessageAll ends

		void putMessage( String msg )
				throws Exception {
			out.write( (msg+"\r\n").getBytes() );
		}

	} // ChatService class ends


}// ChatServer class ends
