package extraPlus;

import java.sql.Connection;

public class DBCon {
	
	private static Connection con;
	//0005. Connection con 마저 static이 되야 하지.
	//0006. con도 함부로 사용하지 못하도록 private지정해주고
	

	private DBCon()	//0001. 다른 클래스에서 DBcon dbcon = new DBcon(); 을 못하도록 해줌. private
	{
		//1. 드라이버 로딩
		
		//2. 연결객체 얻어오기
		
	}
	
	public static Connection getConnection(){
		//0002. dbcon.getConnection(); 이 안되므로
		//0003. 클래스명 접근으로 가능하게 끔 해야 해. 그래서 static을 붙임
		//0004. 그러면 객체 생성보다 먼저 메모리에 올라가므로
		
		
		if(con == null){
			new DBCon();
		}
		return con;	//0007. 이제 다른 클래스에서 기술할 시 Connection con = null
					//con = DBcon.getConnection(); --이렇게 작성
	}

}
