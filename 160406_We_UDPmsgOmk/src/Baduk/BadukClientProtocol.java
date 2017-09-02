package Baduk;
//=============================================================
//	클라이언트에서 서버로 전송할  데이타를 캡슐화한 클래스
//=============================================================

public class BadukClientProtocol implements java.io.Serializable
{
	//---------------------------------------------
	// 클라이언트에서 서버로 전송시 상태값
	
    public static final int			Chatting    =  200;	// 챗팅메세지 
	public static final int			SEND_ID		=  300; // 처음 접속시 아이디
	public static final int			EXIT		=  400; // 접속을 끊을때

	public static final int			REQUEST_GAME =  0;
	public static final int			VIEW_GAME    =  1;


    public static final int			SET_BADUK_ROCK    =  11;

	Object 						 data;
	int                          state;

	public BadukClientProtocol(){
	}
	public void setData(Object obj){
		data = obj;		
	}
	public void setState(int s){
		state = s;
	}
	public Object getData(){
		return data;
	}
	public int getState(){
		return state;
	}

}