//해당하는 서버의 정보 get
package others;

import java.net.InetAddress;
import java.util.Arrays;

public class InetAddressEx {
	public static void main(String[] args) {
		String host ="www.daum.net";
		try{
			InetAddress  addr = InetAddress.getByName(host);
			System.out.println("1. 호스트명: " 	+ addr.getHostName());
			System.out.println("2. 호스트주소 :" + addr.getHostAddress());
			System.out.println("3. IP 주소 : " 		+ addr.toString());
			
			InetAddress  []allAddr = InetAddress.getAllByName(host);
			System.out.println();
			for( InetAddress i : allAddr)
			{
				System.out.println( i );
			}
			
			InetAddress local = InetAddress.getLocalHost();
			System.out.println("4. 로컬로스트 명 : " + local.getHostName());
			System.out.println("5. 로컬로스트 주소 : " + local.getHostAddress());
		}catch( Exception ex ){
			System.out.println("0. 해당 호스트에 연결할 수 없습니다. " + ex.getMessage());
		}
	}
}

