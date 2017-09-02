package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import main.MileStone;
import model.DevModel;
import module.CheckList;
import module.ProgressBarSub;
import record.EmpRecord;

public class DevView2 extends JFrame
{
	EmpRecord rec;	//사원정보
	
	JTree tree;
	DefaultMutableTreeNode devRoot; //개발자 개인 체크리스트 트리 받을 놈
	DefaultMutableTreeNode teamRoot; //팀 전체 체크리스트 트리 받을 놈
	
	ArrayList<JTextField> arrCatTf;
	ArrayList<JTextField> arrContentTf;
	
	JButton bugNewgeul, bugRefresh, devNewgeul, devRefresh;
	
	JSplitPane splitPane;
	JTabbedPane tpane;
	
	TableModel devTModel, bugTModel;
	JTable devTable; 					//개발자 게시판
	JTable bugTable;					//버그 게시판
	
	DevModel devModel;

	JButton bLogout;
	
	//개발자가 선택한 체크리스트 목록
	ArrayList<ArrayList<String>> listForDevChkLARLT = new ArrayList<ArrayList<String>>();
	ArrayList<String> listForDevChkLARL;			//0번: 항목고유번호번호, 1번째: 항목내용???
	
	//GUI로 안보이는 ARL - 프로그램 실행시 팀 내 체크리스트의 정보를 담고 있음.
	ArrayList<ArrayList<String>> chkLARLT = new ArrayList<ArrayList<String>>();
	ArrayList<String> chkLARL;					//GUI로 안보이는 어레이리스트
	
	DevChkLTableModel devChkLTableModel; 
	JTable devChkLTable;					//체크리스트 테이블
	
	//생성자
	public DevView2(EmpRecord rec){
		this.rec = rec;
		devModel = new DevModel(this);
		
		addLayout();
		eventProc();
	}

	
	// 이벤트 등록
	public void eventProc(){
		
		//버튼리스너 등록
		ButtonEventHandler btnHandler = new ButtonEventHandler();		
		bugNewgeul.addActionListener(btnHandler);	
		bugRefresh.addActionListener(btnHandler);
		devNewgeul.addActionListener(btnHandler);		
		devRefresh.addActionListener(btnHandler);
				
		bLogout.addActionListener(btnHandler);
		
		//트리선택 리스너 등록
		TreeEventHandler tevHandler = new TreeEventHandler();
		tree.addTreeSelectionListener(tevHandler);
		
		//개발자 ChkTable 리스너 등록
		devChkLTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = devChkLTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				//선택한 체크리스트의 항목고유번호 갖고 오기
				String listNum = (String)devChkLTable.getValueAt(row, col); //테이블 넘버가 String이라면 
				System.out.println("누른 번호: "+ listNum);
				//devTnum = (Integer)devTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				
				// 선택한 항목, 테이블에서 지우기
				delChkListFromDevChkLTable(listNum);
				refreshSelectedChkLTable();
			}
		});
		
		//개발자테이블 리스너 등록
		devTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = devTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				String selectedName = (String)devTable.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 
				//devTnum = (Integer)devTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				
				//new PostWithCheckBox(rec, null);
				
				//게시번호 갖고 오기
				String postNum = (String)devTable.getValueAt(row, 0); 
				//작성자 갖고 오기
				String postEmpId = (String)devTable.getValueAt(row, 3); 
				//게시물 제목 갖고 오기
				String postTitle = (String)devTable.getValueAt(row, 2);
				
				jump2SelectedPost("코드", postNum, postEmpId, postTitle);
			}
		});
		
		//버그테이블 리스너 등록
		bugTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = bugTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				String selectedNum = (String)bugTable.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 
				//int bugTnum = (Integer)bugTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				
				//게시번호 갖고 오기
				String postNum = (String)bugTable.getValueAt(row, 0); 
				//작성자 갖고 오기
				String postEmpId = (String)bugTable.getValueAt(row, 3); 
				//게시물 제목 갖고 오기
				String postTitle = (String)bugTable.getValueAt(row, 2);
				
				jump2SelectedPost("버그", postNum, postEmpId, postTitle);
			}
		});

	}
	
	/**
	 * 선택한 체크리스트 테이블에서 지우기
	 * @author TaeJoon
	 */
	void delChkListFromDevChkLTable(String listNum){
		
		//선택한 항목이 이미 테이블에 있는지 확인
		for(int i = 0 ; i < listForDevChkLARLT.size() ; i++){
			
			if (listForDevChkLARLT.get(i).get(0).equals(listNum)){
				listForDevChkLARLT.remove(i);
			}
		}
	}
	
	/**
	 * 테이블 갱신용
	 */
	void refreshSelectedChkLTable(){
		devChkLTableModel.data = listForDevChkLARLT;
		devChkLTable.setModel(devChkLTableModel);
		devChkLTableModel.fireTableDataChanged();
		
	}
	
	/**
	 * 버그 혹은 코드 글쓰기 게시판에서 리스트 눌렀을 시 새창 띄우면서 글 불러오는 거.
	 * @param board
	 * @param postNum
	 * @param postEmpId
	 * @param postTitle
	 */
	void jump2SelectedPost(String board, String postNum, String postEmpId, String postTitle){//버그인지 코드인지 board으로 입력 받음
		Object ref = (Object)this;
		new SelectedPost(rec, ref, board, postNum, postEmpId, postTitle);
	}
	void logOut(){
		new MileStone();
		dispose();
	}
	
	// 버튼 이벤트 핸들러 만들기
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			
			if(o==bugNewgeul){					//버그리포트 새글
				jump2NewPostWindow("버그");
			}else  if(o==devNewgeul){				//개발자게시판 새글
				jump2NewPostWindow("코드");
			}else if(o==bugRefresh){				//버그게시판 새로고침
				searchBoard();
			}else if(o==devRefresh){				//개발자게시판 새로고침
				searchBoard();
			}else if(o==bLogout){				//개발자게시판 새로고침
				logOut();
			}
		}
	}
		
	//버그 혹은 코드 글쓰기 새창 띄우는 거.
	void jump2NewPostWindow(String board){//버그인지 코드인지 board으로 입력 받음
		new PostWindow(rec, this, board);
	}
	
	//버그 및 개발자게시판 테이블 불러오기 or 갱신
	void searchBoard(){
		devTModel.data = devModel.devTable(rec.getpNum()); //개발자게시판 갖고와서 JTable인 devTable에 붙이기
		devTable.setModel(devTModel);
		devTModel.fireTableDataChanged();
		
		bugTModel.data = devModel.bugTable(rec.getpNum());//버그게시판 갖고와서 JTable인 bugTable에 붙이기
		bugTable.setModel(bugTModel);
		bugTModel.fireTableDataChanged();
	}

	//화면구성
	public void addLayout(){
		
		devTable = new JTable(); //개발자 게시판 받을 놈
		bugTable = new JTable(); //버그 게시판 받을 놈
		
		devTModel = new TableModel();
		bugTModel = new TableModel();
		
		teamRoot = new DefaultMutableTreeNode();
		devRoot = new DefaultMutableTreeNode();
		
		bLogout = new JButton("로그아웃");
		System.out.println("엄태준여기5");
		
		setTitle("개발자 화면입니다.");	//Frame 타이틀 이름 주기 
		setSize(1200,750);			//Frame 크기 설정 
		setVisible(true);			//생성한 Frame 윈도우에 뿌리기

		//************화면구성************
		// 전체화면 - 왼쪽, 오른

		// 왼쪽상단 - 팀 체크리스트명, 체크리스트 패널
		// 왼쪽상단 UP - 팀 체크리스트명
		// 왼쪽상단 DW - 팀 체크리스트 패널

		// 왼쪽하단 - 프로젝트 진척도

		// 오른상단 - 체크리스트, 개발자게시판, 버그리포트 탭
		// 오른하단 - 새로고침, 글쓰기 버튼

		// 체크리스트 탭  그리드
			// 왼쪽 - 개인 체크리스트
			
			// 중앙상단 - 개인 승인대기  체크리스트
			// 중앙미들 - 테이블
			// 중앙하단 - 개인 보류 체크리스트
	
			// 오른 - 개인 체크리스트 완료된거

		// 왼쪽상단 UP - 팀 체크리스트명
		JPanel p_Left_North_Up = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North_Up.add(new JLabel("프로젝트 체크리스트명 칸", SwingConstants.CENTER));

		// 왼쪽상단 DW - 팀 체크리스트 패널
		// 체크리스트 불러오기
		teamRoot = devModel.teamChklist(String.valueOf(rec.getpNum()));
		// 위에서 만든 트리를 J트리에 심고 스크롤바 붙이기
		JScrollPane teamTree = new JScrollPane(tree = new JTree(teamRoot));
		TitledBorder a = new TitledBorder("팀 "+ rec.getpNum()+"의 체크리스트");
		teamTree.setBorder(a);
		
		// 왼쪽상단 붙이기
		JPanel p_Left_North = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North.setLayout(new BorderLayout());
		p_Left_North.add(p_Left_North_Up, BorderLayout.NORTH );
		p_Left_North.add(teamTree, BorderLayout.CENTER );


		// 왼쪽하단 - 프로젝트 진척도
		JPanel p_Left_South = new JPanel(new BorderLayout());//패널 명은 더미용이며 추후 변경될 수 있음
		//p_Left_South.add(new JLabel("프로젝트 진척도 BAR칸", SwingConstants.CENTER));
		
		TitledBorder center = BorderFactory.createTitledBorder("프로젝트 진행 현황");
		center.setTitleJustification(TitledBorder.CENTER);
		
		p_Left_South.setBorder(center);
		
		JPanel pBar = new JPanel();
		//pBar.setLayout(new BorderLayout());
		
		pBar.add(new ProgressBarSub());
		
		p_Left_South.add(pBar, BorderLayout.CENTER);
		p_Left_South.add(bLogout, BorderLayout.SOUTH);
		
		// 왼쪽상단, 하단 붙이기
		JPanel p_Left = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left.setLayout(new GridLayout(2,1));
		p_Left.add(p_Left_North);
		p_Left.add(p_Left_South);		



		// 오른 - 체크리스트, 개발자게시판, 버그리포트 탭
		// 오른 - 체크리스트 트리, 카테고리와 항목명 만들어서 쪼인.
		
		// 체크리스트 탭  그리드
		// 왼쪽 - 개인 체크리스트
		
		// 중앙
		// 중앙상단 - 개인 승인대기  체크리스트
		// 중앙미들 - 테이블
		// 중앙하단 - 개인 보류 체크리스트

		// 오른 - 개인 체크리스트 완료된거
		
		// 체크리스트 탭 그리드
		JPanel p_Chk_Center = new JPanel(new GridLayout(1,3));
		JPanel p_Chk_South = new JPanel();
				
		// 체크리스트 탭  그리드
		// 왼쪽 - 개인 체크리스트
		JPanel p_Chk_Center_Left = new JPanel();

		// 중앙
		JPanel p_Chk_Center_Middle = new JPanel(new GridLayout(3,1));

		// 중앙상단 - 개인 승인대기  체크리스트
		JPanel p_Chk_Center_Middle_Row1 = new JPanel();

		// 중앙미들 - 테이블
		JPanel p_Chk_Center_Middle_Row2 = new JPanel();
		DevChkLTableModel devChkLTableModel; 
		JTable devChkLTable;
		
		
		// 중앙하단 - 개인 보류 체크리스트
		JPanel p_Chk_Center_Middle_Row3 = new JPanel();
		
		// 오른 - 개인 체크리스트 완료된거
		JPanel p_Chk_Center_Right = new JPanel();
		
		
		
		
		devRoot = devModel.devChklist(rec.getEmpId());		//체크리스트 불러오기
		
		//--------------- ------- 위에서 만든 트리를 J트리에 심고 스크롤바 붙이기
		JScrollPane devTree = new JScrollPane(tree = new JTree(devRoot));
		
		
		// 오른 - 개발자게시판
		JPanel devBoard = new JPanel();
		devBoard.setLayout(new BorderLayout());
		devTModel.data = devModel.devTable(rec.getpNum()); //개발자게시판 갖고와서 JTable인 devTable에 붙이기
		devTable.setModel(devTModel);
		devTModel.fireTableDataChanged();
		
		//위에서 만든 개발자게시판 스크롤바 붙이기
		JScrollPane devTableScr = new JScrollPane(devTable);
		devBoard.add(devTableScr, BorderLayout.CENTER);

		
		// 오른 - 개발자게시판 글쓰기버튼, 글삭제버튼
		JPanel devBoard_South = new JPanel();
		devBoard_South.setLayout(new FlowLayout());
		devBoard_South.add(devRefresh = new JButton("새로고침"));
		devBoard_South.add(devNewgeul = new JButton("글쓰기"));
		devBoard.add(devBoard_South, BorderLayout.SOUTH);
		
		
		// 오른 - 버그리포트 탭
		JPanel bugBoard = new JPanel();
		bugBoard.setLayout(new BorderLayout());
		bugTModel.data = devModel.bugTable(rec.getpNum());//버그게시판 갖고와서 JTable인 bugTable에 붙이기
		bugTable.setModel(bugTModel);
		bugTModel.fireTableDataChanged();
		
		//--------------- ------- 위에서 만든 버그리포트 스크롤바 붙이기
		JScrollPane bugTableScr = new JScrollPane(bugTable);
		bugBoard.add(bugTableScr, BorderLayout.CENTER);
		
		
		// 오른 - 버그리포트 글쓰기버튼, 글삭제버튼
		JPanel bugBoard_South = new JPanel();
		bugBoard_South.setLayout(new FlowLayout());
		bugBoard_South.add(bugRefresh = new JButton("새로고침"));
		bugBoard_South.add(bugNewgeul = new JButton("글쓰기"));
		//bugBoard_South.add(bugDelgeul = new JButton("삭제"));
		bugBoard.add(bugBoard_South, BorderLayout.SOUTH);
		
		//오른 - 모든 탭 붙이기
		tpane = new JTabbedPane();
		tpane.addTab("체크리스트", devTree);
		tpane.addTab("개발자게시판", devBoard);
		tpane.addTab("버그리포트", bugBoard);
		tpane.setSelectedIndex(0);

		//통합 - 왼쪽 오른쪽 붙이기
		splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, p_Left, tpane );
		splitPane.setDividerLocation(300); // 수평으로 쪼갤 위치의 설정 
		add( splitPane ); // 생성된 split창을 프레임에 부착 
		// JPanel은 경량 컨터이너이므로 getContentPane()이 불필요 
		//getContentPane().add(splitPane, BorderLayout.CENTER ); 	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
	}
	
	/**
	 * 승인대기, 보류, 작업중의 상태인 체크리스트들 담는 어레이리스트 5열짜리
	 * 메모리 상에만 저장해 놓고 GUI로 뿌리지 않음
	 * "항목고유번호", "카테고리", "항목내용", "담당자", "상태"
	 * @author TaeJoon
	 *
	 */
	void refreshDevChkLStatTable(){

		System.out.println("refreshDevChkLStatTable 메소드 실행");

		chkLARLT = devModel.devChkLStatTable();

		System.out.println("chkLARLT의 크기: " + chkLARLT.size());

		for(int i = 0; i <  chkLARLT.size() ; i++){
			System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(0));
			System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(1));
			System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(2));
			System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(3));
			System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(4));
		}

		System.out.println("개발자 체크리스트 상태테이블 가져오기 성공");

	}
	
	/**
	 *	사원 레코드 자식 클래스한테 넘겨주기 (현재 PL 이 누구인지 확인하기위해서)
	 */
	public EmpRecord getRecord(){
		return rec;
	}
	
	class TreeEventHandler implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			if (node == null)
				// Nothing is selected.
				return;

			Object nodeInfo = node.getUserObject();
			
			if (node.isLeaf()) {

				//트리에서 선택한 노드가 테이블에 이미 있는지 확인
				for(int i = 0; i < listForDevChkLARLT.size(); i++){

					if(listForDevChkLARLT.get(i).get(1).equals(node.getParent().toString().substring(node.getParent().toString().indexOf(". ")+2)) == true & 
							listForDevChkLARLT.get(i).get(2).equals(nodeInfo.toString()) == true){
						//catListARLT.get(i).get(1) i번째 행의 카테고리명
						//catListARLT.get(i).get(2) i번째 행의 항목내용

						//카테고리명과 항목내용을 한꺼번에 비교하여 이에 해당한 경우 삽입 방지

						JOptionPane.showMessageDialog(null, "항목의 중복선택은 있을 수 없어");
						return;
					}
				}

				//트리에서 선택한 노드정보로 이전에 갖고온 테이블에서 확인
				for(int i = 0; i < chkLARLT.size(); i++){
					if(   chkLARLT.get(i).get(1).equals(node.getParent().toString().substring(node.getParent().toString().indexOf(". ")+2)) == true &
							chkLARLT.get(i).get(2).equals(nodeInfo.toString()) == true ){
						//listForDevChkLARLT.get(i).get(1) 카테고리명
						//listForDevChkLARLT.get(i).get(2) 항목명을 동시에 비교

						listForDevChkLARL = null;
						listForDevChkLARL = new ArrayList<String>();

						//트리에서 선택한 정보를 기반으로 chkLARLT내 카테고리와 항목내용, 상태를 listForDevChkLARL에 저장
						listForDevChkLARL.add(chkLARLT.get(i).get(0)); // 리스트 고유번호
						listForDevChkLARL.add(chkLARLT.get(i).get(1)); // 카테고리명
						listForDevChkLARL.add(chkLARLT.get(i).get(2)); // 항목명
						listForDevChkLARL.add(chkLARLT.get(i).get(4)); // 상태확인

						//위의 두 내용을 한 행에 담아 줄 ARLT에 저장
						listForDevChkLARLT.add(listForDevChkLARL);

					}
				}

				//참고출력용
				System.out.println("THE ROOT NODE IS: "+node.getParent().toString());
				System.out.println(" CHILD NODE IS: "+nodeInfo.toString());

				// 삭제예정 체크리스트 테이블 갱신
				refreshDevChkLStatTable();
			}

		}
	}
	
	/**
	 * 개발자전용 체크리스트 테이블
	 * @author TaeJoon
	 */
	class DevChkLTableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"항목고유번호", "카테고리", "항목내용"};

		//=============================================================
		// 1. 기본적인 TabelModel  만들기
		// 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
		// AbstractTabelModel에서 구현되지 않았기에...
		// 반드시 사용자 구현 필수!!!!

		public int getColumnCount() { 
			return columnNames.length; //{"게시번호", "상태", "제목", "작성자", "작성시간"}
			//길이 반환. 저위에 작성했던 컬럼수 만큼. 현재는 5
		} 

		public int getRowCount() { 
			return data.size(); //어레이리스트 길이 반환.
		} 

		public Object getValueAt(int row, int col) { 
			ArrayList temp = (ArrayList)data.get( row );
			return temp.get( col );
		}

		public String getColumnName(int col){
			return columnNames[col];
		}
	}
	
	/**
	 * 버그 및 개발자게시판 테이블
	 * @author TaeJoon
	 */
	class TableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"게시번호", "상태", "제목", "작성자", "작성시간"};

		//=============================================================
		// 1. 기본적인 TabelModel  만들기
		// 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
		// AbstractTabelModel에서 구현되지 않았기에...
		// 반드시 사용자 구현 필수!!!!

		public int getColumnCount() { 
			return columnNames.length; //{"게시번호", "상태", "제목", "작성자", "작성시간"}
			//길이 반환. 저위에 작성했던 컬럼수 만큼. 현재는 5
		} 

		public int getRowCount() { 
			return data.size(); //어레이리스트 길이 반환.
		} 

		public Object getValueAt(int row, int col) { 
			ArrayList temp = (ArrayList)data.get( row );
			return temp.get( col );
		}

		public String getColumnName(int col){
			return columnNames[col];
		}
	}
}