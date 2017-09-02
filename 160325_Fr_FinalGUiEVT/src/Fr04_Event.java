import java.awt.*;//이건 있어야 함. 왜냐면 layout들은 awt소속이니까 있어야지
import javax.swing.*;
//x버튼을 눌러도 창만 닫힐 뿐 실제로는 빨간 네모 버튼을 눌러야 함.

//awt는 운영체제 베이스 UI. 리눅스와 윈도우에서 서로 구동한 화면이 다른데
//스윙으로 한건 둘다 똑같이 나옴

/*Frame
JFrame
Button
JButton
모든 컴포넌트(만) 앞에 대문자 */

class JEInfoUI {
	//멤버변수 선언
	JFrame f;
	JPanel p_West, p_South, p_East;
	
	JLabel name, tel, jumin, gender, age, addr;
	JTextField tfname, tftel, tfjumin, tfgender, tfage, tfaddr;
	JButton bInsert, bModify, bDelete, bShow, bExit;
	JTextArea ta;
	
	//생성자(객체생성)
	JEInfoUI(){
		f = new JFrame("사용자 정보 입력 창");//제목

		bInsert = new JButton("입력");
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
		
		f.add(ta, BorderLayout.CENTER);

	}
}

public class Fr04_Event {

	public static void main(String[] args) {
	
		JEInfoUI ui = new JEInfoUI();
		
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

