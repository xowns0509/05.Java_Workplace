package thread;

import java.io.*;
import java.net.*;

public class Client{
	
	public final static int PORT = 3333;
	public final static String HOST = "192.168.0.106";//192.168.0.147 나는 0.106, 예지 138, 0.128
	
	static int a[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	static int b[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	
	public static void main( String args[] ) {
		Socket 			cl  = null;
		DataInputStream		br 	= null;
		DataOutputStream 	dos = null;
		
		int result[] = new int[10];
		
		/***********************************************
		* 소켓 객체, 입력스트림과 출력 스트림 객체 생성
		*/	
		try{

		//1. 소켓 객체 생성
			cl = new Socket(HOST, PORT);
			
		//2. 입력 스트림 생성
			br = new DataInputStream(cl.getInputStream());
						
		//3. 출력 스트림 생성
			dos = new DataOutputStream(cl.getOutputStream());

			
		} catch ( Exception ex ) {
			System.out.println("Error is " + ex );	
		}


		/***********************************************
		* 숫자 배열을 서버에 전송
		*/					
		try{
			for( int i=0; i<a.length ; i++ ){
				// 1. a 배열을 서버에 전송
				
				dos.writeInt( a[i] );
				System.out.print("태준 a배열: " + a[i]);
				
			}
			for( int i=0; i<b.length ; i++ ){
				// 2. b 배열을 서버에 전송

				dos.writeInt( b[i] );
				System.out.print("태준 b배열: " + b[i]);	
				
			}
    	} catch( Exception ex ) {
		    	System.out.println("error writing to server.." + ex );
		}
  
		/***********************************************
		* 서버로부터 결과를 읽어 옴
		*/
		try{
			for( int i=0  ; i<result.length; i++ ){
				// 1. 서버에서 읽어와서 result 배열에 저장.
				result[i] = br.readInt();
				System.out.print("내가 받은 result배열" + result[i]);
				
			}
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}

		/***********************************************
		* 결과를 화면에 출력
		*/		
		System.out.println("The sum of the two arrays : " );
		for( int i=0 ; i < result.length ; i++ )
				System.out.println( result[i] + " " );
		
		/***********************************************
		* 출력 스트림, 입력 스트림, 소켓 객체 닫기
		*/
		try{
		
		// 1. 출력 스트림 닫기
			br.close();
		// 2. 입력 스트림 닫기
			dos.close();
		// 3. 소켓 닫기
			cl.close();

		} catch( Exception ex ) {
			System.out.println("Error close.... " + ex );	
		}
	}		
}
//클라이언트가 접속하길 기다렸다가 접속하면 소켓생성해주는 놈