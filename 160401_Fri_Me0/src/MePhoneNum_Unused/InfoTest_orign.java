package Unused;

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

class InfoUI implements ActionListener, ListSelectionListener {
	
	//멤버변수
	JFrame f;
	JButton bInsert,bModify,bDelete,bShow,bExit;
	JLabel name, tel, jumin,gender,age,addr;
	JTextField tfName, tfTel, tfJumin, tfGender, tfAge, tfAddr;
	File folder;

	ArrayList<Record> list ; //arraylist 는 이미 serializable 이므로 바로 통째로 보내기가능!

	DefaultListModel<String> model = new DefaultListModel<String>();
	JList<String> phoneList = new JList<String>(model);
	
	//생성자
	InfoUI(){
		f = new JFrame("Me01_사용자 정보 입력창");		
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

		//텍스트 필드
		tfName = new JTextField(15);
		tfTel = new JTextField();
		tfJumin = new JTextField();
		tfGender = new JTextField();
		tfAge = new JTextField();
		tfAddr = new JTextField();

		//텍스트 에어리어
		/*viewAr.add("rabbit");
		viewAr.add("dog");
		viewAr.add("cat");
		viewAr.add("fox");
		viewAr.add("cat");
		viewAr.add("tiger");
		viewAr.add("zebra");
		viewAr.add("elephant");*/

		//ta = new JTextArea();
		//list = new ArrayList<Record>();
	}

	void init(){

		//생성자. 핸들러 넣기
		bInsert.addActionListener(this);
		bModify.addActionListener(this);
		bDelete.addActionListener(this);
		bShow.addActionListener(this);
		bExit.addActionListener(this);
		phoneList.addListSelectionListener(this);

		try {
			//파일 로딩
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("전화번호부.txt"));
			//객체를 받고 type casting 해줍니다
			list = (ArrayList<Record>)in.readObject();

			for(int i = 0; i < list.size(); i++){
				System.out.println(list.get(i).getName());

				model.addElement(list.get(i).getName() + "/" + list.get(i).getTel() + "/" + list.get(i).getId() +
						"/" + list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");	

				/*viewAr.add(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
						+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");*/

				/*phoneList = new JList(viewAr.toArray());*/
			}
			phoneList = new JList<String>(model);
			in.close();

		} catch (FileNotFoundException e) {

			//이 경우에는 list 객체 생성!
			System.out.println("여기");
			JOptionPane.showMessageDialog(null, "파일 최초생성! list 객체를 만듬(debug)");
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

			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("전화번호부.txt"));
			out.writeObject(list);
			out.close();

		} catch (IOException e1) {
		}	
	}

	//
	void addLayout(){
		f.setSize(650,480);
		f.setVisible(true);
		
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


		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//핸들러
	public void actionPerformed(ActionEvent e) {

		JButton evt = (JButton)e.getSource();
		
		boolean flag = false;		

		if(evt == bInsert){ //입력

			String tmp = tfTel.getText();
			System.out.println(tmp);

			for(int i = 0; i < list.size(); i++){

				System.out.println(list.get(i).getTel());
				System.out.println("**");

				if(tmp.equals(list.get(i).getTel()) ){
					System.out.println("in if : " +  list.get(i).getTel());
					JOptionPane.showMessageDialog(null, "이미 등록된 사용자입니다.");
					flag = true; break; 
				}
			}

			if(!flag){

				System.out.println("여기3!");				
				list.add(new Record(tfName.getText(), tfTel.getText(), tfJumin.getText(),
						tfGender.getText(), tfAge.getText(), tfAddr.getText()));
				flag = false;

			}

			write(list);
			//clear
			clear();
			flag = false;

		}else if(evt == bExit){
			System.exit(0);
		}else if(evt == bDelete){

			String tmp = tfTel.getText();

			//list
			for(int i = 0; i < list.size(); i++){
				if(tmp.equals(list.get(i).getTel())){

					list.remove(i);
					break;
				}
			}			

			write(list);
			//clear
			clear();

		}else if(evt == bShow){	

			model.removeAllElements();
			try {	//파일을 통째로 읽어옵니다
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("전화번호부.txt"));
				//객체를 받고 type casting 해줍니다
				ArrayList<Record> tmp = (ArrayList<Record>)in.readObject();

				list = tmp; 
				for(int i = 0; i < list.size(); i++){
					System.out.println(list.get(i).getName());

					model.addElement(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() + 
							"/"+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");	

					/*viewAr.add(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
						+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");*/

					/*phoneList = new JList(viewAr.toArray());*/
				}
				phoneList = new JList(model);
				in.close();

			} catch (FileNotFoundException ee) {

				//이 경우에는 list 객체 생성!
				System.out.println("여기");
				JOptionPane.showMessageDialog(null, "파일 최초생성! list 객체를 만듬(debug)");
				list = new ArrayList<Record>();
			} catch (IOException ee) {
				System.out.println("뭔가 잘못됨..:( : " + ee.getMessage());
			} catch (ClassNotFoundException ee) {
				System.out.println("클래스 못찾음..:( :" + ee.getMessage());
			}	
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		int i = phoneList.getSelectedIndex();
		System.out.println("리스트액션");
		
	}
}

public class InfoTest_orign{
	public static void main(String[]args){
		InfoUI ui = new InfoUI();
		ui.init(); 
		ui.addLayout();
		ui.valueChanged(null);
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
