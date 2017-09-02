package pack3;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Done_Mo03_ImgGameMain {

	public static void main(String[] args) {

		ImgGame game = new ImgGame();
		game.initChar();
		game.showAnswer();

	}
}

class ImgGame extends JFrame implements ActionListener{

	//필드
	int getsu = 4;
	JButton [][] picBtn = new JButton[4][4];
	int [][] answer = new int[4][4];

	//버튼 첫 번째 선택시 버튼의 정보를 저장할 변수
	JButton firstClick = null;
	int firstRow = 0, firstCol = 0;


	public ImgGame(){
		
		setLayout(new GridLayout(4,4));

		for(int i = 0; i < getsu ; i++){
			for(int j = 0; j < getsu ; j++){
				picBtn[i][j] = new JButton();			//4x4버튼 생성부분.
				add(picBtn[i][j]);						//4x4버튼 붙임.
				picBtn[i][j].addActionListener(this);	//4x4버튼 이벤트객체등록

				answer[i][j] = 0;//'\u0000의 유니코드가 아닌 숫자 0으로 초기화 위해'
				//answer[i][j] = '0'이렇게 되어있으면 화면에 숫자 안뜨고 완전 공백
			}
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(380, 380);

	}
	
	void initChar(){
		int picName = 0;
		
		BACK:
			for(int i = 0 ; i < getsu*getsu;){ 					//16번 반복
				if(i % 2 == 0){									//i가 짝수번째에
					picName = (int)(Math.random()*15);//임의의 숫자 picName에 입력.(총 8번) 16개중 8개 뽑음
					for(int r = 0; r < getsu; r++){				//r을 4번 돌림.
						for(int c = 0; c < getsu; c++){			//r이 1번 가는데 c가 4번 돌림.배열의 모든 요소를 검사.
							if(answer[r][c] == picName) continue BACK;//중복되어있는지 확인. BACK이 맨 끝에 있으니 
																//alpha에 담긴 0과 같다면 맨 끝에 있는 for문의 맨 마지막으로. 
						}										//그러니까 반복문은 나가지 않기에 i가 초기화 되지 않고 계속 증가.
					}//--------------여기 나가면 동일한 놈 없고 계속 새로운 놈을 받는 데 성공.
				}

				//이제 위에서 뽑은 놈들을 죄다 재배열. 아직까진 for(int i = 0 ; i < getsu*getsu;)이 밖으로 나가지 않음.
				boolean ok = false;//무한 do while문의 스위치용.
				do{
					int row = (int)(Math.random()*getsu);//임의의 열,행숫자를 랜덤 getsu 범위내에서 뽑음.
					int col = (int)(Math.random()*getsu);
					if(answer[row][col]== 0){ //그래서 그에 해당하는 놈이 할당 안 한 칸이라면
						answer[row][col] = picName;//뽑았던 놈을 저장한다.
						i++;
						ok = true;
						System.out.println("["+picName+"]"+row+"행"+col+"열");
						System.out.println(answer[row][col]);
					}
				}while(!ok);
			}
		
		//bInsert = new JButton("입력", new ImageIcon("src/img/add-button.png")); //버튼그림 입력
	}
	
	void showAnswer(){//answer로 뽑은 놈들을 죄다 우리가 보는 놈들에게 저장.
		for(int i = 0; i < getsu; i++){
			for(int j = 0; j < getsu; j++){
				System.out.println("여기");
				//picBtn[i][j].setText(String.valueOf(answer[i][j]));
				picBtn[i][j].setIcon(new ImageIcon("src/notdefault/img_mac64px/"+String.valueOf(answer[i][j])+".png"));	//4x4버튼 이미지삽입.
				//answer[0][0] = 15다?. 그럼 15번 이미지를 찾아 picBtn[0][0]에 처넣.
			}
		}
		try{
		Thread.sleep(2000);		//이 메소드가 예외를 발생시킴
		}catch(Exception ex){}

		for(int i = 0; i < getsu; i++){
			for(int j = 0; j < getsu; j++){
				picBtn[i][j].setIcon(null);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){

		JButton evt = (JButton)e.getSource();
		
		for(int i = 0; i < getsu; i++){
			for(int j = 0; j < getsu; j++){
				if(evt == picBtn[i][j]){ //버튼을 클릭한 상태
					
					//첫 번째 버튼인지 두 번째 버튼인지를 감지
					if(firstClick == null){ 		//버튼 첫 번째를 선택한 경우. 버튼을 처음 클릭한 경우
						firstClick = evt; 			//evt 주소값을, 눌린 값을 저장.
						evt.setIcon(new ImageIcon("src/notdefault/img_mac64px/"+String.valueOf(answer[i][j])+".png"));
						//evt.setBackground(new Color(0, 0, 133)); //그 주소에 대한 놈의 색깔을 바꾸는 게야. evt의 색깔을
						firstRow = i;
						firstCol = j;
/*[1]*/					firstClick.removeActionListener(this);
/*[2]					firstClick.setEnabled(false);*/
					}else{//버튼 두 번째를 선택한 경우.
						
						//첫 번째 선택 문자와 두번째의 문자가 같은지 비교
						if(answer[firstRow][firstCol] == answer[i][j]){//같다면
							firstClick.setIcon(new ImageIcon("src/notdefault/img_mac64px/"+String.valueOf(answer[i][j])+".png"));
							firstClick.setBackground(Color.YELLOW);
							evt.setIcon(new ImageIcon("src/notdefault/img_mac64px/"+String.valueOf(answer[i][j])+".png"));
							evt.setBackground(Color.YELLOW);
							
/*[1]*/						evt.removeActionListener(this); //버튼은 눌릴 수 있지만 실행하지 않게 하기
/*[2]						evt.setEnabled(false);*/
					
						}else{//다르다면
/*[1]*/						firstClick.setIcon(null);
							//firstClick.setBackground(null);

/*[1]*/						firstClick.addActionListener(this);
/*[2]						firstClick.setEnabled(true);*/
						}
						firstClick = null;//그래서 두 번째 클릭 후 
					}
					return;//break보다는 이게 나음.
				}
			}
		}
	}
}

//setEnabled(b);