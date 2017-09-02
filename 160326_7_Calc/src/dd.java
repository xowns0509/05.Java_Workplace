class Appa{
	void test(){
		출력("아빠");
	}
}

class Umma{
	void test(){
		출력("엄마");
	}

}

class Ddal{
	void test(){
		출력("딸");
	}

}

class Test extends Umma, {
	//상속은 하나 밖에 안됨. 무조건 단일 상속, 부모가 2명이상 올 수 없다.

	Ddal d = new Ddal();
	if(d instance of Umma)
	{
		출력("Ddal의 객체")
	}
}


엄마의


import java.awt.*;
public class Fr02_GUI_MyframeB extends Frame {//Frame을 통째로 상속 받을 수 있음
	//멤버변수 선언. 버튼생성

	Button b1;
	Button b2;
	Label l;
	TextField tf;
	Checkbox chM;
	Checkbox chW;
	TextArea ta;
	CheckboxGroup cbg;
	List ls1;

	//TextArea 여러 줄 입력 받기
	//

	Fr02_GUI_MyframeB() {
		super("두번째 창");
		//Frame을 상속받기가 가능하므로 Frame상속 후 super처리
		//b1 = new Button("눌러주삼"); //
		b1 = new Button("확인"); //
		b2 = new Button("취소"); //

		l = new Label("이름", Label.LEFT);
		tf = new TextField("여기이름을 입력", 30);
		ta = new TextArea("텍스트 에어리어");

		CheckboxGroup cbg = new CheckboxGroup();

		//chM = new Checkbox("남자 check");
		//chW = new Checkbox("여자 check");

		chM = new Checkbox("남자 check", cbg, true);
		chW = new Checkbox("여자 check", cbg, true);
		//만약 밑에 false를 true로 할 경우 여자가 선택이 됨.

		ls1 = new List(3, true); //6은 창에 보일 목록 갯수
		//중복 체크가 가능(true)한가 불가(false)한가 
		ls1.add("유딩", 3);
		ls1.add("초졸", 2);
		ls1.add("중졸", 6);
		ls1.add("고졸", 4);
		ls1.add("대졸", 5);
		ls1.add("석사과정");
		ls1.add("박사과정");

	}

	void addLayout(){
		//	FlowLayout fl = new FlowLayout();
		//	setLayout(fl); 이렇게 생성하던가 또는 아래와 같이 해도 됨.
		// setLayout(new FlowLayout());//객체 생성하면서 함수인자로 넣어버리겠다.

		/*1. FlowLayout : 상단중앙배치
		 * / 컴포넌트 크기 고정 / 한줄에 배치가 안되면 자동으로 다음 줄로 배치
		 *2. GridLayout : 행과 열에 맞춰 배치 / 창의 전체크기에 맞춰서 컴포넌트의 크기가 변동
		 *3. BorderLayout : 5개의 영역에 배치. 이 영역엔 하나만 붙음.
		 * CENTER NORTH SOUTH WEST EAST*/

		//setLayout(new GridLayout(2, 3));//행의 갯수 열의 갯수
		setLayout(new BorderLayout());//행의 갯수 열의 갯수

		//add(b1, BorderLayout.NORTH);
		//add(b2, BorderLayout.NORTH);//위의 버튼이 덮어짐
		//굳이 한 영역에 버튼을 2개 넣고 싶으면 패널로. 패널에다 2개 붙인뒤 BorderLayout.NORTH를 주는 거임.

		Panel pN = new Panel();
		pN.add(b1);
		pN.add(b2);

		add(pN, BorderLayout.NORTH);
		//		창에다 붙이는 거니까 여기다 add를 주면서 계속 기능을 추가 해 나감

		//누군가를 붙이는 놈들을 컨테이너. 패널, 프레임
		// 화면에 뜨는 놈들 버튼 등의 요소들 . 컴포넌트



		add(l, BorderLayout.WEST);
		//add(tf);
		add(chM, BorderLayout.SOUTH);
		//add(chW);
		add(ta, BorderLayout.EAST);

		add(ls1, BorderLayout.CENTER);
		setSize(500, 700);
		setVisible(true);
	}

	public static void main(String[] args) {
		Fr02_GUI_MyframeB my = new Fr02_GUI_MyframeB();
		my.addLayout();

	}
}

//GUI

/* package
 * import java.awt.*;
 */
import java.awt.*;
public class Fr02_GUI_MyFrameA {

	// 멤버변수 선언
	Frame f;

	// 생성자함수 - 멤버변수의 객체 생성
	Fr02_GUI_MyFrameA(){
		f = new Frame("나의 첫화면"); //awt.패키지 안에 Frame이 존재.
	}

	//화면구성 및 보이기
	void addLayout(){
		f.setSize(500, 350);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		Fr02_GUI_MyFrameA my = new Fr02_GUI_MyFrameA();
		my.addLayout();
	}
}
//프로그램의 정지는 GUI실행시의 창 x버튼이 아니라 밑에 빨간 네모, 작업관리자 프로세스에서 javaw
/* frame은 final이 없어서 부모로 할 수 있다. */