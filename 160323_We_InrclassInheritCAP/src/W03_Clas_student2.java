//3명의 이름, 국어, 영어, 수학을 입력받아 이름, 총점, 평균 출력하기.
public class W03_Clas_student2{
	
	private String name;
	private int kor, eng, math, total;
	private double avg;
	
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
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
	
	public int getTotal() {
		return total;
	}

	public String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	void prtTotAvg(){
		System.out.println("총점: " + total + "평균: " + avg);
		return;
	}
	
	void calcTotAvg(){
		total = kor + eng + math;
		avg = total / 3.;
		return ;
	}
}