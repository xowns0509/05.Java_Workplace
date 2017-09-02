
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;

public class Tu02_ComboS {

	public static void main(String[] args) {

		comboUI mainUI = new comboUI();
		mainUI.UILayout();
		mainUI.eventProc();
	}
}

class comboUI extends JFrame{
	//1. 필드선언
	JComboBox cbY, cbM, cbD;
	JButton btnDate, btnInit;
	JLabel laDate, thistimeLabel;

	Calendar now = Calendar.getInstance();
	Calendar calctTime = Calendar.getInstance();
	
	int thisYear = now.get(Calendar.YEAR);
	int thisMonth = now.get(Calendar.MONTH)+1;
	int thisDate = now.get(Calendar.DATE);
	int thisDay = now.get(Calendar.DAY_OF_WEEK);
	
	String thistime = String.valueOf(thisYear) +"년 "+ String.valueOf(thisMonth) +"월 "+ String.valueOf(thisDate) + "일";

	int CalctWeek = 0;
	int selectedYear = 0, selectedMonth = 0, selectedDay = 0;

	//2. 생성자선언
	comboUI(){

		//콤보박스 생성
		cbY = new JComboBox();
		cbM = new JComboBox();
		cbD = new JComboBox();

		//버튼생성, 라벨생성
		btnDate = new JButton("▶");
		laDate = new JLabel();//"그날의 요일"
		thistimeLabel = new JLabel(thistime, JLabel.CENTER);
		//thistimeLabel = new JLabel(thistime, JLabel.CENTER);//"지금시간"
		//캘린더 객체를 뽑아서 거기서 필요한 놈을 골라옴

		for(int i = thisYear-6 ; i < thisYear+6 ; i++){
			cbY.addItem(i);//addItem<integer>(i)라면 데이터가 정수형만 들어감. 다른건 안됨.
		}
		for(int i = 1 ; i < 13 ; i++){
			cbM.addItem(i);
		}
		for(int i = 1 ; i < 32 ; i++){
			cbD.addItem(i);
		}
		
		cbY.setSelectedIndex(6);//오늘 날짜가 선택되도록 하는 
		cbM.setSelectedIndex(thisMonth-1);//표시용이니까. 0이면 인덱스0에 있는거 나옴.
		cbD.setSelectedIndex(thisDate-1);
		
		switch(thisDay){
		case 1 : laDate.setText("일요일"); break;
		case 2 : laDate.setText("월요일"); break;
		case 3 : laDate.setText("화요일"); break;
		case 4 : laDate.setText("수요일"); break;
		case 5 : laDate.setText("목요일"); break;
		case 6 : laDate.setText("금요일"); break;
		case 7 : laDate.setText("토요일"); break;
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//3. 메소드선언
	void UILayout(){

		setVisible(true);
		setSize(800, 70);
		setLayout(new GridLayout(1,6));
		add(thistimeLabel);
		add(cbY);
		add(cbM);
		add(cbD);
		add(btnDate);
		add(laDate);

/*		setLayout(new BorderLayout());
		
		JPanel p_North = new JPanel();
		p_North.setLayout(new GridLayout(2,6));
		p_North.add(cbY);
		p_North.add(cbM);
		p_North.add(cbD);
		p_North.add(btnDate);
		p_North.add(laDate);
		add(p_North, BorderLayout.NORTH);
		
//		JPanel p_South = new JPanel();
		add(thistimeLabel, BorderLayout.CENTER);*/
	}

	void eventProc(){
		
		Hdlr eHdlr = new Hdlr();
		cbY.addActionListener(eHdlr);
		cbM.addActionListener(eHdlr);
		btnDate.addActionListener(eHdlr);
		
	}

	class Hdlr implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();

			if(evt == cbY | evt == cbM){//
				setDay();
			}else if(evt == btnDate){
				setDate();
			}
			/*어떤놈은 버튼이고 어떤놈은 콤보박스잖아. 그래서
			Object evt = e.getSource();*/
		}

	}
	int [] lastDays = {31, 28, 31, 30, 31, 30,
						31, 31, 30, 31, 30, 31};

	void setDay(){
		// 선택한 년을 구해 그 해가 윤년인지 확인
		// 만일 윤년이라면 lastDays[1] = 29;
		// 그렇지 않다면 lastDays[1] = 28;
		
		/*String xrs = (String)cbY.getSelectedItem();
		 * 불가. 애초에 addItem(i)에다 int형으로 넣었는데 String으로 형변환이 어떻게 되.
		 */
		cbD.removeAllItems();
		selectedYear = (int)cbY.getSelectedItem();

		/* 콤박스객체명.getSelectedItem();은 원래 형태가 object로 던지는데
		 * 내가 아까 addItem(i)으로 int를 넣었으니
		 * 반환되는 놈은 int가 되야 한다.
		 * 애초부터 int형이였던 놈을 멍청하게 (String)cbY.getSelectedItem();으로
		 * string으로 변환하려니 쳐 안될 수 밖에.*/
		 
		int month = cbM.getSelectedIndex();
		
		if((selectedYear % 4 == 0) && (selectedYear % 100 != 0) || (selectedYear == 0)){
			lastDays[1] = 29;
		}else{
			lastDays[1] = 28;
		}
		
		for(int i = 1; i <= lastDays[month]; i++){
			cbD.addItem(i);
		}

	}
	
	void setDate(){
		
		selectedMonth = (int)cbM.getSelectedItem();
		selectedDay = (int)cbD.getSelectedItem();
		
		calctTime.set(selectedYear, selectedMonth-1, selectedDay);
		CalctWeek = calctTime.get(Calendar.DAY_OF_WEEK);
		
		switch(CalctWeek){
		case 1 : laDate.setText("일요일"); break;
		case 2 : laDate.setText("월요일"); break;
		case 3 : laDate.setText("화요일"); break;
		case 4 : laDate.setText("수요일"); break;
		case 5 : laDate.setText("목요일"); break;
		case 6 : laDate.setText("금요일"); break;
		case 7 : laDate.setText("토요일"); break;
		}
	}
	//해당 월의 날짜수

}
//월년일이 선택되면 요일이 나와야 함.
//오늘날짜로 초기화