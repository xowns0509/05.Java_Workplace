package view;

//####################################################
//	JTable 만들기 - TableModel을 만들어 이용하는 테이블
//				 - 생성 후 나중에 데이타모델 추가하는 방법
//----------------------------------------------------
import java.awt.Dimension; 
import javax.swing.*; 
import javax.swing.table.*;

import model.dao.Customer;

import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

public class selectorTable{ 

	JFrame frame;
	JTable table;
	JButton button;
	JLabel label = new JLabel("본인에 해당하는 전화번호를 눌러주세요");
	MyTableModel model;
	String selectedTel = null;
	boolean waitMe = true;

	public selectorTable(ArrayList<Customer> inARL, CustomerView cView) { 
	 	frame = new JFrame(" 사용자를 선택해주세요 ");
		button = new JButton(" 위의 리스트에서 해당하는 걸 눌러주세요 ");
		
		// 기본적인 테이블 생성
		model = new MyTableModel(); //화면을 담당하는 놈이 Jtable,
		table = new JTable(model); //그 안의 데이터 담당이 MyTableModel
		
		frame.getContentPane().add("North", label);
		frame.getContentPane().add("Center", new JScrollPane(table)); 
		frame.getContentPane().add("South", button); 
		frame.setSize(400, 200);
		frame.setVisible(true);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				setData();
			}
		});
		
		ArrayList<ArrayList<String>> row = new ArrayList<ArrayList<String>>(); //i는 행갯수
		ArrayList<String> column;
		for (int i = 0; i < inARL.size() ; i++){
			
			column = null;
			column = new ArrayList<String>();
			//int x = 0;
			//int [] lotto = new int[6];
			//각각 컬럼의 데이터를 얻어오는 부분
				
			column.add(inARL.get(i).getCustTel1());
			//System.out.println(inARL.get(i).getCustTel1());
			column.add(inARL.get(i).getCustName());
			//System.out.println(inARL.get(i).getCustName());
			column.add(inARL.get(i).getCustEmail());
			//System.out.println(inARL.get(i).getCustEmail());
			row.add(column);


//			int x = 0;
//			//int [] lotto = new int[6];
//			ArrayList<String> column = new ArrayList<String>(); //j는 열갯수
//			for(int j = 0 ; j < 3; j++){
//				//각각 컬럼의 데이터를 얻어오는 부분.
//				
//				column.add(inARL.get(j).getCustTel1());
//				column.add(inARL.get(j).getCustName());
//				column.add(inARL.get(j).getCustEmail());
//				System.out.println("j의 값" + j);
//				System.out.println("x의 값" + x);
//	
//			}//여기까지 하나의 레코드에 해당하는 데이터를 갖고 옴
//			row.add(column);
//			System.out.println(i);
			
		}//여기까지를 DB에서 가져오는 부분으로 만들어주면됨.
		
		//화면의 JTable의 모델의 데이터로 지정
		model.data = row;
		table.setModel(model);
		model.fireTableDataChanged();//화면갱신용. 화면이 바뀌었다고 view에게 알려야 해.
		
		
		//테이블에서 클릭했을 때
		//table.addMouseListener(new MouseListener() {	//다른 메소드 안쓰기위해 어뎁터 클래스로 바꿔줘.
		table.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.getSelectedRow();
				int col = 0;
				
				selectedTel = (String)model.getValueAt(row, col); //오브젝트형으로 반환하므로 형변환해라.
				System.out.println(selectedTel);
				
				label.setText("다른 것으로 선택하시려면 해당되는 번호를 다시 클릭하세요");
				button.setText(selectedTel + "로 결정하려면 이 버튼을 누르세요.");

			}
		});
	}
	
	public String setData() {
		return selectedTel;//이름을 선택하여 반환해.
	}

	class MyTableModel extends AbstractTableModel { 
		  
		ArrayList data = new ArrayList();
		String [] columnNames = {"전화번호", "이름","이메일주소"};

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

		//===============================================================
		// 2. 지정된 컬럼명으로 변환하기
		//
		//	요 밑을 주석처리하면 기본적으로 A, B, C, D 라는 이름으로 컬럼명이 지정된다
			 public String getColumnName(int col){
				return columnNames[col];
			}


		//============================================================
		// 3. 한 컬럼에 String 출력이 아닌 다른 형태(Checkbox)로 변환하기
		//	(1) Boolean 형태의 값을 지닌 컬럼의 인덱스를 넘겨받음
		//	(2) 0행의 c번째 셀에 들어있는 값을 Object 형태로 넘김
		//	(3) 2번의 Object를 적용할 클래스로 변환하여 리턴
		//
		//	기본적으로 테이블에서 한 셀에 출력되는 클래스 형태는 String 클래스로
		//	이용하는 그 것을 받아 기존의 클래스 형태로 바꿔주는 기능.
			public Class getColumnClass(int c) { 
				return getValueAt(0, c).getClass(); 
			} 
		//-----------------------
			
			//설정한 값으로 저장하려면 아래를 하나 더 오버라이딩
			public void setValueAt(Object value, int row, int col){
				ArrayList temp = (ArrayList)data.get(row);
				temp.set(col, value);
				fireTableCellUpdated(row, col);
				
			}

		} // end of class MyTableModel
} // end of class TableTest

