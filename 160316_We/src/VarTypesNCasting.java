//변수의 타입과 형변환
public class VarTypesNCasting {

	public static void main(String[] args) {
		
		boolean boolVar;
		int intVar, intVar1;
		char charVar, charVar1;
		float floatVar;

		boolean boolVar1 = false;		//초기값은 true/false
		int intVar2=0;
		char charVar2;
		float floatVar1;		
		
		boolVar = true;					//boolVar = 0; err
		intVar = 2000000000;
		intVar1 = (int)3;	
		charVar = 3;					//charVar = 65;
		charVar = 'A';

		floatVar = 3.6F;				//floatVar = 3.6; err, 자바가 자동으로 판단, 3.6을 실수형으로 심시저장하므로. 근데 실수형과 float은 서로 맞지 않는 형태
		floatVar = (float)3.6;
		
		System.out.println("논리형: "+ boolVar);
		System.out.println("정수형: "+ intVar);
		System.out.println("정수형 케스팅: "+ intVar1);
		System.out.println("문자형: "+ charVar);
		System.out.println("실수형: "+ floatVar);
	}

}
