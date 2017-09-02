package module;
//체크리스트.java는 개인체크리스트 탭(PL, 개발자)에서 나오는 것과
//팀 전체 체크리스트를 담당.

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Database;
import record.CheckListRecord;

public class CheckList {

	Connection con = null;
	Statement stmt = null;
	PreparedStatement ps = null;
	PreparedStatement ps1, ps2, ps3;

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
	
	/**
	 * 개인 CheckList 카테고리 명 갖고오기
	 * @param emp_id
	 * @return
	 * @throws Exception
	 */
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


	/**
	 * 개인 CheckList 내용 갖고오기
	 * @param emp_id
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArrayList<String>> loadChkList(String emp_id) throws Exception{

		ArrayList<ArrayList<String>> arrChek = new ArrayList<ArrayList<String>>();
		// 칸 당 arrCtent 한 줄을 담을 ARList
		ArrayList<String> arrCtent;		//카테고리번호, 카테제목, 항목번호, 항목내용, 상태 담을 ARList


		// 6. sql문장 만들기 (select 문장 만들기)
		String sql2 = "SELECT CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT "
				+   " FROM test_v_cat_List "
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

	/**
	 * 팀 CheckList 카테고리 명 갖고오기 
	 * @param p_num
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> loadTeamCatTitle(String p_num) throws Exception{
		ArrayList<String> arrCatTitle = new ArrayList<String>();	 //카테고리번호 담을 ARList

		System.out.println("검색할 프로젝트번호: " + p_num);

		// 로그인한 사원의 ID에 해당하는 카테고리번호를 먼저 받아온다.
		// 3. sql문장 만들기 (select distinct 문장 만들기)
		String sql1 = "SELECT distinct CAT_TITLE as CAT_TITLE"
				+   " FROM test_v_cat_List "
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
	
	/**
	 * 팀 CheckList 내용 갖고오기
	 */
	public ArrayList<ArrayList<String>> loadTeamChkList(String p_num) throws Exception{

		ArrayList<ArrayList<String>> arrChek = new ArrayList<ArrayList<String>>();
		// 칸 당 arrCtent 한 줄을 담을 ARList
		ArrayList<String> arrCtent;		//카테고리번호, 카테제목, 항목번호, 항목내용, 상태 담을 ARList


		// 6. sql문장 만들기 (select 문장 만들기)
		String sql2 = "SELECT CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT "
				+   " FROM test_v_cat_List "
				+   " WHERE P_NUM = ? ";
		System.out.println("team select sql문장실행: "+sql2);
		
		// 7. select sql 전송객체 얻어오기 (preparedStatement)
		ps = con.prepareStatement(sql2);//여기선 prepared가 아님. 그리고 여기서 sql끌고감.
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

	/**
	 * CheckList 카테고리 및 항목 추가하기
	 */
	public void addARLChkList(ArrayList<CheckListRecord> recARL) throws Exception{
		//다중으로 레코드를 받음
		
		//우선 추가하려는 항목과 카테고리를 갖고 이미 DB에 카테고리가 존재하는지를 확인
		//카테고리와 항목 둘다 비교 해라.
		//카테고리가 중복된다면 해당 카테고리의 번호를 갖고 온 후 항목 추가작업을 실행하고
		//카테고리와 그 안의 항목 동시에 중복된다면 경고창을 띄워야 한다.
		
		//우선 추가하려는 카테고리를 갖고 이미 DB의 Category 테이블에 존재하는지를 확인
		//(일단 위의 쿼리로 CAT_NUM과 CAT_TITLE 갖고는 와야 함)
		//없으면 등록, 있으면
		//추가하려는 항목과 갖고온 카테넘버, 프로젝트 넘버를 가지고 LIST 테이블에서 존재하는지 확인.
		//없으면 등록, 있으면
		//경고창 팝업.
		
		ArrayList<String> loadCatNumTitleARL = new ArrayList<String>(); 
		ArrayList<String> loadListTitleARL = new ArrayList<String>();
		ArrayList<CheckListRecord> recARLTemp = new ArrayList<CheckListRecord>();
	
		
		//3. sql 문장
		String sql1 = "SELECT CAT_NUM, CAT_TITLE from test_CATEGORY "
				+ "	  WHERE P_NUM = ? AND CAT_TITLE = ? ";
		
		System.out.println(sql1);
		
		//4. 전송객체에 등록
		//프로젝트번호와 카테고리명은 이 ARL 내 레코드들의 공통적인 부분으로
		//임의 레코드 갖고와서 카테고리명 갖고 옴.
		ps1 = con.prepareStatement(sql1);
		ps1.setInt(1, recARL.get(0).getpNum());
		ps1.setString(2, recARL.get(0).getCatTitle());
		
		//5. 전송
		ResultSet rs = ps1.executeQuery();
		
		//While반복문으로 DB로부터 카테고리와 카테고리 번호를 배열에 저장.
		while(rs.next()){
			loadCatNumTitleARL = new ArrayList<String>();
			loadCatNumTitleARL.add(String.valueOf(rs.getInt("CAT_NUM")));
			loadCatNumTitleARL.add(rs.getString("CAT_TITLE"));

		}
		ps1.close();
		//참고출력용 카테고리테이블에 카테고리명이 없는데 여기서 출력을 한다고 하면 
		//java.lang.IndexOutOfBoundsException 발생. 아무것도 갖고 온게 없으니까
		//System.out.println("갖고와서 확인 할 카테고리번호 출력: " + loadCatNumTitleARL.get(0));
		//System.out.println("갖고와서 확인 할 카테고리명 출력: " + loadCatNumTitleARL.get(1));
		
		
		
		//아무것도 못갖고왔을 때는 loadCatNumTitleARL.size()가 0
		if(loadCatNumTitleARL.size() == 0){//프로젝트에 카테고리가 없을 때
			
			//카테고리 저장 > 카테고리 테이블
			//sql 문장
			sql1 = "INSERT INTO test_category VALUES (cat_num_seq.nextval, ?,?)";
			
			//프로젝트번호와 카테고리명은 이 ARL 내 레코드들의 공통적인 부분으로
			//임의 레코드 갖고와서 카테고리명 갖고 옴.
			ps1 = con.prepareStatement(sql1);
			ps1.setInt(1, recARL.get(0).getpNum());
			ps1.setString(2, recARL.get(0).getCatTitle());

			int insCatResult = ps1.executeUpdate();
			System.out.println("카테고리" + insCatResult + " 행을 추가");
			
			ps1.close();
			
			
			//방금 삽입한 카테고리에 할당된 카테번호 알아오기.
			String sql2 = "SELECT CAT_NUM, CAT_TITLE from test_CATEGORY "
					+ "	  WHERE P_NUM = ? AND CAT_TITLE = ? ";
			
			System.out.println(sql2);
			
			//4. 전송객체에 등록
			//프로젝트번호와 카테고리명은 이 ARL 내 레코드들의 공통적인 부분으로
			//임의 레코드 갖고와서 카테고리명 갖고 옴.
			ps1 = con.prepareStatement(sql2);
			ps1.setInt(1, recARL.get(0).getpNum());
			ps1.setString(2, recARL.get(0).getCatTitle());

			ResultSet rs1 = ps1.executeQuery();
			
			//While반복문으로 DB로부터 카테고리와 카테고리 번호를 배열에 저장.
			while(rs1.next()){
				loadCatNumTitleARL = new ArrayList<String>();
				loadCatNumTitleARL.add(String.valueOf(rs1.getInt("CAT_NUM")));
				loadCatNumTitleARL.add(rs1.getString("CAT_TITLE"));

			}
			ps1.close();
			
			//참고출력용
			System.out.println("갖고와서 확인 할 카테고리번호 출력: " + loadCatNumTitleARL.get(0));
			System.out.println("갖고와서 확인 할 카테고리명 출력: " + loadCatNumTitleARL.get(1));
			
		}
		
		


		//이제 항목명과 DB로부터 갖고 온 카테넘버를 갖고 List 테이블에서 있는지 확인.

		//3. sql 문장
		String sql4 = "SELECT CONTENT from test_list "
				+ "  WHERE CAT_NUM = ? ";
		System.out.println("sql4: "+ sql4);			

		//4. 전송객체에 등록
		//프로젝트번호와 카테고리명은 이 ARL 내 레코드들의 공통적인 부분으로
		//임의 레코드 갖고와서 카테고리명 갖고 옴.
		ps1 = null;
		ps1 = con.prepareStatement(sql4);
		ps1.setInt(1, Integer.valueOf(loadCatNumTitleARL.get(0)));

		//참고출력용
		System.out.println("Integer.valueOf(loadCatNumTitleARL.get(0)): " + Integer.valueOf(loadCatNumTitleARL.get(0)));

		ResultSet rs2 = ps1.executeQuery();//ResultSet rs2 = ps1.executeQuery();

		while(rs2.next()){
			loadListTitleARL.add(rs2.getString("CONTENT"));//loadListTitleARL.add(여기가 rs1이면 어떡하냐 rs2여야지 .getString("CONTENT"));

		}

		ps1.close();
		System.out.println("여기");

		//참고출력용
		for(int i = 0 ; i < loadListTitleARL.size(); i++){
			System.out.println("갖고와서 확인 할 항목내용 출력: " + loadListTitleARL.get(i));
		}			




		//같은 카테고리 내 동일한 항목의 존재를 확인
		//	이미 DB에 올라와있는 놈들 중 같은카테고리 내 동일한 항목이 있는 지를 확인 후
		//	있으면 걸러내고 없다면 따로 recARLTemp로 저장. 이후 쿼리는 recARLTemp 요걸로 보낼 꺼임.
		jLevel://컨틴뉴에 걸리면 여기서 바로 다음회차로 시작
			for(int i = 0 ; i < recARL.size(); i++){

				for(int j = 0 ; j < loadListTitleARL.size() ; j++){

					if(   recARL.get(i).getContent().equals(loadListTitleARL.get(j)) == true   ){
						continue jLevel;
					}
				}
				//매칭이 안되면 recARLTemp에다 추가... 후 다음 회차로
				// recARLTemp에 저장된 것들은 새로 등록될 것들
				recARLTemp.add(recARL.get(i));

			}

		//중복되는 것들을 모두 덜어낸 recARLTemp로
		//항목저장
		String sql3 = "INSERT INTO test_list (list_num, cat_num, emp_id, content)"
				+ " VALUES(list_num_seq.nextval,?,?,?)";
		ps2 = con.prepareStatement(sql3);

		for(int i = 0; i < recARLTemp.size(); i++){			
			ps2.setInt(1, Integer.valueOf(loadCatNumTitleARL.get(0)));
			ps2.setString(2, recARLTemp.get(i).getEmpId());
			ps2.setString(3, recARLTemp.get(i).getContent());
			ps2.addBatch();
		}

		int[] result2 = ps2.executeBatch(); //executeUpdate 한번에 수행 
		//확인 TODO: 지우기 
		for(int i = 0; i < result2.length; i++){
			System.out.println(result2[i] + " 행을 수행");
		}
		ps2.close();

	}
	
	/**
	 * 미구현 
	 */
	void insertARLChkList(ArrayList<CheckListRecord> recARL, String loadCatNum) throws Exception{
		
	}
	
	/**
	 *  개발자에게 CheckList 카테고리 및 항목 할당
	 */
	public void assignARLChkList(ArrayList<CheckListRecord> recARL) throws Exception{

		//다중으로 레코드를 받음
		
		//카테고리명만 있으므로 카테고리테이블에서 카테고리에 연결된 카테고리번호를 갖고와야 함.
		//그러기 위해 카테고리명만 뽑아내야 하는데
		//문제는 이게 중복된 것도 있고 그 순서도 뒤죽박죽이라 우선,
		//ARL로 서로가 중복않고 카테고리명만 존재하는 배열을 만들어 따로 빼내야 함.
		
		//카테고리명만 존재 할 catNumARL 생성.
		ArrayList<String> catNumARL = new ArrayList<String>();
		catNumARL.add(""); //초기값 공백 넣어주기. 이게 없으면 처음부터 catNumARL.size()가 0이라 계속 돌아.
		
		jLevel://컨틴뉴에 걸리면 여기서 바로 다음회차로 시작
		for(int i = 0 ; i < recARL.size(); i++){
			
			for(int j = 0 ; j < catNumARL.size() ; j++){
				
				if(   recARL.get(i).getCatTitle().equals(catNumARL.get(j)) == true   ){
					continue jLevel;
				}
			}
		
			//매칭이 안되면 catNumARL에다 추가... 후 다음 회차로
			catNumARL.add(recARL.get(i).getCatTitle());
			
		}
		catNumARL.remove(0);//0번째는 ""이므로 지워버림.
		
		//참고출력용. catNumARL출력. 하나씩만 출력된 걸 확인하면 됨.
		for(int i = 0 ; i < catNumARL.size(); i++){
			
			System.out.println("catNumARL출력: "+catNumARL.get(i));
		}
		
		
		
		
		// 할당 될  카테고리명 만의 ARL을 생성했으니 
		// 이걸 가지고 카테고리 번호 가져오기.
		//3. sql 문장
		String sql1 = "SELECT CAT_NUM, CAT_TITLE from test_CATEGORY "
				+ "	  WHERE P_NUM = ? AND CAT_TITLE = ? ";
		System.out.println(sql1);
		
		//4. sql 전송객체
		//임의로 첫번째 레코드 가져오기 (프로젝트번호는 할당될 항목들의 공통적인 부분)
		
		ArrayList<ArrayList<String>> loadCatNumTitleARLT = new ArrayList<ArrayList<String>>();		
		ArrayList<String> loadCatNumTitleARL;
		
		for(int i = 0 ; i < catNumARL.size() ; i++){
			ps1 = con.prepareStatement(sql1);
			
			ps1.setInt(1, recARL.get(0).getpNum());//(프로젝트번호는 할당 될 항목들의 공통적인 부분 ~ )
			ps1.setString(2, catNumARL.get(i));
			
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next()){
				loadCatNumTitleARL = new ArrayList<String>();
				loadCatNumTitleARL.add(String.valueOf(rs.getInt("CAT_NUM")));
				loadCatNumTitleARL.add(rs.getString("CAT_TITLE"));
			
				loadCatNumTitleARLT.add(loadCatNumTitleARL);
			}
			
			ps1.close();
		}
		
		//전부 받아옴.

		//이제 알아온 카테고리 넘버를 기존의 recARL에 저장된 카테고리명과 비교하여
		//해당 카테고리 넘버를 카테고리명에 매칭하여 recARL에 저장.
		for(int i = 0 ; i < recARL.size(); i++){
			for(int j = 0 ; j < loadCatNumTitleARLT.size() ; j++){
				
				if(   recARL.get(i).getCatTitle().equals(loadCatNumTitleARLT.get(j).get(1)   ) == true)
					//맞으면 카테넘버를 아래와 같이 저장.
					recARL.get(i).setCatNum(Integer.parseInt(loadCatNumTitleARLT.get(j).get(0)));
				
			}
		}
		//-------- 여기까지 recARL에 저장된 rec의 카테고리넘버와
		// 카테고리 번호가 매칭하여 전부 저장됨 등록됨
		
		
		
		
		//갖고 온 카테고리 번호를 가지고 EMP_ID, LIST_STAT 수정하기
		//5. sql 문장
		String sql2 = "UPDATE test_List SET EMP_ID = ?, LIST_STAT = '작업중' "
				+ " WHERE CAT_NUM = ? AND CONTENT = ? ";
		System.out.println(sql2);
		
		//6. sql 전송객체
		ps2 = con.prepareStatement(sql2);
		
		//7. sql 전송. 반복문으로 항목들 addBatch로.
		for(int i = 0; i < recARL.size(); i++){			
			
			ps2.setString(1, recARL.get(i).getEmpId());
			ps2.setInt(2, recARL.get(i).getCatNum());
			ps2.setString(3, recARL.get(i).getContent());
			ps2.addBatch();
			
			System.out.println(recARL.get(i).getEmpId() + "  " + recARL.get(i).getCatNum() + "  " + recARL.get(i).getContent());
			
		}
		
		//5. 전송
		int [] udtResult = ps2.executeBatch();
		
		for(int i = 0; i < udtResult.length; i++){
			System.out.println(udtResult[i] + "행이 수정되었어!.");
		}
		

		ps2.close();

	}
	
}

//- 예지꺼

//int catNum = 0;
////카테고리 테이블 부터 생성 (카테고리번호, 프로젝트 번호, 카테고리제목)
////임의로 첫번째 레코드 가져오기 (프로젝트번호와 제목은 이 전체 레코드에서 공통적인 부분)
//
//
//
//
//
//String sql1 = "INSERT INTO test_category VALUES (cat_num_seq.nextval, ?,?)";
//ps1 = con.prepareStatement(sql1);
//ps1.setInt(1, chRec.get(0).getpNum());
//ps1.setString(2, chRec.get(0).getCatTitle());
//
//int result = ps1.executeUpdate();
//
//ps1.close();
//
////카테고리 번호 가져오기 
//String sql2 = "SELECT cat_num_seq.currval currval FROM dual";
//stmt = con.createStatement();
//
//ResultSet rs = stmt.executeQuery(sql2);
//if(rs.next()){
//	catNum = rs.getInt("currval");
//}	
//
////항목 저장
//String sql3 = "INSERT INTO test_list (list_num, cat_num, emp_id, content)"
//		+ " VALUES(list_num_seq.nextval,?,?,?)";
//ps2 = con.prepareStatement(sql3);
//
//for(int i = 0; i < chRec.size(); i++){			
//	ps2.setInt(1, catNum);
//	ps2.setString(2, chRec.get(i).getEmpId());
//	ps2.setString(3, chRec.get(i).getContent());
//	ps2.addBatch();
//}
//
//int[] result2 = ps2.executeBatch(); //executeUpdate 한번에 수행 
////확인 TODO: 지우기 
//for(int i = 0; i < result2.length; i++){
//	System.out.println(result2[i] + " 행을 수행");
//}
//ps2.close();
//
//}

//if(rs.next() == false){//프로젝트에 카테고리가 단 한 개도 없을 때
//	
//	//무조건 카테고리와 항목을 저장
//	insertARLChkList(recARL, null);
//	JOptionPane.showMessageDialog(null, "DB에 최초 삽입 수행완료.");
//	return;
//	
//}else{//프로젝트에 카테고리가 단 한 개라도 있을 때
//
//	//While반복문으로 DB로부터 카테고리와 카테고리 번호를 배열에 저장.
//	while(rs.next()){
//		loadCatNumTitleARL = new ArrayList<String>();
//		loadCatNumTitleARL.add(String.valueOf(rs.getInt("CAT_NUM")));
//		loadCatNumTitleARL.add(rs.getString("CAT_TITLE"));
//
//	}
//}
