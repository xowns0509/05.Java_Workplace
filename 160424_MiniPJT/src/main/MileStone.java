package main;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import record.EmpRecord;
import view.ChartView;
import view.DevView;
import view.LoginView;
import view.PLView;
import view.PMView;

/**
 * 메인 화면을 구성하며 메인 구동 프로그램 
 * @author yeji
 *
 */

public class MileStone extends JFrame{
	//화면 구성 불러오기
	DevView devView;
	PMView pmView;
	PLView plView;
	LoginView lgView;
	ChartView chView;
	//NewAccount newAccount;
	
	//Database db; //테스트

	//생성자
	public MileStone(){
		//look and feel 여기 

		//화면을 관리하는 클래스 객체 생성
		lgView = new LoginView(this);	
		chView = new ChartView();
		
		addLayout();
		
		setLayout(new GridLayout(1,2));
		setSize(1200,750);
		setVisible(true);
		setTitle("milestone");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*try {
			Connection con = db.getConnection();
			System.out.println("db 연결 성공");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}	
	/**
	 * 로그인 상태에 따라 연결해주는 창을 다르게 해주는 메소드 
	 * @param rec 로그인한 사원의 정보
	 * @author yeji
	 */
	public void changeView(EmpRecord rec){
		if(rec.getJob().equals("개발자")){
			System.out.println("엄태준여기3");
			devView = new DevView(rec);
			setVisible(false);
		}else if(rec.getJob().equals("PM")){
			pmView = new PMView(rec);
			setVisible(false);
		}else if(rec.getJob().equals("PL")){
			plView = new PLView(rec);
			setVisible(false);
		}		
	}
	void addLayout(){
		//좌측: 그래프 
		JPanel p_left = new JPanel();

		//우측: (위) 로고, (아래) 로그인		
		JPanel p_right = new JPanel(new GridLayout(2,1));
		
		
		ImageIcon tmp = new ImageIcon("src/test_img.png");
		JLabel img = new JLabel("", tmp, JLabel.CENTER);
		p_right.add(img);	
		p_right.add(lgView);
		add(p_left);
		add(p_right);

	}

	//메인 
	public static void main(String[]args){
		new MileStone();
	}


}
