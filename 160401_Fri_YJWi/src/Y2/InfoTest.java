package Y2;
//리스트 액션 실현

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

/* 검색기능 추가 */
class InfoUI implements ActionListener, ListSelectionListener {
	//멤버변수
	JFrame f;
	JButton bInsert,bModify,bDelete,bShow,bExit,bSearch;
	JLabel name, tel, jumin,gender,age,addr;
	JTextField tfName, tfTel, tfJumin, tfGender, tfAge, tfAddr;	
	JTextArea ta;
	File folder;
	ArrayList<Record> list; //arraylist 는 이미 serializable 이므로 바로 통째로 보내기가능!

	DefaultListModel<String> model = new DefaultListModel<String>();
	JList<String> phoneList = new JList<String>(model);
	
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
		bInsert = new JButton("입력"); 
		bModify = new JButton("수정");
		bDelete = new JButton("삭제");
		bShow = new JButton("전체보기");
		bExit = new JButton("종료");
		bSearch = new JButton("검색");

		//텍스트프레임
		tfName = new JTextField(15);
		tfTel = new JTextField();
		tfJumin = new JTextField();
		tfGender = new JTextField();
		tfAge = new JTextField();
		tfAddr = new JTextField();

		//텍스트에리아
		/*ta = new JTextArea();*/

		//list = new ArrayList<Record>();
	}

	//프로그램 시작할때 파일 읽어오기 
	void init(){
		//파일 읽어오기(지정된 파일이 없다면 리스트 객체 새로 생성!)
		model.removeAllElements();
		list = new ArrayList<Record>();
		list.clear();
		try {
			//파일을 통째로 읽어옵니다
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("전화번호부.txt"));
			//객체를 받고 type casting 해줍니다
			list = (ArrayList<Record>)in.readObject();
			 
			for(int i = 0; i < list.size(); i++){
				System.out.println(list.get(i).getName());
				
				model.addElement(list.get(i).getName() + "/" + list.get(i).getTel() + "/" + list.get(i).getId() +
						"/" + list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");	
			}
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
		/*phoneList = new JList<String>(model);*/
	}
	
	void init2(){
		//핸들러 넣기
		bInsert.addActionListener(this);
		bModify.addActionListener(this);
		bDelete.addActionListener(this);
		bShow.addActionListener(this);
		bExit.addActionListener(this);	
		bSearch.addActionListener(this);
		phoneList.addListSelectionListener(this);	
	}

	//test field 리셋 해주는 메소드
	void clear(){
		tfName.setText(null);
		tfTel.setText(null);
		tfJumin.setText(null);
		tfGender.setText(null);
		tfAge.setText(null);
		tfAddr.setText(null);
	}
	/*
	 * 사용자가 모든 input field 를 입력했는지 
	 * 확인해줌. 다 입력하지 않았다면 사용자에게 알려준다음
	 * 그 입력값들을 무시한 
	 */
	boolean hasAll(){
		if(tfName.getText().equals("") || tfTel.getText().equals("") || tfJumin.getText().equals("")
				|| tfGender.getText().equals("") || tfAge.getText().equals("") || tfAddr.getText().equals("")){
			JOptionPane.showMessageDialog(null,"모든 정보를 입력해주세요");
			return false;			
		}
		return true;
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
		p_south.add(bSearch);
		p_south.add(bModify);
		p_south.add(bDelete);
		p_south.add(bShow);
		p_south.add(bExit);

		f.add(p_south, BorderLayout.SOUTH);

		f.add(phoneList);

		f.setSize(650,480);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//핸들러
	public void actionPerformed(ActionEvent e) {

		JButton evt = (JButton)e.getSource();
		boolean hasKey = false;

		//사용자가 모든 field 에 답변을 다 해야 이벤트 처리 할수있음
		String keyVal = tfTel.getText();
		//bShow는 text field에 구애받지 않는 기능이므로 hasAll()을 
		//부르기 전에 먼저 작업 수행
		if(evt == bShow){
			init();
			
			/*ta.setText(null);*/
			hasKey = false;
			//키 값 받아오기 
			keyVal = tfTel.getText();
			//메모리상의 list와 파일 '전화번호부'는 내용이 같으므로 
			//따로 파일을 읽어올 필요가없음
			System.out.println(list.isEmpty());
			//list 가 비어있다면 미리 종료
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "가져올 전화번호부가 없습니다");
				return;
			}
/*			for(int i = 0; i < list.size(); i++){

				//화면에 보이기 
				printList(i);

			}*/
			
			//전체보기 버튼을 누르고 이 작업만 수행을 원하므로, 제어권 반납
			return;
		}else if(evt == bExit){
			System.exit(0);
		}else if(evt == bSearch){
			String temp = null;
			boolean checkSearch = false;

			System.out.println("검색 시작");
/*			ta.setText(null);*/

			temp = tfName.getText();
			if(!temp.equals("")){
				System.out.println("이름 검색 실행됨");
				//				System.out.println("->" + temp + "\n");

				for(int i=0; i < list.size(); i++){
					if(temp.equals(list.get(i).getName()) ){
						checkSearch = true;
					}
				}
			}else System.out.println("이름 검색 실행안됨");

			temp = tfTel.getText();
			if(!temp.equals("") ){
				System.out.println("전화번호 검색 실행됨");

				for(int i=0; i < list.size(); i++){
					if(temp.equals(list.get(i).getTel()) ){
						checkSearch = true;
					}
				}
			}else System.out.println("전화번호 검색 실행안됨");

			temp = tfJumin.getText();
			if(!temp.equals("") ){
				System.out.println("주민번호 검색 실행됨");

				for(int i=0; i < list.size(); i++){
					if(temp.equals(list.get(i).getId()) ){
						checkSearch = true;
					}
				}
			}else System.out.println("주민번호 검색 실행안됨");

			temp = tfGender.getText();
			if(!temp.equals("") ){
				System.out.println("성별 검색 실행됨");

				for(int i=0; i < list.size(); i++){
					if(temp.equals(list.get(i).getSex()) ){
						checkSearch = true;
					}
				}
			}else System.out.println("성별 검색 실행안됨");

			temp = tfAge.getText();
			if(!temp.equals("") ){
				System.out.println("나이 검색 실행됨");

				for(int i=0; i < list.size(); i++){
					if(temp.equals(list.get(i).getAge()) ){
						checkSearch = true;
					}
				}
			}else System.out.println("나이 검색 실행안됨");

			temp = tfAddr.getText();
			if(!temp.equals("") ){
				System.out.println("주소 검색 실행됨");

				for(int i=0; i < list.size(); i++){
					if(temp.equals(list.get(i).getAddr()) ){
						checkSearch = true;
					}
				}
			}else System.out.println("주소 검색 실행안됨");

			if(checkSearch == false){
				System.out.println("일치하는 결과가 없습니다.");

			}
			return;
		}		

		//text field 값이 모두 채워져있지 않으면 제어권 반납
		if(!hasAll()) return;

		if(evt == bInsert){ //입력

			keyVal = tfTel.getText(); //전화번호가 키값
			System.out.println("키값:" + keyVal);
			System.out.println(keyVal.equals(""));
			System.out.println(keyVal.equals(null));
			//동일한 파일명이 있는지 확인 (중복체크)
			for(int i = 0; i < list.size(); i++){
				System.out.println(list.get(i).getTel());
				System.out.println("**");
				if(keyVal.equals(list.get(i).getTel()) ){//중복이면 사용자에게 알림
					System.out.println("in if : " +  list.get(i).getTel());
					JOptionPane.showMessageDialog(null, "이미 등록된 사용자입니다.");
					hasKey = true; break; 
				}
			}
			if(!hasKey){
				//없다면 저장 
				System.out.println("등록!");				
				list.add(new Record(tfName.getText(), tfTel.getText(), tfJumin.getText(),
						tfGender.getText(), tfAge.getText(), tfAddr.getText()));
				hasKey = false;
				
			}
			//파일에 저장
			write(list);
			
			//clear
			clear();
			hasKey = false;
			init();
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
			init();
		}else if(evt == bModify){
			hasKey = false;
			//키 값 받아오기 
			String tmp = tfTel.getText();
			//메모리상의 list와 파일 '전화번호부'는 내용이 같으므로 
			//따로 파일을 읽어올 필요가없음
			for(int i = 0; i < list.size(); i++){
				if(tmp.equals(list.get(i).getTel())){
					//다시 저장 s
					list.set(i, new Record(tfName.getText(), tfTel.getText(), tfJumin.getText(), tfGender.getText()
							,tfAge.getText(), tfAddr.getText()));
					hasKey = true;
				}
			}
			//일치하는 결과가 있을때 
			if(hasKey){
				ObjectOutputStream out;
				try {
					out = new ObjectOutputStream(new FileOutputStream("전화번호부.txt"));
					out.writeObject(list);
					out.close();
					init();
					hasKey = false;
					JOptionPane.showMessageDialog(null, "성공적으로 수정함");
				} catch (IOException e1) {
					System.out.println("bModify 에서 파일 write 에러" + e1.getMessage());
				}
			}
			clear();
		}
	}//end of event handler

	public void valueChanged(ListSelectionEvent e) {
		
		int i = phoneList.getSelectedIndex();//-1을 리턴하는데 어레이리스트의 잘못일까, 리스트의 잘못일까
		System.out.println("리스트액션" + i);
		if(i != -1){
			tfName.setText(list.get(i).getName());
			tfTel.setText(list.get(i).getTel());
			tfJumin.setText(list.get(i).getId());
			tfGender.setText(list.get(i).getSex());
			tfAge.setText(list.get(i).getAge());
			tfAddr.setText(list.get(i).getAddr());
		}
	}
}

public class InfoTest{
	public static void main(String[]args){
		InfoUI ui = new InfoUI();
		ui.init();
		ui.init2();
		ui.addLayout();		
	}
}
