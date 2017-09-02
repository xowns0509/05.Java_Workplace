import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class We06_LottoHash {

	public static void main(String[] args) {
		
		Set<Integer> lotto = new HashSet<Integer>();//자료구조 용어. HashSet은 중복이 나올 경우 그냥 버림.
		for(int i = 0 ; i < 6; i++){// lotto.size() < 6 단순히 6번만 돌리면 그 중간에 반복이 나올 경우가 있을 것이고 그 경우 HashSet이 그걸 버리기 때문에 6개가 다 안나올 때가 있다.
			lotto.add((int)(Math.random()*45)+1);
		}
		
		System.out.println(lotto);
		
		LinkedList reList = new LinkedList(lotto);// 정렬하기 위해 LinkedList로 따옴 .
		Collections.sort(reList);
		System.out.println(reList);
	}

}
