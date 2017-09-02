package main;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 데이터 베이스 연결 하는 클래스
 * @author yeji
 *
 */
public class Database{
	//db 연결관련 변수 선언 
	private String driver = "oracle.jdbc.driver.OracleDriver";
//	private String url = "jdbc:oracle:thin:@192.168.0.138:1521:orcl";
	private String url = "jdbc:oracle:thin:@192.168.1.130:1521:orcl";
	private String user = "milestone";
	private String pw = "1234";

	private static Connection con;
	private static Database db = null;
	
	private Database() throws Exception{
		//1 드라이버 로딩 
		Class.forName(driver);
		//2 연결객체 얻어오기
		con = DriverManager.getConnection(url, user, pw);
		
	}
	
	/*
	 * 데이터베이스에 연결하는 커넥션 반환하는 메소드 
	 */
	public static Connection getConnection() throws Exception{
		if(db == null)
			new Database();
		return con;
	}
	
	//****************이 밑으로 메소드 추가 *****************************
}