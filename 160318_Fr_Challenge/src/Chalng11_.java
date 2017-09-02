import java.util.Scanner;

/*11. 컴퓨터가 1부터 100사이의 임의의 숫자를 지정하면 사용자는 그 수를 예상하여 입력하여 컴퓨터가 지정한 숫자를 맞추는 게임을 작성하세요.
 * 맞출 때까지 게임이 진행이 되면 맞추면 “성공”이라는 메시지를 출력하고 게임이 종료됩니다.
 * [힌트] 1부터 100사이의 임의의 수를 지정할 때는 int su = (int)(Math.random() * 100); 코드를 이용하면 됩니다.
 * [힌트] 사용자가 입력한수를 받아 위해서는 Scanner의 nextInt() 함수를 이용하면 됩니다. */

public class Chalng11_ {

	public static void main(String[] args){
		
		int input_int0 = 0;
		int su = 0;

		for( ; ; )
		{
			su = (int)(Math.random() * 100);
			
			System.out.println("1부터 100까지 아무 숫자나 정수로 입력하세요.");		
			Scanner in0 = new Scanner(System.in);		/* nextInt는 스페이스 전까지 있는 걸 받음 */
					
			input_int0 = in0.nextInt();
			
			if(su == input_int0){
				break;
			}else{
				System.out.println("입력한 숫자는 " + input_int0 + " 이며");
				System.out.println("나의 숫자는 " + su + " 이므로 불일치합니다. 게임을 계속합니다.\n");
			}
		
		}
		System.out.println("입력한 숫자는 " + input_int0 + " 이며");
		System.out.println("나의 숫자는 " + su + " 이므로 일치합니다. 게임을 종료합니다.");
	}
}
