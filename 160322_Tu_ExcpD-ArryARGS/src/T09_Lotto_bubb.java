//숫자6개 
import java.lang.Math;
public class T09_Lotto_bubb{

	public static void main(String[] args) {
		//1. 배열선언 + 객체생성
		int [][] lotto = new int[5][6];

		//2. 값 지정 + 중복 시 재추출
		for( int i = 0; i < lotto.length ; i++ ){
			LOOP:
				for( int j = 0; j < lotto[i].length ; ){
					int temp = (int)(Math.random()*45 + 1);

					for(int k = 0 ; k < j ; k++){
						if( temp == lotto[i][k]) continue LOOP;
					}
					lotto[i][j] = temp;				

					j++;//이게 위의 출력보다 위에 있으면 마지막에 lotto[i][6]을 의미하게 됨. 그럼 인덱스 에러
				}

		}
		//lotto.length = 5	[<5] 행 
		//lotto.length = 5	[<5] 행 
		//lotto[a].length - i - 1 = 54321[<5, 최대 4번]

		for(int a = 0 ; a < lotto.length ; a++){					
			for(int i = 0 ; i < lotto[a].length - 1 ; i++){	
				/* 열이 0, lotto[a].length-0-1= 5, 0<j<5. 여기서 가장 큰 수는 맨 뒤인 [4]로 가게된다.
				 * 열이 1, lotto[a].length-1-1= 4. 0<j<4
				 * 열이 2, lotto[a].length-2-1= 3. 0<j<3
				 * 열이 3, lotto[a].length-3-1= 2. 0<j<2
				 * 열이 4, lotto[a].length-4-1= 1. 0<j<1
				 */
				for(int j = 0 ; j < lotto[a].length - i - 1; j++){	
	    	/* 열이 0일 때 j가 0, 0=j<5  열이 1일 때 j가 0, 0=j<4	열이 2일 때 j가 0, 0=j<3....
					 * j가 1, 1=j<5			j가 1, 1=j<4			j가 1, 1=j<3....
					 * j가 2, 2=j<5			j가 2, 2=j<4			j가 2, 2=j<3....
					 * j가 3, 3=j<5			j가 3, 3=j<4			
					 * j가 4, 4=j<5							 */
					if(lotto[a][j] > lotto[a][j+1]){
						int tempArr = lotto[a][j];
						lotto[a][j] = lotto[a][j+1];
						lotto[a][j+1] = tempArr;
					}
				}
			}
		}

		for( int i = 0; i < lotto.length ; i++ ){
			for( int j = 0; j < lotto[i].length ; j++){
				System.out.print( lotto[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
//		int tempArr = 0;		
/*		for( int i = 0; i < lotto.length ; i++ ){
 *

			for( int j = 0; j < lotto[i].length ; j++){

				if( j!=5 ){
					while(lotto[i][j] > lotto[i][j+1]){

						tempArr = lotto[i][j];
						lotto[i][j] = lotto[i][j+1];
						lotto[i][j+1] = tempArr;
					}
				}
				System.out.print( lotto[i][j] + "\t");
			}
			System.out.println();
		}*/
