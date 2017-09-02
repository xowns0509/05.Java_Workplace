package pack1;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class PanB extends JPanel{
	JButton back = new JButton("뒤로");
	//이 버튼에 이벤트처리 확인
	// - JOptionPane.showMessageDialog(null, "이벤트 발생");

	PanB(){//'뒤로'버튼이 PanelB에 있어야 하니까 여기에다 버튼을 생성해줘야 함.
		add("2", back);
		
		back.addActionListener(new ActionListener(){//인터페이스를 객체 생성한게 아니라 인터페이스를 구현한 클래스를 객체 생성한거임.
			public void actionPerformed(ActionEvent e) {//ActionEvent e 이벤트가 발생한 정보를 얻어오는 놈.
				JOptionPane.showMessageDialog(null, "이벤트 발생");
			}
		});
		
		setBackground(Color.CYAN);//이름 따로 지정
	}
/*	class Hxdlr implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "이벤트 발생");
		}
	}*/
}

/*class PenB extends JPanel{
PenB(){
	setBackground(Color.CYAN);//이름 따로 지정
}
}*/