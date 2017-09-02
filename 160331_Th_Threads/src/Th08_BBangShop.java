class Producer extends Thread//제작자
{
	private Th08_BBangShop shop; //객체 생성
	private String bread = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//빵의 이름
	
	//생성자
	public Producer(Th08_BBangShop s){
		shop = s;
	}

	public void run(){//빵 만드는 곳?

		char c;

		for( int i=0; i < 10 ; i++)
		{
			c = bread.charAt((int)(Math.random() * 25 ));//빵의 이름을 랜덤으로 뽑.
			shop.add( c );
			System.out.println(c + "빵을 만들고 진열함");

			try{
				sleep((int)(Math.random() * 500 ));//이건 왜?//
			} catch(InterruptedException ex){}
		}	
	}
}


class Consumer extends Thread//손님
{
	private Th08_BBangShop shop;

	public Consumer( Th08_BBangShop s ){
		shop = s;	
	}

	public void run(){

		char c;

		for( int i=0; i<10 ; i++)
		{
			c = shop.buy();

			System.out.println(c + "빵을 산다 ");

			try{
				sleep((int)(Math.random() * 500 ));//
			} catch(InterruptedException ex){}
		}	
	}
}


public class Th08_BBangShop
{
	private char breads[] = new char[6];//진열된 빵들
	private int  addnext = 0;
	private int  buynext = 0;

	private boolean isFull = false;//안비었음 false
	private boolean isEmpty = true;//비었으면 true

	public synchronized char buy(){//사는 걸 sync했네

		while( isEmpty == true ){
			System.out.println("빵이 없습니다");
			try{
				wait();
			}catch(InterruptedException ex ){}
		}

		char buyBread = breads[ buynext ];//여기서 첫번? 배열값이 들어감

		buynext = ( buynext + 1 ) % 6;//6으로 나눠서 가득 차 있는지를 확인

		if( buynext == addnext ){//
			isEmpty = true;//비었네 true
		}

		isFull = false;
		notify();

		return buyBread;
		
	}

	public synchronized void add( char newBread ){//제작자의 생성 빵 진입
		
		while( isFull == true ){
			System.out.println("빵이 가득차서 진열할수 없습니다 ");
			try{
				wait();
			}catch(InterruptedException ex ){}
		}

		breads[ addnext ] = newBread;//빵캐릭터를 breads 배열에 저장

		addnext = ( addnext + 1 ) % 6;//6으로 나눠서 가득 차 있는지를 확인

		if( buynext == addnext ) isFull = true;

		isEmpty = false;
		notify();

	}

	public static void main( String [] args )
	{
		Th08_BBangShop shop = new Th08_BBangShop();
		Producer  p			= new Producer( shop );
		Consumer  c			= new Consumer( shop );

		p.start();
		c.start();	
	}	
}