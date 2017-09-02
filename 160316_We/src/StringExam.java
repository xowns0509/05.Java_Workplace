//편의String 및 정석String의 힙메모리 비교
public class StringExam {

	public static void main(String[] args) {

		String a = new String("Hello");
		String b = new String("Hello");
		String c = new String("hello");

		String aa = "Hello";
		String bb = "Hello";

		// 힙의 주소를 비교하기 때문에 달라야 해(new String)
		if (a == b) {
			System.out.println("주소값이 같다");
		} else {
			System.out.println("주소값이 다르다");
		}

		// 힙의 내용을 비교하기 때문에 같아야 함(둘다 Hello)
		if (a.equals(b)) {
			System.out.println("내용이 같다");
		} else {
			System.out.println("내용이 다르다");
		}

		// 힙 내용을 대소문자 구별 없이 비교하기 때문에 같아야 해
		if (c.equalsIgnoreCase(b)) {
			System.out.println("대소문자 구분 안하고 내용이 같다");
		} else {
			System.out.println("대소문자 구분 안하고 내용이 다르다");
		}

		// 중요!: 힙의 주소를 비교하기 때문에 달라야 하는데
		// bb가 aa와 값이 같아 기존 aa의 메모리주소가 담긴다.
		if (aa == bb) {
			System.out.println("주소값이 같다");
		} else {
			System.out.println("주소값이 다르다");
		}

	}
}
