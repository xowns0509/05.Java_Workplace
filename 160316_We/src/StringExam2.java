public class StringExam2 {

	public static void main(String[] args) {

		String a = new String("Hello");
		String b = new String("Hello");
		b = b + "^^"; // b = "hello" + "^^"

		// 힙의 주소를 비교하기 때문에 달라야 해
		if (a == b) {
			System.out.println("주소값이 같다");
		} else {
			System.out.println("주소값이 다르다");
		}

		// 힙의 내용을 비교하기 때문에 달라야 해
		if (a.equals(b)) {
			System.out.println("내용이 같다");
		} else {
			System.out.println("내용이 다르다");
		}

	}
}
