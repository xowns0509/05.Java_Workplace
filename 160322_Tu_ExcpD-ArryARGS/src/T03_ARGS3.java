class T03_ARGS3{

	public static void main ( String [] args ) {

		try{
			for(int i = 0 ; i <= args.length ; i++){ // 예외가 발생하는 문장.
				System.out.println(i + "번째 = " + args[i] );
			}

			int result = 3/0; /*이 경우, for문에서 먼저 예외발생했기 때문에 얘는 실행안되고 ArrayIndex캐치로 나감.
								int result가 for문 보다 위에 있다면 Arithmetic캐치로 나가겠지.*/
					
		/*}catch(Exception ex){  
		 *	System.out.println("산술, 배열 제외한 예외 발생 " + ex.getMessage());
		 *}
		 *	Exception은 ArrayIndex, Arithmethtic 뿐만 아니라 모든 예외를 포함.
		 *	구체적인 놈이 먼저 잡힐 수 있도록 이놈을 맨 밑으로 내려야 함.if else 조건문의 순서차이 생각하라.
		 *	구체적인 에러 검증이 맨 위로, 포괄적인 에러검증 catch문이 아래로.	*/
			
		}catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("배열 예외 발생 " + ex.getMessage());//간단히 한 줄로 보겠다.
		}catch(ArithmeticException ex){
			System.out.println("산술 예외 발생 " + ex.getMessage());
		}catch(Exception ex){//나머지 모든 예외처리. 그외의 기타 에러들
			System.out.println("산술, 배열 제외한 예외 발생 " + ex.getMessage());
			//그래서 모든 에러검증인 Exception은 맨 밑으로 와야 함.
		}finally{
			System.out.println("무조건 실행해야 하는 부분");
		}
		System.out.println("정상종료");
	}
}
