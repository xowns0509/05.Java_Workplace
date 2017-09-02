package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import module.MakeCheckList;
import module.giveCheckList;
import record.EmpRecord;
/**
 * PL 이 로그인 한 후에 나오는 화면
 * @author yeji
 *
 */
public class PLView extends JFrame{
	JSplitPane splitPane;
	JTabbedPane tpane;
	EmpRecord rec;
	MakeCheckList makeList;
	
	public PLView(EmpRecord rec){
		this.rec = rec;
		addLayout();

		//프레임 설정 
		setTitle("milestone");
		setVisible(true);
		setSize(1200,750);
	}
	void addLayout(){

		//************화면구성************
		// 전체화면 - 왼쪽, 오른

		// 왼쪽상단 - 팀 체크리스트명, 체크리스트 패널
		// 왼쪽상단 UP - 팀 체크리스트명
		// 왼쪽상단 DW - 팀 체크리스트 패널

		// 왼쪽하단 - 프로젝트 진척도

		// 오른상단 - 체크리스트, 개발자게시판, 버그리포트 탭
		// 오른하단 - 체크리스트, 개발자게시판, 버그리포트 탭
		
		// 왼쪽상단 UP - 팀 체크리스트명
		JPanel p_Left_North_Up = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North_Up.add(new JLabel("프로젝트 체크리스트명 칸", SwingConstants.CENTER));
		
		// 왼쪽상단 DW - 팀 체크리스트 패널
		JPanel p_Left_North_Down = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North_Down.add(new JLabel("프로젝트 체크리스트 패널칸", SwingConstants.CENTER));

		// 왼쪽상단 붙이기
		JPanel p_Left_North = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_North.setLayout(new BorderLayout());
		p_Left_North.add(p_Left_North_Up, BorderLayout.NORTH );
		p_Left_North.add(p_Left_North_Down, BorderLayout.CENTER );		
		
		// 왼쪽하단 - 프로젝트 진척도
		JPanel p_Left_South = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left_South.add(new JLabel("프로젝트 진척도 BAR칸", SwingConstants.CENTER));		
		
		// 왼쪽상단, 하단 붙이기
		JPanel p_Left = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		p_Left.setLayout(new GridLayout(2,1));
		p_Left.add(p_Left_North);
		p_Left.add(p_Left_South);		

		// 오른 - 체크리스트, 개발자게시판, 버그리포트 탭
		JPanel chkList = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		JPanel devBoard = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		JPanel bugBoard = new JPanel();//패널 명은 더미용이며 추후 변경될 수 있음
		JPanel workDistribute = new JPanel();
		
		tpane = new JTabbedPane();
		tpane.addTab("PM보고", chkList); //패널 명은 더미용이며 추후 변경될 수 있음
		tpane.addTab("체크리스트 생성", new MakeCheckList(this)); 
		tpane.addTab("개발자게시판", devBoard);//패널 명은 더미용이며 추후 변경될 수 있음
		tpane.addTab("버그리포트", bugBoard);//패널 명은 더미용이며 추후 변경될 수 있음	
		tpane.addTab("업무분담", new giveCheckList(this));
		
		//TODO: 체크리스트 할당 탭 붙이기   
		tpane.setSelectedIndex(0);
		
		
		
		//왼쪽 오른쪽 붙이기
		splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, p_Left, tpane );
		splitPane.setDividerLocation(350); // 수평으로 쪼갤 위치의 설정 
		add( splitPane ); // 생성된 split창을 프레임에 부착 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 *	사원 레코드 자식 클래스한테 넘겨주기 (현재 PL 이 누구인지 확인하기위해서)
	 */
	public EmpRecord getRecord(){
		return rec;
	}
}
