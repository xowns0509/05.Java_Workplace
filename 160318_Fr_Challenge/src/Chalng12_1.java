﻿/*12. 구구단을 출력하는 프로그램을 작성하되 세 단씩 화면에 출력하세요.
           (아래와 같이 출력되도록 하되 숫자의 크기가 달라 조금씩 틀어져 출력될 수 있습니다 )
           2 * 1 = 2                 3 * 1 = 3                    4*1= 4
           
           2* 2 = 4                 3 * 2 = 6                    4*2= 8
           2* 3 = 6                 3 * 3 = 9                    4*3= 12
               ::                               ::                            ::
 
           5* 1 = 5                 6 * 1 = 6                    7*1= 7
           5* 2 = 10                6 * 2 = 12                   7*2= 14
           5* 3 = 15                6 * 3 = 18                   7*3= 21
               ::                               ::                            ::
 
           8* 1 = 8                 9 * 1 = 9                   
           8* 2 = 16                9 * 2 = 18                  
           8* 3 = 24                9 * 3 = 27        */

public class Chalng12_1 {

	public static void main(String[] args){
		
		System.out.println("***** 구구단 출력 *****");
		
		for( int i = 2 ; 10 > i  ; i+=3 )
		{
			for( int j = 1 ; j < 10 ; j++){
				
				System.out.print( i +" * "+ j + " = " + (i*j) + "		");
				System.out.print( (i+1) +" * "+ j + " = " + ((i+1) * j) + "		");
				if( i == 8 ){
					System.out.println("		");
				}
				else{
					System.out.println( (i+2) +" * "+ j + " = " + ((i+2) * j) + "");
				}
				if(j == 9){
					System.out.println();
				}
				//i도 결국은 9까지
				//j는 9까지
				//시작이 2, 한 행이 i i+1 i+2
			}
		}
	}
}