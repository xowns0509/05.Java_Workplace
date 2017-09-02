package Baduk;
//=============================================================
//	서버에서 클라이언트로 전송할  데이타를 캡슐화한 클래스
//=============================================================

public class BadukServerProtocol implements java.io.Serializable
{
	// 서버에서 클라이언트로 전송시 상태값
	
	public static final int		Chatting		 = 200; // 채팅메세지
	public static final int		CHANGE_MEMBER_ID = 100; 	// 멤버 아이디 변경시
	public static final int		SET_BADUK_GAMMER = 300; // 게임자 지정시

	public static final int		START_GAME		 =  10; // 게임 시작시
	public static final int		END_GAME		 =  20; // 게임 종료시
	public static final int		SET_BADUK_ROCK   =  30; // 바둑알이 놓여졌을때


	Object 						data;
	int                          state;
	public BadukServerProtocol(){
		
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