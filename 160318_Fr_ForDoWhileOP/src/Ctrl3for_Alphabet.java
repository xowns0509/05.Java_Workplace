public class Ctrl3for_Alphabet {

	public static void main(String[] args) {

		/*for( int i = 0 ; i < 26 ; i++ ){
			System.out.print(i + ". ");		
			for( char ch = 'A' ; ch <= 'A' + i ; ch++ ){
				System.out.print(ch);
			}
			System.out.print("\n");
		}*/
		
		char char1 = 65;			//숫자를 넣으면 아스키로 자동 변환됨.
		char char2 = 'A';			//char엔 문자를 바로 넣지 못하고 '을 써서 넣는다.
		char char3 = 'B' - 1;
		System.out.println(char1);
		System.out.println(char2);
		System.out.println(char3);
		
		for( int i = 0 ; i < 26 ; i++ ){
			System.out.print(i + ". ");
			
			for( char ch = (char)('Z' - i) ; ch >= 'A' ; ch-- ){//z부터 a까지나오려면 어떻게해야?
//			for( char ch = 'Z' ; ch - i >= 'A' ; ch-- ){ z부터 a까지나오려면 어떻게해야?
				System.out.print(ch);
			}
			System.out.print("\n");
		}
	}
}