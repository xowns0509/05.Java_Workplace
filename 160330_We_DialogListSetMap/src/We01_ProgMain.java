import java.awt.*;
import javax.swing.*;

public class We01_ProgMain {

	public static void main(String[] args) {
		
		MenuUI mainUI = new MenuUI();
		
	}

}

class MenuUI extends JFrame{
	
	//1.필드 선언
	We01_MyMenuAll jBarMenu = new We01_MyMenuAll();
	//2.생성자
	MenuUI(){
		
		setVisible(true);//프레임 생성
		setSize(400,300);
		setJMenuBar(jBarMenu.mb);//Frame에 붙이는거니까 Jframe의 행동이므로 JFrame의 메소드를 찾아야 하는거임
		setTitle("임시 윈도우 제목");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jBarMenu.UILayout();
		jBarMenu.eventProc();
	}
	//3.메소드
	
}
