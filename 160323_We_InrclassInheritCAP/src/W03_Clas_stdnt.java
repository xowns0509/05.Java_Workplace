//클래스 정의, 생성자 오버로딩, 무인자생성자, 전역지역변수
public class W03_Clas_stdnt{
	
	// 서로 다른 데이타 타입들의 변수(member fields)들을 선언하여 클래스 안에 묶었다.
	// 이놈들은 이 클래스 어느 부분에서라도 쓰일 수 있는 전역변수과 같다. 이 클래스로 객체를 선언시 선언되며 	
	String name;
	int kor, eng, math;
	double avg;
	// W03_Clas_stdnts s;
	// = new W03_Clas_stdnt(); 이 구문에서야 비로소 구체화 됨.
	
	public double getAvg() {
		//avg = calAvg();
		//return avg;
		return calcAvg();		
	}
	
	W03_Clas_stdnt(String name, int kor, int eng, int math){
		this.name = name;		
		this.math = math;
		this.eng = eng;		
		this.kor = kor;
		System.out.println("인자 있는 생성자");
	}
	
	W03_Clas_stdnt(){ //이 클래스로 아무인자도 주지 않고 인스턴스를 생성시에 해당. 동시에 위의 생성자와 오버로딩이지. 
		
/*	무인자로 인스턴스 생성시 초기값을 아래와 같이 주고 싶다고 해.
 * name = "이름없음"; kor = 50; eng = 50; math = 50;
 * 
 * 근데 이렇게 쓰자니 그냥 위에서 똑같이 내려받으면 되잖아. 그럼 여기에
 * W03_Clas_stdnt("이름없음",50, 50, 50) 이렇게 하면 괜찮지 않을까? 근데 안됨. 그래서 아래와 같이 처리 */
		this("이름없음", 50, 50, 50); //이 클래스 안에 있는, 나와 다른 생성자를 불러 그 기능을 원할 .
		//단, this는 무조건 해당생성자의 첫 번째 라인에 와야 해. 당장 아래의 출력구문이 이놈 보다 위에 오면 오류 뿜. 
		System.out.println();
	}
	
	// 역할(작업)을 수행하는 함수(메소드 member methods)들이 선언되어 클래스 안에 묶일 수 있다.	
	double calcAvg(){		
		//double avg; // 전역변수와 이름이 똑같은데 에러가 안 발생해?
		/* 이 avg는 지역변수. calAvg을 호출 할 때만 생성되며 calsAvg안에서만 유효.
		 * 그 뜻은 이 avg에 저장된 값은 멤버변수 avg에 저장이 안되겠지.
		 * 당장 double avg 앞에 주석처리를 지워봐도
		 * 밑의 total의 avg와 return avg의 이클립스가 제공하는
		 * 지역변수와 멤버변수를 구분하는 색깔이 달라진다. 파란(전역) → 갈색(지역).
		 * 스택에 잡힘, 자동초기화 안되겠지. 지역변수는 언제나 초기화 해.*/
		avg = (kor + eng + math)/3.;
		//클래스 전역과 상관 없기에 굳이 이 함수의 매개변수에 안써도 avg는 그대로 불러 올 수 있다.
		return avg;
	}
}