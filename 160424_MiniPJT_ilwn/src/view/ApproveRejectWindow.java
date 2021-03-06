package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Database;
import record.EmpRecord;

/**
 * 게시판 새창, 종류 3개
 * -승인 보류버튼 있는 창
 * -이용가능: PM업무 보고 새창 (승인,보류창), 
 * PL 개발자 게시판 (승인, 보류창)
 * @author yeji
 *
 */
//TODO: 스크롤 추가하기 
public class ApproveRejectWindow extends JFrame implements ActionListener{
	JButton bApprove, bReject;			//승인, 보류 
	JTextArea taPost, taComment;		//댓글 , 글 
	JTextField tfTitle;					//제목 입력칸
	JLabel lId;							//아이디

	EmpRecord rec; 						//사원 아이디
	int post_num;
	String post_title;
	
	PMView pmView;
	
	//승인, 보류 버튼
	public ApproveRejectWindow(EmpRecord rec, int post_num, String post_title, PMView pmView){
		//TODO: EmpRecord 에 따라서 어떤 저장 형태가 달라짐 
		// (PM -> PL) 과 (PL -> PM) 이 있음
		this.rec = rec;
		this.post_num = post_num;
		this.pmView = pmView;
		
		bApprove = new JButton("승인"); 	//TODO: 시간있으면 아이콘으로 바꾸기
		bReject = new JButton("보류");
		
		taPost = new JTextArea(15, 100);
		taComment = new JTextArea(20,100);
		tfTitle = new JTextField(post_title, 30); 
		
		lId = new JLabel(rec.getEmpId()); 
		
		setLayout(new BorderLayout());
		addLayout();
		eventProc();
		
		setTitle("milestone");
		setSize(550,535);
		setVisible(true);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		//PM 이면 taPost -> setEditable(false)
		if( !(rec.getJob() == null) ){		// 임시 - nullpointer 에러방지용
			if(rec.getJob().equals("PM"))
				taPost.setEditable(false);
		}
		p_center.add(taPost);
		p_center.add(taComment);
		
		//버튼 
		JPanel p_south = new JPanel(new GridLayout(1,2));
		p_south.add(bApprove); p_south.add(bReject);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);	
	}
	
	void eventProc(){
		bApprove.addActionListener(this);
		bReject.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		
		if(evt == bApprove){
			System.out.println("승인버튼 클릭");
			pmView.approve("승인", post_num);
			pmView.searchBoard();
			setVisible(false);
		}else if(evt == bReject){
			System.out.println("보류버튼 클릭");
			pmView.approve("보류", post_num);
			pmView.searchBoard();
			setVisible(false);
		}
	}
	
	
//	//TODO: 테스트용 
//	public static void main(String[]args){
//		EmpRecord emp = new EmpRecord();
//		emp.setEmpId("예지");		
//		emp.setJob("PL");
//		new ApproveRejectWindow(emp);
//	}
}