package IO.bytestream;

/*
	======================================================
	InputStream을 구현한 FileInputStream을 이용한 예
	======================================================
	
	@ int read()
		` 한 바이트를 읽고 이를 0-255사이의 값을 리턴하지만4byte의 int 형으로 리턴
		` 리턴되는 값은 0-255 부호없는 바이트이지만 형변환 과정에서 -128 ~127의 부호 있는 바이트가 된다
		
		
		` 데이타를 읽어들이기 전까지 기다리므로 다른 부분을 실행할 수가 없다
			-> 쓰레드 적용 필요
			
		` 더이상 읽을 바이트가 없으면 -1 리턴
		
		
		
		[ 참고 ]
			int i =  b >= 0 ? b : 256 + b;
*/

import java.io.FileInputStream;//
import java.io.IOException;

public class InputTestFirst
{
	public static void main( String args[] ) 
	{
		try
		{
			//1. 입력스트림생성
			FileInputStream fis = new FileInputStream("a.txt");//a.txt 파일을 읽음 존재 하지 않은 파일을 읽으려 하면.
			
			int data = 0;//
			//2. 파일 열었으니 데이터 읽기.
			for(int i = 0; ; i++){//언제 끝날지 모르니까 일단 i++
				data = fis.read();//데이터 읽는 함수. data에는 값(출력하려는 실제 데이터)이 저장되어 있다.
				if( data == -1) break;//파일의 종료. 이걸 모르면 반복문에 조건 걸어주면 되고.
				if(i < 5) System.out.println(data);//데이터 읽어서 화면(콘솔)에 출력
				// i를 5로 잡으면 짤리잖아 5부터 9까지. 이게 원래 정수형이라 4바이트를 읽어야 정상적으로 값이 나오는건데
				//밑에서 int로 읽어야 할 걸 캐릭터(2바이트)로 읽으니까. 문제가 되는거
				//9 이후부터는 캐릭터로 저장된 거니까 제대로 읽어대지.
				else System.out.println((char)data);//10개가 넘어가면 케릭터 형으로 출력					
			}
			//3. 반드시 작업이 완료되었으면 스트림 닫기.
			
			fis.close();//반드시 클로즈

		}catch( Exception ex ){
			System.out.println("파일전송실패 :" + ex.toString() );
			
			/*예외처리의 순서
			리딩, 라이트는 아이오예외(IOexception)으로 잡기
			존재파지 않는 파일에 대한 예외발생은 파일낫파운드로 잡기
			이외 모든 예외는 익셉션으러 처리*/
		}
	}	
}

/*
	======================================
		결과 출력
	======================================

	` 숫자만 나오는데, 우선 열개만 읽어서 숫자 자체로 출력하고
	나머지는 읽어서 (char) 형변환 하면 문자로 출력될 것이
*/