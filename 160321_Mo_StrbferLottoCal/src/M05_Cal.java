import java.util.Calendar;
//숫자6개 
public class M05_Cal {

	public static void main(String[] args) {

		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);//year 상수는 대문자
		int m = c.get(Calendar.MONTH) + 1;//아시아는 +1 해야 함
		int d = c.get(Calendar.DATE);
		int w = c.get(Calendar.DAY_OF_WEEK);

//		String [] weeks = new String[7];	
		//초기화 : 메모리 확보 후 바로 값 지정
		String [] weeks = {"일","월","화","수","목","금","토"};

		System.out.println(y + "년 " + m + "월 " + d + "일 " + weeks[w-1] + "요일"); //2. 값 지정
	}
}