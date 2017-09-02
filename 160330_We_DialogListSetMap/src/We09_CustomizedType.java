import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class MyType <K, V>//클래스를 선언 할 때 부터 자료형 명시
{
	K name; //내가 자료형을 만드는거임
	V value;
	
	MyType(K name, V value){
		this.name= name;
		this.value = value;
	}
	
	K getName(){
		return name;
	}
	
	V getValue(){
		return value;
	}
}

public class We09_CustomizedType {

	public static void main(String[] args) {
		MyType<String, String> name = new MyType<String, String>("이름", "홍길동");
		MyType<String, Integer> age = new MyType<String, Integer>("나이", new Integer(33));
		MyType<String, Double> height = new MyType<String, Double>("키", new Double(165.3));
		
		ArrayList<MyType> list = new ArrayList<MyType>();
		list.add(name);
		list.add(age);
		list.add(height);
		
		for(int i = 0 ; i <list.size(); i++){
			MyType mytype = list.get(i);
			System.out.println(mytype.getName() + "/" + mytype.getValue());
		}
		System.out.println();
		
		//향상된 for문. 콜렉션일때만!
		for(MyType mytype : list){ //37 ~ 40까지의 for문
			System.out.println("2>"+ mytype.getName() + "/" + mytype.getValue());
		}
		
	}

}
