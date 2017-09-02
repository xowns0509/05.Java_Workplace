package module;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

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


public class ApproveCheckList extends JPanel{ 

	PLModel plModel; //디비와 실질적으로 연결하고 기능을 수행하는 클래스

	PLView parentPLView;
	/*Connection con;
	PreparedStatement ps;*/

	JSplitPane splitPane;//화면 왼, 오른 분할
	JButton bDoingTRefresh, bDoingTClear, bDecide,	//목록갱신, 선택항목 초기화, 결정
			bGrant, bGrantYet, bDefer, bCollect;	//승인, 승인대기, 보류, 업무회수
	
	JLabel	lplzDecideStatChkL,			// 라벨: 2-1. 선택한 X개의 항목을
			lplzPushBelow,				// 라벨: 2-2. 결정하시려면 아래버튼을 누르세요
			lmodifyChkL; 				// 라벨: 3. 해당항목 수정
	
	//************ 체크리스트 관리 패널 화면구성************
	JPanel p_Down;		 // 전체 - 상단, 하단 - Grid(2,1)
	
	// 상단 - (체크리스트 현황테이블)
	
	// 하단 - 보더레이아웃
	JPanel p_Down_Center;		// 하단중앙 Grid(1,2)
								// 하단중앙왼쪽 - 선택한 업무들 목록(테이블)
	JPanel p_Down_Center_Right;	// 하단중앙오른 - 상세
	JPanel p_Down_Center_Right_Row2, p_Down_Center_Right_Row3;	// 하단중앙오른 - 승인대기, 승인완료, 보류, 업무회수(미할당) (버튼)
	JPanel p_Down_South;		// 하단남단 - 3. 결정버튼
	
	JTextField tfToModifyStat;
	
	ArrayList<ArrayList<String>> listForModifyARLT = new ArrayList<ArrayList<String>>();// 밑에 걸 죄다 담을 리스트
	ArrayList<String> listForModifyARL;			//0번째: 카테고리명, 1번째: 항목내용
	
	JTable chkListStatTable;				//승인대기, 보류, 작업중의 상태인 체크리스트들 담는 테이블
	ChkListStatTableModel chkListStatTableModel;
	
	JTable chkListForModifyTable;			//상태부여를 위해 선택한 ChkList 저장하는 테이블	
	ChkListForModifyTableModel chkListForModifyTableModel;
	
	CheckList chkL; //chkL = new CheckList() 여기서 선언하려고 하면 트라이케치 잡아야 하므로 불가.
	String listStat = null; //전송시 설정할 상태를 담음.
	
	public ApproveCheckList(PLView parent){
		this.parentPLView = parent; //부모 클래스 저장
		
		try {
			plModel = new PLModel();
			System.out.println("ApproveCheckList db 연결 성공");
		} catch (Exception e) {
			System.out.println("ApproveCheckList db 연결 실패 " + e.getMessage());
		}

		addLayout();	
		eventProc();
		refreshSelectedChkLTable();
	}

	/**
	 * ApproveCheckList 이벤트 추가 
	 */
	void eventProc(){
		ButtonEventHandler btnHandler = new ButtonEventHandler();
		
		bDoingTRefresh.addActionListener(btnHandler);
		bDoingTClear.addActionListener(btnHandler);
		bDecide.addActionListener(btnHandler);
		bGrant.addActionListener(btnHandler);
		bGrantYet.addActionListener(btnHandler);
		bDefer.addActionListener(btnHandler);
		bCollect.addActionListener(btnHandler);

		//체크리스트 상태 테이블 리스너 등록
		chkListStatTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = chkListStatTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				//선택한 체크리스트의 항목고유번호 갖고 오기
				String listNum = (String)chkListStatTable.getValueAt(row, col); //테이블 넘버가 String이라면 
				System.out.println("누른 번호: "+ listNum);
				//devTnum = (Integer)devTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
								
				//선택한 체크리스트의 항목내용 갖고 오기
				String empName = (String)chkListStatTable.getValueAt(row, 2);
				System.out.println("항목내용: "+ empName);
				
				//개발자 ID 갖고 오기
				String empId = (String)chkListStatTable.getValueAt(row, 3); 
				System.out.println("empId: "+ empId);

				//선택한 항목이 이미 테이블에 있는지 확인
				for(int i = 0 ; i < listForModifyARLT.size() ; i++){
					
					if (listForModifyARLT.get(i).get(0).equals(listNum)){
						System.out.println("항목중복선택으로 추가기능 건너뜀");
						return;
					}
				}
				
				// 선택한 체크리스트 수정대상 테이블에 넣기
				addChkListForModifyTable(listNum, empName, empId);
				refreshSelectedChkLTable();
			}
		});
		
		//수정 할 테이블 리스너 등록
		chkListForModifyTable.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = chkListForModifyTable.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 글 번호만 갖고 오도록.
				
				//선택한 체크리스트의 항목고유번호 갖고 오기
				String listNum = (String)chkListForModifyTable.getValueAt(row, col); //테이블 넘버가 String이라면 
				System.out.println("누른 번호: "+ listNum);
				//devTnum = (Integer)devTable.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				
				// 선택한 항목, 테이블에서 지우기
				delChkListFromModifyTable(listNum);
				refreshSelectedChkLTable();
			}
		});
	}
	
	/**
	 * 선택한 체크리스트 수정대상 테이블에 넣기
	 * @author TaeJoon
	 */
	void addChkListForModifyTable(String listNum, String empName, String empId){
		
		listForModifyARL = null;
		listForModifyARL = new ArrayList<String>();
		listForModifyARL.add(listNum);
		listForModifyARL.add(empName);
		listForModifyARL.add(empId);
		
		listForModifyARLT.add(listForModifyARL);	
	}
	
	/**
	 * 선택한 체크리스트 수정대상 테이블에서 지우기
	 * @author TaeJoon
	 */
	void delChkListFromModifyTable(String listNum){
		
		//선택한 항목이 이미 테이블에 있는지 확인
		for(int i = 0 ; i < listForModifyARLT.size() ; i++){
			
			if (listForModifyARLT.get(i).get(0).equals(listNum)){
				listForModifyARLT.remove(i);
			}
		}
	}

	/**
	 * 업무할당 패널 버튼 이벤트 클래스
	 * @author TaeJoon
	 */
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();

			if(evt == bDoingTRefresh){		//목록갱신
				refreshChkLStatTable();
				
			}else if(evt == bDoingTClear){	//선택항목 초기화
				listForModifyARL.clear();
				listForModifyARLT.clear();
				refreshSelectedChkLTable();
				
			}else if(evt == bGrant){		//승인
				tfToModifyStat.setText("승인으로 설정합니다.");
				listStat = "승인";
				
			}else if(evt == bGrantYet){		//승인대기
				tfToModifyStat.setText("승인대기로 설정합니다.");
				listStat = "승인대기";
				
			}else if(evt == bDefer){		//보류
				tfToModifyStat.setText("보류로 설정합니다.");
				listStat = "보류";
				
			}else if(evt == bCollect){		//업무회수
				tfToModifyStat.setText("미할당으로 설정합니다.");
				listStat = "미할당";
				
			}else if(evt == bDecide){//결정
				
				if(   tfToModifyStat.getText().equals("")   ){
					//버튼 선택 유무
					
					JOptionPane.showMessageDialog(null, " 수정할 상태를 선택하세요");
					return;
					
				}else if(   listForModifyARLT.size() == 0   ){
					// 0번째 칸 할당내용의 공백 확인
					
					JOptionPane.showMessageDialog(null, "현황테이블에서 상태를 수정할 항목을 선택하세요");
					return;
					
				}
				
				//체크리스트 상태 수정 실행
				modifyChkList();//이 함수가 정상적으로 실행 되었을 때만 모든 저장된 값 초기화.
				
			}
		}
	}
	
	/**
	 * 선택된 항목 상태 수정
	 * @author Taejun
	 */
	void modifyChkList(){

		//필요한 레코드들을 생성한다음에 CheckList.java 로 보내주기
		ArrayList<CheckListRecord> listRec = new ArrayList<CheckListRecord>();

		for(int i = 0; i < listForModifyARLT.size(); i++){
			
			CheckListRecord rec = new CheckListRecord();
			
			//체크리스트 레코드에 담기
			//listForModifyARLT.get(i).get(0) i번째 행의 항목고유번호
			//listForModifyARLT.get(i).get(1) i번째 행의 항목내용
			//listForModifyARLT.get(i).get(2) i번째 행의 개발자ID
			
			rec.setListNum(Integer.parseInt(listForModifyARLT.get(i).get(0)));//항목고유번호
			rec.setContent(listForModifyARLT.get(i).get(1));	//항목내용 설정. where로 조건용
			rec.setListStat(listStat);						//버튼으로 설정한 상태 저장

			if(listStat.equals("미할당")){
				//업무회수일 때는 ID가 개발자에서 PL(지금 로그인한 놈이 PL이니까 이놈 ID)로 바뀜.
				rec.setEmpId(parentPLView.getRecord().getEmpId());//할당 할 개발자의 id. 불러온거 그대로. WHERE조건에 사용.				
			
			}else{
				//업무회수가 아닐때는 설정되어 있는 ID그대로 감.
				rec.setEmpId(listForModifyARLT.get(i).get(2));	//할당 할 개발자의 id. 불러온거 그대로. WHERE조건에 사용.
			
			}

			listRec.add(rec); //추가
		}
		

		try{
			
			chkL = new CheckList();
			//저장하고 추가부분, contentList 리셋			
			chkL.modifyARLChkList(listRec);
					
			JOptionPane.showMessageDialog(null, "업데이트 완료");

		} catch (Exception e1) {
			System.out.println("등록 실패 " + e1.getMessage());
			e1.printStackTrace();
		}finally{
		}
		
		// 선택했던 항목 테이블, 현황테이블 초기화
		listForModifyARL.clear();
		listForModifyARLT.clear();
		refreshChkLStatTable();
		refreshSelectedChkLTable();
		tfToModifyStat.setText("");
		listStat = null; //버튼상태 초기화

	}
	

	
	/**
	 * 승인대기, 보류, 작업중의 상태인 체크리스트들 담는 테이블 갱신용
	 */
	void refreshChkLStatTable(){
		
		try {
			chkL = new CheckList();
			
			chkListStatTableModel.data = chkL.chkLStatTableARL(parentPLView.getRecord().getpNum(), "확인용");
			System.out.println("체크리스트 상태테이블 가져오기 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("체크리스트 상태테이블 가져오기 실패 " + e.getMessage());
		}
		
		chkListStatTable.setModel(chkListStatTableModel);
		chkListStatTableModel.fireTableDataChanged();
		
	}
	
	/**
	 * 수정하려 선택된 체크리스트들 담는 테이블 갱신용
	 */
	void refreshSelectedChkLTable(){
		chkListForModifyTableModel.data = listForModifyARLT;
		chkListForModifyTable.setModel(chkListForModifyTableModel);
		chkListForModifyTableModel.fireTableDataChanged();
		
	}
	
	/**
	 * 패널 구성 
	 */
	void addLayout(){
		
		//************ 체크리스트 관리 패널 화면구성 ************
		setLayout(new GridLayout(2,1));

		
		// 상단 - 현재 작업 중인 업무들 목록(테이블)
		
		// 하단 - 보더레이아웃
		// 하단중앙왼쪽 - 선택한 업무들 목록(테이블)
		// 하단중앙오른 - 상세
		// 하단중앙오른 - 승인대기, 승인완료, 보류, 업무회수(미할당) (버튼)
		// 하단남단 - 3. 결정버튼
		
		// 상단 - 현재 작업 중인 업무들 목록(테이블)
		chkListStatTable = new JTable();
		chkListStatTableModel = new ChkListStatTableModel();
		refreshChkLStatTable();
		
		//스크롤바 추가
		JScrollPane scroll_p_Up_Center = new JScrollPane(chkListStatTable);
		TitledBorder x = new TitledBorder("1. 작업중, 승인대기, 보류 중인 체크리스트 현황");
		scroll_p_Up_Center.setBorder(x);
		
		
		
		
		// 하단 - 보더레이아웃
		p_Down = new JPanel(new BorderLayout());
		
		// 하단중앙
		p_Down_Center = new JPanel(new GridLayout(1,2));
		
		// 하단중앙왼쪽 - 선택한 업무들 목록(테이블)		
		chkListForModifyTable = new JTable();	
		chkListForModifyTableModel = new ChkListForModifyTableModel();
		refreshSelectedChkLTable();

		//스크롤바 추가
		JScrollPane scroll_p_Down_Center_Left = new JScrollPane(chkListForModifyTable);
		TitledBorder y = new TitledBorder("2. 수정할 체크리스트 항목");
		scroll_p_Down_Center_Left.setBorder(y);

		
		
		
		// 하단중앙오른 - 상세
		p_Down_Center_Right = new JPanel(new GridLayout(5,1));
		TitledBorder z = new TitledBorder("3. 업데이트 할 상태 선택");
		p_Down_Center_Right.setBorder(z);
		
		// 하단중앙오른 - 승인대기, 승인완료, 보류, 업무회수(미할당) (버튼)
		// 하단중앙오른 2번째 줄
		p_Down_Center_Right_Row2 = new JPanel(new GridLayout(1,2));
		p_Down_Center_Right_Row2.add(bGrant = new JButton("1. 승인"));
		p_Down_Center_Right_Row2.add(bGrantYet = new JButton("2. 승인대기"));
		
		// 하단중앙오른 3번째 줄
		p_Down_Center_Right_Row3 = new JPanel(new GridLayout(1,2));	
		p_Down_Center_Right_Row3.add(bDefer = new JButton("3. 보류"));
		p_Down_Center_Right_Row3.add(bCollect = new JButton("4. 업무회수"));
		
		
		p_Down_Center_Right.add(lplzDecideStatChkL = new JLabel("2-1. 선택한 항목을", SwingConstants.CENTER));
		p_Down_Center_Right.add(p_Down_Center_Right_Row2);
		p_Down_Center_Right.add(p_Down_Center_Right_Row3);
		p_Down_Center_Right.add(tfToModifyStat = new JTextField());
		p_Down_Center_Right.add(lplzPushBelow = new JLabel("2-2. 결정하시려면 아래버튼을 누르세요", SwingConstants.CENTER));
		tfToModifyStat.setEditable(false);
		tfToModifyStat.setHorizontalAlignment(JTextField.CENTER);
		
		// 하단중앙 붙이기
		p_Down_Center.add(scroll_p_Down_Center_Left);
		p_Down_Center.add(p_Down_Center_Right);
		
		
		
		
		// 하단남단 - 3. 결정버튼
		p_Down_South = new JPanel();
		Border loweredetchedC = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		p_Down_South.setBorder(loweredetchedC);
		p_Down_South.add(bDoingTRefresh = new JButton("목록갱신"));
		p_Down_South.add(bDoingTClear = new JButton("선택항목 초기화"));
		p_Down_South.add(lmodifyChkL = new JLabel("3. 해당항목 수정", SwingConstants.CENTER));
		p_Down_South.add(bDecide = new JButton("결정"));
		
		// 하단 화면 붙이기
		//p_Down.add(p_Down_North, BorderLayout.NORTH);
		p_Down.add(p_Down_Center, BorderLayout.CENTER);
		p_Down.add(p_Down_South, BorderLayout.SOUTH);
		// ----------------하단 완료(최종적으로 부착하는 .add는 맨 아래쪽에.)
		
		add(scroll_p_Up_Center);
		add(p_Down);

	}
	
	
	/**
	 * 승인대기, 보류, 작업중의 상태인 체크리스트들 담는
	 * 테이블을 만들어줄 클래스. 5열짜리
	 * @author TaeJoon
	 *
	 */
	class ChkListStatTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();
		String [] columnNames = {"항목고유번호", "카테고리", "항목내용", "담당자", "상태"};
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
	 *  수정하려 선택된 체크리스트들 담는 테이블
	 *  만들어줄 클래스. 3열짜리 
	 * @author TaeJoon
	 *
	 */
	class ChkListForModifyTableModel extends AbstractTableModel{
		ArrayList data = new ArrayList();
		String [] columnNames = {"항목고유번호", "항목내용", "담당자"};
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