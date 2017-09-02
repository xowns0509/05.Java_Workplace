public class Th03_Book extends Th03_item{

	private String writer;

	public Th03_Book(){
		//super(); 컴파일러가 모든 생성자 바로 밑에 이걸 자동으로 껴줌.
		
		//num = "번호없음";					// 0. 원래는 이렇게 일일히 다 생성해야 하지만.
		//title = "제목없음";
		//writer = "작가없음";

		//this("번호없음", "제목없음", "작가없음");	// 1. this를 사용하여 아래에 있는 이 클래스의 생성자를 사용하거나

		//super("번호없음", "제목없음");			// 2. super로 부모로 부터 기존에 존재하던 것만 내려받음.
											// 부모의 생성자 함수를 불러오면 딱 일텐데... 그럴 때 사용.
		writer = "작가미상";					// 3. writer는 부모에 없은 거라 내가 초기화 해줘야 하는거고.
											
		System.out.println("자식북클래스의 기본생성자");

	}

	public Th03_Book(String num, String title, String writer){
		//super(num, title);	//컴파일러가 강제로 넣는 super();를 무시하고 인자가 있는 부모생성자함수를 사용하고자 할 때.
		
		/* 왜 인자있는 자식의 생성자를 실행했는데도 부모의 인자없는 생성자가 실행되는가.
		 * 이유는 컴파일러가 강제로 넣는 super();의 삽입 때문.
		 * 이걸 원하지 않으면, 즉 내가 원하고자 하는 부모클래스의 인자 있는 놈으로 인스턴스를 생성하고 싶을 땐
		 * super(num, title); 을 적어주면 되. */
		
		/*그럼 컴파일러는 왜 super();를 넣어주는가. 부모를 먼저 메모리영역에 불러오기 위해서.
		 * 부모가 자식보다 먼저 메모리 영역에 선언되어야 하고 그 다음에 비로소 자식이 메모리영역에 생성되니까.*/
		
		super.num = num;		//부모의 레퍼런스를 지칭하는 슈퍼. 부모의 num에 저장.
		super.title = title;	//부모의 레퍼런스를 지칭하는 슈퍼. 부모의 title에 저장.
		this.writer = writer;
		System.out.println("자식북클래스의 인자있는 생성자");
	}

	public void output(){
		System.out.print("책 번호: " + num + ", ");
		System.out.print("책 작가: " + writer + ", ");
		System.out.println("책 제목: " + title+ ". ");
		return;
	}
}