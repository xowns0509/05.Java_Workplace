package Baduk;
import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
  
public class BadukService extends Thread
  {
    String id = "guest";
	
	Socket				socServer ;
   
	ObjectInputStream   inStream;
	ObjectOutputStream	outStream;
    BadukServer 		server;

    //----------------------------------------------------------
	/*	생성자 함수
		1. 인자로 넘겨받은 객체를 멤버변수로 지정
		2. 소켓의 입출력 스트림 얻어오기
		3. 쓰레드 구현
	*/
    public BadukService(BadukServer server, Socket s ){
    	try{
    		this.server = server;
    		socServer = s;

    		outStream = new ObjectOutputStream(socServer.getOutputStream());
    		inStream = new ObjectInputStream(socServer.getInputStream());
    		start();

    	}catch(Exception e){}
    }

	//---------------------------------------------------------
	/*	쓰레드 루핑구문으로 클라이언트가 전송한 데이타 읽기
		데이타의 상태에 따라 각각의 구현 함수 호출
	*/
   	public void run()	{
		try{
			while(true)
			{
				BadukClientProtocol  obj = (BadukClientProtocol)inStream.readObject();
				int state = obj.getState();		//인스트림에서 상태를 get
				Object  data = obj.getData();	//인스트림에서 data를 get
				switch(state){
					// 클라이언트의 쳇팅 메세지를 받은 경우. data가 채팅이면
					case BadukClientProtocol.Chatting :	setChatting(data); break;

//--> 3				// 클라이언트 접속 후 아이디를 받은 경우. data가 아이디이면
					case BadukClientProtocol.SEND_ID :	setID((String)data); break;
					
					// 종료시. data가 종료이면
					case BadukClientProtocol.EXIT	:	removeID((String)data); break;
					
//--> 4				// 클라이언트의 게임요청을 받은 경우 data가 게임요청이면
					case BadukClientProtocol.REQUEST_GAME : requestGame(); break;

//--> 6				// 클라이언트가 바둑판에 클릭했을때의 바둑알정보를 받은 경우. data가 바둑알 정보이면
					case BadukClientProtocol.SET_BADUK_ROCK :  setBadukRock(data); break;
					
				}
			}
		}catch(Exception ex){
		}
	}//run

	//--------------------------------------------------------
	/*	클라이언트가 챗팅을 위한 데이타 객체를 전송했을때
		그 객체의 data 값을 각 클라이언트에 전송
	*/
	public void setChatting(Object data){
		String str = id + " >> "+ (String)data;
		server.setChatting(str);
	}

	//--------------------------------------------------------
	/*	소켓의 출력스트림으로 전송객체 전송
	*/
    public void sendInformation(Object obj){
    	try{
    		outStream.writeObject(obj);
    	}catch(IOException e){
    	}
    }


// --> 3
	//--------------------------------------------------------
	/*	읽어들인 아이디를 멤버변수 id로 지정하고 모든 클라이언트의 멤버 추가를 전송
	*/
    public void setID(String id){

    	this.id = id;
      	server.sendAddMember();		
		
    }

    public String getID(){
    	return id;
    }

	// 종료시
    public void removeID(String id){
      	server.sendRemoveMember(id);
    }

//--> 4
   public void requestGame(){
   		server.requestGame(this);
   }

//--> 6
	//-----------------------------------------------------
	/* 바둑알의 정보를 담은 데이타를 받아 각각의 행과 열값을 얻어 각각 클라이언트에 전송
	 */
   	public void setBadukRock(Object data){
		server.sendBadukRock(this, data);		
   	}

}	 