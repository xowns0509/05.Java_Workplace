/*0. Z
1. ZY
2. ZYX
....
24. ZYXWVUTSRQPONMLKJIHGFEDCB
25. ZYXWVUTSRQPONMLKJIHGFEDCBA */

public class ZZYZYX {

	public static void main(String[] args){
		
		for( int i=0 ; i < 26 ; i++ )
		{
			System.out.print(i + ". ");
			
			for( char j='Z' ; j >= 'Z' - i ; j-- )
			{
				System.out.print(j);
			}
			System.out.print("\n");
		}
	}
}