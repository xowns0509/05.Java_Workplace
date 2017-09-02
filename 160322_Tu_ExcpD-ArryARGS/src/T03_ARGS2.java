class T03_ARGS2{

	public static void main ( String [] args ) {

		try{
			for(int i = 0 ; i < args.length ; i++){ //예외가 발생하게 됨. 이 for문 부분을 try에 넣음
				System.out.println(i + "번째 = " + args[i] );
			}
			//return; 작성시 정상종료는 출력 못하지만(return이후의 모든 문장은 실행 안하지만) 무조건 실행~은 출력한다.
		}catch(Exception ex){
			System.out.println("예외 발생: " + ex.getMessage());//간단히 한 줄로 보겠다.
//			ex.printStackTrace();//예외가 발생한 걸 다 추적하겠다.
		}finally{
			System.out.println("무조건 실행해야 하는 부분");
		}
		System.out.println("정상종료");
	}
}
