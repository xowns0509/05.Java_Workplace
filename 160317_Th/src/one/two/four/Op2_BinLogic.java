package one.two.four;
//이진논리01, 일반논리TF

public class Op2_BinLogic {

	public static void main(String[] args) {
		
		
		int a = 15;							
		System.out.println( ~a );			// 이진논리 NOT 연산자 : ~
		System.out.println( 3 > 4 );		// 일반논리 NOT 연산자 : !
		
											/* System.out.println( !3 > 4 ); 숫자에 못붙임. !는 TrueFalse에만
											그래서 이걸 수정하려면 괄호를 처줌
											System.out.println( !(3 > 4) ); 이러면 False가 반전되어 True로 나오겠지.*/

	}
}
