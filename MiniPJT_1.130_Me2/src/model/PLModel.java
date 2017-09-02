package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import main.Database;
import module.BugReportBoard;	// 버그리포트 게시판
import module.CheckList;		// 체크리스트 생성
import module.PLDevBoard;
import record.CheckListRecord;
import record.EmpRecord;
import record.PostRecord;		// 게시물의 정보를 담는 클래스

public class PLModel {


	PreparedStatement ps1;
	PreparedStatement ps2;
	Statement stmt;
	Connection con;

	BugReportBoard bugBoard;
	PLDevBoard pldBoard;

	PostRecord p_rec;

	CheckList devchkl, teamchkl;
	
	ArrayList<DefaultMutableTreeNode> devTop, teamTop;
	ArrayList<DefaultMutableTreeNode> devNode, teamNode;
	
	ArrayList<ArrayList<String>> arrDevChek, arrTeamChek;
	ArrayList<String> arrDevCatTitle, arrTeamCatTitle;

	ArrayList<DefaultMutableTreeNode> rootTop;
	//ArrayList<DefaultMutableTreeNode> devNode, teamNode;

	ArrayList<ArrayList<String>> contentList;
	ArrayList<String> arrPLCatTitle, catTitleList;

	// 칸 당 arrCtent 한 줄을 담을 ARList

	/**
	 * db 랑 연결
	 */
	public PLModel(){
		// Connection 연결 객체 얻어오기
		
		try{
			con = Database.getConnection();
			
			System.out.println("메인, dbTest메소드 정상 실행, 정상연결");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			// 끝까지 인식 못하면 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}	
	}

	//차트 불러오기
	public void Chart(){

	}

	// 게시물의 정보를 담는 클래스 
	public void postRecord(){

	}
	
	/**
	 * DB에서 팀 체크리스트 불러와서 view로 넘기기
	 * @param p_num
	 * @return
	 */
	public DefaultMutableTreeNode teamChklist(String p_num){

		System.out.println("DevModel, teamChklist메소드 실행");
		DefaultMutableTreeNode teamChkRoot = new DefaultMutableTreeNode("팀" + p_num + "의 체크리스트 입니다");

		try{

			arrTeamChek = new ArrayList<ArrayList<String>>();
			arrTeamCatTitle = new ArrayList<String>();
			teamchkl = new CheckList();
			//emp_id에 할당된 카테고리 이름들을 먼저 불러오고
			//그걸 기준으로 항목들을 카테고리들로 분류하려 한다.
			//arrDevCatTitle[0], arrDevCatTitle[1], arrDevCatTitle[2]....
			//스트링임
			arrTeamCatTitle = teamchkl.loadTeamCatTitle(p_num);

			for(int i = 0 ; i < arrTeamCatTitle.size() ; i++){
				System.out.println(arrTeamCatTitle.get(i));
			}

			//실제 카테고리와 항목이 담긴 리스트내용들
			//arrDevChek[i]당 CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT가 담김
			//죄다 스트링임
			arrTeamChek = teamchkl.loadTeamChkList(p_num);

			System.out.println("DevModel, teamChklist메소드 실행, DB로부터 불러오기 정상");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}


		System.out.println("DevModel, teamChklist메소드 실행, 불러온 걸로 카테 별 분류시작");

		teamTop = new ArrayList<DefaultMutableTreeNode>();
		for(int i = 0 ; i < arrTeamCatTitle.size() ; i++){	//갖고온 카테고리의 갯수만큼만 반복문 돌려.

			teamTop.add(new DefaultMutableTreeNode(String.valueOf(i)+". "+arrTeamCatTitle.get(i)));
			System.out.println("팀 카테고리명 출력"+arrTeamCatTitle.get(i));		//참고용. 카테고리명 출력

			for (int j = 0; j < arrTeamChek.size() ; j++){	//갖고온 체크리스트의 레코드(1세트) 갯수만큼만 돌려.

				if(   (arrTeamCatTitle.get(i)).equals(arrTeamChek.get(j).get(1)) != false   ){


					// arrDevChek.get(i)는 i번째 레코드(행)
					// arrDevChek.get(i).get(1)는 해당 레코드(행)의 카테고리이름

					// 갖고온 카테고리 이름과 실제 체크리스트의 카테고리 이름을 비교하여
					// 맞으면 해당 부모노드에 항목을 추가,
					System.out.println("team 내용 출력: "+arrTeamChek.get(j).get(3));
					teamTop.get(i).add(new DefaultMutableTreeNode(arrTeamChek.get(j).get(3)));
					//항목내용이 카테고리에 삽입		
				}
			}
			teamChkRoot.add(teamTop.get(i));
		}

		//			if(empChkRoot == null){
		//				System.out.println("empChkRoot은 Null 입니다");
		//			}
		return teamChkRoot;
	}

	/**
	 * 개발자 이름, 아이디를 가져오는 메소드
	 * 일단은 레코드 전체를 가져옴  
	 */	
	public ArrayList<ArrayList<String>> getDevList(EmpRecord rec) throws Exception{

		ArrayList<ArrayList<String>> devRecList = new ArrayList<ArrayList<String>>(); //개발자의 이름과 아이디를 저장하는 어레이리스트
		String currPL = rec.getEmpId(); //현재 로그인한 PL 의 아이디 가져오기

		//sql
		String sql = "SELECT ename, emp_id FROM test_emp WHERE job = '개발자' and mgr_id = ?";
		ps1 = con.prepareStatement(sql);
		ps1.setString(1, currPL);

		ResultSet rs = ps1.executeQuery();

		while(rs.next()){
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(rs.getString("ename"));
			tmp.add(rs.getString("emp_id"));

			devRecList.add(tmp);		
		}
		ps1.close();

		return devRecList;
	}

	
	/**
	 * 코드테이블 갖고와서 예외만 처리하고 바로 DevView로 넘기기
	 * TableModel devTModel, bugTModel;
	 * JTable devTable, bugTable;
	 * @param p_num
	 * @return ArrayList<ArrayList<String>>
	 */
	public ArrayList<ArrayList<String>> devTable(int p_num){
		
		ArrayList<ArrayList<String>> ARLT = new ArrayList<ArrayList<String>>();
		
//		devTable = new JTable();
//		devTModel = new TableModel();

		try{			
			pldBoard = new PLDevBoard();
			
			ARLT = pldBoard.loadBoardContent(p_num);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}
//		devTable.setModel(devTModel);
//		devTModel.fireTableDataChanged();

		return ARLT;
	}
	
	/**
	 * 개발자게시판 버그테이블 갖고와서 예외만 처리하고 바로 DevView로 넘기기
	 * @param p_num
	 * @return
	 */
	public ArrayList<ArrayList<String>> bugTable(int p_num){
		
		ArrayList<ArrayList<String>> ARLT = new ArrayList<ArrayList<String>>();
//		bugTable = new JTable();
//		bugTModel = new TableModel();
		
		try{
			bugBoard = new BugReportBoard();
			
			ARLT = bugBoard.loadBugContent(p_num); //예외처리

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}

		
//		bugTable.setModel(bugTModel);
//		bugTModel.fireTableDataChanged();
		
		return ARLT;
	}
}

//------------------예지작성

/**
 * 체크리스트 추가 or 생성 하는 메소드
 * @param chRec 해당 카테고리의 정보
 * @param listRec 카테고리에 포함된 항목들
 * @throws Exception 이 함수를 수행하다 생기는 모든 예외
 * @author yeji
 */
//public void addCheckList(ArrayList<CheckListRecord> chRec) throws Exception{
//
//	ArrayList<CheckListRecord> list = chRec;
//	int catNum = 0;
//	//카테고리 테이블 부터 생성 (카테고리번호, 프로젝트 번호, 카테고리제목)
//	//임의로 첫번째 레코드 가져오기 (프로젝트번호와 제목은 이 전체 레코드에서 공통적인 부분)
//	String sql1 = "INSERT INTO category VALUES (cat_num_seq.nextval, ?,?)";
//	ps1 = con.prepareStatement(sql1);
//	ps1.setInt(1, chRec.get(0).getpNum());
//	ps1.setString(2, chRec.get(0).getCatTitle());
//
//	int result = ps1.executeUpdate();
//
//	ps1.close();
//
//	//카테고리 번호 가져오기 
//	String sql2 = "SELECT cat_num_seq.currval currval FROM dual";
//	stmt = con.createStatement();
//
//	ResultSet rs = stmt.executeQuery(sql2);
//	if(rs.next()){
//		catNum = rs.getInt("currval");
//	}	
//
//	//항목 저장
//	String sql3 = "INSERT INTO list (list_num, cat_num, emp_id, content)"
//			+ " VALUES(list_num_seq.nextval,?,?,?)";
//	ps2 = con.prepareStatement(sql3);
//
//	for(int i = 0; i < chRec.size(); i++){			
//		ps2.setInt(1, catNum);
//		ps2.setString(2, chRec.get(i).getEmpId());
//		ps2.setString(3, chRec.get(i).getContent());
//		ps2.addBatch();
//	}
//
//	int[] result2 = ps2.executeBatch(); //executeUpdate 한번에 수행 
//	//확인 TODO: 지우기 
//	for(int i = 0; i < result2.length; i++){
//		System.out.println(result2[i] + " 행을 수행");
//	}
//	ps2.close();		
//}
///**
// * 저장된 체크리스트들을 가져오는 메소드
// * 
// *//*
//public ArrayList<ArrayList<String>> getPLCheckList(EmpRecord rec) throws Exception{
//	ArrayList<ArrayList<String>> checkListContent = new ArrayList<ArrayList<String>>();
//	ArrayList<String> checkListTitle = new ArrayList<String>();
//
//	checkListTitle = teamchkl.loadCatTitle(rec.getEmpId());
//	checkListContent = teamchkl.loadTeamChkList(String.valueOf(rec.getpNum()));	
//
//	return checkListContent;
//}*/





/**
 * 트리를 만들어주는 메소드 
 * @param rec
 * @return root 트리 root 노트 
 * @throws Exception 이 메소드를 실행하다 발생하는 모든 예외를 던짐 
 * @author yeji
 */
//public DefaultMutableTreeNode getTree(EmpRecord rec) throws Exception{
//	contentList = new ArrayList<ArrayList<String>>();
//	catTitleList = new ArrayList<String>();
//	//루트명을 프로젝트로 함 
//	String sql = "SELECT p_name FROM project WHERE pl_id = ?";
//	ps1 = con.prepareStatement(sql);
//	ps1.setString(1, rec.getEmpId());
//
//	String pName = ""; // 프로젝트명
//	ResultSet rs = ps1.executeQuery();
//
//	if(rs.next()){
//		pName = rs.getString("p_name");
//	}
//	
//	ps1.close(); //닫기 
//	
//	//루트 설정
//	DefaultMutableTreeNode root = new DefaultMutableTreeNode(pName);
//	
//	plCheckList = new CheckList();
//	
//	//emp_id에 할당된 카테고리 이름들을 먼저 불러오고
//	//그걸 기준으로 항목들을 카테고리들로 분류하려 한다.
//	catTitleList = plCheckList.loadTeamCatTitle(String.valueOf(rec.getpNum()));
//
//	//실제 카테고리와 항목이 담긴 리스트내용들
//	//arrDevChek[i]당 CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT가 담김
//	//죄다 스트링임
//	contentList = plCheckList.loadTeamChkList(String.valueOf(rec.getpNum()));
//	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//	System.out.println("카테고리 갯수: " + catTitleList.size());
//	System.out.println();
//	
//	
//	rootTop = new ArrayList<DefaultMutableTreeNode>();
//	for(int i = 0 ; i < catTitleList.size() ; i++){	//갖고온 카테고리의 갯수만큼만 반복문 돌려.
//
//		rootTop.add(new DefaultMutableTreeNode(String.valueOf(0)+". "+catTitleList.get(i)));
//		System.out.println("팀 카테고리명 출력"+ catTitleList.get(i));		//참고용. 카테고리명 출력
//
//		for (int j = 0; j < contentList.size() ; j++){	//갖고온 체크리스트의 레코드(1세트) 갯수만큼만 돌려.
//
//			if(   (catTitleList.get(i)).equals(contentList.get(j).get(1)) != false   ){
//
//
//				// arrDevChek.get(i)는 i번째 레코드(행)
//				// arrDevChek.get(i).get(1)는 해당 레코드(행)의 카테고리이름
//
//				// 갖고온 카테고리 이름과 실제 체크리스트의 카테고리 이름을 비교하여
//				// 맞으면 해당 부모노드에 항목을 추가,
//				System.out.println("team 내용 출력: "+contentList.get(j).get(3));
//				rootTop.get(i).add(new DefaultMutableTreeNode(String.valueOf(j)+". "+ contentList.get(j).get(3)));
//				//항목내용이 카테고리에 삽입		
//			}
//		}
//		root.add(rootTop.get(i));
//	}
//
//	//		if(empChkRoot == null){
//	//			System.out.println("empChkRoot은 Null 입니다");
//	//		}
//	return root;
//}