package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.PLModel;
import model.PMModel;
import module.PLDevBoard;
import module.PMPLBoard;
import record.EmpRecord;

/**
 * 게시판 새창, 종류 3개
 * -승인 보류버튼 있는 창
 * -이용가능: PM업무 보고 새창 (승인,보류창), 
 * PL 개발자 게시판 (승인, 보류창)
 * @author yeji
 * TODO: 수정
 *
 */
//TODO: 스크롤 추가하기 , Object parent 추가 함 
public class ApproveRejectWindow extends JFrame implements ActionListener{

	Object parent;
	JButton bApprove, bReject;			//승인, 보류 
	JTextArea taPost;					//댓글 , 글 
	JTextField tfTitle;					//제목 입력칸
	JLabel lId;							//아이디
	int postNum;
	String postEmpId, postTitle,board;					//작성자 아이디
	/*	PMPLBoard pmplBoard;
	PLDevBoard pldevBoard;
	 */

	EmpRecord rec; 						//사원 아이디

	//승인, 보류 버튼 String board, String postNum, String postEmpId, String postTitle
	//TODO: EmpRecord 에 따라서 어떤 저장 형태가 달라짐 
	// (PM -> PL) 과 (PL -> 개발자) 이 있음		
	public ApproveRejectWindow(EmpRecord rec, Object parent, String board, 
			String postNum, String postEmpId, String postTitle){

		this.parent = parent;	
		this.rec = rec;								//현재 이 창을 부른 사원의 레코드 
		this.postNum = Integer.parseInt(postNum);
		this.postTitle = postTitle;
		this.postEmpId = postEmpId;		
		this.board = board; 						//게시물의 종류 (버그, 업무, 코드)

		addLayout();
		eventProc();
		loadText(); 								//글 가져오기 

	}
	/**
	 * 파일 가져오기 (글)
	 * PM 이면 PMPLBoard, PL 이면 PLDevBoard
	 */
	void loadText(){
		try{
			String text = "";
			System.out.println("현재 사용자의 업무: " + rec.getJob());
			if(rec.getJob().equals("PM")){
				System.out.println("여기여기");
				System.out.println("게시 번호: " + postNum);
				PMPLBoard pmplBoard = new PMPLBoard();
				text = pmplBoard.getContent(postNum);

			}else if(rec.getJob().equals("PL")){
				PLDevBoard pldBoard = new PLDevBoard();
				text = pldBoard.getContent(postNum);

			}	
			taPost.setText(text);
			System.out.println("글 불러오기 성공");
		}catch(Exception e){
			System.out.println("글 불러오기 실패 " + e.getMessage());
			e.printStackTrace();
		}
	}
	/*
	 * 화면구성
	 */
	void addLayout(){
		setLayout(new BorderLayout());

		bApprove = new JButton("승인"); //TODO: 시간있으면 아이콘으로 바꾸기
		bReject = new JButton("보류");

		taPost = new JTextArea(15, 45);
		tfTitle = new JTextField(postTitle, 30); 

		lId = new JLabel(postEmpId);

		//제목 입력칸과 작성자 아이디 
		JPanel p_north = new JPanel(new FlowLayout(FlowLayout.CENTER, 5,0));		

		p_north.add(tfTitle);
		p_north.add(lId);
		System.out.println("addLayout " + postEmpId);

		//업무 보고내용 or 지시내용 text area , 댓글 
		JPanel p_center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,10));


		p_center.add(taPost);
		JScrollPane scroll = new JScrollPane(p_center);

		//버튼 
		JPanel p_south = new JPanel(new GridLayout(1,2));
		p_south.add(bApprove); p_south.add(bReject);

		add(p_north, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);	

		setTitle("milestone");
		setSize(550,380);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * 이벤트 핸들러
	 */
	void eventProc(){
		bApprove.addActionListener(this);
		bReject.addActionListener(this);
	}

	/**
	 * PL, PM  일떄 차별두기 
	 */
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		//update query
		if(evt == bApprove){
			try{
				System.out.println("********************************************");
				System.out.println("게시물 종류 " + board);
				if(rec.getJob().equals("PL")){
					PLDevBoard pldevBoard = new PLDevBoard();
					if(board.equals("버그"))
						pldevBoard.setPostState("해결", postNum);
					else
						pldevBoard.setPostState("승인", postNum);
					PLView tmp = (PLView)parent;
					tmp.searchBoard();
				}else if(rec.getJob().equals("PM")){

					//PMView -> PMModel
					PMPLBoard pmplBoard = new PMPLBoard();
					pmplBoard.setPostState("승인", postNum);
					PMView tmp = (PMView)parent;
					tmp.searchBoard();
				}
				System.out.println("게시물 승인 성공");
			}catch(Exception ex){
				System.out.println("게시물 승인 실패 " + ex.getMessage());
				ex.printStackTrace();
			}
			dispose();
		}else if(evt == bReject){
			try{
				if(rec.getJob().equals("PL")){
					//PLView -> PLModel
					PLDevBoard pldevBoard = new PLDevBoard();		
					if(board.equals("버그"))
						pldevBoard.setPostState("미해결", postNum);
					else
						pldevBoard.setPostState("보류", postNum);
					PLView tmp = (PLView)parent;
					tmp.searchBoard();
				}else if(rec.getJob().equals("PM")){
					//PMView -> PMModel
					PMPLBoard pmplBoard = new PMPLBoard();
					pmplBoard.setPostState("보류", postNum);
					PMView tmp = (PMView)parent;
					tmp.searchBoard();			
				}
				System.out.println("게시물 보류 성공");
			}catch(Exception ex){
				System.out.println("게시물 보류 실패 " + ex.getMessage());
				ex.printStackTrace();
			}
			dispose();
		}
	}
}