import java.awt.*;
/*import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;*/

class InfoUI {
	//멤버변수 선언
	Frame f;
	Panel p_West, p_South, p_East;
	
	Label name, tel, jumin, gender, age, addr;
	TextField tfname, tftel, tfjumin, tfgender, tfage, tfaddr;
	Button bInsert, bModify, bDelete, bShow, bExit;
	TextArea ta;
	
	//생성자(객체생성)
	InfoUI(){
		f = new Frame("사용자 정보 입력 창");//제목

		bInsert = new Button("입력");
		bModify = new Button("수정");
		bDelete = new Button("삭제");
		bShow = new Button("전체보기");
		bExit = new Button("종료");
		
		ta = new TextArea("텍스트 에어리어");
		
		tfname = new TextField(5);
		tftel = new TextField();
		tfjumin = new TextField();
		tfgender = new TextField();
		tfage = new TextField();
		tfaddr = new TextField();
		
		name = new Label("이름", Label.LEFT);
		tel = new Label("전화", Label.LEFT);
		jumin = new Label("주민", Label.LEFT);
		gender = new Label("성별", Label.LEFT);
		age = new Label("나이", Label.LEFT);
		addr = new Label("주소", Label.LEFT);
		
	}

	//화면구성 및 보이기
	void addLayout(){
		f.setSize(650, 480);
		f.setVisible(true); //어떤 버튼을 누르면 뜨는 창 같은거를 truefalse로 onoff
		
		f.setLayout(new BorderLayout());
		
		Panel p_west = new Panel();
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
		
		Panel p_south = new Panel();
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

public class Fr03_GUI_awt {

	public static void main(String[] args) {
	
		InfoUI ui = new InfoUI();
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
 */

