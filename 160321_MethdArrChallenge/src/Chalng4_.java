/* 4. 반복 출력할 횟수를 매개변수로 받아서 “자바는 맛있다”라는 문장을 반복하여 출력하는 loopMethod 메소드를 선언하고,
 * 입력 받아 수를 인자로 그 메소드를 호출하는 프로그램을 작성하세요.*/

import java.util.Scanner;
public class Chalng4_ {

	public static void main(String[] args) {
	
		System.out.println("반복 출력할 횟수를 자연수 1개로 입력하세요");
		Scanner in0 = new Scanner(System.in);

		int inpt_int0 = in0.nextInt();				/* nextInt는 스페이스 전까지 있는 걸 받음 */		
		
		loopMethod(inpt_int0);		
	}
	
	static void loopMethod(int inpt_int0){
		
		for( int i = 1 ; i <= inpt_int0 ; i++ ){
			System.out.print( i +". ");
			System.out.println("자바는 맛있다");
		
		}
	}
}