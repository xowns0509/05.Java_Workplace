/* 5. 실수형 데이터 1개를 매개변수로 받아서 원이 넓이를 계산한 결과값을 반환하는 circle 메소드을 선언하고,
 * 입력 받은 실수형 데이터를 인자로 넘겨 그 메소드를 호출하여 반환되는 결과를 출력하는 프로그램을 작성하세요.
 * 단, 음수 데이터는 입력할 수 없습니다.*/

import java.util.Scanner;
public class Chalng5_ {

	public static void main(String[] args) {

		double inpt_dub0 = 0;
		System.out.println("넓이를 구할 원의 반지름을 입력하세요");

		for(;;){			
			Scanner in0 = new Scanner(System.in);
			inpt_dub0 = in0.nextDouble();

			if(inpt_dub0 > 0){
				break;
			}else{
				System.out.println("음수나 0을 입력하셨군요. 제정신인가요? 다시입력하세요.");
			}
			
		}
		double circleArea = Circ(inpt_dub0);
		
		System.out.println("원의 넓이 공식은 반지름 * 반지름 * 3.14 입니다.");
		System.out.println("입력하신 원의 반지름은 "+ inpt_dub0 + "이며");
		System.out.println("해당 원의 넓이는 "+ circleArea + "입니다.");	
	}

	static double Circ(double inpt_dub0){
		double area = inpt_dub0 * inpt_dub0 * 3.14;
		return area;	
	}
}