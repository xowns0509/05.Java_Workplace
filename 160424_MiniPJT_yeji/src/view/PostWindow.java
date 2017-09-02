package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

import javax.swing.JButton;
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

public class PostWindow extends JFrame{

	//DevView devViewParent;
	Object parent;
	String board;

	JButton bSubmit;					//승인, 보류 
	JTextArea taPost;					//댓글 , 글 
	JTextField tfTitle;					//제목 입력칸
	JLabel lId;							//아이디

	int num;							//파일명 정할때 sequence 같은 역할
	EmpRecord rec; 						//사원 아이디

	//승인, 보류 버튼
	public PostWindow(EmpRecord rec, Object parent, String board){

		this.rec = rec;
		//this.devViewParent = devViewParent;
		this.parent = parent;
		this.board = board;

		bSubmit = new JButton("등록");

		taPost = new JTextArea(15, 45);
		tfTitle = new JTextField("제목", 30); 

		lId = new JLabel(rec.getEmpId()); //아이디 보이기		

		addLayout();
		eventProc();
	}

	// 이벤트 등록
	public void eventProc(){
		//버튼리스너 등록
		ButtonEventHandler btnHandler = new ButtonEventHandler();		
		bSubmit.addActionListener(btnHandler);
	}

	/**
	 * 버튼 이벤트 핸들러 
	 * TODO: 닫기 버튼 눌렀을때 dispose 부르기
	 * @author user
	 *
	 */
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			if(o==bSubmit){				//등록
				try{
					submit();
					dispose();
					System.out.println("등록 성공");
				}catch (Exception e) {
					e.printStackTrace();
					System.out.println("등록 실패 " + e.getMessage());
				}
			}
		}
	}

	/**
	 * 등록 버튼을 눌렀을때 불려지는 메소드 
	 * 현재 창의 정보를 저장한후에 PLDevBoard의 submitBoardContent 를 호출
	 * @throws Exception 등록하면서 생기는 모든 예외
	 * @author yeji
	 */
	void submit() throws Exception{

		PostRecord postRec = new PostRecord();
		int saveBrdrs = 0;

		//파일명: 제목 (데이터베이스에서 설정)
		String fileName = rec.getEmpId() + String.valueOf(num); 
		num++; //sequence 역할 		

		//1. 화면의 입력값들을 얻어와서 PostRecord 클래스에 넣기(setter/constructer)
		postRec.setFileName("");
		postRec.setEmpId(rec.getEmpId());
		postRec.setPostStat("승인대기");
		postRec.setPostTitle(tfTitle.getText());
		postRec.setpNum(rec.getpNum());
		postRec.setFileName(fileName);
		postRec.setPostContent(taPost.getText());

		//파일 저장
		String filePath = "src/" + fileName;
		//File outFile = new File(fileName);
		FileOutputStream fos = new FileOutputStream(filePath);
		byte[] contents = taPost.getText().getBytes();
		fos.write(contents);
		fos.close();		

		postRec.setFilePath(filePath);


		// 이 게시물이 코드게시물인지 버그게시물인지 PostType 지정.
		postRec.setPostType(board);
		System.out.println(board);

		//pldBoard() 호출
		PLDevBoard pldBoard = new PLDevBoard();
		saveBrdrs = pldBoard.submitBoardContent(postRec);

		//목록 새로고침 (해당 클래스로 불러주기)
		if(rec.getJob().equals("개발자")){
			DevView dev = (DevView)parent;
			dev.searchBoard();
		}else if(rec.getJob().equals("PL")){
			PLView pl = (PLView)parent;
			pl.searchBoard();
		}else if(rec.getJob().equals("PM")){
			PMView pm = (PMView)parent;
			//TODO 추가
		}
		//devViewParent.searchBoard();
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * 화면설정
	 * @author taejun	 
	 */
	void addLayout(){
		setLayout(new BorderLayout());
		//제목 입력칸과 작성자 아이디 
		JPanel p_north = new JPanel(new FlowLayout(FlowLayout.CENTER, 5,0));		

		p_north.add(tfTitle);
		p_north.add(lId);		

		//업무 보고내용 or 지시내용 text area , 댓글 
		JPanel p_center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,10));

		p_center.add(taPost);
		JScrollPane scroll = new JScrollPane(p_center);		

		//버튼 
		JPanel p_south = new JPanel(new BorderLayout());
		p_south.add(bSubmit, BorderLayout.CENTER); 

		add(p_north, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);

		setTitle("milestone " + board + " 새글 등록하기");
		setSize(550,380); //535
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
}