import java.util.*;

class HashMapTest 
{
	// 전화번호를 저장할 맴변수
	HashMap phoneBook = new HashMap();

	/*전화번호 정보를 입력*/
	void insertPhoneNo()
	{
		addPhoneNo("가족", "아빠", "010-111-1111");
		addPhoneNo("가족", "엄마", "010-111-1112");
		addPhoneNo("친구", "만득이", "010-333-0303");
		addPhoneNo("업무", "김사장", "010-999-9999");
		addPhoneNo("친구", "천득이", "010-333-3030");
		addPhoneNo("기타", "슈퍼마켓", "032-333-0000");
		addPhoneNo("가족", "동생", "010-111-1113");
		addPhoneNo("기타", "치킨집", "032-777-9292");
		addPhoneNo("친구", "백득이", "010-888-8080");
	}
	
	// 1. 전화번호 Map에 해당 그룹이름이 없으면 추가
	// 2. 입력된 그룹이름으로 저장된 HashMap을 얻어옴
	// 3. 그 HashMap에 전화번호와 이름을 저장
	
	/*전화번호부에 저장*/
	void addPhoneNo(String groupName, String name, String tel)
	{
		if(!phoneBook.containsKey(groupName)){		//없으면 참
			phoneBook.put(groupName, new HashMap());//phoneBook의 키와 밸류 중 밸류에다 또 다른 hashmap객체를 쳐넣.
		} 
		HashMap group = (HashMap)phoneBook.get(groupName);//get은 오브젝트를 리턴하므로 hashmap으로 다운케.
		group.put(tel, name);
		
	}
		Scanner input = new Scanner(System.in);
		boolean stop = false;
		
		while(!stop){
			System.out.println("그룹명을 입력하세요 ->");
			String inputGName = input.nextLine();
			System.out.println("이름을 입력하세요 ->");
			String inputName = input.nextLine();
			System.out.println("전화번호를 입력하세요 ->");
			String inputTel = input.nextLine();
			
			//입력받은 아이디가 이미 맵의 키에 존재한다면
			if(map.containsKey(inputGName)){
			
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
	/*전화번호부 출력*/
	void printPhoneNo()
	{
		Set set = phoneBook.entrySet();
		Iterator it = set.iterator();
		while( it.hasNext() )
		{
			Map.Entry e = (Map.Entry) it.next();

			Set subSet = ( (HashMap)e.getValue() ).entrySet() ;
			Iterator subIt = subSet.iterator();
			System.out.println(" # " + e.getKey() + " [" + subSet.size() +"]");
			while( subIt.hasNext() )
			{
				Map.Entry  subE = (Map.Entry)subIt.next();
				System.out.println( subE.getValue() + "  " + subE.getKey() );
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) 
	{
		HashMapTest  map = new HashMapTest();
		map.insertPhoneNo();
		map.printPhoneNo();
	}
}