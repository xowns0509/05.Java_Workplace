package MePhoneNum_parted;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class M01_UI {
	//멤버변수
	JFrame f;
	JButton bInsert,bModify,bDelete,bShow,bExit;
	JLabel name, tel, jumin,gender,age,addr;
	JTextField tfName, tfTel, tfJumin, tfGender, tfAge, tfAddr;	
	JList phoneList;
	
/*	M02_TmpMem tmpM = new M02_TmpMem();*/

	//생성자
	M01_UI(){
		
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

		//텍스트프레임
		tfName = new JTextField(15);
		tfTel = new JTextField();
		tfJumin = new JTextField();
		tfGender = new JTextField();
		tfAge = new JTextField();
		tfAddr = new JTextField();

		//화면
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

		f.add(this.phoneList, BorderLayout.CENTER);

		f.setSize(650,480);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}