package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import module.PLDevBoard;
import record.EmpRecord;
import record.PostRecord;		// 게시물의 정보를 담는 클래스

/**
 * 게시판 새창, 종류 3개
 * -댓글 수정 삭제 등록 있는 창
 * -이용가능: PMPL 업무보고 게시글 새창, 
 * PL 버그리포트 게시글 새창
 * @author yeji
 *
 */

public class SelectedPost extends JFrame{
	//내가 쓴 글인지 아닌지를 판단하여 수정삭제등록버튼이 보이거나 안보임.

	Object parent;

	//DevView devViewParent;
	//PLView plViewParent;
	
	int postNum;
	String postEmpId,postTitleboard, board, empId, postTitle;

	JButton bEdit, bDelete;	//승인, 보류 
	JTextArea taPost;	//글 
	JTextField tfTitle;					//제목 입력칸
	JLabel lbId, lbPost_num, lbEmpId, lbPost_title;	//아이디, 게시번호, 작성자ID, 글제목
	JCheckBox cbBug, cbCode;

	EmpRecord rec; 						//사원 아이디

	/**
	 * 개발자의 생성자
	 * @param rec
	 * @param devViewParent
	 * @param board
	 * @param post_num
	 * @param empId
	 * @param post_title
	 */
	public SelectedPost(EmpRecord rec, Object parent, String board, String postNum, String postEmpId, String postTitle){

		this.rec = rec;
		this.parent = parent;
		this.postEmpId = postEmpId;
		this.postTitle = postTitle;
		this.board = board;
		
		bEdit = new JButton("수정"); //TODO: 시간있으면 아이콘으로 바꾸기
		bDelete = new JButton("삭제");

		//체크박스 
		cbBug = new JCheckBox("버그");
		cbCode = new JCheckBox("코드");

		taPost = new JTextArea(15, 45);
		tfTitle = new JTextField(postTitle, 30); 

		//정보 저장 
		this.postNum = Integer.parseInt(postNum);

		//라벨 저장
		lbId = new JLabel(postEmpId);				//게시물 작성자 아이디 보이기
		lbPost_num = new JLabel(postNum);		//글의 게시번호 보이기
		//	lbEmpId = new JLabel(empId);			//글의 작성자  보이기
		lbPost_title = new JLabel(postTitle);	// 글의 제목 보이기

		setLayout(new BorderLayout());
		addLayout();
		eventProc();
		loadText();
	}	

	/**
	 * 이벤트 등록
	 * 수정, 삭제 권한이 없는 사용자에게는 수정 삭제 버튼에 리스너를 등록하지 않음
	 * @author taejun, yeji
	 */
	public void eventProc(){
		//버튼리스너 등록
		ButtonEventHandler btnHandler = new ButtonEventHandler();		

		//수정, 삭제 권한이 없는 사용자에게는 수정 삭제 버튼에 리스너를 등록하지 않음
		if(hasAccess()){
			bEdit.addActionListener(btnHandler);
			bDelete.addActionListener(btnHandler);
		}
	}

	// 버튼 이벤트 핸들러 만들기
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();

			if(o == bEdit){//수정
				try {					
					edit();
					dispose();
					System.out.println("수정 성공");
				} catch (Exception e) {
					System.out.println("수정 실패 " + e.getMessage());
					e.printStackTrace();
				}				
			}else if(o == bDelete){//삭제
				try {

					delete();
					dispose();
					System.out.println("삭제 성공");
				} catch (Exception e) {
					System.out.println("삭제 실패 " + e.getMessage());
					e.printStackTrace();
				}				
			}
		}
	}
	/**
	 * 파일 가져오기 (글)
	 */
	void loadText(){
		try{
			PLDevBoard pldBoard = new PLDevBoard();
			String text = pldBoard.getContent(postNum);
			taPost.setText(text);
			System.out.println("글 불러오기 성공");
		}catch(Exception e){
			System.out.println("글 불러오기 실패 " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 글 수정하는 메소드 
	 * 변경되는 사항: post_file, post_title, post_time
	 * (post_title은 변경될수도있고 안될수도있지만 일관성을 위해
	 * 항상 변경)
	 * 필요한 정보: post_num 
	 * @author yeji 
	 */
	void edit() throws Exception{
		int ret = 0;
		PostRecord postRec = new PostRecord();

		//1. 화면의 입력값들을 얻어와서 PostRecord 클래스에 넣기(setter/constructer)
		postRec.setEmpId(postEmpId); //원래 작성자의 ID를 넣는 곳

		if(board.equals("버그")){
			postRec.setPostStat("미해결");
			System.out.println("******************************");
			System.out.println("버그 수정 !!!!!!!!!!!!!!!!!!!!!!!!!");
		}else if(board.equals("공지사항")){
			postRec.setPostStat(board);
		}else
			postRec.setPostStat("승인대기"); //수정했으면 수정대기로 상태가 바뀌어야함 
		
		postRec.setPostTitle(tfTitle.getText());
		postRec.setPostContent(taPost.getText());		

		// 이 게시물이 코드게시물인지 버그게시물인지 PostType 지정.
		postRec.setPostType(board);

		PLDevBoard pldBoard = new PLDevBoard();
		ret = pldBoard.editBoardContent(postRec, postNum);

		//목록 새로고침 (해당 클래스로 불러주기)
		if(rec.getJob().equals("개발자")){
			DevView dev = (DevView)parent;
			dev.searchBoard();
		}else if(rec.getJob().equals("PL")){
			PLView pl = (PLView)parent;
			pl.searchBoard();
		}else if(rec.getJob().equals("PM")){
			PMView pm = (PMView)parent;
			pm.searchBoard();
		}
	}

	/**
	 * 글을 삭제 하는 메소드
	 * 사용자의 아이디와 게시물작성자아이디가 불일치 하면 
	 * 삭제 버튼 비활성화
	 * 필요한 정보 :post_num, 현재 로그인한 사람 아이디, 게시물작성자 아이디
	 * @author yeji
	 */
	void delete() throws Exception{
		PostRecord postRec = new PostRecord();

		//화면의 입력값들을 얻어와서 PostRecord 클래스에 넣기(setter/constructer)

		//pldBoard() 호출
		PLDevBoard pldBoard = new PLDevBoard();
		pldBoard.deleteBoardContent(postNum);

		if(rec.getJob().equals("개발자")){
			DevView dev = (DevView)parent;
			dev.searchBoard();
		}else if(rec.getJob().equals("PL")){
			PLView pl = (PLView)parent;
			pl.searchBoard();
		}else if(rec.getJob().equals("PM")){
			PMView pm = (PMView)parent;
			pm.searchBoard();
		}
	}
	
	/**
	 * 로그인한 아이디와 작성자와 비교 
	 * @return 일치하지 않으면 false, 일치하면 true
	 * @author yeji
	 */
	boolean hasAccess(){
		System.out.println(postEmpId + " **** " + rec.getEmpId());
		if(!postEmpId.equals(rec.getEmpId())){					
			return false;
		}
		return true;
	}
	/**
	 * 화면구성 
	 * @author taejun
	 */
	void addLayout(){
		//제목 입력칸과 작성자 아이디 
		JPanel p_north = new JPanel(new FlowLayout(FlowLayout.CENTER, 5,0));		

		p_north.add(tfTitle);
		p_north.add(lbId);		

		//업무 보고내용 or 지시내용 text area , 댓글 
		JPanel p_center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,10));

		p_center.add(taPost);
		JScrollPane scroll = new JScrollPane(p_center);


		//버튼 
		JPanel p_south = new JPanel(new GridLayout(1,2));
		p_south.add(bEdit); p_south.add(bDelete);

		add(p_north, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);

		setSize(550,380);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}