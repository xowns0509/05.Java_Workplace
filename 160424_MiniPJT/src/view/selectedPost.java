package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Database;
import record.EmpRecord;
import view.DevView.ButtonEventHandler;
import module.BugReportBoard;
import module.PLDevBoard;
import record.PostRecord;		// 게시물의 정보를 담는 클래스

/**
 * 게시판 새창, 종류 3개
 * -댓글 수정 삭제 등록 있는 창
 * -이용가능: PMPL 업무보고 게시글 새창, 
 * PL 버그리포트 게시글 새창
 * @author yeji
 *
 */

public class selectedPost extends JFrame{
	//내가 쓴 글인지 아닌지를 판단하여 수정삭제등록버튼이 보이거나 안보임.

	DevView devViewParent;
	String board, post_num, empId, post_title;

	JButton bEdit, bDelete, bSubmit;	//승인, 보류 
	JTextArea taPost, taComment;		//댓글 , 글 
	JTextField tfTitle;					//제목 입력칸
	JLabel lId, lPost_num, ㅣEmpId, ㅣPost_title;	//아이디, 게시번호, 작성자ID, 글제목
	JCheckBox cbBug, cbCode;

	EmpRecord rec; 						//사원 아이디

	//승인, 보류 버튼
	public selectedPost(EmpRecord rec, DevView devViewParent, String board, String post_num, String empId, String post_title){
		//TODO: EmpRecord 에 따라서 어떤 저장 형태가 달라짐 

		this.rec = rec;
		this.devViewParent = devViewParent;
		
//		this.board
//		post_num
//		empId
//		post_title
		
		//this.board = board;

		bEdit = new JButton("수정"); //TODO: 시간있으면 아이콘으로 바꾸기
		bDelete = new JButton("삭제");
		bSubmit = new JButton("등록");

		//체크박스 
		cbBug = new JCheckBox("버그");
		cbCode = new JCheckBox("코드");

		taPost = new JTextArea(15, 45);
		taComment = new JTextArea(10,40);
		tfTitle = new JTextField(post_title, 30); 

		lId = new JLabel(rec.getEmpId());			//아이디 보이기
		this.lPost_num = new JLabel(post_num);		//글의 게시번호 보이기
		this.ㅣEmpId = new JLabel(empId);			//글의 작성자  보이기
		this.ㅣPost_title = new JLabel(post_title);	// 글의 제목 보이기

		setLayout(new BorderLayout());
		addLayout();
		setTitle("milestone " + board + "게시판 " + empId + "의 글");
		setSize(550,535);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		eventProc();
	}

	// 이벤트 등록
	public void eventProc(){

		//버튼리스너 등록
		ButtonEventHandler btnHandler = new ButtonEventHandler();		

		bEdit.addActionListener(btnHandler);
		bDelete.addActionListener(btnHandler);
		bSubmit.addActionListener(btnHandler);

	}

	// 버튼 이벤트 핸들러 만들기
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();

			if(o==bEdit){				//수정
				JOptionPane.showMessageDialog(null, "수정");
				//JOptionPane.showConfirmDialog(null, "버그리포트 새글");

			}
			else if(o==bDelete){				//삭제
				JOptionPane.showMessageDialog(null, "삭제");

			}			
			else  if(o==bSubmit){				//등록
				submit();

			}

		}
	}

	//글등록
	void submit(){

		PostRecord postRec = new PostRecord();
		int saveBrdrs = 0;

		//1. 화면의 입력값들을 얻어와서 PostRecord 클래스에 넣기(setter/constructer)
		postRec.setFileName("파일네임 넣을 곳");
		postRec.setEmpId(rec.getEmpId());
		postRec.setPostStat("승인대기");
		postRec.setPostTitle(tfTitle.getText());

		// 이 게시물이 코드게시물인지 버그게시물인지 PostType 지정.
		postRec.setPostType(board);

		try{//pldBoard() 호출
			PLDevBoard pldBoard = new PLDevBoard();
			saveBrdrs = pldBoard.submitBoardContent(postRec);

			System.out.println("selectedPost, submit메소드 정상실행");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLexception발생. " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("그 외 예외. " + e.getMessage());
			e.printStackTrace();
		}finally{

		}
		
		devViewParent.searchBoard();
		JOptionPane.showMessageDialog(null, board + "게시물 " + saveBrdrs + "개가 정상 등록되었습니다.");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	//글수정
	void edit(){
		
		PostRecord postRec = new PostRecord();
		int editBrdrs = 0;

		//1. 화면의 입력값들을 얻어와서 PostRecord 클래스에 넣기(setter/constructer)
		postRec.setFileName("수정된 파일네임 넣을 곳");
		postRec.setEmpId(ㅣEmpId.getText()); //원래 작성자의 ID를 넣는 곳
		postRec.setPostStat("승인대기");
		postRec.setPostTitle(tfTitle.getText());

		// 이 게시물이 코드게시물인지 버그게시물인지 PostType 지정.
		postRec.setPostType(board);

		try{//pldBoard() 호출
			PLDevBoard pldBoard = new PLDevBoard();
			editBrdrs = pldBoard.editBoardContent(postRec);

			System.out.println("selectedPost, edit메소드 정상실행");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLexception발생. " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("그 외 예외. " + e.getMessage());
			e.printStackTrace();
		}finally{

		}
		
		devViewParent.searchBoard();
		JOptionPane.showMessageDialog(null, board + "게시물 " + editBrdrs + "개가 정상 수정되었습니다.");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	//글삭제
	void delete(String appr){
		
		PostRecord postRec = new PostRecord();
		int delBrdRs = 0;

		//1. 화면의 입력값들을 얻어와서 PostRecord 클래스에 넣기(setter/constructer)
		postRec.setFileName("수정된 파일네임 넣을 곳");
		postRec.setEmpId(ㅣEmpId.getText()); //원래 작성자의 ID를 넣는 곳
		postRec.setPostStat("승인대기");
		postRec.setPostTitle(tfTitle.getText());

		// 이 게시물이 코드게시물인지 버그게시물인지 PostType 지정.
		postRec.setPostType(board);

		try{//pldBoard() 호출
			PLDevBoard pldBoard = new PLDevBoard();
			delBrdRs = pldBoard.deleteBoardContent(int postNum, String emp_id);

			System.out.println("selectedPost, edit메소드 정상실행");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLexception발생. " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("그 외 예외. " + e.getMessage());
			e.printStackTrace();
		}finally{

		}
		
		devViewParent.searchBoard();
		JOptionPane.showMessageDialog(null, board + "게시물 " + delBrdRs + "개가 정상 수정되었습니다.");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	void addLayout(){
		//제목 입력칸과 작성자 아이디 
		JPanel p_north = new JPanel(new FlowLayout(FlowLayout.CENTER, 5,0));		

		p_north.add(tfTitle);
		p_north.add(lId);		

		//업무 보고내용 or 지시내용 text area , 댓글 
		JPanel p_center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,10));

		p_center.add(taPost);
		p_center.add(taComment);

		//버튼 
		JPanel p_south = new JPanel(new GridLayout(1,3));
		p_south.add(bEdit); p_south.add(bDelete); p_south.add(bSubmit); 

		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);	
	}
	//TODO: 테스트용
	public static void main(String[]args){
		EmpRecord emp = new EmpRecord();
		emp.setEmpId("예지");		
		emp.setJob("PL");
		new selectedPost(emp, null, null, null, null, null);
	}
}