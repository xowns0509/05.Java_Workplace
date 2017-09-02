package one.two.four;


public class OpEX_LeapYear {

	public static void main(String[] args) {
		//윤년(2월 29일이 있는 해)
		//4년 마다 한 번씩, 100년 마다는 아님
		//그러나 400년 마다는 윤년으로 계산.
		
		/* 4년: 윤
		 * 100년 : 평
		 * 200년 : 평
		 * 400년 : 윤
		 * 1000년 : 평
		 * 2000년 : 윤
		 * 2016년 : 윤 */
		
		int year = 2100;
		if( (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)){
			System.out.println("윤");
		}else{
			System.out.println("평");
		}
	}
}
