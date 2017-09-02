import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class We06_ArrayListTest2 {

	public static void main(String[] args) {
		
		ArrayList<String> list = new ArrayList<String>(4);
		//<꺽쇠>안의 Generic는 저장될 자료형을 명확하게 지칭하는 것.
		list.add("rabbit");
		list.add("dog");
		list.add("cat");
		list.add("fox");
		list.add("cat");
		list.add("tiger");
		list.add("zebra");
		list.add("elephant");
		//list.add(3); ArrayList<String>이라서 정수형 3은 못들어가지.
		
		System.out.println(list+"\n");//자동으로 tostring
		
		System.out.println("1. list.set(2, "+"crocodile"+")으로 cat대신 crocodile삽입.");
		list.set(2, "crocodile");
		System.out.println(list);
		
		//4번째 요소 지우기
		System.out.println("2. list.remove(3);으로 Index 4번째 요소 삭제.");
		list.remove(3);
		System.out.println(list);
		
		//자바의정렬은 표준화되어있음
		System.out.println("3. Collections.sort(list);로 정렬");
		Collections.sort(list);
		System.out.println(list);
	}

}
