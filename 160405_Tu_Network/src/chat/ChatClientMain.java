package chat;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClientMain {

	public static void main(String[] args) {
		ChatClient chCl = new ChatClient();
		chCl.addLayout();
		chCl.eventProc();
	}
}

class ChatClient extends JFrame{
	//1.필드선언
	JPanel p_North, p_South, p_East, p_Center;				//패널변수 선언

	JButton nickChge, servCnect, mesgTrsfer, servDisCnect;	//버튼변수 선언
	JTextField nickNameTF, serverTF, messageTF; 			//글자변수 선언
	JTextArea chatBox;										//대화창변수 선언
	JList memberLs;							//멤버창변수 선언
	JLabel nickNme, servNme, membLsLbel, mesg;				//라벨변수 선언
	JScrollPane scrBar;

	//통신에 필요한 소켓, 스트림변수
	Socket s;					//import java.net.Socket;
	BufferedReader in;			//import java.io.BufferedReader;
	OutputStream out;			//import java.io.OutputStream;
	Vector list;

	//2.생성자
	ChatClient(){

		nickNme = new JLabel("사용자명:  ", JLabel.LEFT);
		servNme = new JLabel("서버명:  ", JLabel.LEFT);
		mesg = new JLabel("메세지:  ", JLabel.LEFT);
		membLsLbel = new JLabel("멤버리스트", JLabel.LEFT);

		nickChge = new JButton("닉넴변경");
		servCnect = new JButton("서버접속");
		servDisCnect = new JButton("접속해제");
		mesgTrsfer = new JButton("전송");

		nickNameTF = new JTextField("태준EOM", 20);
		serverTF = new JTextField("192.168.0.106", 15);
		messageTF = new JTextField("메세지입력인가", 45);

		chatBox = new JTextArea("텍스트 에어리어", 20, 55);//채팅창 선언 ("텍스트 에어리어", 세로수치, 가로수치)

		memberLs = new JList();	//멤버창 선언
		list = new Vector();
		scrBar = new JScrollPane(chatBox);

		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
	}

	//3.화면 붙이기 및 보이기
	void addLayout(){

		setSize(725, 480);
		setVisible(true);
		setLayout(new BorderLayout());

		p_North = new JPanel();
		//p_North.setLayout(new GridLayout(1, 6));
		p_North.add(nickNme);
		p_North.add(nickNameTF);
		p_North.add(nickChge);
		p_North.add(servNme);
		p_North.add(serverTF);
		p_North.add(servCnect);
		add(p_North, BorderLayout.NORTH);

		p_Center = new JPanel();
		p_Center.add(scrBar);
		add(p_Center, BorderLayout.CENTER);//chatBox를 그냥 넣을 경우 텍스트에어리어의 크기가 조정 안되는 문제가 발생

		p_East = new JPanel();
		p_East.setLayout(new GridLayout(3, 1));
		p_East.add(servDisCnect);
		p_East.add(membLsLbel);
		p_East.add(memberLs);
		add(p_East, BorderLayout.EAST);

		p_South = new JPanel();
		//		p_South.setLayout(new GridLayout(1, 3));
		p_South.add(mesg);
		p_South.add(messageTF);
		p_South.add(mesgTrsfer);
		add(p_South, BorderLayout.SOUTH);

	}

	//4.이벤트 처리 및 등록
	void eventProc(){

		NickChgeHdlr nChgehdlr = new NickChgeHdlr();	//닉네임관련 핸들러연결
		nickChge.addActionListener(nChgehdlr);
		nickNameTF.addActionListener(nChgehdlr);

		ServCnectHdlr sCncthdlr = new ServCnectHdlr();	//서버관련 핸들러연결
		servCnect.addActionListener(sCncthdlr);
		serverTF.addActionListener(sCncthdlr);
		servDisCnect.addActionListener(sCncthdlr);

		MesgTrsferHdlr mTFerhdlr = new MesgTrsferHdlr();//메세지관련 핸들러연결
		mesgTrsfer.addActionListener(mTFerhdlr);
		messageTF.addActionListener(mTFerhdlr);

		addWindowListener(new WindowAdapter() {
			//어뎁터 쓸 때 주의. 오타 못잡아냄.
			public void windowClosing(WindowEvent e){

				closeCom();
				setVisible(false);
				dispose();
				System.exit(0);

			}
		});

		/*BtnEvtHndlr btEvtHdlr = new BtnEvtHndlr();
		nickChge.addActionListener(btEvtHdlr);
		servCnect.addActionListener(btEvtHdlr);
		mesgTrsfer.addActionListener(btEvtHdlr);
		nickNameTF.addActionListener(btEvtHdlr);
		serverTF.addActionListener(btEvtHdlr);
		messageTF.addActionListener(btEvtHdlr);*/
	}

	void closeCom(){
		try{

			if(s != null){
				//아래 out, in, s등이 구현되지 않았을 때. 즉, 접속행하지 않고 그냥 x눌러서 닫을때.
				//이 if문이 없으면 null포인트exception 발생.
				String msg = "/"
						+ "exit " + nickNameTF.getText() + "\n";
				out.write(msg.getBytes());
				in.close();
				out.close();				
				s.close();
				s = null;

			}
		}catch (Exception ex){
			System.out.println("closeCom()의 문제는 " + ex.getMessage());
		}
	}

	//챗방 입장 시 XX입장 처리
	void enterRoom(){
		try{
			String etroom = "/enter " + nickNameTF.getText() + "\n"; //enter 바보\n
			out.write(etroom.getBytes());
		}catch (Exception ex){
			chatBox.append("입장시 오류: " + ex.getMessage());
		}
	}

	//닉바꿈 핸들러
	class NickChgeHdlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				String msg = "/name " + nickNameTF.getText() + "\n";
				out.write(msg.getBytes());

			}catch (Exception ex){
				System.out.println("NickChgeHdlr의 문제는 " + ex.getMessage());
			}
		}
	}
	//서버연결 핸들러 및 수신쓰레드
	class ServCnectHdlr implements ActionListener, Runnable{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();
			//접속 필드 엔터
			if(o == serverTF || o == servCnect){
				try{
					//1.소켓생성
					s = new Socket(serverTF.getText(), 4444);//포트
					//2.입출력스트림 얻어오기
					out = s.getOutputStream();
					in = new BufferedReader(new InputStreamReader(s.getInputStream()));//try catch 필			
					//소켓에서 인풋객체 생성, 이걸 인풋스트림리더로,이걸버퍼리더로
					new Thread(this).start();

					JOptionPane.showMessageDialog(null, "접속합니다.");

					chatBox.setText("접속성공\n");
					enterRoom();//접속알림

				}catch(Exception ex){
					chatBox.setText("접속실패\n" + ex.getMessage());
				}
			}else if(o == servDisCnect){
				closeCom();
			}
		}


		@Override
		public void run() {
			while( s != null ){//s가 소켓이니까 소켓이 null이 아닐때에만 돌아라
				try {

					String msg = in.readLine();//역슬래시 까지 읽어들이는게 readLine
					//System.out.println("서버로부터 수신한 메세지1: "+msg);
					
					StringTokenizer st = new StringTokenizer(msg);
					/*System.out.println("countTokens: "+st.countTokens());*/
					
					if(st.countTokens() >= 1 ){
						String membSrl = st.nextToken();
						if( membSrl.equals("/member")){

							list.removeAllElements();
							while(st.hasMoreTokens()){
								list.add(st.nextToken());	//
								memberLs.setListData(list);	//setListData는 어레이L못받음 벡터만 받음
							}
							continue;

						}
						chatBox.append(msg + "\n");//수신한 메세지를 이전의 chatbox내용과 합친 후 개행.
						scrBar.getVerticalScrollBar().setValue(scrBar.getVerticalScrollBar().getMaximum());//스크롤바 맨 밑으로
					}

				}catch (IOException e) {
					chatBox.append("데이타 수신 실패: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

	}
	//메세지전송 핸들러
	class MesgTrsferHdlr implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				String msg = messageTF.getText() + "\n";//역슬래시로 짤름.
				out.write(msg.getBytes()); //out.write((messageTF.getText() + "\n").getBytes());
				messageTF.setText(null);

				//자동스크롤
				chatBox.setCaretPosition(chatBox.getDocument().getLength());				
				messageTF.requestFocus();//마우스포커스도 유지하기

			} catch (IOException e1) {
				chatBox.append("메세지 전송실패: " + e1.getMessage());
				e1.printStackTrace();
			}	
		}
	}
}