public class Th03_CD extends Th03_item{

	private String singer;

	public Th03_CD(){
		//this("번호없음", "제목없음", "작가없음");	//1. this를 사용하거나

		//super("번호없음", "제목없음");			//2. super로 부모로 부터 기존에 존재하던 것만 내려받음.
											 
		singer = "가수모름";					
											
		System.out.println("자식CD클래스의 기본생성자");

	}

	public Th03_CD(String num, String title, String singer){
		
		super.num = num;		//부모의 레퍼런스를 지칭하는 슈퍼. 부모의 num에 저장.
		super.title = title;	//부모의 레퍼런스를 지칭하는 슈퍼. 부모의 title에 저장.
		this.singer = singer;
		System.out.println("자식CD클래스의 인자있는 생성자");
	}

	public void output(){
		System.out.print("CD 번호: " + num + ", ");
		System.out.print("CD 가수: " + singer + ", ");
		System.out.println("앨범 명: " + title+ ". ");
		return;
	}
}