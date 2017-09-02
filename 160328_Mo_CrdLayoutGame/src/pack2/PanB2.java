package pack2;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/* PanB와 다르게 parent를 전역변수로 두고 this로 엑세스하는 방식.
 * actionPerformed시 오버라이드 */

class PanB2 extends JPanel implements ActionListener{
	JButton back = new JButton("뒤로");//왜 여기다 버튼을 정의하고 만드려 하냐고? 패널B에 뒤로라는 버튼을 달려고 하잖아.
	Mo01_CardLayoutTest parent;
	
	PanB2(Mo01_CardLayoutTest parent){//'뒤로'버튼이 PanelB에 있어야 하니까 여기에다 버튼을 생성해줘야 함.
		this.parent = parent;//아래 parent.change2에다도 유효하게 하기 위해 this로 필드에 저장.
		add("2", back);
		setBackground(Color.CYAN);//이름 따로 지정
		back.addActionListener(this);//지가 actionslistener니까 여기다 핸들러객체를 넣으려면 자기자신을 넣어야지.
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		parent.change2();
	}
}

/*	class Hxdlr implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "이벤트 발생");
		}
	}*/


/*	class PenB extends JPanel{
		PenB(){
			setBackground(Color.CYAN);//이름 따로 지정
		}
	}*/