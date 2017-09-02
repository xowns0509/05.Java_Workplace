import java.util.Scanner;

/*두 정수값을 입력받아 작은 수를 출력하세요
 *첫번째 수?
 *두번째 수?
 *두 수중 작은 수는 3입니다. */
		
public class Op1_Triple {

	public static void main(String[] args){
			
		System.out.println("첫 번째 수 입력하세요 ");		
		Scanner in0 = new Scanner(System.in);
		//String a = in0.nextLine(); nextline이 반환하는 값이 스트링이라서 int를 String으로 바꾸라고 말하는 거.
		int a = in0.nextInt();
	
		System.out.println("두 번째 수 입력하세요 ");	
		// nextInt는 스페이스 전까지 있는 걸 받음		
		Scanner in1 = new Scanner(System.in);
		int b = in1.nextInt();
		
		int compare = (a < b) ?  a : b ;		
		
		System.out.println("\n 두 수중 작은 수는" + compare + "입니다.");	
				
	}

}
