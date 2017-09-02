public class Th07_ThreadPriority {

	public static void main(String [] args){
		
		new ThreadPriority("차틀제작", Thread.MIN_PRIORITY).start();//우선순위 부여. 1 ~10까지도 부여 가능.
		new ThreadPriority("엔진부착", Thread.NORM_PRIORITY).start();//("도색작업", 10)
		new ThreadPriority("도색작업", Thread.MAX_PRIORITY).start();
		//우선순위를 높게 줘서 도색작업만 나오지않을까
		//운영체제의 문제 어느 한 프로세스가 점유하지 못하게끔 막아둬서
		//이렇게 우선순위를 준다 해도 먹히지 않음.
		
	}
}

class ThreadPriority extends Thread{
	//boolean flag = true;

	String work;

	public ThreadPriority(String _work, int prio){
		work = _work;
		setPriority(prio); //우선순위 지정. 10이 제일 높고 1이 제일 낮음.
	}

	public void run(){

		for(int i = 0  ; i < 5  ;i++){
			System.out.println(work + "작업중");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}