/*4. 1부터 10까지 숫자 중에서 3의 배수를 출력하세요.*/
public class Chalng04_ {

	public static void main(String[] args){
		
		for( int i=1 ; i < 11 ; i++ )
		{
			if( i % 3 == 0){
				System.out.println(i + "는 3의 배수.");
			}else{
				System.out.println(i + "는 3의 배수가 아님.");
			}
		}
	}
}