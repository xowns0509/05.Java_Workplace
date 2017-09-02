package main;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import module.ProgressBar;
import record.EmpRecord;
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

	//생성자
	public MileStone(){
		//look and feel 여기 
		try {
            // select Look and Feel
            //UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			UIManager.put("RootPane.setupButtonVisible", false);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
		

		//화면을 관리하는 클래스 객체 생성
		lgView = new LoginView(this);	  

		addLayout();

		setLayout(new GridLayout(1,2));
		setSize(1200,750);
		setVisible(true);
		setTitle("milestone");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}	

	/**
	 * 로그인 상태에 따라 연결해주는 창을 다르게 해주는 메소드 
	 * @param rec 로그인한 사원의 정보
	 * @author yeji
	 */
	public void changeView(EmpRecord rec){
		if(rec.getJob().equals("개발자")){
			devView = new DevView(rec);
			setVisible(false);
		}else if(rec.getJob().equals("PM")){
			pmView = new PMView(rec);
			setVisible(false);
		}else if(rec.getJob().equals("PL")){
			//System.out.println("PL 창!");
			plView = new PLView(rec);

			setVisible(false);
		}		
	}
	void addLayout(){
		//좌측: 그래프 
		//JPanel p_left = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//p_left.add(new ProgressBar());

		/*//좌측: 하단 progressBar
		JPanel p_left_center = new JPanel();
		//p_left_center.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_left_center.add(new ProgressBar());
		 */
		/*p_left.add(p_left_north);
		p_left.add(p_left_center);
		 */
		JPanel p_left = new JPanel(new GridBagLayout());
		p_left.add(new ProgressBar());

		//우측: (위) 로고, (아래) 로그인		
		//JPanel p_right = new JPanel(new GridLayout(2,1));		
		JPanel p_right = new JPanel(new GridBagLayout());
		Box box = Box.createVerticalBox();
		
		ImageIcon tmp = new ImageIcon("src/test_img.png");
		JLabel img = new JLabel("", tmp, JLabel.CENTER);
		box.add(img);
		
		box.add(lgView);
		/*JPanel p_img = new JPanel(new GridBagLayout());
		p_img.add(img);
		p_right.add(p_img);
		
		JPanel p_login = new JPanel(new GridBagLayout());
		p_login.add(lgView);
		*/
		//p_right.add(p_login);
		p_right.add(box);
		add(p_left);
		add(p_right);
	}

	//메인 
	public static void main(String[]args){
		new MileStone();
	}
}
