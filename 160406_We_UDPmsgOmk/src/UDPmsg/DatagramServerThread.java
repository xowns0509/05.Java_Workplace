package UDPmsg;

import java.awt.GridLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class DatagramServerThread extends Thread {

	public final static int PORT = 7777;

    private DatagramSocket socket = null;
	private DatagramPacket packet = null;
   
    DatagramServerThread() {
		//------------------------------------------------
		// 1. DatagramSocket 생성
		//		( 서버측이기에 포트번호 지정 )
        try {
        	
			socket = new DatagramSocket(PORT);

           
        } catch (java.io.IOException e) {
            System.err.println("Could not create datagram socket. " + e);
        }
    }

    public void run() {
		System.out.println(" 서버 구동 ");
        while (true) {
            try {
                byte[] buf = new byte[1024];

				//-----------------------------------------
				// 2.datagram 받을 DatagramPacket 생성 ( datagram 받을 byte[], 버퍼의 크기 지정 )	
                packet = new DatagramPacket(buf, buf.length);
				// 3.소켓에 datagram을 받을 패킷을 지정
                socket.receive(packet);
                
/*                System.out.println("나는 서버.");
    			System.out.println("클라이언트로 받은 데이터: " + new String(packet.getData()));*/
               
                InetAddress address = packet.getAddress();
                int port			= packet.getPort();
                
				int len				= packet.getLength();
				new MessageWindow( new String( buf, 0, len ), String.valueOf(address));
				
				//socket.close(); 이거 주석처리 안하면 IO에러남

				//-----------------------------------------
				// 참고로 확인
				/*InetAddress address = packet.getAddress();
                int port			= packet.getPort();*/
				System.out.println("접속 클라이언트 : "+ address + " [ " + port + " ] ");
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
                e.printStackTrace();
            }
        }
    }

    protected void finalize() {//옛날 꺼. 가비지콜렉터시 돌아가는 함수
        if (socket != null) {//소켓이 널이면 닫힘.
                socket.close();
                socket = null;
                System.out.println("Closing datagram socket.");
        }
    }

 	public static void main(String[] args) {
		new DatagramServerThread().start();
	}
}

class MessageWindow extends JFrame
{
	MessageWindow(String m, String address){//, String iAdd
		super("받은 쪽지");
		JLabel cli = new JLabel(address);
		JTextArea ta = new JTextArea(m);
		getContentPane().add( new JScrollPane( cli ), "North");
		getContentPane().add( new JScrollPane( ta ), "Center");
		setSize( 300, 200);
		setVisible( true );
	}
};