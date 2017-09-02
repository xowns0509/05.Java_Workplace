public class Th01_ThreadTest2 {
	
	public static void main(String [] args){
		MakeCar2 mC1 = new MakeCar2("차틀 만들기");
		
		//mC1.start(); //이 start는 mC1 인스턴스가 쓰레드 직접 시에만 쓸 수 있다.
		//그러나 아래 MakeCar는 T. 따라서 
		//Thread t1 = new Thread();이렇게 Thread만 생성하면 아무상관이 없다. 그래서 
		Thread t1 = new Thread(mC1);//이렇게 안에 넣어줌
		t1.start();
	
/*[1]	MakeCar2 mC2 = new MakeCar2("엔진부착");
		Thread t2 = new Thread(mC2);
		t2.start();*/
		
/*[2]	Thread t2 = new Thread(new MakeCar2("엔진부착"));
		t2.start();*/
	
/*[3]*/	new Thread(new MakeCar2("엔진부착")).start();//new Thread(new MakeCar2("엔진부착")) 이게 t2니까.
		
	}
}

//1. Thread 상속받는 클래스
class MakeCar2 implements Runnable{
	
	String work;
	
	MakeCar2(String _work){//this 안쓰고 싶을 때. 언더바 쓰는 개발자도 있음
		work = _work;
	}
	
	//2. run() overriding 그럼 start()가 이걸 실행
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

/*class MakeCar{
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