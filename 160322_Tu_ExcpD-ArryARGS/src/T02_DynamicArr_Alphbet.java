/* A
 * AB
 * ABC
 * :
 * ABCDEFGHIJKLMNOPQRSTUVWXYZ*/
public class T02_DynamicArr_Alphbet {

	public static void main(String[] args) {
		
		char [][] alphbetArr = new char[26][];

		for( int i = 0; i < alphbetArr.length ; i++ ){
			
			alphbetArr [i]  = new char[i+1]; // 개행 시 마다 배열의 갯수를 다르게 선언. 1번째 줄에는 1칸, 2번째 줄에는 2칸...
			for( char j = 'A'; j <= 'A'+ i ; j++ ){ //for( char j='A' ; j <= 'A' + i ; j++ )
				alphbetArr[i][(int)(j)-65] = j;				
				System.out.print(alphbetArr[i][(int)(j)-65]); 
			}
			System.out.println(); // \t는 탭
		}
		
/*		char [][] starArr = new char[5][5];
				
		for( int i = 0; i < starArr.length ; i++ ){					
			for( int j = 0; j < i+1 ; j++ ){
				starArr[i][j] = '*';				
				System.out.print(starArr[i][j]); 
			}
			System.out.println(); // \t는 탭
		}*/
	}
}