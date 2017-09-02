import java.util.Arrays;

public class We03_ArrayClone {

	public static void main(String[] args) {
		
		String [] array = {"안녕", "HI", "올라", "고니찌와"};
		String [] copy = array;				//주소가 같이 가리키고 있으니까
		String [] clone = array.clone();	//아예 내용메모리를 새로 확보.
		
		System.out.println("이거는 주소값 반환: "+ array);//여기까지는 아직까지 주소값만을 반환.
		System.out.println();
		
		System.out.println("Arrays.toString(객체명)로 배열에 저장된 값 반환" + Arrays.toString(array));//Arrays.toString(array)로 배열값 출력
		System.out.println(Arrays.toString(copy));
		System.out.println(Arrays.toString(clone)+"\n");		
		
		copy[1] = "하이";
		copy[2] = "Hola";
		System.out.println("copy: "+Arrays.toString(array));
		System.out.println("array: "+Arrays.toString(copy));
		System.out.println("String [] copy = array;로 인해 array와 copy의 주소값이 같으므로");
		System.out.println("copy의 값을 수정하면 array의 값도 수정됨. 그러나,\n");
		
		System.out.println("clone: "+Arrays.toString(clone));
		System.out.println("String [] clone = array.clone();의 경우, 아예 내용메모리가 새로 확보되는 것이므로");
		System.out.println("값이 달라지지 않는다. (아래는 clone[1],[2]에 수정을 가했을 때)");
		
		clone[1] = "하이클론";
		clone[2] = "Hola클론";
		System.out.println(Arrays.toString(clone));
	} 			
}
/*	어째서?
주소가 같이 가리키고 있으니까
내용메모리가 동일하게 복사되는것이 바로 클론. 주소값 복사가 아니라
*/

/*이거는 주소값 반환: [Ljava.lang.String;@2a139a55

Arrays.toString(객체명)로 배열에 저장된 값 반환[안녕, HI, 올라, 고니찌와]
[안녕, HI, 올라, 고니찌와]
[안녕, HI, 올라, 고니찌와]

copy: [안녕, 하이, Hola, 고니찌와]
array: [안녕, 하이, Hola, 고니찌와]
String [] copy = array;로 인해 array와 copy의 주소값이 같으므로
copy의 값을 수정하면 array의 값도 수정됨. 그러나,

String [] clone = array.clone();의 경우 아예 내용메모리가 새로 확보되는 것이므로
값이 달라지지 않는다. 
clone: [안녕, HI, 올라, 고니찌와]
[안녕, 하이클론, Hola클론, 고니찌와]*/
