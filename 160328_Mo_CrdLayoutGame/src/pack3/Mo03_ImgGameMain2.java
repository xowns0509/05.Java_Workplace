package pack3;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Mo03_ImgGameMain2{

	public static void main(String[] args){

		ImgGame2 game = new ImgGame2();
		game.initChar();
		game.showAnswer();

	} 
} 

class ImgGame2 extends JFrame implements ActionListener{ 

	//?? 
	int getsu = 4; 
	JButton [][] la = new JButton[4][4];
	char[][] answer = new char[4][4]; 

	//踰??泥?踰吏???? 踰?쇱 ?蹂대? ??ν 蹂? 
	JButton firstClick = null; 
	int firstRow = 0, firstCol = 0; 

	public ImgGame2(){
		
		setLayout(new GridLayout(4,4)); 
		
		for(int i = 0; i < getsu ; i++){ 
			for(int j = 0; j < getsu ; j++){ 
				la[i][j] = new JButton();			//4x4踰????깅?遺. 
				add(la[i][j]);						//4x4踰??遺?. 
				la[i][j].addActionListener(this);	//4x4踰???대깽?멸?泥대깅? 

				answer[i][j] = '0';//'\u0000? ??肄?媛 ?? ?レ 0?쇰? 珥湲고 ??? 
			} 
		} 

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true); 
		setSize(400, 400); 

	} 
	 
	void initChar(){ 
		char alpha = '0'; 

		BACK: 
			for(int i = 0 ; i < getsu*getsu;){					//16踰 諛蹂?
				if(i % 2 == 0){									//i媛 吏?踰吏몄 
					alpha = (char)('A'+(int)(Math.random()*26));//??? ??踰녹 alpha? ???(珥 8踰) 
					for(int r = 0; r < getsu; r++){				//r? 4踰 ?由? 
						for(int c = 0; c < getsu; c++){			//r??1踰 媛???c媛 4踰 ?由?諛곗댁 紐⑤ ??瑜?寃?? 
							if(answer[r][c] == alpha) continue BACK;//以蹂듬?댁?吏 ??? BACK??留??? ??쇰  
																//alpha? ?닿릿 0怨?媛?ㅻ㈃ 留??? ?? for臾몄 留?留吏留?쇰?.  
						}										//洹몃щ源 諛蹂듬Ц? ?媛吏 ?湲곗 i媛 珥湲고 ?吏 ?怨 怨? 利媛. 
					}//--------------?ш린 ?媛硫???쇳 ? ?怨 怨? ?濡???? 諛? ???깃났. 
			   } 

				//?댁 ??? 戮? ??ㅼ 二???щ같?? ?吏源吏 for(int i = 0 ; i < getsu*getsu;)??諛?쇰? ?媛吏 ??. 
				boolean ok = false;//臾댄 do while臾몄 ?ㅼ移?? 
				do{ 
					int row = (int)(Math.random()*getsu);//??? ????レ瑜????getsu 踰??댁? 戮?. 
					int col = (int)(Math.random()*getsu); 
					if(answer[row][col]=='0'){ //洹몃? 洹몄 ?대뱁? ??????? ? 移몄대쇰㈃ 
						answer[row][col]= alpha;//戮?? ?? ??ν?? 
						i++; 
						ok = true; 
						System.out.println("["+alpha+"]"+row+"?"+col+"??); 
					} 
				}while(!ok); 
			} 
		//8媛留 戮?? 諛곗?瑜?2媛????
	} 
	 
	void showAnswer(){ 
		for(int i = 0; i < getsu; i++){ 
			for(int j = 0; j < getsu; j++){
				System.out.println("??");
				la[i][j].setText(String.valueOf(answer[i][j])); 
			} 
		} 
		try{ 
			Thread.sleep(2000);		//??硫??媛 ??몃? 諛????
		}catch(Exception ex){} 

		for(int i = 0; i < getsu; i++){ 
			for(int j = 0; j < getsu; j++){ 
				la[i][j].setText(null); 
			} 
		} 
	} 

	@Override 
	public void actionPerformed(ActionEvent e){ 
		//?대깽?멸? 諛?? ?蹂닿? e濡 ?ㅼ댁? 
		//e.getSource();媛 ???대 ???二쇱, ?닿?? JButton?쇰? 罹?ㅽ?寃?? 
		//踰?쇱??? ?怨 硫?댁????怨.... 洹?寃?????μ? 蹂대㈃ 
		//寃??ㅻ ?⑥? ?媛 ?대깽?몃? ??吏? ?? ??쇰곗ㅻ? ?寃⑥? 
		//?대ㅺ꼍?곕 ??ㅽ??? ?대?
		//寃 ??ㅺ? ? 援녹?? ?ㅻ???명?쇰? ?湲곕? 
		//??대??쇱쇰? ?湲곗? ?怨 

		//由ы댄? 怨??몃? 
		//??ㅼ? ????蹂닿? ?援곗?瑜?

		JButton evt = (JButton)e.getSource(); 
		//getSource();? 由ы댄? object. JButton?쇰? ?ㅼ???以 ?誘濡 JButton?쇰? ?蹂?. 

		for(int i = 0; i < getsu; i++){ 
			for(int j = 0; j < getsu; j++){ 
				if(evt == la[i][j]){ //踰?쇱 ?대┃? ?? 
					 
					//泥?踰吏?踰?쇱몄? ? 踰吏?踰?쇱몄?瑜?媛吏 
					if(firstClick == null){		 //踰??泥?踰吏몃? ??? 寃쎌? 踰?쇱 泥? ?대┃? 寃쎌?
						firstClick = evt;			 //evt 二쇱媛?, ?由?媛? ??? 
						evt.setBackground(new Color(0, 0, 133)); //洹?二쇱? ?? ?? ?源? 諛袁몃 寃?? evt? ?源? 
						firstRow = i; 
						firstCol = j; 
/*[1]*/					firstClick.removeActionListener(this); 
/*[2]					firstClick.setEnabled(false);*/ 
					}else{//踰??? 踰吏몃? ??? 寃쎌? 
						 
						//泥?踰吏??? 臾몄? ?踰吏몄 臾몄媛 媛?吏 鍮援 
						if(answer[firstRow][firstCol] == answer[i][j]){
							firstClick.setBackground(Color.YELLOW); 
							evt.setBackground(Color.YELLOW); 
							 
/*[1]*/						evt.removeActionListener(this); //踰?쇱 ?由?? ?吏留 ?ㅽ?吏 ?寃 ?湲?
/*[2]						evt.setEnabled(false);*/ 
					 
						}else{ 
/*[1]*/						firstClick.setBackground(null); 

/*[1]*/						firstClick.addActionListener(this); 
/*[2]						firstClick.setEnabled(true);*/ 
						} 
						firstClick = null;//洹몃? ? 踰吏??대┃ ?  
					} 
					return;//break蹂대ㅻ ?닿? ??. 
				} 
			} 
		} 
	}
}