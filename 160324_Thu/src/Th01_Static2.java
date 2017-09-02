////static 예제2
public abstract class Th01_Static2 {
	
	static String name = "홍길자";
	static int age = 23;
	static double height = 167.9;
	
	/* 1. 메인함수가 존재하는 src에도 전역변수 선언이 가능.
	 * 단, 메인함수가 전역변수를 쓰려면 static을 붙여야해.
	
	 * 	또한 메인에서 호출할 함수는 static 이어야 하고
	 * 	그 static 함수가 쓰려는 변수들 또한 static 이어야 함.
	 * 	어떤 이유에서든 변수들 앞에 static 붙음.	*/

	public static void main(String[] args) {

		method(name, age, height);

	}
	
	static void method(String name, int age, double height){

		System.out.println("함수의 이름 " + name);
		System.out.println("함수의 나이 " + age);
		System.out.println("함수의 키 " + height);
		return;
	}
}