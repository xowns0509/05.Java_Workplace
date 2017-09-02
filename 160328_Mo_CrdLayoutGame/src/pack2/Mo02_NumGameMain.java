package pack2;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/* 1. 이벤트핸들러(이너클래스 / 
 * 2. 이벤트핸들러클래스의 객체생성 
 * 3. 그 객체를 버튼에 등록 */

public class Mo02_NumGameMain {

	public static void main(String[] args) {
		
		NumberGame game = new NumberGame();
		game.initChar();
		game.showAnswer();

	}
}

class NumberGame extends JFrame{
	
	int getsu = 4;
	JButton [][] la = new JButton[4][4];
	
	public NumberGame(){
		setLayout(new GridLayout(4,4));
		
		
		BtnHndlr bh = new BtnHndlr(); //이벤트 객체 생성
		
		for(int i = 0; i < 4 ; i++){
			for(int j = 0; j <4 ; j++){
				la[i][j] = new JButton();
				add(la[i][j]);
				
				la[i][j].addActionListener(bh); //이벤트 객체 등록
			}
		}
		
		setVisible(true);
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}//end of constructor()
	
	class BtnHndlr implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JOptionPane.showMessageDialog(null,  "버튼이 눌렸다.");
		}
	}	
	
	void initChar(){
		
	}//end of initChar()
	void showAnswer(){
		
	}//end of showAnswer()
}