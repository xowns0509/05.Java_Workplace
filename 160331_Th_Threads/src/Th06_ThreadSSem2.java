import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Th06_ThreadSSem2 extends JFrame{
	
	private JPanel p1,p2;
	private JButton btn1;
	private JTextArea res;
	private JLabel lb;
	private boolean inputChk = false;
	
	public Th06_ThreadSSem2() {
		
		setTitle("단일 스레드 테스트!");
		
		p1 = new JPanel();
		p1.add(btn1 = new JButton("Click"));
		p1.add(lb = new JLabel("Count!"));//추가 
		add(p1,"North");
		
		p2 = new JPanel();
		res = new JTextArea(20,50);
		p2.add(res);
		add(p2);
		
		setBounds(200, 200, 600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {//클릭하면 카운터 시작
				res.append("이벤트 발생");
				
				new Thread(new Runnable()/*이름이 없어*/{
					public void run(){//반드시 오버라이딩. 왜? 구현했으니까
						for(int i = 10; i >= 0; i--){
							//# 아래 boolean 값이 true라면 쓰레드를 멈추려고 함.
							// 라벨에 "참 잘하셨어요, 아주." 글씨 출력 후
							// 그 변수값을 false로 바꿈
							// 이 실행하는 부분을 멈추고자 할 때. return
							
							if(inputChk == true){
								System.out.println("참 잘하셨어요, 아주.");
								inputChk = false;
								//Thread.stop(); Deprecated 메소드. 쓰지 말라고 함.
								return;
								
							}else{
								
								System.out.println(i);
								lb.setText(String.valueOf(i));
								
								try {
									Thread.sleep(1000);//한 줄 출력 후 0.5초 쉬고, 또 한 줄 출력 후 0.5초 쉬고 반복
								} catch (InterruptedException e) {
									e.printStackTrace();
								}//0.5초. 예외발생하므로 trycatch
								
							}
						}
					}
				}).start();
				//카운터 실행. 이벤트 발생시 카운터
				
				new Thread(new Runnable(){//
					public void run(){
						String input = JOptionPane.showInputDialog("10초 안에 값을 입력하세요");
						res.append("입력한 값은 "+ input+ "입니다.");
						//boolean 변수를 true로 지정. boolean로 반복문의 on/off기능.
						inputChk = true;
					}
				}).start();
			}
		});
	}
	
	public static void main(String[] args) {
		new Th06_ThreadSSem2();
	}
}

/* 구현실패
 * class Ten2Zero extends Thread{
	
	int i = 10;
	int puttext = 0;
	
	Ten2Zero(){}
	
	public void run(){
		
		for(; i >= 0; i--)
			puttext = i;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}*/

/*
XXXXX x = new XXXXX();
Thread th = new Thread(x);
th.start();

class XXXXX implements Runnable{
	
	public void run(){
		for(int i = 10; i > 0; i--){
			lb.setText(String.valueOf(i));
			try{
				Thread.sleep(1000);
			}catch(){
				
			}
		}
	}
}*/