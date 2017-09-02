public class Th03_DVD extends Th03_item{

	private String director;
	private String actor;

	public Th03_DVD(){
		//this("번호없음", "제목없음", "작가없음");	1. this를 사용하여 아래에 있는 이 클래스의 생성자를 사용하거나

		//super("번호없음", "제목없음");			2. super로 부모로 부터 기존에 존재하던 것만 내려받은 후
		
		director = "감독모름";	
		actor = "배우모름";

		System.out.println("자식DVD클래스의 기본생성자");

	}

	public Th03_DVD(String num, String title, String actor, String director){

		super.num = num;		//부모의 레퍼런스를 지칭하는 슈퍼. 부모의 num에 저장.
		super.title = title;	//부모의 레퍼런스를 지칭하는 슈퍼. 부모의 title에 저장.
		this.title = title;
		this.director = director;
		System.out.println("자식DVD클래스의 인자있는 생성자");
	}

	public void output(){
		System.out.print("CD 번호: " + num + ", ");
		System.out.print("CD 가수: " + actor + ", ");
		System.out.println("앨범 명: " + title+ ". ");
		System.out.println("앨범 명: " + director+ ". ");
		return;
	}
}