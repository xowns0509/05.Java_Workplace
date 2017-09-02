//숫자6개 
import java.lang.Math;
public class T09_Lotto_bubb_while{

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

		for( int i = 0; i < lotto.length ; i++ ){

			for( int j = 0; j < lotto[i].length ; j++){ //j까지는 돌려야해 j까지는 모두 정렬해야 할 대상이니까.

				for( int k = 0; k < lotto[i].length ; k++){	//여기서부터 돌리기 시작
					if( k!=5 ){
						while(lotto[i][k] > lotto[i][k+1]){	//버블정렬이 완료될 때까지 계속 while안에서 돌아감.

							int tempArr = lotto[i][k];
							lotto[i][k] = lotto[i][k+1];
							lotto[i][k+1] = tempArr;
						}
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
