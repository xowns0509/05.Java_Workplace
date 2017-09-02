package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.Database;

public class PMModel {
	
	// Connection con = null;
	Connection con = Database.getConnection();
	
	PreparedStatement ps = null;
	
	public PMModel() throws Exception{
		
//		// 1. 드라이버 로딩
//		Class.forName(driver);
//		
//		// 2. Connection 객체 얻어오기
//		con = DriverManager.getConnection(url, user, pass);
//		System.out.println("VideoModel() 연결 성공");
			
	}
	
	public ArrayList searchBorad(){
		
		System.out.println("searchBorad() 실행");
		
		// 3. sql 문장 만들기 ( "게시번호","글제목","작성자","작성시간","승인여부" )
		String sql = "SELECT post_num, post_title , emp_id, post_time, post_stat" 
					+ " FROM post";
					
		System.out.println(sql);
		
		ArrayList list = new ArrayList();
		
		try{
		// 디비에서 데이터 읽어서 저장
		// 4. 전송객체 얻어오기 (Statement)
		Statement stmt = con.createStatement();
		
		// 5. 전송( executeQuery() )
		ResultSet rs = stmt.executeQuery(sql);
		
		System.out.println("stmt.executeQuery(sql) 실행됨");
		
		// 6. 결과처리
		while( rs.next() ){
			// ArrayList에 한 행(레코드)이 각각의 정보를 저장
			// 그 ArrayList를 list(ArrayList) 저장

			ArrayList temp = new ArrayList();

			String post_num = rs.getString("post_num");
			String post_title = rs.getString("post_title");
			String emp_id = rs.getString("emp_id");
			String post_time = rs.getString("post_time");
			String post_stat = rs.getString("post_stat");

			temp.add(post_num);
			temp.add(post_title);
			temp.add(emp_id);
			temp.add(post_time);
			temp.add(post_stat);

			list.add(temp);
		}
		
		stmt.close();
		
		}catch(SQLException ex){
			System.out.println("연결 및 실행 실패 : " + ex.getMessage() );
		}catch(Exception ex){
			System.out.println("그 외 예외 : " + ex.getMessage());
		}
		
		return list;
		
	}

}
