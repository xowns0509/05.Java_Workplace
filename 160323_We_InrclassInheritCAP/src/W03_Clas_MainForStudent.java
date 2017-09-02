public class W03_Clas_MainForStudent {

	public static void main(String[] args) {
		// 1. 클래스선언
		W03_Clas_stdnt_cap s;			//여기까진 스택에 s의 주소용 메모리영역만 확보됨.
		// 2. 객체 생성
		s = new W03_Clas_stdnt_cap();	//이제 힙 메모리에다가 인스턴스(객체)를 확보하고 그 주소를 s에 처넣.
		
//		s.kor = 99; //이클립스에서는 not visible 이라 뜨는데 이게 javac에선 접근할 수 없다는 메세지와 동일.
//		s.eng = 88;
//		s.math = 77;
		
		s.setKor(99); //s.setKor = 88;로 했을 경우 계속 필드를 만들어야 한다고 에러가 뜸.
		s.setEng(88); //그 이유는 set, get을 지정하면서 이것이 필드에 값을 넣는게 아니라 메소드 형태로 변형되었기 때문.
		s.setMath(77);//그래서 함수에 인자를 넣는 형태로 써줘야 됨.

		
		System.out.println("평균: " + s.calAvg());//인스턴스는 힙에다 선언되었기에 따로 calAvg()에 값이 없어도 자동으로 초기화 된 0이 들어간다.
// 3. 값 지정하거나 출력
	}
}
