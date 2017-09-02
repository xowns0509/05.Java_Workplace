//형변환
public class Casting {
	
	public static void main(String[] args) {
		
		byte b = 127;
		char c = '글';
		int i = 20000000;
		long l = 1L;
		
		System.out.println("케스팅 전");
		System.out.println("byte b = 127: " + b);
		System.out.println("char c = '글'" + c);
		System.out.println("int i = 20000000: " + i);
		System.out.println("long l = 1L:" + l);
		
		b = (byte)i;
		i = (int)c;
		int var = (int)b;
		float f = (float)l;
		// l = (double)i; 에러. 케스팅 불가
		
		System.out.println("케스팅 후");
		System.out.println("b = (byte)i, b =  " + b); // 정수형 i의 20000000을 byte로 캐스팅
		System.out.println("i = (int)c, i = " + i); // 문자형 c의 '글'을 int로 캐스팅
		System.out.println("int var = (int)b, var = " + var); // 바이트형 b의 127을 int로 캐스팅: 
		System.out.println("float f = (float)l, f = " + f); // 롱형 l의 1L을 float으로 캐스팅: 
		
	}
}