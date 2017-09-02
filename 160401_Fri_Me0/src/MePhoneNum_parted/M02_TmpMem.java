package MePhoneNum_parted;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class M02_TmpMem {

	//멤버변수
	ArrayList<Record> list; //arraylist 는 이미 serializable 이므로 바로 통째로 보내기가능!

	//ArrayList viewAr = new ArrayList();

	DefaultListModel model = new DefaultListModel();
	JList phoneList;

	//생성자. 파일 읽어오기(지정된 파일이 없다면 리스트 객체 새로 생성!)
	M02_TmpMem(){

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
}
