package module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Database;
import record.PostRecord;

public class BugReportBoard {

	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	public BugReportBoard() throws Exception{
		
		// Connection 연결 객체 얻어오기
		con = Database.getConnection();
		System.out.println("PLDevBoard db연결!");
			
	}
	
//	POST	//테이블 명
//	POST_NUM	NUMBER				//게시물 번호
//	P_NUM	NUMBER					//프로젝트 번호
//	FILE_NAME	VARCHAR2(500 BYTE)	//파일명
//	EMP_ID	VARCHAR2(20 BYTE)		//사원ID
//	POST_TIME	DATE				//작성시간
//	POST_TYPE	VARCHAR2(30 BYTE)	//게시물 종류
//	POST_STAT	VARCHAR2(20 BYTE)	//상태
//	POST_TITLE	VARCHAR2(100 BYTE)	//게시물 제목

//	POST_COMMENT					//테이블 명
//	CMT_NUM	NUMBER					//댓글번호
//	POST_NUM	NUMBER				//게시물 번호
//	EMP_ID	VARCHAR2(20 BYTE)		//사원ID
//	FILE_NAME	VARCHAR2(500 BYTE)	//파일명
//	CMT_TIME	DATE				//댓글 작성시간

//	private int postNum;		//게시물 번호
//	private int pNum;			//프로젝트 번호
//	private String fileName;	//파일명
//	private String empId;		//사원ID
//	private String postTime;	//작성시간
//	private String postType;	//게시물종류
//	private String postStat;	//상태
//	private String postTitle;	//게시물제목
	
	//코드게시판 글 불러오는 메소드
	public ArrayList<ArrayList<String>> loadBugContent(int p_num) throws Exception{
		//p_num 추가 인자로 받기

		ArrayList<ArrayList<String>> ARLT = new ArrayList<ArrayList<String>>();
		ArrayList<String> ARL;

		// 3. sql 문장만들기
		// 프로젝트 넘버와 게시물종류(코드) 가 필요
		//"게시번호", "상태", "제목", "작성자", "작성시간"
		String sql = "SELECT POST_NUM, FILE_NAME, EMP_ID, POST_TIME, POST_STAT, POST_TITLE "
				+ "   FROM POST "
				+ "   WHERE POST_TYPE = '버그' AND P_NUM = ?";

		//위 sql은 테스트용, 실제 불러와야 할 sql.
		//select ~ from post
		//where post_type = '버그' and P_NUM = 1;
		System.out.println("loadBugContent sql문장실행: "+sql);

		// 4. sql 전송객체 얻어오기(PreparedStatement)	
		System.out.println("loadBugContent p_num 출력: " + p_num);
		ps = con.prepareStatement(sql);
		ps.setInt(1, p_num);

		// 5. sql 전송(executeUpdate()이용)
		ResultSet rs = ps.executeQuery();
		

		while(rs.next()){

			ARL = null;
			ARL = new ArrayList<String>();

			ARL.add(String.valueOf(rs.getInt("POST_NUM")));
			ARL.add(rs.getString("POST_STAT"));
			ARL.add(rs.getString("POST_TITLE"));
			//ARL.add(rs.getString("FILE_NAME"));
			ARL.add(rs.getString("EMP_ID"));
			ARL.add(rs.getString("POST_TIME"));
			

			ARLT.add(ARL);

		}

		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		ps.close();

		return ARLT;
	}
	
	//글 올리는 메소드
	public void saveBoardContent(PostRecord postRec) throws Exception{
		
		// 3. sql 문장 만들기
		String sql = "Insert into POST VALUES(seq_tmp_post_POST_NUM, seq_tmp_post_P_NUM, ?, ?, SYSDATE, ?, ?, ?)";

		// 4. sql 전송객체(PreparedStatement)
		ps = con.prepareStatement(sql);
		//순서는 SQL테이블 순서

		ps.setString(1, postRec.getFileName());
		ps.setString(2, postRec.getEmpId());
		ps.setString(3, postRec.getPostType());
		ps.setString(4, postRec.getPostStat());
		ps.setString(5, postRec.getPostTitle());
		
		// 5. sql 전송		
		int saveBrdrs = ps.executeUpdate();
		
		// 6. 결과처리
		System.out.println( "saveBrdrs 출력: " + saveBrdrs);
		ps.close();
	}
}
