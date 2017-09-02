/* 3. 정수형 데이터 1개를 매개변수로 받아서 해당되는 문자열(인사말)을 모니터로 출력하는 메소드 greeting를 선언하고,
 * 입력 받은 정수형 데이터를 인자로 보내서 그 메소드를 호출합니다.
 * 단, 그 정수 데이터에 따라 다음과 같은 인사말을 출력합니다. ( 1-안녕하세요,  2-굿모닝,   3-올라,   4-곤니치와)*/

import java.util.Scanner;
public class Chalng3_ {

	public static void main(String[] args) {
	
		System.out.println("자연수 1개를 입력하세요");
		Scanner in0 = new Scanner(System.in);

		int inpt_int0 = in0.nextInt();				/* nextInt는 스페이스 전까지 있는 걸 받음 */		
		
		howdyLang(inpt_int0);
		
	}
	
	static void howdyLang(int inpt_int0){
		
		switch(inpt_int0){
		case 1 : System.out.println("안녕하세요"); break;
		case 2 : System.out.println("굿모닝"); break;
		case 3 : System.out.println("올라"); break;
		case 4 : System.out.println("곤니치와"); break;
		default : System.out.println("해당되는 인사가 없습니다."); break;
		
		}
	}
}