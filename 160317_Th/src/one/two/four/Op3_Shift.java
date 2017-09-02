package one.two.four;
//시프트, 비트이동연산자

public class Op3_Shift {
	public static void main(String[] args){
		
		int a = 7; 							//00000000 00000000 00000000 00000111 = 7
		int b = 8;
		int c = -8;							//11111111 11111111 1111111 11111000 = -8

		
		System.out.println( a << 2 ); 		//00000000 00000000 00000000 00011100 = 28
		System.out.println( a >> 1 );		//00000000 00000000 00000000 00001110 = 14인 줄 알았는데
											//이동만 하되 위의 a값에 영향은 안주네. 따라서 마지막 바이트가 00000011

		System.out.println( (a >>> 2 ) + "\n");		//두 칸 이동하므로 

		System.out.println( b << 2 );
		System.out.println( b >> 1 );
		System.out.println( (b >>> 2 ) + "\n");
		
		System.out.println( c << 2 );
		System.out.println( c >> 1 );
		System.out.println( (c >>> 2 ) + "\n");	// 꺽쇠3개는 부호비트 상관없이 무조건 0이 들어옴
												// 원래 음수라 대부분 비트가 1이였는데 앞이 0으로 들어오면서 양수가 怜?
												// 나머지 1들이 유효한 수가 되면서 엄청나게 커진 거. 
	}

}
