package chat.Orig;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatClientMainOrig {

	public static void main(String[] args) {
		ChatClient chCl = new ChatClient();
		chCl.addLayout();
		chCl.eventProc();
	}
}

class ChatClient extends JFrame{

	//1.필드선언
	JPanel p_North, p_South, p_East, p_Center;

	JButton nickChge, servCnect, mesgTrsfer;	//버튼변수 선언
	JTextField nickNameTF, serverTF, messageTF; //글자변수 선언
	JTextArea chatBox;							//대화창변수 선언
	JList memberLs;								//멤버창변수 선언
	JLabel nickNme, servNme, membLsLbel, mesg;				//라벨변수 선언

	//통신에 필요한 소켓, 스트림변수
	Socket s;
	BufferedReader in;
	OutputStream out;
	
	//2.생성자
	ChatClient(){

		nickNme = new JLabel("사용자명:  ", JLabel.LEFT);
		servNme = new JLabel("서버명:  ", JLabel.LEFT);
		mesg = new JLabel("메세지:  ", JLabel.LEFT);
		membLsLbel = new JLabel("멤버리스트", JLabel.LEFT);

		nickChge = new JButton("닉넴변경");
		servCnect = new JButton("서버접속");
		mesgTrsfer = new JButton("전송");

		nickNameTF = new JTextField("무엇인가", 20);
		serverTF = new JTextField("서버인가", 15);
		messageTF = new JTextField("메세지입력인가", 45);

		chatBox = new JTextArea("텍스트 에어리어", 20, 55);//채팅창 선언 ("텍스트 에어리어", 세로수치, 가로수치)
		//		memberLs = new JList();//멤버창 선언

		String [] member = {"게스트A", "게스트B"};
		memberLs = new JList(member);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		p_Center.add(chatBox);
		add(p_Center, BorderLayout.CENTER);//chatBox를 그냥 넣을 경우 텍스트에어리어의 크기가 조정 안되는 문제가 발생

		p_East = new JPanel();
		p_East.setLayout(new GridLayout(2, 1));
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

		NickChgeHndlr nChgehdlr = new NickChgeHndlr();	//닉네임관련 핸들러연결
		nickChge.addActionListener(nChgehdlr);
		nickNameTF.addActionListener(nChgehdlr);

		ServCnectHndlr sCncthdlr = new ServCnectHndlr();//서버관련 핸들러연결
		servCnect.addActionListener(sCncthdlr);
		serverTF.addActionListener(sCncthdlr);

		MesgTrsferHndlr mTFerhdlr = new MesgTrsferHndlr();	//메세지관련 핸들러연결
		mesgTrsfer.addActionListener(mTFerhdlr);
		messageTF.addActionListener(mTFerhdlr);	

		/*BtnEvtHndlr btEvtHdlr = new BtnEvtHndlr();
		nickChge.addActionListener(btEvtHdlr);
		servCnect.addActionListener(btEvtHdlr);
		mesgTrsfer.addActionListener(btEvtHdlr);
		nickNameTF.addActionListener(btEvtHdlr);
		serverTF.addActionListener(btEvtHdlr);
		messageTF.addActionListener(btEvtHdlr);*/
	}
	class NickChgeHndlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "nickChge");
		}

	}
	class ServCnectHndlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "servCnect");	
		}

	}
	class MesgTrsferHndlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "mesgTrsfer");
		}

	}

}

