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

	CheckList plCheckList;

	ArrayList<DefaultMutableTreeNode> rootTop;
	ArrayList<DefaultMutableTreeNode> devNode, teamNode;

	ArrayList<ArrayList<String>> contentList;
	ArrayList<String> arrPLCatTitle, catTitleList;

	// 칸 당 arrCtent 한 줄을 담을 ARList

	/**
	 * db 랑 연결
	 */
	public PLModel() throws Exception{
		// Connection 연결 객체 얻어오기	
		con = Database.getConnection();	
	}

	//차트 불러오기
	public void Chart(){

	}

	// 게시물의 정보를 담는 클래스
	public void postRecord(){

	}

	/**
	 * 체크리스트 추가 or 생성 하는 메소드
	 * @param chRec 해당 카테고리의 정보
	 * @param listRec 카테고리에 포함된 항목들
	 * @throws Exception 이 함수를 수행하다 생기는 모든 예외
	 * @author yeji
	 */
	public void addCheckList(ArrayList<CheckListRecord> chRec) throws Exception{

		ArrayList<CheckListRecord> list = chRec;
		int catNum = 0;
		//카테고리 테이블 부터 생성 (카테고리번호, 프로젝트 번호, 카테고리제목)
		//임의로 첫번째 레코드 가져오기 (프로젝트번호와 제목은 이 전체 레코드에서 공통적인 부분)
		String sql1 = "INSERT INTO category VALUES (cat_num_seq.nextval, ?,?)";
		ps1 = con.prepareStatement(sql1);
		ps1.setInt(1, chRec.get(0).getpNum());
		ps1.setString(2, chRec.get(0).getCatTitle());

		int result = ps1.executeUpdate();

		ps1.close();

		//카테고리 번호 가져오기 
		String sql2 = "SELECT cat_num_seq.currval currval FROM dual";
		stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery(sql2);
		if(rs.next()){
			catNum = rs.getInt("currval");
		}	

		//항목 저장
		String sql3 = "INSERT INTO list (list_num, cat_num, emp_id, content)"
				+ " VALUES(list_num_seq.nextval,?,?,?)";
		ps2 = con.prepareStatement(sql3);

		for(int i = 0; i < chRec.size(); i++){			
			ps2.setInt(1, catNum);
			ps2.setString(2, chRec.get(i).getEmpId());
			ps2.setString(3, chRec.get(i).getContent());
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
	 * 저장된 체크리스트들을 가져오는 메소드
	 * 
	 *//*
	public ArrayList<ArrayList<String>> getPLCheckList(EmpRecord rec) throws Exception{
		ArrayList<ArrayList<String>> checkListContent = new ArrayList<ArrayList<String>>();
		ArrayList<String> checkListTitle = new ArrayList<String>();

		checkListTitle = teamchkl.loadCatTitle(rec.getEmpId());
		checkListContent = teamchkl.loadTeamChkList(String.valueOf(rec.getpNum()));	

		return checkListContent;
	}*/

	/**
	 * 트리를 만들어주는 메소드 
	 * @param rec
	 * @return root 트리 root 노트 
	 * @throws Exception 이 메소드를 실행하다 발생하는 모든 예외를 던짐 
	 * @author yeji
	 */
	public DefaultMutableTreeNode getTree(EmpRecord rec) throws Exception{
		contentList = new ArrayList<ArrayList<String>>();
		catTitleList = new ArrayList<String>();
		//루트명을 프로젝트로 함 
		String sql = "SELECT p_name FROM project WHERE pl_id = ?";
		ps1 = con.prepareStatement(sql);
		ps1.setString(1, rec.getEmpId());

		String pName = ""; // 프로젝트명
		ResultSet rs = ps1.executeQuery();

		if(rs.next()){
			pName = rs.getString("p_name");
		}

		ps1.close(); //닫기 

		//루트 설정
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(pName);

		plCheckList = new CheckList();

		//emp_id에 할당된 카테고리 이름들을 먼저 불러오고
		//그걸 기준으로 항목들을 카테고리들로 분류하려 한다.
		catTitleList = plCheckList.loadTeamCatTitle(String.valueOf(rec.getpNum()));

		//실제 카테고리와 항목이 담긴 리스트내용들
		//arrDevChek[i]당 CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT가 담김
		//죄다 스트링임
		contentList = plCheckList.loadTeamChkList(String.valueOf(rec.getpNum()));
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("카테고리 갯수: " + catTitleList.size());
		System.out.println();


		rootTop = new ArrayList<DefaultMutableTreeNode>();
		for(int i = 0 ; i < catTitleList.size() ; i++){	//갖고온 카테고리의 갯수만큼만 반복문 돌려.

			rootTop.add(new DefaultMutableTreeNode(String.valueOf(0)+". "+catTitleList.get(i)));
			System.out.println("팀 카테고리명 출력"+ catTitleList.get(i));		//참고용. 카테고리명 출력

			for (int j = 0; j < contentList.size() ; j++){	//갖고온 체크리스트의 레코드(1세트) 갯수만큼만 돌려.

				if(   (catTitleList.get(i)).equals(contentList.get(j).get(1)) != false   ){


					// arrDevChek.get(i)는 i번째 레코드(행)
					// arrDevChek.get(i).get(1)는 해당 레코드(행)의 카테고리이름

					// 갖고온 카테고리 이름과 실제 체크리스트의 카테고리 이름을 비교하여
					// 맞으면 해당 부모노드에 항목을 추가,
					System.out.println("team 내용 출력: "+contentList.get(j).get(3));
					rootTop.get(i).add(new DefaultMutableTreeNode(String.valueOf(j)+". "+ contentList.get(j).get(3)));
					//항목내용이 카테고리에 삽입		
				}
			}
			root.add(rootTop.get(i));
		}

		//		if(empChkRoot == null){
		//			System.out.println("empChkRoot은 Null 입니다");
		//		}
		return root;
	}

	/**
	 * 개발자 이름, 아이디를  가져오는 메소드
	 * 일단은 레코드 전체를 가져옴  
	 */	
	public ArrayList<ArrayList<String>> getDevList(EmpRecord rec) throws Exception{

		ArrayList<ArrayList<String>> devRecList = new ArrayList<ArrayList<String>>(); //개발자의 이름과 아이디를 저장하는 어레이리스트
		String currPL = rec.getEmpId(); //현재 로그인한 PL 의 아이디 가져오기

		//sql
		String sql = "SELECT ename, emp_id FROM emp WHERE job = '개발자' and mgr_id = ?";
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
	 * 개발자 게시판 버그 테이블 가져오는 메소드 
	 * @param p_num 프로젝트 번호
	 * @return 버그게시판의 모든 정보를 담은 어레이 리스트를 반환
	 * @throws Exception 버그테이블을 가져오며 생기는 모든 예외를 던짐 
	 */
	public ArrayList<ArrayList<String>> bugTable(int p_num) throws Exception{

		ArrayList<ArrayList<String>> ARLT = new ArrayList<ArrayList<String>>();

		bugBoard = new BugReportBoard();
		ARLT = bugBoard.loadBugContent(p_num); 

		return ARLT;
	}

	/**
	 * 개발자 게시판 가져오는 메소드 
	 * @param p_num 프로젝트 번호
	 * @return 개발자 게시판에 있는 모든 정보를 가지고있는 어레이리스트를 반환
	 * @throws Exception 개발자 게시판을 가져오며 발생하는 모든 예외를 던짐 
	 */
	public ArrayList<ArrayList<String>> devPLTable(int p_num) throws Exception{

		ArrayList<ArrayList<String>> ARLT = new ArrayList<ArrayList<String>>();
		pldBoard = new PLDevBoard();
		ARLT = pldBoard.loadBoardContent(p_num);

		return ARLT;
	}


}
