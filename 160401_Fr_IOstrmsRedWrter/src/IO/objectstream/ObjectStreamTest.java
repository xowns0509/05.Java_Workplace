package IO.objectstream;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ObjectStreamTest
{
	public static void main( String [] args )
	{
		UIForm2  ui = new UIForm2();
		ui.addToLayout();
		ui.eventProc();
	}
}

//-----------------------------------------
// 화면을 관리하는 클래스
class UIForm2 extends JFrame
{
	// 데이타를 저장할 변수
	String name;
	int		age;
	double 	height;
	char	bloodType;
	
	// 화면 GUI에 관련한 변수
	JTextField tfName, tfAge, tfHeight, tfBloodType;
	JButton	   bSave, bLoad;
	
	
	UIForm2()
	{
		tfName 		= new JTextField(10);
		tfAge 		= new JTextField(10);
		tfHeight 	= new JTextField(10);
		tfBloodType = new JTextField(10);
		
		bSave		= new JButton("저장하기", new ImageIcon("../img/save.gif"));
		bLoad		= new JButton("읽어오기", new ImageIcon("../img/load.gif"));
		
	}
	
	// 화면 구성하는 메소
	void addToLayout()
	{
		JPanel pCenter = new JPanel();
		pCenter.setLayout( new GridLayout(5 ,2,10,10) );
		pCenter.add( new JLabel("이름") );
		pCenter.add( tfName );
		pCenter.add( new JLabel("나이") );
		pCenter.add( tfAge );
		pCenter.add( new JLabel("신장") );
		pCenter.add( tfHeight );
		pCenter.add( new JLabel("혈액형") );
		pCenter.add( tfBloodType );
		
		pCenter.add( bSave );
		pCenter.add( bLoad );
		
		getContentPane().add("Center", pCenter);
		
		setSize( 400, 300 );
		setVisible( true );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

	}

	// 이벤트 처리하는 메소드 
	void eventProc()
	{
		EventHandler hdlr = new EventHandler();
		bSave.addActionListener( hdlr );
		bLoad.addActionListener( hdlr );
		
	}
	
	class EventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			JButton evt = (JButton)ev.getSource();
			// "저장하기" 버튼이 눌렸을 때 
			if( evt == bSave){
				/*
				1. 텍스트필드에서 입력값 얻어와서 변수에 저장
				2. 파일출력스트림 생성 ( filter 포함 )
				3. 스트림에 출력
				4. 스트림 닫기
				5. 텍스트필드 초기화 
				*/
				name		= tfName.getText();
				age			= Integer.parseInt( 	tfAge.getText() );
				height		= Double.parseDouble(	tfHeight.getText() );
				bloodType 	= (tfBloodType.getText()).charAt(0);
				
				System.out.println("파일을 제대로 저장했어");
				
				try{
					ObjectOutputStream out = new ObjectOutputStream(//객체로 전달하네.
						new FileOutputStream("data.txt"));
					Record rec = new Record(name, age, height, bloodType);
					out.writeObject( rec );
					out.close();
				}catch( IOException ex ){ }
				
				tfName.setText("");
				tfAge.setText("");
				tfHeight.setText("");
				tfBloodType.setText("");
			}
			// "읽어오기" 버튼이 눌렸을 
			else if( evt == bLoad ){
				/*
				1. 파일입력스트림 생성 (filter 포함 )
				2. 스트림에서 데이타 읽어서 변수에 저장
				3. 스트림 닫기
				4. 텍스트필드에 읽은값(변수)로 지정 
				*/
				
				try {
/*					FileInputStream fis = new FileInputStream("data.txt");
					ObjectInputStream in = new ObjectInputStream(fis);*/
					
					ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.txt"));
					System.out.println("파일을 제대로 불러왔어");
										
					Record recieVe = (Record)in.readObject();//위에서 Record로 넣었으니 Record로 받아야지.
					
					name = recieVe.getName();
					age = recieVe.getAge();
					height = recieVe.getHeight();
					bloodType = recieVe.getBloodType();
					
					in.close();//스트림 폐쇄

					tfName.setText(name);
					tfAge.setText(String.valueOf(age));
					tfHeight.setText(String.valueOf(height));
					tfBloodType.setText(String.valueOf(bloodType));
					
				} catch (FileNotFoundException ee) {
					ee.printStackTrace();
					
					System.out.println("파일 불러오기 실패");
					/*System.out.println("화일이 존재하지 않습니다. " + ee.getMessage());*/
					
				} catch (IOException ee) {
					ee.printStackTrace();
					
					tfName.setText("이름 불러오기 실패");
					tfAge.setText("나이 불러오기 실패");
					tfHeight.setText("키 불러오기 실패");
					tfBloodType.setText("혈액형 불러오기 실패");
					/*System.out.println("입출력 예외: " + ee.getMessage());*/
					
				} catch (Exception ee){
					System.out.println("그외의 불러오기 실패 이슈 발생");
					/*System.out.println("그외 예외: " + ee.getMessage());*/
				
				}
			}
		}
	}
}