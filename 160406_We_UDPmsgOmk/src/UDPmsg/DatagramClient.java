package UDPmsg;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DatagramClient extends JFrame implements ActionListener {

	public static final int PORT = 7777;

	JTextField      server;
	JTextArea		input;
	JButton         send, clear;

	public DatagramClient() {
		server	= new JTextField(20);
		input	= new JTextArea(50,10);
		send	= new JButton("SEND");
		clear	= new JButton("CLEAR");

		JPanel  p_north = new JPanel();
		p_north.add( new JLabel(" 서버이름 (아이피) : ") );
		p_north.add( server );

		JPanel  p_south = new JPanel();
		p_south.add(send);
		p_south.add(clear);

		JPanel p_center = new JPanel();
		p_center.add( new JScrollPane( input ) );

		getContentPane().add(p_center, "Center");
		getContentPane().add(p_north, "North");
		getContentPane().add(p_south, "South");

		setSize(400, 200);
		setVisible(true);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			
		send.addActionListener(this);
		clear.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object b = e.getSource();
		if(b == send) {
			sendMessage();
			input.setText("");
		} else {
			input.setText("");
		}
	}


	protected void sendMessage() {
        try {

			InetAddress address = InetAddress.getByName( server.getText() );

			String msg = input.getText();
			byte[] buf = msg.getBytes();
			
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), buf.length, address, 7777);
			
			socket.send(packet);
			System.out.println("나는 클라이언트. \n 당신이 보낸 메시지: " + new String(packet.getData()));
			socket.close();
			
			//socket.send(데이터그램 패킷)
			//-----------------------------------------------------
			// 1. DatagramSocket 생성 ( 패킷을 보낼수 있도록 생성 )
			// 2. DatagramPacket 생성 
			//		( 보낼 데이타 byte[], 길이, 목적지주소, 목적지포트 지정)
			// 3. 소켓에 패킷 전송
			// 4. 소켓 닫기
           
			
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
			e.printStackTrace();
		}

	}


    public static void main(String[] args) {
		new DatagramClient();
    }
}
