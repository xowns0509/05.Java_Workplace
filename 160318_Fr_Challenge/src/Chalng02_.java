import java.util.Scanner;
//2. 나이를 입력 받아서 19세이상이면 ‘성인’,  10세 이상에서 19세 미만은‘청소년’, 10세 미만은 ‘어린이’라고 출력하세요.
public class Chalng02_ {

	public static void main(String[] args){
		
		System.out.println("나이를 입력하세요.");		
		Scanner in0 = new Scanner(System.in);
		/* String a = in0.nextLine();
		 * nextline이 반환하는 값이 스트링이라서 int를 String으로 바꾸라고 말하는 거.
		 * nextInt는 스페이스 전까지 있는 걸 받음 */
		int age = in0.nextInt();
		
		if( age < 10 ){
			System.out.println("입력한 나이는 " + age + " 이며 어린이입니다.");
		}
		else{
		String adultOrAdole = (age >= 19)?"성인입니다":"청소년입니다.";
		System.out.println("입력한 나이는 " + age + " 이며 " + adultOrAdole);
		}				
		
	}
}