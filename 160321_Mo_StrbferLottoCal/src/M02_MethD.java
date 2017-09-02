import java.util.Scanner;
public class M02_MethD {

	public static void main(String[] args) {
		
		int [] subj_score = new int[3];
		System.out.println("국어, 수학, 영어 점수를 숫자만 차례대로 입력하세요. 1/3");
		
		for( int i = 0 ; i < 3; i++ ){
			Scanner in0 = new Scanner(System.in);	
			subj_score[i] = in0.nextInt();
		}
		
		int total = subj_score[0] + subj_score[1] + subj_score[2];	
		double avg = total / 3.;
		
		for( int i = 0 ; i < 3; i++ ){
			switch(i){
			case 0 : System.out.print("국어점수 "); break;
			case 1 : System.out.print("수학점수 "); break;
			case 2 : System.out.print("영어점수 "); break;
			}
			System.out.println(subj_score[i] + "점");
		}
		
		String result = (avg >= 80)?"합격입니다.":"불합격입니다";

		System.out.println("\n****결과입니다.****");
		System.out.println("총점은 " + total);
		System.out.println("평균은 " + avg);
		System.out.println("결론은 " + result);
	}
}