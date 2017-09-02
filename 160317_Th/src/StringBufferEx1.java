// 문자열을 처리하는 클래스
public class StringBufferEx1 {

	public static void main(String[] args) {
		StringBuffer a = new StringBuffer("Hello");
		StringBuffer b = new StringBuffer("안녕");

		change(a, b);
		System.out.println(a);
		System.out.println(b);
	}

	static void change(StringBuffer x, StringBuffer y) {// Hello와 안녕을 받음
		y.append("안녕");// b의 안녕에
		System.out.println(x);
		System.out.println(y);

	}
}
