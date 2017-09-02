import java.util.ArrayList;

public class We05_ArrayListTest {//List는 순서 유, 데이터중복 유(허용)

	public static void main(String[] args) {
		ArrayList data = data();
		for(int i = 0 ; i< data.size() ; i++){	//data.size()
			System.out.println(i+"번 어레이리스트: "+data.get(i));	//data.get(i)
		}

	}
	static ArrayList data(){//함수
		
		String name = "송혜교";
		int age = 33;
		double height = 163.5;
		
		String str1[];
		str1 = new String[5];
		str1[0] = "바보";
		str1[1] = "크림";
		str1[2] = "구름";
		str1[3] = "빠리";
		str1[4] = "엔터";
		
		Object[] data = new Object[3];
		//동적인 오브젝트배열.
		
		data[0] = name;
		data[1] = age;		//원래 안되는건데 1.5 이후 부터는 됨.
							//원래는 이렇게 선언생성 data[1] = new Integer(age);
		data[2] = new Double(height);
		
		ArrayList data1 = new ArrayList();//동적으로 크기가 변동되는 오브젝트 배열
/*0*/	data1.add(name);
/*1*/	data1.add(new Integer(age));
/*2*/	data1.add(new Double(height));
/*3*/	data1.add(str1[0]);
/*4*/	data1.add(str1);
/*5*/	data1.add(new String[3]);
/*6*/	data1.add(new String[3]);
/*7*/	data1.add(new String[3]);
		
		//ArrayList에 넣으려면 Object뿐.
		
		return data1;
	}
}
