//숫자6개 
import java.lang.Math;
public class T09_LottoUnOverlap {

	public static void main(String[] args) {
		//1. 배열선언 + 객체생성
		int [][] lotto = new int[5][6];
		
		//2. 값 지정 + 중복 시 재추출
		for( int i = 0; i < lotto.length ; i++ ){
			System.out.println(); // \t는 탭
			
			LOOP:
			for( int j = 0; j < lotto[i].length ; ){
				int temp = (int)(Math.random()*45 + 1);
				
				for(int k = 0 ; k < j; k++){
					if( temp == lotto[i][k]) continue LOOP;
				}
				
				System.out.print( lotto[i][j] + "\t");
				j++;//이게 위의 출력보다 위에 있으면 마지막에 lotto[i][6]을 의미하게 됨. 그럼 인덱스 에러
			}
		}
		
		for( int i = 0 ; i < lotto.length ; i ++){
			for( int j = 0 ; j < lotto[i].length ; j++){
				
				
			}
		}
		
	}
}