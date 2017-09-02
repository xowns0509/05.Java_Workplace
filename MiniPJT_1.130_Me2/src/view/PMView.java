package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import model.PMModel;
import record.EmpRecord;

public class PMView extends JFrame implements ActionListener{

	EmpRecord rec;
	JSplitPane splitPane;
	JTabbedPane tpane;
	
	JTable		tableBoard;
	BoardTableModel tbModelBoard;
	
	JButton		bWrite, bDelete, bSearch;
	
	PMModel pmModel;
	
	public PMView(EmpRecord rec){
		this.rec = rec; //TODO 추가 
		
		System.out.println("PMView 실행");
		
		try {
			pmModel = new PMModel();
		} catch (Exception ex) {
			System.out.println("PMModel() 생성에러");
		}
		
		addLayout();
		eventProc();
		
	}
	
	public void eventProc(){
		bWrite.addActionListener(this);
		bDelete.addActionListener(this);
		bSearch.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		
		if(evt == bWrite){
			System.out.println("글쓰기");
		}else if(evt == bDelete){
			System.out.println("삭제");
		}else if(evt == bSearch){
			System.out.println("불러오기");
			searchBoard();
		}
	}
	
	/* 검색 텍스트필드에서 엔터 이벤트 발생할 때 비디오 정보를 검색한다*/
	void searchBoard(){
		
		try {
			System.out.println("pmModel.searchBorad() 실행전");
			tbModelBoard.data = pmModel.searchBorad(); 		// ArrayList 리턴
			System.out.println("pmModel.searchBorad() 실행됨");
		} catch (Exception ex) {
			System.out.println("pmModel.searchBorad() 에러발생");
		}	
		tableBoard.setModel(tbModelBoard);
		tbModelBoard.fireTableDataChanged(); // 데이타(모델)측에서 내용이 변경된 사실을 뷰측에 알려줘야함
		
	}
	
	public void addLayout(){
		
		bWrite = new JButton("글쓰기");
		bDelete = new JButton("삭제");
		bSearch = new JButton("불러오기");
		
		tbModelBoard = new BoardTableModel();
		tableBoard = new JTable(tbModelBoard);

		// 화면구성 - 왼쪽영역 - 체크리스트와 그래프가 들어가야함

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
		JPanel p_Left_North = new JPanel(); //패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North.setLayout(new BorderLayout());
		p_Left_North.add(p_Left_North_Up, BorderLayout.NORTH );
		p_Left_North.add(p_Left_North_Down, BorderLayout.CENTER );

		// 왼쪽하단 - 프로젝트 진척도
		JPanel p_Left_South = new JPanel(); //패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_South.add(new JLabel("프로젝트 진척도 BAR칸", SwingConstants.CENTER));

		// 왼쪽상단, 하단 붙이기
		JPanel p_Left = new JPanel(); //패널 명은 더미용이며 추후 변경될 수 있음
		p_Left.setLayout(new GridLayout(2,1));
		p_Left.add(p_Left_North);
		p_Left.add(p_Left_South);
		
		// 화면구성 - 오른쪽영역 - 게시판
		JPanel p_east = new JPanel();
		p_east.setLayout(new BorderLayout());

		JPanel p_east_north = new JPanel();
		p_east_north.add(new JLabel("업무보고"),BorderLayout.CENTER);

		JPanel p_east_south = new JPanel();
		p_east_south.add(bSearch);
		p_east_south.add(bWrite);
		p_east_south.add(bDelete);

		p_east.add(p_east_north,BorderLayout.NORTH);
		p_east.add(new JScrollPane(tableBoard),BorderLayout.CENTER);
		// 테이블을 붙일때에는 반드시 JScrollPane() 이렇게 해야함 
		p_east.add(p_east_south,BorderLayout.SOUTH);

		//왼쪽 오른쪽 붙이기
		splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, p_Left, p_east );
		splitPane.setDividerLocation(300); // 수평으로 쪼갤 위치의 설정 
		add( splitPane ); // 생성된 split창을 프레임에 부착 
		// JPanel은 경량 컨터이너이므로 getContentPane()이 불필요 
		//getContentPane().add(splitPane, BorderLayout.CENTER ); 
		//add(p_Center, BorderLayout.CENTER);
		//add(p_south, BorderLayout.SOUTH);	

		
		setTitle("milestone");	//Frame 타이틀 이름 주기 
		setSize(1200,750);		//Frame 크기 설정 
		setVisible(true);			//생성한 Frame 윈도우에 뿌리기
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
	}

	//화면에 테이블 붙이는 메소드 
	class BoardTableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"게시번호","글제목","작성자","작성시간","승인"};

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
