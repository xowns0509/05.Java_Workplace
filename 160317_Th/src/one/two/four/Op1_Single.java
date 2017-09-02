package one.two.four;

public abstract class Op1_Single {

	public static void main(String[] args) {
		
		int a = 5;
		int b = 7;
		
		a++; b--; 												//5에서 1증가, 7에서 1감소
		System.out.println("A = " + a + ", B = " + b);
		
		++a; --b; 												//6에서 1증가, 6에서 1감소
		System.out.println("A = " + a + ", B = " + b);
		
		System.out.println("A = " + ++a + ", B = " + --b);		//7에서 1증가하며 출력, 5에서 1감소하며 출력
		
		System.out.println("A = " + a++ + ", B = " + b--);		//출력후 8에서 1증가, 출력 후 b에서 1감소
	
		System.out.println("A = " + a + ", B = " + b);			//위에서 증가, 감소했던 게 그대로 출력
		
	}

}