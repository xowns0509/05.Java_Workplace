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