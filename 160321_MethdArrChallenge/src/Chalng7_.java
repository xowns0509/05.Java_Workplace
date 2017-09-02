/* 7. 2부터 9사이의 숫자를 입력받아 gugudan 메소드로 넘겨주면
 * gugudan() 메소드에서는 넘겨 받은 숫자에 해당하는 구구단을 출력하세요.
 * [예] 구구단의 숫자를입력하세요 -> 8
 * 8 * 1 = 8
 * 8 * 2 = 16
 * ....(생략)...
 * 8 * 9 = 72
*/

import java.util.Scanner;
public class Chalng7_ {

	public static void main(String[] args) {

		int inpt_int0 = 0;
		System.out.println("구구단을 구할 숫자를 입력하세요.");

		for(;;){			
			Scanner in0 = new Scanner(System.in);
			System.out.print("2에서 9사이의 숫자 입력: ");
			inpt_int0 = in0.nextInt();
			
			if((inpt_int0 > 1) & (inpt_int0 < 10)){
				break;
			}else{
				System.out.println("2에서 9사이의 숫자가 아니군요. 다시입력하세요.");
			}
			
		}
		gugudan(inpt_int0);
	}

	static void gugudan(int inpt_int0){
		
		System.out.println("\n입력하신 숫자는 "+ inpt_int0 + "입니다. 구구단을 출력합니다.");
		for(int i = 1 ; i <= 9 ; i++){
			System.out.println(inpt_int0 + " * " + i + " = " + (inpt_int0*i));			
		}
	}
}