import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class JEventInfoUI {
	//멤버변수 선언
	JFrame f;
	JPanel p_West, p_South, p_East;
	
	JLabel name, tel, jumin, gender, age, addr;
	JTextField tfname, tftel, tfjumin, tfgender, tfage, tfaddr;
	JButton bInsert, bModify, bDelete, bShow, bExit;
	JTextArea ta;
	
	//생성자(객체생성)
	JEventInfoUI(){
		f = new JFrame("사용자 정보 입력 창");//제목

		bInsert = new JButton("입력", new ImageIcon("src/img/mac_os_X.png"));
		//여기서 new ImageIcon("src/img/add-button.png") 안해주면
		//객체 생성시부터 이미지가 없다면 롤오버나 클릭시 변경되는 아이콘이 안뜸.
		bModify = new JButton("수정");
		bDelete = new JButton("삭제");
		bShow = new JButton("전체보기");
		bExit = new JButton("종료");
		
		ta = new JTextArea("텍스트 에어리어");
		
		tfname = new JTextField(5);
		tftel = new JTextField();
		tfjumin = new JTextField();
		tfgender = new JTextField();
		tfage = new JTextField();
		tfaddr = new JTextField();
		
		name = new JLabel("이름", JLabel.LEFT);
		tel = new JLabel("전화", JLabel.LEFT);
		jumin = new JLabel("주민", JLabel.LEFT);
		gender = new JLabel("성별", JLabel.LEFT);
		age = new JLabel("나이", JLabel.LEFT);
		addr = new JLabel("주소", JLabel.LEFT);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	//화면구성 및 보이기
	void addLayout(){
		
		f.setSize(650, 480);
		f.setVisible(true); //어떤 버튼을 누르면 뜨는 창 같은거를 truefalse로 onoff
		f.setLayout(new BorderLayout());
		
		JPanel p_west = new JPanel();
		p_west.setLayout(new GridLayout(6, 2));
		p_west.add(name);
		p_west.add(tfname);
		
		p_west.add(tel);
		p_west.add(tftel);
		
		p_west.add(jumin);
		p_west.add(tfjumin);
		
		p_west.add(gender);
		p_west.add(tfgender);
		
		p_west.add(age);
		p_west.add(tfage);
		
		p_west.add(addr);
		p_west.add(tfaddr);	
		f.add(p_west, BorderLayout.WEST);
		
		JPanel p_south = new JPanel();
		p_south.setLayout(new GridLayout(1, 5));
		p_south.add(bInsert);
		p_south.add(bModify);
		p_south.add(bDelete);
		p_south.add(bShow);
		p_south.add(bExit);
		f.add(p_south, BorderLayout.SOUTH);
		
		f.add(ta, BorderLayout.CENTER);

	}
	
	//입력받는 부분
	
	
	//전체보기
	
	
	ArrayList data = data();
	
		String name = "송혜교";
		int age = 33;
		double height = 163.5;
		
		/*		Object[] data = new Object[3];
			//동적인 오브젝트배열.
			
			data[0] = name;
			data[1] = age;		//원래 안되는건데 1.5 이후 부터는 됨.
								//원래는 이렇게 선언생성 data[1] = new Integer(age);
			data[2] = new Double(height);*/
		
		ArrayList data = new ArrayList(2);//동적으로 크기가 변동되는 오브젝트 배열
		data.add(name);
		data.add(new Integer(age));
		data.add(new Double(height));
		//ArrayList에 넣으려면 Object뿐.
	
	return data;
	
	ArrayList data(){
		
	}
	
	

/*	String tName = tfname.getText();
	String tTel =  tftel.getText();
	String tJumin = tfjumin.getText();
	String tGender = tfgender.getText();
	String tAge = tfage.getText();
	String tAddr = tfaddr.getText();
	
	//출력받는 부분
	String tName = tfname.setText();
	String tTel =  tftel.setText();
	String tJumin = tfjumin.setText();
	String tGender = tfgender.setText();
	String tAge = tfage.setText();
	String tAddr = tfaddr.setText();*/

	
	//이벤트 핸들러
	class BtnHdlr implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton evt = (JButton)e.getSource();
			if(evt == bInsert){
				JOptionPane.showMessageDialog(null, "정보를 입력합니다");
			}else if(evt == bModify){
				JOptionPane.showMessageDialog(null, "정보를 수정합니다");
			}else if(evt == bDelete){
				JOptionPane.showMessageDialog(null, "정보를 삭제합니다");
			}else if(evt == bShow){
				JOptionPane.showMessageDialog(null, "정보를 조회합니다");
			}else if(evt == bExit){
				System.exit(0);
			}
		} 	
	}	
	
	class EventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			JButton evt = (JButton)ev.getSource();
			// "저장하기" 버튼이 눌렸을 때 
			if( evt == bInsert){
				/*
				1. 텍스트필드에서 입력값 얻어와서 변수에 저장
				2. 파일출력스트림 생성 ( filter 포함 )
				3. 스트림에 출력
				4. 스트림 닫기
				5. 텍스트필드 초기화 
				*/
				
				/*	String tName = tfname.getText();
				String tTel =  tftel.getText();
				String tJumin = tfjumin.getText();
				String tGender = tfgender.getText();
				String tAge = tfage.getText();
				String tAddr = tfaddr.getText();
				*/
				
				String name		= tfname.getText();
				String tTel 	= tftel.getText();
				String tJumin 	= tfjumin.getText();
				String tGender 	= tfgender.getText();
				String tAge		= tfage.getText();
				String tAddr 	= tfaddr.getText();
				
				System.out.println("파일을 제대로 저장했어");
				
				try{
					ObjectOutputStream out = new ObjectOutputStream(//객체로 전달하네.
						new FileOutputStream("data.txt"));
					Record rec = new Record(name, tJumin, tTel, tGender, tAge, tAddr);
					out.writeObject( rec );
					out.close();
				}catch( IOException ex ){ }
				
				tfname.setText("");
				tftel.setText("");
				tfjumin.setText("");
				tfgender.setText("");
				tfage.setText("");
				tfaddr.setText("");
				
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
	//이벤트 처리
	void eventProc(){
		
		BtnHdlr bh = new BtnHdlr();
		bInsert.addActionListener(bh);
		bModify.addActionListener(bh);
		bDelete.addActionListener(bh); 
		bShow.addActionListener(bh); 
		bExit.addActionListener(bh);
		
	}

}

public class Fr03_GUI_Swing_PhoneNum {

	public static void main(String[] args) {
	
		JEventInfoUI ui = new JEventInfoUI();
		ui.addLayout();
		ui.eventProc();
		
	}
}

/*홍길동 881111-1234678 010-3158-4556 남 29 서울시
홍길녀 881111-1234678 010-3333-2222 여 25 판교
홍길동 881111-1234678 010-3158-4556 남 29 서울시
홍길동 881111-1234678 010-3158-4556 남 29 서울시
홍길동 881111-1234678 010-3158-4556 남 29 서울시*/