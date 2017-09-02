class Appa{
	void test(){
		System.out.println("아빠");
	}
}

class Umma{
	void test(){
		System.out.println("엄마");
	}

}

class Ddal extends Umma{
	void test(){
		System.out.println("딸");
	}

}

public class Fr01_InstanceOf  {
	//상속은 하나 밖에 안됨. 무조건 단일 상속, 부모가 2명이상 올 수 없다.
	public static void main(String[] args) {
		
		Umma u = new Umma();
		Ddal d = new Ddal();
//		Umma up = d;
//		Ddal dp = u;
		
		Umma up = (Umma)d;	// 자식을 부모로 up하는게 up케스팅 
//		Ddal dp = (Ddal)u;	//. 그 반대는 down케스팅. 클래스의 케스팅은 상속관계에 있을 때만 가능.  - 이거 에러
							//up으로 한번 올린 놈만 다운으로 올 수 있다.
		
		Ddal dp = (Ddal)up;// 함수에 
		
		up.test();
		dp.test();
		if(d instance of Umma)
		{
			System.out.println("Ddal의 객체")
		}
	}
}

