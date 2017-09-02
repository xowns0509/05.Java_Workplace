//캡슐화 setter와 getter.
public class W03_Clas_stdnt_cap{
	
	/*원하는 멤버변수의 자료형 앞에 private를 추가,
	 * 이 클래스 안에서만 사용 할 수 있도록 한다. */
	private String name;
	private int kor, eng, math;
	private double avg;

	/* 캡슐화. setter와 getter.
	 * private를 걸었는 데 접근은 해야 할 꺼 아냐.
	 * 그래서 접근이 가능하도록 함수로 만들어 처리.
	 * 
	 * 이는 완벽한 보안은 아니지만 ++나 -- 등 개발자기 놓치기 쉬운
	 * 수상한 접근을 불허하는 데에 유용하게 쓰임. 
	 * 그 함수는 보통 public을 주고
	 * 내용에 this함수를 붙여 private된 변수들에게 비로소 접근 가능하게 한다.
	 * 
	 * public void setId(String id) {
	 * this.id = id;
	 * } 
	 * 	public String getId() {
	 * 		return id;
	 * }
	 * 참고로 100개의 멤버변수에 캡슐화 하려면
	 * 100개의 변수마다 이 짓을 해야 한다.
	 * 일일히 get, set 쳐줄 필요 없이 이클립스에서 원하는 전역변수만 찍어
	 * 자동생성 가능.*/	

	public void setKor(int kor) {//여기 매개변수 kor는 setKor 안에서만 유효한 존재.
		this.kor = kor;			//그래서 여기에 this를 안 붙일 경우 멤버변수에 값이 저장 안 됨.
	}
	/* 이제 kor변수에 의한 접근은 setKor();와 getKor();로 행해야 한다.
	 * s.kor = 99; 불가.
	 * s.setKor(99); 가능. */
	
	public int getKor() {
		return kor;
	}
	
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}

	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}

	public String getName() {
		return name;
	}
	public void setName(String n){//누구나 허용할 수 있는 public
		name = n;
	}

	double calAvg(){
		avg = (kor + eng + math)/3.;
		return avg;
	}
}