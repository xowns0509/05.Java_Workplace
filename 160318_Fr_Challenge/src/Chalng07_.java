/*7. 두 개의 주사위를 던져 두 주사위의 눈의 수가 7이 되는 경우를 모두 출력하세요.*/
public class Chalng07_ {

	public static void main(String[] args){

		for( int i=1 ; i < 7 ; i++ ) //1번째 주사위
		{
			for( int j=1 ; j < 7 ; j++ ) //2번째 주사위
			{
				if( i + j == 7){
					System.out.print( " 첫 번째 주사위 " + i + ", 두 번째 주사위 "+ j + " 이므로");
					System.out.println( " 합이 "+ (i + j) + "이 되는 경우." );
				}
			}
		}
	}
}

/*		
 * 
 * 		int a = 0, b = 0;
 * for( int i=1 ; i < 51 ; i++ )
		{
			a = 2 * i;
			b = b + a;
			System.out.println("1 에서 " + (2 * i)+ " 까지의 홀합은"+ a +", 짝합은" + b );
		} */
