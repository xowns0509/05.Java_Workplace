package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import record.EmpRecord;

/**
 * 게시판 새창, 종류 3개
 * -코드, 버그 게시글 
 * -이용가능: 개발자 버그 리포트 
 * @author yeji
 *
 */
public class PostWithCheckBox extends JFrame{
	JButton bEdit, bDelete, bSubmit;	//승인, 보류 
	JTextArea taPost, taComment;		//댓글 , 글 
	JTextField tfTitle;					//제목 입력칸
	JLabel lId;							//아이디
	JCheckBox cbBug, cbCode;
	
	EmpRecord rec; 						//사원 아이디
	
	//승인, 보류 버튼
	public PostWithCheckBox(EmpRecord rec){
		//TODO: EmpRecord 에 따라서 어떤 저장 형태가 달라짐 
		
		this.rec = rec;
		bEdit = new JButton("수정"); //TODO: 시간있으면 아이콘으로 바꾸기
		bDelete = new JButton("삭제");
		bSubmit = new JButton("등록");
		
		//체크박스 
		cbBug = new JCheckBox("버그");
		cbCode = new JCheckBox("코드");
		
		taPost = new JTextArea(15, 45);
		taComment = new JTextArea(10,40);
		tfTitle = new JTextField("제목", 30); 
		
		lId = new JLabel(rec.getEmpId()); //아이디 보이기		
		
		setLayout(new BorderLayout());
		addLayout();
		setTitle("milestone");
		setSize(550,535);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JPanel p_comment = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		JPanel p_checkboxes = new JPanel(new GridLayout(2,1));
		p_checkboxes.add(cbBug); p_checkboxes.add(cbCode);
		
		p_comment.add(p_checkboxes, FlowLayout.LEFT);
		p_comment.add(taComment, FlowLayout.CENTER );
		p_center.add(p_comment);
		
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
		new PostWithCheckBox(emp);
	}
}