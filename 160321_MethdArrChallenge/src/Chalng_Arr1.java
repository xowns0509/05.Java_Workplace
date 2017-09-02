//1.  정수형배열 arr에 들어있는 정수들의 합을 출력하는 프로그램을 작성하시오.

public class Chalng_Arr1{
	public static void main ( String [] args ) {
		
		int [ ] arr = { 66, 55, 44, 33, 22, 11 };
		int sum = 0; /*  배열의 각각의 값을 더하는 코드 */
		
		for(int i = 0 ; i < 6 ; i++ ){
			sum += arr[i];
		}

		System.out.println( "sum = " + sum );
	}
}