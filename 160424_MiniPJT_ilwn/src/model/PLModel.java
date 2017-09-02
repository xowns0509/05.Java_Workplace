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
	
	ArrayList<ArrayList<String>> devRecList = new ArrayList<ArrayList<String>>(); //개발자의 이름과 아이디를 저장하는 어레이리스트

	BugReportBoard bugBoard;
	PLDevBoard pldBoard;

	PostRecord p_rec;

	CheckList devchkl, teamchkl;

	ArrayList<DefaultMutableTreeNode> devTop, teamTop;
	ArrayList<DefaultMutableTreeNode> devNode, teamNode;

	ArrayList<ArrayList<String>> arrPLChek, arrTeamChek;
	ArrayList<String> arrPLCatTitle, arrTeamCatTitle;

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
	 * 개발자 이름, 아이디를  가져오는 메소드
	 * 일단은 레코드 전체를 가져옴  
	 */	
	public ArrayList<ArrayList<String>> getDevList(EmpRecord rec) throws Exception{

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
	 * 개발자 체크리스트 불러와서 view에 넘기기
	 * @param emp_id 사원 아이디 
	 * @return taejun, yeji
	 */
	public DefaultMutableTreeNode devChklist(String emp_id){

		System.out.println("DevModel, devChklist메소드 실행");
		DefaultMutableTreeNode empChkRoot = new DefaultMutableTreeNode(emp_id + "의 체크리스트 입니다");

		try{			
			arrPLChek = new ArrayList<ArrayList<String>>();
			arrPLCatTitle = new ArrayList<String>();
			devchkl = new CheckList();
			//emp_id에 할당된 카테고리 이름들을 먼저 불러오고
			//그걸 기준으로 항목들을 카테고리들로 분류하려 한다.
			//arrDevCatTitle[0], arrDevCatTitle[1], arrDevCatTitle[2]....
			//스트링임
			arrPLCatTitle = devchkl.loadCatTitle(emp_id);

			//실제 카테고리와 항목이 담긴 리스트내용들
			//arrDevChek[i]당 CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT가 담김
			//죄다 스트링임
			arrPLChek = devchkl.loadChkList(emp_id);

			System.out.println("DevModel, devChklist메소드 실행, DB로부터 불러오기 정상");

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


		System.out.println("DevModel, devChklist메소드 실행, 불러온 걸로 카테 별 분류시작");

		devTop = new ArrayList<DefaultMutableTreeNode>();
		for(int i = 0 ; i < arrPLCatTitle.size() ; i++){	//갖고온 카테고리의 갯수만큼만 반복문 돌려.

			devTop.add(new DefaultMutableTreeNode(String.valueOf(0)+". "+arrPLCatTitle.get(i)));
			System.out.println("카테고리명 출력"+arrPLCatTitle.get(i));		//참고용. 카테고리명 출력

			for (int j = 0; j < arrPLChek.size() ; j++){	//갖고온 체크리스트의 레코드(1세트) 갯수만큼만 돌려.

				if(   (arrPLCatTitle.get(i)).equals(arrPLChek.get(j).get(1)) != false   ){


					// arrDevChek.get(i)는 i번째 레코드(행)
					// arrDevChek.get(i).get(1)는 해당 레코드(행)의 카테고리이름

					// 갖고온 카테고리 이름과 실제 체크리스트의 카테고리 이름을 비교하여
					// 맞으면 해당 부모노드에 항목을 추가,
					System.out.println("개인내용 출력: "+arrPLChek.get(j).get(3));
					devTop.get(i).add(new DefaultMutableTreeNode(String.valueOf(j)+". "+arrPLChek.get(j).get(3)));
					//항목내용이 카테고리에 삽입		
				}
			}
			empChkRoot.add(devTop.get(i));
		}

		//		if(empChkRoot == null){
		//			System.out.println("empChkRoot은 Null 입니다");
		//		}
		return empChkRoot;
	}


	// 팀 체크리스트 불러와서 view로 넘기기
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

			teamTop.add(new DefaultMutableTreeNode(String.valueOf(0)+". "+arrTeamCatTitle.get(i)));
			System.out.println("팀 카테고리명 출력"+arrTeamCatTitle.get(i));		//참고용. 카테고리명 출력

			for (int j = 0; j < arrTeamChek.size() ; j++){	//갖고온 체크리스트의 레코드(1세트) 갯수만큼만 돌려.

				if(   (arrTeamCatTitle.get(i)).equals(arrTeamChek.get(j).get(1)) != false   ){


					// arrDevChek.get(i)는 i번째 레코드(행)
					// arrDevChek.get(i).get(1)는 해당 레코드(행)의 카테고리이름

					// 갖고온 카테고리 이름과 실제 체크리스트의 카테고리 이름을 비교하여
					// 맞으면 해당 부모노드에 항목을 추가,
					System.out.println("team 내용 출력: "+arrTeamChek.get(j).get(3));
					teamTop.get(i).add(new DefaultMutableTreeNode(String.valueOf(j)+". "+arrTeamChek.get(j).get(3)));
					//항목내용이 카테고리에 삽입		
				}
			}
			teamChkRoot.add(teamTop.get(i));
		}

		//		if(empChkRoot == null){
		//			System.out.println("empChkRoot은 Null 입니다");
		//		}
		return teamChkRoot;
	}

	//	TableModel devTModel, bugTModel;
	//	JTable devTable, bugTable;
	//개발자게시판 코드테이블 갖고와서 예외만 처리하고 바로 DevView로 넘기기
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

	//개발자게시판 버그테이블 갖고와서 예외만 처리하고 바로 DevView로 넘기기
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
