package	 view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.Database;
import record.EmpRecord;

/**
 * 회원가입 창 
 * 수정: 변수명 (jcomboboxMgr -> cbMgr/ jcomboPName -> cbPName)
 * job , position 미리 콤보박스로 넣어둠 
 * 추가: cbJob, cbPosition 
 * @author taejun
 *
 */
public class NewAccount extends JFrame 
{
	JLabel	lbId, lbPw, lbTel, lbPosition, lbJob, lbMgr, lbPnum;
	//라벨: 아이디, 비번, 전번, 직급, 업무, 상사아이디, 프로젝트명

	JTextField	tfId, tfPw, tfTel, tfName;
	//텍필드: 아이디, 비번, 전번, 직급, 업무
	//중복검사는 tfId 텍스트 필드에서 이벤트걸어서 확인 후 바로 밑 라벨에다 뿌리면 어떨까 

	JComboBox cbMgr, cbPName,cbJob, cbPosition ; //수정: 프로젝트 번호 -> 프로젝트명
	//콤보박스: 상사아이디, 프로젝트명

	JButton idOverlap, crAccount;
	//버튼: ID중복검사, 회원가입완료

	Connection con; // 연결 
	Statement stmt1, stmt2;
	PreparedStatement ps1 , ps2; 
	EmpRecord rec; //회원가입시 사원의 정보를 담는 클래스

	JSplitPane sPane;

	public NewAccount(){
		addLayout();
		connectDB();
		eventProc();		
	}	
	public void connectDB(){
		try {
			con = Database.getConnection();
			System.out.println("회원가입 db 연결 성공");
		} catch (Exception e) {
			System.out.println("회원가입 db 연결 실패");
		}
	}
	/**
	 * 상사아이디와 프로젝트명을 미리 db 에서 가지고오는 메소드
	 * 회원가입창의 '업무' 가 입력된 후 불러옴 
	 * @author yeji
	 */
	public void getDefaultInfo() throws Exception{
		//		
		//job 이 선택되었는지 확인
		if(cbJob.getSelectedItem() == null)
			return;
		String job = (String)cbJob.getSelectedItem();
		String sqlId = "";
		if(job.equals("개발자")){
			sqlId = "SELECT emp_id FROM test_emp WHERE job = 'PL'";
		}else if(job.equals("PL")){
			sqlId = "SELECT emp_id FROM test_emp WHERE job = 'PM'";
		}else if(job.equals("PM")){
			sqlId = "SELECT emp_id FROM test_emp WHERE job = '!'";//더미 
		}
		//아이디 가져오기
		stmt1 = con.createStatement();
		ResultSet rs1 = stmt1.executeQuery(sqlId);
		while(rs1.next()){
			cbMgr.addItem(rs1.getString("emp_id"));			
		}	
		stmt1.close();

		//프로젝트 이름 
		String sqlProject = "SELECT p.p_name p_name "
				+ " FROM test_project p ";

		//프로젝트 명 가져오기
		stmt2 = con.createStatement();
		ResultSet rs2 = stmt2.executeQuery(sqlProject);
		while(rs2.next()){
			cbPName.addItem(rs2.getString("p_name"));
		}
		stmt2.close();
	}
	public void eventProc(){
		ButtonEventHandler btnHandler = new ButtonEventHandler();

		// 이벤트 등록
		cbMgr.addActionListener(btnHandler);
		cbPName.addActionListener(btnHandler);
		idOverlap.addActionListener(btnHandler);
		crAccount.addActionListener(btnHandler);
		tfId.addActionListener(btnHandler);
		cbJob.addActionListener(btnHandler);
		tfId.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0) {}
			public void focusLost(FocusEvent fe) {
				if(!tfId.getText().equals("")){
					try {
						//중복검사 후 -> 중복되는 아이디가 있으면 tfId 필드 지우기 
						if(idOverlapCheck(tfId.getText())){
							JOptionPane.showMessageDialog(null, "다른 아이디를 입력해주세요");
							tfId.setText("");
							return;
						}
					} catch (Exception e) {
						System.out.println("중복검사 오류: " + e.getMessage());
					}
				}

			}			
		});
	}
	// 버튼 이벤트 핸들러 만들기
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();

			if(o==cbJob){ //업무가 선택되었을때 , 상사아이디 프로젝트명 가져올수있음
				try {
					getDefaultInfo();
					System.out.println("상사아이디, 프로젝트명 가져오기 성공!");
				} catch (Exception e) {
					System.out.println("상사아이디, 프로젝트명 가져오기 실패: " + e.getMessage());
				}
			}
			else if(o==cbPName){
				System.out.println("이벤트");
				//updateCustomer();  // 회원정보수정
			}			
			else  if(o==idOverlap || o == tfId){ //TODO: tab 눌렀을때도 중복검사 해보기 
				try {
					//중복검사 후 -> 중복되는 아이디가 있으면 tfId 필드 지우기 
					if(idOverlapCheck(tfId.getText())){
						JOptionPane.showMessageDialog(null, "다른 아이디를 입력해주세요");
						tfId.setText("");
						return;
					}
				} catch (Exception e) {
					System.out.println("중복검사 오류: " + e.getMessage());
				}
			}
			else if(o==crAccount){ //회원가입
				try {
					signUp();
					JOptionPane.showMessageDialog(null, "회원가입 완료");
					dispose();					
				} catch (Exception e) {
					System.out.println("회원가입 오류: " + e.getMessage());
					clear();
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 클리어
	 * @author yeji
	 */
	//tfId, tfPw, tfTel, tfName;
	void clear(){
		tfId.setText("");
		tfPw.setText("");
		tfTel.setText("");
		tfName.setText("");
	}

	/**
	 * 회원가입 
	 * @author yeji
	 */
	void signUp() throws Exception{
		rec = new EmpRecord();
		int pNum = 0;
		String permission = ""; //전체권한 : CPLPM
		//PL 권한: PL
		//PM 권한: PM
		//개발자 권한: C
		//해당없음 : NONE
		//권한 설정
		String job = (String)cbJob.getSelectedItem();
		if(job.equals("PL")){
			permission = "PL";
		}else if(job.equals("개발자")){
			permission = "C";
		}else if(job.equals("PM")){
			permission = "PM";
		}

		//프로젝트 번호 가져오기 
		String tmpSql = "SELECT p_num FROM test_project WHERE p_name = ?";
		ps1 = con.prepareStatement(tmpSql);
		ps1.setString(1, (String)cbPName.getSelectedItem());

		//TODO: 프로젝트가 없는 경우도 생각해야하나? 
		ResultSet tmpRs = ps1.executeQuery();
		if(tmpRs.next())
			pNum = tmpRs.getInt("p_num");

		tmpRs.close(); 


		//sql 
		String sql = "INSERT INTO test_emp VALUES ( ? ,? ,? ,? ,? ,? ,? ,? ,? )";
		System.out.println("여기");
		System.out.println((String)cbJob.getSelectedItem());
		//emp_id, ename, tel, position, job, mgr_id, permission, p_num, emp_pw
		ps2 = con.prepareStatement(sql);
		ps2.setString(1,tfId.getText());  //emp_id
		ps2.setString(2, tfName.getText()); //ename
		ps2.setString(3,tfTel.getText() ); //tel
		ps2.setString(4, (String)cbPosition.getSelectedItem()); //posotion
		ps2.setString(5, (String)cbJob.getSelectedItem()); //job
		ps2.setString(6, (String)cbMgr.getSelectedItem()); //mgr_id
		ps2.setString(7, permission); //permission
		ps2.setInt(8, pNum); //p_num
		ps2.setString(9, tfPw.getText()); //emp_pw

		int result = ps2.executeUpdate();

		ps2.close();		
	}


	/**
	 * 아이디 중복검사 , 하나이상을 반환하면 중복된다는 의미
	 * @author yeji
	 */
	boolean idOverlapCheck(String id) throws Exception{
		//sql
		String sql = "SELECT * FROM V_ACCOUNT_INFO WHERE emp_id = ? ";
		ps1 = con.prepareStatement(sql);

		ps1.setString(1, id);

		ResultSet rs = ps1.executeQuery();

		if(rs.next()){
			ps1.close();
			return true;
		}
		ps1.close();
		return false;

	}
	public void addLayout(){

		tfId			= new JTextField("중복확인:엔터",20);
		tfPw			= new JTextField(20);
		tfTel			= new JTextField(20);
		tfName			= new JTextField(20); //추가

		cbMgr			= new JComboBox();
		cbPName			= new JComboBox();
		cbPosition 		= new JComboBox();
		cbJob 			= new JComboBox();
		idOverlap		= new JButton("중복검사");
		crAccount		= new JButton("회원가입");


		//콤보박스 정보 미리 넣어두기 
		cbPosition.addItem("사장");
		cbPosition.addItem("부장");
		cbPosition.addItem("차장");
		cbPosition.addItem("과장");
		cbPosition.addItem("대리");
		cbPosition.addItem("사원");

		cbJob.addItem("PM");
		cbJob.addItem("PL");
		cbJob.addItem("개발자");

		//tbModelRent = new RentTableModel(); //표를 만들어서
		//tableRecentList = new JTable(tbModelRent);//J테이블(tableRecentList)에 붙임
		// tableVideo.setModel(tbModelVideo);

		setTitle("회원가입");//Frame의 타이틀 이름 주기 
		setSize(300, 400);//Frame의 크기 설정 
		setVisible(true);//생성한 Frame을 윈도우에 뿌리기
		setLayout(new BorderLayout());

		//************화면구성************
		// 전체화면 - 중앙, 하단
		// 상단 - 격자 7x3
		// 하단 - 버튼

		//setLayout(new BorderLayout());
		//		// 전체화면 - 상단,중앙. 붙이는건 나중에.
		//		JPanel p_all = new JPanel();
		//		p_all.setLayout(new BorderLayout());

		// 중앙 격자
		JPanel p_Center = new JPanel();
		p_Center.setLayout(new GridLayout(8,1,5,5));
		p_Center.add(tfId);
		p_Center.add(tfPw);
		p_Center.add(tfName);
		p_Center.add(tfTel);
		p_Center.add(cbPosition);	
		p_Center.add(cbJob);	
		p_Center.add(cbMgr);		
		p_Center.add(cbPName);

		JPanel p_Left = new JPanel();
		p_Left.setLayout(new GridLayout(8,1,5,5));
		p_Left.add(new JLabel("아이디", SwingConstants.CENTER));
		p_Left.add(new JLabel("비밀번호", SwingConstants.CENTER));
		p_Left.add(new JLabel("이름", SwingConstants.CENTER));
		p_Left.add(new JLabel("전화번호", SwingConstants.CENTER));
		p_Left.add(new JLabel("직급", SwingConstants.CENTER));
		p_Left.add(new JLabel("업무", SwingConstants.CENTER));
		p_Left.add(new JLabel("상사아이디", SwingConstants.CENTER));
		p_Left.add(new JLabel("프로젝트명", SwingConstants.CENTER));

		// 하단 버튼
		JPanel p_south = new JPanel();
		p_south.setLayout(new FlowLayout());
		//		
		p_south.add(idOverlap);		
		p_south.add(crAccount);

		//		// 중앙
		//		p_all.add(new JScrollPane(tableRecentList),BorderLayout.CENTER);
		//		// 테이블을 붙일때에는 반드시 JScrollPane() 이렇게 해야함 
		//		
		//		add(p_all);

		sPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, p_Left, p_Center );
		sPane.setDividerLocation(100);//분할 설정
		add( sPane ); // split창을 JPanel에 부착 
		// JPanel은 경량 컨터이너이므로 getContentPane()이 불필요 

		// 생성된 split창을 프레임에 부착 
		getContentPane().add(sPane, BorderLayout.CENTER ); 
		add(p_south, BorderLayout.SOUTH);

	}
}			 	
