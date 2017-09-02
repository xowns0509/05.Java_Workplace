/*6. 1부터 100까지의 수 중에서 홀수의 합과 짝수의 합을 구하여 출력하세요.*/
public class Chalng06_ {

	public static void main(String[] args){

		int even = 0;
		int odd = 0;

		for( int i=1 ; i < 101 ; i++ )
		{
			if(i % 2 == 0){
				even = even + i;
			}else{
				odd = odd + i;
			}

			System.out.println("1 에서 " + i + " 까지의 홀합은"+ odd +", 짝합은" + even );
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
