import java.util.Scanner;

public class Ctrl1for_totalavg2 {

	public static void main(String[] args) {
		
		System.out.println("국어 점수 입력하세요. 1/3");		
		Scanner in0 = new Scanner(System.in);
		/* String a = in0.nextLine();
		 * nextline이 반환하는 값이 스트링이라서 int를 String으로 바꾸라고 말하는 거.
		 * nextInt는 스페이스 전까지 있는 걸 받음 */	
		int kor = in0.nextInt();
	
		System.out.println("수학 점수 입력하세요 2/3");	
		Scanner in1 = new Scanner(System.in);
		int math = in1.nextInt();
		
		System.out.println("영어 점수 입력하세요 3/3");	
		Scanner in2 = new Scanner(System.in);
		int eng = in2.nextInt();
				
		int total = kor + eng + math;		// 어저께 평균 냈던 거 활용하여 평균이 80점 이상이면 합격 출력, 이하면 불합격.
		double avg = total / 3.;			
		String result = (avg >= 80)?"합격입니다.":"불합격입니다";

		System.out.println("\n****결과입니다.****");
		System.out.println("총점은 " + total);
		System.out.println("평균은 " + avg);
		System.out.println("결론은 " + result);
				
		int score = (int)(avg / 10); /* avg가 double이라 형변환 해야 하는데
										(int)avg/10; 보다는
										(int)(avg/10); 이겠지 */
		switch( score )
		{
			case 10 : System.out.println("A");
			case 9 : System.out.println("A");
			case 8 : System.out.println("B");
			default : System.out.println("F");
		}
		
	}
}