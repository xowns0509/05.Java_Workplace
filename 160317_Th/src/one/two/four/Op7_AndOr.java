package one.two.four;


public class Op7_AndOr {

	public static void main(String[] args) {
		int 성적 = 85;
		char 태도 = 'A';
		
		if( 성적 > 80 & 성적 < 90 ){
			System.out.println("성적이 80점 에서 90점 사이면 성적향상반입니다.");
		}
		
		if( 성적 > 80 | 태도 == 'A'){
			System.out.println("성적이 80점 이상이거나 태도가 A이면 우등생입니다.");
		}

	}

}
