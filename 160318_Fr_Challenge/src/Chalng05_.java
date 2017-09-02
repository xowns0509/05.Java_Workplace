/*5. 1부터 100까지의합을 구하여 그 결과값을 출력하세요.*/
public class Chalng05_ {

	public static void main(String[] args){
		
		int a = 0;
		for( int i=1 ; i < 101 ; i++ )
		{
			a = a + i;
			System.out.println("1 에서 " + i + " 까지 합은 "+ a +".");
		}
	}
}