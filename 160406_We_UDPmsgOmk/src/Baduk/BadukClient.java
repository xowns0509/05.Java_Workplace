package Baduk;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;


class  BadukClient extends JPanel implements ActionListener, Runnable
{
	// 생성자 기본 인자
	String serverIP;
	String id;

	// 화면 구성에 관련된 멤버 변수
	MyCanvas	canvas;
	JButton		btnStop, btnGame;

	JTextField	tfBlackRock, tfWhiteRock, tfOrder, tfWinner;

	JComboBox	comMember;
	JTextArea	taChatting;
	JTextField	tfMessage;

	// 네트워크 연결에 관련된 멤버 변수a
	Socket		socClient;
	ObjectInputStream	inStream;
	ObjectOutputStream	outStream;
	
	final static int	PORT=7777;

	Thread		thread;

	// 게임정보에 관련된 멤버 변수
	Vector		badukRock;	// 바둑알정보(Rock)를 저장할 벡터
	boolean		bGamming;	// 게임의 시작을 알리는 변수


	//==================================================
	/* ClientBadukMain 실행시 도스에서 입력값으로 서버IP와 사용자아이디 지정
	   인자값이 없는 경우는 127.0.0.1 서버에 guest 아이디 지정
	*/
	BadukClient(){
		this( "127.0.0.1", "guest" );
	}

	BadukClient(String serverIP, String id){
		this.serverIP = serverIP;
		this.id		= id;

		createGUI();		// 화면출력 UI 완성
		
		connectServer();	// 서버에 연결하기
	}

	//=================================================
	/* 화면을 완성하는 GUI 구성하기
		- 화면 관련 멤버 변수의 객체를 생성
		- JPanel에 layout에 맞게 붙이기
	*/
	void createGUI(){
//<- 1 
		// West 영역 완성
		tfBlackRock	= new JTextField(10);
		tfWhiteRock	= new JTextField(10);
		tfOrder		= new JTextField(10);
		tfWinner	= new JTextField(10);
		JLabel lBlackRock	= new JLabel(" 흑기사 ");
		JLabel lWhiteRock	= new JLabel(" 백기사 ");		
		JLabel lOrder		= new JLabel(" 순  번 ");
		JLabel lWinner		= new JLabel(" 승  리 ");

		JPanel p_west	= new JPanel();
		p_west.setLayout( new GridLayout( 14, 1 ) );
		p_west.add( lBlackRock );
		p_west.add( tfBlackRock );
		p_west.add( lWhiteRock );
		p_west.add( tfWhiteRock );
		p_west.add( lOrder );
		p_west.add( tfOrder );
		p_west.add( lWinner );
		p_west.add( tfWinner );


		//	East 영역 완성
		comMember	= new JComboBox();
		taChatting	= new JTextArea(40,15);
		tfMessage	= new JTextField(15);
		JPanel p_east = new JPanel();
		p_east.setLayout( new BorderLayout( 10, 10 ) );
			JPanel p_east_up = new JPanel();
			JLabel lMember	= new JLabel(" 접속자 " );
			p_east_up.add( lMember );
			p_east_up.add( comMember );		
			//** TextArea의 내용이 많이지만 스크롤바 생기도록
			JScrollPane sp = new JScrollPane(taChatting);
		p_east.add("North", p_east_up );
		p_east.add("Center",  sp);
		p_east.add("South", tfMessage );


		//	South 영역 완성
		btnStop	= new JButton(" 그만하기 ");
		btnGame	= new JButton(" 게임하기 ");

		JPanel p_south = new JPanel();
		p_south.setLayout( new GridLayout( 1, 3 ) );
		p_south.add( btnGame );
		p_south.add( btnStop );

		//	Center 영역 완성
		canvas	= new MyCanvas();
		

		// 전체 클래스에 부분완성된 영역 붙이기
		setLayout( new BorderLayout() );
		add("West", p_west );
		add("East", p_east );
		add("South", p_south );
		add("Center", canvas );
		
		//이벤트 등록
		tfMessage.addActionListener(this);
		btnGame.addActionListener(this);
		btnStop.addActionListener(this);


	}//end of addLayout()

	//==================================================
	/*	소켓클래스로 서버에 연결
		1. 소켓클래스 생성 ( serverIP, port번호 )
		2. 생성된 소켓객체의 입력/출력 스트림 얻어오기
	*/
	void connectServer(){
		try{
			
			socClient = new Socket(serverIP, PORT);//서버 연결
			//inStream = (ObjectInputStream) socClient.getInputStream();
			inStream = new ObjectInputStream(socClient.getInputStream());
			outStream = new ObjectOutputStream(socClient.getOutputStream());
			
			thread = new Thread(this);
			thread.start();
			
			sendId();
			
		} catch(Exception ex){
			JOptionPane.showMessageDialog(null, "서버 연결 실패 :"+ ex.getMessage() );
		}
	}
	
	@Override
	public void run() {
		while(socClient != null){//소켓 감지하여 null이면 쓰레드 정지.
			try {
				BadukServerProtocol obj = (BadukServerProtocol) inStream.readObject();//readobject니까 그 object를 내가 원하는걸로 형변환
			int state = obj.getState();
			Object data = obj.getData();
			
			switch (state) {
			
			//채팅 메세지가 넘어왔을 때 메세지를 받은 경우
			case BadukServerProtocol.Chatting:	setChatting(data);	break;
			//멤버가 추가되어 멤버아이디
			case BadukServerProtocol.CHANGE_MEMBER_ID: addMemberId(data); break;
			//게임자의 아이디값들을 받는 경우.
			case BadukServerProtocol.SET_BADUK_GAMMER: setBadukGammer(data); break;
			//게임의 시작을 받은 경우
			case BadukServerProtocol.START_GAME: startGameConfirm(); break;
			//게임자의 바둑알을 놓은 경우
			case BadukServerProtocol.SET_BADUK_ROCK: setBadukRock(data); break;
			
			}
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				taChatting.append("수신실패");
			}
		}
		
	}
	void setChatting(Object data){
		taChatting.append((String)data + "\n");//챗 온걸 받아서 이전의 챗박스 글들과 합쳐서 뿌림
		taChatting.setCaretPosition(taChatting.getText().lastIndexOf("\n"));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		
		if(evt == tfMessage){
			chatMessageSend();
		}else if(evt == btnGame){
			startGame();
			
		}else if(evt == btnStop){
			
		}
		
	};
	
	void chatMessageSend(){
		//사용자가 입력한 값 얻어오기
		String msg = tfMessage.getText();
		if( msg.equals("")){//공백씹기. 공백은 클라에서 서버로 전송 안 됨.
			return;
		}
		
		BadukClientProtocol obj = new BadukClientProtocol();
		obj.setState(BadukClientProtocol.Chatting);//chatting은 상태
		obj.setData(msg);
		sendInfomation(obj);
		
		tfMessage.setText(null);
		tfMessage.requestFocus();//메세지 첬을 때 텍필드에 커서 가있는거.
		
		
	}
	
	void sendInfomation(BadukClientProtocol obj){
		try {
			outStream.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("그외 에러: " + e.getMessage());
		}
	}


//-> 2

//<-- 3
	//----------------------------------------------------
	/*	서버에 접속후 입력한 아이디를 서버에 전송
		1. 클라이언트에서 서버로 전송 데이타 객체 생성 
			( BadukClientProtocol )
		2. 1번 객체에 state는 BadukClientProtocol.SEND_ID로 지정
		3. 1번 객체에 data는 입력받은 id로 지정
		4. 서버로 전송 ( 소켓의 출력스트림에 write )
			: sendInformation() 이용
	*/
	void sendId(){//바둑클라이언트 프로토콜을 만들어서 ....
		
		BadukClientProtocol obj = new BadukClientProtocol();
		obj.setState(BadukClientProtocol.SEND_ID);//객체의 상태를 설정
		obj.setData(id);
		sendInfomation(obj);//sendInfomation으로 서버에 보내기
		
		
	}

	//---------------------------------------------------
	/*	넘겨받은 아이디값들을 콤보박스에 추가
		1. 넘겨받은 객체를 다시 String[]로 형변환
		2. 기존의 콤보박스의 내용들 모두 지움
		3. String[] 배열값을 콤보박스에 하나씩 추가
	*/
	void addMemberId( Object data ){
		String [] ids = (String[])data;
		comMember.removeAllItems();
		for(int i = 0 ; i<ids.length ; i++){
			comMember.addItem(ids[i]);
		}
		
	}

	//---------------------------------------------------
	// 과제 클라이언트가 종료하고 나갈때 서버에 알린후 소켓 닫기
	void sendExit(){
		//BadukClientProtocol 생성
		//상태:
		//데이타:id값
		//전송
		//스트림과 소켓 닫기
		BadukClientProtocol obj = new BadukClientProtocol();
		obj.setState(BadukClientProtocol.EXIT);//객체의 상태를 설정
		obj.setData(id);
		sendInfomation(obj);//sendInfomation으로 서버에 보내기
		
		try {
			
			socClient = null;
			inStream.close();
			outStream.close();				
			socClient.close();
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}


	}

//--> 3


//<-- 4	
	//---------------------------------------------------
	/*	"게임하기" 버튼 클릭시 서버에 전송
		1. 클라이언트에서 서버로 전송 데이타 객체 생성 
			( BadukClientProtocol )
		2. 1번 객체에 state는 BadukClientProtocol.REQUEST_GAME 값으로 지정
		3. 서버로 전송 ( 소켓의 출력스트림에 write )
			: sendInformation() 이용
		4. "게임하기" 버튼을 한번 누르면 비활성화
	*/
	void startGame(){
		
/*		if( socClient == null){
			connectServer();
		}*/
		
		BadukClientProtocol obj = new BadukClientProtocol();
		obj.setState(BadukClientProtocol.REQUEST_GAME);//객체의 상태를 설정
		sendInfomation(obj);//sendInfomation으로 서버에 보내기
		btnGame.setEnabled(false);
	}

	//--------------------------------------------------
	/*	1. 넙겨받은 데이타(BadukServerProtocol의 data임)를 벡터로 변환
		2. 벡터의 0번째 요소( 흑돌 게임자의 아이디 )를 얻어 흑돌게임자 텍스트필드에 지정
		3. 벡터의 1번째 요소( 흰돌 게임자의 아이디 )를 얻어 흰돌게임자 텍스트필드에 지정
	*/
	void setBadukGammer( Object data ){
		Vector gammers = (Vector) data;
		tfBlackRock.setText( (String)gammers.get(0) );
		tfWhiteRock.setText( (String)gammers.get(1) );
	}
//--> 4


//<-- 5
	//--------------------------------------------------
	/*	게임시작시 초기화 작업
		1. 바둑알정보(Rock 클래스)을 저장할 벡터변수(badukRock) 객체 생성
		2. 게임이 시작됨을 지정할 변수(bGamming)을 true로 초기화
		3. 승리자 텍스트필드 초기화
		4. 바둑판 캔버스 초기화
	 */
	void startGameConfirm(){//게임 확정
		badukRock	= new Vector();
		bGamming	= true;
		tfWinner.setText("");
		canvas.repaint();
	}

	//--------------------------------------------------
	/* 마우스 클릭한 위치에 대한 바둑알의 정보를 서버에 전송
		1. 인자로 넘어온 row와 col 값을 임시벡터 변수에 저장
		2. 서버에 전송
			- BadukClientProtocol 객체 생성
			- state에 BadukClientProtocol.SET_BADUK_ROCK로 지정
			- data에 1번 객체(임시벡터변수)
			- BadukClientProtocol 객체 서버로 전송
	 */
	void sendSetBaduk( int row, int col ){
		Vector v = new Vector();
		v.add(row);
		v.add(col);
		BadukClientProtocol obj = new BadukClientProtocol();
		obj.setState(BadukClientProtocol.SET_BADUK_ROCK);
		obj.setData(v);//좌표 보내기
		sendInfomation(obj);

	}
//--> 5

//<-- 6
	//-----------------------------------------------------
	/* 서버로부터 놓여진 바둑돌의 위치정보를 받았을때
		1. 넘겨받은 객체를 Baduk클래스로 형변환
		2. Baduk객체에서 색상을 얻어서 흑인지 비교후 순서를 백으로 지정
		3. 아니면 흑으로 지정
		4. Baduk객체를 바둑돌을 저장하는 벡터(badukRock)에 추가
		5. 캔버스를 다시 그리기
	 */
	void setBadukRock( Object data ){
		Baduk b = (Baduk)data;
		if(b.getColor() == Baduk.BLACK_BADUK){//참은 흑돌플레이어
			tfOrder.setText("백");
		}else{
			tfOrder.setText("흑");
		}
		badukRock.add(b);
		canvas.repaint();
	}


//<-- 7
	//------------------------------------------------
	/* 게임승리자를 출력하고 모든 화면 초기화
		1. 게임진행중을 확인하는 boolean 변수(bGamming)에 false 지정
		2. 넘겨받은 데이타값을 승리자 텍스트필드에 출력
		3. "게임하기"버튼을 활성화
	 */
	void endGame( Object data ){
		bGamming = false;
		String winner = (String) data;
		//tfWinner.setText( winner );
		JOptionPane.showMessageDialog(null, winner + "님께서 승리하셨습니다!!");
		btnGame.setEnabled( true );
		tfBlackRock.setText("");
		tfWhiteRock.setText("");
		tfOrder.setText("");
		tfWinner.setText("");
	}



	//###################################################
	/* 바둑판 모양을 만든 Canvas
	*/
	class MyCanvas extends Canvas
	{
		int term;			// 한칸의 길이
		Point ptS, ptE;		// 바둑판의 시작점과 끝점좌표

		//----------------------------------------------
		/* Canvas의 그림 그리는 역할을 하는 paint() overriding
		*/
		public void paint(Graphics g){
			// 캔버스의 폭과 높이를 구하여 정사각형의 폭과 높이로 계산
			int w = getWidth();
			int h = getHeight();
			if( w > h ) w = h;
			else if( w < h ) h =w;

			// 4면의 여백
			int margin = 20;
			
			// 바둑 한칸의 길이
			term = w / 18;

			// 시작좌표와 끝좌표 지정
			ptS = new Point( margin, margin );
			ptE = new Point( w - margin, h - margin );

			// 바둑판 글 긋기
			for( int i=0; i < 19 ; i++){
				g.drawLine( ptS.x, ptS.y + term*i , ptE.x,  ptS.y + term*i );
				g.drawLine( ptS.x + term*i, ptS.y, ptS.x + term*i, ptE.y );
			}

			//!!!!!!!! 여기는 나중에 참고
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			// 서버로 넘어온 바둑알정보를 저장하는 벡터에 데이타가 있다면
			// 벡터의 각각 바둑알정보(Rock 클래스)을 얻어 row,column,color 값을 얻어와서
			// 화면에 그 정보로 원 그리기
			int x = 0, y = 0, c = 0;	// 바둑알의  x좌표, y좌표, 색상을 저장할 변수
			int r = term / 2;			// 바둑알의 반지름을 저장할 변수
			if(	badukRock != null ){				
				for( int i =0; i< badukRock.size(); i++){
					Baduk	al = (Baduk)badukRock.get(i);
					x = al.getCol();
					y = al.getRow();
					c = al.getColor();
					
					if( c == Baduk.BLACK_BADUK )	g.setColor(Color.black);
					else	g.setColor( Color.yellow );

					g.fillArc( ptS.x + x*term-r, ptS.y + y*term-r, term, term, 0, 360 );
				}
				// 마지막바둑알에는 빨간 사각표시
				g.setColor( Color.red );
				g.drawRect( ptS.x + x*term-r, ptS.y + y*term-r, term, term );
			}

		}


		//----------------------------------------------
		/* 생성자 함수
		 */
		MyCanvas(){
//<-- 6
			// 바둑판에서 마우스 클릭시 서버에 데이타 전송
			/*	1. 눌려진 좌표값 얻어오기
				2. 1번에서 x값이 바둑판의 몇번째 세로줄인지 지정
					( getXValue() 이용 )
				3. 1번에서 y값이 바둑판의 몇번째 가로줄인지 지정
					( getYValue() 이용 )
				4. 서버에 전송하기 위해 sendSetBaduk() 호출
			 */
			
/*			addMouseListener(new MouseListener);*/
			addMouseListener(new MouseAdapter(){

/*				@Override
				public void mouseClicked(MouseEvent e)
				public void mouseExited(MouseEvent e)
				public void mouseEntered(MouseEvent e)
				public void mousePressed(MouseEvent e)*/

				public void mouseReleased(MouseEvent e) {
					Point p = e.getPoint();
					int col = getXValue(p.x);
					int row = getYValue(p.y);
					// TODO Auto-generated method stub
					if(bGamming){//마우스가 좌표 외의 것을 눌렀을 때 차단하는 놈
						if( row < 0 || col < 0 || row > 18 || col > 18 ) return;
						sendSetBaduk(row, col);
					}
				}
							
			});
			
//--> 6
		}


		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		// 클릭한 x좌표값을 바둑판에서의 column 값으로 변경
		int getXValue( int x ){
			x -= ptS.x;
			int v = x / term;
			int r = x % term;
			if( r > term /2 ) v++;
			return v;
		}
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		// 클릭한 x좌표값을 바둑판에서의 column 값으로 변경
		int getYValue( int y ){
			y -= ptS.y;
			int v = y / term;
			int r = y % term;
			if( r > term /2 ) v++;
			return v;
			
		}
	}
}
