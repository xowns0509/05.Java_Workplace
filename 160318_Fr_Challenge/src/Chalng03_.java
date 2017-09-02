import java.util.Scanner;
/*3. 주민번호 뒷자리를 입력받아 그 번호의 첫번째 문자에 따라 성별을출력하세요.
 * 그 문자가 ‘9’이면 1800년생남자이고 ‘0’이면 1800년생 여자입니다.  ‘1’이면 1900년생 남자이고 ‘2’이면1900년생 여자입니다. 또‘3’ 이면 2000년생 남자이고 ‘4’이면 2000년생 여자입니다.
 * [힌트] 주민번호 뒷자리 7개의 문자는 문자열로 받아야합니다. 문자열을 입력받기 위해서는 Sacnner의 next() 함수를 이용하세요.
 * [힌트] 문자열 String에서 한 문자를 얻어오는 함수는 char charAt(intindex) 함수를 이용하세요.
 */
public class Chalng03_ {

	public static void main(String[] args){
		
		System.out.println("민번 뒤 7자리를 입력하세요.");		
		
		Scanner in0 = new Scanner(System.in);
		String id = in0.nextLine();				// 개행 전까지의 입력을 문자열id로 받음.
		char sung = id.charAt(0);				// 문자열id로부터 성별을 구분 할 숫자 추출.
		
		System.out.println("입력한 민번 뒤 7자리는 " + id + " 이며 " + "\n");		

		switch(sung){
		case '1' : System.out.println("1900년도 출생, 남자입니다"); break;
		case '2' : System.out.println("1900년도 출생, 여자입니다"); break;
		case '3' : System.out.println("2000년도 출생, 남자입니다"); break;
		case '4' : System.out.println("2000년도 출생, 여자입니다"); break;
		case '9' : System.out.println("1800년도 출생, 남자입니다"); break;
		case '0' : System.out.println("1800년도 출생, 여자입니다"); break;
		default : System.out.println("년도, 성별확인 불가, 프로그램 재실행하여 올바른 민번 입력해"); break;
		}	
	}
}