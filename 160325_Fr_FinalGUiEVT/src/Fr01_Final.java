class Parent{
final class Parent{ //여기다가 final을 붙여버리면 상속하지 않겠다고 함.
/*	abstract와 final은 그래서 서로 모순이며 둘 중 하나만 써야 한다.
	abstract는 내가 부모라는걸 명시, final은 내가 부모가 절대로 안된다고 하는 거잖. */
	
}
	//String field = "부모님꺼";//부모가 원래 field를 갖고 있고 거기에 값이 있었는데
	//자식이 field를 물려받아 이 값을 "부모님꺼"에서 "내꺼" 고치려 한다.
	//이를 막기 위해 부모는 변수자료형 앞에 final을 붙여 못 상속 받게 한다.
	
	//변수에 붙이면 값변경이, 함수 앞에 붙이면 재정의가 안되는 것.

	final String field = "부모님꺼";
	
	//public void jib(){//마찬가지로 메소드도 오버라이딩 하지 않겠다. 하려면 이 앞에 final붙이면 됨.
	final public void jib(){
		System.out.println("부모님이 만드신 거");
	}
}

class Child extends Parent{//자식은 부모의 private 빼고는 다 접근 가능하지.
	Child(){
		field = "내꺼"; //그럼 여기에 에러 뜸
	}
	
	public void jib(){
		System.out.println("부모님께 물려 받아 탕진.");
	}
}

public class Fr01_Final {

	public static void main(String[] args) {
		
		Parent p = new Child();
//		p.field = "내꺼"; //1.
		System.out.println(p.field);
		p.jib();

	}
}