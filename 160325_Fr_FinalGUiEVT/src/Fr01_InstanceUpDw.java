import java.util.Scanner;

public class Fr01_InstanceUpDw  {

	public static void main(String[] args) {
		//화면 가정 - 여기서 그림을 그린다고 가정하면 아래와 같이 하면 됨.
		Sharp receivesInstance = method();
		receivesInstance.draw();
		
		Rect r = (Rect)receivesInstance;
		r.rectangle();

	}

	static Sharp/*void*/ method(){
		
		Scanner in = new Scanner(System.in);
		//ctrl + shift + o 하면  import java.util.Scanner;가 자동으로 써짐.
		System.out.println("님이 원하는 도형은? 1.사각, 2.원, 3.삼각");
		int sel = in.nextInt();

		Sharp s = null;//4. null을 안주면 

		switch (sel) {
		case 1: s = new Rect(); break; //변수는 부모s인데 자식은 객체. 부모가 아니라 자식이 객체가 되었으므로, 오버라이딩 가능.
		case 2: s = new Circle(); break;
		case 3: s = new Tri(); break;
		}
		
		//s.draw();
		return s;//5. 여기서 에러남. Initialize variable. Sharp s를 초기화 하라고.

//				switch (sel) {
//				case 1: new Rect(); break;
//				case 2: new Circle(); break;
//				case 3: new Tri(); break;

	}

}
//부모. 자식들 간의 공통 된 놈들.
abstract class Sharp { //2. 그리고 해당 클래스에다도 abstract를 해주면
	abstract public void draw(); // 1. 밑에 실수로 오타났는데 그것도 씹고 오버라이딩 해주려면 abstract,
}
//자식1
class Rect extends Sharp{ //상속은 하나 밖에 안됨. 무조건 단일 상속, 부모가 2명이상 올 수 없다.
	public void draw(){
		System.out.println("사각형을 그림");
	}
	
	public void rectangle(){
		System.out.println("4각형은 점 4개의 도형입니다.");
	}
}

//자식2
class Circle extends Sharp{ //3. 메소드명이 오타난 부분에 자동으로 경고창이 뜸. 오류아이콘을 눌러서 Add unimplemented method하면 자동으로 메소드를 입력해줌.
	public void draw(){ //오버라이딩 을 원하는데 draw를 drow로 실수로 오타. 그렇다면 강제로 오버라이딩 하기 위해
		System.out.println("원을 그림");		
	}
}

//자식3
class Tri extends Sharp{
	public void draw(){
		System.out.println("삼각형을 그림");
	}
}