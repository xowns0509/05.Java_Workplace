package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import view.ChartView;			// 차트
import record.CheckListRecord;	// 체크리스트 (항목, 카테고리등)의 정보를 담는 클래스
import record.PostRecord;		// 게시물의 정보를 담는 클래스
import module.BugReportBoard;	// 버그리포트 게시판
import module.CheckList;		// 체크리스트 생성
import module.PLDevBoard;
import main.Database;

public class DevModel {
	
	Connection con = null; //db연결 테스트용

//	TableModel devTModel, bugTModel;
//	JTable devTable, bugTable;
	
	BugReportBoard bugBoard;
	PLDevBoard pldBoard;
	
	PostRecord p_rec;
	
	CheckList devchkl, teamchkl;
	
	ArrayList<DefaultMutableTreeNode> devTop, teamTop;
	ArrayList<DefaultMutableTreeNode> devNode, teamNode;
	
	ArrayList<ArrayList<String>> arrDevChek, arrTeamChek;
	ArrayList<String> arrDevCatTitle, arrTeamCatTitle;
	
	// 칸 당 arrCtent 한 줄을 담을 ARList
	
	public DevModel(){
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


	// 개발자 체크리스트 불러와서 view에 넘기기
	public DefaultMutableTreeNode devChklist(String emp_id){
		
		System.out.println("DevModel, devChklist메소드 실행");
		DefaultMutableTreeNode empChkRoot = new DefaultMutableTreeNode(emp_id + "의 체크리스트 입니다");
		
		try{
			
			arrDevChek = new ArrayList<ArrayList<String>>();
			arrDevCatTitle = new ArrayList<String>();
			devchkl = new CheckList();
			//emp_id에 할당된 카테고리 이름들을 먼저 불러오고
			//그걸 기준으로 항목들을 카테고리들로 분류하려 한다.
			//arrDevCatTitle[0], arrDevCatTitle[1], arrDevCatTitle[2]....
			//스트링임
			arrDevCatTitle = devchkl.loadCatTitle(emp_id);
			
			//실제 카테고리와 항목이 담긴 리스트내용들
			//arrDevChek[i]당 CAT_NUM, CAT_TITLE, LIST_NUM, CONTENT, LIST_STAT가 담김
			//죄다 스트링임
			arrDevChek = devchkl.loadChkList(emp_id);
			
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
		for(int i = 0 ; i < arrDevCatTitle.size() ; i++){	//갖고온 카테고리의 갯수만큼만 반복문 돌려.

			devTop.add(new DefaultMutableTreeNode(String.valueOf(0)+". "+arrDevCatTitle.get(i)));
			System.out.println("카테고리명 출력"+arrDevCatTitle.get(i));		//참고용. 카테고리명 출력

			for (int j = 0; j < arrDevChek.size() ; j++){	//갖고온 체크리스트의 레코드(1세트) 갯수만큼만 돌려.
				
				if(   (arrDevCatTitle.get(i)).equals(arrDevChek.get(j).get(1)) != false   ){
					
					
					// arrDevChek.get(i)는 i번째 레코드(행)
					// arrDevChek.get(i).get(1)는 해당 레코드(행)의 카테고리이름

					// 갖고온 카테고리 이름과 실제 체크리스트의 카테고리 이름을 비교하여
					// 맞으면 해당 부모노드에 항목을 추가,
					System.out.println("개인내용 출력: "+arrDevChek.get(j).get(3));
					devTop.get(i).add(new DefaultMutableTreeNode(String.valueOf(j)+". "+arrDevChek.get(j).get(3)));
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
