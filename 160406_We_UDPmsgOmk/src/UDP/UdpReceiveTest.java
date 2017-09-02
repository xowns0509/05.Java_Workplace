package UDP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReceiveTest {
	
	public static void main(String []args){
		
		byte[] data = new byte[8129];
		int port = 5000;
		
		try {
			DatagramSocket socket = new DatagramSocket(port);
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			socket.receive(packet);
			
			System.out.println("나는 서버.");
			System.out.println("클라이언트로 받은 데이터: " + new String(packet.getData()));
			//packet.getData()을 형변환 해야 함. new String(packet.getData())이렇게 해야 함.
			
			socket.close();
			
		} catch (SocketException e) {
			System.out.println("예외발생, 서버실패: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}
}
