//숫자6개 
import java.lang.Math;
public class M03_LottoSngleLine {

	public static void main(String[] args) {
		//1. 배열선언 + 객체생성
		int [] lotto = new int[6];
		
		//2. 값 지정
		for( int i = 0; i < 6 ; i++ ){
			lotto[i] = (int)(Math.random()*45 + 1);
			/* Math.random()는 임의 0.0부터 1.0미만까지 소수를 컴퓨터가 뽑아줌.
			 * 거기다 원하는 범위의 수 만큼 곱하면 원하는 수만큼 랜덤으로 나오는 것.
			 * +1을 더하는 이유는 1에서 45까지의 숫자를 원할 시 0.0이 45와 곱하면 0이되기에
			 * 또 1.0미만이라 45와 1.0이 곱해야 45도 나올텐데 미만이라 했으니 1을 안 더하면 절대로 45가 나올일이 없다.
			 *  
			 * 예를 들어 24까지의 랜덤을 원할 때 컴퓨터가 임의로 뽑은 수 0.4와 24를 곱하면 9.6이 나오고
			 * 여기다 1을 더하여 10.6으로 나옴.
			 * 정수형을 원하기 때문에 int로 형변환하여 소숫점을 잘라버림. 			 
			 */
		}
		
		//3.값 출력
		for( int i = 0; i < 6 ; i++ ){
			System.out.print( lotto[i] + "\t");
		}
	}
}