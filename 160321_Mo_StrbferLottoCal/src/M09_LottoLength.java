//숫자6개 
import java.lang.Math;
public class M09_LottoLength {

	public static void main(String[] args) {
		//1. 배열선언 + 객체생성
		int [][] lotto = new int[5][6];
		
		//2. 값 지정
		for( int i = 0; i < 5 ; i++ ){
			System.out.println(); // \t는 탭
			
			for( int j = 0; j < 6 ; j++ ){
				lotto[i][j] = (int)(Math.random()*45 + 1);
				System.out.print( lotto[i][j] + "\t");
			}
		}
	}
}