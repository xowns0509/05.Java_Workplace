public class Th07_ThreadSync {
	
	public static void main(String [] args){
		
		Count count = new Count();
		ThreadCount tc1 = new ThreadCount(count);
		tc1.start();
		
		ThreadCount tc2 = new ThreadCount(count);
		tc2.start();
		
		try {
			tc1.join();//2. 그래서 이 두 놈(tc1, 2)의 쓰레드가 끝날 때까지 난 기다리겠어.
			tc2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//이 쓰레드가 끝날 까지 난 기다리겠어
		
		System.out.println("i값의 결과는" + count.i);
		//1. 2억이 나올 줄 알았는데 쓰레드 두놈이 도는 동안 메인함수는 그냥 지 할 꺼 다 했다고 끝나버림.
		//물론 아까 그 놈둘은 1억 올라 갈 때 까지 돌고 있지.
 		
	}
}

class ThreadCount extends Thread{
	
	Count cnt;
	ThreadCount(Count _cnt){
		cnt = _cnt;
	}
	
	public void run(){
		for(int i = 0 ; i <100000000 ; i++){
			cnt.increment();
		}
	}
}

/*class Count{
	
	int i;
	
	synchronized void increment(){//i를 동시에 수정이 이뤄지지 않겠금 함.
		//쓰레드는 서로 각자 독립적이므로 누가 먼저 i를 갖고 올리고 붙여넣는가에 따라 i값은 
		i++;
	}
}*/

class Count{
	
	int i;
	
	void increment(){

		synchronized (this){ //인스턴스의 increment메소드를 실행하면서 i는 고정하고자 할 때
			i++;
		}
		
	}
}

/*메소드의 기술부분이 길다면 메소드를 통째로 sync할 경우 앞뒤로 기능이 있으면 더 느려짐.
그래서 sync를 원하는 부분에다만 블럭을 주고 싶을 때(위의 경우엔 i만 sync가 되어도 되지)
synchronized(this){//싱크로나이즈드 블럭
	i
}*/