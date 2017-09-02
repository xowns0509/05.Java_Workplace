//이건 있어야 함. 왜냐면 layout들은 awt소속이니까 있어야지
import java.awt.*;
import java.awt.event.*;

//x버튼을 눌러도 창만 닫힐 뿐 실제로는 빨간 네모 버튼을 눌러야 함.
import javax.swing.*;


//awt는 운영체제 베이스 UI. 리눅스와 윈도우에서 서로 구동한 화면이 다른데
//스윙으로 한건 둘다 똑같이 나옴

/*Frame
JFrame
Button
JButton
모든 컴포넌트(만) 앞에 대문자 */

class JEventInfoUI {
	//멤버변수 선언
	JFrame f;
	JPanel p_West, p_South, p_East;
	
	JLabel name, tel, jumin, gender, age, addr;
	JTextField tfname, tftel, tfjumin, tfgender, tfage, tfaddr;
	JButton bInsert, bModify, bDelete, bShow, bExit;
	JTextArea ta;
	
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
	//이벤트 처리
	void eventProc(){
		/*[이벤트 처리단계]
		 * 1. 이벤트 listener(인터페이스)를 구현한 클래스 : 이벤트 핸들러 -이벤트 메소드를 오버라이딩
		 * 2. 이벤트 핸들러 객체 생성
		 * 3. 이벤트발생을 원하는 컴포넌트와 2번의 객체를 연결(등록)*/
		
		BtnHdlr bh = new BtnHdlr(); //2. 이벤트 핸들러 객체 생성
		bInsert.addActionListener(bh); //3. 이벤트발생을 원하는 컴포넌트와 2번의 객체를 연결(등록)*/
		bModify.addActionListener(bh); //이벤트 핸들러를 등록했으니 여기다 추가만 하면 됨.
		bDelete.addActionListener(bh); 
		bShow.addActionListener(bh); 
		bExit.addActionListener(bh);
		
		JuminHdler jh = new JuminHdler();
		tfjumin.addFocusListener(jh); //민번창에다가 입력하다 포커스를 잃으면 이벤트 발생
	}
	
	class JuminHdler implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
	
		}

		@Override
/*		public void focusLost(FocusEvent e) { //Jvm이 이 함수를 불러주는거야. 포커스를 잃으면
			String id = tfjumin.getText();
			JOptionPane.showMessageDialog(null, "당신의 주민번호는 "+ id);
		}*/
		
		public void focusLost(FocusEvent e) { //주민번호 입력텍스트 에어리어에 포커스가 있었다가 잃어버리면
			//자동으로 남정네와 나이가 입력되는 것
			//advenced.주민번호에 의한 성별과 나이, 출신도(주소)를 구해서 출력
			String id = tfjumin.getText();
			JOptionPane.showMessageDialog(null, "당신의 주민번호는 "+ id);
			
			char sung1 = id.charAt(7);
			String ageS = id.substring(0, 2);//한 글자 아닌 다수의 문자열을 뽑아냄
			String str = id.substring(8, 10);	// 민번에서 지역을 구분 할 숫자 추출
			int dosi = Integer.parseInt(str);
			int born = Integer.parseInt(ageS);
			int cent = 0;
			
			String dosiStr;
			if( 0 <= dosi && dosi <= 12){
				dosiStr = "서울태생";
				}else if( 13 <= dosi && dosi <= 22 ){
					dosiStr = "인천, 부산태생";
				}else if( 23 <= dosi && dosi <= 38 ){
					dosiStr = "경기태생";
				}else if( 90 <= dosi && dosi <= 99 ){
					dosiStr = "제주태생";
				}else{
					dosiStr = "지역확인 불가, 관리자에게 문의";
				}	
			
			String gender;
			switch(sung1){
			case '1' : cent = 1900; gender = "남자"; break;
			case '2' : cent = 1900; gender = "여자"; break;
			case '3' : cent = 2000; gender = "남자"; break;
			case '4' : cent = 2000; gender = "여자"; break;
			case '9' : cent = 1800; gender = "남자"; break;
			case '0' : cent = 1800; gender = "여자"; break;
			default : cent = 0000; gender = "Unknown"; break;	
			}
			int realAge = cent + born;
			
			tfgender.setText(gender);
			tfage.setText(String.valueOf(2016 - realAge + 1));//String.valueOf(23) : 23을 string으로 바꿔주는 거
			tfaddr.setText(dosiStr);
			
		}
		
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

public class Fr03_GUI_Swing2 {

	public static void main(String[] args) {
	
		JEventInfoUI ui = new JEventInfoUI();
		ui.init();
		ui.addLayout();
		ui.eventProc();/* 메인에서 이렇게 해줘야 위의 이벤트 처리 eventProc();메소드를 실행할 꺼 아냐.
		굳이 이 메소드를 쓰고 싶지 않다면 이 안에 있는 놈들을 죄다 JEventInfoUI() 생성자 안에다 때려박으면 됨.
		단 eventProc();이렇게 구분해주고 싶을 땐 구분해주되 메인에서 ui.eventProc(); 이렇게 불러와야 함.*/
	}
}