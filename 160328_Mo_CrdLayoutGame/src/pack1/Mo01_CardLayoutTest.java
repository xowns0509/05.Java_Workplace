package pack1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mo01_CardLayoutTest extends JFrame{
	
	JButton btn = new JButton("눌러주삼");
	PanA penA = new PanA();
	PanB penB = new PanB();
	CardLayout card = new CardLayout();
	JPanel panel = new JPanel();

	Mo01_CardLayoutTest(){
		super("카드레이아웃 테스트");	
		//카드레이아웃 붙이기. 전환할 두개 이상의 카드들을 어떠한패널에 붙이고 그 패널을 다시 창에다붙임

		setLayout(new BorderLayout());
		add(btn, BorderLayout.NORTH);
		
		panel.setLayout(card);
		panel.add("1", penA);
		panel.add("2", penB);//패널에 이름을 부여
		add(panel, BorderLayout.CENTER);

		//버튼에 이벤트를 발생하여 카드레이아웃 변경
		// - card.show(panel, "2");

		setSize(400, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
/*[0]	class BHdlr implements ActionListener{
			public void actionPerformed(ActionEvent e) {//ActionEvent e 이벤트가 발생한 정보를 얻어오는 놈.
			card.show(panel, "2");//2는 패널의 별명
			} 
		}*/
		
/*[1]	BHdlr bh1 = new BHdlr();
		btn.addActionListener(bh1);*/
		
/*[2]	btn.addActionListener(new BHdlr()); *///객체 생성자를 함수의 인자로 넣어버림.
		
/*[3]	CardLayoutTest$BHdlr.class. 아래는 버튼 한개당 하나의 이벤트를 부여 할 때 아래와 같이 사용.
 * 		버튼 5개 만드는 거를 이렇게는 불가, if else 연속으로 줘야 함.*/		
		
		btn.addActionListener(new ActionListener(){//인터페이스를 객체 생성한게 아니라 인터페이스를 구현한 클래스를 객체 생성한거임.
			public void actionPerformed(ActionEvent e) {//ActionEvent e 이벤트가 발생한 정보를 얻어오는 놈.
				card.show(panel, "2");//2는 패널의 별명
			}
		});
	}
	

	public void change(){//이 함수를 불러서 패널을 부른다.
		card.show(panel, "1");
	}
		
	public static void main(String[] args) {
		Mo01_CardLayoutTest clt = new Mo01_CardLayoutTest();
	}

}