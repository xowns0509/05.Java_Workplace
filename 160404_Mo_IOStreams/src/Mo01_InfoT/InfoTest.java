package Mo01_InfoT;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class InfoUI implements ActionListener{
	//멤버변수
	JFrame f;
	JButton bInsert,bModify,bDelete,bShow,bExit;
	JLabel name, tel, jumin,gender,age,addr;
	JTextField tfName, tfTel, tfJumin, tfGender, tfAge, tfAddr;	
	JTextArea ta;
	StringBuffer sb = new StringBuffer();

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

		//텍스트프레임
		tfName = new JTextField(15);
		tfTel = new JTextField();
		tfJumin = new JTextField();
		tfGender = new JTextField();
		tfAge = new JTextField();
		tfAddr = new JTextField();

		//텍스트에리아
		ta = new JTextArea();
	}

	void init(){
		//버튼 핸들러 추가 
		bInsert.addActionListener(this);
		bModify.addActionListener(this);
		bDelete.addActionListener(this);
		bShow.addActionListener(this);
		bExit.addActionListener(this);
		tfTel.addActionListener(this);
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

		f.add(ta);

		f.setSize(650,480);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//버튼핸들러
	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();

		if(evt == bInsert){
			saveData();
			System.out.println("bInsert 발생");
		}else if(evt == bShow){
			readData();
			System.out.println("bShow 발생");
		}else if(evt == tfTel){//전화번호 텍필에서 엔터 이벤트 발생시
			search();
		}else if(evt == bDelete){
			System.out.println("bDelete 발생");
			delete();
		}
	}

	/*
	 * 입력받은 데이터를 저장 
	 */
	void saveData(){
		File f = new File("info.xml");
		System.out.println("여기");
		//<Info> </Info> append

		//RAF 사용해서 저장하기(한사람만)
		try {

			RandomAccessFile raf = new RandomAccessFile(f,"rw"); //r:read, rw:쓰기모드
			long pos = raf.length()-7;//파일에 저장된 문자수 반환(바이트수).
			//여기서 -7은 </Info> 뺀거. </Info>을 지우고 여기서 부터 그 다음 사람의 <person> 작성이 시작되어야 하므로

			if (pos <= 0 ){
				String tag = "<Info>" + makeTags() + "</Info>";//makeTags()양쪽에 <Info> 붙여 스트링tag에 저장
				raf.write(tag.getBytes());

				/*				위에서 -7에 의해 </Info>를 지우고 그 다음 사람의 <person>을 붙여 나가며

				원래 파일처리는 첨부터 다시 쓰는거고
				랜덤엑세스파일만 seek를 이용해 파일 안의 문자 찾기가 가능*/

			}else{
				raf.seek(pos);//위에서 -7에 의해 </Info>를 지우고 그 다음 사람의 <person>을 붙여 나가며
				String tag = makeTags() + "</Info>";
				raf.write(tag.getBytes());
			}

			raf.close();

		} catch (Exception e) {
			System.out.println("파일쓰기 실패:" + e.getMessage());
		}		
		clear();
	}
	/*
	 * DOM 형식 태그 생성 
	 */

	void readData(){
		File file = new File("info.xml");

		try {

			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();// javax.xml.parsers.docu.....
			DocumentBuilder docBuild = docFac.newDocumentBuilder(); //팩토리가 빌더를 만들어줌. //document를 만들기 위해 빌더가 필요. 이놈도 지가 객체 생성이 안되서 빌더팩터리가 필요
			Document doc = docBuild.parse(file);
			//doc는 인터페이스인데 xml과 html을 표현함.
			//document는 document트리의 루트이다.

			doc.getDocumentElement().normalize();
			//getDocumentElement()은 루트를 반환한다.
			//normalize();는 노드로 만들어줌. 텍스트를 노드로 만들어주는 역할. 현재 엘리먼트 민테 있는 놈들을 죄다 노드로 만들어줌.
			//노드들을 트리구조로 만들어준다는 거임.

			NodeList personlist = doc.getElementsByTagName("person");//노드여야 해. 이 명령을 기술하기 위해선 반드시 .normalize();된 상태여야만 해
			//person 목록의 생성
			ta.setText(null);//불러오기 전 초기화
			for(int i = 0; i < personlist.getLength(); i++){

				Node tempNode = personlist.item(i);							//person목록의 내용물로 tempNode껴 놓기, //i번째 자식. org dom import 하기.
				if(tempNode.getNodeType() != Node.ELEMENT_NODE) continue;	//요소만 감지하기 위해. person의 자식으로 tel이 있는 경우, person과 tel 사이에 공백이나 개행이 있으면 이걸 요소가 아닌 걸로 처리해야 함.
				//getNodeType()이 노드의 형태를 반환하는데, 그게 ELEMENT_NODE가 아닐 경우 continue

				NodeList childList = tempNode.getChildNodes();//getChildNodes();
				/*				ta.setText("person의 자식수: " + childList.getLength()+"\t");*/

				for (int j = 0; j < childList.getLength(); j++){
					Node temp = childList.item(j);
					ta.append(temp.getTextContent()+"\t");
				}
				ta.append("\n");
			}
			//인포의 자식을 찾아보자
			/*			ta.setText("person 수 : " + personlist.getLength());//getLength() 갯수를 나타내줌.
			 */			
			//System.out.println("루트 : " + doc.getDocumentElement().getNodeName());

		} catch (ParserConfigurationException e) {
			e.printStackTrace();

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		//팩토리 구조 참조하기
		//태그 구조를 메모리에 띄우는 게 파싱
	}

	void search(){
		//사용자가 입력한 전화번호를 얻어오기
		/*String telnum = tfTel.getText();*/

		File file = new File("info.xml");
		try {

			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();// javax.xml.parsers.docu.....
			DocumentBuilder docBuild = docFac.newDocumentBuilder(); //팩토리가 빌더를 만들어줌. //document를 만들기 위해 빌더가 필요. 이놈도 지가 객체 생성이 안되서 빌더팩터리가 필요
			Document doc = docBuild.parse(file);

			doc.getDocumentElement().normalize();

			NodeList personlist = doc.getElementsByTagName("person");

			ta.setText(null);//불러오기 전 초기화
			
			for(int i = 0; i < personlist.getLength(); i++){
				
				//tel노드 찾아 그 노드의 텍스트 내용이 입력한 전화번호 인지 확인 후
				//각각의 내용을 화면에 출력
				//temp노드에 들어오는게 텔노드 이름노드 주소 노드들임..getClass().getName().equals(anObject)

				Node personNode = personlist.item(i);				//System.out.println(i+ ". " + personlist.item(i).getNodeName());
				NodeList childList = personNode.getChildNodes();

				for(int j = 0; j < childList.getLength(); j++){
					
					Node childNode = childList.item(j);					//System.out.println(i+"."+j+ ". "  + childList.item(j).getNodeName()); /*if(childNode.getNodeName() == "tel"){ 이렇게 쓰면 안됨. 스트링은 .equals로 내용을 비교해야지*/
					System.out.println(i+":"+j+ ". " + childNode.getTextContent());
					if((childNode.getNodeName()).equals("tel")){	//childNode들에 담긴 태그명들 중 tel이란 이름의 태그 찾는 거.

						if(tfTel.getText().equals(childNode.getTextContent())){ //그 중에서 tfTel에 담긴 놈과 childNode.getTextContent()에 담긴 같은 놈걸 찾아내라
							
							System.out.println("getTextContent(): "+childNode.getTextContent());//010-3158-4556
							System.out.println("getChildNodes(): "+childNode.getChildNodes());
							System.out.println("getLocalName(): "+childNode.getLocalName());
							System.out.println("getNextSibling(): "+childNode.getNextSibling());//[name: null]
							System.out.println("getPreviousSibling(): "+childNode.getPreviousSibling());
							System.out.println("getParentNode(): "+childNode.getParentNode());
							JOptionPane.showMessageDialog(null, "해당 번호" + tfTel.getText() + "존재함");
							
							/*Node childNode = childList.item(j)*/
							while(childNode.getNextSibling() != null){//af
								ta.append(childNode.getTextContent() + "/");
								childNode = childNode.getNextSibling();
							}
						/*ta.add(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
									+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");*/
						}
					}
				}				
			}
		}catch (ParserConfigurationException e) {
			e.printStackTrace();

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	void delete(){
		File tmpfile = new File("info.xml");

		try {
			DocumentBuilderFactory docFac1 = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBld1 = docFac1.newDocumentBuilder();
			Document doc1 = docBld1.parse(tmpfile);
			
			doc1.getDocumentElement().normalize();
			
			NodeList personlist = doc1.getElementsByTagName("person");

			ta.setText(null);//불러오기 전 초기화
			
			for(int i = 0; i < personlist.getLength(); i++){//getClass().getName().equals(anObject)

				NodeList childList = personlist.item(i).getChildNodes();
				
				for(int j = 0; j < childList.getLength(); j++){										
					if((childList.item(j).getNodeName()).equals("tel")){
						//전화번호로 지우기
						if(tfTel.getText().equals(childList.item(j).getTextContent())){ //그 중에서 tfTel에 담긴 놈과 childNode.getTextContent()에 담긴 같은 놈걸 찾아내라
							
							childList.item(j).getParentNode().getParentNode().removeChild(personlist.item(i));
							System.out.println("--------------삭제 발동 -----------------");
							System.out.println(childList.item(j).getTextContent());
							System.out.println(childList.item(j));					//[tel: null] null은 상관 안해도 됨. tel이 자기 자신임을 알려주고 있음
							System.out.println(childList.item(j).getParentNode());	//[person: null]. tel의 부모는 person임을 나타냄.
							System.out.println(((childList.item(j)).getParentNode()).getParentNode());
							//System.out.println(childList.item(j).getParentNode().getParentNode().removeChild(personlist.item(i)));
							//childList.item(j) = tel의 getParentNode()은 person, person의 부모는 
							
							try {
								
								Transformer trf = TransformerFactory.newInstance().newTransformer(); //변환클래스 생성. doc을 xml로 바꾸는 놈.
								Result output = new StreamResult(new File("info.xml"));
								Source input = new DOMSource(doc1);
								trf.transform(input, output); //doc1을 info.xml로
								System.out.println("출력완료");
		
							} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
								e.printStackTrace();
							} catch (TransformerException e) {
								e.printStackTrace();
							}
						}
					}
				}				
			}
			
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	String makeTags(){

		sb.append("<person>");
		sb.append("<tel>" + tfTel.getText() + "</tel>");
		sb.append("<name>" + tfName.getText() + "</name>");
		sb.append("<id>" + tfJumin.getText() + "</id>");
		sb.append("<gender>" + tfGender.getText() + "</gender>");
		sb.append("<home>" + tfAddr.getText() + "</home>");
		sb.append("<age>" + tfAge.getText() + "</age>");
		sb.append("</person>");
		return sb.toString();

	}
	/*
	 * 텍스트 필드 클이어 
	 */
	void clear(){
		tfName.setText(null);
		tfTel.setText(null);
		tfJumin.setText(null);
		tfGender.setText(null);
		tfAge.setText(null);
		tfAddr.setText(null);
	}
}
public class InfoTest{
	public static void main(String[]args){
		InfoUI ui = new InfoUI();
		ui.init(); 
		ui.addLayout();		
	}
}


/*package Mo01_InfoT;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class InfoUI implements ActionListener {
	//멤버변수
	JFrame f;
	JButton bInsert,bModify,bDelete,bShow,bExit;
	JLabel name, tel, jumin,gender,age,addr;
	JTextField tfName, tfTel, tfJumin, tfGender, tfAge, tfAddr;	
	JTextArea ta;
	File folder;
	ArrayList<Record> list; //arraylist 는 이미 serializable 이므로 바로 통째로 보내기가능

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
		ta = new JTextArea();

		list = new ArrayList<Record>();
	}

	void saveData(){
		File f = new File("info.xml");
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "rw");//r: 읽기모드, rw: 쓰기모드

			String tag = "<info>" + makeTags() + "</info>";
			raf.write(tag.getBytes());//스트링을 바이트로 변환 후 전송 
			raf.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			System.out.println("파일 없음");
		} catch (IOException e) {

			e.printStackTrace();
			System.out.println("IOException발생");
		} 
		clear();

	}

	//프로그램 시작할때 파일 읽어오기 
	void init(){

		//핸들러 넣기
		bInsert.addActionListener(this);
		bModify.addActionListener(this);
		bDelete.addActionListener(this);
		bShow.addActionListener(this);
		bExit.addActionListener(this);	

		//파일 읽어오기(지정된 파일이 없다면 리스트 객체 새로 생성!)
		try {
			//파일을 통째로 읽어옵니다
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("전화번호부.txt"));
			//객체를 받고 type casting 해줍니다
			ArrayList<Record> tmp = (ArrayList<Record>)in.readObject();
			list = tmp; 
			for(int i = 0; i < list.size(); i++){
				System.out.println(list.get(i).getName()+list.get(i).getTel());
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

		f.add(ta);

		f.setSize(650,480);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	String makeTags(){

		StringBuffer tagBuf = new StringBuffer();

		tagBuf.append("<person>");
		tagBuf.append("<tel>" + tfTel.getText() + "</tel>");
		tagBuf.append("<name>" + tfName.getText() + "</name>");
		tagBuf.append("<id>" + tfJumin.getText() + "</id>");
		tagBuf.append("<gender>" + tfGender.getText() + "</gender>");
		tagBuf.append("<home>" + tfAddr.getText() + "</home>");
		tagBuf.append("<age>" + tfAge.getText() + "</age>");
		tagBuf.append("</person>");

		return tagBuf.toString();
	}

	//텍필드 초기화
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
			//�넻吏몃줈 ���옣!!!!!!!!!!!!!!!!!!!!!!!!
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("�쟾�솕踰덊샇遺�.txt"));
			out.writeObject(list);
			out.close();
		} catch (IOException e1) {
			System.out.println("�뙆�씪 write �뿉�윭媛� 諛쒖깮: " + e1.getMessage());
		}	
	}

	//핸들러
	public void actionPerformed(ActionEvent e) {

		JButton evt = (JButton)e.getSource();
		boolean flag = false;		

		if(evt == bInsert){ //�엯�젰
			//���옣留뚰븯怨� ���옣�맂 �냸�쓣 �븞遺덈윭�삤�땲源� 洹몃젃吏�
			String tmp = tfTel.getText(); //�쟾�솕踰덊샇媛� �궎媛�
			System.out.println(tmp);

			//�룞�씪�븳 �뙆�씪紐낆씠 �엳�뒗吏� �솗�씤 (以묐났泥댄겕)
			for(int i = 0; i < list.size(); i++){

				System.out.println(list.get(i).getTel());
				System.out.println("**");

				if(tmp.equals(list.get(i).getTel()) ){//以묐났�씠硫� �궗�슜�옄�뿉寃� �븣由�
					System.out.println("in if : " +  list.get(i).getTel());
					JOptionPane.showMessageDialog(null, "�씠誘� �벑濡앸맂 �궗�슜�옄�엯�땲�떎.");
					flag = true; break; 
				}
			}

			if(!flag){
				//�뾾�떎硫� ���옣 
				System.out.println("�벑濡�!");				
				list.add(new Record(tfName.getText(), tfTel.getText(), tfJumin.getText(),
						tfGender.getText(), tfAge.getText(), tfAddr.getText()));
				flag = false;

			}
			//�뙆�씪�뿉 ���옣
			write(list);
			//clear
			clear();
			flag = false;

		}else if(evt == bExit){
			System.exit(0);
		}else if(evt == bDelete){
			//�쁽�옱 由ъ뒪�듃�뿉�꽌 吏��슦怨� 
			String tmp = tfTel.getText();

			//list �뿉�꽌 李얘린 
			for(int i = 0; i < list.size(); i++){
				if(tmp.equals(list.get(i).getTel())){
					//�궘�젣
					list.remove(i);
					break;
				}
			}			
			//�뙆�씪濡� ��
			write(list);
			//clear
			clear();

		}else if(evt == bShow){	

			//硫붾え由ъ긽�쓽 list �� �뙆�씪 '�쟾�솕踰덊샇遺�'�뒗 �궡�슜�씠 媛숈븘�빞 �븳�떎
			//洹몃윭誘�濡� bShow �븞�뿉�꽌�뒗 �뙆�씪�쓣 �씫�쓣 �븘�슂�뾾�쓬
			//踰꾪듉�쓣 �닃���쓣�븣 '�깉濡쒓퀬移�' 媛숈� �뒓�굦�씠 �굹湲곗쐞�빐 textArea 瑜� �겢由ъ뼱�빐以��떎.
			model.removeAllElements();
			try {
				//�뙆�씪�쓣 �넻吏몃줈 �씫�뼱�샃�땲�떎
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("�쟾�솕踰덊샇遺�.txt"));
				//媛앹껜瑜� 諛쏄퀬 type casting �빐以띾땲�떎
				ArrayList<Record> tmp = (ArrayList<Record>)in.readObject();

				list = tmp; 
				for(int i = 0; i < list.size(); i++){
					System.out.println(list.get(i).getName());

					model.addElement(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
							+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");	

					viewAr.add(list.get(i).getName() + "/" + list.get(i).getTel() + "/"+ list.get(i).getId() 
							+list.get(i).getSex() + "/" + list.get(i).getAge() + "/" + list.get(i).getAddr() + "\n");

					phoneList = new JList(viewAr.toArray());
				}
				phoneList = new JList(model);
				in.close();

			} catch (FileNotFoundException ee) {

				//�씠 寃쎌슦�뿉�뒗 list 媛앹껜 �깮�꽦!
				System.out.println("�뿬湲�");
				JOptionPane.showMessageDialog(null, "�뙆�씪 理쒖큹�깮�꽦! list 媛앹껜瑜� 留뚮벉(debug");
				list = new ArrayList<Record>();
			} catch (IOException ee) {
				System.out.println("萸붽� �옒紐삳맖..:( : " + ee.getMessage());
			} catch (ClassNotFoundException ee) {
				System.out.println("�겢�옒�뒪 紐살갼�쓬..:( :" + ee.getMessage());
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
 */