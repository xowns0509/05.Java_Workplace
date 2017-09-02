package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
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
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import model.DevModel;
import record.EmpRecord;

public class DevView extends JFrame
{
	EmpRecord rec;
	JTree tree;
	DefaultMutableTreeNode root;
	DefaultMutableTreeNode top1, top2;

	ArrayList<DefaultMutableTreeNode> top;
	ArrayList<DefaultMutableTreeNode> node;
	
	DefaultMutableTreeNode node1, node2, node3;
	
	JButton bugNewgeul, bugDelgeul, devNewgeul, devDelgeul;
	
	JSplitPane splitPane;
	JTabbedPane tpane;
	
	TableModel devTModel, bugTModel;
	JTable devTable;
	JTable bugTable;
	
	DevModel devModel;

	public DevView(EmpRecord rec){
		this.rec = rec;
		addLayout();
		eventProc();
		dbTest();
		
	}
	
	// 이벤트 등록
	public void eventProc(){
		
		//버튼리스너 등록
		ButtonEventHandler btnHandler = new ButtonEventHandler();		
		bugNewgeul.addActionListener(btnHandler);
		bugDelgeul.addActionListener(btnHandler);
		devNewgeul.addActionListener(btnHandler);
		devDelgeul.addActionListener(btnHandler);
				
		//트리선택 리스너 등록
//		TreeEventHandler tevHandler = new TreeEventHandler();
//		tree.addTreeSelectionListener(tevHandler);

		//테이블리스너 등록
		devTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = devTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				//selectedName = (String)devTable.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 
				int devTnum = (Integer)devTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				
				System.out.println(devTnum);
				
			}
		});
		
		bugTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = bugTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				//selectedName = (String)bugTable.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 
				int bugTnum = (Integer)bugTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				
				System.out.println(bugTnum);
				
			}
		});

	}
	
	//모델 DB연결테스트
	public void dbTest(){
		

		
		try{
			devModel = new DevModel();
			
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
	
	

	
//	//트리선택 리스너
//	class TreeEventHandler implements TreeSelectionListener{
//		@Override
//		public void valueChanged(TreeSelectionEvent e) {
//			//Returns the last path element of the selection.
//			//This method is useful only when the selection model allows a single selection.
//			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
//					tree.getLastSelectedPathComponent();
//
//			if (node == null)
//				return;
//
//			Object nodeInfo = node.getUserObject();
//			if (node.isLeaf()) {
//				BookInfo book = (BookInfo)nodeInfo;
//				displayURL(book.bookURL);
//			} else {
//				displayURL(helpURL); 
//			}
//		}
//	}

	// 버튼 이벤트 핸들러 만들기
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			
			if(o==bugNewgeul){				//버그리포트 새글
				JOptionPane.showMessageDialog(null, "버그리포트 새글");
				//JOptionPane.showConfirmDialog(null, "버그리포트 새글");

			}
			else if(o==bugDelgeul){				//버그리포트 글삭제
				JOptionPane.showMessageDialog(null, "버그리포트 글삭제");

			}			
			else  if(o==devNewgeul){				//개발자게시판 새글
				JOptionPane.showMessageDialog(null, "개발자게시판 새글");

			}
			else if(o==devDelgeul){				//개발자게시판 글삭제
				JOptionPane.showMessageDialog(null, "개발자게시판 글삭제");

			}
		}
	}


	//화면구성
	public void addLayout(){
		
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
		JPanel p_Left_North_Down = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North_Down.add(new JLabel("프로젝트 체크리스트 패널칸", SwingConstants.CENTER));

		// 왼쪽상단 붙이기
		JPanel p_Left_North = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North.setLayout(new BorderLayout());
		p_Left_North.add(p_Left_North_Up, BorderLayout.NORTH );
		p_Left_North.add(p_Left_North_Down, BorderLayout.CENTER );



		// 왼쪽하단 - 프로젝트 진척도
		JPanel p_Left_South = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_South.add(new JLabel("프로젝트 진척도 BAR칸", SwingConstants.CENTER));


		// 왼쪽상단, 하단 붙이기
		JPanel p_Left = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left.setLayout(new GridLayout(2,1));
		p_Left.add(p_Left_North);
		p_Left.add(p_Left_South);		

		
		

		// 오른 - 체크리스트, 개발자게시판, 버그리포트 탭
		
		// 오른 - 개발자게시판
		JPanel devBoard = new JPanel();
		devBoard.setLayout(new BorderLayout());
		devTModel = new TableModel();//개발자게시판 리스트 만들기
		devTable = new JTable(devTModel);
		
		//위에서 만든 개발자게시판 스크롤바 붙이기
		JScrollPane devTableScr = new JScrollPane(devTable);
		devBoard.add(devTableScr, BorderLayout.CENTER);
		
		
		// 오른 - 개발자게시판 글쓰기버튼, 글삭제버튼
		JPanel devBoard_South = new JPanel();
		devBoard_South.setLayout(new FlowLayout());
		devBoard_South.add(devNewgeul = new JButton("글쓰기"));
		devBoard_South.add(devDelgeul = new JButton("삭제"));
		
		devBoard.add(devBoard_South, BorderLayout.SOUTH);
		
		
		
		// 오른 - 버그리포트 탭
		JPanel bugBoard = new JPanel();
		bugBoard.setLayout(new BorderLayout());
		bugTModel = new TableModel();//버그리포트 리스트 만들기
		bugTable = new JTable(bugTModel);
		
		//위에서 만든 버그리포트 스크롤바 붙이기
		JScrollPane bugTableScr = new JScrollPane(bugTable);
		bugBoard.add(bugTableScr, BorderLayout.CENTER);
		
		
		// 오른 - 버그리포트 글쓰기버튼, 글삭제버튼
		JPanel bugBoard_South = new JPanel();
		bugBoard_South.setLayout(new FlowLayout());
		bugBoard_South.add(bugNewgeul = new JButton("글쓰기"));
		bugBoard_South.add(bugDelgeul = new JButton("삭제"));
		
		bugBoard.add(bugBoard_South, BorderLayout.SOUTH);
		
		
		

		// 오른 - 체크리스트 트리, 카테고리와 항목명 만들어서 쪼인.
		
		
		
		//트리생성 테스트
		root = new DefaultMutableTreeNode("ChkList 명");
		top1 = new DefaultMutableTreeNode("ChkList 카테고리명1");
		node1 = new DefaultMutableTreeNode("ChkList 항목명1");
		
		top2 = new DefaultMutableTreeNode("ChkList 카테고리명2");
		node2 = new DefaultMutableTreeNode("ChkList 항목명2");
		node3 = new DefaultMutableTreeNode("ChkList 항목명3");
		
		root.add(top1);
		top1.add(node1);
		
		root.add(top2);
		top2.add(node2);
		top2.add(node3);
		
		
		top = new ArrayList<DefaultMutableTreeNode>();
		for(int i = 0 ; i < 10 ; i++){
			top.add(new DefaultMutableTreeNode("ChkList arr리스트 카테고리명"+String.valueOf(i)));

			for (int j = 0; j < 5; j++){
				top.get(i).add(new DefaultMutableTreeNode("ChkList 리스트 항목명"+String.valueOf(j)));
			}
			root.add(top.get(i));
		}

				
		//위에서 만든 트리를 J트리에 심고 스크롤바 붙이기
		JScrollPane treeView = new JScrollPane(tree = new JTree(root));

		

		//오른 - 모든 탭 붙이기
		tpane = new JTabbedPane();
		tpane.addTab("체크리스트", treeView);
		tpane.addTab("개발자게시판", devBoard);
		tpane.addTab("버그리포트", bugBoard);
		tpane.setSelectedIndex(0);



		//통합 - 왼쪽 오른쪽 붙이기
		splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, p_Left, tpane );
		splitPane.setDividerLocation(300); // 수평으로 쪼갤 위치의 설정 
		add( splitPane ); // 생성된 split창을 프레임에 부착 
		// JPanel은 경량 컨터이너이므로 getContentPane()이 불필요 

		//getContentPane().add(splitPane, BorderLayout.CENTER ); 
	
	}
	
	//화면에 테이블 붙이는 메소드 
	class TableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"게시번호","제목","작성자","작성시간"};

		//=============================================================
		// 1. 기본적인 TabelModel  만들기
		// 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
		// AbstractTabelModel에서 구현되지 않았기에...
		// 반드시 사용자 구현 필수!!!!

		public int getColumnCount() { 
			return columnNames.length; 
		} 

		public int getRowCount() { 
			return data.size(); 
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