/*10. 출력결과가 아래와 같이 나오도록 for문을 이용하여 출력하세요.
ABCDEFGHIJKLMNOPQRSTUVWXYZ
BCDEFGHIJKLMNOPQRSTUVWXYZ
CDEFGHIJKLMNOPQRSTUVWXYZ
DEFGHIJKLMNOPQRSTUVWXYZ
.... (생략)
XYZ
YZ

Z */

public class Chalng10_ {

	public static void main(String[] args){
		
		for( int i=0 ; i <= 26 ; i++ )
		{
			System.out.print(i + ". ");
			
			for( char j=(char)('A'+i) ; j <= 'Z' ; j++ )
			{
				System.out.print(j);
			}
			System.out.print("\n");
		}
	}
}

/* 한 행의 ABC...아스키가 증가하고 있다. 그래서 끝나는건 Z. 끝나는 반응은 조건문에서 걸려야 한다.
 * 개행 시 B부터, 다음 개행 시 C부터. 계속 아스키가 올라간 값에서 시작. 시작값이 변하고 있다. 개행마다. 초기치가 루프마다 변화
 * 증가치는 A에서 부터 Z까지 하나씩 올라가고 있음. 증가치는 1.
 */


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
