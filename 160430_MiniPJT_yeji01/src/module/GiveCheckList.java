package module;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.PLModel;
import record.CheckListRecord;
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

	JSplitPane splitPane;//화면 왼, 오른 분할
	JButton bTreeRefresh, bClearChkL, bClearDev, bClearAll, bSend;
	
	JLabel 	plzSelectChkL, selectedChkL,// 라벨: 1. 항목선택, 선택한 개발자 체크리스트 카테고리와 내용
			plzSelectDev,				// 라벨: 2. 업무를 부여받을 개발자 선택
			selectedDev, selectedDevId,	// 라벨: 선택된 개발자명과 ID
			giveChkL; 					// 라벨: 3. 해당항목 전달 라벨
	
	JTextField tfSelectedDev, tfSselectedDevId; //선택한 개발자 이름, ID 보여주느 곳
	
	JPanel p_Left, p_Right, p_Tree, p_Right_Center, p_Left_South, p_Right_South, p_Right_Up, p_Right_Up_Center, p_Right_Down_Center_Left;
	JPanel p_Right_Up_North, p_Right_Down, p_Right_Down_North, p_Right_Down_Center, p_Right_Down_Center_Right,	p_Right_Down_South;
	
	DefaultMutableTreeNode teamRoot; //팀 전체 체크리스트 트리 받을 놈
	DefaultTreeModel tModel;
	
	JTree tree;

	ArrayList<ArrayList<String>> catListARLT = new ArrayList<ArrayList<String>>();// 밑에 걸 죄다 담을 리스트
	ArrayList<String> catListARL = new ArrayList<String>();//0번째: 카테고리명, 1번째: 항목내용
	
	JTable devListTable;				//개발자 목록을 저장하는 테이블
	DevListTableModel devListTableModel;
	
	JTable catListTable;				//선택한 ChkList을 저장하는 테이블	
	CatListTableModel catListTableModel;
	
	CheckList assignChkL;
	
	public GiveCheckList(PLView parent){
		this.parentPLView = parent; //부모 클래스 저장
		
		try {
			plModel = new PLModel();
			System.out.println("업무분담 db 연결 성공");
		} catch (Exception e) {
			System.out.println("업무분담 db 연결 실패 " + e.getMessage());
		}
//		catListARL.add("1");
//		catListARL.add("2");
//		catListARLT.add(catListARL);
		addLayout();	
		eventProc();
		refreshSelectedChkLTable();
	}

	/**
	 * GiveCheckList 이벤트 추가 
	 */
	void eventProc(){
		ButtonEventHandler btnHandler = new ButtonEventHandler();
		
		bTreeRefresh.addActionListener(btnHandler);
		bClearChkL.addActionListener(btnHandler);
		bClearDev.addActionListener(btnHandler);
		bClearAll.addActionListener(btnHandler);
		bSend.addActionListener(btnHandler);
		
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
								
				//개발자 이름 갖고 오기
				String empName = (String)devListTable.getValueAt(row, 0);
				
				//개발자 ID 갖고 오기
				String empId = (String)devListTable.getValueAt(row, 1); 
				selectedDevNameID(empName, empId);
				
				//jump2SelectedPost("코드", post_num, empId, post_title);
			}
		});
	
		// 미구현 - 할당 체크리스트 테이블 리스너 등록

	}
	void selectedDevNameID(String empName, String empId){
		tfSelectedDev.setText(empName);
		tfSselectedDevId.setText(empId);
	}

	/**
	 * 업무할당 패널 버튼 이벤트 클래스
	 * @author TaeJoon
	 */
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();

			if(evt == bTreeRefresh){//트리 갱신
				treeRefresh();
				//트리갱신 메소드
				teamRoot = treeRefresh();		//다시 가져옴.
				tModel = null;					//모델 초기화
				tModel = new DefaultTreeModel(teamRoot);//초기화 후 트리노드들 심기.
				
				tree = new JTree(tModel);		//모델을 JTree로 
				
				p_Tree.removeAll();				//JTree를 심었던 패널의 내용을 지움
												//하지만 초기화 하는 것은 아님.
				
				p_Tree.add(tree);				//다시 변경된 트리들이 담긴 JTree를 패널에 부착.
												//p_Tree패널은 레이아웃이 그리드(1,1)이라 꽉차게 보임
												//기본값인 flow레이아웃 일 경우 회색의 보더가 생긴다.

				p_Tree.revalidate();			//p_Tree패널 다시 보여주기.
				eventProc();					//tree를 새로 할당했으므로
												//다시 이벤트 부여하기 위해 eventProc() 메소드 재실행
				
			}else if(evt == bClearChkL){//체크리스트 선택 취소
				catListARL.clear();
				catListARLT.clear();
				refreshSelectedChkLTable();
				
			}else if(evt == bClearDev){//개발자 선택 취소
				selectedDevNameID("", "");
				
			}else if(evt == bClearAll){//전체 선택 취소
				selectedDevNameID("", "");
				catListARL.clear();
				catListARLT.clear();
				refreshSelectedChkLTable();
				
			}else if(evt == bSend){//전달
				
				if(   tfSelectedDev.getText().equals("") == true   ){
					//개발자 선택 유무
					
					JOptionPane.showMessageDialog(null, "개발자를 선택하지 않으면 업무할당 불가");
					return;
					
				}else if(   catListARLT.size() == 0   ){
					// 0번째 칸 할당내용의 공백 확인
					
					JOptionPane.showMessageDialog(null, "항목을 선택하지 않으면 업무할당 불가");
					return;
					
				}
				
				//체크리스트 할당 실행
				assignChkList();
			}
		}
	}
	
	/**
	 * 선택된 개발자에게 업무할당
	 * @author Taejun
	 */
	void assignChkList(){

		//필요한 레코드들을 생성한다음에 CheckList.java 로 보내주기
		ArrayList<CheckListRecord> listRec = new ArrayList<CheckListRecord>();

		//카테고리부터 설정 (catNum, listNum, listStat) 은 설정안해도 됨 
		//catNum,listNum 은 sequence 로 
		//listStat은 생성당시에는 없어도 됨 (NULL 값허용)

		
		//트리에서 선택한 노드가 테이블에 이미 있는지 확인
		for(int i = 0; i < catListARLT.size(); i++){
			
			CheckListRecord rec = new CheckListRecord();

			//체크리스트 레코드에 담기
			//catListARLT.get(i).get(0) i번째 행의 카테고리명
			//catListARLT.get(i).get(1) i번째 행의 항목내용
			
			rec.setCatTitle(catListARLT.get(i).get(0).substring(catListARLT.get(i).get(0).indexOf(". ")+2));	//where로 조건 줄 카테고리 명 저장.
			
			//String str3 = "총 비용은 $45.76"; 
			//int a2 = str3.indexOf("$45.76"); // 6 // 문자열이 시작하는 위치를 찾음
			
			rec.setContent(catListARLT.get(i).get(1));	//where로 조건 줄 항목내용 설정.
			rec.setpNum(parentPLView.getRecord().getpNum());//프로젝트 번호 저장.
			rec.setEmpId(tfSselectedDevId.getText());	//할당 할 개발자의 id 저장.

			listRec.add(rec); //추가
		}
		
		

		

		try{
			
			assignChkL = new CheckList();
			//저장하고 추가부분, contentList 리셋			
			assignChkL.assignARLChkList(listRec);
					
			JOptionPane.showMessageDialog(null, "등록완료");

		} catch (Exception e1) {
			System.out.println("등록 실패 " + e1.getMessage());
			e1.printStackTrace();
		}finally{
		}
		
		// 선택했던 항목들 및 개발자명, ID 초기화
		catListARL.clear();
		catListARLT.clear();
		refreshSelectedChkLTable();

	}
	
	/**
	 * //트리 및 체크리스트 삽입 패널 갱신용 
	 * @author Taejun
	 */
	public DefaultMutableTreeNode treeRefresh(){
		
		DefaultMutableTreeNode teamRoot = new DefaultMutableTreeNode();

		teamRoot = plModel.teamChklist(String.valueOf(parentPLView.getRecord().getpNum()));
		
		return teamRoot;
	}
	
	/**
	 * 할당 할 체크리스트들의 카테고리명과 항목명을 담는 테이블 갱신용
	 */
	void refreshSelectedChkLTable(){
		catListTableModel.data = catListARLT;
		catListTable.setModel(catListTableModel);
		catListTableModel.fireTableDataChanged();
		
	}

	/**
	 * 트리 선택시 카테고리명(부모)과 항목내용(자식)을 갖고오는 클래스
	 * @author TaeJoon
	 */
	class TreeEventHandler implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			if (node == null)
				// Nothing is selected.
				return;

			Object nodeInfo = node.getUserObject();
			if (node.isLeaf()) {
				
				//트리에서 선택한 노드가 테이블에 이미 있는지 확인
				for(int i = 0; i < catListARLT.size(); i++){
					for (int j = 0; j < catListARLT.size(); j++){
						
						if(catListARLT.get(i).get(0).equals(node.getParent().toString()) == true & 
								catListARLT.get(i).get(1).equals(nodeInfo.toString()) == true){
							//catListARLT.get(i).get(0) i번째 행의 카테고리명
							//catListARLT.get(i).get(1) i번째 행의 항목내용
							
							//카테고리명과 항목내용을 한꺼번에 비교하여 이에 해당한 경우 삽입 방지

							JOptionPane.showMessageDialog(null, "항목의 중복선택은 있을 수 없어");
							return;
						}
					}
				}
				
				catListARL = null;
				catListARL = new ArrayList<String>();
				
				//트리에서 선택한 카테고리와 항목을 ARL에 저장
				catListARL.add(node.getParent().toString());
				catListARL.add(nodeInfo.toString());
				
				//위의 두 내용을 한 행에 담아 줄 ARLT에 저장
				catListARLT.add(catListARL);

				//참고출력용
				System.out.println("THE ROOT NODE IS: "+node.getParent().toString());
				System.out.println(" CHILD NODE IS: "+nodeInfo.toString());
				
				// 할당 체크리스트 테이블 갱신
				refreshSelectedChkLTable();
				
			} else {

			}
		}
	}
	
	/**
	 * 패널 구성 
	 */
	void addLayout(){
		
		setLayout(new BorderLayout());
		
		catListTable = new JTable();
		catListTableModel = new CatListTableModel();
		
		//************ 업무할당 패널 화면구성************
		// 전체 - 왼쪽, 오른
		
		// 왼쪽 - 보더레이아웃
		// 왼쪽북단 - 항목선택 라벨표시
		// 왼쪽중앙 - 팀 전체 체크리스트 트리
		// 왼쪽남단 - 전체 체크리스트 트리 새로고침
		
		// 오른 - 그리드레이아웃(상하)

		// 오른상단 - 보더레이아웃
		// 오른상단북단 - 선택 체크리스트 정보라벨 표시
		// 오른상단중앙 - 선택한 체크리스트 테이블 표시

		// 오른하단 - 보더레이아웃
		// 오른하단북단 - 업무 전달할 개발자 선택 라벨표시
		// 오른하단중앙왼쪽 - 개발자 목록 테이블
		// 오른하단중앙오른 - 선택한 개발자 정보
		// 오른하단북단 - 해당 개발자에게 전달 라벨 및 전달 버튼
		

		
		// 왼쪽 - 보더레이아웃 - 보더레이아웃 해야 트리가 꽉참
		p_Left = new JPanel(new BorderLayout());
		
		// 왼쪽북단 - 항목선택 라벨표시
		//p_Left_North
		plzSelectChkL = new JLabel("1. 항목선택", SwingConstants.CENTER);
		
		// 왼쪽중앙 - 팀 전체 체크리스트 트리
		//p_left_Center
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
		
		
		// 왼쪽남단 - 전체 체크리스트 트리 새로고침
		p_Left_South = new JPanel();
		p_Left_South.add(bTreeRefresh = new JButton("팀 체크리스트 새로고침"));
		
		// 왼쪽 모두 붙이기
		p_Left.add(plzSelectChkL, BorderLayout.NORTH);
		p_Left.add(teamTree, BorderLayout.CENTER);
		p_Left.add(p_Left_South, BorderLayout.SOUTH);
		// ----------------왼쪽 완료(최종적으로 부착하는 스플릿페인은 맨 아래쪽에.)
		

		
		// 오른 - 그리드레이아웃(상하)
		p_Right  = new JPanel(new GridLayout(2,1));
		
		// 오른상단 - 보더레이아웃
		p_Right_Up = new JPanel(new BorderLayout());
		
		// 오른상단북단 - 선택 체크리스트 정보라벨 및 항목표기화 버튼 표시
		p_Right_Up_North = new JPanel(new FlowLayout());
		p_Right_Up_North.add(selectedChkL = new JLabel("선택한 체크리스트 카테고리와 내용", SwingConstants.CENTER));
		p_Right_Up_North.add(bClearChkL = new JButton("목록초기화"));
		
		// 오른상단중앙 - 선택한 체크리스트 테이블 표시
		//p_Right_Up_Center = new JPanel(new GridLayout(1, 1));
		catListTable = new JTable();
		catListTableModel = new CatListTableModel();
		refreshSelectedChkLTable();
		
//		catListTableModel.data = catListARLT;	
//		catListTable.setModel(catListTableModel);
//		catListTableModel.fireTableDataChanged();
		
//		p_Right_Up_Center.add(catListTable);
		JScrollPane p_Right_Up_Center_scroll = new JScrollPane(catListTable);
		TitledBorder a = new TitledBorder("선택한 체크리스트 카테고리와 내용");
		p_Right_Up_Center_scroll.setBorder(a);
		
		// 오른상단 붙이기
		p_Right_Up.add(p_Right_Up_North, BorderLayout.NORTH);
		p_Right_Up.add(p_Right_Up_Center_scroll, BorderLayout.CENTER);
		// ----------------오른상단 완료
		
		
		
		
		// 오른하단 - 보더레이아웃
		p_Right_Down = new JPanel(new BorderLayout());
		
		// 오른하단북단 - 업무 전달할 개발자 선택 라벨 및 개발자초기화 버튼 표시
		p_Right_Down_North = new JPanel(new FlowLayout());
		p_Right_Down_North.add(plzSelectDev = new JLabel("2. 업무를 부여받을 개발자 선택", SwingConstants.CENTER));
		p_Right_Down_North.add(bClearDev = new JButton("개발자 선택 취소"));
		
		
		// 오른하단중앙 - 그리드레이아웃(1,2)
		p_Right_Down_Center = new JPanel(new GridLayout(1, 2)); 
		
		
		// 오른하단중앙왼쪽 - 개발자 목록 테이블
		p_Right_Down_Center_Left = new JPanel(new GridLayout(1, 1));
		devListTableModel = new DevListTableModel();

		try {
			devListTableModel.data = plModel.getDevList(parentPLView.getRecord());
			System.out.println("개발자 리스트 가져오기 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("개발자 리스트 가져오기 실패 " + e.getMessage());
		}
		
		//devListTable.setModel(devListTableModel);
		devListTable = new JTable(devListTableModel);
		devListTableModel.fireTableDataChanged();
		//테이블 붙일 곳
		
		// 테이블에 스크롤바 붙이기
		JScrollPane scrollPane = new JScrollPane(devListTable);
		TitledBorder x = new TitledBorder("현재 프로젝트에 참여 중인 개발자");
		p_Right_Down_Center_Left.setBorder(x);
		p_Right_Down_Center_Left.add(scrollPane);
		
		
		// 오른하단중앙오른 - 선택한 개발자 정보
		p_Right_Down_Center_Right = new JPanel(new GridLayout(2, 2));
		TitledBorder y = new TitledBorder("선택한 개발자 이름과 ID");
		p_Right_Down_Center_Right.setBorder(y);
		p_Right_Down_Center_Right.add(selectedDev = new JLabel("개발자 명 ", SwingConstants.CENTER));
		p_Right_Down_Center_Right.add(tfSelectedDev = new JTextField());
		p_Right_Down_Center_Right.add(selectedDevId = new JLabel("개발자 ID", SwingConstants.CENTER));
		p_Right_Down_Center_Right.add(tfSselectedDevId = new JTextField());
		tfSelectedDev.setEditable(false);
		tfSselectedDevId.setEditable(false);

		// 오른하단중앙 붙이기
		p_Right_Down_Center.add(p_Right_Down_Center_Left);//보더 타이틀을 설정했는데 여기다 스크롤을 넣으니까 보더가 안보이지...
		p_Right_Down_Center.add(p_Right_Down_Center_Right);
		// ----------------오른하단중앙 완료
		
		
		// 오른하단남단 - 해당 개발자에게 전달 라벨 및 전달 버튼
		p_Right_Down_South = new JPanel(new FlowLayout());
		p_Right_Down_South.add(giveChkL = new JLabel("3. 전달버튼 클릭하여 개발자에게 해당 항목을 전달    ", SwingConstants.CENTER));
		p_Right_Down_South.add(bClearAll = new JButton("선택 전체초기화"));
		p_Right_Down_South.add(bSend = new JButton("업무 전달"));
		
		
		// 오른하단 붙이기
		p_Right_Down.add(p_Right_Down_North, BorderLayout.NORTH);
		p_Right_Down.add(p_Right_Down_Center, BorderLayout.CENTER);
		p_Right_Down.add(p_Right_Down_South, BorderLayout.SOUTH);
		// ----------------오른하단 완료
		
		
		// 오른상하단 붙이기		
		p_Right.add(p_Right_Up);
		p_Right.add(p_Right_Down);
		// ----------------오른 완료
		

		
		//왼쪽, 오른쪽 splitpane에 붙이기
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p_Left, p_Right);
		splitPane.setDividerLocation(200);
		add(splitPane);
	}
	
	/**
	 * 체크리스트들을 할당할 개발자들이 담기는 테이블을 만들어줄 클래스
	 * @author TaeJoon
	 *
	 */
	class DevListTableModel extends AbstractTableModel{
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
	
	/**
	 * 할당할 체크리스트들의 카테고리명과 항목명을 담을 테이블 만들어줄 클래스
	 * @author TaeJoon
	 *
	 */
	class CatListTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();
		String [] columnNames = {"카테고리명", "항목내용"};
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