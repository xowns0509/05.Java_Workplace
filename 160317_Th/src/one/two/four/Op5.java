package one.two.four;


public class Op5 {

	public static void main(String[] args) {
				
		int kor = 98, eng = 77, math = 58;
		int total = kor + eng + math;		// 233
		double avg = total / 3;				/* 77.666666 → 77
											 * 자료형이 더블이라 소숫점을 저장 할 수 있어도
											 * int(total=233)와 int(3)를 연산을 하면 반드시 결과가 int형이 됨.
											 * 이는 강제이며 임시공간에도 int형으로 저장, 이미 계산과정에서 소숫점 다 짤림.*/
		
		double avg1 = total / 3.;			/* 그런데 둘 중 하나라도 실수라면 결과를 실수로 뽑아준다.
											 * 임시저장 역시 실수형으로. 소숫점손실이 발생 안함.
											 * 3.에 따로 0을 안 붙여도 알아서 자바가 실수로 파악 그러나
											 * 유지보수가 보기에 힘들지 않도록, 3.0으로 써주는 게 옳음. */
		
		System.out.println("총점 " + total );
		System.out.println("평균 " + avg );

		
		if( avg1 >= 90 ){					/* 90점 이상 A / 80부터 90미만 B / 70부터 80미만 C*/
			System.out.println("학점 A, 니 점수 평균은 " + avg1 );
		}
		else if( avg1 >= 80 ){
			System.out.println("학점 B, 니 점수는 평균은 " + avg1 );
		}
		else if( avg1 >= 70 ){
			System.out.println("학점 C, 니 점수는 평균은 " + avg1 );
		}

		if( avg1 >= 70 && avg1 < 80 ){
			System.out.println("학점 C, 니 점수는 평균은 " + avg1 );
		}
		else if( avg1 >= 80 && avg1 < 90 ){
			System.out.println("학점 B, 니 점수는 평균은 " + avg1 );
		}
		else if( avg1 >= 90 && avg1 <= 100 ){
			System.out.println("학점 A, 니 점수 평균은 " + avg1 );
		}
		
		System.out.println("평균 " + avg1 );
		
	}
}
