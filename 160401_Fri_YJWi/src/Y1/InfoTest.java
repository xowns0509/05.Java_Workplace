import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class InfoUI implements ListSelectionListener, ActionListener {
	//멤버변수
	JFrame f;
	JButton bInsert,bModify,bDelete,bShow,bExit;
	JLabel name, tel, jumin,gender,age,addr;
	JTextField tfName, tfTel, tfJumin, tfGender, tfAge, tfAddr;	
	/*JTextArea ta;*/
	File folder;

	ArrayList<Record> list; //arraylist 는 이미 serializable 이므로 바로 통째로 보내기가능!
	
	ArrayList viewAr = new ArrayList();
	
	DefaultListModel model = new DefaultListModel();
	JList phoneList;
	
	//생성자
	InfoUI(){
		f = new JFrame("사용자 정보 입력창");		
		//라벨
		name = new JLabel("이름");
		tel = new JLabel("전화번호");
		jumin = new JLabel("주민");
		gender = new JLabel("성별");
		age = new JLabel("나이");
		addr = new JLabel("주소");

		//버튼
		bInsert = new JButton("입력", new ImageIcon("src/images/backpack.png")); 
		bModify = new JButton("수정", new ImageIcon("src/images/backpack2.png"));
		bDelete = new JButton("삭제", new ImageIcon("src/images/bookmark.png"));
		bShow = new JButton("전체보기", new ImageIcon("src/images/bus.png"));
		bExit = new JButton("종료", new ImageIcon("src/images/car.png"));

		//텍스트프레임
		tfName = new JTextField(15);
		tfTel = new JTextField();
		tfJumin = new JTextField();
		tfGender = new JTextField();
		tfAge = new JTextField();
		tfAddr = new JTextField();

		//텍스트에리아
		phoneList = new JList(model);
/*		viewAr.add("rabbit");
		viewAr.add("dog");
		viewAr.add("cat");
		viewAr.add("fox");
		viewAr.add("cat");
		viewAr.add("tiger");
		viewAr.add("zebra");
		viewAr.add("elephant");*/
		
//		ta = new JTextArea();
		//list = new ArrayList<Record>();
		
	}

	//프로그램 시작할때 파일 읽어오기 
	void init(){

		//핸들러 넣기
		bInsert.addActionListener(this);
		bModify.addActionListener(this);
		bDelete.addActionListener(this);
		bShow.addActionListener(this);
		bExit.addActionListener(this);
		phoneList.addListSelectionListener(this);
	

		//파일 읽어오기(지정된 파일이 없다면 리스트 객체 새로 생성!)
		try {
			//파일을 통째로 읽어옵니다
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("전화번호부.txt"));
			//객체를 받고 type casting 해줍니다
			ArrayList<Record> tmp = (ArrayList<Record>)in.readObject();
			
			list = tmp; 
			for(int i = 0; i < list.size(); i++){
				System.out.println(list.get(i).getName());
				
				model.addElement(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
						+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");	
				
				/*viewAr.add(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
						+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");*/

				/*phoneList = new JList(viewAr.toArray());*/
			}
			phoneList = new JList(model);
			in.close();
			
		} catch (FileNotFoundException e) {
			
			//이 경우에는 list 객체 생성!
			System.out.println("여기");
			JOptionPane.showMessageDialog(null, "파일 최초생성! list 객체를 만듬(debug");
			list = new ArrayList<Record>();
		} catch (IOException e) {
			System.out.println("뭔가 잘못됨..:( : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("클래스 못찾음..:( :" + e.getMessage());
		}	
	}

	void clear(){
		tfName.setText(null);
		tfTel.setText(null);
		tfJumin.setText(null);
		tfGender.setText(null);
		tfAge.setText(null);
		tfAddr.setText(null);
	}
	
	void write(ArrayList<Record> list){
		try {
			//통째로 저장!!!!!!!!!!!!!!!!!!!!!!!!
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("전화번호부.txt"));
			out.writeObject(list);
			out.close();
		} catch (IOException e1) {
			System.out.println("파일 write 에러가 발생: " + e1.getMessage());
		}	
	}
	
	//화면
	void addLayout(){
		f.setLayout(new BorderLayout());
		
		//p_west
		JPanel p_west = new JPanel(new GridLayout(6,2));
		p_west.add(name); p_west.add(tfName);
		p_west.add(tel); p_west.add(tfTel);
		p_west.add(jumin); p_west.add(tfJumin);
		p_west.add(gender); p_west.add(tfGender);
		p_west.add(age); p_west.add(tfAge);
		p_west.add(addr); p_west.add(tfAddr);
		f.add(p_west, BorderLayout.WEST);

		//p_south
		JPanel p_south = new JPanel(new GridLayout(1,5));
		p_south.add(bInsert);
		p_south.add(bModify);
		p_south.add(bDelete);
		p_south.add(bShow);
		p_south.add(bExit);
		f.add(p_south, BorderLayout.SOUTH);

		f.add(phoneList, BorderLayout.CENTER);

		f.setSize(650,480);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//리스트 핸들러
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		int i = phoneList.getSelectedIndex();
/*		// getContentPane(): GUI 컴포넌트의 참조를 반환
		// setBackground(): 컴포넌트의 배경을 설정
		// getSelectedIndex(): 선택된 항목의 인덱스 반환
		// 리스트에서 색상 항목이 선택되면 해당 색상으로 배경지정
		getContentPane().setBackground(colors[colorJList.getSelectedIndex()]);
		JOptionPane.showMessageDialog(null, colorNames[colorJList.getSelectedIndex()]);*/
		
		//memberList.getSelectedIndex();//: 선택된 항목의 인덱스 반환
		System.out.println(model.get(i));
		System.out.println(phoneList);
		tfName.setText(String.valueOf(i));
		tfTel.setText(String.valueOf(i));
		tfJumin.setText(String.valueOf(i));
		tfGender.setText(String.valueOf(i));
		tfAge.setText(String.valueOf(i));
		tfAddr.setText(String.valueOf(i));
		
	}
	
	//핸들러
	public void actionPerformed(ActionEvent e) {
		
		JButton evt = (JButton)e.getSource();
		
		boolean flag = false;		

		if(evt == bInsert){ //입력
			//저장만하고 저장된 놈을 안불러오니까 그렇지
			String tmp = tfTel.getText(); //전화번호가 키값
			System.out.println(tmp);
			
			//동일한 파일명이 있는지 확인 (중복체크)
			for(int i = 0; i < list.size(); i++){
				
				System.out.println(list.get(i).getTel());
				System.out.println("**");
				
				if(tmp.equals(list.get(i).getTel()) ){//중복이면 사용자에게 알림
					System.out.println("in if : " +  list.get(i).getTel());
					JOptionPane.showMessageDialog(null, "이미 등록된 사용자입니다.");
					flag = true; break; 
				}
			}
			
			if(!flag){
				//없다면 저장 
				System.out.println("등록!");				
				list.add(new Record(tfName.getText(), tfTel.getText(), tfJumin.getText(),
						tfGender.getText(), tfAge.getText(), tfAddr.getText()));
				flag = false;
				
			}
			//파일에 저장
			write(list);
			//clear
			clear();
			flag = false;
			
		}else if(evt == bExit){
			System.exit(0);
		}else if(evt == bDelete){
			//현재 리스트에서 지우고 
			String tmp = tfTel.getText();
			
			//list 에서 찾기 
			for(int i = 0; i < list.size(); i++){
				if(tmp.equals(list.get(i).getTel())){
					//삭제
					list.remove(i);
					break;
				}
			}			
			//파일로 씀
			write(list);
			//clear
			clear();
			
		}else if(evt == bShow){	

			//메모리상의 list 와 파일 '전화번호부'는 내용이 같아야 한다
			//그러므로 bShow 안에서는 파일을 읽을 필요없음
			//버튼을 눌렀을때 '새로고침' 같은 느낌이 나기위해 textArea 를 클리어해준다.
			model.removeAllElements();
			try {
				//파일을 통째로 읽어옵니다
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("전화번호부.txt"));
				//객체를 받고 type casting 해줍니다
				ArrayList<Record> tmp = (ArrayList<Record>)in.readObject();
				
				list = tmp; 
				for(int i = 0; i < list.size(); i++){
					System.out.println(list.get(i).getName());
					
					model.addElement(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
							+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");	
					
					/*viewAr.add(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
							+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");*/

					/*phoneList = new JList(viewAr.toArray());*/
				}
				phoneList = new JList(model);
				in.close();
				
			} catch (FileNotFoundException ee) {
				
				//이 경우에는 list 객체 생성!
				System.out.println("여기");
				JOptionPane.showMessageDialog(null, "파일 최초생성! list 객체를 만듬(debug");
				list = new ArrayList<Record>();
			} catch (IOException ee) {
				System.out.println("뭔가 잘못됨..:( : " + ee.getMessage());
			} catch (ClassNotFoundException ee) {
				System.out.println("클래스 못찾음..:( :" + ee.getMessage());
			}	
		}
		
	}

}

public class InfoTest{
	public static void main(String[]args){
		InfoUI ui = new InfoUI();
		ui.init(); 
		ui.addLayout();		
	}
}

/*JList phoneList;

ArrayList viewAr = new ArrayList();
viewAr.add(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
					+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");
phoneList = new JList(viewAr.toArray());

DefaultListModel model = new DefaultListModel();
model.removeAllElements();
model.addElement(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
					+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");	
phoneList = new JList(model);*/
