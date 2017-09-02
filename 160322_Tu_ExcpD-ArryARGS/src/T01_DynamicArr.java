/* *
 * **
 * ***
 * ****
 * ***** */
public class T01_DynamicArr {

	public static void main(String[] args) {
		
		char [][] starArr = new char[5][];
		
		for( int i = 0; i < starArr.length ; i++ ){
			
			starArr [i]  = new char[i+1]; // 개행 시 마다 배열의 갯수를 다르게 선언. 1번째 줄에는 1칸, 2번째 줄에는 2칸...
			for( int j = 0; j < i+1 ; j++ ){
				starArr[i][j] = '*';				
				System.out.print(starArr[i][j]); 
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