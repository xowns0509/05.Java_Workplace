public class M01_MethD {

	public static void main(String[] args) {
	
		StringBuffer s = new StringBuffer("Hello");
		StringBuffer t = new StringBuffer("Happy");
		
		/* addData(a, b);			// 함수 호출. 여기서 a, b를 인자라 하며 영어로 argument.
		 * int hap = addData(a, b); // 함수로 부터 값을 받으려면 함수 내에 return이 존재해야 하고 void가 아닌 반환형이 정해져야 한다
		 * System.out.println("합 = " + hap );
		 */

		// StringBuffer hap = addData(s, t);		
		// System.out.println("두 문자열의 합: " + hap);	
		addData(s, t);
		System.out.println("두 문자열의 합: " + s);
	}
	
	// 두 인자를 받아 더하기역할 하는 함수
	/* static void addData(int z, int y){//받을 놈(parameter - 매개변수)의 자료형과 그 순서를 명확히 기재
		int result = z + y;
		System.out.println("합 = " + result );
	}
	*/
	
	// int a = 10, b = 20;
	
	// 두 인자를 받아 더한 후 결과를 반환하는 함수
	/* static int addData(int z, int y){//값을 반환하려면 void대신 반환할 값의 자료형을 써줌.
		int result = z + y;
		return result;
	}
	*/
	
	/* static StringBuffer addData(StringBuffer z, StringBuffer y){//값을 반환하려면 void대신 반환할 값의 자료형을 써줌.
		// z += y; 는 불가. 이건 string에서만 된다고.
		z.append(y);
		return z;		
	} */
	
	static void addData(StringBuffer z, StringBuffer y){//값을 반환하려면 void대신 반환할 값의 자료형을 써줌.
		// z += y; 는 불가. 이건 string에서만 된다고.
		z.append(y);
	}
}
