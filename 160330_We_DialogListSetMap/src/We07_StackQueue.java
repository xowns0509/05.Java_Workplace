import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class We07_StackQueue {

	public static void main(String[] args) {
		Stack<String> stack = new Stack<String>();
		stack.push("가");
		stack.push("나");
		stack.push("다");
		
		while(!stack.empty()){//먼저 들어온게 맨 나중에 나오기 때문에 다부터 나옴. 그리고 그 스택이 비어있을 때 종료
			System.out.println(stack.pop());
		}
		Queue<String> queue = new LinkedList<String>();//먼저 들어온거 먼저 나감
		queue.offer("A");
		queue.offer("B");
		queue.offer("C");
		while(!queue.isEmpty()){//다부터 나옴 그리고 그 스택이 비어있을 때 종료
			System.out.println(queue.poll());
		}
	}

}
