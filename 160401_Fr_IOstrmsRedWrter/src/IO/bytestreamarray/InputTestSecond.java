package IO.bytestreamarray;

/*
	======================================================
	InputStream을 구현한 FileInputStream을 이용한 예
	======================================================
	
	@ int read( byte[] data )
		` 한번에 한 바이트만 읽는 것이 비효율적이기 여러 개의 바이트를 읽어 들임
		` 읽은 바이트 수를 리턴	
		
		` 더이상 읽을 바이트가 없으면 -1 리턴
		  ( -1은 배열에 저장되지 않는다 )
	
	
	
	@ int read( byte[] data, int offset, int length )
	
	[ 참고 ] 주의할 점
		` 네트워크에서 1024 바이트를 읽을려고 시도했지만 실제로 512바이트만 읽고
		나머지는 수신중인 경우, 나중에 모두 도착하겠지만 현재는 사용할수 없는 데이타가 된다
		
		int read = 0;
		int bytesToRead = 1024;
		byte [] data = new byte[ bytesToRead ];
		while ( read < bytesToRead )
		{
			read += in.read( data, read, bytesToRead - read );	
		}
	
	
		` 스트림은 닫히지 않았지만 읽을 데이타 없으면 -1이 아니라 0을 리턴한다
		  ( 이런 경우 프로그램은 실행되지 않고 블럭된다 )
		  
		  
	
	[ 참고 ] available()
	
		` 읽을 수 있는 바이트 수 리턴하여 읽을 수 있는 바이트가 준비되지 않았을 때
		읽기를 멈추고 싶을 경우에 사용한다
	
		` read( byte[] data, int offset, int length )에서 length가 0이면 
		스트림의 끝을 모르기에 -1이 아닌 0을 리턴하므로 이런 경우 사용한다
		
	
	
*/

import java.io.FileInputStream;
import java.io.IOException;

public class InputTestSecond
{
	public static void main( String args[] ) 
	{
		try
		{
			FileInputStream fis = new FileInputStream("b.txt");
			
			//바이트 배열변수 지정 바이트를 배열로 일단 받아야 해
			byte[] data = new byte[1024];//잘 안씀. 지금은 잘 알지만 보통의 상황에선 외부에서 넘어오는 게 몇개인지 모르니까. 일종의 버퍼
			
//이럴 줄 알았는데			data = fis.read();
			int rcount = fis.read(data);//일단 리턴은 갯 수를 반환.
			//data = fis.read(); 이 경우엔 read메소드 자체가 1바이트씩 읽기 때문에
			//읽은 데이터를 data에다 넣었지만.
			//버퍼인자(data는 배열)가 들어가면 값을 리턴하는게 아니라 받았던 놈의 양을 수치로 리턴. 
			
			//버퍼만큼 양을 읽어서 data안에다 쳐넣고 동시에
			//얼마만큼 읽었는가... 즉 받은 데이터의 양에 대한 수치를 int형으로 반환함.
			for(int i = 0; i < rcount ; i++){
				if(i < 10) System.out.println(data[i]);
				else System.out.println((char)data[i]);
			}
			
			//3. Stream 닫기.
			fis.close();

		}catch( Exception ex ){
			System.out.println("파일전송실패 :" + ex.toString() );
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