public class Ctrl4for_2 {

	public static void main(String[] args) {

/*		for( i = 0 ; i < 5 ; i++ ){//프로그램은 0부터시작한다. 그래서 i = 0, 1, 2, 3, 4 총 5번 수행.
			//일단 for 문 수행하고 그제서야 i++를 수행. }를 나가기 직전에 i++수행
			System.out.println("행복하세요");
		}*/
		
		int a = 0;
		for( int i = 1 ; i < 101 ; i++ ){
			a = i + a;
			System.out.println(a);
		}
		
		for( int i = 1 ; i < 101 ; i++ ){
			a = 2 * i + a;
			System.out.println(a);
		}
	}
}