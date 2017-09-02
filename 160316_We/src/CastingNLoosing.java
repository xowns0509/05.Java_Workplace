class CastingNLoosing{
	
	public static void main ( String [] args ) {
		int i = 360;
		byte b = ( byte ) i;	/*int가 byte보다 더 크므로 손실 발생.
								360을 2진수 전환시 0000 0001 0110 1000라
								표현을 위해 최소 16비트가 필요하지만 byte는 8비트이므로
								오른쪽 0110 1000만 남기고 나머지 모두 사라짐.
								0110 1000은 10진수 변환시 104.*/
		
		System.out.println( "i = " + i ); 	//360은 4바이트로 표현이 가능하므로 360정상출력.
		
		System.out.println( "b = " + b );
		
		System.out.println( "tested" );
				
	}
}