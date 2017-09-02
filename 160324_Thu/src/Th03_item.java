public abstract class Th03_item {
	
	protected String num;
	protected String title;
	
	protected Th03_item(){
		this("번호없음", "제목없음");
		System.out.println("부모클래스의 기본생성자");
	}
	
	protected Th03_item(String num, String title){
		
		this.num = num;
		this.title = title;
		System.out.println("부모클래스의 인자있는 생성자");
	}
	
	public void output(){
		System.out.println(num + "번의 " + title);
	}
	
//	public abstract void output();//미완성 함수라는 신호
//	미완성 함수라서 이 클래스도 결국 미완성이다 따라서 클래스 앞에도 abstract 
}