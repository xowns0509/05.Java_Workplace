package module;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
public class GiveCheckList extends JPanel{ 

	PLModel plModel; //디비와 실질적으로 연결하고 기능을 수행하는 클래스

	PLView parentPLView;
	/*Connection con;
	PreparedStatement ps;*/

	JSplitPane splitPane;
	JButton bSend, bTreeRefresh;
	JPanel p_Left, p_Right, p_Tree, p_Right_Center, p_Left_South, p_Right_South;
	
	DefaultMutableTreeNode teamRoot; //팀 전체 체크리스트 트리 받을 놈
	DefaultTreeModel tModel;
	
	JTree tree;
	JScrollPane scrollPane;

	JTable devListTable;				//개발자 목록을 저장하는 리스트
	TableModel devListTablemodel;
	
	
	public GiveCheckList(PLView parent){
		this.parentPLView = parent; //부모 클래스 저장
		
		try {
			plModel = new PLModel();
			System.out.println("업무분담 db 연결 성공");
		} catch (Exception e) {
			System.out.println("업무분담 db 연결 실패 " + e.getMessage());
		}
		
		addLayout();	
		eventProc();
	}

	/**
	 * giveCheckList 이벤트 추가 
	 */
	void eventProc(){
		ButtonEventHandler btnHandler = new ButtonEventHandler();

		bSend.addActionListener(btnHandler);
		bTreeRefresh.addActionListener(btnHandler);
		
		//트리선택 리스너 등록
		TreeEventHandler tevHandler = new TreeEventHandler();
		tree.addTreeSelectionListener(tevHandler);

		//개발자테이블 리스너 등록
		devListTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = devListTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				String selectedName = (String)devListTable.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 
				System.out.println("누른 이름: "+ selectedName);
				//devTnum = (Integer)devTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				
				//new PostWithCheckBox(rec, null);
				
				//개발자 이름 갖고 오기
				String empName = (String)devListTable.getValueAt(row, 0); 
				//개발자 ID 갖고 오기
				String empId = (String)devListTable.getValueAt(row, 1); 
				
				//jump2SelectedPost("코드", post_num, empId, post_title);
			}
		});
	}
	
	/**
	 * //트리 및 체크리스트 삽입 패널 갱신용 
	 * @author Taejun
	 */
	DefaultMutableTreeNode treeRefresh(){
		
		DefaultMutableTreeNode teamRoot = new DefaultMutableTreeNode();

		teamRoot = plModel.teamChklist("1");
		
		return teamRoot;
	}

	/**
	 * 선택한 개발자에게 체크리스트 할당.
	 */
	
	
	/**
	 * 패널 구성 
	 */
	void addLayout(){
		
		setLayout(new BorderLayout());
		
		//************ 업무할당 패널 화면구성************
		// 패널 - 왼쪽, 오른
		
		// 왼쪽 - 보더레이아웃
		// 왼쪽중앙 - 팀 전체 체크리스트 트리
		// 왼쪽하단 - 전체 체크리스트 트리 새로고침
		
		// 오른 - 보더레이아웃(개발자 테이블, 전달 버튼)
		// 오른중앙 - 개발자 테이블 패널
		// 오른하단 - 전달 버튼
		
		
		
		// 왼쪽 - 보더레이아웃
		p_Left = new JPanel(new BorderLayout());
		
		// 왼쪽중앙 - 팀 전체 체크리스트 트리
		p_Tree = new JPanel(new GridLayout(1,1));
		//p_Tree로 패널을 새로 만들고 레이아웃을 그리드(1,1)로 지정하여 왼쪽화면이 트리화면으로 꽉차게 만든다. 
		//이걸 지정안하면 기본인 Flowlayout으로 지정되어 회색 보더가 생기게 됨.
		
		// 왼쪽 체크리스트 불러와서 트리모델에 심기
		teamRoot = treeRefresh();
		tModel = new DefaultTreeModel(teamRoot);
		
		// 트리모델을 JTree에 부착.
		tree = new JTree(tModel);
		
		// JTree를 패널에 부착 후
		p_Tree.add(tree);
		
		// 그 패널에 스크롤바 붙이기
		JScrollPane teamTree = new JScrollPane(p_Tree);
		
		
		// 왼쪽하단 - 전체 체크리스트 트리 새로고침
		p_Left_South = new JPanel();
		p_Left_South.add(bTreeRefresh = new JButton("팀 체크리스트 새로고침"));
		
		// 왼쪽 모두 붙이기
		p_Left.add(teamTree, BorderLayout.CENTER);
		p_Left.add(p_Left_South, BorderLayout.SOUTH);
		// ----------------왼쪽 완료(최종적으로 부착하는 스플릿페인은 맨 아래쪽에.)
		
		
		
		// 오른 - 보더레이아웃(개발자 테이블, 전달 버튼)
		p_Right = new JPanel(new BorderLayout());
		
		// 오른중앙 - 개발자 테이블 패널
		//p_Right_Center = new JPanel();
		devListTablemodel = new TableModel();

		//model 정보 저장 (getDevList)
		try {
			devListTablemodel.data = plModel.getDevList(parentPLView.getRecord());
			System.out.println("개발자 리스트 가져오기 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("개발자 리스트 가져오기 실패 " + e.getMessage());
		}
		//devListTable.setModel(devListTablemodel);
		devListTable = new JTable(devListTablemodel);
		devListTablemodel.fireTableDataChanged();
		//테이블 붙일 곳
		
		// 테이블에 스크롤바 붙이기
		JScrollPane scrollPane = new JScrollPane(devListTable);
		
		// 오른하단 - 전달 버튼
		p_Right_South = new JPanel();
		p_Right_South.add(bSend = new JButton("전달"));
		
		// 오른 모두 붙이기
		p_Right.add(scrollPane, BorderLayout.CENTER);
		p_Right.add(p_Right_South, BorderLayout.SOUTH);
		
		
		
		//왼쪽, 오른쪽 splitpane에 붙이기
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p_Left, p_Right);
		splitPane.setDividerLocation(200);
		add(splitPane);
	}

	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();

			if(evt == bSend){

			}
		}
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
				System.out.println("THE ROOT NODE IS: "+node.getParent().toString()+" CHILD NODE IS: "+nodeInfo.toString());
			} else {

			}
		}
	}
	
	/**
	 * 체크리스트들을 할당할 개발자들이 담기는 테이블을 만들어줄 클래스
	 * @author TaeJoon
	 *
	 */
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
}

//----미사용
//class TreeEventHandler implements TreeSelectionListener{
//
//	public void valueChanged(TreeSelectionEvent e) {
//		//Returns the last path element of the selection.
//		//This method is useful only when the selection model allows a single selection.
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
//				tree.getLastSelectedPathComponent();
//
//		if (node == null)
//			//Nothing is selected.     
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

//---------백업
//class TreeEventHandler implements TreeSelectionListener{
//
//	@Override
//	public void valueChanged(TreeSelectionEvent e) {
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
//
//		if (node == null)
//			// Nothing is selected.
//			return;
//
//		Object nodeInfo = node.getUserObject();
//		if (node.isLeaf()) {
//			System.out.println("THE ROOT NODE IS: "+node.getParent().toString()+" CHILD NODE IS: "+nodeInfo.toString());
//		} else {
//
//		}
//	}
//}