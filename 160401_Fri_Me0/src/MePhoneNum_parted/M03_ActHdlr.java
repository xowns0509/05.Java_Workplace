package MePhoneNum_parted;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class M03_ActHdlr implements ActionListener{

	//1.필드생성
	JTextField tfN, tfT, tfJ, tfG, tfAge, tfAddr;
	JButton bInsert,bModify,bDelete,bShow,bExit;
	JLabel name, tel, jumin, gender, age, addr;
	
	M02_TmpMem tmpM = new M02_TmpMem();

	//생성자. 핸들러 넣기
	M03_ActHdlr(){
		bInsert.addActionListener(this);
		bModify.addActionListener(this);
		bDelete.addActionListener(this);
		bShow.addActionListener(this);
		bExit.addActionListener(this);
	}
	
	void clear(){
		tfN.setText(null);
		tfT.setText(null);
		tfJ.setText(null);
		tfG.setText(null);
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

	//핸들러
	public void actionPerformed(ActionEvent e) {

		JButton evt = (JButton)e.getSource();
		boolean flag = false;		

		if(evt == bInsert){ //입력
			//저장만하고 저장된 놈을 안불러오니까 그렇지
			String tmp = tfTel.getText(); //전화번호가 키값
			System.out.println(tmp);

			//동일한 파일명이 있는지 확인 (중복체크)
			for(int i = 0; i < tmpM.list.size(); i++){

				System.out.println(tmpM.list.get(i).getTel());
				System.out.println("**");

				if(tmp.equals(tmpM.list.get(i).getTel()) ){//중복이면 사용자에게 알림
					System.out.println("in if : " +  tmpM.list.get(i).getTel());
					JOptionPane.showMessageDialog(null, "이미 등록된 사용자입니다.");
					flag = true; break; 
				}
			}

			if(!flag){
				//없다면 저장 
				System.out.println("등록!");				
				tmpM.list.add(new Record(tfName.getText(), tfTel.getText(), tfJumin.getText(),
						tfGender.getText(), tfAge.getText(), tfAddr.getText()));
				flag = false;

			}
			//파일에 저장
			write(tmpM.list);
			//clear
			clear();
			flag = false;

		}else if(evt == bExit){
			System.exit(0);
		}else if(evt == bDelete){
			//현재 리스트에서 지우고 
			String tmp = tfTel.getText();

			//list 에서 찾기 
			for(int i = 0; i < tmpM.list.size(); i++){
				if(tmp.equals(tmpM.list.get(i).getTel())){
					//삭제
					tmpM.list.remove(i);
					break;
				}
			}			
			//파일로 씀
			write(tmpM.list);
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

/*	class Hdlr

void eventProc(){

	NickChgeHndlr nChgehdlr = new NickChgeHndlr();	//닉네임관련 핸들러연결
	nickChge.addActionListener(nChgehdlr);
	nickNameTF.addActionListener(nChgehdlr);

	ServCnectHndlr sCncthdlr = new ServCnectHndlr();//서버관련 핸들러연결
	servCnect.addActionListener(sCncthdlr);
	serverTF.addActionListener(sCncthdlr);

	MesgTrsferHndlr mTFerhdlr = new MesgTrsferHndlr();	//메세지관련 핸들러연결
	mesgTrsfer.addActionListener(mTFerhdlr);
	messageTF.addActionListener(mTFerhdlr);	

	BtnEvtHndlr btEvtHdlr = new BtnEvtHndlr();
	nickChge.addActionListener(btEvtHdlr);
	servCnect.addActionListener(btEvtHdlr);
	mesgTrsfer.addActionListener(btEvtHdlr);
	nickNameTF.addActionListener(btEvtHdlr);
	serverTF.addActionListener(btEvtHdlr);
	messageTF.addActionListener(btEvtHdlr);
}*/

/*class NickChgeHndlr implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "nickChge");
	}

}
class ServCnectHndlr implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "servCnect");	
	}

}
class MesgTrsferHndlr implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "mesgTrsfer");
	}

}*/
