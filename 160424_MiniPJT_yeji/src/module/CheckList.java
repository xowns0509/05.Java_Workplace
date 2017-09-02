package module;
//체크리스트.java는 개인체크리스트 탭(PL, 개발자)에서 나오는 것과
//팀 전체 체크리스트를 담당.

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import main.Database;
import record.CheckListRecord;

public class CheckList {

	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	public CheckList() throws Exception{
		// Connection 연결 객체 얻어오기
		con = Database.getConnection();
		System.out.println("CheckList db연결!");
	}

	
	//	public class CheckListRecord {
	//		private int catNum;			// 카테고리번호	//chkcatenum - 항목테이블
	//		private int pNum; 			// 프로젝트번호	//pnum		 - 카테고리테이블
	//		private String catTitle;	// 카테고리명	//			-
	//		private int listNum; 		// 항목번호	//ctentNUm	- 항목테이블
	//		private String empId; 		// 사원ID		//empid		- 항목테이블
	//		private String content;		// 내용		//chkcontent- 항목테이블
	//		private String listStat;	// 상태		//chkstate	- 항목테이블

	//	tmp_chklist
	//	CAT_NUM   NOT NULL NUMBER         
	//	P_NUM              NUMBER         
	//	CAT_TITLE          VARCHAR2(4000) 
	//	LIST_NUM  NOT NULL NUMBER         
	//	EMP_ID             VARCHAR2(20)   
	//	CONTENT            VARCHAR2(4000) 
	//	LIST_STAT          VARCHAR2(20)   
	
	//개인 CheckList 카테고리 명 갖고오기
	public ArrayList<String> loadCatTitle(String emp_id) throws Exception{
		ArrayList<String> arrCatTitle = new ArrayList<String>();	 //카테고리번호 담을 ARList

		System.out.println("검색할 개발자 ID: " + emp_id);

		// 로그인한 사원의 ID에 해당하는 카테고리번호를 먼저 받아온다.
		// 3. sql문장 만들기 (select distinct 문장 만들기)
		String sql1 = "SELECT distinct CAT_TITLE as CAT_TITLE"
				+   " FROM tmp_chklist "
				+   " WHERE emp_id = ? ";	

		System.out.println("distinct sql문장실행: "+sql1);

		// 4. distinct sql 전송객체 얻어오기 (preparedStatement)
		ps = con.prepareStatement(sql1);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		ps.setString(1, emp_id);

		// 5. 전송
		ResultSet getCatTitle = ps.executeQuery();

		while(getCatTitle.next()){ //카테고리 넘버만 추출

			//참고출력용. System.out.println(getCatTitle.getString("CAT_TITLE"));			
			arrCatTitle.add(getCatTitle.getString("CAT_TITLE"));

			//	System.out.println(arrCatTitle.get(i++));
			//	//카테고리번호 갖고오는거 그냥 스트링으로 변환			
		}

		ps.close();
		return arrCatTitle;
	}


	// 개인 CheckList 내용 갖고오기
	public ArrayList<ArrayList<String>> loadChkList(String emp_id) throws Exception{

		ArrayList<ArrayList<String>> arrChek = new ArrayList<ArrayList<String>>();
		// 칸 당 arrCtent 한 줄을 담을 ARList
		ArrayList<String> arrCtent;		//카테고리번호, 카테제목, 항목번호, 항목내용, 상태 담을 ARList


		// 6. sql문장 만들기 (select 문장 만들기)
		String sql2 = "SELECT CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT "
				+   " FROM tmp_chklist "
				+   " WHERE emp_id = ? ";
		System.out.println("select sql문장실행: "+sql2);

		// 7. select sql 전송객체 얻어오기 (preparedStatement)
		ps = con.prepareStatement(sql2);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		ps.setString(1, emp_id);

		// 8. 전송
		ResultSet getChkList = ps.executeQuery();

		while(getChkList.next()){

			arrCtent = null;
			arrCtent = new ArrayList<String>();

			arrCtent.add(String.valueOf(getChkList.getInt("CAT_NUM")));
			arrCtent.add(getChkList.getString("CAT_TITLE"));
			arrCtent.add(String.valueOf(getChkList.getInt("LIST_NUM")));
			arrCtent.add(getChkList.getString("CONTENT"));
			arrCtent.add(getChkList.getString("LIST_STAT"));

			arrChek.add(arrCtent);

		}

		ps.close();
		return arrChek;
	}

	
	//팀 CheckList 카테고리 명 갖고오기
	public ArrayList<String> loadTeamCatTitle(String p_num) throws Exception{
		ArrayList<String> arrCatTitle = new ArrayList<String>();	 //카테고리번호 담을 ARList

		System.out.println("검색할 프로젝트번호: " + p_num);

		// 로그인한 사원의 ID에 해당하는 카테고리번호를 먼저 받아온다.
		// 3. sql문장 만들기 (select distinct 문장 만들기)
		String sql1 = "SELECT distinct CAT_TITLE as CAT_TITLE"
				+   " FROM tmp_chklist "
				+   " WHERE P_NUM = ? ";	

		System.out.println("team distinct sql문장실행: "+sql1);

		// 4. distinct sql 전송객체 얻어오기 (preparedStatement)
		ps = con.prepareStatement(sql1);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		ps.setInt(1, Integer.parseInt(p_num));

		// 5. 전송
		ResultSet getCatTitle = ps.executeQuery();

		while(getCatTitle.next()){ //카테고리 넘버만 추출

			//참고출력용. System.out.println(getCatTitle.getString("CAT_TITLE"));			
			arrCatTitle.add(getCatTitle.getString("CAT_TITLE"));

			//	System.out.println(arrCatTitle.get(i++));
			//	카테고리번호 갖고오는거 그냥 스트링으로 변환			
		}

		ps.close();
		return arrCatTitle;
	}
	
	
	// 팀 CheckList 내용 갖고오기
	public ArrayList<ArrayList<String>> loadTeamChkList(String p_num) throws Exception{

		ArrayList<ArrayList<String>> arrChek = new ArrayList<ArrayList<String>>();
		// 칸 당 arrCtent 한 줄을 담을 ARList
		ArrayList<String> arrCtent;		//카테고리번호, 카테제목, 항목번호, 항목내용, 상태 담을 ARList


		// 6. sql문장 만들기 (select 문장 만들기)
		String sql2 = "SELECT CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT "
				+   " FROM v_cat_list "
				+   " WHERE P_NUM = ? ";
		//System.out.println("team select sql문장실행: "+sql2);
		
		// 7. select sql 전송객체 얻어오기 (preparedStatement)
		ps = con.prepareStatement(sql2);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
		System.out.println("p_num " + p_num);
		ps.setInt(1, Integer.parseInt(p_num));

		// 8. 전송
		ResultSet getTChkList = ps.executeQuery();

		while(getTChkList.next()){

			arrCtent = null;
			arrCtent = new ArrayList<String>();

			arrCtent.add(String.valueOf(getTChkList.getInt("CAT_NUM")));
			arrCtent.add(getTChkList.getString("CAT_TITLE"));
			arrCtent.add(String.valueOf(getTChkList.getInt("LIST_NUM")));
			arrCtent.add(getTChkList.getString("CONTENT"));
			arrCtent.add(getTChkList.getString("LIST_STAT"));

			arrChek.add(arrCtent);

		}

		ps.close();
		return arrChek;

	}

	//	//CheckList 카테고리 및 항목 저장하기
	//	public void SaveChkList(CheckListRecord chklistR) throws Exception{
	//		
	//		//3. sql문장 만들기 (INSERT 문장 만들기)
	//		String sql = "INSERT INTO rent VALUES(RENT_NO_SEQ.nextval, ?, ?, '0100/01/01', sysdate, ?, ?)";
	//		//String sql = "INSERT INTO info VALUES(?,?,?,?,?,?)";
	//		System.out.println("sql문장실행: "+sql);
	//
	//		//4. sql 전송객체 얻어오기 (preparedStatement)
	//		ps = con.prepareStatement(sql);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
	//		
	//		ps.setString(1, chklistR.getCatTitle());
	//		ps.setString(2, chklistR.getContent());
	//		ps.setString(3, chklistR.getEmpId());
	//		ps.setString(4, chklistR.getListStat()());
	//		ps.setString(5, chklistR.getCatNum());
	//		ps.setString(6, chklistR.getListNum());
	//		ps.setString(7, chklistR.getpNum());
	//		
	//		ps.setInt(5, Integer.parseInt(chklistR.getAge()));
	//		ps.setString(6, chklistR.getHome());
	//		
	//		//4. sql 전송객체
	//		ps = con.prepareStatement(sql);
	//		
	//		ps.setInt(1, vnum);		//VIDEO_NO
	//		ps.setString(2, tel);	//TEL
	//		//ps.setString(2, vnum);	//DUE_DATE
	//		//ps.setString(3, vnum);	//CHECKOUT_DATE
	//		ps.setString(3, "미납");	//IS_RETURNED 반납미납
	//		ps.setInt(4, 10000);		//PRICE
	//		
	//		//5. 전송
	//		int insResult = ps.executeUpdate();
	//		System.out.println(insResult + "행이 수정되었어!.");
	//		
	//		ps.close();
	//		
	//	}

}
