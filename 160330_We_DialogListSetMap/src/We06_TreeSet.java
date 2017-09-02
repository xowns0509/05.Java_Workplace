import java.util.TreeSet;

public class We06_TreeSet {

	public static void main(String[] args) {
		
		TreeSet<String> set = new TreeSet<String>();//자료구조 용어. HashSet은 중복이 나올 경우 그냥 버림.
		set.add("elephant");
		set.add("dog");
		set.add("HALLS");
		set.add("GOODSPERY");
		set.add("PEN");
		set.add("MOUse");
		set.add("EDGE");
		set.add("KEYBOARD");
		set.add("NOTHING");
		
		//정렬되어 저장된건 아니지만 정렬된 것처럼 뽑아옴.
		//자기 기준을 잡아 나보다 ㅋ면 ㅇ른 나보다 작으면 왼
		
		System.out.println(set);
		System.out.println(set.subSet("d","s"));
		System.out.println(set.headSet("e"));
		System.out.println(set.tailSet("e"));
		
	}
}
