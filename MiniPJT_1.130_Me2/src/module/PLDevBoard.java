package module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Database;
import record.PostRecord;

public class PLDevBoard {

	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	public PLDevBoard() throws Exception{

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
	public ArrayList<ArrayList<String>> loadBoardContent(int p_num) throws Exception{
		//p_num 추가 인자로 받기	

		ArrayList<ArrayList<String>> ARLT = new ArrayList<ArrayList<String>>();
		ArrayList<String> ARL;

		// 3. sql 문장만들기
		// 프로젝트 넘버와 게시물종류(코드) 가 필요
		//"게시번호", "상태", "제목", "작성자", "작성시간"
		String sql = "SELECT POST_NUM, FILE_NAME, EMP_ID, POST_TIME, POST_STAT, POST_TITLE "
				+ "   FROM POST "
				+ "   WHERE POST_TYPE = '코드' AND P_NUM = ? ";

		//위 sql은 테스트용, 실제 불러와야 할 sql.
		//select ~ from post
		//where post_type = '코드' and P_NUM = 1;
		System.out.println("loadBoardContent p_num 출력: " + p_num);
		System.out.println("loadBoardContent sql문장실행: "+sql);

		// 4. sql 전송객체 얻어오기(PreparedStatement)		
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
	public int submitBoardContent(PostRecord postRec) throws Exception{

		// 3. sql 문장 만들기
		String sql = "Insert into POST VALUES(seq_tmp_post_POST_NUM.nextval, 1, ?, ?, SYSDATE, ?, ?, ?)";

		// 4. sql 전송객체(PreparedStatement)
		ps = con.prepareStatement(sql);
		//순서는 SQL테이블 순서
		
		System.out.println(postRec.getFileName()); //파일명
		System.out.println(postRec.getEmpId());	//사원ID
		System.out.println(postRec.getPostType());
		System.out.println(postRec.getPostStat());
		System.out.println(postRec.getPostTitle());

		ps.setString(1, postRec.getFileName()); //파일명
		ps.setString(2, postRec.getEmpId());	//사원ID
		ps.setString(3, postRec.getPostType());  //게시물종류
		ps.setString(4, postRec.getPostStat());  //상태
		ps.setString(5, postRec.getPostTitle()); //게시물제목

		// 5. sql 전송		
		int saveBrdrs = ps.executeUpdate();

		// 6. 결과처리
		System.out.println( "saveBrdrs 출력: " + saveBrdrs);
		ps.close();
		
		return saveBrdrs;
	}

	//수정필요. 글 수정하는 메소드
	public int editBoardContent(PostRecord postRec) throws Exception{	
		
		//	POST_NUM	NUMBER				//게시물 번호
		//	P_NUM	NUMBER					//프로젝트 번호
		//	FILE_NAME	VARCHAR2(500 BYTE)	//파일명
		//	EMP_ID	VARCHAR2(20 BYTE)		//사원ID
		//	POST_TIME	DATE				//작성시간
		//	POST_TYPE	VARCHAR2(30 BYTE)	//게시물 종류
		//	POST_STAT	VARCHAR2(20 BYTE)	//상태
		//	POST_TITLE	VARCHAR2(100 BYTE)	//게시물 제목

		int udtResult = 0;//~행의 행 숫자 초기화.
		
		// 3. sql 문장만들기
		String sql = "UPDATE POST SET FILE_NAME = ?, POST_TITLE = ? "
				+ 	" WHERE POST_NUM = ? AND EMP_ID = ? ";

		System.out.println(sql);

		// 4. sql 전송객체(PreparedStatement)
		ps = con.prepareStatement(sql);
		//순서는 SQL테이블 순서

		System.out.println(postRec.getFileName()); //파일명
		System.out.println(postRec.getEmpId());	//사원ID
		System.out.println(postRec.getPostType());
		System.out.println(postRec.getPostStat());
		System.out.println(postRec.getPostTitle());

		//파일명, 게시물제목만 수정 할 수 있도록 함. 게시물번호와 사원ID는 조건주기용.
		ps.setString(1, postRec.getFileName());		//파일명
		ps.setString(2, postRec.getPostTitle());	//게시물제목
		ps.setInt(3, postRec.getpNum());			//게시물번호
		ps.setString(4, postRec.getEmpId());		//사원ID

		// 5. sql 전송		
		udtResult = ps.executeUpdate();

		// 6. 결과처리
		//JOptionPane.showMessageDialog(null, "비디오가 "+ udtResult+ "수 만큼 수정되었습니다.");
		System.out.println( "editBoardContent 결과 출력: " + udtResult);//콘솔출력용
		ps.close();

		return udtResult;
	}

	//글 삭제
	public int deleteBoardContent(int postNum, String emp_id) throws Exception{

		int dltResult = 0;//~행의 행 숫자 초기화.

		// 3. sql 문장만들기
		String sql = "DELETE FROM POST WHERE POST_NUM = ? AND EMP_ID = ? ";
		System.out.println(sql);

		// 4. sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		//순서는 SQL테이블 순서
		ps.setInt(1, postNum);  //게시물번호
		ps.setString(2, emp_id);	//사원ID

		// 5. sql 전송(executeUpdate()이용)
		dltResult = ps.executeUpdate();

		// 6. 닫기 (PreparedStatement 만 닫아줘. Connection 닫으면 안되.)
		//JOptionPane.showMessageDialog(null, "비디오가 "+ dltResult+ "수 만큼 삭제되었습니다.");
		System.out.println( "deleteBoardContent 결과 출력: " + dltResult);//콘솔출력용
		ps.close();

		return dltResult;
	}
}	

//
//}


//System.out.println("이제 리스트 출력!!");
//
///*		for(int i=0; i < daoARL.size(); i++){
//
////if(returnedName.equals(ctmrlist.get(i).getCustName())){
//
//System.out.println(daoARL.get(i).getVideoNo());//tfCustTel
//System.out.println(daoARL.get(i).getVideoName());//tfCustName
//System.out.println(daoARL.get(i).getGenre());//tfCustTelAid
//System.out.println(daoARL.get(i).getDirector());//tfCustAddr
//System.out.println(daoARL.get(i).getActor());//tfCustEmail
//
//}*/


//부분 검색하는 방법. 중간에 봉 or 222 들어갈 때
//String sql = "SELECT VIDEO_NO, TITLE, GENRE, DIRECTOR, ACTOR FROM VIDEO WHERE DIRECTOR like '%?%'";
//Select VIDEO_NO, TITLE, GENRE, DIRECTOR, ACTOR
//from VIDEO
//where DIRECTOR like '%봉%';

//Select VIDEO_NO, TITLE, GENRE, DIRECTOR, ACTOR
//from VIDEO
//where DIRECTOR like '%222%';
//여기서는 프리페어스테이트먼트 못씀. ?못씀.
//검색할 쿼리문의 열 명에 ''이 붙어버려

//String [] colName={"TITLE","DIRECTOR"};
//String sql = "SELECT VIDEO_NO, TITLE, GENRE, DIRECTOR, ACTOR  "
//		+ "   FROM VIDEO"
//		+ "   WHERE " + colName [idx]+"  like  '%"+word+"%'";
//개행 할 때는 공백을 넉넉히 해서 주자. 컬럼명과 예약어가 붙어서 제대로 작동 안할 수도