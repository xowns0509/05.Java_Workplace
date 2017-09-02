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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.PMModel;
import record.EmpRecord;

/**
 * 게시판 새창, 종류 3개
 * -댓글 수정 삭제 등록 있는 창
 * -이용가능: PMPL 업무보고 게시글 새창, 
 * PL 버그리포트 게시글 새창
 * @author yeji
 *
 */

public class PostWindow extends JFrame implements ActionListener{
	JButton bEdit, bDelete, bSubmit;	//승인, 보류 
	JTextArea taPost, taComment;		//댓글 , 글 
	JTextField tfTitle;					//제목 입력칸
	JLabel lId;							//아이디

	EmpRecord rec; 						//사원 아이디
	PMView pmView;

	//승인, 보류 버튼
	public PostWindow(EmpRecord rec, PMView pmView){
		//TODO: EmpRecord 에 따라서 어떤 저장 형태가 달라짐 
		this.rec = rec;
		this.pmView = pmView;
		
		bEdit = new JButton("수정"); 	//TODO: 시간있으면 아이콘으로 바꾸기
		bDelete = new JButton("삭제");
		bSubmit = new JButton("등록");
		
		taPost = new JTextArea(15, 100);
		taComment = new JTextArea(20,100);
		tfTitle = new JTextField("제목", 30); 
		
		lId = new JLabel(rec.getEmpId()); //아이디 보이기		
		
		setLayout(new BorderLayout());
		addLayout();
		eventProc();
		
		setTitle("milestone");
		setSize(550,535);
		setVisible(true);
	}
	
	void eventProc(){
		bSubmit.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		
		if(evt == bSubmit){
			System.out.println("등록버튼 클릭");
			writeBoard();
		}
	}
	
	void writeBoard(){
		String title = tfTitle.getText();
		pmView.uploadBoard(rec, title);
		pmView.searchBoard();
		setVisible(false);
	}
	
	/*
	 * 화면구성
	 */
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
		p_south.add(bEdit); p_south.add(bDelete); 
		// JPanel p_south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p_south.add(bSubmit); 
		
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);	
	}
//	//TODO: 테스트용
//	public static void main(String[]args){
//		EmpRecord emp = new EmpRecord();
//		emp.setEmpId("예지");		
//		emp.setJob("PL");
//		new PostWindow(emp);
//	}
	
}