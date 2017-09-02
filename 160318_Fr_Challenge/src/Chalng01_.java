import java.util.Scanner;
//1. 정수의 값을 입력 받은 후 그 수가 홀수인지 짝수인지 출력하세요.
public class Chalng01_ {

	public static void main(String[] args){
		
		System.out.println("정수 하나를 입력하세요.");		
		Scanner in0 = new Scanner(System.in);
		/* String a = in0.nextLine();
		 * nextline이 반환하는 값이 스트링이라서 int를 String으로 바꾸라고 말하는 거.
		 * nextInt는 스페이스 전까지 있는 걸 받음 */	
		int a = in0.nextInt();
		
		String defin = (a%2 == 0)?"짝수입니다.":"홀수입니다.";
		
		System.out.println("입력한 수는 " + a + " 이며" + defin);
	}
}