package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import main.MileStone;
import model.PLModel;
import module.ApproveCheckList;
import module.GiveCheckList;
import module.MakeCheckList;
import module.ProgressBarSub;
import record.EmpRecord;
/**
 * PL 이 로그인 한 후에 나오는 화면
 * @author yeji
 *
 */
public class PLView extends JFrame{
	JSplitPane splitPane;
	JTabbedPane tpane;
	EmpRecord rec;
	MakeCheckList makeList;
	GiveCheckList giveList;
	

	JTree tree;
	DefaultMutableTreeNode teamRoot; //팀 전체 체크리스트 트리 받을 놈

	ArrayList<JTextField> arrCatTf;
	ArrayList<JTextField> arrContentTf;


	JButton bugNewgeul, bugRefresh, devPLNewgeul, devPLRefresh,plpmRefresh, plpmNewgeul;

	TableModel devPLTModel, bugTModel, plpmTModel;
	JTable devPLTable, bugTable, plpmTable;
	PLModel plModel;

	JButton bLogout;

	MakeCheckList makeCheckList;
	ApproveCheckList approveList;

	public PLView(EmpRecord rec){
		this.rec = rec;
		try {
			plModel = new PLModel();
			System.out.println("PLModel 연결 성공");
		} catch (Exception e) {
			System.out.println("PLModel 연결 실패 " + e.getMessage());
			e.printStackTrace();
		}
		treeRefresh();
		addLayout();
		eventProc();
	}
	
	/**
	 * //트리 및 체크리스트 삽입 패널 갱신용 
	 * @author Taejun
	 */
	public JScrollPane treeRefresh(){
		
		DefaultMutableTreeNode teamRoot = new DefaultMutableTreeNode();
		//PLModel plModel = new PLModel();
		
		teamRoot = plModel.teamChklist(String.valueOf(rec.getpNum()));
		JScrollPane teamTree = new JScrollPane(tree = new JTree(teamRoot));
		return teamTree;
	}

	void addLayout(){
		//객체생성
		devPLTable = new JTable();
		bugTable = new JTable();
		plpmTable = new JTable();

		devPLTModel = new TableModel();
		bugTModel = new TableModel();
		plpmTModel = new TableModel();

		bLogout = new JButton("로그아웃");

		teamRoot = new DefaultMutableTreeNode();

		//************화면구성************
		// 전체화면 - 왼쪽, 오른

		// 왼쪽상단 - 팀 체크리스트명, 체크리스트 패널
		// 왼쪽상단 UP - 팀 체크리스트명
		// 왼쪽상단 DW - 팀 체크리스트 패널

		// 왼쪽하단 - 프로젝트 진척도

		// 오른상단 - 체크리스트, 개발자게시판, 버그리포트 탭
		// 오른하단 - 체크리스트, 개발자게시판, 버그리포트 탭

		// 왼쪽상단 UP - 팀 체크리스트명
		JPanel p_Left_North_Up = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North_Up.add(new JLabel("안녕하세요 "+String.valueOf(rec.getEmpId())+ " 님, 팀 "+String.valueOf(rec.getpNum())+" 프로젝트로 접속하셨습니다.", SwingConstants.CENTER));
//		p_Left_North_Up.add(new JLabel("팀 "+String.valueOf(rec.getpNum())+"의 프로젝트로 접속하셨습니다.", SwingConstants.CENTER));

		// 왼쪽상단 DW - 팀 체크리스트 패널
		/*JPanel p_Left_North_Down = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North_Down.add(new JLabel("프로젝트 체크리스트 패널칸", SwingConstants.CENTER));
		 */
		// 위에서 만든 트리를 J트리에 심고 스크롤바 붙이기
		JScrollPane teamTree = treeRefresh();


		// 왼쪽상단 붙이기
		JPanel p_Left_North = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North.setLayout(new BorderLayout());
		p_Left_North.add(p_Left_North_Up, BorderLayout.NORTH );
		p_Left_North.add(teamTree, BorderLayout.CENTER );		

		// 왼쪽하단 - 프로젝트 진척도
		JPanel p_Left_South = new JPanel(new BorderLayout());

		TitledBorder center = BorderFactory.createTitledBorder("프로젝트 진행 현황");
		center.setTitleJustification(TitledBorder.CENTER);

		p_Left_South.setBorder(center);

		JPanel pBar = new JPanel();
		//pBar.setLayout(new BorderLayout());

		//JPanel pBar_center = new JPanel();

		//JPanel pBar_scroll = new JPanel();
		//pBar_scroll.add(new ProgressBarSub());
		pBar.add(new ProgressBarSub());
		//JScrollPane scroll = new JScrollPane(pBar);
		//pBar_center.add(scroll);

		//JPanel pBar_south = new JPanel();
		//pBar_south.add(bLogout);

		//pBar.add(pBar_center, BorderLayout.CENTER);
		//pBar.add(pBar_south, BorderLayout.SOUTH);

		p_Left_South.add(pBar, BorderLayout.CENTER);
		p_Left_South.add(bLogout, BorderLayout.SOUTH);

		// 왼쪽상단, 하단 붙이기
		JPanel p_Left = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left.setLayout(new GridLayout(2,1));
		p_Left.add(p_Left_North);
		p_Left.add(p_Left_South);		

		// 오른 - 체크리스트, 개발자게시판, 버그리포트 탭
		JPanel plpmBoard = new JPanel();
		JPanel devBoard = new JPanel();
		JPanel bugBoard = new JPanel();		

		// 오른 - 체크리스트생성 탭
		makeCheckList = new MakeCheckList(this);
		approveList = new ApproveCheckList(this);

		//*******************************************************
		//개발자 PL 게시판 
		devBoard.setLayout(new BorderLayout());
		try {
			System.out.println("pNum " + rec.getpNum() );
			devPLTModel.data = plModel.devPLTable(rec.getpNum());

			//개발자게시판 갖고와서 JTable인 devTable에 붙이기
			devPLTable.setModel(devPLTModel);
			devPLTModel.fireTableDataChanged();

			//위에서 만든 개발자게시판 스크롤바 붙이기
			JScrollPane devTableScr = new JScrollPane(devPLTable);
			devBoard.add(devTableScr, BorderLayout.CENTER);

			//개발자 게시판에 버튼 붙이기 
			JPanel devPLBoard_south = new JPanel();
			devPLBoard_south.setLayout(new FlowLayout());
			devPLBoard_south.add(devPLRefresh = new JButton("새로고침"));
			devPLBoard_south.add(devPLNewgeul = new JButton("글쓰기"));
			devBoard.add(devPLBoard_south, BorderLayout.SOUTH);

			//버그리포트 탭	 
			bugBoard = new JPanel();
			bugBoard.setLayout(new BorderLayout());
			bugTModel.data = plModel.bugTable(rec.getpNum());//버그게시판 갖고와서 JTable인 bugTable에 붙이기
			bugTable.setModel(bugTModel);
			bugTModel.fireTableDataChanged();

			//--------------- ------- 위에서 만든 버그리포트 스크롤바 붙이기
			JScrollPane bugTableScr = new JScrollPane(bugTable);
			bugBoard.add(bugTableScr, BorderLayout.CENTER);

			// 오른 - 버그리포트 글쓰기버튼, 글삭제버튼
			JPanel bugBoard_south = new JPanel();
			bugBoard_south.setLayout(new FlowLayout());
			bugBoard_south.add(bugRefresh = new JButton("새로고침"));
			bugBoard_south.add(bugNewgeul = new JButton("글쓰기"));
			bugBoard.add(bugBoard_south, BorderLayout.SOUTH);

			// 오른 - 업무분담
			JPanel chkList = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
			giveList = new GiveCheckList(this);			
			
			//업무 보고 탭
			plpmBoard.setLayout(new BorderLayout());
			plpmTModel.data = plModel.getPLPMTable(rec.getpNum());
			plpmTable.setModel(plpmTModel);
			plpmTModel.fireTableDataChanged();

			//스크롤
			JScrollPane plpmTableScr = new JScrollPane(plpmTable);
			plpmBoard.add(plpmTableScr, BorderLayout.CENTER);

			//버튼
			JPanel plpmBoard_south = new JPanel();
			plpmBoard_south.setLayout(new FlowLayout());
			plpmBoard_south.add(plpmRefresh = new JButton("새로고침"));
			plpmBoard_south.add(plpmNewgeul = new JButton("글쓰기"));
			plpmBoard.add(plpmBoard_south, BorderLayout.SOUTH);

			tpane = new JTabbedPane();
			tpane.addTab("PM보고", plpmBoard); 
			tpane.addTab("체크리스트 생성", makeCheckList); 
			tpane.addTab("개발자게시판", devBoard);
			tpane.addTab("버그리포트", bugBoard);	
			tpane.addTab("업무분담", giveList);
			tpane.addTab("업무현황설정", approveList);
 
			tpane.setSelectedIndex(0);		

			//왼쪽 오른쪽 붙이기
			splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, p_Left, tpane );
			splitPane.setDividerLocation(350); // 수평으로 쪼갤 위치의 설정 
			add( splitPane ); // 생성된 split창을 프레임에 부착 
		} catch (Exception e) {
			System.out.println("PL 화면 가져오기 실패 " + e.getMessage());
			e.printStackTrace();
		} 
		//시간 가져오기 
		//Calendar cal = Calendar.getInstance();


		//프레임 설정 
		setTitle("milestone" + 
				"                                                                        " +
				"                                                                        " 
				+ "                                      " +  rec.getEmpId() + "  " 
				+ LocalDateTime.now().getHour() + " : " + LocalDateTime.now().getMinute());
		setVisible(true);
		setSize(1300,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 이벤트 등록
	 */
	void eventProc(){

		//버튼리스너 등록
		ButtonEventHandler btnHandler = new ButtonEventHandler();		
		bugNewgeul.addActionListener(btnHandler);	
		bugRefresh.addActionListener(btnHandler);
		devPLNewgeul.addActionListener(btnHandler);		
		devPLRefresh.addActionListener(btnHandler);
		plpmRefresh.addActionListener(btnHandler);
		plpmNewgeul.addActionListener(btnHandler);

		bLogout.addActionListener(btnHandler);

		//트리선택 리스너 등록
		//		TreeEventHandler tevHandler = new TreeEventHandler();
		//		tree.addTreeboardListener(tevHandler);

		//개발자테이블 리스너 등록
		devPLTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = "";
				int row = devPLTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.

				String selectedName = (String)devPLTable.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 
				//devTnum = (Integer)devTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면

				//new PostWithCheckBox(rec, null);

				//게시번호 갖고 오기
				String postNum = (String)devPLTable.getValueAt(row, 0); 
				//작성자 갖고 오기
				String postEmpId = (String)devPLTable.getValueAt(row, 3); 
				//게시물 제목 갖고 오기
				String postTitle = (String)devPLTable.getValueAt(row, 2);
				String postStat = (String)devPLTable.getValueAt(row, 1); //PL 의 공지사항 을 위해서 값 가져옴
				if(postStat.equals("공지사항"))
					type = "공지사항";
				else type = "코드";

				jump2SelectedPost(type, postNum, postEmpId, postTitle);
			}
		});

		//버그테이블 리스너 등록
		bugTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = "";
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
				String postStat = (String)devPLTable.getValueAt(row, 1); //PL 의 공지사항 을 위해서 값 가져옴

				if(postStat.equals("공지사항"))
					type = "공지사항";
				else type = "버그";

				jump2SelectedPost(type, postNum, postEmpId, postTitle);
			}
		});

		//업무 테이블 
		plpmTable.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = plpmTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.

				String selectedNum = (String)plpmTable.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 
				//int bugTnum = (Integer)bugTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면

				//게시번호 갖고 오기
				String postNum = (String)plpmTable.getValueAt(row, 0); 
				//작성자 갖고 오기
				String postEmpId = (String)plpmTable.getValueAt(row, 3); 
				//게시물 제목 갖고 오기
				String postTitle = (String)plpmTable.getValueAt(row, 2);

				jump2SelectedPost("업무", postNum, postEmpId, postTitle);
			}
		});

	}
	/**
	 * 버그 혹은 코드 글쓰기 게시판에서 리스트 눌렀을 시 새창 띄우면서 글 불러오는 메소드
	 * 작성자 아이디로  업무 가져오기 , 업무 = PL or PM 이면 SelectedPost, 업무 = 개발자 면 ApproveRejectWindow
	 * @param board 글 종류 (코드, 버그)
	 * @param postNum 글 번호
	 * @param postEmpId 작성자 아이디
	 * @param postTitle 글 제목 
	 * @author yeji
	 */
	void jump2SelectedPost(String board, String postNum, String postEmpId, String postTitle){//버그인지 코드인지 board으로 입력 받음
		Object ref = (Object)this;
		//작성자 아이디로  업무 가져오기 , 업무 = PL or PM 이면 SelectedPost, 업무 = 개발자 면 ApproveRejectWindow
		try{
			String tmp = plModel.getJob(postEmpId); 
			System.out.println("작성자 업무 가져오기 성공");

			if(tmp.equals("PL") || tmp.equals("PM")){
				System.out.println("PLView board: " + board);
				new SelectedPost(rec, ref, board, postNum, postEmpId, postTitle);
				searchBoard();
			}else if(tmp.equals("개발자")){
				new ApproveRejectWindow(rec, ref, board ,postNum ,postEmpId ,postTitle);
				searchBoard(); //업데이트
			}
		}catch(Exception e){
			System.out.println("작성자 업무 가져오기 실패 ");
			e.printStackTrace();
		}
	}
	//버그 혹은 코드 글쓰기 새창 띄우는 거.
	void jump2NewPostWindow(String board){//버그인지 코드인지 board으로 입력 받음
		Object ref = (Object)this;
		new PostWindow(rec, ref, board);
	}

	/**
	 * 버그, 개발자 게시판, 업무게시판 불러오기 or 갱신
	 */
	void searchBoard(){
		try{
			devPLTModel.data = plModel.devPLTable(rec.getpNum()); //개발자게시판 갖고와서 JTable인 devTable에 붙이기
			devPLTable.setModel(devPLTModel);
			devPLTModel.fireTableDataChanged();

			bugTModel.data = plModel.bugTable(rec.getpNum());//버그게시판 갖고와서 JTable인 bugTable에 붙이기
			bugTable.setModel(bugTModel);
			bugTModel.fireTableDataChanged();

			plpmTModel.data = plModel.getPLPMTable(rec.getpNum());
			plpmTable.setModel(plpmTModel);
			plpmTModel.fireTableDataChanged();

		}catch(Exception e){
			System.out.println("게시판 불러오기 or 갱신 실패 " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *	사원 레코드 자식 클래스한테 넘겨주기 (현재 PL 이 누구인지 확인하기위해서)
	 */
	public EmpRecord getRecord(){
		return rec;
	}

	void logOut(){
		new MileStone();
		dispose();
	}	

	/**
	 * 화면에 테이블 붙이는 메소드 
	 * @author taejun
	 *
	 */
	class TableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"게시번호", "상태", "제목", "작성자", "작성시간"};

		public int getColumnCount() { 
			return columnNames.length; 
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

	// 버튼 이벤트 핸들러 만들기
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();

			if(o == bugNewgeul || o == devPLNewgeul ){						//버그리포트 새글
				jump2NewPostWindow("공지사항");
			}/*else  if(o == devPLNewgeul){				//개발자게시판 새글
				jump2NewPostWindow("코드");
			}*/else if(o == bugRefresh){				//버그게시판 새로고침
				searchBoard();
			}else if(o == devPLRefresh){				//개발자게시판 새로고침
				searchBoard();
			}else if(o == plpmNewgeul){					//업무보고 새글
				jump2NewPostWindow("업무보고");
			}else if(o == plpmRefresh){					//업무보고 새로고침
				searchBoard();
			}else if(o == bLogout){
				System.out.println("로그아웃");
				logOut();
			}
		}
	}
}
