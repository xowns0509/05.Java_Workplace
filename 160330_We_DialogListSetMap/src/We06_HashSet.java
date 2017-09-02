import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class We06_HashSet {

	public static void main(String[] args) {
		
		HashSet<String> set = new HashSet<String>();
		set.add("zebra");
		set.add("dog");
		set.add("cat");
		set.add("dog");
		set.add("dog");
		set.add("rabbit");
		
		System.out.println("Set은 순서가 없고 데이터중복 불허, 3개 도그가 들어도 1개의 도그만");
		System.out.println(set);//자동으로 tostring
		
		LinkedList list = new LinkedList(set);
		Collections.sort(list);
		
		System.out.println(list);
	}

}
