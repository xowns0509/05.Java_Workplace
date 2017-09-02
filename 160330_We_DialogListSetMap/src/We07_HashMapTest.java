import java.util.HashMap;
import java.util.Scanner;

public class We07_HashMapTest {

	public static void main(String[] args) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("javassem", "1111");
		map.put("Kimjava", "1234");
		map.put("javabook", "1111");
		map.put("javabook", "9999");//키가 중복瑛릿?덮어짐 1111을 9999로.
		
		Scanner input = new Scanner(System.in);
		boolean stop = false;
		
		while(!stop){
			System.out.println("아이디를 입력하세요 ->");
			String id = input.nextLine();
			System.out.println("PW입력하세요 ->");
			String pass = input.nextLine();//pass가 id와 맞냐.
			
			//입력받은 아이디가 이미 맵의 키에 존재한다면 
			if(map.containsKey(id)){
				//그 key의 value와 입력받은 패스워드와 동일하면 반복문을 멈춤.
				//그렇지 않으면 반복을 진행. ID와 PW가 맞을 때만 종료하겠다.
				
				if(pass.equals(map.get(id))){//map.get(id). id에 해당하는 pass값을 반환. 그 반대는 안됨. 오로지 key가 유일하며 pw를 찾는 이것이 열쇠. map.get(pass)로 id는 못찾는다고. 
					//map.containsValue(pass) 이건 있냐고만 묻는 거임.
					System.out.println("로그인성공");
					stop = true;//불린에 stop 줬으니 그걸 이용
					
				}else{//pw틀린 경우
					System.out.println("pw다시");
					continue; 
				}
				
			}else{//입력받은 아이디가 이미 맵의 키에 존재안한다면
				System.out.println("존재않는 아이디.");
				continue;
			}
		}	
	}
}
