package notD;

class Producer extends Thread{
	private BBangShop shop;
	private String bread = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public Producer( BBangShop s ){
		shop = s;	
	}

	public void run(){

		char c;

		for( int i=0; i<10 ; i++){
			c = bread.charAt( (int)(Math.random() * 25 ) );

			synchronized(shop){
				shop.add( c );
				System.out.println(c + "빵을 만들고 진열함");
			}
			//만들고 임의의 수만큼 대기 
			try{
				sleep( (int)(Math.random() * 5000 ));
			} catch(InterruptedException ex){}
		}
		System.out.println("제빵종료");
	}
}

class Consumer extends Thread{
	private BBangShop  shop;

	public Consumer( BBangShop s ){
		shop = s;	
	}

	public void run(){

		char c;

		for( int i=0; i<10 ; i++){
			synchronized(shop){
				c = shop.buy( );
				System.out.println(c + "빵을 산다 ");
			}
			try{
				sleep( (int)(Math.random() * 8000 ) );
			} catch(InterruptedException ex){}
		}
		System.out.println("손님 끝");
	}
}

//
public class BBangShop{
	private char breads[] = new char[6];
	private int  addnext = 0;
	private int  buynext = 0;

	private boolean isFull = false;
	private boolean isEmpty = true;

	public synchronized char buy(){
		char buyBread;
		while( isEmpty == true ){
			System.out.println("빵이 없습니다");
			try{
				wait();
			}catch(InterruptedException ex ){}
		}
		//버퍼풀
		//synchronized(this){
			buyBread = breads[ buynext ];

			buynext = ( buynext + 1 ) % 6;
	//	}
		//빵진열대에 빵이 없을때
		if( buynext == addnext ) isEmpty = true;
		else isFull = false;
		
		//synchronized(this){
			notify();
		//}*/	

		return buyBread;	
	}

	public synchronized void add( char newBread ){
		while( isFull == true ){
			System.out.println("빵이 가득차서 진열할수 없습니다 ");
			try{
				wait();
			}catch(InterruptedException ex ){}
		}

		//synchronized(this){
			//여기 
			breads[ addnext ] = newBread;
			addnext = ( addnext + 1 ) % 6;//5일까 6일까
		//}
		if( buynext == addnext ) isFull = true;
		else {
			//진열대가 비지 않음, 고객이 빵 살수있음 
			isEmpty = false;
			
			//synchronized(this){
				notify(); //TODO: synchronized?
			//}
		}				
	}

	public static void main( String [] args ){
		BBangShop shop 	= new BBangShop();
		Producer  p		= new Producer( shop );
		Consumer  c		= new Consumer( shop );

		p.start();
		c.start();	
	}	
}