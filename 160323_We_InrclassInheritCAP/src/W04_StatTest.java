//메인함수에도 전역변수 선언이 가능.  단, 메인함수가 전역변수를 쓰려면 static을 붙여야해.
public class W04_StatTest {
	
	String normal = "일반변수";				//멤버변수로 선언.
	static String staticVar = "클래스변수";		//static 멤버변수로 선언.

	public static void main(String[] args){

		System.out.println( staticVar );
		//static변수는 메인함수가 바로 가져와서 쓸 수 있다.
		
		System.out.println( normal );
		 /* 그러나 메인함수에서 메인함수의 전역변수들을 쓰려면 static을 붙여야.
		 * 왜? 메인함수에 static이 붙잖아.
		 * 아까 student 클래스에서는 메인함수가 없어서
		 * static없이 모든 멤버변수들을 그대로 쓸 수 있었어.*/
		
		W04_StatTest stat = new W04_StatTest();	// 메인함수 자신을 인스턴스하는 것도 가능.
		System.out.println( stat.normal );		/* 자신의 인스턴스에서 갖고 오는 것은 가능.
												 * 그러나 인스턴스의 normal과 자신의 normal은 엄연히 다른 놈.*/
		System.out.println( stat.staticVar );
		System.out.println( W04_StatTest.staticVar );
	}
}
