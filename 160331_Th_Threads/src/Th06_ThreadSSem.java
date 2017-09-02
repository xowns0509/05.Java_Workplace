import java.awt.*;
import javax.swing.*;

public class Th06_ThreadSSem extends Frame{
	
	Dal a, b, c;
	
	public Th06_ThreadSSem()
	{
		setSize( 500, 400 );
		setVisible( true );

		a = new Dal(this, 0, 50);
		b = new Dal(this, 0, 100);
		c = new Dal(this, 0, 150);

		//(3)아래에 각 객체의 쓰레드 메소드(run) 호출한다
		a.start();
		b.start();
		c.start();
	}

	public void paint( Graphics g )
	{
		g.setColor(Color.red);
		g.drawString("__@", a.x, a.y );

		g.setColor(Color.blue);
		g.drawString("__@", b.x, b.y );

		g.setColor(Color.green);
		g.drawString("__@", c.x, c.y );
			
	}

	public static void main(String args[] )
	{
		Th06_ThreadSSem dal = new Th06_ThreadSSem();
	}
	
/*	public void run(){
		
		int speed = 0, x = 0;

		while(true){
			speed = (int)(Math.random()*5);
			x += speed;
			
			a.app.repaint();
			b.app.repaint();
			c.app.repaint();
			
			try {
				Thread.sleep(speed * 100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}*/
}

class Dal extends Thread
{
	int x, y;
	int speed;
	Frame app;
	
	public Dal( Frame _app, int _x, int _y )// 컴퓨터는 왼쪽 맨 상단이 0,0임. x좌표는 그렇다 치고 수학 좌표와는 다르게 y좌표가 밑으로 내려가면 y좌표값이 증가하ㅡㄴ 거임.
	{
		app = _app;
		x = _x;
		y = _y;	
	}
	
	public void run(){
		while(true){
			speed = (int)(Math.random()*5);
			x += speed;
			
			app.repaint();
			
			try {
				Thread.sleep(speed * 10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	}
}

/*# Thread 처리
(1) Thread 클래스나 Runnable 인터페이스 상속
(2) run() 오버라이딩

	- 임의의 수를 speed 값으로 지정		
	- x 값을 위의 임의의 수를 더하기
	- 화면을 다시 그린다 (*) app.repaint() 이용
	- 임의의 수만큼 잠시 쓰레드를 블럭한다
	# 위의 작업을 반복한다
*/

/*class Dal
{
	int x, y;
	int speed;
	Frame app;
	
	public Dal( Frame _app, int _x, int _y )// 컴퓨터는 왼쪽 맨 상단이 0,0임. x좌표는 그렇다 치고 수학 좌표와는 다르게 y좌표가 밑으로 내려가면 y좌표값이 증가하ㅡㄴ 거임.
	{
		app = _app;
		x = _x;
		y = _y;	
	}
}*/

