//숫자6개 
import java.lang.Math;
public class T09_Lotto_selt2{

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
		//미정렬 출력 부분
		for( int i = 0; i < lotto.length ; i++ ){
			for( int j = 0; j < lotto[i].length ; j++){
				System.out.print( lotto[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("아래는 왼쪽부터 작은 순으로 선택정렬");		

		for( int i = 0; i < lotto.length ; i++ ){

			for( int j = 0; j < lotto[i].length ; j++){ //lotto[i].length = 6

				int temp1 = lotto[i][j]; //0번째 시작

				for( int k = j + 1; k < lotto[i].length ; k++){ //lotto[i].length = 6
					if( temp1 > lotto[i][k]){
						int temp2 = temp1;
						temp1 = lotto[i][k];
						lotto[i][k] = temp2;
					}
					lotto[i][j] = temp1;// 도마에 나왔던 놈은 다시 집어 넣어야지.
				}
			}
		}
		//정렬 출력부분
		for( int i = 0; i < lotto.length ; i++ ){
			for( int j = 0; j < lotto[i].length ; j++){
				System.out.print( lotto[i][j] + "\t");
			}
			System.out.println();
		}
	}
}