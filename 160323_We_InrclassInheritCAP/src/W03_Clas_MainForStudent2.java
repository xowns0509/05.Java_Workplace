//이름. 총점 평균구하기
import java.util.*;

public class W03_Clas_MainForStudent2 {

	public static void main(String[] args) {

		//		String name1, name2, name3; //name[3]
		//		int kor1, eng1, math1; //score[0][0,1,2]
		//		int kor2, eng2, math2; //score[1][0,1,2]
		//		int kor3, eng3, math3; //score[2][0,1,2]		

		String [] name = new String[3];
		int [][] score = new int[3][3];

		for( int i = 0 ; i < 3 ; i++ ){

			System.out.println("학생의 이름, 국어점수, 영어점수, 수학점수를 입력하세요 ");
			System.out.print((i+1) + "번째 학생이름: ");
			Scanner in0 = new Scanner(System.in);		/* nextInt는 스페이스 전까지 있는 걸 받음 */				
			name[i] = in0.nextLine();

			for( int j = 0 ; j < 3 ; j++ ){				

				String subj = new String();
				switch(j){
				case 0 : subj = "국어성적 입력: "; break;
				case 1 : subj = "영어성적 입력: "; break;
				case 2 : subj = "수학성적 입력: "; break;
				}

				System.out.print(name[i] + " 학생의 " + subj);
				Scanner in1 = new Scanner(System.in);		/* nextInt는 스페이스 전까지 있는 걸 받음 */				
				score[i][j] = in1.nextInt();
			}
			System.out.println((i+1) + "번째 학생의 성적입력을 받았습니다.\n");
		}

		//클래스 생성 3개.
		W03_Clas_student2 [] s = new W03_Clas_student2[3];
		for( int i = 0 ; i < 3 ; i++){
			s[i] = new W03_Clas_student2(); //
		}

		// 생성된 인스턴스로 이름,성적입력
		for( int i = 0 ; i < 3 ; i++ ){
			s[i].setName(name[0]);
			for( int j = 0 ; j < 3 ; j++ ){
				switch(j){
				case 0 : s[i].setKor(score[i][j]); s[i].calcTotAvg(); break; //국어성적 입력
				case 1 : s[i].setEng(score[i][j]); s[i].calcTotAvg(); break; //영어성적
				case 2 : s[i].setMath(score[i][j]); s[i].calcTotAvg(); break; //수학성적 입력
				}			
			}
			System.out.println(i + "번째 학생의 성적입력을 완료했습니다.");
		}

		//출력단
		for( int i = 0; i < 3 ; i++ ){
			System.out.print("\n************ "+name[i]+"학생의 결과 ************" + "\n" + name[i]);
			switch(i){
			case 0 : s[i].prtTotAvg(); break;
			case 1 : s[i].prtTotAvg(); break;
			case 2 : s[i].prtTotAvg(); break;
			}
		}
	}
}