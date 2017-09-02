import java.util.*;//scanner, calendar 사용하므로

public class Ctrl2SwCs_minbun {

	public static void main(String[] args) {

		Calendar now = Calendar.getInstance();		//Calendar는 클래스이며 java.util.calendar에 있다.
		int year = now.get(Calendar.YEAR);			//Calendar의 
		
		System.out.println("주민번호 입력. 하이픈까지 다 치세요~");
		Scanner in = new Scanner(System.in);
		String id1 = in.nextLine();
		
		char sung1 = id1.charAt(7);					/* 민번에서 성별을 구분 할 숫자 추출
													 * 민번 7번째 자리가 1,3,9면 M. 2,4,0이면 W. */

//도시------------------------------------------------------------------

		String str = id1.substring(8, 10);			/* 민번에서 지역을 구분 할 숫자 추출
		int dosi = (int)str; 						 * str이 문자열이기 때문에 함부로 int로 형변환이 안됨. 그래서 */
		int dosi = Integer.parseInt(str);			/* 이 클래스를 사용, 문자열을 기본형으로 형변환 될 수 있게 함.*/

		if( 0 <= dosi && dosi <= 12){
		System.out.println("서울태생 \n");
		}else if( 13 <= dosi && dosi <= 22 ){
			System.out.println("인천, 부산태생 \n");
		}else if( 23 <= dosi && dosi <= 38 ){
			System.out.println("경기태생 \n");
		}else if( 90 <= dosi && dosi <= 99 ){
			System.out.println("제주태생 \n");
		}else{
			System.out.println("지역확인 불가, 관리자에게 문의 \n");
		}		
		
//성별------------------------------------------------------------------
		System.out.println(sung1); //sung 확인용
		switch(sung1){
		case '1' : System.out.println("1900년도 출생, 남자"); break;
		case '2' : System.out.println("1900년도 출생, 여자"); break;
		case '3' : System.out.println("2000년도 출생, 남자"); break;
		case '4' : System.out.println("2000년도 출생, 여자"); break;
		case '9' : System.out.println("1800년도 출생, 남자"); break;
		case '0' : System.out.println("1800년도 출생, 여자"); break;
		default : System.out.println("년도, 성별확인 불가, 관리자에게 문의 \n"); break;
		}
		
//나이------------------------------------------------------------------		
				
		String ageS = id1.substring(0, 2); //한 글자 아닌 다수의 문자열을 뽑아냄
		int born = Integer.parseInt(ageS);
		int cent = 0;
		
		switch(sung1){
		case '1' : cent = 1900; break;
		case '2' : cent = 1900; break;
		case '3' : cent = 2000; break;
		case '4' : cent = 2000; break;
		case '9' : cent = 1800; break;
		case '0' : cent = 1800; break;
		default : System.out.println("관리자에게 문의 \n"); break;	
		}
		
		int realAge = cent + born;
		System.out.println("id1의 나이는 " + (2016 - realAge + 1));		
	}
}