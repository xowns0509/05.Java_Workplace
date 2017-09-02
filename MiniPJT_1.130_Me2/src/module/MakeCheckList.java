package module;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.PLModel;
import record.CheckListRecord;
import view.PLView;

/**
 * 체크리스트 생성 탭 
 * @author yeji
 *
 */
public class MakeCheckList extends JPanel{

	//PLModel db; //디비와 실질적으로 연결하고 기능을 수행하는 클래스
	
	JPanel p_Tree, p_Right, p_Right_Center, p_category, p_content, p_Right_South_buttons, lbContent_txtFieldnum;
	
	PLView parentPLView;
	JSplitPane splitPane;
	JTextField tfCatTitle, tfContent; //카테고리 명, 항목내용
	JLabel lbCatTitle, lbContent; //라벨
	JButton bAddContent, bSubmit;
	
	ArrayList<JTextField> contentList;
	
	DefaultMutableTreeNode teamRoot; //팀 전체 체크리스트 트리 받을 놈
	
	JScrollPane teamTree;
	
	Box x_AxisBox = new Box(BoxLayout.X_AXIS);//항목내용, 항목라벨 담을 칸
	Box v_LayoutBox;//세로 배치용 

	JTree tree;
	CheckList teamChkl;
	PLModel plModel = new PLModel();

	DefaultTreeModel tModel;
	//model.reload()
	//or
	

	
	int txtFieldnum = 0;

	public MakeCheckList(PLView parent){
		this.parentPLView = parent;
		contentList = new ArrayList<JTextField>(); //항목 텍필드 리스트
		
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
	}

	/**
	 * //트리 및 체크리스트 삽입 패널 갱신용 
	 * @author Taejun
	 */
	public DefaultMutableTreeNode treeRefresh(){
		
		DefaultMutableTreeNode teamRoot = new DefaultMutableTreeNode();

		teamRoot = plModel.teamChklist("1");
		
		return teamRoot;
	}
	

	/**
	 * 화면 구성
	 * @author yeji
	 */
	void addLayout(){
		
		setLayout(new BorderLayout());
		
		teamRoot = new DefaultMutableTreeNode();
		//teamRoot = new DefaultMutableTreeNode();
		
		//************체크리스트 생성 패널 화면구성************
		// 패널 - 왼쪽, 오른

		// 왼쪽 - 팀 전체 체크리스트 트리
		
		// 오른 - 체크리스트 삽입 패널, 항목추가 버튼, 카테고리등록 버튼
		// 오른상단 
		// 오른중앙 - 체크리스트 삽입 패널
		// 오른하단 - 항목추가 버튼, 카테고리등록 버튼
		
		
		
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
		
		
		// 오른 - 체크리스트 삽입 패널, 항목추가 버튼, 카테고리등록 버튼
		p_Right = new JPanel(new BorderLayout());
		
		// 오른상단 - 카테고리 명 입력 패널
		JPanel p_Right_North = new JPanel(new FlowLayout());
		p_Right_North.add(lbCatTitle = new JLabel("카테고리", JLabel.TRAILING));
		p_Right_North.add(tfCatTitle = new JTextField(20));

		// 오른중앙 - 체크리스트 삽입 패널
		p_Right_Center = new JPanel();
		
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
		
		p_Right_Center.add(v_LayoutBox);
		//p_Right_Center = contentTfList();
		
		//카테고리, 항목 입력패널에 오토스크롤 추가
		JScrollPane p_Right_Center_Input = new JScrollPane(p_Right_Center);
		
		
		// 오른하단 - 항목추가 버튼, 카테고리등록 버튼
		p_Right_South_buttons = new JPanel(new GridLayout(1,2,5,0));
		p_Right_South_buttons.add(bAddContent = new JButton("항목추가"));//항목추가버튼
		p_Right_South_buttons.add(bSubmit = new JButton("카테고리등록"));//카테고리등록 버튼
		
		
		// 오른 - 중앙, 하단 붙임
		p_Right.add(p_Right_North, BorderLayout.NORTH);
		p_Right.add(p_Right_Center_Input, BorderLayout.CENTER);
		p_Right.add(p_Right_South_buttons, BorderLayout.SOUTH);

		
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
		//listStat은 생성당시에는 없어도 됨 (NULL 값허용)
		


		for(int i = 0; i < contentList.size(); i++){
			CheckListRecord rec = new CheckListRecord();
			rec.setCatTitle(tfCatTitle.getText()); //카테고리 명 설정
			rec.setContent(contentList.get(i).getText()); //항목설정 
			rec.setpNum(parentPLView.getRecord().getpNum());
			rec.setEmpId(parentPLView.getRecord().getEmpId());

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
	
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();

			//등록이 되었을때 저장! -> JTree 부분 업데이트 해주기
			if(evt == bSubmit){
				
				if(tfCatTitle.getText().equals("") == true){
					
					//카테고리명 공백 확인
					JOptionPane.showMessageDialog(null, "카테고리명에 공백은 있을 수 없어");
					return;
					
				}else{
					
					//항목내용 공백 확인
					for(int i = 0; i < contentList.size(); i++){
						if( contentList.get(i).getText().equals("") == true ){
							JOptionPane.showMessageDialog(null, "항목내용에 공백은 있을 수 없어");
							return;
						}
					}
					
				}
				
				//체크리스트 전송 실행
				submitChkList();
				
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
				
				
				p_Right_Center.removeAll();//패널 지우기
				//p_Right_Center = null; 
				//p_Right_Center = new JPanel();
				
				p_Right_Center.add(v_LayoutBox);
				p_Right_Center.revalidate();
				System.out.println("bSubmit 버튼실행");

				
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
				//버티컬 레이아웃 박스에 담은 놈은 p_Right_Center에 담겨있을 테니
				
				
				p_Right_Center.revalidate();
				//5. p_Right_Center를 revalidate로 갱신시켜줌.				
				//updateUI(); - revalidate 있으니 안써도 됨.

			}		
		}
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