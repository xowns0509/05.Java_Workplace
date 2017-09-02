import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class CalcUI extends JFrame{

	JPanel p_Center, p_South;
	JButton [] b = new JButton[10];//버튼 b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
	JButton bPlus, bMinus, bDivide, bTimes, bEquals, bClear;//버튼 +,-, /, =, 초기화
	JTextArea ta;

	String [] strNum = new String[10];
	String [] strOpr = new String[6];

	String Oprstr0 = "0";
	String Oprstr1 = "0";
	int pnter = 0;

	int Oprint0 = 0;
	int Oprint1 = 0; 	
	int result = 0;

	CalcUI(){
		super("EOM's Calc");

		for(int i = 0 ; i < 10 ; i++){
			b[i] = new JButton(String.valueOf(i));//숫자 버튼 생성
		}
		bPlus = new JButton("+");
		bMinus = new JButton("-");
		bTimes = new JButton("X");
		bDivide = new JButton("/");
		bEquals = new JButton("=");
		bClear = new JButton("초기화");

		ta = new JTextArea("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void addLayout(){
		setSize(650, 480);
		setVisible(true); //어떤 버튼을 누르면 뜨는 창 같은거를 truefalse로 onoff
		setLayout(new BorderLayout());

		add(ta, BorderLayout.NORTH);

		p_Center = new JPanel();
		p_Center.setLayout(new GridLayout(4, 3));
		for(int i = 9 ; i >= 0 ; i--){
			p_Center.add(b[i]);
		}
		add(p_Center, BorderLayout.CENTER);

		p_South = new JPanel();
		p_South.setLayout(new GridLayout(2, 3));
		p_South.add(bPlus);
		p_South.add(bMinus);
		p_South.add(bTimes);
		p_South.add(bDivide);
		p_South.add(bEquals);
		p_South.add(bClear);
		add(p_South, BorderLayout.SOUTH);

	}

	void eventProc(){

		BtnHdlr bhandler = new BtnHdlr();

		for(int i = 0 ; i < 10 ; i++){
			b[i].addActionListener(bhandler);
		}
		bPlus.addActionListener(bhandler);
		bMinus.addActionListener(bhandler);
		bTimes.addActionListener(bhandler); 
		bDivide.addActionListener(bhandler); 
		bEquals.addActionListener(bhandler);
		bClear.addActionListener(bhandler);
	}
	
	class BtnHdlr implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			JButton evt = (JButton)e.getSource();
		
				if(evt == b[0]){
					Oprstr0 = Oprstr0 + "0";
				}else if(evt == b[1]){
					Oprstr0 = Oprstr0 + "1";
				}else if(evt == b[2]){
					Oprstr0 = Oprstr0 + "2";
				}else if(evt == b[3]){
					Oprstr0 = Oprstr0 + "3";
				}else if(evt == b[4]){
					Oprstr0 = Oprstr0 + "4";
				}else if(evt == b[5]){
					Oprstr0 = Oprstr0 + "5";
				}else if(evt == b[6]){
					Oprstr0 = Oprstr0 + "6";
				}else if(evt == b[7]){
					Oprstr0 = Oprstr0 + "7";
				}else if(evt == b[8]){
					Oprstr0 = Oprstr0 + "8";
				}else if(evt == b[9]){
					Oprstr0 = Oprstr0 + "9";
				}else if(evt == bPlus){
					strOpr[0] =  " + ";
					pnter += 1; 
				}else if(evt == bMinus){
					strOpr[0] =  " - ";
					pnter += 1;
				}else if(evt == bTimes){
					strOpr[0] =  " X ";
					pnter += 1;
				}else if(evt == bDivide){
					strOpr[0] =  " / ";
					pnter += 1;
				}else if(evt == bEquals){
					strOpr[0] =  " = ";
					pnter += 1;
				}else if(evt == bClear){
					strOpr[0] =  "숫자를 입력하세요.";
					pnter = 0;
				}
			}else if(pnter == 1){
				if(evt == b[0]){
					Oprstr1 = Oprstr1 + "0";
				}else if(evt == b[1]){
					Oprstr1 = Oprstr1 + "1";
				}else if(evt == b[2]){
					Oprstr1 = Oprstr1 + "2";
				}else if(evt == b[3]){
					Oprstr1 = Oprstr1 + "3";
				}else if(evt == b[4]){
					Oprstr1 = Oprstr1 + "4";
				}else if(evt == b[5]){
					Oprstr1 = Oprstr1 + "5";
				}else if(evt == b[6]){
					Oprstr1 = Oprstr1 + "6";
				}else if(evt == b[7]){
					Oprstr1 = Oprstr1 + "7";
				}else if(evt == b[8]){
					Oprstr1 = Oprstr1 + "8";
				}else if(evt == b[9]){
					Oprstr1 = Oprstr1 + "9";
				}else if(evt == bPlus){
					strOpr[1] =  " + ";
					pnter += 1; 
				}else if(evt == bMinus){
					strOpr[1] =  " - ";
					pnter += 1;
				}else if(evt == bTimes){
					strOpr[1] =  " X ";
					pnter += 1;
				}else if(evt == bDivide){
					strOpr[1] =  " / ";
					pnter += 1;
				}else if(evt == bEquals){
					strOpr[1] =  " = ";
					pnter += 1;
				}else if(evt == bClear){
					strOpr[1] =  "숫자를 입력하세요.";
					pnter = 0;
				}
			}
		}
	}
}

public class Calc {

	public static void main(String[] args) {

		CalcUI ui = new CalcUI();
		ui.addLayout();
	}
}
