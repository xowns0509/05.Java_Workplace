package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.MileStone;
import module.Login;
import record.EmpRecord;

/**
 * 로그인화면 보여주는 부분
 * @author yeji
 */
public class LoginView extends JPanel implements ActionListener{

	MileStone parent;
	JButton bLogin, bSignup;
	JLabel lId, lPassword;
	JTextField tfId, tfPassword;
	Login login;
	EmpRecord rec = null;
	NewAccount newAcc = null; // 회원가입창 & 기능
	

	public LoginView(MileStone parent){
		
		this.parent = parent;
		login = new Login();
		bLogin = new JButton("로그인");
		bSignup = new JButton("회원가입");

		tfId = new JTextField(15);
		tfPassword = new JTextField(15);

		lId = new JLabel("아이디");
		lPassword = new JLabel("비밀번호");

		setLayout(new BorderLayout());
		JPanel p_north = new JPanel(new GridLayout(2,1));

		//아이디, 비밀번호 부분 붙이기 
		JPanel p_id = new JPanel();
		p_id.add(lId); p_id.add(tfId);
		JPanel p_pw = new JPanel();
		p_pw.add(lPassword); p_pw.add(tfPassword);
		p_north.add(p_id);
		p_north.add(p_pw);

		add(p_north, BorderLayout.CENTER);


		//로그인, 회원가입 버튼 붙이기 
		JPanel p_south = new JPanel(new GridLayout(1,2));
		p_south.add(bLogin); p_south.add(bSignup);

		add(p_south, BorderLayout.SOUTH);

		setVisible(true);
		eventProc();
	}
	/*
	 * 이벤트 핸들러 
	 */
	void eventProc(){
		//로그인 리스너 추가하기 
		bLogin.addActionListener(this);
		bSignup.addActionListener(this);
	}

	/*
	 * 로그인 or 회원가입 
	 */
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		System.out.println("!");
		//로그인 
		if(evt == bLogin){
			try {
				rec = login.login(tfId.getText(), tfPassword.getText());
				if(rec == null) JOptionPane.showConfirmDialog(null,"로그인 실패");
				JOptionPane.showMessageDialog(null, "로그인 성공");
				
				//메인 함수 부르기
				parent.changeView(rec);
			} catch (Exception e1) {
				System.out.println("오류 " + e1.getMessage());
				JOptionPane.showMessageDialog(null, "로그인 실패");
			}
		}else if(evt == bSignup){//회원가입
			//이 버튼이 눌렸을때 
			newAcc = new NewAccount();			
		}
	}	
}
