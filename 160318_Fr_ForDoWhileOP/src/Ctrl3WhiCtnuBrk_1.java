public class Ctrl3WhiCtnuBrk_1 {

	public static void main(String[] args) {

		for( int i = 0 ; i < 5 ; i++ )
		{
			for( int j = 0 ; j < 4 ; j++ )
			{
				System.out.println("<" + i + "," + j + ">");
				if(j == 2){
					break;
				}
			}
			System.out.println("데이타");
		}

		System.out.println("\n아래는 continue \n");
		
		for( int i = 0 ; i < 5 ; i++ )
		{
			for( int j = 0 ; j < 4 ; j++ )
			{
				if(j == 2){
					continue;
				}
				System.out.println("<" + i + "," + j + ">");
			
			}
			System.out.println("데이타");
		}
		
		System.out.println("\n아래는 break라벨 \n");
		
		RUN:
		for( int i = 0 ; i < 5 ; i++ )
		{
			for( int j = 0 ; j < 4 ; j++ )
			{
				if(j == 2){
					continue RUN;
				}
				System.out.println("<" + i + "," + j + ">");
			
			}
			System.out.println("데이타");
		}
	}
}