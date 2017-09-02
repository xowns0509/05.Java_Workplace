/*8. 1 + (1+2) + (1+2+3) + . . . +(1+2+3+4+5+6+7+8+9+10)의 결과를 구하는 프로그램을 작성하여 결과값을 출력하세요.*/
public class Chalng08_ {

	public static void main(String[] args){

		int a = 0;
		for( int i=1 ; i < 11 ; i++ )
		{
			for( int j=1 ; j <= i ; j++ )
			{
				a = a + j;
				System.out.println(a);
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
