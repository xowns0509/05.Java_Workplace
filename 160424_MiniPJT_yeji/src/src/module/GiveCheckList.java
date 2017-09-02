package module;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;

import main.Database;
import model.PLModel;
import view.PLView;

/**
 * 체크리스트를 분담해주는 클래스 (및 UI)
 * 입력받은 값을 parent 에 보내줌
 * 사용자: PL
 * @author yeji
 *
 */

//1 왼쪽 splitpane 완성
//2 JTree 붙이고, 항목은 checkbox 로 처리
//3 이벤트처리 해서 선택된 항목 + 선택된 개발자 (JTable)을 
//전달 버튼이 눌렸을때 보내기 
// 전달 처리 된 항목은 비활성화 해주거나 아직 체크가 되지않은 
//항목들과 차별을 두기 
public class GiveCheckList extends JPanel implements ActionListener{ 

	PLModel db; //디비와 실질적으로 연결하고 기능을 수행하는 클래스
	PLView parent;
	/*Connection con;
	PreparedStatement ps;*/

	JSplitPane splitPane;
	JButton bSend;
	JPanel p_checkList, p_list; //카테고리 , 개발자 리스트와 버튼 패널
	JList devList; //개발자 목록을 저장하는 리스트
	JTree tree;
	JScrollPane scrollPane;
	
	JTable table;
	TableModel model;

	public GiveCheckList(PLView parent){
		this.parent = parent; //부모 클래스 저장
		try {
			db = new PLModel();
			System.out.println("업무분담 db 연결 성공");
		} catch (Exception e) {
			System.out.println("업무분담 db 연결 실패 " + e.getMessage());
		}
		addLayout();	
		eventProc();
	}

	/**
	 * 패널 구성 
	 */
	void addLayout(){
		setLayout(new BorderLayout());

		p_checkList = new JPanel();
		p_list = new JPanel(new BorderLayout()); 

		bSend = new JButton("전달");
		
		model = new TableModel();
		table = new JTable(model);
		
		//model 정보 저장 (getDevList)		
		try {
			model.data = db.getDevList(parent.getRecord());
			System.out.println("개발자 리스트 가져오기 성공");
		} catch (Exception e) {
			System.out.println("개발자 리스트 가져오기 실패 " + e.getMessage());
		}		
		table.setModel(model);

		//마우스 리스너 추가 
		scrollPane = new JScrollPane(table);
		
		p_list.add(scrollPane, BorderLayout.CENTER);
		p_list.add(bSend, BorderLayout.SOUTH);

		//splitpane 에 붙이기 
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p_checkList, p_list);
		splitPane.setDividerLocation(500);
		add(splitPane);
	}
	/**
	 * 이벤트 추가 
	 */
	void eventProc(){
		bSend.addActionListener(this);
	}
	
	class TableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();
		String [] columnNames = {"이름", "아이디"};
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			ArrayList temp = (ArrayList)data.get(row);
			return temp.get(col);
		}
		public String getColumnName(int col){
			return columnNames[col];
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton evt = (JButton)e.getSource();
		
		//TODO : 등록 버튼이 눌려졌을때 체크된 항목들과 선택된 개발자를 가져옴
		if(evt == bSend){
			
		}
		
	}
}
