package Baduk;
//====================================================
//	클라이언트 프로그램을 실행하는 클래스
//	( main() 포함 )
//====================================================

import javax.swing.*;
import java.awt.event.*;


public class BadukClientMain extends JFrame 
{
	BadukClient			badukClient;       

	public BadukClientMain(String server, String id){
		if( server == null || server.equals("")){
			badukClient = new BadukClient();
		}else{
			badukClient = new BadukClient(server, id);
		}
		getContentPane(). add(badukClient);
		//setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		
//--> 3
		// 종료시 서버에 나감을 알림
		addWindowListener( new WindowAdapter(){
			public void windowClosing( WindowEvent ev){
				badukClient.sendExit();
				System.exit(0);
			}
		});
	}
	public static void main(String[] args){
		BadukClientMain main = new BadukClientMain(args[0], args[1]);
		main.setSize(880,660);
		main.setVisible(true);
	}
}