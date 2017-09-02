package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import model.DevModel;
import record.EmpRecord;

public class DevView extends JFrame
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

	
	//생성자
	public DevView(EmpRecord rec){
		this.rec = rec;
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
				
		//트리선택 리스너 등록
//		TreeEventHandler tevHandler = new TreeEventHandler();
//		tree.addTreeboardListener(tevHandler);

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
	
	//버그 혹은 코드 글쓰기 게시판에서 리스트 눌렀을 시 새창 띄우면서 글 불러오는 거.
	void jump2SelectedPost(String board, String postNum, String postEmpId, String postTitle){//버그인지 코드인지 board으로 입력 받음
		Object ref = (Object)this;
		new SelectedPost(rec, ref, board, postNum, postEmpId, postTitle);
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
		
		devModel = new DevModel();
		
		devTModel = new TableModel();
		bugTModel = new TableModel();
		
		teamRoot = new DefaultMutableTreeNode();
		devRoot = new DefaultMutableTreeNode();
		
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
		// 오른하단 - 체크리스트, 개발자게시판, 버그리포트 탭


		// 왼쪽상단 UP - 팀 체크리스트명
		JPanel p_Left_North_Up = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North_Up.add(new JLabel("프로젝트 체크리스트명 칸", SwingConstants.CENTER));

		// 왼쪽상단 DW - 팀 체크리스트 패널
		// 체크리스트 불러오기
		teamRoot = devModel.teamChklist("1");
		// 위에서 만든 트리를 J트리에 심고 스크롤바 붙이기
		JScrollPane teamTree = new JScrollPane(tree = new JTree(teamRoot));

		
		// 왼쪽상단 붙이기
		JPanel p_Left_North = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North.setLayout(new BorderLayout());
		p_Left_North.add(p_Left_North_Up, BorderLayout.NORTH );
		p_Left_North.add(teamTree, BorderLayout.CENTER );


		// 왼쪽하단 - 프로젝트 진척도
		JPanel p_Left_South = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_South.add(new JLabel("프로젝트 진척도 BAR칸", SwingConstants.CENTER));


		// 왼쪽상단, 하단 붙이기
		JPanel p_Left = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left.setLayout(new GridLayout(2,1));
		p_Left.add(p_Left_North);
		p_Left.add(p_Left_South);		



		// 오른 - 체크리스트, 개발자게시판, 버그리포트 탭
		
		// 오른 - 체크리스트 트리, 카테고리와 항목명 만들어서 쪼인.
		devRoot = devModel.devChklist("xowns");		//체크리스트 불러오기
		
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
	
	//화면에 테이블 붙이는 메소드 
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


////트리선택 리스너
//class TreeEventHandler implements TreeboardListener{
//	@Override
//	public void valueChanged(TreeboardEvent e) {
//		//Returns the last path element of the board.
//		//This method is useful only when the board model allows a single board.
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
//				tree.getLastSelectedPathComponent();
//
//		if (node == null)
//			return;
//
//		Object nodeInfo = node.getUserObject();
//		if (node.isLeaf()) {
//			BookInfo book = (BookInfo)nodeInfo;
//			displayURL(book.bookURL);
//		} else {
//			displayURL(helpURL); 
//		}
//	}
//}