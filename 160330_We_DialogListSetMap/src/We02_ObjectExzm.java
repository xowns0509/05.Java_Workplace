//클래스명 Object로 주지말기. 왜? Object 클래스가 존재하므로.
public class We02_ObjectExzm {

	public static void main(String[] args) {
		
		We02_ObjectStudent s1 = new We02_ObjectStudent("990101", "송중기");
		We02_ObjectStudent s2 = new We02_ObjectStudent("061111", "송중기");
		
		if(s1.equals(s2)){//s1.equals(s2) 이 결과는 당연히 다르므로 else문이 출력되는데
			System.out.println("같음");
		}else{
			System.out.println("다름");
		}//이름이 같은걸 같은거라고 감지하고 싶어 = 이름만 같은 지를 비교해서 같다면 true로 반환하고 싶어.
		//그렇다면 We02_ObjectStudent에서 equals를 재정의 해줘야 함(오버라이딩 ).
		
		System.out.println(s1);
		System.out.println(s2.toString()); 
		/*또 toString을 오버라이딩
		이건 print ln s2.toString()*/
		/*System.out.println(s1);에서는 자동으로 Object의 toString 불러주므로
		s1을 쓰나 s1.toString()을 쓰나 결과가 똑같음. */
	}

}
