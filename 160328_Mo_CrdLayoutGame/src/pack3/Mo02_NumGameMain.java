package pack3;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Mo02_NumGameMain {

	public static void main(String[] args) {
		
		NumberGame game = new NumberGame();
		game.initChar();
		game.showAnswer();

	}
}

class NumberGame extends JFrame implements ActionListener{
	
	int getsu = 4;
	JButton [][] la = new JButton[4][4];
	char[][] answer = new char[4][4];
	
	public NumberGame(){
		setLayout(new GridLayout(4,4));
		
		for(int i = 0; i < 4 ; i++){
			for(int j = 0; j <4 ; j++){
				la[i][j] = new JButton();
				add(la[i][j]);
				la[i][j].addActionListener(this);
				
				answer[i][j] = '0';//'\u0000의 유니코드가 아닌 숫자 0으로 초기화 위해'
			}
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(400, 400);
		
	}
	void initChar(){
		char alpha = '0';
		
		BACK:
		for(int i = 0 ; i < getsu*getsu;){
			if(i % 2 == 0){
				alpha = (char)('A'+(int)(Math.random()*26));//임의의 알파벳 지정.0부터 26까지 더하니까 A부터 z까지
				for(int r = 0; r < getsu; r++){
					for(int c = 0; c < getsu; c++){
						if(answer[r][c] == alpha) continue BACK;
					}
				}//--------------여기 나가면 동일한 놈 없고 계속 새로운 놈을 받는 데 성공
			}

			//이제 이놈들을 죄다 재배열함
			boolean ok = false;
			do{
				int row = (int)(Math.random()*getsu);
				int col = (int)(Math.random()*getsu);
				if(answer[row][col]=='0'){
					answer[row][col]= alpha;
					i++;
					ok = true;
					System.out.println("["+alpha+"]"+row+"행"+col+"열");
				}
			}while(!ok);
		}
		//8개만 뽑아서 배치를 2개씩 함
	}
	void showAnswer(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				la[i][j].setText(String.valueOf(answer[i][j]));
			}
		}
/*		try{
		Thread.sleep(2000);		//이 메소드가 예외를 발생시킴
		}catch(Exception ex){}
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				la[i][j].setText(null);
			}
		}*/
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		//이벤트가 발생한 정보가 e로 들어옴.
		//e.getSource();가 나타내는 놈이 주소, 이것을 JButton으로 캐스팅하겠다.
		//버튼일 수도 있고 메뉴일 수도있고.... 그 겟소스 입장에서 보면
		//겟소스란 함수는 누가 이벤트를 했는지에 대한 레퍼런스를 넘겨줌
		//어떤경우는 텍스트 필드 어떤
		//겟 소스가 왜 굳이 왜 오브젝트형으로 넘기느냐
		//제이버튼으로 넘기지 않고
		
		//리턴형은 고정인데 
		//눌려진 놈이 정보가 누군지를
		
		JButton evt = (JButton)e.getSource();
		//getSource();의 리턴형은 object. JButton으로 들어 올줄아므로 JButton으로 형변환.
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(evt == la[i][j]){
					JOptionPane.showMessageDialog(null, i+":"+j+"이벤트발생함");
					return;//break보다는 이게 나음.
				}
			}
		}
	}
}