package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.dao.Customer;
import model.dao.Video;

public class VideoModel {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	String user = "scott";
	String pass = "tiger";
	
	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;
	
	public VideoModel() throws Exception{
		
		// 1. 드라이버 로딩
		Class.forName(driver);
		
		// 2. Connection 연결 객체 얻어오기
		con = DriverManager.getConnection(url, user, pass);
			
	}
	
	public void insertVideo(Video dao, int count) throws Exception{
		
		// 3. sql 문장 만들기
		String sql = "Insert into VIDEO VALUES(video_no_seq.nextval, ?, ?, ?, ?, ?)";
		
		// 4. sql 전송객체(PreparedStatement)
		ps = con.prepareStatement(sql);
		//순서는 SQL테이블 순서
		ps.setString(1, dao.getGenre());//비디오 번호 제외한체 죄다 들어가는 놈.
		ps.setString(2, dao.getVideoName());
		ps.setString(3, dao.getDirector());
		ps.setString(4, dao.getActor());
		ps.setString(5, dao.getExp());
		
		// 5. sql 전송		
		for(int i = 0; i < count; i++){

			ps.executeUpdate();
		}
		
		// 6. 결과처리(PreparedStatement�� �ݱ�)
		JOptionPane.showMessageDialog(null, "비디오가 "+ count+ "수 만큼 입력 완료 되었습니다.");  // 수정
		ps.close();
	}
	
	public void updateVideo(Video dao) throws Exception{

		int udtResult = 0;//~행의 행 숫자 초기화.

		// 3. sql 문장만들기
		String sql = "UPDATE VIDEO SET GENRE=?, TITLE=?, DIRECTOR=?, ACTOR=?, DESCRIPTION=? WHERE VIDEO_NO=?";
		System.out.println(sql);

		// 4. sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);

		//순서는 SQL테이블 순서
		ps.setString(1, dao.getGenre());//비디오 번호 제외한체 죄다 들어가는 놈.
		ps.setString(2, dao.getVideoName());
		ps.setString(3, dao.getDirector());
		ps.setString(4, dao.getActor());
		ps.setString(5, dao.getExp());
		ps.setInt(6, dao.getVideoNo());

		// 5. sql 전송(executeUpdate()이용)
		udtResult = ps.executeUpdate();

		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		//System.out.println(udtResult + " 행이 수정되었어!.");//콘솔출력용
		JOptionPane.showMessageDialog(null, "비디오가 "+ udtResult+ "수 만큼 수정되었습니다.");
		ps.close();

		return;
	}
	
	public void deleteVideo(int videoNum) throws Exception{

		int udtResult = 0;//~행의 행 숫자 초기화.

		// 3. sql 문장만들기
		String sql = "DELETE FROM VIDEO WHERE VIDEO_NO = ?";
		System.out.println(sql);

		// 4. sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		//순서는 SQL테이블 순서
		ps.setInt(1, videoNum);

		// 5. sql 전송(executeUpdate()이용)
		udtResult = ps.executeUpdate();

		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		//System.out.println(udtResult + " 행이 수정되었어!.");//콘솔출력용
		JOptionPane.showMessageDialog(null, "비디오가 "+ udtResult+ "수 만큼 삭제되었습니다.");
		ps.close();
		
		return;
	}
	
	public ArrayList searchVideo(int idx, String word) throws Exception{

		ArrayList daoARLT = new ArrayList();
		ArrayList daoARL;
		
		Video dao;
		
		// 3. sql 문장만들기
		//String sql = "SELECT VIDEO_NO, TITLE, GENRE, DIRECTOR, ACTOR FROM VIDEO WHERE DIRECTOR like '%?%'";
		//Select VIDEO_NO, TITLE, GENRE, DIRECTOR, ACTOR
		//from VIDEO
		//where DIRECTOR like '%봉%';
				
		//Select VIDEO_NO, TITLE, GENRE, DIRECTOR, ACTOR
		//from VIDEO
		//where DIRECTOR like '%222%';
		//여기서는 프리페어스테이트먼트 못씀. ?못씀.
		//검색할 쿼리문의 열 명에 ''이 붙어버려
		
		String [] colName={"TITLE","DIRECTOR"};
		String sql = "SELECT VIDEO_NO, TITLE, GENRE, DIRECTOR, ACTOR  "
				+ "   FROM VIDEO"
				+ "   WHERE " + colName [idx]+"  like  '%"+word+"%'";
		//개행 할 때는 공백을 넉넉히 해서 주자. 컬럼명과 예약어가 붙어서 제대로 작동 안할 수도
		System.out.println(sql);
		
		// 4. sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		
		// 5. sql 전송(executeUpdate()이용)
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			
			daoARL = null;
			daoARL = new ArrayList();
		
			daoARL.add(rs.getInt("VIDEO_NO"));
			daoARL.add(rs.getString("TITLE"));
			daoARL.add(rs.getString("GENRE"));
			daoARL.add(rs.getString("DIRECTOR"));
			daoARL.add(rs.getString("ACTOR"));
			daoARLT.add(daoARL);
			
//			System.out.println(dao.getVideoNo());
//			System.out.println(dao.getVideoName());
//			System.out.println(dao.getGenre());
//			System.out.println(dao.getDirector());
//			System.out.println(dao.getActor());
		}
		System.out.println("이제 리스트 출력!!");
		
/*		for(int i=0; i < daoARL.size(); i++){
			
//			if(returnedName.equals(ctmrlist.get(i).getCustName())){

			System.out.println(daoARL.get(i).getVideoNo());//tfCustTel
			System.out.println(daoARL.get(i).getVideoName());//tfCustName
			System.out.println(daoARL.get(i).getGenre());//tfCustTelAid
			System.out.println(daoARL.get(i).getDirector());//tfCustAddr
			System.out.println(daoARL.get(i).getActor());//tfCustEmail

		}*/
		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		ps.close();
		
		return daoARLT;
		
	}
	
	public String searchExp(int vnum) throws Exception{
		
		String getDetail = new String();
		// 3. sql 문장만들기
		String sql = "SELECT DESCRIPTION FROM VIDEO WHERE VIDEO_NO = ?";
		
		// 4.
		ps = con.prepareStatement(sql);
		ps.setInt(1, vnum);
		
		// 5.
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			getDetail = rs.getString("DESCRIPTION");
		}
		
		// 6.
		System.out.println(vnum+ "에 관한 비디오 정보를 아래와 같이 제대로 갖고왔습니다. \n"+ getDetail);
		
		ps.close();
		return getDetail;
	}

}

//VIDEO_NO    NOT NULL NUMBER        
//GENRE                VARCHAR2(100) 
//TITLE                VARCHAR2(100) 
//DIRECTOR             VARCHAR2(100) 
//ACTOR                VARCHAR2(100) 
//DESCRIPTION          VARCHAR2(100) 