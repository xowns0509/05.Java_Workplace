package  view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.RentModel;

public class RentView extends JPanel 
{
	JTextField tfRentTel, tfRentCustName, tfRentVideoNum;
	JButton bRent;
	
	JTextField tfReturnVideoNum;
	JButton bReturn;
	
	JTable tableRecentList;
	RentTableModel tbModelRent = null;
	
	RentModel db = null;
	
	//==============================================
	//	 생성자 함수
	public RentView(){
		addLayout(); 	// 화면설계
		eventProc();
		connectDB();	// DB연결
		initProc();
		getMinab();
	}
	
	public void initProc(){
//		tfVideoNum.setEnabled(false);
//		tfVideoNum.setEditable(false);//비디오번호 텍스트필드를 처음에 안보이도록 초기화
//		tfInsertCount.setEditable(false);
//		tfInsertCount.setHorizontalAlignment(JTextField.RIGHT);//수평위치 정렬. 숫자가 맨 오른쪽으로 가게끔.
	}

	public void connectDB(){	// DB연결
		try {
			db = new RentModel();
		} catch (Exception e) { //기존에 throws Exception으로 되어있었으므로 Exception로 자동지정됨.
			System.out.println("대여관리에서 DB연결문제 발생: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void eventProc(){
		EvtHandler btnHandler = new EvtHandler();
		
		// 이벤트 등록
		tfRentTel.addActionListener(btnHandler);
		tfRentCustName.addActionListener(btnHandler);
		tfRentVideoNum.addActionListener(btnHandler);
		bRent.addActionListener(btnHandler);
		tfReturnVideoNum.addActionListener(btnHandler);
		bReturn.addActionListener(btnHandler);
		
		
		//JTable 마우스클릭 이벤트 발생 시
		//table.addMouseListener(new MouseListener() {	//다른 메소드 안쓰기위해 어뎁터 클래스로 바꿔줘.
		//MouseListener 쓴다면 밑의 오버라이딩메소드를 죄다 써줘야해.
//		tableRecentList.addMouseListener(new MouseAdapter() {		
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				
//				int row = tableRecentList.getSelectedRow();	//테이블에서 선택한 행
//				int col = 0;							//무조건 0. 비디오 번호만 갖고 오도록.
//				
//				//오브젝트형으로 반환하므로 형변환해라.
//				//selectedName = (String)tableVideo.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 				
//				int vnum = (Integer)tableRecentList.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
//				//int vnum = ((Integer)tableVideo.getValueAt(row, col)).intValue()//1.5버전 이전.
//				
//				tfVideoNum.setText(String.valueOf(vnum));
//				tfVideoTitle.setText((String)tableVideo.getValueAt(row, 1));//제목
//				comVideoJanre.setSelectedItem((String)tableVideo.getValueAt(row, 2));//장르
//				tfVideoDirector.setText((String)tableVideo.getValueAt(row, 3));//감독
//				tfVideoActor.setText((String)tableVideo.getValueAt(row, 4));//배우
//				
//				//System.out.println(selectedName);
//				//label.setText("다른 것으로 선택하시려면 해당되는 번호를 다시 클릭하세요");
//				//button.setText(selectedName + "로 결정하려면 이 버튼을 누르세요.");
//				searchExp(vnum);
//
//			}
//		});
	}
	
	// 대여버튼을 클릭하였을 때.
	void rentVDO(){

		try {
			//1. 화면에서 필요한 정보를 얻어옴
			//2. 데이터베이스 연동클래스의 실행메소드 호출
			
			db.rent(tfRentTel.getText(), Integer.parseInt(tfRentVideoNum.getText()));
			
			//3. DB업로드 후 화면초기화.
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println("정상적으로 rent 메소드 실행");
		JOptionPane.showConfirmDialog(tfRentTel, "메세지");
		//JOptionPane.showConfirmDialog(tfRentTel, "메세지", "제목", null, null, null);

	}
	
	// 반납버튼을 클릭하였을 때.
	void returnVDO(){
		
		int vnum = Integer.parseInt(tfRentVideoNum.getText());
		
	}
	
	
	void getMinab(){
		
		try {
			//1. 화면에서 필요한 정보를 얻어옴
			//2. 데이터베이스 연동클래스의 실행메소드 호출
			tbModelRent.data = db.search(); //예외처리
			tableRecentList.setModel(tbModelRent);
			tbModelRent.fireTableDataChanged();
			
			//3. DB업로드 후 화면초기화.
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}
	
	
	class EvtHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			
			if(o == tfRentTel){  
				System.out.println("이벤트");			// 전번
			}
			else if(o == tfRentCustName){
				System.out.println("이벤트");			// 고객명
			}
			else if(o == tfRentVideoNum){
				System.out.println("이벤트");			// 비번호
			}
			else if(o == bRent){
				rentVDO();								// 대여버튼
				getMinab();
			}
			else  if(o == tfReturnVideoNum){
				System.out.println("이벤트");			// 비디오번호
			}
			else if(o == bReturn){  						
				System.out.println("이벤트");			// 반납버튼
				getMinab();
			}
		}
	}
	
	//수정필요. 화면설계 메소드
	public void addLayout(){
		//멤버변수의 객체 생성
		
		tfRentTel = new JTextField();
		tfRentCustName = new JTextField();
		tfRentVideoNum = new JTextField();
		bRent = new JButton("대여")  ;
		
		tfReturnVideoNum = new JTextField(10);
		bReturn = new JButton("반납");
		
		tbModelRent = new RentTableModel(); //표를 만들어서
		tableRecentList = new JTable(tbModelRent);//J테이블(tableRecentList)에 붙임
		// tableVideo.setModel(tbModelVideo);
		

		
		//************화면구성************
		// 전체화면 - 상단, 중앙
		// 상단 - 격자왼쪽, 격자오른
		// 격자왼쪽 - 그리드
		// 격자오른 - 플로우

		setLayout(new BorderLayout());
//		// 전체화면 - 상단,중앙. 붙이는건 나중에.
//		JPanel p_all = new JPanel();
//		p_all.setLayout(new BorderLayout());
		
		
		
		// 상단 격자
		JPanel p_north = new JPanel();
		p_north.setLayout(new GridLayout(1,2));
		
		// 상단 격자의 윗쪽
		JPanel p_west_north_left = new JPanel();
		p_west_north_left.setLayout(new GridLayout(4,2));
		p_west_north_left.add(new JLabel("전화번호"));
		p_west_north_left.add(tfRentTel);
		p_west_north_left.add(new JLabel("고 객 명"));
		p_west_north_left.add(tfRentCustName);
		p_west_north_left.add(new JLabel("비디오번호"));
		p_west_north_left.add(tfRentVideoNum);
		p_west_north_left.add(bRent); //대여버튼
		p_west_north_left.setBorder(new TitledBorder("대여"));
		
		// 상단 격자의 오른쪽
		JPanel p_west_north_right = new JPanel();
		p_west_north_right.setLayout(new FlowLayout());
		p_west_north_right.add(new JLabel("비디오번호"));
		p_west_north_right.add(tfReturnVideoNum);
		p_west_north_right.add(bReturn); //반납버튼
		p_west_north_right.setBorder(new TitledBorder("반납"));
		
		// 상단 격자에 붙이기
		p_north.add(p_west_north_left);
		p_north.add(p_west_north_right);
		

		
		// 상단 격자 - 전체화면 북쪽 붙이기
//		p_all.add(p_north, BorderLayout.NORTH);
//		
//		// 중앙
//		p_all.add(new JScrollPane(tableRecentList),BorderLayout.CENTER);
//		// 테이블을 붙일때에는 반드시 JScrollPane() 이렇게 해야함 
//		
//		add(p_all);
		
		add(p_north, BorderLayout.NORTH);
		add(new JScrollPane(tableRecentList), BorderLayout.CENTER);
		
	}	
	
	//화면에 테이블 붙이는 메소드... 
	class RentTableModel extends AbstractTableModel { 
		  
		ArrayList data = new ArrayList();
		String [] columnNames = {"비디오번호","비디오제목","고 객 명","전화번호","반납예정일","반납여부"};

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
