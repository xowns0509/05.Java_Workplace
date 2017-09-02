import java.awt.event.*;
import javax.swing.*;

public class We01_MyMenuAll extends JFrame implements ActionListener{
	//1.필드선언
	JMenuBar mb;
	JMenu mFile, mInfo;
	JMenuItem miOpen, miSave, miExit, miHelp, miAbout, miA, miB, miC;
	JFileChooser saveDilg, openDilg;
	We01_MyDialog helPDilg = new We01_MyDialog();
	//2.생성자
	We01_MyMenuAll(){
		
		mb = new JMenuBar();
		
		mFile = new JMenu("파일");
		mInfo = new JMenu("정보");
		
		miOpen = new JMenuItem("열기");//메뉴버튼 선언
		miSave = new JMenuItem("저장");
		miExit = new JMenuItem("종료");
		miHelp = new JMenu("도움말");
		
		miA = new JMenuItem("항목A");
		miB = new JMenuItem("항목D");
		miC = new JMenuItem("항목C");
		
		miAbout = new JMenuItem("관하여");
		
		saveDilg = new JFileChooser();
		openDilg = new JFileChooser();
	}

	//3.메소드: 레이아웃
	void UILayout(){
		
		mb.add(mFile);//파일메뉴와 그 아래 목록들
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.addSeparator();
		mFile.add(miExit);
		
		mb.add(mInfo);//정보메뉴와 그 아래 목록들
		mInfo.add(miHelp);
		miHelp.add(miA);
		miHelp.add(miB);
		miHelp.add(miC);
		mInfo.add(miAbout);
		
	}
	
	////3.메소드: 이벤트 핸들러와 컴포넌트 연결팩
	void eventProc(){
		//We01_MyMenuAll hdlrInstnce = new We01_MyMenuAll();
		
		miOpen.addActionListener(this);// this로 안하고 hdlrInstnce로 객체 연결할시 이벤트 발생 안함
		miSave.addActionListener(this);
		miExit.addActionListener(this);
		
		miHelp.addActionListener(this);
		miA.addActionListener(this);
		miB.addActionListener(this);
		miC.addActionListener(this);
		miAbout.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem jMItem = (JMenuItem)e.getSource();
		
		if(jMItem == miOpen){
			//JOptionPane.showMessageDialog(null, "열기");
			openDilg.showOpenDialog(null);
		}else if(jMItem == miSave){
			//JOptionPane.showMessageDialog(null, "저장");
			saveDilg.showSaveDialog(null);
		}else if(jMItem == miExit){
			JOptionPane.showMessageDialog(null, "프로그램을 종료할 것이야.");
			System.exit(0);
		}else if(jMItem == miHelp){
			JOptionPane.showMessageDialog(null, "도움말");
		}else if(jMItem == miA){
			JOptionPane.showMessageDialog(null, "항목 A");
		}else if(jMItem == miB){
			JOptionPane.showMessageDialog(null, "항목 B");
		}else if(jMItem == miC){
			JOptionPane.showMessageDialog(null, "항목 C");
		}else if(jMItem == miAbout){
			JOptionPane.showMessageDialog(null, "관하여");
			helPDilg.UILayout();
		}
		
	}
}
