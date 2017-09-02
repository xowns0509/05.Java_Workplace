package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class RentModel {
	
	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;
	
	public RentModel() throws Exception{
		
		// Connection 연결 객체 얻어오기
		con = DBConnect_Genrtor.getConnection();

	}
	
	//대여동작 메소드
	public void rent(String tel, int vnum) throws Exception{
		
		//3. sql 문장
		System.out.println("입력받은 "+ tel);
		System.out.println("입력받은 "+ vnum);
		String sql = "INSERT INTO rent VALUES(RENT_NO_SEQ.nextval, ?, ?, '0100/01/01', sysdate, ?, ?)";
		System.out.println(sql);//열을 사용할 수 없습니다. 컬럼명 오타
		
		//4. sql 전송객체
		ps = con.prepareStatement(sql);
		
		ps.setInt(1, vnum);		//VIDEO_NO
		ps.setString(2, tel);	//TEL
		//ps.setString(2, vnum);	//DUE_DATE
		//ps.setString(3, vnum);	//CHECKOUT_DATE
		ps.setString(3, "미납");	//IS_RETURNED 반납미납
		ps.setInt(4, 10000);		//PRICE
		
		//5. 전송
		int insResult = ps.executeUpdate();
		System.out.println(insResult + "행이 수정되었어!.");
		
		ps.close();
		
	}
	
	public ArrayList<ArrayList> search() throws Exception{
		
		ArrayList<ArrayList> ARLT = new ArrayList<ArrayList>();
		ArrayList ARL;
		
		//3. sql 문장제작
		String sql = "select V.VIDEO_NO V_NO, V.TITLE TITLE, C.NAME NAME, C.TEL TEL, CHKDATE CDATE, R.is_returned rturned " +
					" from (select video_no, is_returned, TEL, CHECKOUT_DATE+3 CHKDATE from rent) r " +
					" join (select TEL, Name from customer) c on (c.TEL = r.TEL) " +
					" join (select VIDEO_NO, TITLE from video) v on (r.video_no = v.video_no) " +
					" where r.is_returned = '미납'";
		
		//4. sql 객체 획득
		Statement stmt = con.createStatement();
		
		//5. 전송
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			
			ARL = null;
			ARL = new ArrayList();
		
			ARL.add(rs.getInt("V_NO"));
			ARL.add(rs.getString("TITLE"));
			ARL.add(rs.getString("NAME"));
			ARL.add(rs.getString("TEL"));
			ARL.add(rs.getString("CDATE"));
			ARL.add(rs.getString("rturned"));
			ARLT.add(ARL);
			
//			System.out.println(dao.getVideoNo());
//			System.out.println(dao.getVideoName());
//			System.out.println(dao.getGenre());
//			System.out.println(dao.getDirector());
//			System.out.println(dao.getActor());
		}
		
		stmt.close();
		return ARLT;
	}

	
	// 반납을 처리하는 메소드
	public void returnVDO(int vnum) throws Exception{ //Vnum: 비디오번호
		
		//3. sql 문장
		System.out.println("입력받은 "+ vnum);
		
		String sql = "UPDATE rent SET IS_RETURNED = '반납' WHERE VIDEO_NO = ? and IS_RETURNED = '미납'";
		System.out.println(sql);
		//열을 사용할 수 없습니다. 컬럼명 오타
		
		//4. sql 전송객체
		ps = con.prepareStatement(sql);
		ps.setInt(1, vnum);		//VIDEO_NO
		
		//5. 전송
		int insResult = ps.executeUpdate();
		System.out.println(insResult + "행이 수정되었어!.");
		
		ps.close();
		
	}
//	RENT_NO       NOT NULL NUMBER       
//	VIDEO_NO      NOT NULL NUMBER       
//	TEL           NOT NULL VARCHAR2(20) 
//	DUE_DATE               DATE         
//	CHECKOUT_DATE          DATE         
//	IS_RETURNED            VARCHAR2(2)  
//	PRICE                  NUMBER    
}
