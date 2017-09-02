import java.util.Arrays;
import java.util.StringTokenizer;

//문자 쪼개는 놈
public class We04_StringTokenizer {

	public static void main(String[] args) {
		
		String str = "독도는 아름다운 대한민국의 땅입니다";
		StringTokenizer st = new StringTokenizer(str);//공백을 기준으로 단어를 잘라줌.
		
		while(st.hasMoreTokens()){//짤랐을 때 다음 단어가 있니?를 확인하는 메소드. 있으면 true, 없으면 false
			System.out.println(st.nextToken());
			//아무것도 지정하지 않으면 공백을 기준으로 자름.
			//콤마로 잘랐으면 좋겠어? 생성자내용을 아래와 같이 수정.
			//StringTokenizer st = new StringTokenizer(str,",");
			
		}
		System.out.println("----------------");
		//String클래스 내의 split("구분을 원하는 문자")메소드로도 구분이 가능.
		//str토크나이저는 구분자를 여러개 써도 됨.
		//.split(",")은 불가.
		
		//str.split(",");여기까지만 하면 안나옴.
		//구분된 놈들을 배열로 받아야지. 아래에 기술.
		String [] sub = str.split(" ");//
		for(int i = 0 ; i < sub.length ; i++){
			System.out.println(sub[i]);
		}
		
		//구분자를 여러개 쓴 str토크나이저.
		String cal = "3 * 5 - 2 * 7 / 5 + 10";
		StringTokenizer st2 = new StringTokenizer(cal, "*-/+");//*-/+ 즉, 사칙연산기호로 다 짜름.
		while(st2.hasMoreTokens()){
			System.out.println(st2.nextToken());
		}
	}	
}
