/*9. 출력결과가 아래와 같이 나오도록 for문을 이용하여 출력하세요.
A
AB
ABC
.... (생략)
ABCDEFGHIJKLMNOPQRSTUVWXYZ */

public class Chalng09_ {

	public static void main(String[] args){
		
		for( int i=0 ; i < 26 ; i++ )
		{
			System.out.print(i + ". ");
			
			for( char j='A' ; j <= 'A' + i ; j++ )
			{
				System.out.print(j);
			}
			System.out.print("\n");
		}
	}
}

//	for( int i = 0 ; i < 26 ; i++ ){
	//	System.out.print(i + ". ");
		
	//	for( char ch = (char)('Z' - i) ; ch >= 'A' ; ch-- ){//z부터 a까지나오려면 어떻게해야?
	//	for( char ch = 'Z' ; ch - i >= 'A' ; ch-- ){ z부터 a까지나오려면 어떻게해야?
	//		System.out.print(ch);
	//	}
	//	System.out.print("\n");
//	}
//}
//}

/*		
 * 
 * 		int a = 0, b = 0;
 * for( int i=1 ; i < 51 ; i++ )
		{
			a = 2 * i;
			b = b + a;
			System.out.println("1 에서 " + (2 * i)+ " 까지의 홀합은"+ a +", 짝합은" + b );
		} */
