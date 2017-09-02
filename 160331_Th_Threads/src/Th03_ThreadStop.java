public class Th03_ThreadStop {
	
	public static void main(String [] args){
		//메인도 쓰레드임.
		
		System.out.println("현재쓰레드"+Thread.currentThread().getName());
		//지금 현재 구동되고 있는 쓰레드
		ThreadStop ts = new ThreadStop();
		ts.start();
		
		try{
			Thread.sleep(2000);//메인함수가 쓰레드에 의해서 구동중이므로 이 구문은 유효.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("쓰레드 종료");
		ts.stop();
		
		//ts.start();//한번 죽은 쓰레드는 다시 구동되지 않는다.
		//ts.flag = false;
		
	}
}

class ThreadStop extends Thread{
	//boolean flag = true;
	ThreadStop(){}
	
	public void run(){
		
		while(true){
			System.out.println("이 쓰레드의 이름. " + getName());
			
			try {
				//Thread.sleep(500);
				sleep(500);//이 클래스가 Thread를 상속받았으니 즉석에서 사용가능.
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ThreadDeath e){
				System.out.println("강제로 종료");//쓰레드를 무한 루프 안에다 넣지 말라고.
				//stop()은 ThreadDeath란 예외를 던져줌. 
				//메인 ts.stop();로부터 던져진 예외를 여기서 잡았다. 그걸 잡았으니 당연히 종료가 안되지.
				//결론은 쓰레드를 무한 루프 안에다 넣지 말라고. 그리고 stop쓰지 말고 flag쓰기 권장.
			}
			
		}
		
	}

}