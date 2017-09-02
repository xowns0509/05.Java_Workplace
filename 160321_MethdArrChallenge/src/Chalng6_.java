/* 정수형 데이터 2개를 매개변수로 받아서 사각형의 넓이를 계산한 결과값을 반환하는 square 메소드를 선언하고,
 * 입력 받은 두 정수 데이터를 인자로 넘겨 그 메소드를 호출하여 반환값을 출력하는 프로그램을 작성하세요.
 * 단, 음수 데이터는 입력할 수 없습니다.*/

import java.util.Scanner;
public class Chalng6_ {

	public static void main(String[] args) {

		int inpt_int0 = 0, inpt_int1 = 0;
		System.out.println("넓이를 구할 사각형의 가로, 세로를 차례로 입력하세요");

		for(;;){			
			Scanner in0 = new Scanner(System.in);
			System.out.print("가로: ");
			inpt_int0 = in0.nextInt();
			System.out.print("세로: ");
			inpt_int1 = in0.nextInt();
			
			if((inpt_int0 > 0) & (inpt_int1 > 0)){
				break;
			}else{
				System.out.println("음수나 0을 입력하셨군요. 제정신인가요? 다시입력하세요.");
			}
			
		}
		int recArea = rect(inpt_int0, inpt_int1);
		
		System.out.println("사각형넓이 공식은 가로 * 세로 입니다.");
		System.out.println("입력하신 사각형의 가로, 세로는 각각 "+ inpt_int0 + ", " + inpt_int1 + "이며");
		System.out.println("해당 사각형의 넓이는 "+ recArea + "입니다.");	
	}

	static int rect(int inpt_int0, int inpt_int1){
		int area = inpt_int0 * inpt_int1;
		return area;	
	}
}