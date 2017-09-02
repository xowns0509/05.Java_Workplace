package IO.datastream;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.xml.crypto.Data;

public class DataStreamTest
{
	public static void main( String [] args )
	{
		UIForm  ui = new UIForm();
		ui.addToLayout();
		ui.eventProc();
	}
}

//-----------------------------------------
// 화면을 관리하는 클래스
class UIForm extends JFrame
{
	// 데이타를 저장할 변수
	String name;
	int		age;
	double 	height;
	char	bloodType;
	
	// 화면 GUI에 관련한 변수
	JTextField tfName, tfAge, tfHeight, tfBloodType;
	JButton	   bSave, bLoad;
	
	
	UIForm()
	{
		tfName 		= new JTextField(10);
		tfAge 		= new JTextField(10);
		tfHeight 	= new JTextField(10);
		tfBloodType = new JTextField(10);
		
		bSave		= new JButton("저장하기", new ImageIcon("src\\IO\\img\\save.gif"));
		bLoad		= new JButton("읽어오기", new ImageIcon("src/IO/img/load.gif"));
		
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
		BtnHandler bHdlr = new BtnHandler();
		bSave.addActionListener(bHdlr);
		bLoad.addActionListener(bHdlr);				
	}


	class BtnHandler implements ActionListener 
	{
		public void actionPerformed( ActionEvent ev ){

			Object evtBtn = ev.getSource();

			// "저장하기" 버튼이 눌렸을 때
			if( evtBtn == bSave )
			{
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
				
				try{
					DataOutputStream out = new DataOutputStream(new FileOutputStream("data.txt"));
					
	/*				FileOutputStream fos= new FileOutputStream("data.txt");
					DataOutputStream out = new DataOutputStream(fos);*/
					
					out.writeUTF(name);
					out.writeInt(age);
					out.writeDouble(height);
					out.writeChar(bloodType);
					out.close();
					
					System.out.println("파일을 제대로 저장했어");
				}catch( IOException ex ){ }
				
				tfName.setText("");
				tfAge.setText("");
				tfHeight.setText("");
				tfBloodType.setText("");
					
				
			}
			// "읽어오기" 버튼이 눌렸을 때
			else if ( evtBtn == bLoad )
			{
				//데이터를 받아 임시로 저장할 변수
				
/*				String rName;
				int rAge;
				int rHeight;
				char rBloodType;*/
				
				try {
					/*FileInputStream fis = new FileInputStream("data.txt");
					DataInputStream in = new DataInputStream(fis);*/
					
					//1. 파일입력스트림 생성 (filter 포함 )
					DataInputStream in = new DataInputStream(new FileInputStream("data.txt"));
					System.out.println("파일을 제대로 불러왔어");
					
					//2. 스트림에서 데이타 읽어서 변수에 저장 반드시 아래와같은 순서대로 읽어야 한다
					//왜? DataOutputStream 할 때 이 순서대로 export 했잖아.
					name = in.readUTF();//알아서 UTF자료형 판별 후 name에 저장.
					age = in.readInt();// 얘네들도 알아서 저장.
					height = in.readDouble();
					bloodType =in.readChar();
					
					//3. 스트림 닫기 
					in.close();
					
					//4. 텍스트필드에 읽은값(변수)로 지정
					tfName.setText(name);
					tfAge.setText(String.valueOf(age));
					tfHeight.setText(String.valueOf(height));
					tfBloodType.setText(String.valueOf(bloodType));
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.out.println("파일 불러오기 실패");
					
				} catch(IOException e){
					
					tfName.setText("이름 불러오기 실패");
					tfAge.setText("나이 불러오기 실패");
					tfHeight.setText("키 불러오기 실패");
					tfBloodType.setText("혈액형 불러오기 실패");
				}


			/*
			1. 파일입력스트림 생성 (filter 포함 )
			2. 스트림에서 데이타 읽어서 변수에 저장
			3. 스트림 닫기 
			4. 텍스트필드에 읽은값(변수)로 지정 
			*/
			}
		}
	}
}
/* 서로다른 데이터 형태 때문에 클래스로 묶어야 하는 상황이 발생
 * 클래스는 통로를 타지 못하므로
 *  * 일렬로 나열하게끔 함. 직렬화.
 * serializable 알아서 직렬화 해줌 

 */
