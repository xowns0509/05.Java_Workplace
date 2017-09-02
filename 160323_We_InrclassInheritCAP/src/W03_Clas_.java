//클래스로부터 인스턴스를 생성
public class W03_Clas_ {

	public static void main(String[] args) {
		
		W03_Clas_stdnt s = new W03_Clas_stdnt("홍길동", 99, 88 ,77);
		System.out.println("s의 평균" + s.getAvg());
		
		W03_Clas_stdnt s2 = new W03_Clas_stdnt();
		System.out.println("s2 평균" + s2.getAvg());
		
	}
}