//static 예제1
public class Th01_Static1 {

	public static void main(String[] args) {
		
		/*0. 변수를 메인함수의 지역변수로 선언,
		 * 이 값을 method의 인자로 넣는다. */
		
		String name = "홍길자";
		int age = 23;
		double height = 167.9;
		method(name, age, height);
	}
	
	static void method(String name, int age, double height){
		
		/*1. 메인이 이 함수로 접근해야 하므로 이 함수는 static이지
		 * 메인함수의 데이터를 넘겨받아 여기서 출력*/
		
		System.out.println("함수의 이름 " + name);
		System.out.println("함수의 나이 " + age);
		System.out.println("함수의 키 " + height);
		return;
	}
}