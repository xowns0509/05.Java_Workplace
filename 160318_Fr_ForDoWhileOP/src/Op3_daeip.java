/* int a = 10;
 * int b = 7;
 * 
 * a += b;
 * 출력(a += b);
 * 
 * a %= b;
 * 출력(a += b);
 * 
 * a &= b;
 * 출력(a &= b);
 * 
 * a ^= b;
 * 출력(a ^= b);
*/

public class Op3_daeip
{

	public static void main(String[] args) {
		
		int a = 10;
		int b = 7;

		System.out.println("a = 10, b = 7\n");
		
		System.out.println("a += b → a = a + b 의 결과. " + (a += b) );
		System.out.println("a %= b → a = a % b 의 결과. " + (a %= b) );
		System.out.println("a &= b → a = a & b 의 결과. " + (a &= b) );
		System.out.println("a ^= b → a = a ^ b 의 결과. " + (a ^= b) );		
		
		System.out.println("\n 아래는 무시" + (a -= b) );
		System.out.println("a -= b → a = a - b 의 결과. " + (a -= b) );
		System.out.println("a /= b → a = a / b 의 결과. " + (a /= b) );
		
	}

}
