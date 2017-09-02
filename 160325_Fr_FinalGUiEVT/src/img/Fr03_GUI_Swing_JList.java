package img;
//스윙, 단축키, 마우스오버, 클릭 시 아이콘등록. x버튼 프로그램종료.
/* 이건 있어야 함.
왜냐면 컴포넌트 들만 swing소속 일 뿐layout들은 awt 소속이니까 있어야지.*/
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//x버튼을 눌러도 창만 닫힐 뿐 실제로는 빨간 네모 버튼을 눌러야 함.*/

/* awt는 운영체제 베이스 UI.
 * 리눅스와 윈도우에서 서로 구동한 화면이 다른데
 * 스윙으로 한건 둘 다 똑같이 나옴.
 * 
 * awt로 구현한 컴포넌트를 Swing으로 변환 시
 * 컴포넌트 클래스이름들 앞에 대문자 J만 붙여주면 됨.
 * Frame > JFrame
 * Button > JButton */

class JInfoUI {
	//멤버변수 선언
	JFrame f;
	JPanel p_West, p_South, p_East;
	
	JLabel name, tel, jumin, gender, age, addr;
	JTextField tfname, tftel, tfjumin, tfgender, tfage, tfaddr;
	JButton bInsert, bModify, bDelete, bShow, bExit;
	
	ArrayList testAr = new ArrayList();
	JList memberList;
	JTextArea ta;
	
	//생성자(객체생성)
	JInfoUI(){
		f = new JFrame("사용자 정보 입력 창");//제목

		bInsert = new JButton("입력", new ImageIcon("src/img/add-button.png")); //버튼그림 입력
		bModify = new JButton("수정");
		bDelete = new JButton("삭제");
		bShow = new JButton("전체보기");
		bExit = new JButton("종료");
		
		memberList = new JList();
		/*ta = new JTextArea("텍스트 에어리어");*/
		
		tfname = new JTextField(5);
		tftel = new JTextField();
		tfjumin = new JTextField();
		tfgender = new JTextField();
		tfage = new JTextField();
		tfaddr = new JTextField();
		
		name = new JLabel("이름", JLabel.LEFT);
		tel = new JLabel("전화", JLabel.LEFT);
		jumin = new JLabel("주민", JLabel.LEFT);
		gender = new JLabel("성별", JLabel.LEFT);
		age = new JLabel("나이", JLabel.LEFT);
		addr = new JLabel("주소", JLabel.LEFT);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		f.add(memberList, BorderLayout.CENTER);
		
		// 이벤트 핸들러 등록
		JListHandler handler = new JListHandler();
		memberList.addListSelectionListener(handler);
		
	}
	
	private class JListHandler implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent event) {
			{
				int i = memberList.getSelectedIndex();
/*				// getContentPane(): GUI 컴포넌트의 참조를 반환
				// setBackground(): 컴포넌트의 배경을 설정
				// getSelectedIndex(): 선택된 항목의 인덱스 반환
				// 리스트에서 색상 항목이 선택되면 해당 색상으로 배경지정
				getContentPane().setBackground(colors[colorJList.getSelectedIndex()]);
				JOptionPane.showMessageDialog(null, colorNames[colorJList.getSelectedIndex()]);*/
				
				tfname.setText(String.valueOf(testAr.get(i)));
				tftel.setText(String.valueOf(i));
				tfjumin.setText(String.valueOf(i));
				tfgender.setText(String.valueOf(i));
				tfage.setText(String.valueOf(i));
				tfaddr.setText(String.valueOf(i));
			}
		}
	}
	
	void JListView(){
		
		testAr.add("rabbit");
		testAr.add("dog");
		testAr.add("cat");
		testAr.add("fox");
		testAr.add("cat");
		testAr.add("tiger");
		testAr.add("zebra");
		testAr.add("elephant");
		
		memberList = new JList(testAr.toArray());
		
/*		memberList = new JList(3, true);
		memberList.add("홍길동", 4);*/
		
		//중복 체크가 가능(true)한가 불가(false)한가 
/*		memberList.add("초졸", 2);
		memberList.add("중졸", 6);
		memberList.add("고졸", 4);
		memberList.add("대졸", 5);
		memberList.add("석사과정");
		memberList.add("박사과정");*/
	}
}

public class Fr03_GUI_Swing_JList {

	public static void main(String[] args) {
	
		JInfoUI ui = new JInfoUI();
		ui.JListView();
		ui.addLayout();
		

	}
}

/*src\image에 아이콘파일들 JPG vs jpg
 * swing이 더 이쁘고 쉬움.
 * component
 * 	container
 * 		window
 * 			frame
 * 		panel
 * 	button
 * 	TF
 * 
 * Swing의 소속은 java.x.swing
 * 
 * 			
 */

