//데이터를 메소드가 갖고 있고 그 데이터를 메인함수로 보내서
//메인함수에서 출력하고 싶어.
public class Th01_Static3 {

	public static void main(String[] args) {
		
/*		클래스 인스턴스명 = 함수명();
 * 		Data d = method(); d라는 인스턴스를 생성,
 * 		여기서 method는 값이 담겼던 메인함수 아래에 있는 static 함수였다. */
		
//		d.print 
//		인스턴스.print(); 해당 인스턴스의 출력메소드
		
		Method meT = new Method();		
		System.out.println("클래스에 담긴 name" + meT.name);
		System.out.println("클래스에 담긴 age" + meT.age);
		System.out.println("클래스에 담긴 height" + meT.height);
		
		PriMethod pMeT = new PriMethod();
		
		pMeT.prt();		

	}
}

class Method{
//	클래스 선언시 반드시 생성자나 메소드(함수)를 적어 줄 필요는 없네
//	아래처럼 멤버변수만 적어줘도 되잖아.
	
	String name = "홍길자";
	int age = 23;
	double height = 167.9;
	
/*	void method(String name, int age, double height){ }*/
}

class PriMethod{
//private 전용	
	private String name = "홍길자";
	private int age = 23;
	private double height = 167.9;
	
	public PriMethod() {} //기본생성자는 반드시 기재함을 습관들여라.
	public PriMethod(String name, int age, double height){
		
		this.name = name;
		this.age = age;
		this.height = height;
		
	}
	
	public void prt(){
		
		System.out.println("pri클래스에 담긴 name" + name);
		System.out.println("pri클래스에 담긴 age" + age);
		System.out.println("pri클래스에 담긴 height" + height);
		
		return;
	}
}