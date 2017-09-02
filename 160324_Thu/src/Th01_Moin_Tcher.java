//강사님 만든 거
public class Th01_Moin_Tcher{

	public static void main(String[] args) {
		// method() 함수에게서 데이타를 넘겨받아 출력.
		Data d = method();
		/* 리턴되는 놈의 자료형이 data 클래스. 그렇다면 받는 놈도 자료형을 맞춰야 함.
		 * 함수(메소드)는 값 하나 밖에 반환 못하지만 이런 식으로 함수 내에 인스턴스를 생성하여 
		 * 서로 다른 자료형과 기능들을 마치 하나의 값으로 반환 할 수도 있다.
		 * 
		 * 단, 이건 메인에서 생성한 d인스턴스와 함수 method();에서 생성한
		 * data 인스턴스의 자료형, 즉 원본 클래스가 같아야지.
		 * 
		 * 실제는 data에 저장된 4바이트의 주소번지 값을 d로 넘겨주는 거. */

		d.print();
	}

	static Data method(){
		String name = "홍길자";
		int age =  23;
		double height = 167.9;
		Data data = new Data(name, age, height);
		/* data는 4바이트의 주소값 메모리영역.
		 * Data클래스로부터 data인스턴스 생성.*/
		return data; //리턴되는 놈의 자료형이 data이므로 
	}
}

class Data{
	private String name;
	private int age;
	private double height;
	public Data(){} //기본 생성자 생성하는 습관
	public Data(String name, int age, double height){
		this.name = name;
		this.age = age;
		this.height= height;
	}
	public void print(){
		System.out.println(name+"님은 " + age +"세이고 키는 " + height + "입니다");
	}
}