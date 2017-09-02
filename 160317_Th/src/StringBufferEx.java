// 문자열을 처리하는 클래스
public class StringBufferEx {

	public static void main(String[] args) {
		String a = new String("Hello");
		String b = new String("안녕");

		String aa = "aa.Hello";
		String bb = "bb.안녕";

		change(a, b);
		System.out.println(a);
		System.out.println(b + "\n");

		change(aa, bb);
		System.out.println(aa);
		System.out.println(bb + "\n");
	}

	static void change(String x, String y) {
		x = y + x;
		System.out.println("함수 change의 출력");
		System.out.println(x);
		System.out.println(y + "\n");

	}

}
