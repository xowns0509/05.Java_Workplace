//static 테스트, 클래스의 중복
public class W04_BookExam {

	public static void main(String[] args) {
		//	Book b1 = new Book();
		//	Book b2 = new Book();
		//	Book b3 = new Book();
		//	System.out.println("총 책의 갯수: " + b3.count);
		/*	객체가 생성될 때마다 번호를 부여하고 싶어
		 *	객체가 생성될 때마다 카운트를 증가시키면 되겠네?
		 *	그럼 카운트를 증가하는 생성자를 만들면 되겠네? 그런데
		 */
		//	System.out.println("총 책의 갯수: " + Book.count);//클래스명.스태틱변수명
		System.out.println("총 책의 갯수: " + Book.getCount());
		/*	객체가 생성할 때 마다 카운트를 증가시키려면 카
		 *  왜냐면 생성자는 객체가 
		 */
		
	}
}

/* BookExam.java이지만 클래스가 2개일 수도 있다.
 * 여러개의 클래스를 하나의 소스문에 만들 경우 메인함수에다만 public 쓸 수 있음.
 * 메인함수를 포함하고 있는 클래스명으로 파일명을 써야함. */
class Book{
	private static int count;//static을 붙이니 메모리구조가 달라짐.
	Book(){
		count++;
		System.out.println("생성자를 호출하셨군요. 멤버변수 초기화");
	}

	static{
		System.out.println("static 초기화"); //인스턴스 생성을 위해 
	}

	static public int getCount(){
	// Book.getCount()를 수행하려 할 때 이놈도 static을 붙여야...
		// 그렇지 않으면 객체가 먼저 생성되지 않는 이상 불러 올 수 없다. 
		return count;
	}
}

/* 자 그럼 왜 자바는 메인함수를 static으로 만들었는가.