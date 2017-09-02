package module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import main.Database;
import record.PostRecord;

/**
 * PMPL 게시판 (업무보고 or 지시 ) 관리하는 클래스 
 * @author yeji
 *
 */
public class PMPLBoard {
	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	public PMPLBoard()throws Exception{		
		// Connection 연결 객체 얻어오기
		con = Database.getConnection();		
	}

	/**
	 * 게시물의 승인 여부를 업데이트하는 메소드 
	 * @exception 게시물의 상태를 업데이트 할때 생기는 모든 예외를 던짐
	 * @author yeji
	 */
	public void setPostState(String stat, int postNum) throws Exception{
		//sql 
		String sql = "UPDATE test_post SET post_stat = ? WHERE post_num = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, stat);
		ps.setInt(2, postNum);

		int result = ps.executeUpdate();
		ps.close();		
	}

	/**
	 * 업무게시판 글 불러오는 메소드 
	 * @param p_num 프로젝트 번호
	 * @return 업무게시판 내용
	 * @throws Exception 코드게시판을 불러올때 발생하는 모든 예외
	 */
	public ArrayList<ArrayList<String>> loadBoardContent(int p_num) throws Exception{		
		ArrayList<ArrayList<String>> ARLT = new ArrayList<ArrayList<String>>();
		ArrayList<String> ARL;

		// 3. sql 문장만들기
		// 프로젝트 넘버와 게시물종류(코드) 가 필요
		//"게시번호", "상태", "제목", "작성자", "작성시간"
		String sql = "SELECT POST_NUM, FILE_NAME, EMP_ID, POST_TIME, POST_STAT, POST_TITLE "
				+ "   FROM TEST_POST "
				+ "   WHERE (POST_TYPE = '업무지시' or POST_TYPE = '업무보고') AND P_NUM = ? ";

		//  sql 전송객체 얻어오기(PreparedStatement)		
		ps = con.prepareStatement(sql);
		ps.setInt(1, p_num);

		// sql 전송(executeUpdate()이용)
		ResultSet rs = ps.executeQuery();

		while(rs.next()){

			ARL = null;
			ARL = new ArrayList<String>();

			ARL.add(String.valueOf(rs.getInt("POST_NUM")));
			ARL.add(rs.getString("POST_STAT"));
			ARL.add(rs.getString("POST_TITLE"));
			ARL.add(rs.getString("EMP_ID"));
			ARL.add(rs.getString("POST_TIME"));

			ARLT.add(ARL);
		}
		// 닫기 
		ps.close();

		return ARLT;
	}

	/**
	 * 게시물의 글을 가져오는 메소드 
	 * @param post_num
	 * @return
	 * @throws Exception
	 * @author yeji
	 */
	public String getContent(int post_num) throws Exception{
		Clob clob = null;	

		String ret = "";

		StringBuffer sb = new StringBuffer();
		String str = "";

		//파일 읽어오기 		
		String sql = "SELECT post_file FROM test_post WHERE post_num = ?";
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
		System.out.println("게시물 @");
		System.out.println(ret);
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
		String sql = "INSERT INTO test_post VALUES(post_num_seq.nextval, ?, ?, ?, SYSDATE, ?, ?, ?,?)";

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
		String sql = "UPDATE test_post "
				+ "SET POST_TITLE = ?, post_file = ? , post_time = sysdate, post_stat = ? "
				+ 	" WHERE POST_NUM = ?";

		// sql 전송객체(PreparedStatement)
		ps = con.prepareStatement(sql);

		ps.setString(1, postRec.getPostTitle());
		ps.setClob(2, clob);
		ps.setString(3, postRec.getPostStat());
		ps.setInt(4, post_num);	

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
		String sql = "DELETE FROM test_post WHERE post_num = ?";
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
