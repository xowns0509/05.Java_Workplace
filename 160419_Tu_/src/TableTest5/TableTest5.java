package TableTest5;

//####################################################
//	JTable 만들기 - TableModel을 만들어 이용하는 테이블
//				 - 생성 후 나중에 데이타모델 추가하는 방법
//----------------------------------------------------
import java.awt.Dimension; 
import javax.swing.*; 
import javax.swing.table.*; 
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

public class TableTest5{ 

	JFrame frame;
	JTable table;
	JButton button;
	MyTableModel model;

	public TableTest5() { 
	 	frame = new JFrame("테이블 테스트 1 ");
		button = new JButton(" 눌려주세요 ");
		
		// 기본적인 테이블 생성
		model = new MyTableModel(); 	
		table = new JTable(model); //화면을 담당하는 놈이 Jtable, 그안의 데이터 담당이 MyTableModel
		
//table.getColumnModel().getColumn(2).setPreferredWidth(200); 

		frame.getContentPane().add("Center", new JScrollPane(table)); 
		frame.getContentPane().add("South", button); 
		frame.setSize(800,200);
		frame.setVisible(true);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				change();
			}
		});
		
		//테이블에서 클릭했을 때
		//table.addMouseListener(new MouseListener() {	//다른 메소드 안쓰기위해 어뎁터 클래스로 바꿔줘.
		table.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				
				String data = (String)model.getValueAt(row, col); //오브젝트형으로 반환하므로 형변환해라.
				System.out.println(data);
				JOptionPane.showMessageDialog(null, row+"행" + col + "열 클릭");
			
			}
		});

	} 
	
	void change(){
		//나중에 여기는 DB에서 데이터를 검색하는 부분.

		ArrayList list = new ArrayList();
		for (int i = 0; i<5; i++){
			
			ArrayList temp = new ArrayList();
			for(int j = 0 ; j< 4; j++){
				//각각 컬럼의 데이터를 얻어오는 부분.
				
				temp.add("<"+i +","+ j+">");
				
			}//여기까지 하나의 레코드에 해당하는 데이터를 갖고 옴
			list.add(temp);

		}//여기까지를 DB에서 가져오는 부분으로 만들어주면됨.
		
		//화면의 JTable의 모델의 데이터로 지정
		model.data = list;
		table.setModel(model);
		
		model.fireTableDataChanged();//화면갱신용. 화면이 바뀌었다고 view에게 알려야 해.
	}
	
	public static void main(String args[]) { 
		TableTest5 test = new TableTest5(); 
	
	} 

	class MyTableModel extends AbstractTableModel { 
		  
		ArrayList data = new ArrayList();
		String [] columnNames = {"First", "Second","삼","사" };

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
			 public String getColumnName(int col) { 
				return columnNames[col]; 
			}


		//============================================================
		// 3. 한 컬럼에 String 출력이 아닌 다른 형태(Checkbox)로 변환하기
		//	(1) Boolean 형태의 값을 지닌 컬럼의 인덱스를 넘겨받음
		//	(2) 0행의 c번째 셀에 들어있는 값을 Object 형태로 넘김
		//	(3) 2번의 Object를 적용할 클래스로 변환하여 리턴
		//
		//		기본적으로 테이블에서 한 셀에 출력되는 클래스 형태는 String 클래스로
		//	   이용하는 그 것을 받아 기존의 클래스 형태로 바꿔주는 기능.
			public Class getColumnClass(int c) { 
				return getValueAt(0, c).getClass(); 
			} 
		//-----------------------
			public boolean isCellEditable(int row, int col){
				
				if(col == 1) return true; //편집을 원하면 트루, 아니라면 펄스
				else return false;//
	
//				전체를 수정할 건지.
//				칸 하나만 수정할 건지.
//				한 열만 수정할 건지.를 설정해야 해.
				
			}
			
			//설정한 값으로 저장하려면 아래를 하나 더 오버라이딩
			public void setValueAt(Object value, int row, int col){
				ArrayList temp = (ArrayList)data.get(row);
				temp.set(col, value);
				fireTableCellUpdated(row, col);
				
			}

		} // end of class MyTableModel
} // end of class TableTest

