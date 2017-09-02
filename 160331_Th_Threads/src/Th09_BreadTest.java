public class Th09_BreadTest {
	
	public static void main(String [] args){
 		
		//객체 생성
		Bread bbang = new Bread();
		
		Baker baker = new Baker(bbang);
		Customer customer = new Customer(bbang);
		
		customer.start();
		baker.start();
	}
}

//베이커는 빵을 만들어서 진열
//커스터머는 get을 써서 그 빵을 가져오는 놈.
//이 고객이 빵이 있건 없건 대기해서라도 빵을 사야해. 그렇다면 커스터머 대기하고 있어야지. 

class Bread{
	
	String bread;						//빵을 공유하니까	
	boolean isCheck = false;			//빵을 누가 호출 했는지 알기위해 boolean 줌
	
	public void setBread(String bread){
		
		this.bread = bread;
		isCheck = true;					//빵이 존재한다면 참. 없으면 거짓이지.
		
		synchronized (this){ 
			notifyAll();
		}
		
	}
	
	public String getBread(){
		if(isCheck == false){			//빵이 안들어온 상태
			try {
				synchronized (this){	//내가 지금 block 되어 있을 때 따른 놈이 접근하지 못하게.
					wait();				//러닝에서 block상태로 올라감. 
				}
			} catch (InterruptedException e) {
				
			}
		}
		return bread;
		
	}
}

//----------------------------------------------------
class Baker extends Thread{
	Bread bbang;
	
	Baker(Bread bbang){
		this.bbang = bbang;
	}
	
	public void run(){
		bbang.setBread("완성된 수상한 빵을 진열");
	}
}

//----------------------------------------------------
class Customer extends Thread{//손님이 언제 올지 모르니까
	Bread bbang;
	
	Customer(Bread bbang){
		this.bbang = bbang;
	}
	public void run(){
		System.out.println("빵을 사러 옴: " + bbang.getBread());
	}
}