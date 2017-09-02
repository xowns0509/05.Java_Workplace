package Jva2Orcl_GUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

class JEventInfoUI {
	//멤버변수 선언
	JFrame f;
	JPanel p_West, p_South, p_East;
	
	JLabel name, tel, jumin, gender, age, addr;
	JTextField tfname, tftel, tfjumin, tfgender, tfage, tfaddr;
	JButton bInsert, bModify, bDelete, bShow, bExit;
	JTextArea ta;
	
	Mo06_Database db;
	
	//생성자(객체생성)
	JEventInfoUI(){
		f = new JFrame("사용자 정보 입력 창");//제목

		bInsert = new JButton("입력", new ImageIcon("src/img/mac_os_X.png"));
		//여기서 new ImageIcon("src/img/add-button.png") 안해주면
		//객체 생성시부터 이미지가 없다면 롤오버나 클릭시 변경되는 아이콘이 안뜸.
		bModify = new JButton("수정");
		bDelete = new JButton("삭제");
		bShow = new JButton("전체보기");
		bExit = new JButton("종료");
		
		ta = new JTextArea("텍스트 에어리어");
		
		//db연결
		try {
			db = new Mo06_Database();
			ta.setText("db연결성공 !");
		} catch (Exception e) {
			ta.setText("db연결 실패: !" + e.getMessage());
			e.printStackTrace();
		}
		
		tfname = new JTextField(5);
		tftel = new JTextField();
		tfjumin = new JTextField();
		tfgender = new JTextField();
		tfage = new JTextField();
		tfaddr = new JTextField();
		
		name = new JLabel("이름", JLabel.LEFT);
		tel = new JLabel("전화", JLabel.LEFT);
		jumin = new JLabel("주민 하이픈까지 다 치세요~", JLabel.LEFT);
		gender = new JLabel("성별", JLabel.LEFT);
		age = new JLabel("나이", JLabel.LEFT);
		addr = new JLabel("주소", JLabel.LEFT);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	void init(){//마우스를 올려놨을 때 그림이 바뀌는 거
		bInsert.setRolloverIcon(new ImageIcon("src/img/9_mac_os_X.png"));//마우스 오버시 그림변경
		bInsert.setPressedIcon(new ImageIcon("src/img/desktop_mac_48px.png"));//마우스 클릭시 그림변경
		bInsert.setToolTipText("이것은 입력버튼이니라"); //툴팁
		bInsert.setMnemonic('i'); //alt + i 눌렀을 때 실행
	}
	
	void saveData(){
		//1. 화면에서 사용자의 입력값들을 얻어오기
		//String tel = tftel.getText();		
		//2. Database의 insert() 를 호출. 그리고 1번 값을 넘기기
		

	}
	
	void srchData(){
		//1. 화면에서 사용자의 입력값들을 얻어오기
		//String tel = tftel.getText();		
		//2. Database의 insert() 를 호출. 그리고 1번 값을 넘기기
		
		try{
			int result = db.insert(new Record2(tftel.getText(), tfname.getText(), tfjumin.getText(), tfage.getText(), tfgender.getText(), tfaddr.getText()));
			ta.setText(result + "행 입력했어.");
			System.out.println(result + " 행을 수행했어");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			// 끝까지 인식 못하면 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}
	}
	
	//이벤트 처리
	void eventProc(){
		
		BtnHdlr bh = new BtnHdlr();
		bInsert.addActionListener(bh);
		bModify.addActionListener(bh);
		bDelete.addActionListener(bh); 
		bShow.addActionListener(bh); 
		bExit.addActionListener(bh);
		
	}
	
	//이벤트 핸들러
	class BtnHdlr implements ActionListener{//import java.awt.event.*
		//1. 이벤트 listner(인터페이스)를 구현한 클래스 : 이벤트 핸들러 -이벤트 메소드를 오버라이딩
		//import java.awt.* *은 클래스만 의미하는 것일 뿐 그 안에 있는 패키지는 또 import 시켜야 함.

		@Override
		public void actionPerformed(ActionEvent e) {
//			Object evt = (JButton)e.getSource();
			
			JButton evt = (JButton)e.getSource();
			if(evt == bInsert){
				JOptionPane.showMessageDialog(null, "정보를 입력합니다");
				saveData();
			}else if(evt == bModify){
				JOptionPane.showMessageDialog(null, "정보를 수정합니다");
			}else if(evt == bDelete){
				JOptionPane.showMessageDialog(null, "정보를 삭제합니다");
			}else if(evt == bShow){
				JOptionPane.showMessageDialog(null, "정보를 조회합니다");
			}else if(evt == bExit){
				System.exit(0);
			}
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(null, "정보를 입력합니다");
//			} 
		} 
		
	}
	
	//화면구성 및 보이기
	void addLayout(){
		
		f.setSize(650, 480);
		f.setVisible(true); //어떤 버튼을 누르면 뜨는 창 같은거를 truefalse로 onoff
		f.setLayout(new BorderLayout());
		
		JPanel p_west = new JPanel();
		p_west.setLayout(new GridLayout(6, 2));
		p_west.add(name);
		p_west.add(tfname);
		
		p_west.add(tel);
		p_west.add(tftel);
		
		p_west.add(jumin);
		p_west.add(tfjumin);
		
		p_west.add(gender);
		p_west.add(tfgender);
		
		p_west.add(age);
		p_west.add(tfage);
		
		p_west.add(addr);
		p_west.add(tfaddr);	
		f.add(p_west, BorderLayout.WEST);
		
		JPanel p_south = new JPanel();
		p_south.setLayout(new GridLayout(1, 5));
		p_south.add(bInsert);
		p_south.add(bModify);
		p_south.add(bDelete);
		p_south.add(bShow);
		p_south.add(bExit);
		f.add(p_south, BorderLayout.SOUTH);
		
		f.add(ta, BorderLayout.CENTER);

	}
}

public class Mo05_GUI_Swing_PhoneNum {

	public static void main(String[] args) {
	
		JEventInfoUI ui = new JEventInfoUI();
		ui.init();
		ui.addLayout();
		ui.eventProc();/* 메인에서 이렇게 해줘야 위의 이벤트 처리 eventProc();메소드를 실행할 꺼 아냐.
		굳이 이 메소드를 쓰고 싶지 않다면 이 안에 있는 놈들을 죄다 JEventInfoUI() 생성자 안에다 때려박으면 됨.
		단 eventProc();이렇게 구분해주고 싶을 땐 구분해주되 메인에서 ui.eventProc(); 이렇게 불러와야 함.*/
	}
}