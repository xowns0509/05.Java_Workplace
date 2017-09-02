package pack3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 두 숫자의 연산을 수행하는 계산기
class CalcUI{
	//변수
	JFrame f;
	JButton bAdd,bMinus,bMul,bDivide,bEqual,numbers[],bZero;
	JTextArea taInput;
	double total;
	String op;

	//생성자
	CalcUI(){
		//JFrame
		f = new JFrame("Calculator");
		//JButton  
		bAdd = new JButton("+");
		bMinus = new JButton("-");
		bMul = new JButton("*");
		bDivide =new JButton("/");
		bEqual = new JButton("=");
		numbers = new JButton[10];
		//숫자지정
		for(int i = 0; i < numbers.length; i++){
			numbers[i] = new JButton(String.valueOf(i));
		}

		//JTextField
		taInput = new JTextArea();
		total = 0;
		op = "";
	}
	void init(){
		taInput.setToolTipText("계산할 값을 입력해 주세요");
		//단축키
		bAdd.setMnemonic('+');
		bMinus.setMnemonic('-');
		bMul.setMnemonic('*');
		bDivide.setMnemonic('/');
	}
	//화면
	void addLayout(){
		f.setLayout(new BorderLayout());

		//p_north 
		JPanel p_north = new JPanel(new GridLayout(1,1));
		p_north.add(taInput);
		f.add(p_north, BorderLayout.NORTH);

		//p_south (숫자 & 연산자)
		JPanel p_south = new JPanel(new GridLayout(5,3));
		for(int i = 0; i < numbers.length; i++){
			p_south.add(numbers[i]);
		}
		p_south.add(bAdd);
		p_south.add(numbers[0]);
		p_south.add(bEqual);
		p_south.add(bMinus);
		p_south.add(bMul);
		p_south.add(bDivide);
		f.add(p_south, BorderLayout.SOUTH);

		f.setSize(300, 200);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void eventProc(){
		//버튼핸들러
		ButtonHandler bh = new ButtonHandler();

		for(int i = 0; i < numbers.length; i++){
			numbers[i].addActionListener(bh);
		}
		bAdd.addActionListener(bh);
		bEqual.addActionListener(bh);
		bMinus.addActionListener(bh);
		bMul.addActionListener(bh);
		bDivide.addActionListener(bh);
	}

		class ButtonHandler implements ActionListener{  
		public void actionPerformed(ActionEvent e) {
			//버튼 핸들러에서 입력된 연산/숫자들을 저장하고 값을 계산
			//연산자일때 그 전 값들을 저장하여 처리
			String tmp = e.getActionCommand();
			double tmpNum = 0;
			String tmpString = taInput.getText();

			//입력한 값이 숫자일때, textArea에 append,
			//다음 버튼 핸들러가 불러질때 (연산자가선택되었을때)
			//값을 저장.
			if(tmp.matches(".*\\d+.*")){
				taInput.append(tmp);

			}else if(tmp != "="){
				//연산사 저장
				tmpNum = Double.parseDouble(tmpString);
				op = tmp; //연산저장
				total = tmpNum; //숫자저장
				//taOutput.append(tmpString + op);
				taInput.setText("");
			}else{//연산자가 = 일때
				tmpNum = Double.parseDouble(tmpString);
				switch(op){
				case "+" : total += tmpNum; break; 
				case "-" : total -= tmpNum; break;
				case "/" : total /= tmpNum; break;
				case "*" : total *= tmpNum; break;
				}    
				taInput.setText(String.valueOf(total));;
			}
		}
	}
}
public class CalcTest_Yeji {
	public static void main(String[]args){
		CalcUI calc = new CalcUI();
		calc.init();
		calc.addLayout();
		calc.eventProc();
	}
}