public class Ctrl3while_2 {

	public static void main(String[] args) {

		boolean flag = false;

		int i = 0;
		while( i < 5 )//여기다 ;이거 안찍어도 정상으로 판단, 오류 안 잡음. 그리고 while을 돌리지 않지.
		{
			System.out.print(i + ". ");	
			System.out.println("불금");
			i++;
		}
		
		int j = 0;
		do
		{
			System.out.print(j + ". ");
			System.out.println("불금");
			j++;
		}while( flag );// 여기다 세미콜론은 찍어야 함. 아니면 에러
			
	}
}