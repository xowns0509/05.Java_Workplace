//클래스 안에 클래스를 또 선언 할 수 있다. 멤버필드처럼 사용하기 위해.
class Outer{					// 사실 outer부터 메모리 올려야 inner가 올라가지. 
	class Inner{ 			// 결국 "엄청 소중한 거"를 출력시키려면 inner클래스로부터 인스턴스를 생성해야한다.
		public void secret(){		// 그래야 비로소 secret()으로 접근이 가능하니까
			System.out.println("엄청 소중한 이너클래스의 출력단.");
		}
	}
}

public class W04_InnerTest {

	public static void main(String[] args){
		
		Outer out = new Outer();			//Outer클래스에서 out인스턴스 생성
		Outer.Inner in = out.new Inner(); 	//out인스턴스를 내의 Inner클래스로부터 in인스턴스 생성.
		in.secret();						//in인스턴스의 메소드 실행
	}
}
