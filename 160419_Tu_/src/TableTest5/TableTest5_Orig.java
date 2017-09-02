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

public class TableTest5_Orig{ 

	JFrame frame;
	JTable table;
	JButton button;
	MyTableModel model;

    public TableTest5_Orig() { 
	 	frame = new JFrame("테이블 테스트 1 ");
		button = new JButton(" 눌려주세요 ");
		
		// 기본적인 테이블 생성
		model = new MyTableModel(); 	
	    table = new JTable(model); 
		
//table.getColumnModel().getColumn(2).setPreferredWidth(200); 

		frame.getContentPane().add("Center", new JScrollPane(table)); 
        frame.getContentPane().add("South", button); 
        frame.setSize(800,200); 
        frame.setVisible(true); 

    } 

     
    public static void main(String args[]) { 
        TableTest5_Orig test = new TableTest5_Orig(); 
    
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
		//		기본적으로 A, B, C, D 라는 이름으로 컬럼명이 지정된다
			 public String getColumnName(int col) { 
				return columnNames[col]; 
			} 


		//============================================================
		// 3. 한 컬럼에 String 출력이 아닌 다른 형태(Checkbox)로 변환하기
		//		(1) Boolean 형태의 값을 지닌 컬럼의 인덱스를 넘겨받음
		//		(2) 0행의 c번째 셀에 들어있는 값을 Object 형태로 넘김
		//		(3) 2번의 Object를 적용할 클래스로 변환하여 리턴
		//
		//		기본적으로 테이블에서 한 셀에 출력되는 클래스 형태는 String 클래스로
		//	   이용하는 그 것을 받아 기존의 클래스 형태로 바꿔주는 기능
			public Class getColumnClass(int c) { 
				return getValueAt(0, c).getClass(); 
			} 

		} // end of class MyTableModel
} // end of class TableTest

