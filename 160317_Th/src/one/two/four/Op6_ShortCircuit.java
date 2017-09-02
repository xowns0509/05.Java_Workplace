package one.two.four;
//자바 숏서킷 로직
//일반논리연산자에는 short circuit logic 발동



public class Op6_ShortCircuit {
	
	public static void main(String[] args) {
		
		int a = 3;
	
		if ( a > 3 && ++a > 3){					//앞 결과가 false(a > 3), 그것이 and와 묶이면 무조건 false이기 때문에
			System.out.println("조건만족 1");		//++a > 3을 연산 안 함. 그랬기 때문에
		}
		System.out.println(a);					//이 결과에 4가 나오는게 아니라 3이 나와

		
		if ( a > 1 || ++a > 3){					//마찬가지로 앞 결과가 true(a > 1), 그것이 or와 묶이면 무조건 true이므로
			System.out.println("조건만족 2");	//++a > 3을 연산 안 함. 그러므로
		}										
		System.out.println(a);					//이 결과에 4가 나오는게 아니라 3이 나와.

		
												//이를 자바의 숏서킷로직이라 하는데 이를 발동시키지 않으려면
												//일반논리에도 이진연산자를 써주면 됨.
		
		if ( a > 3 & ++a > 3){					//++a > 3을 처리했으니 a가 4가怜?
			System.out.println("조건만족 1");
		}		
		System.out.println(a);					//4가 출력 됨.
		
		if ( a > 1 | ++a > 3){					//++a > 3을 처리 했으니 a가 5가怜?
			System.out.println("조건만족 2");
		}
		System.out.println(a);					//5가 출력됨.
	}

}
