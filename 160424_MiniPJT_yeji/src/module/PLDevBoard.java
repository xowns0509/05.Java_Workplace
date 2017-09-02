package module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import main.Database;
import record.PostRecord;

/**
 * 
 * @author taejun
 *
 */
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


	/**
	 * 코드게시판 글 불러오는 메소드 
	 * @param p_num 프로젝트 번호
	 * @return 코드게시판 내용
	 * @throws Exception 코드게시판을 불러올때 발생하는 모든 예외
	 */
	public ArrayList<ArrayList<String>> loadBoardContent(int p_num) throws Exception{		
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
	public String getContent(int post_num) throws Exception{
		Clob clob = null;	
	
		String ret = "";
	
		StringBuffer sb = new StringBuffer();
		String str = "";
		
		//파일 읽어오기 		
		String sql = "SELECT post_file FROM post WHERE post_num = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, post_num);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			clob = rs.getClob("post_file");
		}
		BufferedReader br= new BufferedReader(clob.getCharacterStream());
		
		while((str = br.readLine() ) != null){
			sb.append(str + "\n");
		}
		ret = sb.toString();	
		return ret;
	}
	
	
	/**
	 * 글 올리는 메소드
	 * @param postRec 게시물 레코드
	 * @return 상태값
	 * @throws Exception 글 등록시 발생하는 모든 예외 
	 * @author taejun, yeji
	 */
	public int submitBoardContent(PostRecord postRec) throws Exception{
		//파일 가져오기 
		File file = new File(postRec.getFilePath());
		
		//FileReader 로 파일 읽기
		FileReader fr = new FileReader(file);		
		
		//clob 만들기 
		Clob clob = con.createClob();
		
		clob.setString(1, postRec.getPostContent());
		
		
		// 3. sql 문장 만들기
		String sql = "INSERT INTO post VALUES(post_num_seq.nextval, ?, ?, ?, SYSDATE, ?, ?, ?,?)";

		// 4. sql 전송객체(PreparedStatement)
		ps = con.prepareStatement(sql);
		//순서는 SQL테이블 순서

		ps.setInt(1, postRec.getpNum()); 	//프로젝트 번호
		ps.setString(2, postRec.getFileName()); //파일명
		ps.setString(3, postRec.getEmpId());	//사원ID
		ps.setString(4, postRec.getPostType());  //게시물종류
		ps.setString(5, postRec.getPostStat());  //상태
		ps.setString(6, postRec.getPostTitle()); //게시물제목
		ps.setClob(7, clob);		

		// sql 전송		
		int saveBrdrs = ps.executeUpdate();

		// 결과처리	
		ps.close();
		fr.close();

		return saveBrdrs;
	}
	
	/**
	 * 게시물 수정
	 * 변경되는 사항: post_file, post_title, post_time
	 * (post_title은 변경될수도있고 안될수도있지만 일관성을 위해
	 * 항상 변경)
	 * @param postRec 수정하고자 하는 게시물의 정보
	 * @return 수정한 행의 갯수
	 * @throws Exception 글을 수정하면서 일어나는 모든 예외 
	 * @author yeji
	 */
	public int editBoardContent(PostRecord postRec, int post_num) throws Exception{	

		//	POST_NUM	NUMBER				//게시물 번호
		//	P_NUM	NUMBER					//프로젝트 번호
		//	FILE_NAME	VARCHAR2(500 BYTE)	//파일명
		//	EMP_ID	VARCHAR2(20 BYTE)		//사원ID
		//	POST_TIME	DATE				//작성시간
		//	POST_TYPE	VARCHAR2(30 BYTE)	//게시물 종류
		//	POST_STAT	VARCHAR2(20 BYTE)	//상태
		//	POST_TITLE	VARCHAR2(100 BYTE)	//게시물 제목

		int udtResult = 0;//~행의 행 숫자 초기화. 1개가 되야함!
		//클랍 파일 만들기 
		Clob clob = con.createClob();
		
		//내용 설정
		clob.setString(1, postRec.getPostContent());
				
		// sql 문장만들기
		String sql = "UPDATE post SET POST_TITLE = ?, post_file = ? , post_time = sysdate "
				+ 	" WHERE POST_NUM = ?";

		// sql 전송객체(PreparedStatement)
		ps = con.prepareStatement(sql);
		
		ps.setString(1, postRec.getPostTitle());
		ps.setClob(2, clob);
		ps.setInt(3, post_num);	
		
		// sql 전송		
		udtResult = ps.executeUpdate();

		//  결과처리		
		System.out.println( "editBoardContent 결과 출력: " + udtResult);//콘솔출력용
		ps.close();

		return udtResult;
	}

	//글 삭제
	public void deleteBoardContent(int postNum) throws Exception{

		int dltResult = 0;//~행의 행 숫자 초기화.

		// sql 문장만들기
		String sql = "DELETE FROM post WHERE post_num = ?";
		System.out.println(sql);

		// sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		ps.setInt(1, postNum);  //게시물번호

		// sql 전송(executeUpdate()이용)
		dltResult = ps.executeUpdate();

		//닫기 
		ps.close();

		//return dltResult;
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