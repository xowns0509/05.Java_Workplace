/* 1. 정수형 데이터 2개를 매개변수로 받아서 덧셈 연산을 수행하여 그 결과값을 반환하는 add 메소드를 선언하고,
 * 입력 받은 두 수를 인자로 보내서 그 메소드를 호출하여 결과값을 출력하는 프로그램을 작성하시오. */
import java.util.*;
public class Chalng1_ {

	public static void main(String[] args) {
	
		System.out.println("정수 2개를 입력하세요");
		Scanner in0 = new Scanner(System.in);
		Scanner in1 = new Scanner(System.in);

		int inpt_int0 = in0.nextInt();				/* nextInt는 스페이스 전까지 있는 걸 받음 */		
		int inpt_int1 = in1.nextInt();	
		
		add(inpt_int0, inpt_int1);
		
	}
	
	static void add(int inpt_int0, int inpt_int1){
		
		int result = inpt_int0 + inpt_int1;
		System.out.println("입력 한 두 수는 " + inpt_int0 +", "+ inpt_int1 + " 이며");
		System.out.println("두 수의 합은 " + result +" 입니다.");
	}
}