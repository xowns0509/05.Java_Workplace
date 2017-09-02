package MePhoneNum_Unparted;
//실패작. 리스트업데이트는 YJ의 Y2로!
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
	
	//멤버변수
	JFrame f;
	JButton bInsert,bModify,bDelete,bShow,bExit;
	JLabel name, tel, jumin,gender,age,addr;
	JTextField tfName, tfTel, tfJumin, tfGender, tfAge, tfAddr;
	File folder;

	ArrayList<Record> list ; //arraylist ? ?대?serializable ?대濡 諛濡 ?듭㎏濡 蹂대닿린媛??

	DefaultListModel<String> model = new DefaultListModel<String>();
	JList<String> phoneList = new JList<String>(model);
	
	//??깆
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
//		bSearch = new JButton("검색");
		
		//텍스트프레임
		tfName = new JTextField(15);
		tfTel = new JTextField();
		tfJumin = new JTextField();
		tfGender = new JTextField();
		tfAge = new JTextField();
		tfAddr = new JTextField();

		//??ㅽ???대━??

	}

	void init(){

		//??깆. ?몃ㅻ??ｊ린
		bInsert.addActionListener(this);
		bModify.addActionListener(this);
		bDelete.addActionListener(this);
		bShow.addActionListener(this);
		bExit.addActionListener(this);
		phoneList.addListSelectionListener(this);

		try {
			//???濡??
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("??踰?몃?.txt"));
			//媛泥대? 諛怨 type casting ?댁????
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

			//??寃쎌곗? list 媛泥????
			System.out.println("?ш린");
			JOptionPane.showMessageDialog(null, "???理珥??? list 媛泥대? 留??debug)");
			list = new ArrayList<Record>();
		} catch (IOException e) {
			System.out.println("萸媛 ?紐삳?.:( : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("?대??紐살갼?..:( :" + e.getMessage());
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

			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("??踰?몃?.txt"));
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

	//?몃ㅻ?
	public void actionPerformed(ActionEvent e) {

		Object evt = e.getSource();
				
		boolean flag = false;		

		if(evt == bInsert){ //???

			String tmp = tfTel.getText();
			System.out.println(tmp);

			for(int i = 0; i < list.size(); i++){

				System.out.println(list.get(i).getTel());
				System.out.println("**");

				if(tmp.equals(list.get(i).getTel()) ){
					System.out.println("in if : " +  list.get(i).getTel());
					JOptionPane.showMessageDialog(null, "?대??깅?? ?ъ⑹????");
					flag = true; break; 
				}
			}

			if(!flag){

				System.out.println("?ш린3!");				
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
			try {	//??쇱 ?듭㎏濡 ?쎌댁듬??
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("??踰?몃?.txt"));
				//媛泥대? 諛怨 type casting ?댁????
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

				//??寃쎌곗? list 媛泥????
				System.out.println("?ш린");
				JOptionPane.showMessageDialog(null, "???理珥??? list 媛泥대? 留??debug)");
				list = new ArrayList<Record>();
			} catch (IOException ee) {
				System.out.println("萸媛 ?紐삳?.:( : " + ee.getMessage());
			} catch (ClassNotFoundException ee) {
				System.out.println("?대??紐살갼?..:( :" + ee.getMessage());
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		int i = phoneList.getSelectedIndex();
		System.out.println("由ъㅽ몄≪" + i);
		
	}
}

public class InfoTest{
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
