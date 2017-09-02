package IO.readerwriter;
//추가로 JFileChooser사용.

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReaderWriterTest
{
	public static void main( String args[])
	{
		UIForm3 ui = new UIForm3();
		ui.addLayout();
		ui.eventProc();	
	}	
}

//========================================
//	화면을 관리하는 클래스 
//----------------------------------------
class UIForm3 extends JFrame
{
	JTextArea	ta;
	JButton 	bSave, bLoad, bClear;

	JFileChooser fd; //개발자 스타일. 생성자에 있는걸 여기다 동시에 생성해도 상관 없음
	JScrollPane scrBar;
	
	UIForm3()
	{
		ta		= new JTextArea();		
		bSave 	= new JButton("파일저장");
		bLoad	= new JButton("파일읽기");
		bClear	= new JButton("화면지우기");
		fd = new JFileChooser();
		scrBar = new JScrollPane(ta);
	}

	void addLayout()
	{
		JPanel pCenter 	= new JPanel();
		pCenter.setLayout( new BorderLayout() );
		/*pCenter.add("Center", ta );*/
		pCenter.add("Center", scrBar );

		JPanel pSouth	= new JPanel();
		pSouth.add( bSave );
		pSouth.add( bLoad );
		pSouth.add( bClear );

		getContentPane().add("Center", pCenter );
		getContentPane().add("South",  pSouth );

		setSize( 400, 350 );
		setVisible( true );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	void eventProc()
	{
		EventHandler hdlr = new EventHandler();

		bSave.addActionListener(hdlr);
		bLoad.addActionListener(hdlr);
		bClear.addActionListener(hdlr);
		/*fd.addActionListener(l);*/

	}

	class EventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object evt = ev.getSource();

			// "화일저장" 버튼이 눌렸을 때 
			if( evt == bSave){
				int returnValue = fd.showSaveDialog(/*"자기를 열어준 컨테이너를 기술"*/null); /*x버튼, 취소, ok버튼 등 버튼마다 반환값들이 int로 이미 상수화 쳐리되었다는 거지.*/
				if(returnValue == JFileChooser.APPROVE_OPTION){
					/*JOptionPane.showMessageDialog(null, "넌 저장버튼을 눌렀지");//파일 이름을 입력하여 저장액션이 될 때 이게 뜨는거임. 아무이름도 없으면 저장이 안되잖아.*/

					File f = fd.getSelectedFile();
					String filePath = f.getPath();//그 파일의 경로 얻어오기

					try {

						FileWriter out = new FileWriter(filePath);//입 출력은 100%예외처리 필수
						out.write(ta.getText());
						out.close();

					} catch (IOException ee) {
						System.out.println(""+ee.getMessage());
					} catch (Exception ee) {
						ee.printStackTrace();//모든 에러를 추적하고자 할 때.
					}
				}
			}else if( evt == bLoad){
				int returnValue = fd.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION){

					File f = fd.getSelectedFile();
					String filePath = f.getPath();//그 파일의 경로 얻어오기

					try {

						FileReader in = new FileReader(filePath); 
						boolean flag = false;

						int tmp;

						while(!flag){
							tmp = in.read();//FileReader∙Writer는 단위가 문자형(2바이트). read()메소드는 반환형이 항상 int형임.
							if(tmp == -1){	//int형인 tmp로 받아도 굳이 문제가 되지 않는 이유는 2바이트에서 4바이트로 저장하는거니까 손실없음.
								flag = true;
								
							}else{
								System.out.println(tmp); //이걸로 콘솔 출력하면 아스키 10진코드가 출력됨
								System.out.println((char)tmp);//우리는 문자로 뽑아내기 위해 char로 형변환 했지. 게다가 자바는 char가 유니코드 2바이트라서 한글도 안깨지고 출력 가능.
								ta.append(String.valueOf((char)tmp));//받은 문자형들 마다 String으로 형변환 후 ta에 계속 붙여나감.
							}
						}
						in.close();
						
					} catch (FileNotFoundException ee) {
						System.out.println("파일 로드 실패");
						ee.printStackTrace();
					} catch (IOException ee) {
						System.out.println("파일 IOException");
						ee.printStackTrace();
					} catch (Exception ee) {
						System.out.println("그외 Exception");
						ee.printStackTrace();
					}

				}

			}else if( evt == bClear){
				JOptionPane.showMessageDialog(null, "텍스트창을 초기화 합니다");
				ta.setText("");
			}
		}
	}
}

/*FileReader in = new FileReader(filePath); 
boolean flag = false;

int tmp;

while(!flag){
	tmp = in.read();//FileReader∙Writer는 단위가 문자형(2바이트). read()메소드는 반환형이 항상 int형임.
	if(tmp == -1){	//int형인 tmp로 받아도 굳이 문제가 되지 않는 이유는 2바이트에서 4바이트로 저장하는거니까 손실없음.
		flag = true;
		
	}else{
		System.out.println(tmp); 이걸로 콘솔 출력하면 아스키 10진코드가 출력됨
		System.out.println((char)tmp);//우리는 문자로 뽑아내기 위해 char로 형변환 했지. 게다가 자바는 char가 유니코드 2바이트라서 한글도 안깨지고 출력 가능.
		ta.append(String.valueOf((char)tmp));//받은 문자형들 마다 String으로 형변환 후 ta에 계속 붙여나감.
	}
}
in.close();*/

/*FileReader in = new FileReader(filePath); 
boolean flag = false;

char tmpCh;

while(!flag){
	tmpCh = ((char)(in.read()));//FileReader∙Writer는 단위가 문자형(2바이트). read()메소드는 반환형이 항상 int형임.
	if(in.read() == -1){	//int형인 tmp로 받아도 굳이 문제가 되지 않는 이유는 2바이트에서 4바이트로 저장하는거니까 손실없음.
		flag = true;
		
	}else{
		System.out.println(tmp); 이걸로 콘솔 출력하면 아스키 10진코드가 출력됨
		System.out.println(tmpCh);//우리는 문자로 뽑아내기 위해 char로 형변환 했지. 게다가 자바는 char가 유니코드 2바이트라서 한글도 안깨지고 출력 가능.
		ta.append(String.valueOf(tmpCh));//받은 문자형들 마다 String으로 형변환 후 ta에 계속 붙여나감.
	}
}*/