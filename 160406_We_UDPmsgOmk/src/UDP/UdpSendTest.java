package UDP;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UdpSendTest {

	public static void main(String[] args) {
		
		//콘솔
		String msg = "오늘은 연금복권";
		byte[] data = msg.getBytes();
		int port = 5000;
		
		try {
			
			InetAddress addr = InetAddress.getByName("localhost");
			//IP주소로 127.0.0.1 또는 "localhost"
			
			DatagramSocket socket = new DatagramSocket();
			// DatagramSocket 생성 ( 패킷을 보낼수 있도록 생성 )
			DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
			// DatagramPacket 생성. ( 보낼 데이타 byte[], 길이, 목적지주소, 목적지포트 지정)
			
			socket.send(packet);
			
			socket.close();
			System.out.println("나는 클라이언트.");
			System.out.println("서버로 보낸 데이터: " + data);
			
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException 에러: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("그외 에러: " + e.getMessage());
		}
		
	}
}
