public class Th01_ThreadTest {
	
	public static void main(String [] args){
		MakeCar mC1 = new MakeCar("차틀 만들기");
		mC1.run();
		//3.run() 호출 안됨. start()를 호출해줌.
		//mC1.start();
		
		MakeCar mC2 = new MakeCar("엔진부착");
		mC2.run();
		//mC2.start();
	}
}

//1. Thread 상속받는 클래스
class MakeCar extends Thread{
	String work;
	
	MakeCar(String _work){//this 안쓰고 싶을 때. 언더바 쓰는 개발자도 있음
		work = _work;
	}
	
	//2. run() overriding
	public void run(){
		for(int i = 0 ; i < 5; i++){
			System.out.println(work + " 작업중");
			try {
				Thread.sleep(500);//한 줄 출력 후 0.5초 쉬고, 또 한 줄 출력 후 0.5초 쉬고 반복
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//0.5초. 예외발생하므로 trycatch
		}
	}
}