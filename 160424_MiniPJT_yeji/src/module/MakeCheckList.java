package module;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;

import model.PLModel;
import record.CheckListRecord;
import view.PLView;

/**
 * 체크리스트 생성 탭 
 * @author yeji
 *
 */
public class MakeCheckList extends JPanel implements ActionListener{

	PLModel db; //디비와 실질적으로 연결하고 기능을 수행하는 클래스
	PLView parent;
	JSplitPane splitPane;
	JTextField tfCatTitle, tfContent; //카테고리 명, 항목내용
	JLabel lbCatTitle, lbContent; //라벨
	JButton bAddContent, bSubmit;
	ArrayList<JTextField> contentList = new ArrayList<JTextField>(); //항목 리스트
	JPanel p_tree, p_createList, p_input, p_cat, p_content, p_buttons;
	Box box;
	Box newBox;
	JTree tree;

	public MakeCheckList(PLView parent){
		this.parent = parent;
		try {
			db = new PLModel();
			System.out.println("체크리스트 생성 db 연결 성공");
		} catch (Exception e) {
			System.out.println("체크리스트 생성 db 연결 실패 " + e.getMessage());
		}
		addLayout();
		eventProc();
	}
	/**
	 * 화면 구성
	 * @author yeji
	 */
	void addLayout(){		
		setLayout(new BorderLayout());
		p_tree = new JPanel(); //왼쪽 체크리스트 뜨는 부분
		p_createList = new JPanel(new BorderLayout()); //체크리스트 생성

		p_input = new JPanel();
		p_cat = new JPanel(new BorderLayout(5,0));
		p_content = new JPanel(new BorderLayout(30,0));

		box = Box.createVerticalBox();

		//라벨, 텍스트필드 
		lbCatTitle = new JLabel("카테고리", JLabel.TRAILING);
		lbContent = new JLabel("항목", JLabel.TRAILING);
		tfCatTitle = new JTextField(20);
		tfContent = new JTextField(15);

		//항목 리스트에 추가 
		contentList.add(tfContent);

		p_cat.add(lbCatTitle,BorderLayout.WEST );
		p_cat.add(tfCatTitle, BorderLayout.CENTER);
		p_content.add(lbContent, BorderLayout.WEST);
		p_content.add(tfContent, BorderLayout.CENTER);

		box.add(p_cat);
		box.add(p_content);
		
		//newBox = box; //박스 저장 
		
		//p_input.add(p_cat, BorderLayout.PAGE_START);
		//p_input.add(p_content, BorderLayout.PAGE_END);
		p_input.add(box);	

		JScrollPane inputView = new JScrollPane(p_input); //TODO: 오토스크롤 추가하기 		

		bAddContent = new JButton("항목추가");
		bSubmit = new JButton("카테고리등록");

		p_buttons = new JPanel(new GridLayout(1,2,5,0));
		p_buttons.add(bAddContent);
		p_buttons.add(bSubmit);

		p_createList.add(p_buttons, BorderLayout.SOUTH);
		p_createList.add(inputView, BorderLayout.CENTER);

		JScrollPane treeView = null;
		//JTree 저장 
		try {
			System.out.println("getTree 호출 전 ");
			tree = new JTree(db.getTree(parent.getRecord()));
			if(tree == null){
				System.out.println("가져올 트리 없음");
				treeView = new JScrollPane(p_tree);
			}else{
				treeView = new JScrollPane(tree);
				System.out.println("여기");
			}
			
			System.out.println("트리 가져오기 성공");
		} catch (Exception e) {
			System.out.println("트리 불러오기 실패 " + e.getMessage());
		}	
		
		//splitpane 에 붙이기
		splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT ,treeView, p_createList);
		splitPane.setDividerLocation(250);
		add(splitPane);
		
	}	

	/**
	 * 이벤트 처리 
	 * @author yeji
	 */
	void eventProc(){
		bSubmit.addActionListener(this);
		bAddContent.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();

		//등록이 되었을때 저장! -> JTree 부분 업데이트 해주기
		if(evt == bSubmit){
			//필요한 레코드들을 생성한다음에 PLModel 로 보내주기
			ArrayList<CheckListRecord> listRec = new ArrayList<CheckListRecord>();

			//카테고리부터 설정 (catNum, listNum, listStat) 은 설정안해도 됨 
			//catNum,listNum 은 sequence 로 
			//listStat은 생성당시에는 없어도 됨 (NULL 값허용)

			for(int i = 0; i < contentList.size(); i++){
				CheckListRecord rec = new CheckListRecord();
				rec.setCatTitle(tfCatTitle.getText()); //카테고리 명 설정
				rec.setContent(contentList.get(i).getText()); //항목설정 
				rec.setpNum(parent.getRecord().getpNum());
				rec.setEmpId(parent.getRecord().getEmpId());

				listRec.add(rec); //추가 				
			}
			try {
				//저장하고 추가부분, contentList 리셋 
				db.addCheckList(listRec);
						
				JOptionPane.showMessageDialog(null, "등록완료");
				
				contentList.clear(); //리셋
				box.removeAll();
				removeAll();
				addLayout();				
				updateUI();
			} catch (Exception e1) {
				System.out.println("등록 실패 " + e1.getMessage());
				e1.printStackTrace();
			}			
		}else if(evt == bAddContent){
			//항목추가! 
			JTextField tmp = new JTextField(15);
			contentList.add(tmp); 

			//panel 에 새로운 textfield 추가
			box.add(tmp);
			p_input.add(box);
			updateUI();
		}		
	}
}
