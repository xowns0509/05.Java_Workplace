package module;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.PLModel;
import module.ApproveCheckList.ChkListForModifyTableModel;
import module.GiveCheckList.TreeEventHandler;
import record.CheckListRecord;
import view.PLView;

/**
 * 체크리스트 생성 탭 
 * @author yeji
 *
 */
public class MakeCheckList extends JPanel{

	//PLModel db; //디비와 실질적으로 연결하고 기능을 수행하는 클래스
	JPanel lbContent_txtFieldnum;
	
	//************체크리스트 생성 패널 화면구성************
	// 패널 - p_Tree 왼쪽, p_Right 오른
	
	// 왼쪽 - 팀 전체 체크리스트 트리, 오른 - 상단, 하단
	JPanel p_Tree, p_Right;

	// 오른상단
	JPanel p_Right_Up;
	
	JPanel p_Right_Up_North; 		// 오른상단북쪽
	JPanel p_Right_Up_Center; 		// 오른상단중앙
	JPanel p_Right_Up_Center_Left;	// 오른상단중앙왼쪽
	JPanel p_Right_Up_Center_Right; // 오른상단중앙오른
	
	JPanel p_Right_Up_Center_Right_Center;	// 오른상단중앙오른중앙 - 체크리스트 삽입 패널
	JPanel p_Right_Up_Center_Right_South;	// 오른상단중앙오른남측 - 항목리셋 버튼, 항목추가 버튼, 카테고리등록 버튼
	
	JPanel p_Right_Down;		// 오른하단
	JPanel p_Right_Down_North;	// 오른하단북단 - 삭제라디오버튼, 삭제라벨, 목록초기화, 삭제버튼
								// 오른하단중앙 - 테이블
	
	PLView parentPLView;
	JSplitPane splitPane;
	JTextField tfCatTitle, tfContent; //카테고리 명, 항목내용
	JLabel lbCatTitle, lbContent, lbSubmit, lbDelete; //라벨
	JButton bAddContent, bSubmit, bContentClear, bWillbeDeleteTableClear, bDelete;
	
	ArrayList<JTextField> contentList;
	
	DefaultMutableTreeNode teamRoot; //팀 전체 체크리스트 트리 받을 놈
	
	JScrollPane teamTree;
	
	Box x_AxisBox = new Box(BoxLayout.X_AXIS);//항목내용, 항목라벨 담을 칸
	Box v_LayoutBox;//세로 배치용 

	JTree tree;
	CheckList teamChkl;
	PLModel plModel;

	JRadioButton rbuttonAdd, rbuttonDel;
	
	DefaultTreeModel tModel;
	//model.reload()
	//or
	
	//삭제대상 목록
	ArrayList<ArrayList<String>> listForDeleteARLT = new ArrayList<ArrayList<String>>();
	ArrayList<String> listForDeleteARL;			//0번: 항목고유번호번호, 1번째: 항목내용???
	
	//GUI로 안보이는 ARL - 프로그램 실행시 팀 내 체크리스트의 정보를 담고 있음.
	ArrayList<ArrayList<String>> chkLARLT = new ArrayList<ArrayList<String>>();
	ArrayList<String> chkLARL;					//GUI로 안보이는 어레이리스트

	JTable chkListForDeleteTable;				//삭제를 위해 임시로 선택한 ChkList 저장하는 테이블
	ChkListForDeleteTableModel chkListForDeleteTableModel;
	
	
	int txtFieldnum = 0;

	public MakeCheckList(PLView parent){
		this.parentPLView = parent;
		contentList = new ArrayList<JTextField>(); //항목 텍필드 리스트
		try {
			plModel = new PLModel();
			System.out.println("MakeCheckList: PLModel 접속 성공 ");
		} catch (Exception e) {
			System.out.println("MakeCheckList: PLModel 접속 실패 " + e.getMessage());
			e.printStackTrace();
		}
		addLayout();
		eventProc();
	}
	

	/**
	 * 이벤트 처리 
	 * @author yeji
	 */
	void eventProc(){
		ButtonEventHandler btnHandler = new ButtonEventHandler();
		
		bSubmit.addActionListener(btnHandler);	//항목추가
		bAddContent.addActionListener(btnHandler);//리스트등록
		bContentClear.addActionListener(btnHandler);
		bWillbeDeleteTableClear.addActionListener(btnHandler);
		bDelete.addActionListener(btnHandler);
		
		//트리선택 리스너 등록
		TreeEventHandler tevHandler = new TreeEventHandler();
		tree.addTreeSelectionListener(tevHandler);
		
		//삭제 할 테이블 리스너 등록
		chkListForDeleteTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = chkListForDeleteTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				//선택한 체크리스트의 항목고유번호 갖고 오기
				String listNum = (String)chkListForDeleteTable.getValueAt(row, col); //테이블 넘버가 String이라면 
				System.out.println("누른 번호: "+ listNum);
				//devTnum = (Integer)devTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				
				// 선택한 항목, 테이블에서 지우기
				delChkListFromModifyTable(listNum);
				refreshWillBeDeletedChkLTable();
			}
		});	
	}
	
	/**
	 * 선택한 체크리스트 수정대상 테이블에서 지우기
	 * @author TaeJoon
	 */
	void delChkListFromModifyTable(String listNum){
		
		//선택한 항목이 이미 테이블에 있는지 확인
		for(int i = 0 ; i < listForDeleteARLT.size() ; i++){
			
			if (listForDeleteARLT.get(i).get(0).equals(listNum)){
				listForDeleteARLT.remove(i);
			}
		}
	}
	
	/**
	 * 승인대기, 보류, 작업중의 상태인 체크리스트들 담는 어레이리스트 5열짜리
	 * 메모리 상에만 저장해 놓고 GUI로 뿌리지 않음
	 * "항목고유번호", "카테고리", "항목내용", "담당자", "상태"
	 * @author TaeJoon
	 *
	 */
	void refreshChkLStatTable(){
		
		System.out.println("MakeCheckList, refreshChkLStatTable 메소드 실행");
		
		try{
			teamChkl = new CheckList();
			chkLARLT = teamChkl.chkLStatTableARL(parentPLView.getRecord().getpNum(), "삭제용");
			System.out.println("chkLARLT의 크기: " + chkLARLT.size());
			
			for(int i = 0; i <  chkLARLT.size() ; i++){
				System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(0));
				System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(1));
				System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(2));
				System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(3));
				System.out.println("chkLARLT의 내용출력: " + chkLARLT.get(i).get(4));
			}
			
			System.out.println("체크리스트 상태테이블 가져오기 성공");
		} catch (Exception e) {
			System.out.println("체크리스트 상태테이블 가져오기 실패" + e.getMessage());
			e.printStackTrace();
			
		}finally{
		}
		
	}
	
	/**
	 * 삭제하려 선택된 체크리스트들 담는 테이블 갱신용
	 */
	void refreshWillBeDeletedChkLTable(){
		chkListForDeleteTableModel.data = listForDeleteARLT;
		chkListForDeleteTable.setModel(chkListForDeleteTableModel);
		chkListForDeleteTableModel.fireTableDataChanged();
		
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
			 
			if(rbuttonAdd.isSelected()){//추가 라디오버튼 눌렸을 때
				
				if (node.isLeaf() == false & node.isRoot() == false) {
					//트리에서 선택한 노드가 부모가 아니고 맨 끝의 자식도 아닐 때.
					tfCatTitle.setText(nodeInfo.toString().substring(nodeInfo.toString().indexOf(". ")+2));

				}
				
			}else if(rbuttonDel.isSelected()){//삭제 라디오버튼 눌렸을 때

				if (node.isLeaf()) {
					
					//트리에서 선택한 노드가 테이블에 이미 있는지 확인
					for(int i = 0; i < listForDeleteARLT.size(); i++){
							
						if(listForDeleteARLT.get(i).get(1).equals(node.getParent().toString().substring(node.getParent().toString().indexOf(". ")+2)) == true & 
								listForDeleteARLT.get(i).get(2).equals(nodeInfo.toString()) == true){
							//catListARLT.get(i).get(1) i번째 행의 카테고리명
							//catListARLT.get(i).get(2) i번째 행의 항목내용

							//카테고리명과 항목내용을 한꺼번에 비교하여 이에 해당한 경우 삽입 방지

							JOptionPane.showMessageDialog(null, "항목의 중복선택은 있을 수 없습니다.");
							return;
						}
					}
					
					//트리에서 선택한 노드정보로 이전에 갖고온 테이블에서 확인
					for(int i = 0; i < chkLARLT.size(); i++){
						if(   chkLARLT.get(i).get(1).equals(node.getParent().toString().substring(node.getParent().toString().indexOf(". ")+2)) == true &
								chkLARLT.get(i).get(2).equals(nodeInfo.toString()) == true ){
							//listForDeleteARLT.get(i).get(1) 카테고리명
							//listForDeleteARLT.get(i).get(2) 항목명을 동시에 비교
							
							listForDeleteARL = null;
							listForDeleteARL = new ArrayList<String>();
							
							//트리에서 선택한 정보를 기반으로 chkLARLT내 카테고리와 항목내용, 상태를 listForDeleteARL에 저장
							listForDeleteARL.add(chkLARLT.get(i).get(0)); // 리스트 고유번호
							listForDeleteARL.add(chkLARLT.get(i).get(1)); // 카테고리명
							listForDeleteARL.add(chkLARLT.get(i).get(2)); // 항목명
							listForDeleteARL.add(chkLARLT.get(i).get(4)); // 상태확인
							
							//위의 두 내용을 한 행에 담아 줄 ARLT에 저장
							listForDeleteARLT.add(listForDeleteARL);
							
						}
					}
					
					//참고출력용
					System.out.println("THE ROOT NODE IS: "+node.getParent().toString());
					System.out.println(" CHILD NODE IS: "+nodeInfo.toString());
					
					// 삭제예정 체크리스트 테이블 갱신
					refreshWillBeDeletedChkLTable();
				}
			}
		}
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
	 * 화면 구성
	 * @author yeji
	 */
	void addLayout(){
		
		setLayout(new BorderLayout());
		refreshChkLStatTable();
		teamRoot = new DefaultMutableTreeNode();
		//teamRoot = new DefaultMutableTreeNode();
		
		//************체크리스트 생성 패널 화면구성************
		// 패널 - p_left 왼쪽, p_Right 오른
		
		// 왼쪽 - 팀 전체 체크리스트 트리
		// 오른 - 상단, 하단
		
		// 왼쪽 - 팀 전체 체크리스트 트리
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
		// ----------------왼쪽 완료(최종적으로 부착하는 스플릿페인은 맨 아래쪽에.)
		
		
		
		
		// 오른 - 상단, 하단
		p_Right = new JPanel(new GridLayout(2,1));

		// 오른상단
		p_Right_Up = new JPanel(new BorderLayout());

		// 오른상단북쪽
		p_Right_Up_North = new JPanel();
		p_Right_Up_North.add(rbuttonAdd = new JRadioButton("1. 카테고리 및 항목 추가", true));
		//p_Right_Up_North.add(lbSubmit = new JLabel("1. 카테고리 및 항목 추가", SwingConstants.CENTER));
		p_Right_Up_North.add(bContentClear =new JButton("항목 및 트리 초기화"));
		//Border loweredetchedC = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		//p_Right_Up_North.setBorder(loweredetchedC);		
		
		// 오른상단중앙
		p_Right_Up_Center = new JPanel(new GridLayout(1,2));
		
		// 오른상단중앙왼쪽 - 카테고리 명 입력 패널
		p_Right_Up_Center_Left = new JPanel(new GridLayout(2,1));
		p_Right_Up_Center_Left.add(lbCatTitle = new JLabel("카테고리", JLabel.CENTER));
		p_Right_Up_Center_Left.add(tfCatTitle = new JTextField(20));
		tfCatTitle.setHorizontalAlignment(JTextField.CENTER);
		TitledBorder y = new TitledBorder("1-1. 카테고리를 선택 혹은 작성하세요.");
		p_Right_Up_Center_Left.setBorder(y);
		
		// 오른상단중앙오른
		p_Right_Up_Center_Right = new JPanel(new BorderLayout());
		TitledBorder z = new TitledBorder("1-2. 항목내용을 작성하세요");
		p_Right_Up_Center_Right.setBorder(z);
		
		// 오른상단중앙오른중앙 - 체크리스트 삽입 패널
		p_Right_Up_Center_Right_Center = new JPanel();
		
		// 1. 항목라벨과 항목텍필드를 담을 x축배열 박스 생성
		v_LayoutBox = Box.createVerticalBox();
		
		contentList.add(tfContent = new JTextField(15)); //항목tf용 텍필드를 ARL에 추가
		
		//2. x_AxisBox에 항목라벨과 항목텍필드를 담는다.
		x_AxisBox.add(lbContent = new JLabel("항목 "+String.valueOf(txtFieldnum) + " :  ", JLabel.TRAILING));
		x_AxisBox.add(contentList.get(txtFieldnum));
				
		//x축배열 박스대신 패널로 써도 상관 없다.
//		lbContent_txtFieldnum = new JPanel();
//		lbContent_txtFieldnum.add(lbContent = new JLabel("항목"+String.valueOf(txtFieldnum), JLabel.TRAILING));
//		lbContent_txtFieldnum.add(contentList.get(txtFieldnum));

		//4. 항목라벨과 항목텍필드를 담은 x_AxisBox를 버티컬 레이아웃 박스에 담는다.
		v_LayoutBox.add(x_AxisBox);
		
		p_Right_Up_Center_Right_Center.add(v_LayoutBox);
		//p_Right_Up_Center_Right_Center = contentTfList();
		
		//카테고리, 항목 입력패널에 오토스크롤 추가
		JScrollPane scroll_p_Right_Up_Center_Right_Center = new JScrollPane(p_Right_Up_Center_Right_Center);
		
		// 오른상단중앙오른남측 - 항목리셋 버튼, 항목추가 버튼, 카테고리등록 버튼
		p_Right_Up_Center_Right_South = new JPanel(new GridLayout(1,2,5,0));
		p_Right_Up_Center_Right_South.add(bAddContent = new JButton("항목추가"));//항목추가버튼
		p_Right_Up_Center_Right_South.add(bSubmit = new JButton("카테고리등록"));//카테고리등록 버튼
		
		// 오른상단중앙오른 붙이기
		p_Right_Up_Center_Right.add(scroll_p_Right_Up_Center_Right_Center, BorderLayout.CENTER);
		p_Right_Up_Center_Right.add(p_Right_Up_Center_Right_South, BorderLayout.SOUTH);
		
		// 오른상단중앙 붙이기
		p_Right_Up_Center.add(p_Right_Up_Center_Left);
		p_Right_Up_Center.add(p_Right_Up_Center_Right);
		
		// 오른상단 붙이기
		p_Right_Up.add(p_Right_Up_North, BorderLayout.NORTH);
		p_Right_Up.add(p_Right_Up_Center, BorderLayout.CENTER);		
		//----------------- 여기까지 오른상단 끝
		
		
		
		
		// 오른하단
		p_Right_Down = new JPanel(new BorderLayout());
		
		// 오른하단상단 - 삭제라디오버튼, 삭제라벨, 목록초기화, 삭제버튼
		p_Right_Down_North = new JPanel();
		p_Right_Down_North.add(rbuttonDel = new JRadioButton("2. 카테고리 및 항목 삭제", false));
		//p_Right_Down_North.add(lbDelete = new JLabel("2. 카테고리 및 항목 삭제", SwingConstants.CENTER));
		p_Right_Down_North.add(bWillbeDeleteTableClear =new JButton("목록초기화"));
		p_Right_Down_North.add(bDelete =new JButton("카테고리 및 항목 삭제"));
		
		// 오른하단중앙 - 테이블
		chkListForDeleteTable = new JTable();	
		chkListForDeleteTableModel = new ChkListForDeleteTableModel();
		refreshWillBeDeletedChkLTable();
		
		//스크롤바 추가
		JScrollPane scroll_p_Down_Center = new JScrollPane(chkListForDeleteTable);
		TitledBorder a = new TitledBorder("2. 삭제할 체크리스트 항목");
		scroll_p_Down_Center.setBorder(a);
		
		// 버튼그룹지정하기
		ButtonGroup rbuttonGroup = new ButtonGroup();
		rbuttonGroup.add(rbuttonAdd);
		rbuttonGroup.add(rbuttonDel);

		// 오른 - 중앙, 하단 붙임
		p_Right_Down.add(p_Right_Down_North, BorderLayout.NORTH);
		p_Right_Down.add(scroll_p_Down_Center, BorderLayout.CENTER);

		
		
		
		// 오른 - 중앙, 하단 붙임
		p_Right.add(p_Right_Up);
		p_Right.add(p_Right_Down);

		
		//왼쪽, 오른쪽 splitpane에 붙이기
		splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT ,teamTree, p_Right);
		splitPane.setDividerLocation(250);
		add(splitPane);
		
	}	

	/**
	 * 체크리스트 등록
	 */
	void submitChkList(){

		//필요한 레코드들을 생성한다음에 PLModel 로 보내주기
		ArrayList<CheckListRecord> listRec = new ArrayList<CheckListRecord>();

		//카테고리부터 설정 (catNum, listNum, listStat) 은 설정안해도 됨 
		//catNum,listNum 은 sequence 로 
		//listStat은 생성당시에는 '미할당'으로 지정

		for(int i = 0; i < contentList.size(); i++){
			CheckListRecord rec = new CheckListRecord();
			rec.setCatTitle(tfCatTitle.getText()); //카테고리 명 설정
			rec.setContent(contentList.get(i).getText()); //항목설정 
			rec.setpNum(parentPLView.getRecord().getpNum());
			rec.setEmpId(parentPLView.getRecord().getEmpId());
			rec.setListStat("미할당");
			
			listRec.add(rec); //추가 				
		}
		
		try{
			
			teamChkl = new CheckList();
			//저장하고 추가부분, contentList 리셋			
			teamChkl.addARLChkList(listRec);
					
			JOptionPane.showMessageDialog(null, "등록완료");


		} catch (Exception e1) {
			System.out.println("등록 실패 " + e1.getMessage());
			e1.printStackTrace();
		}finally{

		}

	}
	
	/**
	 * 체크리스트 삭제
	 */
	void deleteChkList(){

		//필요한 레코드들을 생성한 다음 체크리스트.java 로 보내주기
		ArrayList<CheckListRecord> listRec = new ArrayList<CheckListRecord>();

		//우선 선택한 항목 중 "승인", "작업 중", ""레코드들을 생성한 다음 체크리스트.java 로 보내주기
		for(int i = 0; i < listForDeleteARLT.size(); i++){

			CheckListRecord rec = new CheckListRecord();

			rec.setListNum(Integer.parseInt(listForDeleteARLT.get(i).get(0))); //리스트번호 넣기
			rec.setContent(listForDeleteARLT.get(i).get(2));  //항목설정 

			listRec.add(rec); //추가 				
		}
		
		try{
			
			teamChkl = new CheckList();
			//저장하고 추가부분, contentList 리셋			
			teamChkl.delARLChkList(listRec);
					
			JOptionPane.showMessageDialog(null, "삭제완료");

		} catch (Exception e1) {
			System.out.println("등록 실패 " + e1.getMessage());
			e1.printStackTrace();
		}finally{

		}

	}
	
	
	/**
	 *  수정하려 선택된 체크리스트들 담는 테이블
	 *  만들어줄 클래스. 5열짜리 
	 * @author TaeJoon
	 *
	 */
	class ChkListForDeleteTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();
		String [] columnNames = {"항목고유번호", "카테고리명", "항목내용", "상태"};
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
	
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();

			//등록이 되었을때 저장! -> JTree 부분 업데이트 해주기
			if(evt == bSubmit){
				
				if(tfCatTitle.getText().equals("") == true){
					
					//카테고리명 공백 확인
					JOptionPane.showMessageDialog(null, "카테고리명에 공백은 있을 수 없습니다");
					return;
					
				}else{
					
					//항목내용 공백 확인
					for(int i = 0; i < contentList.size(); i++){
						if( contentList.get(i).getText().equals("") == true ){
							JOptionPane.showMessageDialog(null, "항목내용에 공백은 있을 수 없습니다");
							return;
						}
					}
					
				}
				
				//체크리스트 전송 실행
				submitChkList();
				
				//추가목록 초기화 실행
				clearTextfield();
				
				refreshWillBeDeletedChkLTable();
				refreshChkLStatTable();
				treeRefresh();
				
				System.out.println("bSubmit 버튼실행");
				
			}else if(evt == bAddContent){
				// 항목라벨과 항목텍필드 추가

				txtFieldnum += 1;
				System.out.println("txtFieldnum출력: "+txtFieldnum);
				System.out.println("contentList사이즈 체크: "+ contentList.size() );
				
				contentList.add(tfContent = new JTextField(15));//항목tf용 ARL에 추가
				
				
				x_AxisBox = new Box(BoxLayout.X_AXIS); // 1. 항목라벨과 항목텍필드를 담을 또하나의 x축배열 박스 생성
				JPanel lbContent_txtFieldnum = new JPanel();//x축배열박스대신 패널로 써도 상관 없다.
				//2. 중요한 건 추가 할 항목라벨과 항목텍필드를 담을 x축배열박스 혹은 패널이 지역변수여야 함.
				//그렇지 않을 경우(전역 변수일 경우) 밑이 아니라 오른쪽으로 계속 추가됨.
				//추가확인 - 전역변수로 바꿔 봤는데 제대로 된다. 상관 없을 듯
				
				
				x_AxisBox.add(lbContent = new JLabel("항목 "+String.valueOf(txtFieldnum) + " :  ", JLabel.TRAILING));
				x_AxisBox.add(contentList.get(txtFieldnum));
				//3. x_AxisBox에 항목라벨과 항목텍필드를 담는다.
				
				
				v_LayoutBox.add(x_AxisBox);
				//4. 항목라벨과 항목텍필드를 담은 x_AxisBox를 버티컬 레이아웃 박스에 담는다.
				//버티컬 레이아웃 박스에 담은 놈은 p_Right_Up_Center_Right_Center에 담겨있을 테니
				
				
				p_Right_Up_Center_Right_Center.revalidate();
				//5. p_Right_Up_Center_Right_Center를 revalidate로 갱신시켜줌.				
				//updateUI(); - revalidate 있으니 안써도 됨.

			}else if(evt == bContentClear){

				//추가목록 초기화 실행
				clearTextfield();
				
			}else if(evt == bWillbeDeleteTableClear){
				
				listForDeleteARL.clear();
				listForDeleteARLT.clear();				
				//refreshChkLStatTable();
				refreshWillBeDeletedChkLTable();
				
			}else if(evt == bDelete){

				//추가목록 초기화 실행
				deleteChkList();
				
				listForDeleteARL.clear();
				listForDeleteARLT.clear();
				refreshChkLStatTable();
				refreshWillBeDeletedChkLTable();
				
				
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
				//eventProc();					//tree를 새로 할당했으므로
												//다시 이벤트 부여하기 위해 eventProc() 메소드 재실행
												//추가 - 이걸 통째로 했더니 항목박스 추가가 2개씩 증가해서 추가됨.
												//그래서 트리만 다시 리스너 등록함.
				
				//트리선택 리스너 등록
				TreeEventHandler tevHandler = new TreeEventHandler();
				tree.addTreeSelectionListener(tevHandler);
			}
		}
	}
	
	/**
	 * 항목필드 초기화
	 */
	void clearTextfield(){
		
		//항목라벨, 텍필드 비우기 실행
		tfCatTitle.setText("");			//카테고리 텍필드 초기화
		txtFieldnum = 0; 				//항목텍필드 ARL 배열넘버 초기화
		
		contentList.clear(); //리셋		//항목텍필드 ARL 초기화
		//contentList = null;
		//contentList = new ArrayList<JTextField>();
		
		x_AxisBox.removeAll();			//항목텍필드, 항목라벨 담는 x축배열박스 지우기(박스컨테이너)
		//x_AxisBox = null;
		//x_AxisBox = new Box(BoxLayout.X_AXIS);
		
		v_LayoutBox.removeAll();		//v_LayoutBox 지우기
		//v_LayoutBox = null;
		//v_LayoutBox  = Box.createVerticalBox();

		
		//항목tf용 텍필드를 ARL에 추가
		contentList.add(tfContent = new JTextField(15)); 				

		//2. x_AxisBox에 항목라벨과 항목텍필드를 담는다.
		x_AxisBox.add(lbContent = new JLabel("항목 "+String.valueOf(txtFieldnum) + " :  ", JLabel.TRAILING));
		x_AxisBox.add(contentList.get(txtFieldnum));

		//4. 항목라벨과 항목텍필드를 담은 x_AxisBox를 버티컬 레이아웃 박스에 담는다.
		v_LayoutBox.add(x_AxisBox);
		
		
		p_Right_Up_Center_Right_Center.removeAll();//패널 지우기
		//p_Right_Up_Center_Right_Center = null; 
		//p_Right_Up_Center_Right_Center = new JPanel();
		
		p_Right_Up_Center_Right_Center.add(v_LayoutBox);
		p_Right_Up_Center_Right_Center.revalidate();


		
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
		//eventProc();					//tree를 새로 할당했으므로
										//다시 이벤트 부여하기 위해 eventProc() 메소드 재실행
										//추가 - 이걸 통째로 했더니 항목박스 추가가 2개씩 증가해서 추가됨.
										//그래서 트리만 다시 리스너 등록함.
		
		//트리선택 리스너 등록
		TreeEventHandler tevHandler = new TreeEventHandler();
		tree.addTreeSelectionListener(tevHandler);
	}

}
//사용 못한 것들

//model = (DefaultTreeModel)tree.getModel();
//model.reload(teamRoot);
//여기서는 추가가 아니라 그냥 db에서 통째로 다 불러오는 것이므로 reload가 필요 없음.


//teamRoot = parentPLView.treeRefresh();
//teamRoot = plModel.teamChklist("1");


//p_Tree = new JPanel(new BorderLayout());
//tree = new JTree(parentPLView.treeRefresh());
//p_Tree.add(tree);

//tree.removeAll();//JTree 지우기
//teamRoot.removeAllChildren();
//teamRoot = treeRefresh();
//tree.set(teamRoot);;

//teamRoot = treeRefresh();
//model.reload(teamRoot);

//teamRoot.removeAllChildren();	//날렸어

//JOptionPane.showMessageDialog(null, teamRoot == null);


//tree.revalidate();
//tModel.reload(teamRoot);		//
//tree.revalidate();
//tree.setVisible(false);
//tree.revalidate();
//tree.setVisible(true);



//model = (DefaultTreeModel)tree.getModel();
//model.reload(teamRoot);
			
//teamRoot.
//teamTree.removeAll();
//splitPane.removeAll();


//updateUI();
//tree = treeRefresh();
//tree.fireTreeStructureChanged();