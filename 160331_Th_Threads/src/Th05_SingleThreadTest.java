public class Th05_SingleThreadTest {
	
	public static void main(String [] args){
		/*SingleThread sT1 = new SingleThread();
		Thread thr1 = new Thread(sT1);
		thr1.start();*/
		
		//new Thread(new SingleThread()).start();
	
		/*SingleThread는 이 프로그램에서 오직 한 번만 필요하다.
		 * 딱 한 번 필요한 걸 굳이 따로 클래스 파일 만들고 싶지 않을 때 한 번 더 축약 가능.
		 * 한 번 쓸 때 이름 안 쓰려고.*/
		//new Thread(new Runnable()).start();					//Runnable은 인터페이스라 단독으로 객체 못해
		//new Thread(new Runnable()이름이 없어{}).start();			//그러나 이름없는 클래스 생성이 가능.*/
		new Thread(new Runnable()/*이름이 없어*/{
			public void run(){//반드시 오버라이딩. 왜? 구현했으니까
				for(int i = 0 ; i < 5; i++){
					System.out.println(" 작업중");
					try {
						Thread.sleep(500);//한 줄 출력 후 0.5초 쉬고, 또 한 줄 출력 후 0.5초 쉬고 반복
					} catch (InterruptedException e) {
						e.printStackTrace();
					}//0.5초. 예외발생하므로 trycatch
				}
			}
		}).start();	
		
		
/*[1]	MakeCar2 mC2 = new MakeCar2("엔진부착");
		Thread t2 = new Thread(mC2);
		t2.start();*/
		
/*[2]	Thread t2 = new Thread(new MakeCar2("엔진부착"));
		t2.start();*/
	
/*[3]	new Thread(new MakeCar2("엔진부착")).start();//new Thread(new MakeCar2("엔진부착")) 이게 t2니까.*/
		
	}
}

//1. Thread 상속받는 클래스
/*class SingleThread implements Runnable{
	
	
}

class MakeCar{
	String work;
	
	MakeCar(String _work){//this 안쓰고 싶을 때. 언더바 쓰는 개발자도 있음
		work = _work;
	}
	
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
}*/