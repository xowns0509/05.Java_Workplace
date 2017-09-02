package one.two.four;
/*
 * 년도를 추출하여 몇 살인지 계산.
이것이 1900년대인지 2000년대 인지를 판단
무엇으로? 뒤의 성별로.
 */

import java.util.*;//scanner, calendar 사용하므로

public class Minbun {

	public static void main(String[] args) {

		//String id1 = "880125-1066789";			//뒤의 7자리 중 맨 앞자리 성별, 그다음 2자리 출생지역, 3자리 신고한 동, 마지막자리 에러체크코드
		//String id1 = "620909-2334234";
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
		
		if( (sung1 == '1') | (sung1 == '3') | (sung1 == '9') ){			// sung1이 char로 선언되어 있으니
			System.out.println("성별은 남자 \n");						// 그 값을 숫자가 아닌 문자로 비교해야함. sung1 = '1'
		}else if( (sung1 == '2') | (sung1 == '4') | (sung1 == '0') ){ 	// 그걸 sung1 = 1(문자형의 1. 즉, 유니코드1번 문자)로 비교하고 있으니 값이... 나올리가.
			System.out.println("성별은 여자 \n");
		}else{
			System.out.println("성별확인 불가, 관리자에게 문의 \n");
		}
		
//나이------------------------------------------------------------------		
				
		String ageS = id1.substring(0, 2); //한 글자 아닌 다수의 문자열을 뽑아냄
		int born = Integer.parseInt(ageS);
		int cent = 0;
		
		if( (sung1 == '1') | (sung1 == '2') ){			// 1, 2는 1900년대 생
			cent = 1900;
			System.out.println("1900년도 태생 \n");
		}else if( (sung1 == '3') | (sung1 == '4') ){	// 3, 4는 2000년대 생
			cent = 2000;
			System.out.println("2000년도 태생 \n");
		}else if( (sung1 == '9') | (sung1 == '0') ){	// 9, 0은 1800년대 생
			cent = 1800;
			System.out.println("1800년도 태생 \n");
		}else{
			System.out.println("관리자에게 문의 \n");
		}
		
		int realAge = cent + born;
		System.out.println("id1의 나이는 " + (2016 - realAge + 1));		
	}
}
