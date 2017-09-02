/* 2  5명의 학생들의 국어, 영어, 수학, 과학, 사회 점수를 이차배열 arr에 저장한 경우 각 학생들의 총점과 평균을 구하시오.*/
public class Chalng_Arr2{
	public static void main ( String [] args ) {

		int [][] score = { { 98, 98, 90, 92, 99 },
				{ 81, 82, 83, 84, 85 },
				{ 71, 73, 75, 77, 79 },
				{ 60, 65, 60, 65, 69 },
				{ 77, 74, 79, 78, 72 } };
		
		int sum = 0;
		int avg = 0;
		for(int i = 0 ; i < 5 ; i++ ){
			sum = 0;
			avg = 0;
			for(int j = 0 ; j < 5 ; j++ ){
				sum += score[i][j];
			}
			avg = (int)(sum / 5.);
			System.out.println( (i+1) + "번 학생의 총점은 " + sum + ", 평균은" + avg);
		}
	}
}