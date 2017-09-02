//이너클래스의 static적용
class Outer2{
	static class Inner2{		/* 원래 클래스는 static을 붙일 수 없다.
								 * 이너클래스는 멤버필드, 멤버메소드처럼 쓰이기 때문에 static 가능. */										
		public void secret(){	// 메소드라서 이것도 static이 가능하지
			System.out.println("엄청 소중한 거");
		}
	}
}

public class W04_InnerTest2 {
	public static void main(String[] args){
		
		Outer2.Inner2 in = new Outer2.Inner2(); //o인스턴스를 타고 또 다른 인스턴스를 생성.
		in.secret();	
	}
}
//		Outer.Inner in = new Outer.Inner(); //o인스턴스를 타고 또 다른 인스턴스를 생성.
//		Outer.Inner.secret();이너 모		