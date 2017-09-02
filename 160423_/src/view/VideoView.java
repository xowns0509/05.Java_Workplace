package	 view;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.AbstractTableModel; 
import javax.swing.text.TabExpander;

import model.VideoModel;
import model.dao.Customer;
import model.dao.Video;
import view.CustomerView.ButtonEventHandler;


public class VideoView extends JPanel
{	
	//	member field
	JTextField	tfVideoNum, tfVideoTitle, tfVideoDirector, tfVideoActor;
	JComboBox	comVideoJanre;
	JTextArea	taVideoContent;

	JCheckBox	cbMultiInsert;
	JTextField	tfInsertCount;

	JButton		bVideoInsert, bVideoModify, bVideoDelete;

	JComboBox	comVideoSearch;
	JTextField	tfVideoSearch;
	
	JTable		tableVideo;
	VideoTableModel tbModelVideo = null;
	
	int searchedVideoNum = 0;

	//데이터베이스 연결객체 선언
	VideoModel db = null;

	//##############################################
	//	constructor method
	public VideoView(){
		addLayout(); 	// 화면설계
		eventProc();
		connectDB();	// DB연결
		initProc();
	}
	
	/*화면 초기화*/
	public void initProc(){
//		tfVideoNum.setEnabled(false);
		tfVideoNum.setEditable(false);//비디오번호 텍스트필드를 처음에 안보이도록 초기화
		tfInsertCount.setEditable(false);
		tfInsertCount.setHorizontalAlignment(JTextField.RIGHT);//수평위치 정렬. 숫자가 맨 오른쪽으로 가게끔.
	}
	
	void insertVideo(){
		
		//1. 화면의 입력값들을 얻어와서 Video 클래스로 지정(setter/constructer)
		Video vid = new Video();
		
		//2. 입고 갯수도 얻어와야지.
		int count = Integer.parseInt(tfInsertCount.getText());
		
		//vid.setVideoNo(Integer.parseInt(tfVideoNum.getText()));입력시 비디오번호는 갖고올 필요가 없지.
		vid.setGenre(String.valueOf(comVideoJanre.getSelectedItem()));
		vid.setVideoName(tfVideoTitle.getText());
		vid.setDirector(tfVideoDirector.getText());
		vid.setActor(tfVideoActor.getText());
		vid.setExp(taVideoContent.getText());
		
		try{
			
			//3.VideoModel의 insertVideo() 호출
			db.insertVideo(vid, count);

			System.out.println("VideoView, insertVideo메소드 정상 실행.");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			// 끝까지 인식 못하면 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}
		//4.결과처리. 화면초기화 or 입력되었다고 메세지창 팝업.
		
	}
	
	void searchVideo(){
	
		//사용자의 입력값을 얻어오기
		int idx = comVideoSearch.getSelectedIndex();//콤보박스의 인덱스 얻어오기.
		String word = tfVideoSearch.getText();
		
		try{
			
			tbModelVideo.data = db.searchVideo(idx, word);//리턴을 어레이리스트로.
			tableVideo.setModel(tbModelVideo);
			tbModelVideo.fireTableDataChanged();//데이터를 갖고 있는 모델측에서 내용이 변경된 사실을 뷰측에다 알려줘야함.

			System.out.println("VideoView, insertVideo메소드 정상 실행.");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			// 끝까지 인식 못하면 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}
		
	}
	
	void deleteVideo(){
		
		try{
			
			//3.VideoModel의 deleteVideo() 호출
			db.deleteVideo(Integer.parseInt(tfVideoNum.getText()));
			
			System.out.println("VideoView, deleteVideo 메소드 정상 실행.");//
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			// 끝까지 인식 못하면 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}
		
		searchVideo();
	}
	
	void updateVideo(){ //수정필요
		
		//1. 화면의 입력값들을 얻어와서 Video 클래스로 지정(setter/constructer)
		Video vid = new Video();
		
		//2. 입고 갯수도 얻어와야지.
		
		vid.setVideoNo(Integer.parseInt(tfVideoNum.getText()));//수정시엔 비디오번호필요.
		vid.setGenre(String.valueOf(comVideoJanre.getSelectedItem()));
		vid.setVideoName(tfVideoTitle.getText());
		vid.setDirector(tfVideoDirector.getText());
		vid.setActor(tfVideoActor.getText());
		vid.setExp(taVideoContent.getText());
		
		try{
			
			//3.VideoModel의 insertVideo() 호출
			db.updateVideo(vid);

			System.out.println("VideoView, insertVideo메소드 정상 실행.");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			// 끝까지 인식 못하면 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}
		//4.결과처리. 화면초기화 or 입력되었다고 메세지창 팝업.
	}

	
	public void connectDB(){	// DB연결
		try {
			db = new VideoModel();
		} catch (Exception e) { //기존에 throws Exception으로 되어있었으므로 Exception로 자동지정됨.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eventProc(){
		EvtHandler btnHandler = new EvtHandler();
		
		// 이벤트 등록
		bVideoInsert.addActionListener(btnHandler);
		bVideoModify.addActionListener(btnHandler);
		bVideoDelete.addActionListener(btnHandler);
		tfVideoSearch.addActionListener(btnHandler);
		cbMultiInsert.addActionListener(btnHandler);
		
		//JTable 마우스클릭 이벤트 발생 시
		//table.addMouseListener(new MouseListener() {	//다른 메소드 안쓰기위해 어뎁터 클래스로 바꿔줘.
		//MouseListener 쓴다면 밑의 오버라이딩메소드를 죄다 써줘야해.
		tableVideo.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = tableVideo.getSelectedRow();	//테이블에서 선택한 행
				int col = 0;							//무조건 0. 비디오 번호만 갖고 오도록.
				
				//오브젝트형으로 반환하므로 형변환해라.
				//selectedName = (String)tableVideo.getValueAt(row, col); //테이블 비디오 넘버가 String이라면 				
				int vnum = (Integer)tableVideo.getValueAt(row, col);//테이블의 비디오 넘버가 int형이라면
				//int vnum = ((Integer)tableVideo.getValueAt(row, col)).intValue()//1.5버전 이전.
				
				tfVideoNum.setText(String.valueOf(vnum));
				tfVideoTitle.setText((String)tableVideo.getValueAt(row, 1));//제목
				comVideoJanre.setSelectedItem((String)tableVideo.getValueAt(row, 2));//장르
				tfVideoDirector.setText((String)tableVideo.getValueAt(row, 3));//감독
				tfVideoActor.setText((String)tableVideo.getValueAt(row, 4));//배우
				
				//System.out.println(selectedName);
				//label.setText("다른 것으로 선택하시려면 해당되는 번호를 다시 클릭하세요");
				//button.setText(selectedName + "로 결정하려면 이 버튼을 누르세요.");
				searchExp(vnum);

			}
		});
	}
	
	void searchExp(int vnum){
		
		try{
			
			//3.VideoModel의 insertVideo() 호출

			taVideoContent.setText(db.searchExp(vnum));
			System.out.println("VideoView, searchExp메소드 정상 실행.");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패: " + e.getMessage());
			// 끝까지 인식 못하면 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLexception발생. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("그 외 예외. " + e.getMessage());
		}finally{
		}

	}
	
	class EvtHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			
			if(o==bVideoInsert){  
				insertVideo();								// 입고
			}
			else if(o==bVideoModify){  
				updateVideo();								// 수정
			}			
			else  if(o==bVideoDelete){  // 이름검색
				deleteVideo();								// 삭제
			}
			else if(o==tfVideoSearch){  // 이름검색
				searchVideo();	//찾기
			}
			else if(o==cbMultiInsert){  // 이름검색
				tfInsertCount.setEditable(cbMultiInsert.isSelected());
				//cbMultiInsert.isSelected(): 체크박스의 상태를 따져서 체크박스가 선택 안되어있다면 false,를 선택이 되어있다면 true를 줌
			}
		}
	}
	
	//  화면설계 메소드
	public void addLayout(){
		//멤버변수의 객체 생성
		tfVideoNum = new JTextField();
		tfVideoTitle = new JTextField();
		tfVideoDirector = new JTextField();
		tfVideoActor = new JTextField();
		
		String []cbJanreStr = {"멜로","엑션","스릴","코미디"};
		comVideoJanre = new JComboBox(cbJanreStr);
		taVideoContent = new JTextArea();
		
		cbMultiInsert = new JCheckBox("다중입고");
		tfInsertCount = new JTextField("1",5);
	
		bVideoInsert = new JButton("입고");
		bVideoModify = new JButton("수정");
		bVideoDelete = new JButton("삭제");
		
		String []cbVideoSearch = {"제목","감독"};
		comVideoSearch = new JComboBox(cbVideoSearch);
		tfVideoSearch = new JTextField(15);
		
		tbModelVideo = new VideoTableModel();
		tableVideo = new JTable(tbModelVideo);
		// tableVideo.setModel(tbModelVideo);
		
		
		
		//************화면구성************
		//왼쪽영역
		JPanel p_west = new JPanel();
		p_west.setLayout(new BorderLayout());
		// 왼쪽 가운데
		JPanel p_west_center = new JPanel();	
		p_west_center.setLayout(new BorderLayout());
		// 왼쪽 가운데의 윗쪽
		JPanel p_west_center_north = new JPanel();
		p_west_center_north.setLayout(new GridLayout(5,2));
		p_west_center_north.add(new JLabel("비디오번호"));
		p_west_center_north.add(tfVideoNum);
		p_west_center_north.add(new JLabel("장르"));
		p_west_center_north.add(comVideoJanre);
		p_west_center_north.add(new JLabel("제목"));
		p_west_center_north.add(tfVideoTitle);
		p_west_center_north.add(new JLabel("감독"));
		p_west_center_north.add(tfVideoDirector);
		p_west_center_north.add(new JLabel("배우"));
		p_west_center_north.add(tfVideoActor);
		
		// 왼쪽 가운데의 가운데
		JPanel p_west_center_center = new JPanel();
		p_west_center_center.setLayout(new BorderLayout());
		// BorderLayout은 영역 설정도 해야함
		p_west_center_center.add(new JLabel("설명"),BorderLayout.WEST);
		p_west_center_center.add(taVideoContent,BorderLayout.CENTER);
		
		// 왼쪽 화면에 붙이기
		p_west_center.add(p_west_center_north,BorderLayout.NORTH);
		p_west_center.add(p_west_center_center,BorderLayout.CENTER);
		p_west_center.setBorder(new TitledBorder("비디오 정보입력"));
		
		// 왼쪽 아래
		JPanel p_west_south = new JPanel();		
		p_west_south.setLayout(new GridLayout(2,1));
		
		JPanel p_west_south_1 = new JPanel();
		p_west_south_1.setLayout(new FlowLayout());
		p_west_south_1.add(cbMultiInsert);
		p_west_south_1.add(tfInsertCount);
		p_west_south_1.add(new JLabel("개"));
		p_west_south_1.setBorder(new TitledBorder("다중입력시 선택하시오"));
		// 입력 수정 삭제 버튼 붙이기
		JPanel p_west_south_2 = new JPanel();
		p_west_south_2.setLayout(new GridLayout(1,3));
		p_west_south_2.add(bVideoInsert);
		p_west_south_2.add(bVideoModify);
		p_west_south_2.add(bVideoDelete);
		
		p_west_south.add(p_west_south_1);
		p_west_south.add(p_west_south_2);
		
		p_west.add(p_west_center,BorderLayout.CENTER);
		p_west.add(p_west_south, BorderLayout.SOUTH);   // 왼쪽부분완성
		
		// 화면구성 - 오른쪽영역
		JPanel p_east = new JPanel();
		p_east.setLayout(new BorderLayout());
		
		JPanel p_east_north = new JPanel();
		p_east_north.add(comVideoSearch);
		p_east_north.add(tfVideoSearch);
		p_east_north.setBorder(new TitledBorder("비디오 검색"));
		
		p_east.add(p_east_north,BorderLayout.NORTH);
		p_east.add(new JScrollPane(tableVideo),BorderLayout.CENTER);
		// 테이블을 붙일때에는 반드시 JScrollPane() 이렇게 해야함 
		
		
		// 전체 화면에 왼쪽 오른쪽 붙이기
		setLayout(new GridLayout(1,2));
		
		add(p_west);
		add(p_east);
		
	}
	
	//화면에 테이블 붙이는 메소드 
	class VideoTableModel extends AbstractTableModel { 
		  
		ArrayList data = new ArrayList();
		String [] columnNames = {"비디오번호","제목","장르","감독","배우"};

		//=============================================================
		// 1. 기본적인 TabelModel  만들기
		// 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
		// AbstractTabelModel에서 구현되지 않았기에...
		// 반드시 사용자 구현 필수!!!!

		    public int getColumnCount() { 
		        return columnNames.length; 
		    } 
		     
		    public int getRowCount() { 
		        return data.size(); 
		    } 

		    public Object getValueAt(int row, int col) { 
				ArrayList temp = (ArrayList)data.get( row );
		        return temp.get( col );
		    }
		    
		    public String getColumnName(int col){
		    	return columnNames[col];
		    }
		    
	}
}


